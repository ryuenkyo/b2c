package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.Brand;
import com.aisy.b2c.pojo.VBrand;

import tk.mybatis.mapper.common.Mapper;

/**
 * 品牌Mapper
 * 自定义SQL命令映射
 * 
 * @author YanqingLiu
 *
 */

public interface BrandMapper extends Mapper<Brand> {
	
	/**
	 * 搜索品牌并获取CuName
	 * @return List
	 */
	public List<VBrand> selectBrandWithCuName(Map<String, Object> context);
}