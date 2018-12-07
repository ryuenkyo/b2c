package com.aisy.b2c.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

import com.aisy.b2c.model.CartModel;
import com.aisy.b2c.model.ClientModel;
import com.aisy.b2c.service.ICacheService;
import com.aisy.b2c.service.IShoppingCartService;
import com.aisy.b2c.util.SYConst;

@Controller
@RequestMapping("/front/product/shopping")
public class ShoppingCartController {
	
	@Resource
	IShoppingCartService shoppingCartService;
	
	@Resource
	ICacheService cacheService;
	
	@RequestMapping("/showCart")
	public ModelAndView showCart(HttpServletRequest request) {
		ModelAndView mView = new ModelAndView("front/cart/cart");
		HttpSession session = request.getSession();
		List<CartModel> cartList = (List<CartModel>) session.getAttribute(SYConst.SESSION.CART);
		mView.addObject(SYConst.CART.CART_LIST, cartList);
		return mView;
	}
	
	@ResponseBody
	@RequestMapping(value="/updateCart", method=RequestMethod.POST)
	public Map<String, Object> updateCart(
			@RequestParam(value="skuId", required=true) Short skuId,
			@RequestParam(value="option", required=false) String option,
			@RequestParam(value="count", required=false) Integer count,
			HttpServletRequest request
			) {
		List<CartModel> cartList = (List<CartModel>) request.getSession().getAttribute(SYConst.SESSION.CART);
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.PRODUCT_PARAM.SKU_ID, skuId);
		context.put(SYConst.CART.CART_LIST, cartList);
		context.put(SYConst.CART.OPTION, option);
		context.put(SYConst.CART.NUMBER, count);
		
		return shoppingCartService.updateCartSession(context);
	}
	/*添加购物车*/
	@ResponseBody
	@RequestMapping(value="/addCart", method=RequestMethod.POST)
	public Map<String, Object> addCart(
			@RequestParam(value="skuId", required=true) Short skuId,
			@RequestParam(value="count", required=false) Integer count,
			HttpServletRequest request
			) {
		List<CartModel> cartList = (List<CartModel>) request.getSession().getAttribute(SYConst.SESSION.CART);
		ClientModel currentClient = (ClientModel) request.getSession().getAttribute(SYConst.SESSION.FRONT_LOGIN_USER);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.PRODUCT_PARAM.SKU_ID, skuId);
		context.put(SYConst.CART.CART_LIST, cartList);
		context.put(SYConst.CART.NUMBER, count);
		context.put(SYConst.SESSION.FRONT_LOGIN_USER, currentClient);
		return shoppingCartService.newCartSession(context);
	}
	/*删除购物车*/
	@ResponseBody
	@RequestMapping(value="/deleteCart", method=RequestMethod.POST)
	public Map<String, Object> deleteCart(
			@RequestParam(value="skuId", required=true) Short skuId,
			HttpServletRequest request
			) {
		List<CartModel> cartList = (List<CartModel>) request.getSession().getAttribute(SYConst.SESSION.CART);
		
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.PRODUCT_PARAM.SKU_ID, skuId);
		context.put(SYConst.CART.CART_LIST, cartList);
		
		return shoppingCartService.deleteCartSession(context);
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteCartAll", method=RequestMethod.POST)
	public Map<String, Object> deleteCart(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<CartModel> cartList = (List<CartModel>) request.getSession().getAttribute(SYConst.SESSION.CART);
		System.out.println("asfghjkl"+context);
		for (Iterator<CartModel> it = cartList.iterator(); it.hasNext(); ) {
			CartModel cm = it.next();
			String skuIdStr = String.valueOf(cm.getSkuId());
			if (context.containsKey(skuIdStr)) {
				it.remove();
			}
		}
		
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}
	/*确认订单*/
	@RequestMapping(value="/goConfirm", method=RequestMethod.POST)
	public ModelAndView showCart(
			String cartObject,
			String count,
			String skuId,
			HttpServletRequest request) {
		ModelAndView mView = new ModelAndView("front/cart/confirm");
		HttpSession session = request.getSession();
		List<CartModel> cartList = (List<CartModel>) session.getAttribute(SYConst.SESSION.CART);
		ClientModel currentClient = (ClientModel) session.getAttribute(SYConst.SESSION.FRONT_LOGIN_USER);	
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.CART.CART_LIST, cartList);
		context.put(SYConst.SESSION.FRONT_LOGIN_USER, currentClient);
		context.put("cartObject", cartObject);
		context.put(SYConst.PRODUCT_PARAM.COUNT,count);
		context.put(SYConst.PRODUCT_PARAM.SKU_ID,skuId);
		Map<String, Object> confirmResult = shoppingCartService.confirmOrder(context);
		mView.addObject("confirmList", confirmResult.get("confirmList"));
		mView.addObject("addressList", confirmResult.get("addressList"));
		mView.addObject("logisticsList", confirmResult.get("logisticsList"));
		mView.addObject("orderAmount", confirmResult.get("orderAmount"));
		mView.addObject("payList", confirmResult.get("payList"));
		return mView;
	}
	/*下订单*/
	@RequestMapping(value="/order", method=RequestMethod.POST)
	public ModelAndView paySuccess(
			String skuObject,
			String otherObject,
			HttpServletRequest request) {
		ModelAndView mView = new ModelAndView("front/order/pay");
		HttpSession session = request.getSession();
		List<CartModel> cartList = (List<CartModel>) session.getAttribute(SYConst.SESSION.CART);
		ClientModel currentClient = (ClientModel) session.getAttribute(SYConst.SESSION.FRONT_LOGIN_USER);
		Map<String, Object> context = new HashMap<String, Object>();
		System.out.println(skuObject);
		System.out.println(otherObject);
		context.put(SYConst.CART.CART_LIST, cartList);
		context.put(SYConst.SESSION.FRONT_LOGIN_USER, currentClient);
		context.put(SYConst.PRODUCT_PARAM.SKU_OBJECT, skuObject);
		context.put(SYConst.PRODUCT_PARAM.OTHER_OBJECT, otherObject);
		Map<String, Object> confirmResult = shoppingCartService.order(context);
		mView.addObject("total", confirmResult.get("total"));
		mView.addObject("orderId", confirmResult.get("orderId"));
		mView.addObject("countMapString", confirmResult.get("countMapString"));
		cacheService.productCache();
		return mView;
	}
	
	/*支付*/
	@ResponseBody
	@RequestMapping(value="/payStatus", method=RequestMethod.POST)
	public Map<String, Object> orderStatus(
			String orderId,
			String status,
			String countMap,
			HttpServletRequest request) {
		Map<String, Object> context = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		List<CartModel> cartList = (List<CartModel>) session.getAttribute(SYConst.SESSION.CART);
		context.put(SYConst.CART.CART_LIST, cartList);
		context.put(SYConst.PRODUCT_PARAM.ORDER_ID, orderId);
		context.put(SYConst.PRODUCT_PARAM.STATUS, status);
		context.put(SYConst.PRODUCT_PARAM.COUNT_MAP, countMap);
		System.out.println("controllerStatus++++++++++++++++++++="+countMap);
		cacheService.productCache();
		return shoppingCartService.updateStatus(context);
	}
	
	@RequestMapping("/paySuccess.html")
	public ModelAndView getIndexPage() {
		ModelAndView view = new ModelAndView("front/order/pay_success");
		return view;
	}
}
