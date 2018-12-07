package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.SRole;
import com.aisy.b2c.pojo.VSRole;

import tk.mybatis.mapper.common.Mapper;

public interface SRoleMapper extends Mapper<SRole> {
	public List<VSRole> selectRoleDataWithCuName(Map<String, Object> context);
}