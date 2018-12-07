package com.aisy.b2c.service.impl;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aisy.b2c.dao.OrderHeaderMapper;
import com.aisy.b2c.dao.OrderItemMapper;
import com.aisy.b2c.dao.SOrderHeaderItemClientEmnuMapper;
import com.aisy.b2c.dao.SUserRoleMapper;
import com.aisy.b2c.pojo.SRole;
import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.pojo.Storage;
import com.aisy.b2c.pojo.VOrderHeader;
import com.aisy.b2c.pojo.VOrderHeaderItemClientEmnu;
import com.aisy.b2c.service.IOrderService;
import com.aisy.b2c.util.SYConst;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;
@Service
public class OrderServicelmpl implements IOrderService {

	@Resource
	SOrderHeaderItemClientEmnuMapper sOrderHeaderItemClientEmnuMapper;
	@Resource
	OrderHeaderMapper orderHeaderMapper;
	
	@Resource
	OrderItemMapper orderItemMapper;
	@Override
	public Map<String, Object> getHeaderData(Map<String, Object> context) {

		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		Map<String, Object> result = new HashMap<String, Object>();
		 //取得总数量
		int orderHeadercount = orderHeaderMapper.selectOrderHeaderByCount(context);	    
	    List<VOrderHeader> orderHeaderlist =  orderHeaderMapper.selectOrderHeaderByStatusAll(context);
	    		  	     
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, orderHeadercount);
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, orderHeaderlist);
		return result;
	
	
	}
	@Override
	public Map<String, Object> updateOrderHeaderStatus(Map<String, Object> context) {
		Date currentDate = new Date(System.currentTimeMillis());
		VOrderHeader vOrderHeader = new VOrderHeader();
		vOrderHeader.setOrderId(Short.valueOf((String)context.get("orderId")));
		vOrderHeader.setUu((Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID));
		vOrderHeader.setUt(currentDate);
	    vOrderHeader.setOrderStatus("已发货");
	    int o =orderHeaderMapper.updateByPrimaryKeySelective(vOrderHeader);
		
	    Map<String, Object> result = new HashMap<String, Object>(1);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}
	
	@Override
	public Map<String, Object> getOrderItemData(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		Map<String, Object> result = new HashMap<String, Object>();
	    List<VOrderHeaderItemClientEmnu> orderItemlist =  orderItemMapper.selectOrderHeaderByorderId(context);
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, orderItemlist);
		return result;
	}
	
}
