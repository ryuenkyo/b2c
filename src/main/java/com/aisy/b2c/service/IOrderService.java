package com.aisy.b2c.service;

import java.util.Map;

public interface IOrderService {
	public Map<String, Object> getHeaderData(Map<String, Object> context);
	
	public Map<String, Object> updateOrderHeaderStatus(Map<String, Object> context);
	
	public Map<String, Object> getOrderItemData(Map<String, Object> context);
	
}
