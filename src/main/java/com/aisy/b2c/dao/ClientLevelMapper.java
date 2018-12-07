package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.ClientLevel;
import com.aisy.b2c.pojo.VSPermission;

import tk.mybatis.mapper.common.Mapper;

public interface ClientLevelMapper extends Mapper<ClientLevel> {
	public List<ClientLevel> selectLeveOderBy(Map<String, Object> context);

}