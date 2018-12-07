package com.aisy.b2c.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aisy.b2c.service.ILoginService;
import com.aisy.b2c.util.SYConst;

@Controller
@RequestMapping("/admin")
public class LoginController {
	
	@Resource
	ILoginService loginService;
	
	@ResponseBody
	@RequestMapping(value="/login", method=RequestMethod.PUT)
	public Map<String, Object> login(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request
			) {
		Map<String, Object> loginResult = loginService.systemLogin(context);
		
		if (SYConst.SERVICE.SUCCESS.equals(loginResult.get(SYConst.SERVICE.STATUS))) {
			request.getSession().setAttribute(SYConst.SESSION.LOGIN_USER, loginResult.get(SYConst.SESSION.LOGIN_USER));
		}
		
		return loginResult;
	}
	
	@RequestMapping("/logout")
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		ModelAndView mv = new ModelAndView("back/main");
		return mv;
	}
	
	/**
	 * 登陆验证码校验
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/kaptchaImageConfirm.json", method=RequestMethod.GET)
	public Map<String, Object> kaptchaImageConfirm(
			@RequestParam String valiCode, 
			HttpSession session) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (valiCode.equals(session.getAttribute(SYConst.SESSION.KAPTCHA_SESSION_KEY))) {
			result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
			return result;
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
		return result;
	}
	
//	@RequestMapping(value="/loginFail", method=RequestMethod.GET) 
//	public ModelAndView{
//		
//	}
}
