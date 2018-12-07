package com.aisy.b2c.controller.admin;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.aisy.b2c.cache.ProductCache;
import com.aisy.b2c.dao.StorageMapper;
import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.pojo.Brand;
import com.aisy.b2c.pojo.PAttrName;
import com.aisy.b2c.pojo.Product;
import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.pojo.Sku;
import com.aisy.b2c.pojo.Storage;
import com.aisy.b2c.pojo.VSku;
import com.aisy.b2c.pojo.VStorageProdcutDetail;
import com.aisy.b2c.service.ICacheService;
import com.aisy.b2c.service.IProductService;
import com.aisy.b2c.util.JsonUtil;
import com.aisy.b2c.util.Message;
import com.aisy.b2c.util.SYConst;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/system/product")
public class ProductController {
	
	@Resource
	IProductService productService;
	
	@Resource
	ICacheService cacheService;
	
	@Resource
	ProductCache PRODUCT_CACHE;
	
	@Resource
	Message message;

	/**
	 * 产品属性值新增
	 * @param context
	 * @param request
	 * @return back/product/attr/attr_list
	 */
	@RequestMapping("/attr/list")
	public String attrList(Map<String, Object> context,
			HttpServletRequest request) {
		/*从session中取出用户id*/
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		return "back/product/attr/attr_list";
	}
	
	@RequestMapping("/category/list")
	public String categoryList(Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		return "back/product/category/category_list";
	}
	
	@RequestMapping("/list")
	public String productList(Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		return "back/product/product_list";
	}
	
	@RequestMapping("/sku/list")
	public String skuList(Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		return "back/product/sku/sku";
	}
	
	/**
	 * 返回品牌视图页面
	 * @return "back/product/brand/brand_list"
	 */
	@RequestMapping(value="/brand/list", method=RequestMethod.GET)
	public String brandListPage(HttpServletRequest request) {
		return "back/product/brand/brand_list";
	}
	
