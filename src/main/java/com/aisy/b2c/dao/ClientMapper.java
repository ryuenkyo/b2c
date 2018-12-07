package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.Client;
import tk.mybatis.mapper.common.Mapper;

/**
 * 客户Mapper
 * 自定义SQL命令映射
 * 
 * @author YanqingLiu
 *
 */

public interface ClientMapper extends Mapper<Client> {
	
	/**
	 * 模糊条件查询客户信息
	 * @return List
	 */
	public List<Client> selectClientInfo(Map<String, Object> context);
	
	/**
	 * 模糊条件查询客户信息总数
	 * @return int
	 */
	public int selectCountClientInfo(Map<String, Object> context);
}