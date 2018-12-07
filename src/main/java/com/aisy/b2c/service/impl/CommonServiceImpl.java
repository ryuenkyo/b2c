package com.aisy.b2c.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aisy.b2c.cache.CommonCache;
import com.aisy.b2c.pojo.RcDistrict;
import com.aisy.b2c.service.ICommonService;

@Service
public class CommonServiceImpl implements ICommonService {

	@Resource
	CommonCache COMMON_CACHE;
	
	@Override
	public List<RcDistrict> getProvice() {
		return COMMON_CACHE.PROVICE_LIST;
	}

	@Override
	public List<RcDistrict> getCity(String proviceIdStr) {
		return COMMON_CACHE.CITY_MAP.get(proviceIdStr);
	}

	@Override
	public List<RcDistrict> getArea(String cityIdStr) {
		return COMMON_CACHE.AREA_MAP.get(cityIdStr);
	}

}
