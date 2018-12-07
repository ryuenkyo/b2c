package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.Storage;

import tk.mybatis.mapper.common.Mapper;

public interface StorageMapper extends Mapper<Storage> {
	
	public List<Storage> selectStorageWithName(Map<String, Object> context);
	
	public int selectCountStorageWithName(Map<String, Object> context);
}