	/**
	 * 按分页取得所有品牌
	 * @param brandName 品牌名称
	 * @param page 页数
	 * @param limit 显示条数
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/brandList.json", method=RequestMethod.GET)
	public Map<String, Object> getBrandAll(
			String brandName,
			int page,
			int limit){
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.PRODUCT_PARAM.BRAND_NAME, brandName);
		context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
		context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		return productService.getBrandAllByList(context);
	}
	
	/**
	 * 品牌新增
	 * @param request
	 * @param context
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/brand/info", method=RequestMethod.PUT)
	public Map<String, Object> newBrand(HttpServletRequest request,
			@RequestBody Map<String, Object> context) {
		/* 获取当前登陆用户ID  */
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return productService.newBrand(context);
	}
	
	/**
	 * 品牌修改
	 * @param context
	 * @param request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/brand/info", method=RequestMethod.POST)
	public Map<String, Object> updateBrand(HttpServletRequest request,
			@RequestBody Map<String, Object> context) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return productService.updateBrand(context);
	}
	
	/**
	 * 品牌删除
	 * @param brandId
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/brand/info/{brandId}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteBrand(
			@PathVariable("brandId") String brandId) {
		return productService.deleteBrand(brandId);
	}
	
	/**
	 * 品牌名重复校验
	 * @param brandName
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/brandNameConfirm.json", method=RequestMethod.GET)
	public Map<String, Object> brandNameConfirm(@RequestParam String brandName) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		for (Iterator<Entry<String, Brand>> it = PRODUCT_CACHE.BRAND.entrySet().iterator(); it.hasNext();) {
			Entry<String, Brand> brand = it.next();
			if (brand.getValue().getBrandName().equals(brandName)) {
				result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
				return result;
			}
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
		return result;
	}
	
	/**
	 * 返回货架视图页面
	 * @return "back/product/storage/storageproduct_list"
	 */
	@RequestMapping(value="/storage/list", method=RequestMethod.GET)
	public String storageProdcut(Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		return "back/product/storage/storageproduct_list";
	}
	/**
	 * 查询属性名与属性值
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping("/attr/productAttr.json")
	public Map<String, Object> getProductAttrAll() {
		return productService.getAttrInfoAll(null);
	}
	
	/**
	 * 对属性名修改，通过id进行查找
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/attr/info", method=RequestMethod.GET)
	public Map<String, Object> getAttrInfo(
			@RequestParam String pAttrNameId,
			HttpServletRequest request) {
		return productService.getAttrInfo(pAttrNameId);
	}
	/**
	 * 对属性名进行新增
	 * @param context
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/attr/info", method=RequestMethod.PUT)
	public Map<String, Object> newAttrName(@RequestBody Map<String, Object> context) {
		return productService.insertAttrName(context);
	}
	/**
	 * 产品属性修改
	 * @param context
	 * @param request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/attr/info", method=RequestMethod.POST)
	public Map<String, Object> updateAttrName(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		return productService.updateAttrName(context);
	}
	
	/**
	 * 产品属性值删除
	 * @param pAttrNameId
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/attr/deleteAttrName/{pAttrNameId}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteAttrName(
			@PathVariable("pAttrNameId") String pAttrNameId) {
		return productService.deleteAttrName(pAttrNameId);
	}
	
	/**
	 * 产品属性名删除
	 * @param pAttrValueId
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/attr/deleteAttr/{pAttrValueId}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteAttrValue(
			@PathVariable("pAttrValueId") String pAttrValueId) {
		return productService.deleteAttrValue(pAttrValueId);
	}

	/**
	 * 取得产品属性值用于编辑窗口回填数据
	 * @param context
	 * @param request
	 * @return List
	 */
	@ResponseBody
	@RequestMapping(value="/attr/value/info", method=RequestMethod.GET)
	public List<PAttrName> getAttrName(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		return productService.getAttrName(context);
	}
	
	/**
	 * 属性名重复校验
	 * @param pAttrName
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/attrNameConfirm.json", method=RequestMethod.GET)
	public Map<String, Object> attrNameConfirm(@RequestParam String pAttrName) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		for (Iterator<Entry<String, PAttrName>> it = PRODUCT_CACHE.P_ATTR_NAME.entrySet().iterator(); it.hasNext();) {
			Entry<String, PAttrName> PAttrName = it.next();
			if (PAttrName.getValue().getpAttrName().equals(pAttrName)) {
				result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
				return result;
			}
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
		return result;
	}
	
	/**
	 * 产品属性值新增
	 * @param context
	 * @param request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/attr/value/info", method=RequestMethod.PUT)
	public Map<String, Object> newAttrValue(@RequestBody Map<String, Object> context) {
		return productService.insertAttrValue(context);
	}
	
	@ResponseBody
	@RequestMapping(value="/category/categoryInfo.json", method=RequestMethod.GET)
	public Map<String, Object> getCategoryInfo() {
		return productService.getCategoryInfo(null);
	}
	
	@ResponseBody
	@RequestMapping(value="/category/info", method=RequestMethod.PUT)
	public Map<String, Object> newCategoryInfo(
			@RequestBody Map<String, Object> context, HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		Map<String, Object> result = productService.newCategoryInfo(context);
		cacheService.categoryCache();
		cacheService.categoryAttrCache();
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/category/info", method=RequestMethod.GET)
	public Map<String, Object> getCategoryDetailInfo(@RequestParam String categoryId) {
		
		if (StringUtils.isEmpty(categoryId)) {
			throw new SYException("分类ID必须缺失！");
		}
		Short categoryIdShort = Short.valueOf(categoryId);
		
		return productService.getCategoryDetailInfo(categoryIdShort);
	}
	
	@ResponseBody
	@RequestMapping(value="/category/info", method=RequestMethod.POST)
	public Map<String, Object> getCategoryDetailInfo(
			@RequestBody Map<String, Object> context
			) {
		Map<String, Object> result;
		synchronized (this) {
			result = productService.updateCategory(context);
		}
		cacheService.categoryCache();
		cacheService.categoryAttrCache();
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/status.json", method=RequestMethod.GET) 
	public List<Map<String, Object>> getStatus() {
		return productService.getStatus(null);
	}
	/**
	 * 查询出brand.json
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/brand.json", method=RequestMethod.GET) 
	public List<Brand> getBrands() {
		return productService.getBrands(null);
	}
	/* add start by liuzhaoyu for product search by page 2018-05-11 17:50:30 */
	/**
	 * 商品条件查询的方法
	 * 
	 * @param product对象
	 * @param page 当前页
	 * @param limit 每页限制数
	 * @param sortBy 排序条件
	 * @param direction 排序正反序
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value="/productList.json", method=RequestMethod.GET)
	public Map<String, Object> getproductListData(
			Product product,
            @RequestParam(value="sortBy", required=false) String sortBy,
            @RequestParam(value="direction", required=false) String direction,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="limit", required=false) Integer limit) {
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.PRODUCT_PARAM.PRODUCT_NAME, product.getProductName());
		context.put(SYConst.PRODUCT_PARAM.BRAND_ID, product.getBrandId());
		context.put(SYConst.PRODUCT_PARAM.STATUS, product.getStatus());
		
		if (null != page && null != limit) {
			context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
			context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		}
		
		context.put(SYConst.PAGE_PARAM_NAME.SORTBY, sortBy);
		context.put(SYConst.PAGE_PARAM_NAME.DIRECTION, direction);
		return productService.getProductData(context);
	}
	/* add end by liuzhaoyu for product search by page 2018-05-11 18:17:30 */
	
	/* updated start by YanqingLiu for FORM validation 2018-05-14 10:00:30 */
	/**
	 * 商品新增
	 * 
	 * @param productBody 商品主体属性
	 * @param cover 商品封面图片
	 * @param files 商品详细图片
	 * @return 
	 */
