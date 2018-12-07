package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.OrderHeader;
import com.aisy.b2c.pojo.OrderItem;
import com.aisy.b2c.pojo.VOrderHeader;
import com.aisy.b2c.pojo.VOrderHeaderItemClientEmnu;

import tk.mybatis.mapper.common.Mapper;

public interface OrderItemMapper extends Mapper<OrderItem> {
	
	public List<VOrderHeaderItemClientEmnu> selectOrderHeaderByorderId(Map<String, Object> context);
	
}