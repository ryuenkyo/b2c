package com.aisy.b2c.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.aisy.b2c.pojo.Emnu;
import com.aisy.b2c.pojo.Logistics;
import com.aisy.b2c.pojo.MonitorInfo;
import com.aisy.b2c.pojo.Pay;
import com.aisy.b2c.pojo.RcDistrict;

/**
 * 数据缓存
 * 
 * @author YanqingLiu
 *
 */
@Component
public class CommonCache {
	/**
	 * 枚举缓存
	 */
	public Map<String, Map<String, Emnu>> EMNU = new LinkedHashMap<String, Map<String, Emnu>>();
	/**
	 * SESSION缓存
	 * 登陆用
	 */
	public Map<String, HttpSession> SESSION_CONTAINER = new LinkedHashMap<String, HttpSession>();
	/**
	 * 省份缓存
	 */
	public List<RcDistrict> PROVICE_LIST = new ArrayList<RcDistrict>();
	/**
	 * 城市缓存
	 */
	public Map<String, List<RcDistrict>> CITY_MAP = new HashMap<String, List<RcDistrict>>();
	/**
	 * 区域缓存
	 */
	public Map<String, List<RcDistrict>> AREA_MAP = new HashMap<String, List<RcDistrict>>();
	/**
	 * 市区缓存
	 */
	public Map<String, RcDistrict> DISTRICT_MAP = new HashMap<String, RcDistrict>();
	/**
	 * 数据统计缓存
	 */
	public Map<String, Integer> MONITORINFO_CACHE = new HashMap<String, Integer>();
	/**
	 * 物流方式缓存
	 */
	public List<Logistics> LOGISTICS_LIST = new ArrayList<Logistics>();
	/**
	 * 支付方式缓存
	 */
	public List<Pay> PAY_LIST = new ArrayList<Pay>();
	
}
