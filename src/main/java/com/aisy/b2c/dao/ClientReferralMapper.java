package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.ClientReferral;
import com.aisy.b2c.pojo.VClientReferral;

import tk.mybatis.mapper.common.Mapper;

public interface ClientReferralMapper extends Mapper<ClientReferral> {
	public List<ClientReferral> selectClinetReferralSql(Map<String, Object> context);
	public int selectCountClinetReferralSql(Map<String, Object> context);
	
	//huangjun
	public List<VClientReferral> selectClientReferralInfo(Map<String, Object> context);
	public int selectCountClientReferralInfo(Map<String, Object> context);
	
	public List<VClientReferral> selectClientReferralDetailInfo(Map<String, Object> context);
	public int selectCountClientReferralDetailInfo(Map<String, Object> context);
	

	
}