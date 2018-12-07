package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.pojo.VUserRole;

import tk.mybatis.mapper.common.Mapper;

public interface SUserMapper extends Mapper<SUser> {
	
	public List<VUserRole> selectUserInfoWithRole(Map<String, Object> param);
	public int selectCountUserInfoWithRole(Map<String, Object> param);
}