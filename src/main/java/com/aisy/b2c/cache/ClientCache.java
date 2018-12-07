package com.aisy.b2c.cache;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import com.aisy.b2c.pojo.ClientLevel;

/**
 * 会员缓存
 * 
 * @author YanqingLiu
 *
 */
@Component
public class ClientCache {
	
	/**
	 * 等级缓存List
	 */
	public static List<ClientLevel> CLIENT_LEVEL = new ArrayList<ClientLevel>();
}
