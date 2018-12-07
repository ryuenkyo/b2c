package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.OrderHeader;
import com.aisy.b2c.pojo.VOrderHeader;
import com.aisy.b2c.pojo.VSPermission;

import tk.mybatis.mapper.common.Mapper;

public interface OrderHeaderMapper extends Mapper<OrderHeader> {
	public List<VOrderHeader> selectOrderHeaderByStatusAll(Map<String, Object> context);

	public int selectOrderHeaderByCount(Map<String, Object> context);
	
	/** 
	 * 获得销售总额. 
	 * @return int
	 * @author YanqingLiu 
	 */ 
	public int selectSumAmountOrderHeader();
	
	/** 
	 * 获得订单总数. 
	 * @return int
	 * @author YanqingLiu 
	 */ 
	public int selectCountOrderHeader();
}