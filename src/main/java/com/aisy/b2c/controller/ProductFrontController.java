package com.aisy.b2c.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aisy.b2c.pojo.Brand;
import com.aisy.b2c.service.IProductService;
import com.aisy.b2c.util.SYConst;

@Controller
@RequestMapping("/front/product")
public class ProductFrontController {
	
	@Resource
	IProductService productService;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ModelAndView searchPage(
			@RequestParam(value="categoryId", required=false) String categoryIdStr,
			@RequestParam(value="productName", required=false) String productName,
			@RequestParam(value="pAttrObject", required=false) String pAttrObject,
			@RequestParam(value="brandId", required=false) String brandIdStr
			) {
		ModelAndView view = new ModelAndView("front/product/product_list");
		
		view.addObject("categoryId", categoryIdStr);
		view.addObject("productName", productName);
		view.addObject("pAttrObject", pAttrObject);
		view.addObject("brandId", brandIdStr);

		return view;
	}
	
	/* 刘笑楠 添加 商品详情查询 开始于2018.6.11 09:25:00:00 */
	@RequestMapping(value="/detail/{productId}.html", method=RequestMethod.GET)
	public ModelAndView productDetail(
		@PathVariable(value = "productId") String productId) {
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.PRODUCT_PARAM.PRODUCT_ID, productId);
		
		Map<String, Object> result = productService.getProductDetailData(context);

		ModelAndView view = new ModelAndView("front/product/product_detail");
		
		view.addObject("product", result.get("product"));
		view.addObject("imageList", result.get("imageList"));
		view.addObject("skuMap", result.get("skuMap"));
		view.addObject("pAttrNameList", result.get("pAttrNameList"));
		view.addObject("pAttrValueMap", result.get("pAttrValueMap"));
		view.addObject("evaluationCount", result.get("evaluationCount"));
		view.addObject("hotProduct", result.get("hotProduct"));
		view.addObject("skuSum", result.get("skuSum"));
		view.addObject("imageHotList", result.get("imageHotList"));
		
		return view;
	}
	/* 刘笑楠 添加 商品详情查询 结束于2018.6.11 09:25:30:00 */
	
	@ResponseBody
	@RequestMapping("/searchInfo")
	public Map<String, Object> getSearchInfo() {
		Map<String, Object> result = new HashMap<String, Object>();
		
		Map<String, Object> categoryInfo = productService.getCategoryInfo(null);
		List<Brand> brandList = productService.getBrands(null);
		Map<String, Object> attrInfo = productService.getAttrInfoAll(null);
		
		result.put("category", categoryInfo);
		result.put("brand", brandList);
		result.put("attrInfo", attrInfo);
		
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/searchProductInfo", method=RequestMethod.POST)
	public Map<String, Object> getSearchProductInfo(
		@RequestBody Map<String, Object> context) {
		return productService.getProductData(context);
	}
	
	
	
}
