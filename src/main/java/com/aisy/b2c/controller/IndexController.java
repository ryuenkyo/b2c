package com.aisy.b2c.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.aisy.b2c.service.IProductService;
import com.aisy.b2c.util.SYConst;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Resource
	IProductService productService;

	@RequestMapping("/index.html")
	public ModelAndView getIndexPage() {
		ModelAndView view = new ModelAndView("front/index");
		
		Map<String, Object> categoryResult = productService.getCategoryInfo(null);
		view.addObject(SYConst.PRODUCT_PARAM.CATEGORY, categoryResult);
		
		Map<String, Object> storageResult = productService.getFrontStorageInfo(null);
		view.addObject(SYConst.PRODUCT_PARAM.STORAGE, storageResult);
		
		return view;
	}
	
}
