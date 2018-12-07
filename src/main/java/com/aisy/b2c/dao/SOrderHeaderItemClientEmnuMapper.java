package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.OrderHeader;
import com.aisy.b2c.pojo.VOrderHeaderItemClientEmnu;

import tk.mybatis.mapper.common.Mapper;

public interface SOrderHeaderItemClientEmnuMapper extends Mapper<OrderHeader> {
	public List<VOrderHeaderItemClientEmnu> selectOrderByStatusAll(Map<String, Object> context);
	public List<VOrderHeaderItemClientEmnu> selectOrderByStatusPay(Map<String, Object> context);
	public List<VOrderHeaderItemClientEmnu> selectOrderByStatusGo(Map<String, Object> context);
	public List<VOrderHeaderItemClientEmnu> selectOrderByStatusOk(Map<String, Object> context);
	public List<VOrderHeaderItemClientEmnu> selectOrderByStatusEvaluation(Map<String, Object> context);
	
	public int selectCountOrderByStatusAll(Map<String, Object> context);
	public int selectCountOrderByStatusPay(Map<String, Object> context);
	public int selectCountOrderByStatusGo(Map<String, Object> context);
	public int selectCountOrderByStatusOk(Map<String, Object> context);
	public int selectCountOrderByStatusEvaluation(Map<String, Object> context);
}