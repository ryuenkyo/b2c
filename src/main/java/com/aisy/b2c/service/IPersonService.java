package com.aisy.b2c.service;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.Client;
import com.aisy.b2c.pojo.DeliveryAddress;
import com.aisy.b2c.pojo.RcDistrict;
import com.aisy.b2c.pojo.VDeliveryAddress;


public interface IPersonService {


	 

	
	/**
	 * 新增推荐码
	 * @param context
	 * @return
	 */
	public Map<String, Object> newReferral(Map<String, Object> context);
	
	/**
	 * 订单管理条件查询
	 * @param context
	 * @return
	 */
	public Map<String, Object> getOrderByStatus(Map<String, Object> context);
	
	/**
	 * 推荐码查询
	 * @param context
	 * @return
	 */
	public Map<String, Object> getReferral(Map<String, Object> context);
	
	/**
	 * 订单管理状态修改
	 * @param context
	 * @return
	 */
	public Map<String, Object> updateOrderStatus(Map<String, Object> context);
	
	/**
	 * 评价新增
	 * @param context
	 * @return
	 */
	public Map<String, Object> insertEvaluation(Map<String, Object> context);
	
	
	// 任蔚松 2018/6/19 开始
	
	public	List<Object> getclient ();
	
	public	List<Object> getclientgrade ();
	
	public	List<Object> getdeliveryAddresses ();
	 
	public List<Object> upclient(Client from);
	
	public List<Object> getrcdistrict();
	

	public List<RcDistrict> getrcdistrictc(Short districtId);
	
	
	public List<String> getrcat(String district);
	
	
	public List<Object> delivery(VDeliveryAddress from);
	
	
	public Object datadelivery(DeliveryAddress addressId);
	
	public Object getpassword(Client password);
	
	public Object uppassword(Client password);
	
	
	public Object upisdefault(Short addressId);
	
	public VDeliveryAddress upisdefaultadd(VDeliveryAddress addressId);
	
	public VDeliveryAddress updeliveryaddress(VDeliveryAddress form);
	
	public List<Object> getpoints();
	
	public List<Object> getclientPoint();
	
	public Boolean getuser(String user);
	
	public Boolean inclient(Map<String, Object>  context);
	
	public Boolean referralCode(String referralCode);

	public Boolean admin(Map<String, Object>  context);
	
	//任蔚松 2018/6/19 结束
	
}
