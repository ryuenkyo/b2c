package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.ClientPoint;
import com.aisy.b2c.pojo.VClientPoint;

import tk.mybatis.mapper.common.Mapper;

public interface ClientPointMapper extends Mapper<ClientPoint> {
	public List<VClientPoint> selectClientPointInfo(Map<String, Object> context);
	public int selectCountClientPointInfo(Map<String, Object> context);
	

	public List<VClientPoint> selectClientPointDetailInfo(Map<String, Object> context);
	public int selectCountClientPointDetailInfo(Map<String, Object> context);
}