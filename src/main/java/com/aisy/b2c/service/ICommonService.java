package com.aisy.b2c.service;

import java.util.List;

import com.aisy.b2c.pojo.RcDistrict;

public interface ICommonService {
	public List<RcDistrict> getProvice();
	public List<RcDistrict> getCity(String proviceIdStr);
	public List<RcDistrict> getArea(String cityIdStr);
}
