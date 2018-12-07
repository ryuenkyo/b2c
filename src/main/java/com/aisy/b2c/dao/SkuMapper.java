package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.Sku;
import com.aisy.b2c.pojo.VSku;

import tk.mybatis.mapper.common.Mapper;

public interface SkuMapper extends Mapper<Sku> {
	List<VSku> selectSkuWithProduct(Map<String, Object> context);
	int selectSkuWithProductCount(Map<String, Object> context);
	int selectSkuWithProductSum(Map<String, Object> context);
}