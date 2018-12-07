package com.aisy.b2c.util;

import java.util.Iterator;
import java.util.Map.Entry;

import com.aisy.b2c.cache.CommonCache;
import com.aisy.b2c.cache.ProductCache;
import com.aisy.b2c.pojo.DeliveryAddress;

import net.sf.json.JSONObject;

public class JsonUtil {
	
	public static JSONObject stringToObj(String str) {
		return JSONObject.fromObject(str);
	}
	
	public static String fomartAttrNameValue(String jsonObjStr, ProductCache productCache) {
		JSONObject attrObj = stringToObj(jsonObjStr);
		StringBuffer sb = new StringBuffer();
		for (Iterator<Entry<Object, Object>> it = attrObj.entrySet().iterator(); it.hasNext();) {
			Entry<Object, Object> entry = it.next();
			String pAttrNameIdStr = (String) entry.getKey();
			String pAttrValueIdStr = (String) entry.getValue();
			
			sb.append(productCache.P_ATTR_NAME.get(pAttrNameIdStr).getpAttrName());
			sb.append(SYConst.SYMBOL.COLON);
			sb.append(productCache.P_ATTR_VALUE.get(pAttrValueIdStr).getpAttrValue());
			sb.append(SYConst.SYMBOL.SEMICOLON);
			//sb.append(SYConst.SYMBOL.HTML_NEWLINE);
		}
		
		return sb.toString();
	}
	
	public static String attrNameValue(String jsonObjStr, ProductCache productCache) {
		JSONObject attrObj = stringToObj(jsonObjStr);
		StringBuffer sb = new StringBuffer();
		for (Iterator<Entry<Object, Object>> it = attrObj.entrySet().iterator(); it.hasNext();) {
			Entry<Object, Object> entry = it.next();
			String pAttrNameIdStr = (String) entry.getKey();
			String pAttrValueIdStr = (String) entry.getValue();
			
			sb.append(productCache.P_ATTR_NAME.get(pAttrNameIdStr).getpAttrName());
			sb.append(SYConst.SYMBOL.COLON);
			sb.append(productCache.P_ATTR_VALUE.get(pAttrValueIdStr).getpAttrValue());
			sb.append(SYConst.SYMBOL.SEMICOLON);
		}
		
		return sb.toString();
	}
	
	public static String deliveryAddress(DeliveryAddress da, CommonCache commonCache) {
		StringBuffer sb = new StringBuffer();
		Short getpCode = da.getpCode();
		Short getcCode = da.getcCode();
		Short getaCode = da.getaCode();
		String street = da.getStreet();
		String zipCode = da.getZipCode();
		sb.append(commonCache.DISTRICT_MAP.get(getpCode.toString()).getDistrict());
		sb.append(SYConst.SYMBOL.SEMICOLON);
		sb.append(commonCache.DISTRICT_MAP.get(getcCode.toString()).getDistrict());
		sb.append(SYConst.SYMBOL.SEMICOLON);
		sb.append(commonCache.DISTRICT_MAP.get(getaCode.toString()).getDistrict());
		sb.append(SYConst.SYMBOL.SEMICOLON);
		sb.append(street);
		sb.append(SYConst.SYMBOL.SEMICOLON);
		sb.append(zipCode);
		sb.append(SYConst.SYMBOL.SEMICOLON);
		return sb.toString();
	}
}
