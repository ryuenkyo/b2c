package com.aisy.b2c.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisy.b2c.pojo.RcDistrict;
import com.aisy.b2c.service.ICommonService;

@Controller
@RequestMapping("/common")
public class CommonController {
	
	@Resource
	ICommonService commonService;
	
	@ResponseBody
	@RequestMapping("/getProvice")
	public List<RcDistrict> getProvice() {
		return commonService.getProvice();
	}
	
	@ResponseBody
	@RequestMapping("/getCity")
	public List<RcDistrict> getCity(String proviceId) {
		return commonService.getCity(proviceId);
	}
	
	@ResponseBody
	@RequestMapping("/getArea")
	public List<RcDistrict> getArea(String cityId) {
		return commonService.getArea(cityId);
	}
}
