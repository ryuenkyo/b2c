package com.aisy.b2c.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.aisy.b2c.pojo.SPermission;
import com.aisy.b2c.pojo.SRole;
import com.aisy.b2c.pojo.SUser;
/**
 * 用户相关缓存
 * 
 * @author YanqingLiu
 *
 */
@Component
public class SUserCache {
	
	/**
	 * 用户缓存MAP
	 */
	public static Map<Short, SUser> SUSER_TABLE = new LinkedHashMap<Short, SUser>();
	
	/**
	 * 用户缓存LIST
	 */
	public static List<SUser> SUSER_LIST = new ArrayList<SUser>();
	
	/**
	 * 角色缓存LIST
	 */
	public static List<SRole> ROLE_LIST = new ArrayList<SRole>();
	
	/**
	 * 权限缓存LIST
	 */
	public static List<SPermission> PERMISSION_LIST = new ArrayList<SPermission>();
}
