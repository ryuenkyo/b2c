package com.aisy.b2c.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aisy.b2c.cache.CommonCache;
import com.aisy.b2c.model.CartModel;
import com.aisy.b2c.model.ClientModel;
import com.aisy.b2c.service.ILoginService;
import com.aisy.b2c.service.IShoppingCartService;
import com.aisy.b2c.util.SYConst;

@Controller
@RequestMapping("/frontLogin")
public class LoginFrontController {
	
	@Resource
	CommonCache COMMON_CACHE;
	
	@Resource
	IShoppingCartService shoppingCartService;
	
	@Resource
	ILoginService loginService;
	
	@RequestMapping("/loginPage")
	public ModelAndView loginPage() {
		ModelAndView mv = new ModelAndView("front/common/login");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/loginAjax", method=RequestMethod.POST)
	public Map<String, Object> loginAjax(
			HttpServletRequest request,
			@RequestBody Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		
		Map<String, Object> loginResultMap = loginService.frontLogin(context);
		
		ClientModel cm = (ClientModel) loginResultMap.get(SYConst.CLIENT_PARAM_NAME.CLIENT_MODEL);
		session.setAttribute(SYConst.SESSION.FRONT_LOGIN_USER, cm);
		 request.getSession().setAttribute(SYConst.CLIENT_PARAM_NAME.CLIENT_ID, cm.getClient().getClientId());
		String clientIdStr = String.valueOf(cm.getClient().getClientId());
		if (COMMON_CACHE.SESSION_CONTAINER.containsKey(clientIdStr)) {
			HttpSession beforSession = COMMON_CACHE.SESSION_CONTAINER.get(clientIdStr);
			beforSession.setAttribute(SYConst.SESSION.OP_CLOSE_FLG, true);
			List<CartModel> cartList = (List<CartModel>) beforSession.getAttribute(SYConst.SESSION.CART);
			session.setAttribute(SYConst.SESSION.CART, cartList);
			beforSession.invalidate();
			COMMON_CACHE.SESSION_CONTAINER.put(clientIdStr, session);
		} else {
			Map<String, Object> cartContext = new HashMap<String, Object>();
			cartContext.put(SYConst.CLIENT_PARAM_NAME.CLIENT_ID, cm.getClient().getClientId());
			Map<String, Object> cartInfoMap = shoppingCartService.getCartInfo(cartContext);
			List<CartModel> cartList = (List<CartModel>) cartInfoMap.get(SYConst.CART.CART_LIST);
			session.setAttribute(SYConst.SESSION.CART, cartList);
			COMMON_CACHE.SESSION_CONTAINER.put(clientIdStr, session);
		}
		
		result.put("clientModel", cm);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		
		return result;
	}
	
}
