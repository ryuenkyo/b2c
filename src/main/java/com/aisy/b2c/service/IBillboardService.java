package com.aisy.b2c.service;

import java.util.Map;

public interface IBillboardService {
	
	/**
	 * 取得广告轮播数据  分页
	 * @param context 查询条件
	 * @return Map
	 */
	public Map<String, Object>getBillboardInfo(Map<String, Object> context);
	
	/**
	 * 广告轮播增加
	 * @param context
	 * @return Map
	 */
	public Map<String , Object>newBillboard(Map<String, Object> context);
	
	/**
	 * 广告轮播修改
	 * @param context
	 * @return Map
	 */
	public Map<String , Object>updateBillboard(Map<String, Object> context);
	
	/**
	 * 广告轮播删除
	 * @param context
	 * @return Map
	 */
	public Map<String , Object>deleteBillboard(String advertisementId);
	
	/**
	 * 变更广告轮播状态
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> changeBillboardStatus(Map<String, Object> context);
}
