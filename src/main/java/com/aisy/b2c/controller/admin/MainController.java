package com.aisy.b2c.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aisy.b2c.pojo.MonitorInfo;
import com.aisy.b2c.pojo.SPermission;
import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.service.IMonitorService;
import com.aisy.b2c.util.SYConst;

@Controller
@RequestMapping("/system")
public class MainController {
	
	final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@RequestMapping("/main")
	public ModelAndView mainPage(HttpServletRequest request) {
		
		SUser currentUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		
		ModelAndView mv = new ModelAndView("back/main");
		mv.addObject(SYConst.USER_PARAM_NAME.CURRENT_USER, currentUser);
		return mv;
	}
	
	@RequestMapping("/index")
	public String indexPage() {
		return "back/index";
	}
	
	@RequestMapping("/menu/{menuId}")
	@ResponseBody
	public Map<String, Object> menuInfo(@PathVariable String menuId) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<SPermission> list = new ArrayList<SPermission>();
		
		return result;
	}
	
	@Resource
	IMonitorService monitorService;
	
	@RequestMapping("/getInfo")
	@ResponseBody
	public MonitorInfo getInfo() throws Exception{
		return monitorService.getMonitorInfoBean();
	}
}
