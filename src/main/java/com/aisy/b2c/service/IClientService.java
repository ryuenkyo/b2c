package com.aisy.b2c.service;

import java.util.Map;

public interface IClientService {
	public Map<String, Object> regist(Map<String, Object> context);
	
	public Map<String, Object> newReferralCode(Map<String, Object> context);
	
	/**
	 * 取得客户数据  分页
	 * @param context 查询条件
	 * @return Map
	 */
	public Map<String, Object>getClientListData(Map<String, Object> context);
	
	/**
	 * 取得等级数据  分页
	 * @param context 查询条件
	 * @return Map
	 */
	public Map<String, Object>getLevelListData(Map<String, Object> context);
	
	/**
	 * 等级名重复校验
	 * @param levelName
	 * @return Map
	 */
	public Map<String, Object>levelNameConfirm(String levelName);
	
	/**
	 * 等级增加
	 * @param context
	 * @return Map
	 */
	public Map<String , Object>newLevel(Map<String, Object> context);
	
	/**
	 * 等级修改
	 * @param context
	 * @return Map
	 */
	public Map<String , Object>updateLevel(Map<String, Object> context);
	
	/**
	 * 等级删除
	 * @param context
	 * @return Map
	 */
	public Map<String , Object>deleteLevel(String level);
	
	/**
	 * 变更会员状态
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> changeClientStatus(Map<String, Object> context);
	
	/**
	 * 取得客户积分数据  分页
	 * @param context 查询条件
	 * @return Map
	 */
	public Map<String, Object> getClientPointListData(Map<String, Object> context);
	
	/**
	 * 取得客户积分详细数据  分页
	 * @param context 查询条件
	 * @return Map
	 */
	public Map<String, Object> getClientPointDetailList(Map<String, Object> context);
	
	
	/**
	 * 取得客户推荐详细数据  分页
	 * @param context 查询条件
	 * @return Map
	 */
	public Map<String, Object> getClientReferralDetailList(Map<String, Object> context);
	/**
	 * 取得客户推荐码数据  分页
	 * @param context 查询条件
	 * @return Map
	 */
	public Map<String, Object> getClientReferralListData(Map<String, Object> context);
	/**
	 * 查询出等级
	 * @param Short 
	 * @return Map
	 */
	public Map<String, Object> selectLevel(Short level);
	/**
	 * 做出验证，增添的等级要大于前一个等级区间
	 * @param context 查询条件
	 * @return Map
	 */
	public Map<String, Object> levelPointConfirm(Integer level);
	/**
	 * 做出验证，增添的等级要改在区间之内
	 * @param context 查询条件
	 * @return Map
	 */
	public Map<String, Object> updateLevelPointConfirm(Map<String, Object> context);
}
