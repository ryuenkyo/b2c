package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.Evaluation;

import tk.mybatis.mapper.common.Mapper;

public interface EvaluationMapper extends Mapper<Evaluation> {
	public int selectEvaluationCountSql(Map<String, Object> context);
	public List<Evaluation> selectEvaluationSql(Map<String, Object> context);
}