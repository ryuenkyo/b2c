package com.aisy.b2c.service;

/**
 * 缓存服务
 * 
 * 
 * @author cailongyang
 *
 */
public interface ICacheService {
	
	public void emnuCache();
	
	public void pAttrNameCache();
	
	public void pAttrValueCache();
	
	public void categoryCache();
	
	public void categoryAttrCache();
	
	public void brandCache();
	
	public void productCategoryCache();
	
	public void productCache();
	
	public void storageCache();
	
	public void storageProductsCache();
	
	/* created start by YanqingLiu for product imageCache 2018-05-14 10:00:30 */
	/**
	 * 构建产品图片缓存
	 */
	public void imageCache();
	
	/**
	 * 构建等级缓存
	 */
	public void clientLevelCache();
	/**
	 * 构建用户缓存
	 */
	public void userCache();
	/**
	 * 构建用户缓存
	 */
	public void userTableCache();
	/**
	 * 构建角色缓存
	 */
	public void roleCache();
	
	/**
	 * 构建用户缓存
	 */
	public void permissionCache();
	/* add end by YanqingLiu for product imageCache 2018-05-14 10:00:30 */
	
	public void categoryProductCache();
	
	public void attrProductCache();
	
	public void rcDistrictCache();
	
	/**
	 * 系统信息缓存
	 */
	public void MonitorInfoCache();
	/**
	 * 支付方式缓存
	 */
	public void payCache();
	/**
	 * 物流方式缓存
	 */
	public void logisticsCache();
	
}