//	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.POST) 
	public Map<String, Object> newProduct(HttpServletRequest request,
			@RequestParam(value="productBody", required=true) String productInfoJson,
			@RequestParam(value="cover", required=false) CommonsMultipartFile cover,
			@RequestParam(value="files", required=false) CommonsMultipartFile[] files
			) throws IOException {
		/* 创建结果集  */
		Map<String, Object> context = new HashMap<String, Object>();
		/* 获取当前登陆用户ID  */
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		/* 将product数据实现JSON与Object之间的互转  */
		JSONObject productJson = JsonUtil.stringToObj(productInfoJson);
		/* 定义图片存放相对路径  */
		String path = "/WEB-INF/front/assets/";
		/* 获取绝对路径  */
		String uploadRealPath = request.getSession().getServletContext().getRealPath(path);
		/* 创建图片MAP */
		Map<String, Object> imageMap = new HashMap<String, Object>();
		/* 当productCover图片不为空时， 将图片存入MAP */
		if (null != cover) {
			File coverFile = new File(uploadRealPath, System.currentTimeMillis()+cover.getOriginalFilename());
			FileUtils.copyInputStreamToFile(cover.getInputStream(), coverFile);
			imageMap.put("cover", coverFile.getName());
		/* 否则抛出异常  */	
		} else {
			throw new SYException(message.MPI03);
		}
		/* 当productDetail图片不为空时， 将图片存入MAP */
		if (null != files) {
			List<File> detailFileList = new ArrayList<File>();
			List<String> detailFileUrlList = new ArrayList<String>();
			for (CommonsMultipartFile detailCMFile : files) {
				File detailFile = new File(uploadRealPath, System.currentTimeMillis()+detailCMFile.getOriginalFilename());
				FileUtils.copyInputStreamToFile(detailCMFile.getInputStream(), detailFile);
				detailFileList.add(detailFile);
				detailFileUrlList.add(detailFile.getName());
			}
			imageMap.put("detail", detailFileUrlList);
		/* 否则抛出异常  */	
		} else {
			throw new SYException(message.MPI03);
		}
		/* 将图片MAP存入context */
		context.put("image", imageMap);
		/* 从productJson取出product各项数据  */
		String productName = productJson.getString("productName");
		String brandIdStr = productJson.getString("brandId");
		String des = productJson.getString("des");
		String showPrice = productJson.getString("showPrice");
		String status = productJson.getString("status");
		/* 定义商品MAP并将数据存放进去  */
		Map<String, Object> productMap = new HashMap<String, Object>();
		productMap.put("productName", productName);
		productMap.put("brandId", Short.valueOf(brandIdStr));
		productMap.put("des", des);
		productMap.put("showPrice", new BigDecimal(showPrice));
		productMap.put("status", status);
		context.put("product", productMap);
		/* 将category数据实现JSON与Object之间的互转  */
		JSONObject categoryJson = productJson.getJSONObject("category");
		/* 如果category数据为空抛出异常  */
		if (StringUtils.isEmpty(categoryJson)) {
			throw new SYException(message.MPI03);
		}
		/* 定义LIST存放category */
		List<Short> categoryList = new ArrayList<Short>();
		/* 将循环的category数据放入LIST */
		for (Iterator<String> it = categoryJson.keySet().iterator(); it.hasNext(); ) {
			String categoryIdStr = categoryJson.getString(it.next());
			categoryList.add(Short.valueOf(categoryIdStr));
		}
		/* 将循categoryList放入context */
		context.put("category", categoryList);
		/* 将attr数据实现JSON与Object之间的互转  */
		JSONObject attrJson = productJson.getJSONObject("attr");
		/* 如果attr数据为空抛出异常  */
		if (StringUtils.isEmpty(attrJson)) {
			throw new SYException(message.MPI03);
		}
		/* 定义LIST存放attr */
		List<Map<String, Object>> attrList = new ArrayList<Map<String, Object>>();
		/* 将循环的attr数据放入LIST */
		for (Iterator<String> it = attrJson.keySet().iterator(); it.hasNext();) {
			String level = it.next();
			JSONObject attrLevelJson = attrJson.getJSONObject(level);
			for (Iterator<String> ite = attrLevelJson.keySet().iterator(); ite.hasNext();) {
				String keyPAttrNameId = ite.next();
				JSONObject attrValueJson = attrLevelJson.getJSONObject(keyPAttrNameId);
				Map<String, Object> attrMap = new HashMap<String, Object>();
				attrMap.put("isSku", attrValueJson.getBoolean("isSku"));
				attrMap.put("pAttrNameId", Short.valueOf(keyPAttrNameId));
				for (Iterator<String> itAttrValue = attrValueJson.keySet().iterator(); itAttrValue.hasNext();) {
					String pAttrValueIdStr = itAttrValue.next();
					if (!"isSku".equals(pAttrValueIdStr)) {
						attrMap.put(pAttrValueIdStr, Short.valueOf(pAttrValueIdStr));
					}
				}
				attrList.add(attrMap);
			}
		}
		/* 将循attrList放入context */
		context.put("attr", attrList);
		/* 调用Service方法  */
		return productService.newProductInfo(context);
	}
	/* updated end by YanqingLiu for FORM validation 2018-05-14 10:00:30 */
	
	@ResponseBody
	@RequestMapping(value="/sku.json", method=RequestMethod.GET)
	public Map<String, Object> getSkuList(
				VSku sku,
				
				@RequestParam(value="sortBy", required=false) String sortBy,
	            @RequestParam(value="direction", required=false) String direction,
	            @RequestParam(value="page", required=false) Integer page,
				@RequestParam(value="limit", required=false) Integer limit
				
	         
				
				) {

		Map<String, Object> context = new HashMap<String, Object>();
		
		/*update sku by huangjun 2018-06-04 16:32:45 */
		context.put(SYConst.PRODUCT_PARAM.PRODUCT_NAME, sku.getProductName());
		/*update sku by huangjun 2018-06-04 16:33:30 */
		
		if (null != page && null != limit) {
			context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
			context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		}
		context.put(SYConst.PAGE_PARAM_NAME.SORTBY, sortBy);
		context.put(SYConst.PAGE_PARAM_NAME.DIRECTION, direction);
		return productService.getSkuInfo(context);
	}
	
	@ResponseBody
	@RequestMapping(value="/sku/info", method=RequestMethod.PUT)
	public Map<String, Object> newSku(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request
			) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return productService.newSku(context);
	}
	
	@ResponseBody
	@RequestMapping(value="/productAttrNameValue.json", method=RequestMethod.GET)
	public Map<String, Object> getProductAttrValueInfo(
			@RequestParam(value="productId", required=true) short productId
			) {
		return productService.getAttrValueByProductId(productId);
	}
	
	/**
	 * 变更商品状态
	 * @param context
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/changeProductStatus.json", method=RequestMethod.POST)
	public Map<String, Object> changeProductStatus(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		return productService.changeProductStatus(context);
	}
	

	/* start by liuyanqing add new service Method for storage and storage product 2018-06-04 12:00:00 */
	
	/**
	 * 变更货架状态
	 * @param context
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/changeStorageStatus.json", method=RequestMethod.POST)
	public Map<String, Object> changeStorageStatus(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		cacheService.storageCache();
		return productService.changeStorageStatus(context);
	}
	
	/**
	 * 取得所有货架
	 * @param storageName
	 * @param page 当前页
	 * @param limit 每页限制数
	 * @return List
	 */
	@ResponseBody
	@RequestMapping(value="/storageList.json", method=RequestMethod.GET)
	public Map<String, Object> getStorageAll(
			String storageName, 
			int page,
			int limit){
		Map<String, Object> context = new HashMap<String, Object>(3);
		context.put(SYConst.PRODUCT_PARAM.STORAGE_NAME, storageName);
		context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
		context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		return productService.getStorageAll(context);
	}
	
	/**
	 * 取得货架商品
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/frontStorageInfo.json", method=RequestMethod.GET)
	public Map<String, Object> getFrontStorageInfo(Map<String, Object> context){
		return productService.getFrontStorageInfo(context);
		
	}
	
	/**
	 * 货架删除
	 * @param storageId
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/storage/info/{storageId}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteStorage(
			@PathVariable("storageId") String storageId) {
		return productService.deleteStorage(storageId);
	}
	
	/**
	 * 取得货架及货架商品信息
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping("/storage/storageProdcutInfo.json")
	public Map<String, Object> getStorageInfoAll() {
		return productService.getStorageInfoAll(null);
	}
	
	/**
	 * 新增货架及商品
	 * @param context
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/storageProduct/info", method=RequestMethod.PUT)
	public Map<String, Object> newStorageWithProduct(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request
			) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		synchronized (this) {
			productService.newStorageWithProduct(context);
		}
		cacheService.storageCache();
		return result;
	}
	
	/**
	 * 货架名重复校验
	 * @param storageName
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/storageNameConfirm.json", method=RequestMethod.GET)
	public Map<String, Object> storageNameConfirm(@RequestParam String storageName) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Iterator<Entry<String, Storage>> it = PRODUCT_CACHE.STORAGE.entrySet().iterator(); it.hasNext();) {
			Entry<String, Storage> storage = it.next();
			if (storage.getValue().getStorageName().equals(storageName)) {
				result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
				return result;
			}
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
		return result;
	}
	
	/**
	 * 修改货架名与描述
	 * @param context
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/storage/info", method=RequestMethod.POST)
	public Map<String, Object> updateStorage(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request
			) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		synchronized (this) {
			productService.updateStorage(context);
		}
		cacheService.storageCache();
		return result;
	}

	/**
	 * 根据货架取得货架商品信息
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping("/getStorageProdcutInfoById.json")
	public List<VStorageProdcutDetail> getStorageProdcutInfoByStorageId(
			@RequestParam String storageId) {
		return productService.getStorageProdcutInfoByStorageId(storageId);
	}
	
	/**
	 * 根据货架取得货架商品信息且不包含当前货架商品信息
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping("/selectStorageProdcutInfoExceptId.json")
	public List<VStorageProdcutDetail> selectStorageProdcutInfoExceptId(
			String storageId,
			String productName) {
		Map<String, Object> context = new HashMap<String, Object>(5);
		context.put(SYConst.PRODUCT_PARAM.STORAGE_ID, storageId);
		context.put(SYConst.PRODUCT_PARAM.PRODUCT_NAME, productName);
		return productService.getStorageProdcutInfoExceptId(context);
	}
	
	/**
	 * 货架商品移除
	 * @param storageProductId
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/storageProduct/info/{stroageId}/{productId}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteStorageProduct(
			@PathVariable("stroageId") String stroageId,
			@PathVariable("productId") String productId) {
		return productService.deleteStorageProduct(stroageId,productId);
	}

	/**
	 * 变更货架商品
	 * @param stroageId
	 * @param prodcut
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/updateStorageProduct/info/{stroageId}", method=RequestMethod.PUT)
	public Map<String, Object> newStorageProduct(
			@PathVariable("stroageId") String stroageId,
			@RequestBody ArrayList<String> prodcut,
			HttpServletRequest request
			) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		Map<String, Object> context = new HashMap<String, Object>(4);
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.PRODUCT_PARAM.PRODUCT, prodcut);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		context.put(SYConst.PRODUCT_PARAM.STORAGE_ID, stroageId);
		synchronized (this) {
			productService.newStorageProduct(context);
		}
		cacheService.storageProductsCache();
		return result;
	}
	
	/**
	 * 变更货架顺序
	 * @param option
	 * @param storageId
	 * @param sortInex
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/changeStorageSortIndex/info/{option}/{storageId}/{sortInex}", method=RequestMethod.POST)
	public Map<String, Object> changeStorageSortIndex(
			@PathVariable("option") String option,
			@PathVariable("storageId") String storageId,
			@PathVariable("sortInex") String sortInex,
			HttpServletRequest request
			){
		Map<String, Object> context = new HashMap<String, Object>(2);
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		context.put(SYConst.PRODUCT_PARAM.OPTION, option);
		context.put(SYConst.PRODUCT_PARAM.STORAGE_ID, storageId);
		context.put(SYConst.PRODUCT_PARAM.SORT_INDEX, sortInex);
		return productService.changeStorageSortIndex(context);		
	}
	
	/**
	 * 根据货架取得货架商品信息
	 * @param storageId
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping("/getStorageProdcutInfoByStorageIdForupdate.json/{storageId}")
	public List<VStorageProdcutDetail> getStorageProdcutInfoByStorageIdForupdate(
			@PathVariable("storageId") String storageId) {
		return productService.getStorageProdcutInfoByStorageId(storageId);
	}
	
	/**
	 * 变更货架内商品顺序
	 * @param option
	 * @param storageId
	 * @param productId
	 * @param sortInex
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/changeStorageProdcutSortIndex/info/{option}/{storageId}/{productId}/{sortInex}", method=RequestMethod.POST)
	public Map<String, Object> changeStorageProdcutSortIndex(
			@PathVariable("option") String option,
			@PathVariable("storageId") String storageId,
			@PathVariable("productId") String productId,
			@PathVariable("sortInex") String sortInex,
			HttpServletRequest request
			){
		Map<String, Object> context = new HashMap<String, Object>(2);
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		context.put(SYConst.PRODUCT_PARAM.OPTION, option);
		context.put(SYConst.PRODUCT_PARAM.STORAGE_ID, storageId);
		context.put(SYConst.PRODUCT_PARAM.PRODUCT_ID, productId);
		context.put(SYConst.PRODUCT_PARAM.SORT_INDEX, sortInex);
		return productService.changeStorageProdcutSortIndex(context);		
	}
	
	/* end by liuyanqing add new service Method for storage and storage product 2018-06-04 12:00:00 */

	/* add start by huangjun for sku delete by  2018-06-04 10:34:30 */
	/**
	 * 删除商品库存信息
	 * @param skuId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/info/{skuId}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteSku(
			@PathVariable("skuId") String skuId
			) {	
		
		return productService.deleteSku(Short.valueOf(skuId));
	}
	/* add end by huangjun for sku delete by  2018-06-04 10:34:30 */
	
	/* add start by huangjun for category update by  2018-06-04 10:46:30 */
	/**
	 * 修改分类状态
	 * @param context
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/changeCategoryStatus.json", method=RequestMethod.POST)
	public synchronized Map<String, Object> changeCategoryStatus(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		
		cacheService.categoryCache();
		return productService.changeCategoryStatus(context);
	}
	/* add end by huangjun for category update by  2018-06-04 10:46:30 */
	
	/**
	 * 选择商品页面 商品查询 带出商品属性
	 * @param productId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/productAttrNameValue1.json", method=RequestMethod.GET)
	public Map<String, Object> getProductAttrNameValueInfo(@RequestParam Short productId) {
		
		return productService.getProductAttrNameValueInfo(productId);
	}
	
	/**
	 * 选择商品页面,点击商品条件查询
	 * @param Product类
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value="/productName.json", method=RequestMethod.GET)
	public List<Product> getproductListData(
			Product product
			) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.PRODUCT_PARAM.PRODUCT_NAME, product.getProductName());
		return productService.getProductNames(context);
	}
    
	/*add start sunzhiming add new controller 2018-06-04 9:53*/
	/**
	 * 产品属性值修改
	 * @param context
	 * @param request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/attrvalue/info", method=RequestMethod.POST)
	public Map<String, Object> updateAttrValueName(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		
		return productService.updateAttrValue(context);
	}
	/*end start sunzhiming add new controller 2018-06-04 9:53*/
	/*add start sunzhiming add new controller 2018-06-04 9:53*/
	/**
	 * 修改属性值，调用的函数，通过id找属性值
	 * @param String
	 * @param HttpServletRequest request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/attrValue/info", method=RequestMethod.GET)
	public Map<String, Object> getAttrValueInfo(
			@RequestParam String pAttrValueId,
			HttpServletRequest request) {
		return productService.getAttrValueInfo(pAttrValueId);
	}
	/*end start sunzhiming add new controller 2018-06-04 9:53*/
	
	/**
	 * 库存更新
	 * @param request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/sku1/info", method=RequestMethod.POST)
	public Map<String, Object> updateSku(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
	/*	从season中取出userid*/
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return productService.updateSku(context);
	}
	
	/**
	 * 更改分类排序
	 * @param context
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/categorysortindex/info", method=RequestMethod.POST)
	public Map<String, Object> getCategorySortIndexInfo(
			@RequestBody Map<String, Object> context
			) {
		
	    Map<String, Object> result = productService.updateCategorySortIndex(context);
		
		
		
		return result;
	}
	/**
	 * 通过brandid来取值，进行修改
	 * @param String brandId
	 * @return
	 */
	 @ResponseBody
	 @RequestMapping(value="/brand/selectbrandid",method=RequestMethod.GET)
	  public Map<String,Object> getRole(@RequestParam String brandId){
	       
	        return productService.getBrandWithId(Short.valueOf(brandId));
	    }
	
	

	
}   

