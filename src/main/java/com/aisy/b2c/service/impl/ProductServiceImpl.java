package com.aisy.b2c.service.impl;



import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.aisy.b2c.cache.CommonCache;
import com.aisy.b2c.cache.ProductCache;
import com.aisy.b2c.dao.BrandMapper;
import com.aisy.b2c.dao.CategoryAttrMapper;
import com.aisy.b2c.dao.CategoryMapper;
import com.aisy.b2c.dao.EvaluationMapper;
import com.aisy.b2c.dao.PAttrNameMapper;
import com.aisy.b2c.dao.PAttrValueMapper;
import com.aisy.b2c.dao.ProductAttrMapper;
import com.aisy.b2c.dao.ProductCategoryMapper;
import com.aisy.b2c.dao.ProductImageMapper;
import com.aisy.b2c.dao.ProductMapper;
import com.aisy.b2c.dao.SkuMapper;
import com.aisy.b2c.dao.StorageMapper;
import com.aisy.b2c.dao.StorageProductsMapper;
import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.model.ProductModel;
import com.aisy.b2c.pojo.AdvertisementImage;
import com.aisy.b2c.pojo.Brand;
import com.aisy.b2c.pojo.Category;
import com.aisy.b2c.pojo.CategoryAttr;
import com.aisy.b2c.pojo.Emnu;
import com.aisy.b2c.pojo.Evaluation;
import com.aisy.b2c.pojo.PAttrName;
import com.aisy.b2c.pojo.PAttrValue;
import com.aisy.b2c.pojo.Product;
import com.aisy.b2c.pojo.ProductAttr;
import com.aisy.b2c.pojo.ProductCategory;
import com.aisy.b2c.pojo.ProductImage;
import com.aisy.b2c.pojo.Sku;
import com.aisy.b2c.pojo.Storage;
import com.aisy.b2c.pojo.StorageProducts;
import com.aisy.b2c.pojo.VCategory;
import com.aisy.b2c.pojo.VProductBrandCategoryEmnu;
import com.aisy.b2c.pojo.VSku;
import com.aisy.b2c.pojo.VStorageProdcutDetail;
import com.aisy.b2c.service.ICacheService;
import com.aisy.b2c.service.IProductService;
import com.aisy.b2c.util.JsonUtil;
import com.aisy.b2c.util.Message;
import com.aisy.b2c.util.SYConst;
import com.aisy.b2c.util.SYConst.PRODUCT_PARAM;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import net.sf.json.JSONObject;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class ProductServiceImpl implements IProductService {
	
	
	@Resource
	CommonCache COMMON_CACHE;
	
	@Resource
	ProductCache PRODUCT_CACHE;
	
	@Resource
	IProductService productservice;
	
	@Resource
	ICacheService cacheService;

	@Resource
	PAttrNameMapper pAttrNameMapper;
	
	@Resource
	PAttrValueMapper pAttrValueMapper;
	
	@Resource
	CategoryMapper categoryMapper;
	
	@Resource
	CategoryAttrMapper categoryAttrMapper;
	
	@Resource
	ProductMapper productMapper;
	
	@Resource
	ProductCategoryMapper productCategoryMapper;
	
	@Resource
	ProductAttrMapper productAttrMapper;
	
	@Resource
	ProductImageMapper productImageMapper;
	
	@Resource
	StorageMapper storageMapper;
	
	@Resource
	SkuMapper skuMapper;
	
	@Resource
	BrandMapper brandMapper;
	
	@Resource
	StorageProductsMapper storageProductsMapper;

	@Resource
	EvaluationMapper evaluationMapper;

	@Resource(name="message")
	Message message;
	
	@Override
	public Map<String, Object> getAttrInfoAll(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<PAttrName> nameList = pAttrNameMapper.selectAll();
		
		List<Short> pAttrNameList = new ArrayList<Short>();
		Map<String, PAttrName> attrNameMapper = new HashMap<String, PAttrName>();
		
		for (Iterator<PAttrName> it = nameList.iterator(); it.hasNext();) {
			PAttrName pan = it.next();
			pAttrNameList.add(pan.getpAttrNameId());
			attrNameMapper.put(String.valueOf(pan.getpAttrNameId()), pan);
		}
		
		List<PAttrValue> valueList = pAttrValueMapper.selectAll();
		Map<String, List<PAttrValue>> attrValueMapper = new HashMap<String, List<PAttrValue>>();
		
		for (Iterator<PAttrValue> it = valueList.iterator(); it.hasNext();) {
			PAttrValue pav = it.next();
			
			String pAttrNameId = String.valueOf(pav.getpAttrNameId());
			
			/* 当attrValueMapper中存在指定的属性名ID时 */
			if (attrValueMapper.containsKey(pAttrNameId)) {
				List<PAttrValue> pvalueList = attrValueMapper.get(pAttrNameId);
				pvalueList.add(pav);
			/* 当attrValueMapper中不存在指定的属性名ID时 */
			} else {
				List<PAttrValue> pvalueList = new ArrayList<PAttrValue>();
				pvalueList.add(pav);
				attrValueMapper.put(pAttrNameId, pvalueList);
			}
		}
		
		result.put(PRODUCT_PARAM.P_ATTR_NAME_LIST, pAttrNameList);
		result.put(PRODUCT_PARAM.ATTR_NAME_MAPPER, attrNameMapper);
		result.put(PRODUCT_PARAM.ATTR_VALUE_MAPPER, attrValueMapper);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		
		return result;
	}


	@Override
	public synchronized Map<String, Object> insertAttrName(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		String pAttrNameStr = (String) context.get(PRODUCT_PARAM.P_ATTR_NAME);
		
		if (StringUtil.isEmpty(pAttrNameStr)) {
			throw new SYException(message.MPI01);
		}
		
		PAttrName pAttrName = new PAttrName();
		pAttrName.setpAttrName(pAttrNameStr);
		pAttrNameMapper.insert(pAttrName);
		
		//TODO 重新生成attr缓存文件
		cacheService.pAttrNameCache();  // 更新属性名缓存
		cacheService.pAttrValueCache(); // 更新属性值缓存
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		result.put(PRODUCT_PARAM.P_ATTR_NAME, pAttrName);
		
		return result;
	}


	@Override
	public synchronized Map<String, Object> insertAttrValue(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		//声明结果集
		Map<String, Object> result = new HashMap<String, Object>();
		//从前面传来的集合，从集合中取出属性名，取出属性值，进行insert，如何失败则抛出异常
		try {
			String pAttrNameId = (String) context.get(PRODUCT_PARAM.P_ATTR_NAME_ID);
			String pAttrValue = (String) context.get(PRODUCT_PARAM.P_ATTR_NAME_VALUE);
			PAttrValue pav = new PAttrValue();
			pav.setpAttrNameId(Short.valueOf(pAttrNameId));
			pav.setpAttrValue(pAttrValue);
			pAttrValueMapper.insert(pav);
			cacheService.pAttrNameCache();  // 更新属性名缓存
			cacheService.pAttrValueCache(); // 更新属性值缓存
			result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
			result.put(PRODUCT_PARAM.P_ATTR_NAME_VALUE, pav);
			
			return result;
			
		} catch (Exception e) {
			throw new SYException(message.MPE01, e);
		}

	}


	@Override
	public Map<String, Object> deleteAttrValue(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		return null;
	}


	@Override
	public Map<String, Object> getCategoryInfo(Map<String, Object> context) {

		Map<String, VCategory> categoryInfo = new HashMap<String, VCategory>();
		Map<String, List<Short>> children = new HashMap<String, List<Short>>();
		List<Short> levelOne = new ArrayList<Short>();
		Map<String, List<Short>> categoryAttrInfo = new HashMap<String, List<Short>>();
		
		for (Iterator<Entry<String, Category>> it 
				= PRODUCT_CACHE.CATEGORY.entrySet().iterator(); it.hasNext();) {
			Entry<String, Category> entry = it.next();
			Category category = entry.getValue();
			
			/* 构建分类信息结构 */
			VCategory vCategory = new VCategory();
			
			vCategory.setCategoryId(category.getCategoryId());
			vCategory.setCategoryLevel(category.getCategoryLevel());
			vCategory.setCategoryName(category.getCategoryName());
			vCategory.setpCategoryId(category.getpCategoryId());
			vCategory.setSortIndex(category.getSortIndex());
			vCategory.setStatus(category.getStatus());
			vCategory.setStatusName(COMMON_CACHE.EMNU.get(SYConst.EMNU.STATUS).get(category.getStatus()).getEmnuValue());
			
			categoryInfo.put(String.valueOf(vCategory.getCategoryId()), vCategory);
			
			/* 构建分类子父级结构 */
			if (0 != category.getpCategoryId()) {
				if (children.containsKey(String.valueOf(category.getpCategoryId()))) {
					List<Short> childrenList = children.get(String.valueOf(category.getpCategoryId()));
					childrenList.add(category.getCategoryId());
				} else {
					List<Short> childrenList = new ArrayList<Short>();
					childrenList.add(category.getCategoryId());
					children.put(String.valueOf(category.getpCategoryId()), childrenList);
				}
			/* 构建顶级分类结构 */
			} else {
				levelOne.add(category.getCategoryId());
			}
			
			/* 构建分类属性结构 */
			categoryAttrInfo.put(String.valueOf(category.getCategoryId()), new ArrayList<Short>());
		}
		
		/* 分类属性结构赋值 */
		for (Iterator<Entry<String, CategoryAttr>> it 
				= PRODUCT_CACHE.CATEGORY_ATTR.entrySet().iterator(); it.hasNext();) {
			Entry<String, CategoryAttr> entry = it.next();
			CategoryAttr categoryAttr = entry.getValue();
			List<Short> list = categoryAttrInfo.get(String.valueOf(categoryAttr.getCategoryId()));
			list.add(categoryAttr.getpAttrNameId());
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.PRODUCT_PARAM.CATEGORY_INFO, categoryInfo);
		result.put(SYConst.PRODUCT_PARAM.CHILDREN, children);
		result.put(SYConst.PRODUCT_PARAM.LEVEL_ONE, levelOne);
		result.put(SYConst.PRODUCT_PARAM.CATEGORY_ATTR_INFO, categoryAttrInfo);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> newCategoryInfo(Map<String, Object> context) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		Date currentDate = new Date(System.currentTimeMillis());
		
		Integer categoryLevel = (Integer) context.get(PRODUCT_PARAM.CATEGORY_LEVEL);
		String categoryName = (String) context.get(PRODUCT_PARAM.CATEGORY_NAME);
		Map<String, Object> parentCategory = (Map<String, Object>) context.get(PRODUCT_PARAM.PARENT_CATEGORY);
		Map<String, Object> selfAttr = (Map<String, Object>) context.get(PRODUCT_PARAM.SELF_ATTR);
		String status = (String) context.get(PRODUCT_PARAM.STATUS);
		
		Category category = new Category();
		
		category.setCategoryLevel(categoryLevel);
		
		if (1 == categoryLevel) {
			category.setpCategoryId((short) 0);
		} else if (1 < categoryLevel) {
			int parentLevel = categoryLevel - 1;
			String parentCategoryIdStr = (String) parentCategory.get(String.valueOf(parentLevel));
			category.setpCategoryId(Short.valueOf(parentCategoryIdStr));
		} else {
			throw new SYException(message.MPE02);
		}
		
		category.setCategoryName(categoryName);
		
		String parentCategoryIdStr = String.valueOf(category.getpCategoryId());
		
		short sortIndex = 0;
		
		if (PRODUCT_CACHE.CATEGORY_SORTINDEX.containsKey(parentCategoryIdStr)) {
			sortIndex = PRODUCT_CACHE.CATEGORY_SORTINDEX.get(String.valueOf(category.getpCategoryId()));
		}

		category.setSortIndex((short) (sortIndex + 1));
		category.setStatus(status);
		category.setCu(currentUserId);
		category.setCt(currentDate);
		category.setUu(currentUserId);
		category.setUt(currentDate);
		
		categoryMapper.insert(category);
		
		for (Iterator<String> it = selfAttr.keySet().iterator(); it.hasNext();) {
			String pAttrNameId = it.next();
			CategoryAttr ca = new CategoryAttr();
			ca.setCategoryId(category.getCategoryId());
			ca.setpAttrNameId(Short.valueOf(pAttrNameId));
			categoryAttrMapper.insert(ca);
		}
		
		
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		
		return result;
	}

	private Map<String, Short> getParentCategoryCollection(Category category) {

		Short pCategoryId = category.getpCategoryId();
		
		if (0 == pCategoryId) {
			return new LinkedHashMap<String, Short>();
		}
		
		Category pCategory = PRODUCT_CACHE.CATEGORY.get(String.valueOf(pCategoryId));
		Map<String, Short> parentResult = getParentCategoryCollection(pCategory);
		parentResult.put(String.valueOf(pCategory.getCategoryLevel()), pCategoryId);
		
		return parentResult;
	}
	
	private Map<String, Object> getCategoryAttr(short categoryId) {
		Map<String, Object> attrMap = new HashMap<String, Object>();
		
		attrMap.put(PRODUCT_PARAM.CATEGORY_ID, categoryId);

		StringBuffer sbKey = new StringBuffer()
				.append(String.valueOf(categoryId))
				.append(SYConst.SYMBOL.OCTOTHORPE);
		
		for (Iterator<String> it 
				= PRODUCT_CACHE.CATEGORY_ATTR.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			
			if (key.startsWith(sbKey.toString())) {
				CategoryAttr categoryAttr = PRODUCT_CACHE.CATEGORY_ATTR.get(key);
				attrMap.put(String.valueOf(categoryAttr.getpAttrNameId()), String.valueOf(categoryAttr.getpAttrNameId()));
			}
		}
		
		return attrMap;
	}

	@Override
	public Map<String, Object> getCategoryDetailInfo(short categoryId) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Category category = PRODUCT_CACHE.CATEGORY.get(String.valueOf(categoryId));
		
		Map<String, Short> parentCategory = getParentCategoryCollection(category);
		
		Map<String, Object> selfAttr = getCategoryAttr(categoryId);
		
		Map<String, Map<String, Object>> parentAttr = new HashMap<String, Map<String, Object>>();
		
		for (Iterator<Entry<String, Short>> it = parentCategory.entrySet().iterator(); it.hasNext();) {
			Entry<String, Short> entry = it.next();
			parentAttr.put(entry.getKey(), getCategoryAttr(entry.getValue()));
		}
		
		result.put(PRODUCT_PARAM.CATEGORY, category);
		result.put(PRODUCT_PARAM.PARENT_CATEGORY, parentCategory);
		result.put(PRODUCT_PARAM.SELF_ATTR, selfAttr);
		result.put(PRODUCT_PARAM.PARENT_ATTR, parentAttr);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		
		return result;
	}
	

	@Transactional
	@Override
	public Map<String, Object> updateCategory(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, Object> updateCategory = (Map<String, Object>) context
				.get(SYConst.PRODUCT_PARAM.UPDATE_CATEGORY);
		Map<String, Object> originalCategory = (Map<String, Object>) context
				.get(SYConst.PRODUCT_PARAM.ORIGINAL_CATEGORY);

		Category category = null;
		Map<String, Object> originalCategoryBean = (Map<String, Object>) originalCategory
				.get(SYConst.PRODUCT_PARAM.CATEGORY);

		if (updateCategory.containsKey(SYConst.PRODUCT_PARAM.CATEGORY_NAME)) {
			String categoryName = (String) updateCategory.get(SYConst.PRODUCT_PARAM.CATEGORY_NAME);

			String originalCategoryName = (String) originalCategoryBean.get(SYConst.PRODUCT_PARAM.CATEGORY_NAME);

			if (!categoryName.equals(originalCategoryName)) {
				if (null == category) {
					category = new Category();
				}

				category.setCategoryName(categoryName);
			}
		} else {
			if (null == category) {
				category = new Category();
			}
			String originalCategoryName = (String) originalCategoryBean.get(SYConst.PRODUCT_PARAM.CATEGORY_NAME);
			category.setCategoryName(originalCategoryName);
		}

		if (updateCategory.containsKey(SYConst.PRODUCT_PARAM.CATEGORY_LEVEL)) {
			Integer categoryLevel = (Integer) updateCategory.get(SYConst.PRODUCT_PARAM.CATEGORY_LEVEL);

			Integer originalCategoryLevel = (Integer) originalCategoryBean
					.get(SYConst.PRODUCT_PARAM.CATEGORY_LEVEL);

			if (originalCategoryLevel.intValue() != categoryLevel.intValue()) {
				if (null == category) {
					category = new Category();
				}

				category.setCategoryLevel(Integer.valueOf(categoryLevel));
			}
		} else {
			Integer originalCategoryLevel = (Integer) originalCategoryBean
					.get(SYConst.PRODUCT_PARAM.CATEGORY_LEVEL);
			category.setCategoryLevel(Integer.valueOf(originalCategoryLevel));
		}

		if (updateCategory.containsKey(SYConst.PRODUCT_PARAM.STATUS)) {
			String categoryStatus = (String) updateCategory.get(SYConst.PRODUCT_PARAM.STATUS);

			String originalCategoryStatus = (String) originalCategoryBean.get(SYConst.PRODUCT_PARAM.STATUS);

			if (!originalCategoryStatus.equals(categoryStatus)) {
				if (null == category) {
					category = new Category();
				}

				category.setStatus(categoryStatus);
			}
		} else {
			String originalCategoryStatus = (String) originalCategoryBean.get(SYConst.PRODUCT_PARAM.STATUS);
			category.setStatus(originalCategoryStatus);
		}

		if (updateCategory.containsKey(SYConst.PRODUCT_PARAM.PARENT_CATEGORY)) {
			Map<String, Object> parentCategory = (Map<String, Object>) updateCategory
					.get(SYConst.PRODUCT_PARAM.PARENT_CATEGORY);
			Map<String, Object> originalParentCategory = (Map<String, Object>) originalCategory
					.get(SYConst.PRODUCT_PARAM.PARENT_CATEGORY);
			if (!parentCategory.equals(originalParentCategory)) {
				if (null == category) {
					category = new Category();
				}

				Integer originalCategoryLevel = (Integer) updateCategory.get(SYConst.PRODUCT_PARAM.CATEGORY_LEVEL);

				Integer pCategoryId = (Integer) parentCategory.get(String.valueOf(originalCategoryLevel - 1));

				category.setpCategoryId(Integer.valueOf(pCategoryId).shortValue());
			} else {
				Integer pCategoryId = (Integer) originalCategoryBean.get(SYConst.PRODUCT_PARAM.P_CATEGORY_ID);
				category.setpCategoryId(Integer.valueOf(pCategoryId).shortValue());
			}
		} else {
			Integer pCategoryId = (Integer) originalCategoryBean.get(SYConst.PRODUCT_PARAM.P_CATEGORY_ID);
			category.setpCategoryId(Integer.valueOf(pCategoryId).shortValue());
		}
		Integer attrnameid = (Integer) originalCategoryBean.get(SYConst.PRODUCT_PARAM.CATEGORY_ID);
		category.setCategoryId(Integer.valueOf(attrnameid).shortValue());
		Integer sortIndex = (Integer) originalCategoryBean.get(SYConst.PRODUCT_PARAM.SORT_INDEX);
		Integer cu = (Integer) originalCategoryBean.get(SYConst.PRODUCT_PARAM.CU);
		Long ct = (Long) originalCategoryBean.get(SYConst.PRODUCT_PARAM.CT);
		category.setSortIndex(Integer.valueOf(sortIndex).shortValue());
        Date date = new Date(ct);
        //TODOuu变成登录的用户
		category.setCu(Integer.valueOf(cu).shortValue());
		category.setUu(Integer.valueOf(cu).shortValue());
		Date currentDate = new Date(System.currentTimeMillis());
		category.setCt(date);
		category.setUt(currentDate);
		int byPrimaryKey = categoryMapper.updateByPrimaryKey(category);

		if (0 == byPrimaryKey) {
			throw new SYException("更新出现问题！");
		} else if (1 < byPrimaryKey) {
			throw new SYException("商品分类层次出现问题！更新失败！");
		}

		if (updateCategory.containsKey(SYConst.PRODUCT_PARAM.SELF_ATTR)) {
			Map<String, Object> selfAttr = (Map<String, Object>) updateCategory
					.get(SYConst.PRODUCT_PARAM.SELF_ATTR);
			Map<String, Object> originalSelfAttr = (Map<String, Object>) originalCategory
					.get(SYConst.PRODUCT_PARAM.SELF_ATTR);

			if (!selfAttr.equals(originalSelfAttr)) {
				Example example = new Example(CategoryAttr.class);
				example.createCriteria().andEqualTo(SYConst.PRODUCT_PARAM.CATEGORY_ID, category.getCategoryId());
				categoryAttrMapper.deleteByExample(example);
				for (Iterator<String> it = selfAttr.keySet().iterator(); it.hasNext();) {
					String key = it.next();
					if (!key.equals("categoryId")) {
						String pAttrNameIdInt = (String) selfAttr.get(key);
						CategoryAttr ca = new CategoryAttr();
						ca.setCategoryId(category.getCategoryId());
						short sh = Short.parseShort(pAttrNameIdInt);
						ca.setpAttrNameId(sh);
						categoryAttrMapper.insert(ca);
					}
				}
			}
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}



	@Override
	public List<Map<String, Object>> getStatus(Map<String, Object> context) {
		Map<String, Emnu> statusMap = COMMON_CACHE.EMNU.get(SYConst.EMNU.STATUS);
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> first = new HashMap<String, Object>();
		first.put("type", "");
		first.put("name", "请选择状态");
		first.put("selected", true);
		result.add(first);
		
		for (Iterator<Entry<String, Emnu>> it = statusMap.entrySet().iterator(); it.hasNext();) {
			Entry<String, Emnu> entry = it.next();
			entry.getKey();
			entry.getValue();
			
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("type", entry.getValue().getEmnuName());
			item.put("name", entry.getValue().getEmnuValue());
			
			result.add(item);
		}
		
		return result;
	}


	@Override
	public List<Brand> getBrands(Map<String, Object> context) {
		List<Brand> listResult = new ArrayList<Brand>();
		
		for (Iterator<Entry<String, Brand>> it = PRODUCT_CACHE.BRAND.entrySet().iterator(); it.hasNext();) {
			Entry<String, Brand> entry = it.next();
			Brand brand = entry.getValue();
			listResult.add(brand);
		}
		
		return listResult;
	}

	@Transactional
	@Override
	public Map<String, Object> newProductInfo(Map<String, Object> context) {
		
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		Date currentDate = new Date(System.currentTimeMillis());
		
		Map<String, Object> productMap = (Map<String, Object>) context.get("product");
		List<Short> categoryList = (List<Short>) context.get("category");
		List<Map<String, Object>> attrList = (List<Map<String, Object>>) context.get("attr");
		Map<String, Object> imageMap = (Map<String, Object>) context.get("image");
		/* updated by YanqingLiu start for verification 2018-05-15 18:05:30 */
		/* 如果category或attr数据为空抛出异常  */
		if (StringUtils.isEmpty(categoryList) || StringUtils.isEmpty(attrList)) {
			throw new SYException("属性与分类不能为空！");
		}
		/* 判断添加数据为空时抛出异常 */
		if (null == productMap.get("productName")
				|| null == productMap.get("brandId")
				|| null == productMap.get("des")
				|| null == productMap.get("showPrice")
				|| null == productMap.get("status")) {
			throw new SYException("商品内容数据必填项不能有空！");
		}
		/* updated by YanqingLiu end for verification 2018-05-15 18:05:30 */
		Product product = new Product();
		product.setProductName((String) productMap.get("productName"));
		product.setBrandId((Short) productMap.get("brandId"));
		product.setDes((String) productMap.get("des"));
		product.setShowPrice((BigDecimal) productMap.get("showPrice"));
		product.setStatus((String) productMap.get("status"));
		product.setCu(currentUserId);
		product.setCt(currentDate);
		product.setUu(currentUserId);
		product.setUt(currentDate);
		/* updated by YanqingLiu start for build default value to salesVolume*/
		product.setSalesVolume(0);
		/* updated by YanqingLiu end for build default value to salesVolume*/
		productMapper.insertSelective(product);
		
		for (Short categoryId : categoryList) {
			ProductCategory cp = new ProductCategory();
			cp.setProductId(product.getProductId());
			cp.setCategoryId(categoryId);
			productCategoryMapper.insert(cp);
		}
		
		for (Map<String, Object> attrMap : attrList) {
			
			for (Iterator<String> it = attrMap.keySet().iterator(); it.hasNext();) {
				
				String key = it.next();
				
				if (!"pAttrNameId".equals(key) && !"isSku".equals(key)) {
					ProductAttr pa = new ProductAttr();
					pa.setProductId(product.getProductId());
					pa.setpAttrNameId((Short) attrMap.get("pAttrNameId"));
					pa.setIsSku((Boolean) attrMap.get("isSku"));
					pa.setpAttrValueId((Short) attrMap.get(key));
					
					productAttrMapper.insert(pa);
				}
			}
		}
		
		String fileCoverName = (String) imageMap.get("cover");
		/* updated by YanqingLiu start for verification 2018-05-15 18:05:30 */
		/* 当productCover图片不为空时， 将图片存入MAP */
		if (null == fileCoverName) {
			throw new SYException("商品封面图片缺失！");
		}
		/* updated by YanqingLiu end for verification 2018-05-15 18:05:30 */
		ProductImage piCover = new ProductImage();
		piCover.setProductId(product.getProductId());
		piCover.setImageType("COVER");
		piCover.setImageUrl("/front/assets/"+fileCoverName);
		piCover.setCu(currentUserId);
		piCover.setUu(currentUserId);
		piCover.setCt(currentDate);
		piCover.setUt(currentDate);
		productImageMapper.insert(piCover);
		
		List<String> detailFileList = (List<String>) imageMap.get("detail");
		/* updated by YanqingLiu start for verification 2018-05-15 18:05:30 */
		/* 当productDetail图片不为空时， 将图片存入MAP */
		if (StringUtils.isEmpty(detailFileList)) {
			throw new SYException("商品详细图片缺失！");
		}
		/* updated by YanqingLiu end for verification 2018-05-15 18:05:30 */
		for (String detailFileName : detailFileList) {
			ProductImage piDetail = new ProductImage();
			piDetail.setProductId(product.getProductId());
			piDetail.setImageType("DETAIL");
			piDetail.setImageUrl("/front/assets/"+detailFileName);
			piDetail.setCu(currentUserId);
			piDetail.setUu(currentUserId);
			piDetail.setCt(currentDate);
			piDetail.setUt(currentDate);
			productImageMapper.insert(piDetail);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		cacheService.categoryProductCache();
		cacheService.attrProductCache();
		cacheService.productCategoryCache();
		cacheService.productCache();
		cacheService.pAttrNameCache();
		cacheService.pAttrValueCache();
		return result;
	}
	
	/* update start by liuzhaoyu update select product 2018-05-11 18:15:30 */
	
	/**
	 * 取得商品数据的方法
	 */
	@Override
	public Map<String, Object> getProductData(Map<String, Object> context) {
		// 创建result map 对象
		Map<String, Object> result = new HashMap<String, Object>();
		
		Set<Short> products = new HashSet<Short>();
		boolean filterFlg = false;
		
		if (context.containsKey(SYConst.PRODUCT_PARAM.CATEGORY_ID)) {
			String categoryIdStr = (String) context.get(SYConst.PRODUCT_PARAM.CATEGORY_ID);
			
			if (!StringUtils.isEmpty(categoryIdStr)) {
				Set<Short> resultSet = PRODUCT_CACHE.CATEGORY_PRODUCT.get(categoryIdStr);
				filterFlg = true;
				if(resultSet==null) {
					products.clear();
				}else {
					products.addAll(resultSet);		
				}

			}
		}

		/* 当搜索对象中存在属性对象的时候 */
		if (context.containsKey("pAttrObject")) {
			
			// 取得属性对象
			Map<String, Object> resultMap = (Map<String, Object>) context.get("pAttrObject");
			
			/* 遍历属性对象集合 */
			for (Iterator<Entry<String, Object>> it = resultMap.entrySet().iterator(); it.hasNext();) {
				Entry<String, Object> entry = it.next();
				String pAttrNameIdStr = entry.getKey(); // 取得属性名ID
				String pAttrValueIdStr = (String) entry.getValue(); // 取得属性值ID
				
				/* 当前属性值选择的是全部的时候 */
				if (StringUtils.isEmpty(pAttrValueIdStr)) {
					// 从【属性名-商品ID集合】缓存中取得属性名所对应的商品ID集合
					Set<Short> resultSet = PRODUCT_CACHE.ATTR_NAME_PRODUCT.get(pAttrNameIdStr);
					
					if (null != resultSet && resultSet.size() > 0) {
						products.retainAll(resultSet);
					}
					
				/* 当前属性值选择的是具体的属性值得时候 */
				} else {
					// 从【属性值-商品ID集合】缓存中取得属性值所对应的商品ID集合
					Set<Short> resultSet = PRODUCT_CACHE.ATTR_VALUE_PRODUCT.get(pAttrValueIdStr);
					if (null == resultSet) {
						resultSet = Collections.EMPTY_SET;
					}
					products.retainAll(resultSet);
				}
			}
		} 

		List<Short> productList = new ArrayList<Short>();
		/* 当商品ID集合中没有值的情况下：两种情况：1 都出现； 2 都不出现； */
		if (products.size() == 0) {
			
			/* 当分类过滤条件存在的情况下：交集失败 ，因此 都不出现*/
			if (filterFlg) {
				products.add((short) 0);
				productList.addAll(products);
			/* 当分类过滤条件不存在的情况下： 放弃使用分类过滤， 因此 都出现 */
			} else {
				products = null;
				productList = null;
			}
		} else {
			productList.addAll(products);
		}
		
		context.put("products", products);
		
		List<Product> selectProductInfo = productMapper.selectProductInfo(context);
		List<VProductBrandCategoryEmnu> vProductList = new ArrayList<VProductBrandCategoryEmnu>();
		List<VProductBrandCategoryEmnu> FProductList = new ArrayList<VProductBrandCategoryEmnu>();
		for (Iterator<Product> it = selectProductInfo.iterator(); it.hasNext();) {
			Product product = it.next();
			VProductBrandCategoryEmnu vProduct = new VProductBrandCategoryEmnu();
			vProduct.setProductId(product.getProductId());
			vProduct.setProductName(product.getProductName());
			vProduct.setShowPrice(product.getShowPrice());
			vProduct.setBrandId(product.getBrandId());
			vProduct.setDes(product.getDes());
			vProduct.setStatus(product.getStatus());
			vProduct.setSalesVolume(product.getSalesVolume());
			vProduct.setCt(product.getCt());
			vProduct.setCu(product.getCu());
			vProduct.setUt(product.getUt());
			vProduct.setUu(product.getUu());
			
			Map<String, List<ProductImage>> imageMap = PRODUCT_CACHE.PRODUCT_IMAGE.get(String.valueOf(product.getProductId()));
			String imageUrl = imageMap.get(SYConst.PRODUCT_PARAM.COVER).get(0).getImageUrl();
			vProduct.setImgCover(imageUrl);
			
			
			Map<String, Emnu> map = COMMON_CACHE.EMNU.get(SYConst.EMNU.STATUS);
			String statusName = map.get(product.getStatus()).getEmnuValue();
			vProduct.setStatusName(statusName);
			if (PRODUCT_CACHE.BRAND.containsKey(String.valueOf(product.getBrandId()))) {
				String brandName = PRODUCT_CACHE.BRAND.get(String.valueOf(product.getBrandId())).getBrandName();
				vProduct.setBrandName(brandName);
			}
			if (PRODUCT_CACHE.PRODUCT_CATEGORY.containsKey(String.valueOf(product.getProductId()))) {
				List<Short> categoryList = PRODUCT_CACHE.PRODUCT_CATEGORY.get(String.valueOf(product.getProductId()));
				 Short max = Collections.max(categoryList);
				if (PRODUCT_CACHE.CATEGORY.containsKey(String.valueOf(max))) {
					String categoryName = PRODUCT_CACHE.CATEGORY.get(String.valueOf(max)).getCategoryName();
					vProduct.setCategoryName(categoryName);
				}      
			}
			System.out.println(vProduct.getStatus());
			if(vProduct.getStatus().equals(SYConst.PRODUCT_PARAM.ACTIVATE)) {
				FProductList.add(vProduct);
				
			}
			vProductList.add(vProduct);
		}
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, vProductList);
		// 将根据context查询的总条数total放入reult中
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, productMapper.selectCountProductInfo(context));
		// 将根据已激活查询的记录frecords放入reult中
		result.put(SYConst.PAGE_PARAM_NAME.FRECORDS, FProductList);
		// 将根据已激活查询的记录ftotal放入reult中
		result.put(SYConst.PAGE_PARAM_NAME.FTOTAL, FProductList.size());
		// 返回result
		return result;
	}
	/* update end by liuzhaoyu update select product 2018-05-11 18:15:30 */


	
	@Override
	public Map<String, Object> getFrontStorageInfo(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Storage> storageList = new ArrayList<Storage>();
		
		for (Iterator<Entry<String, Storage>> it = 
				PRODUCT_CACHE.STORAGE.entrySet().iterator(); it.hasNext();) {
			Entry<String, Storage> entry = it.next();
			if(entry.getValue().getStatus().equals(SYConst.PRODUCT_PARAM.ACTIVATE)) {
				storageList.add(entry.getValue());
			}
		}
		
		result.put("storageList", storageList);
		
		Map<String, List<ProductModel>> spMap = new HashMap<String, List<ProductModel>>();
		
		for (Iterator<Entry<String, StorageProducts>> it = 
				PRODUCT_CACHE.STORAGE_PRODUCTS.entrySet().iterator(); it.hasNext();) {
			Entry<String, StorageProducts> entry = it.next();
			String key = entry.getKey();
			String[] keyArrs = key.split(SYConst.SYMBOL.OCTOTHORPE);
			String storageIdStr = keyArrs[0];
			String productIdStr = keyArrs[1];
			
			Product product = PRODUCT_CACHE.PRODUCT.get(productIdStr);
			
			Map<String, List<ProductImage>> productImageMap = 
					PRODUCT_CACHE.PRODUCT_IMAGE.get(productIdStr);
			
			ProductModel pm = new ProductModel();
			pm.setProductId(product.getProductId());
			pm.setProductName(product.getProductName());
			pm.setCoverImageUrl(productImageMap.get("COVER").get(0).getImageUrl());
			pm.setShowPrice(product.getShowPrice().setScale(2, BigDecimal.ROUND_HALF_UP));
			
			if (spMap.containsKey(storageIdStr)) {
				List<ProductModel> list = spMap.get(storageIdStr);
				list.add(pm);
			} else {
				List<ProductModel> list = new ArrayList<ProductModel>();
				list.add(pm);
				spMap.put(storageIdStr, list);
			}
		}
		
		result.put("storageProductsMap", spMap);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}


	@Override
	public Map<String, Object> getSkuInfo(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		int total = skuMapper.selectSkuWithProductCount(context);
		List<VSku> skuList = skuMapper.selectSkuWithProduct(context);
		
		if (null != skuList && skuList.size() > 0) {
			for (VSku sku : skuList) {
				sku.setSkuCollectionShow(JsonUtil.fomartAttrNameValue(sku.getSkuCollection(), PRODUCT_CACHE));
			}
		}
		
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, skuList);
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, total);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}


	@Override
	public Map<String, Object> getAttrValueByProductId(Short productId) {
		if (null == productId) {
			throw new SYException(message.MPI04);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		Example example = new Example(ProductAttr.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.PRODUCT_PARAM.PRODUCT_ID, productId);
		criteria.andEqualTo(SYConst.PRODUCT_PARAM.IS_SKU, true);
		List<ProductAttr> productAttrList = productAttrMapper.selectByExample(example);
		
		result.put(SYConst.PRODUCT_PARAM.PRODUCT_ID, productId);
		
		Set<String> pAttrNameIdSet = new HashSet<String>();
		List<PAttrName> pAttrNameList = new ArrayList<PAttrName>();
		Map<String, List<PAttrValue>> pAttrValueMap = new HashMap<String, List<PAttrValue>>();
		
		for (Iterator<ProductAttr> it = productAttrList.iterator(); it.hasNext();) {
			ProductAttr productAttr = it.next();
			if (!pAttrNameIdSet.contains(String.valueOf(productAttr.getpAttrNameId()))) {
				pAttrNameList.add(PRODUCT_CACHE.P_ATTR_NAME.get(String.valueOf(productAttr.getpAttrNameId())));
				pAttrNameIdSet.add(String.valueOf(productAttr.getpAttrNameId()));
			}
			
			if (pAttrValueMap.containsKey(String.valueOf(productAttr.getpAttrNameId()))) {
				List<PAttrValue> valueList = pAttrValueMap.get(String.valueOf(productAttr.getpAttrNameId()));
				valueList.add(PRODUCT_CACHE.P_ATTR_VALUE.get(String.valueOf(productAttr.getpAttrValueId())));
			} else {
				List<PAttrValue> valueList = new ArrayList<PAttrValue>();
				valueList.add(PRODUCT_CACHE.P_ATTR_VALUE.get(String.valueOf(productAttr.getpAttrValueId())));
				pAttrValueMap.put(String.valueOf(productAttr.getpAttrNameId()), valueList);
			}
		}
		
		result.put("pAttrNameList", pAttrNameList);
		result.put("pAttrValueMap", pAttrValueMap);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}


	@Override
	public Map<String, Object> newSku(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		Sku sku = new Sku();
		
		String productIdStr = (String) context.get(SYConst.PRODUCT_PARAM.PRODUCT_ID);
		sku.setProductId(Short.valueOf(productIdStr));
		
		String priceStr = (String) context.get("price");
		sku.setPrice(new BigDecimal(priceStr));
		
		String stockStr = (String) context.get("stock");
		sku.setStock(Integer.valueOf(stockStr));
		
		Map<String, Object> skuCollection = (Map<String, Object>) context.get("skuCollection");
		sku.setSkuCollection(JSONObject.fromObject(skuCollection).toString());
		
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		sku.setCu(currentUserId);
		sku.setUu(currentUserId);
		
		Date currentDate = new Date(System.currentTimeMillis());
		
		sku.setCt(currentDate);
		sku.setUt(currentDate);
		
		sku.setSalesVolume(0);
		
		skuMapper.insert(sku);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}


	@Override
	public Map<String, Object> getProductDetailData(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String productIdStr = (String) context.get(SYConst.PRODUCT_PARAM.PRODUCT_ID);
		// TODO 从商品基础数据缓存中取得商品基础信息
		Product product = PRODUCT_CACHE.PRODUCT.get(productIdStr);
		
		// TODO 从商品图片缓存中取得商品图片信息
		Map<String, List<ProductImage>> imgMap = PRODUCT_CACHE.PRODUCT_IMAGE.get(productIdStr);
		List<ProductImage> imageList = imgMap.get(SYConst.PRODUCT_PARAM.DETAIL);
		
		// TODO 从数据库中取得SKU信息
		Example example = new Example(Sku.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.PRODUCT_PARAM.PRODUCT_ID, Short.valueOf(productIdStr));
		List<Sku> skuList = skuMapper.selectByExample(example);
		
		Map<String, Sku> skuMap = new HashMap<String, Sku>();
		
		for(Iterator<Sku> it = skuList.iterator(); it.hasNext();) {
			Sku sku = it.next();
			String collection = sku.getSkuCollection();
			skuMap.put(collection, sku);
		}
		
		int skuSum = skuMapper.selectSkuWithProductSum(context);
		
		Map<String, Object> attrMap = getAttrValueByProductId(Short.valueOf(productIdStr));

		// TODO 取得评价数量 TODO 刘笑楠 评价表
		int evaluationCount = evaluationMapper.selectEvaluationCountSql(context);
		List<Evaluation> evaluationList = evaluationMapper.selectEvaluationSql(context);
		for(Iterator<Evaluation> ite = evaluationList.iterator(); ite.hasNext();) {
			Evaluation e = ite.next();
			if(String.valueOf(e.getProductId()).equals(productIdStr)) {
				if(result.containsKey("evaluation")) {
					List<Evaluation> pEList = (List<Evaluation>) result.get("evaluation");
					pEList.add(e);
				} else {
					List<Evaluation> pEList = new ArrayList<Evaluation>();
					pEList.add(e);
					result.put("evaluation", pEList);
				}		
			}
		}
		// TODO 刘笑楠  根据当前商品分类 取得同级分类下的前三个商品  如果去不到  取得当前分类父级分类下的商品  如果去不到一直往上去
		Map<String, Object> hotProduct = new HashMap<String, Object>();
		List<Object> imgHotIdList = new ArrayList<Object>();
		for (Iterator<Entry<String, List<Short>>> it 
				= PRODUCT_CACHE.PRODUCT_CATEGORY.entrySet().iterator(); it.hasNext();) {
			Entry<String, List<Short>> entry = it.next();			
			if(entry.getKey().equals(productIdStr)) {
				List<Short> categoryIdList = entry.getValue();
				context.put("categoryIdList", categoryIdList);
				List<Short> productIdList = getProductIdList(context);
				for(Iterator<Short> itPid = productIdList.iterator(); itPid.hasNext();) {
					Short pid = itPid.next();
					for(Iterator<Entry<String, Product>> itp 
							= PRODUCT_CACHE.PRODUCT.entrySet().iterator(); itp.hasNext();) {
						Entry<String, Product> entryp = itp.next();
						Product p = entryp.getValue();
						if(pid == p.getProductId()) {
							Map<String, List<ProductImage>> imgHot = PRODUCT_CACHE.PRODUCT_IMAGE.get(String.valueOf(pid));
							List<ProductImage> imageHotList = imgHot.get(SYConst.PRODUCT_PARAM.COVER);
							imgHotIdList.add(imageHotList.get(0));
							hotProduct.put(String.valueOf(pid), p);
							result.put("imageHotList", imgHotIdList);
						}
					}
				}
			}
		}
		
		// TODO magic code 孙志明
		result.put("product", product);
		result.put("imageList", imageList);
		result.put("skuMap", skuMap);
		result.put("pAttrNameList", attrMap.get("pAttrNameList"));
		result.put("pAttrValueMap", attrMap.get("pAttrValueMap"));
		result.put("evaluationCount", String.valueOf(evaluationCount));
		result.put("hotProduct", hotProduct);
		result.put("skuSum", skuSum);

		return result;
	}
	
	private List<Short> getProductIdList(Map<String, Object> context) {
		List<Short> categoryIdList = (List<Short>)context.get("categoryIdList");
		
		String productIdStr = (String) context.get(SYConst.PRODUCT_PARAM.PRODUCT_ID);
		
		Short categoryId = Collections.max(categoryIdList);
		
		List<Short> pIdList = new ArrayList<Short>();
		
		List<Category> categoryList = categoryMapper.selectAll();
		
		List<Short> productIdList = new ArrayList<Short>();
		
		for(Iterator<Entry<String, Set<Short>>> it 
				= PRODUCT_CACHE.CATEGORY_PRODUCT.entrySet().iterator(); it.hasNext();) {
			Entry<String, Set<Short>> entry = it.next();
			if(entry.getKey().equals(String.valueOf(categoryId))) {
				Set<Short> productIdSet = entry.getValue();
				for(Iterator<Category> itc = categoryList.iterator(); itc.hasNext();) {
					Category c = itc.next();
					if(c.getpCategoryId() == 0) { 
						for(Iterator<Short> itpid = productIdSet.iterator(); itpid.hasNext();) {
							Short pid = itpid.next();
							if(String.valueOf(pid).equals(productIdStr)) {
								productIdList.add(pid);
							}							
						}
						if(productIdList.size() >= 3) {
							pIdList = productIdList.subList(0, 3);	
						} else {
							pIdList = productIdList;
						}
						return pIdList;
					} else {
						for(Iterator<Short> itpid = productIdSet.iterator(); itpid.hasNext();) {
							Short pid = itpid.next();
							if(String.valueOf(pid).equals(productIdStr)) {
								productIdList.add(pid);
							}
						}
						if(productIdList.size() >= 3) {
							pIdList = productIdList.subList(0, 3);	
							return pIdList;
						} else {
							List<Short> pCategoryIdList = new ArrayList<Short>();
							if(c.getCategoryId() == categoryId) {
								
								pCategoryIdList.add(c.getpCategoryId());
							}
							if(pCategoryIdList == null || pCategoryIdList.size() == 0) {
								pCategoryIdList.add(c.getCategoryId());
							}
							categoryIdList = pCategoryIdList;
							context.put("categoryIdList", categoryIdList);
							
						}	
					}
				}
			}	
		}
		pIdList = getProductIdList(context);
		return pIdList;
	}
	
	@Override
	public Map<String, Object> getAdImage(Map<String, Object> context){
		Map<String, Object> result = new HashMap<String, Object>();
		
		for(Iterator<Entry<String, AdvertisementImage>> it = 
				PRODUCT_CACHE.ADVERTISEMENT_IMAGE.entrySet().iterator();it.hasNext();) {
			Entry<String, AdvertisementImage> entry = it.next();
			AdvertisementImage ad = entry.getValue();
			result.put("productId", ad);
		}
		
		return result;
	}


	@Override
	public synchronized Map<String, Object> changeProductStatus(Map<String, Object> context) {
		Date currentDate = new Date(System.currentTimeMillis());
		Product record = new Product();
		record.setProductId(Short.valueOf((String)context.get(SYConst.PRODUCT_PARAM.PRODUCT_ID)));
		record.setUu((Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID));
		record.setUt(currentDate);
		if (SYConst.EMNU.ACTIVATE.equals((String)context.get(SYConst.PRODUCT_PARAM.STATUS))) {
			record.setStatus(SYConst.EMNU.INACTIVATE);
		}else {
			record.setStatus(SYConst.EMNU.ACTIVATE);
		}
		productMapper.updateByPrimaryKeySelective(record);
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}
	
	@Override
	public synchronized Map<String, Object> changeStorageStatus(Map<String, Object> context) {
		Date currentDate = new Date(System.currentTimeMillis());
		Storage record = new Storage();
		record.setStorageId(Short.valueOf((String)context.get(SYConst.PRODUCT_PARAM.STORAGE_ID)));
		record.setUu((Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID));
		record.setUt(currentDate);
		if (SYConst.EMNU.ACTIVATE.equals((String)context.get(SYConst.PRODUCT_PARAM.STATUS))) {
			record.setStatus(SYConst.EMNU.INACTIVATE);
		}else {
			record.setStatus(SYConst.EMNU.ACTIVATE);
		}
		storageMapper.updateByPrimaryKeySelective(record);
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> getStorageAll(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(3);
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, storageMapper.selectCountStorageWithName(context));
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, storageMapper.selectStorageWithName(context));
		return result;
	}


	@Override
	public Map<String, Object> getAttrInfo(String pAttrNameId) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		PAttrName pAttrName = new PAttrName();
		pAttrName.setpAttrNameId(Short.valueOf(pAttrNameId));
		PAttrName selectOne = pAttrNameMapper.selectOne(pAttrName);
		result.put(SYConst.PRODUCT_PARAM.P_ATTR_NAME, selectOne.getpAttrName());
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}


	@Override
	public Map<String, Object> updateAttrName(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		PAttrName pAttrName = new PAttrName();
		pAttrName.setpAttrNameId(Short.valueOf((String)context.get(SYConst.PRODUCT_PARAM.P_ATTR_NAME_ID)));
		pAttrName.setpAttrName((String)context.get(SYConst.PRODUCT_PARAM.P_ATTR_NAME));
		pAttrNameMapper.updateByPrimaryKeySelective(pAttrName);
		cacheService.pAttrNameCache();  // 更新属性名缓存
		cacheService.pAttrValueCache(); // 更新属性值缓存
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}


	@Override
	public Map<String, Object> deleteAttrValue(String pAttrValueId) {
		Map<String, Object> result = new HashMap<String, Object>();
		ProductAttr pAttr = new ProductAttr();
		pAttr.setpAttrValueId(Short.valueOf(pAttrValueId));
		/*从productattr中，看属性值是否挂在商品上*/
		int count = productAttrMapper.selectCount(pAttr);
		if (0 == count) {
			PAttrValue pAttrValue = new PAttrValue();
			pAttrValue.setpAttrValueId(Short.valueOf(pAttrValueId));
			result.put(SYConst.PRODUCT_PARAM.P_ATTR_NAME_ID, pAttrValueMapper.selectOne(pAttrValue).getpAttrNameId());
			pAttrValueMapper.deleteByPrimaryKey(Short.valueOf(pAttrValueId));
			result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
			cacheService.pAttrNameCache();  // 更新属性名缓存
			cacheService.pAttrValueCache(); // 更新属性值缓存
			return result;
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
		return result;
	}


	@Override
	public List<PAttrName> getAttrName(Map<String, Object> context) {
		/* 获取属性名ID  */
		Short pAttrNameId = (Short)context.get(SYConst.PRODUCT_PARAM.P_ATTR_NAME_ID);
		/* 构建EXAMPLE并放入属性值类  */
		Example example = new Example(PAttrValue.class);
		/* 设立条件为属性名ID */
		example.createCriteria().andEqualTo(SYConst.PRODUCT_PARAM.P_ATTR_NAME_ID, pAttrNameId);
		/* 根据条件查询属性名  */
		return pAttrNameMapper.selectByExample(example);
	}
	
	@Override
	public Map<String, Object> getBrandAllByList(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		Example example = new Example(Brand.class);
		example.createCriteria().equals(context.get(SYConst.PRODUCT_PARAM.BRAND_NAME));
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, brandMapper.selectCountByExample(example));
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, brandMapper.selectBrandWithCuName(context));
		return result;
	}
	
	@Override
	public Map<String, Object> newBrand(Map<String, Object> context) {
		 Brand bd = new Brand();
//			取得当前毫秒数
		    Date currentDate = new Date(System.currentTimeMillis());
		    
		   
		    
		    bd.setBrandName((String)context.get(SYConst.PRODUCT_PARAM.BRAND_NAME));
		    bd.setDes((String)context.get(SYConst.PRODUCT_PARAM.DESCRIPTION));		    
//		    设置key
		    bd.setCu((Short)context.get(SYConst.USER_PARAM_NAME.CURRENT_USER));
		    bd.setUu((Short)context.get(SYConst.USER_PARAM_NAME.CURRENT_USER));
		    bd.setCt(currentDate);
		    bd.setUt(currentDate);
//		    把数据inse进数据库中
		    brandMapper.insert(bd);
//		    声明对象result
		    Map<String, Object> result = new HashMap<String, Object>( );
		    result.put(SYConst.SERVICE.STATUS, "SUCCESS");
//		    把permission的idput进reuslt，页面可以通过reuslt来知道更新后的id，和状态
		    result.put(SYConst.PRODUCT_PARAM.BRAND_ID,bd.getBrandId());
		    return result;
	}
	
	@Override
	public Map<String, Object> updateBrand(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		//取得更新后的对象
		Map<String, Object> updateBrandMap =  (Map<String, Object>) context.get("update_brand");
		//
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		Date currentDate = new Date(System.currentTimeMillis());
//		此时表明根本没有变化
		if (updateBrandMap.size() == 0) {
			result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
			result.put(SYConst.SERVICE.MESSAGE_BODY, message.MUI01);
			return result;
		}
		
		Map<String, Object> originalBrandMap =  (Map<String, Object>) context.get("originalBrand");
		Short brandId = ((Integer) originalBrandMap.get("brandId")).shortValue();
		Brand brand = null;
		
		if (updateBrandMap.containsKey(SYConst.PRODUCT_PARAM.BRAND_NAME)) {
			String newUpdateName = (String) updateBrandMap.get(SYConst.PRODUCT_PARAM.BRAND_NAME);
			
			String oldUpdateName = (String) originalBrandMap.get(SYConst.PRODUCT_PARAM.BRAND_NAME);
//			改过的name不等于新的name则表示改过
			if (!newUpdateName.equals(oldUpdateName)) {
				brand = new Brand();
				brand.setBrandId(brandId);    
				brand.setBrandName(newUpdateName);  
			}

		}
//		校验描述是否更改
		if (updateBrandMap.containsKey("brandDes")) {
			//从键取出值
			String newBrandDes = (String) updateBrandMap.get("brandDes");
			String oldBrandDes = (String) originalBrandMap.get("brandDes");
			if (!newBrandDes.equals(oldBrandDes)) {
				if (null == brand) {
					brand = new Brand();
					brand.setBrandId(brandId);         
				}
				brand.setDes(newBrandDes);  
			}
		}
		
		if (null != brand) {
			brand.setUu(currentUserId);
			brand.setUt(currentDate);
			brandMapper.updateByPrimaryKeySelective(brand);
		}
		//如果做出了修改，直接对修改的那一段进行删除，直接在这基础上进行新增
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> deleteBrand(String brandId) {
		HashMap<String, Object> result = new HashMap<String, Object>(2);
		Product product = new Product();
		product.setBrandId(Short.valueOf(brandId));
		int count = productMapper.selectCount(product);
		if (count != 0) {
			throw new SYException(message.MU12);
		}
		brandMapper.deleteByPrimaryKey(Short.valueOf(brandId));
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}


	@Override
	public Map<String, Object> deleteAttrName(String pAttrNameId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Example exmaple = new Example (PAttrValue.class);
		Criteria criteria = exmaple.createCriteria();
		criteria.andEqualTo(SYConst.PRODUCT_PARAM.P_ATTR_NAME_ID,pAttrNameId);
		int count1 = categoryAttrMapper.selectCountByExample(exmaple);
		if (count1!=0) {
			result.put(SYConst.SERVICE.STATUS, "FALL");
			return result;
		}
		/*判断实现值下是否有属性名*/
		int count = pAttrValueMapper.selectCountByExample(exmaple);
		System.out.print("数量为"+count+"删除的id为"+pAttrNameId);
		if (0 == count) {
			PAttrValue pAttrValue = new PAttrValue();
			pAttrValue.setpAttrValueId(Short.valueOf(pAttrNameId));
			pAttrNameMapper.deleteByPrimaryKey(pAttrNameId);
			cacheService.pAttrNameCache();  // 更新属性名缓存
			cacheService.pAttrValueCache(); // 更新属性值缓存
			result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
			return result;
		}
		result.put(SYConst.SERVICE.STATUS, "FALL");
		return result;
	}
	

	/* start by liuyanqing add new service Method for storage and storage product 2018-06-04 12:00:00 */
	
	@Override
	public synchronized Map<String, Object> deleteStorage(String storageId) {
		HashMap<String, Object> result = new HashMap<String, Object>(2);
		storageMapper.deleteByPrimaryKey(Short.valueOf(storageId));
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}
	
	@Override
	public Map<String, Object> getStorageInfoAll(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Storage> storageList = storageMapper.selectAll();
		Map<Short, Integer> storageSortIndexList = new HashMap<Short, Integer>();
		List<Short> storageInfoList = new ArrayList<Short>();
		Map<String, Storage> storageInfoMapper = new HashMap<String, Storage>();
		for (Iterator<Storage> it = storageList.iterator(); it.hasNext();) {
			Storage pan = it.next();
			storageInfoList.add(pan.getStorageId());
			storageSortIndexList.put(pan.getStorageId(), pan.getSortIndex());
			storageInfoMapper.put(String.valueOf(pan.getStorageId()), pan);
		}
		
		List<StorageProducts> storageProductsList = storageProductsMapper.selectAll();
		int count = storageProductsMapper.selectCount(null);
		Map<String, List<StorageProducts>> storageProductsMapper = new HashMap<String, List<StorageProducts>>();
		
		for (Iterator<StorageProducts> it = storageProductsList.iterator(); it.hasNext();) {
			StorageProducts pav = it.next();
			String pStorageId = String.valueOf(pav.getStorageId());
			
			/* 当storageProductsMapper中存在指定的属性名ID时 */
			if (storageProductsMapper.containsKey(pStorageId)) {
				List<StorageProducts> sProductsList = storageProductsMapper.get(pStorageId);
				sProductsList.add(pav);
			/* 当storageProductsMapper中不存在指定的属性名ID时 */
			} else {
				List<StorageProducts> sProductsList = new ArrayList<StorageProducts>();
				sProductsList.add(pav);
				storageProductsMapper.put(pStorageId, sProductsList);
			}
		}

		result.put(PRODUCT_PARAM.SORT_INDEX_LIST, storageSortIndexList);
		result.put(PRODUCT_PARAM.STORAGE, storageInfoList);
		result.put(PRODUCT_PARAM.STORAGE_LIST, storageInfoMapper);
		result.put(PRODUCT_PARAM.STORAGE_PRODUCT, count);
		result.put(PRODUCT_PARAM.STORAGE_PRODUCTS_MAP, storageProductsMapper);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		
		return result;
	}

	@Transactional
	@Override
	public synchronized Map<String, Object> newStorageWithProduct(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		String storageName  = (String)context.get(SYConst.PRODUCT_PARAM.STORAGE_NAME);
		String storageDes = (String)context.get(SYConst.PRODUCT_PARAM.STORAGE_DES);
		Date currentDate = new Date(System.currentTimeMillis());
		Short cuId = (Short)context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID);
		int count = storageMapper.selectCount(null)+1;
		Storage storage = new Storage();
		storage.setStorageName(storageName);
		storage.setStorageDes(storageDes);
		storage.setUu(cuId);
		storage.setCu(cuId);
		storage.setStatus(SYConst.EMNU.INACTIVATE);
		storage.setSortIndex(count);
		storage.setCt(currentDate);
		storage.setUt(currentDate);
		storageMapper.insert(storage);
		
		List<String> storageProductsList = (List<String>) context.get(SYConst.PRODUCT_PARAM.STORAGE_PRODUCTS_MAP);
		Iterator<String> it = storageProductsList.iterator();
		int fist = 0;
		while (it.hasNext()) {
			String productId = it.next();
			StorageProducts storageProducts = new StorageProducts();
			storageProducts.setProductId(Short.valueOf(productId));
			storageProducts.setCu(cuId);
			storageProducts.setUu(cuId);
			storageProducts.setCt(currentDate);
			storageProducts.setUt(currentDate);
			storageProducts.setStorageId(storage.getStorageId());
			fist++;
			storageProducts.setSortIndex(fist);
			storageProductsMapper.insert(storageProducts);
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Transactional
	@Override
	public synchronized Map<String, Object> newStorageProduct(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		Date currentDate = new Date(System.currentTimeMillis());
		Short cuId = (Short)context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID);
		Short storageId = Short.valueOf((String)context.get(SYConst.PRODUCT_PARAM.STORAGE_ID));
		List<String> storageProductsList = (List<String>) context.get(SYConst.PRODUCT_PARAM.PRODUCT);
		Iterator<String> it = storageProductsList.iterator();
		int fist = storageProductsMapper.selectSortIndxeInfoByStorageId((String)context.get(SYConst.PRODUCT_PARAM.STORAGE_ID));
		while (it.hasNext()) {
			String productId = it.next();
			StorageProducts storageProducts = new StorageProducts();
			storageProducts.setProductId(Short.valueOf(productId));
			storageProducts.setCu(cuId);
			storageProducts.setUu(cuId);
			storageProducts.setCt(currentDate);
			storageProducts.setUt(currentDate);
			storageProducts.setStorageId(Short.valueOf(storageId));
			fist++;
			storageProducts.setSortIndex(fist);
			storageProductsMapper.insert(storageProducts);
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}
	
	@Override
	public synchronized Map<String, Object> updateStorage(Map<String, Object> context) {
		Storage storage = new Storage();
		storage.setStorageId(Short.valueOf((String)context.get(SYConst.PRODUCT_PARAM.STORAGE_ID)));
		storage.setStorageName((String)context.get(SYConst.PRODUCT_PARAM.STORAGE_NAME));
		storage.setStorageDes((String)context.get(SYConst.PRODUCT_PARAM.STORAGE_DES));
		Date currentDate = new Date(System.currentTimeMillis());
		storage.setUu((Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID));
		storage.setUt(currentDate);
		storageMapper.updateByPrimaryKeySelective(storage);
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public List<VStorageProdcutDetail> getStorageProdcutInfoByStorageId(String storageId) {
		return storageProductsMapper.selectStorageProdcutInfoByStorageId(storageId);
	}

	@Override
	public List<VStorageProdcutDetail> getStorageProdcutInfoExceptId(Map<String, Object> context) {
		return storageProductsMapper.selectStorageProdcutInfoExceptId(context);
	}

	@Override
	public synchronized Map<String, Object> deleteStorageProduct(String storageId, String productId) {
		HashMap<String, Object> result = new HashMap<String, Object>(2);
		StorageProducts storageProducts = new StorageProducts();
		storageProducts.setStorageId(Short.valueOf(storageId));
		storageProducts.setProductId(Short.valueOf(productId));
		storageProductsMapper.delete(storageProducts);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Transactional
	@Override
	public synchronized Map<String, Object> changeStorageSortIndex(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(1);
		int sortIndex =  Integer.parseInt((String)context.get(SYConst.PRODUCT_PARAM.SORT_INDEX));
		Short storageId = Short.valueOf((String) context.get(SYConst.PRODUCT_PARAM.STORAGE_ID));
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID);
		String option = (String)context.get(SYConst.PRODUCT_PARAM.OPTION);
		Date currentDate = new Date(System.currentTimeMillis());
		List<Storage> storageList = storageMapper.selectAll();
		int index = 0;
		int max = 0;
		for (int i = 0; i < storageList.size(); i++) {
			if (storageId.equals(storageList.get(i).getStorageId())) {
				index = i;
			} else if (sortIndex < storageList.get(i).getSortIndex()){
				max++;
			}
		}
		if (SYConst.PRODUCT_PARAM.OPTION_UP.equals(option)) {
			if (index == 0) {
				throw new SYException(message.MP02);
			}
			int targetSortIndex = storageList.get(index-1).getSortIndex();
			Storage currentStorage = new Storage();
			currentStorage.setStorageId(storageId);
			currentStorage.setSortIndex(targetSortIndex);
			currentStorage.setUt(currentDate);
			currentStorage.setUu(currentUserId);
			storageMapper.updateByPrimaryKeySelective(currentStorage);
			Storage orginalStorage = new Storage();
			orginalStorage.setStorageId(storageList.get(index-1).getStorageId());
			orginalStorage.setSortIndex(sortIndex);
			orginalStorage.setUt(currentDate);
			orginalStorage.setUu(currentUserId);
			storageMapper.updateByPrimaryKeySelective(orginalStorage);
		}
		if (SYConst.PRODUCT_PARAM.OPTION_DOWN.equals(option)) {
			if (max == 0) {
				throw new SYException(message.MP03);
			}
			int targetSortIndex = storageList.get(index+1).getSortIndex();
			Storage currentStorage = new Storage();
			currentStorage.setStorageId(storageId);
			currentStorage.setSortIndex(targetSortIndex);
			currentStorage.setUt(currentDate);
			currentStorage.setUu(currentUserId);
			storageMapper.updateByPrimaryKeySelective(currentStorage);
			Storage orginalStorage = new Storage();
			orginalStorage.setStorageId(storageList.get(index+1).getStorageId());
			orginalStorage.setSortIndex(sortIndex);
			orginalStorage.setUt(currentDate);
			orginalStorage.setUu(currentUserId);
			storageMapper.updateByPrimaryKeySelective(orginalStorage);
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}


	@Override
	public Map<String, Object> changeStorageProdcutSortIndex(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(1);
		int sortIndex =  Integer.parseInt((String)context.get(SYConst.PRODUCT_PARAM.SORT_INDEX));
		String storageId = (String) context.get(SYConst.PRODUCT_PARAM.STORAGE_ID);
		Short productId = Short.valueOf((String) context.get(SYConst.PRODUCT_PARAM.PRODUCT_ID));
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID);
		String option = (String)context.get(SYConst.PRODUCT_PARAM.OPTION);
		Date currentDate = new Date(System.currentTimeMillis());
		List<VStorageProdcutDetail> storageProdcutList = storageProductsMapper.selectStorageProdcutInfoByStorageId(storageId);
		int index = 0;
		int max = 0;
		for (int i = 0; i < storageProdcutList.size(); i++) {
			if (productId.equals(storageProdcutList.get(i).getProductId())) {
				index = i;
			} else if (sortIndex < storageProdcutList.get(i).getSortIndex()){
				max++;
			}
		}
		if (SYConst.PRODUCT_PARAM.OPTION_UP.equals(option)) {
			if (index == 0) {
				throw new SYException(message.MP02);
			}
			int targetSortIndex = storageProdcutList.get(index-1).getSortIndex();
			StorageProducts currentStorageProdcut = new StorageProducts();
			currentStorageProdcut.setStorageId(Short.valueOf(storageId));
			currentStorageProdcut.setSortIndex(targetSortIndex);
			currentStorageProdcut.setProductId(productId);
			currentStorageProdcut.setUt(currentDate);
			currentStorageProdcut.setUu(currentUserId);
			storageProductsMapper.updateByPrimaryKeySelective(currentStorageProdcut);
			StorageProducts orginalStorageProdcut = new StorageProducts();
			orginalStorageProdcut.setStorageId(Short.valueOf(storageId));
			orginalStorageProdcut.setProductId(storageProdcutList.get(index-1).getProductId());
			orginalStorageProdcut.setSortIndex(sortIndex);
			orginalStorageProdcut.setUt(currentDate);
			orginalStorageProdcut.setUu(currentUserId);
			storageProductsMapper.updateByPrimaryKeySelective(orginalStorageProdcut);
		}
		if (SYConst.PRODUCT_PARAM.OPTION_DOWN.equals(option)) {
			if (max == 0) {
				throw new SYException(message.MP03);
			}
			int targetSortIndex = storageProdcutList.get(index+1).getSortIndex();
			StorageProducts currentStorageProdcut = new StorageProducts();
			currentStorageProdcut.setStorageId(Short.valueOf(storageId));
			currentStorageProdcut.setSortIndex(targetSortIndex);
			currentStorageProdcut.setProductId(productId);
			currentStorageProdcut.setUt(currentDate);
			currentStorageProdcut.setUu(currentUserId);
			storageProductsMapper.updateByPrimaryKeySelective(currentStorageProdcut);
			StorageProducts orginalStorageProdcut = new StorageProducts();
			orginalStorageProdcut.setStorageId(Short.valueOf(storageId));
			orginalStorageProdcut.setProductId(storageProdcutList.get(index+1).getProductId());
			orginalStorageProdcut.setSortIndex(sortIndex);
			orginalStorageProdcut.setUt(currentDate);
			orginalStorageProdcut.setUu(currentUserId);
			storageProductsMapper.updateByPrimaryKeySelective(orginalStorageProdcut);
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	/* end by liuyanqing add new service Method for storage and storage product 2018-06-04 12:00:00 */
	
	/* add start by huangjun add new service Interface 2018-06-04 09:57:30 */
	/**
	 * 删除商品库存信息
	 */
	@Override
	public Map<String, Object> deleteSku(short skuId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Sku	stock  = skuMapper.selectByPrimaryKey(skuId);
		
		stock.getStock();
	    if (stock.getStock() > 0) {
			throw new SYException(message.MSI02);
		}
	    else{
			skuMapper.deleteByPrimaryKey(skuId);
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;	
	}	   
	/* add end by huangjun add new service Interface 2018-06-04 09:57:30 */
	
	/* add start by huangjun add new service Interface 2018-06-04 09:59:30 */
	/**
	 * 更新商品分类状态
	 */
	@Override
	public Map<String, Object> changeCategoryStatus(Map<String, Object> context) {
		Date currentDate = new Date(System.currentTimeMillis());
		Category record = new Category();
		record.setCategoryId(Short.valueOf((String)context.get(SYConst.PRODUCT_PARAM.CATEGORY_ID)));
		record.setUu((Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID));
		record.setUt(currentDate);
		if (SYConst.EMNU.ACTIVATE.equals((String)context.get(SYConst.PRODUCT_PARAM.STATUS))) {
			record.setStatus(SYConst.EMNU.INACTIVATE);
		}else {
			record.setStatus(SYConst.EMNU.ACTIVATE);
		}
		categoryMapper.updateByPrimaryKeySelective(record);
		cacheService.categoryCache();
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);

		return result;
	}
	/* add end by huangjun add new service Interface 2018-06-04 09:59:30 */
	
	/* add start by huangjun add new service Interface 2018-06-04 09:59:30 */
	/**
	 * 选择商品页面 商品查询 带出商品属性
	 * @param productId
	 * @return
	 */
	@Override
	public Map<String, Object> getProductAttrNameValueInfo(short productId) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Object> pAttrNameList = new ArrayList<Object>();
		Map<String, Object> pAttrNameValue = new HashMap<String, Object>();
		List<PAttrName> pAttrName = pAttrNameMapper.selectAll(); 
		List<PAttrValue> pAttrValue = pAttrValueMapper.selectAll(); 
		
		Example example = new Example(ProductAttr.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(PRODUCT_PARAM.PRODUCT_ID, productId);
		criteria.andEqualTo(PRODUCT_PARAM.IS_SKU, true);
		List<ProductAttr> productAttrList = productAttrMapper.selectByExample(example);
		
		for(Iterator<ProductAttr> itNameId = productAttrList.iterator(); itNameId.hasNext();) {
			ProductAttr pa = itNameId.next();
			for(Iterator<PAttrName> itName = pAttrName.iterator(); itName.hasNext();) {
				PAttrName pn = itName.next();
				if(pa.getpAttrNameId() == pn.getpAttrNameId()) {
					Map<String, Object> pAttrNameMap = new HashMap<String, Object>();
					pAttrNameMap.put(PRODUCT_PARAM.P_ATTR_NAME_ID, pa.getpAttrNameId());
					pAttrNameMap.put(PRODUCT_PARAM.P_ATTR_NAME, pn.getpAttrName());
					pAttrNameList.add(pAttrNameMap);
				}
			}
			
			List<Object> pAttrValueList = new ArrayList<Object>();
			for(Iterator<PAttrValue> itValue = pAttrValue.iterator(); itValue.hasNext();) {
				PAttrValue pv = itValue.next();
				if(pa.getpAttrNameId() == pv.getpAttrNameId()) {
					Map<String, Object> pAttrValueMap = new HashMap<String, Object>();
					pAttrValueMap.put(PRODUCT_PARAM.P_ATTR_VALUE_ID, pv.getpAttrValueId());
					pAttrValueMap.put(PRODUCT_PARAM.P_ATTR_VALUE, pv.getpAttrValue());
					pAttrValueList.add(pAttrValueMap);
					pAttrNameValue.put(String.valueOf(pv.getpAttrNameId()), pAttrValueList);
				}
			}
		}
		result.put("pAttrNameList", pAttrNameList);
		result.put("productId", productId);
		result.put("pAttrValueMap", pAttrNameValue);
		
		return result;
	}
	/* add end by huangjun add new service Interface 2018-06-04 09:59:30 */
	
	/* add start by huangjun add new service Interface 2018-06-04 09:58:30 */
	/**
	 * 选择商品页面 条件查询
	 */
	@Override
	public List<Product> getProductNames(Map<String, Object> context) {	
		List<Product> selectProductInfo = productMapper.selectProductInfo(context);		
		return selectProductInfo;
	}
	/* add end by huangjun add new service Interface 2018-06-04 09:58:30 */
	@Override
	public Map<String, Object> updateAttrValue(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		PAttrValue pAttrValue = new PAttrValue();
		pAttrValue.setpAttrValueId(Short.valueOf((String)context.get(SYConst.PRODUCT_PARAM.P_ATTR_VALUE_ID)));
		pAttrValue.setpAttrValue((String)context.get(SYConst.PRODUCT_PARAM.P_ATTR_VALUE));
		int o =pAttrValueMapper.updateByPrimaryKeySelective(pAttrValue);
		cacheService.pAttrNameCache();  // 更新属性名缓存
		cacheService.pAttrValueCache(); // 更新属性值缓存
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}     

	@Override
	public Map<String, Object> getAttrValueInfo(String pAttrValueId) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		PAttrValue pAttrValue = new PAttrValue();
		pAttrValue.setpAttrValueId(Short.valueOf(pAttrValueId));
		PAttrValue selectOne = pAttrValueMapper.selectOne(pAttrValue);
		result.put(SYConst.PRODUCT_PARAM.P_ATTR_NAME_VALUE, selectOne.getpAttrValue());
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
		
	}
	/**
	 * 更改分类排序
	 */
	@Override
	public Map<String, Object> updateCategorySortIndex(Map<String, Object> context) {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> currentCategory = (Map<String,Object>) context.get(PRODUCT_PARAM.CURRENT_CATEGORY);
		Map<String,Object> targetCategory = (Map<String,Object>) context.get(PRODUCT_PARAM.TARGET_CATEGORY)	;
		Category ca = new Category();
		Category c = new Category();
		Date currentDate = new Date(System.currentTimeMillis());
		Short sortindex = Short.valueOf(String.valueOf(currentCategory.get("sortIndex")));
		Short sortindex1 = Short.valueOf(String.valueOf(targetCategory.get("sortIndex")));

		for(Iterator<Entry<String, Category>> it = PRODUCT_CACHE.CATEGORY.entrySet().iterator();it.hasNext();) {
			Entry<String, Category> entry = it.next();
			c = entry.getValue();
			
			c.setSortIndex(sortindex1);
		}
		
		for(Iterator<Entry<String, Category>> itca = PRODUCT_CACHE.CATEGORY.entrySet().iterator();itca.hasNext();) {
			Entry<String, Category> entry = itca.next();
			ca = entry.getValue();
			
			ca.setSortIndex(sortindex);
		}
		
		
		
		Short categoryid = Short.valueOf(String.valueOf(currentCategory.get("categoryId")));
		Short orialgeid = Short.valueOf(String.valueOf(targetCategory.get("categoryId")));
		
		Example example1 = new Example(Category.class);
		Criteria criteria = example1.createCriteria();
		//where key value 
		criteria.andEqualTo(PRODUCT_PARAM.CATEGORY_ID, categoryid );
		categoryMapper.updateByExampleSelective(c, example1);
		
		Example example = new Example(Category.class);
		criteria.andEqualTo(PRODUCT_PARAM.CATEGORY_ID, orialgeid );		
		categoryMapper.updateByExampleSelective(ca, example);
		
		
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}
	/**
	 * 库存更新
	 * @param request
	 * @return Map
	 */
	@Override
	public Map<String, Object> updateSku(Map<String, Object> context) {
		Sku sku = new Sku();
		sku.setSkuId(Short.valueOf((String)context.get(SYConst.PRODUCT_PARAM.SKU_ID)));
		String priceStr = (String) context.get("price");
		sku.setPrice(new BigDecimal(priceStr));
		String stockStr = (String) context.get("stock");
		sku.setStock(Integer.valueOf(stockStr));
		
		Date currentDate = new Date(System.currentTimeMillis());
		sku.setUu((Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID));
		sku.setUt(currentDate);
		Example example = new Example(Sku.class);
		
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo(PRODUCT_PARAM.SKU_ID, Short.valueOf((String)context.get(SYConst.PRODUCT_PARAM.SKU_ID))); 
		skuMapper.updateByExampleSelective(sku, example);
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
		
		

	}
	public Map<String, Object> getBrandWithId(Short brandId) {
		Brand brand = brandMapper.selectByPrimaryKey(brandId); 
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("brand", brand);		
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	
	}
}
