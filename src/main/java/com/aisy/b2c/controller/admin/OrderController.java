package com.aisy.b2c.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.pojo.VOrderHeader;
import com.aisy.b2c.pojo.VOrderHeaderItemClientEmnu;
import com.aisy.b2c.service.impl.OrderServicelmpl;
import com.aisy.b2c.util.SYConst;



/**
 * 订单controller
 * 
 * @author YanqingLiu
 *
 */

@Controller
@RequestMapping("/system/order")
public class OrderController {
	@Resource
	OrderServicelmpl orderServicelmpl;
	
	
	/**
	 * 返回订单视图页面
	 * @return "back/order/order_list"
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String orderListPage(HttpServletRequest request) {
		return "back/order/header_list";
	}
	
	@ResponseBody
	@RequestMapping(value="/orderHeader.json", method=RequestMethod.GET)
	public Map<String, Object> getUserListData(
			String amountMin,
			String amountMax,
			String amountEnd,
			VOrderHeader vOrderHeader,
			String timeMin,
			String timeMax,
			Map<String, Object> context,
			int page,
			int limit) {
	
		context.put("clientName", vOrderHeader.getClientName());
		context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
		context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);		
		Map<String, Object> headermap = orderServicelmpl.getHeaderData(context);
		return headermap;
		
	}

	/**
	 * 更改订单状态
	 * @return "back/order/order_list"
	 */
	@ResponseBody
	@RequestMapping(value="/updateOrderHeaderStatus", method=RequestMethod.POST)
	public Map<String, Object> orderHeaderStatus(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		return orderServicelmpl.updateOrderHeaderStatus(context);
	}
	/**
	 * 订单详情展示
	 * 
	 * @author ming
	 *
	 */
	@ResponseBody
	@RequestMapping(value="/orderItem.json/{orderId}", method=RequestMethod.GET)
	public Map<String, Object> getorderHeaderStatus(
			@PathVariable("orderId") String orderId) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("orderId", orderId);
		return orderServicelmpl.getOrderItemData(context);
	}
	
	
}

