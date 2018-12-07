package com.aisy.b2c.service;

import java.util.Map;

public interface IShoppingCartService {
	public Map<String, Object> getCartInfo(Map<String, Object> context);
	public Map<String, Object> newCartItem(Map<String, Object> context);
	public Map<String, Object> updateCart(Map<String, Object> context);
	public Map<String, Object> deleteCartItem(Map<String, Object> context);
	public Map<String, Object> updateBack(Map<String, Object> context);
	public Map<String, Object> confirmOrder(Map<String, Object> context);
	public Map<String, Object> order(Map<String, Object> context);
	public Map<String, Object> updateCartSession(Map<String, Object> context);
	public Map<String, Object> newCartSession(Map<String, Object> context);
	public Map<String, Object> deleteCartSession(Map<String, Object> context);
	public Map<String, Object> updateStatus(Map<String, Object> context);
}
