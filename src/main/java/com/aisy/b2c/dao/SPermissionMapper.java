package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.SPermission;
import com.aisy.b2c.pojo.VSPermission;

import tk.mybatis.mapper.common.Mapper;

public interface SPermissionMapper extends Mapper<SPermission> {
	
	public List<VSPermission> selectPermissionDataWithCuName(Map<String, Object> context);
}