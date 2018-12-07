package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.Product;

import tk.mybatis.mapper.common.Mapper;

public interface ProductMapper extends Mapper<Product> {
	
	/* add start by liuzhaoyu add new method in dao 2018-05-11 18:15:50 */
	/**
	 * 条件查询商品
	 */
	public List<Product> selectProductInfo(Map<String, Object> context);
	
	/**
	 * 条件查询商品的总数
	 */
	public int selectCountProductInfo(Map<String, Object> context);
	/* add end by liuzhaoyu add new method in dao 2018-05-11 18:16:10 */
}