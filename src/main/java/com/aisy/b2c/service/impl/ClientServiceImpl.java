package com.aisy.b2c.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aisy.b2c.cache.ClientCache;
import com.aisy.b2c.cache.CommonCache;
import com.aisy.b2c.dao.ClientLevelMapper;
import com.aisy.b2c.dao.ClientMapper;
import com.aisy.b2c.dao.ClientPointMapper;
import com.aisy.b2c.dao.ClientReferralMapper;
import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.pojo.Client;
import com.aisy.b2c.pojo.ClientLevel;
import com.aisy.b2c.pojo.ClientPoint;
import com.aisy.b2c.pojo.ClientReferral;
import com.aisy.b2c.pojo.Emnu;
import com.aisy.b2c.pojo.Product;
import com.aisy.b2c.pojo.VClient;
import com.aisy.b2c.pojo.VClientPoint;
import com.aisy.b2c.pojo.VClientReferral;
import com.aisy.b2c.service.ICacheService;
import com.aisy.b2c.service.IClientService;
import com.aisy.b2c.util.SYConst;
import com.aisy.b2c.util.SYConst.EMNU;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ClientServiceImpl implements IClientService {
	
	@Resource
	ClientMapper clientMapper;
	
	@Resource
	ClientPointMapper clientPointMapper;
	
	@Resource
	ClientReferralMapper clientReferralMapper;
	
	@Resource
	ClientLevelMapper clientLevelMapper;
	
	@Resource
	ICacheService cacheService;
	
	@Resource
	CommonCache COMMON_CACHE;

	@Override
	public Map<String, Object> getClientListData(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		List<Client> clientInfo = clientMapper.selectClientInfo(context);
		List<VClient> clientList = new ArrayList<VClient>();
		Map<String, Emnu> map = COMMON_CACHE.EMNU.get(SYConst.EMNU.STATUS);
		Iterator<Client> it = clientInfo.iterator();
		while (it.hasNext()) {
			Client client = it.next();
			VClient vClient = new VClient();
			vClient.setClientId(client.getClientId());
			vClient.setClientName(client.getClientName());
			vClient.setAge(client.getAge());
			vClient.setHeaderImg(client.getHeaderImg());
			vClient.setNickName(client.getNickName());
			vClient.setStatus(client.getStatus());
			vClient.setTelphone(client.getTelphone());
			String statusName = map.get(client.getStatus()).getEmnuValue();
			vClient.setStatusName(statusName);
			vClient.setCt(client.getCt());
			vClient.setUt(client.getUt());
			vClient.setCu(client.getCu());
			vClient.setUu(client.getUu());
			clientList.add(vClient);
		}
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, clientList);
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, clientMapper.selectCountClientInfo(context));
		return result;
	}

	@Override
	public Map<String, Object> getLevelListData(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, ClientCache.CLIENT_LEVEL);
		return result;
	}

	@Override
	public Map<String, Object> levelNameConfirm(String levelName) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		for (Iterator<ClientLevel> it = ClientCache.CLIENT_LEVEL.iterator(); it.hasNext();) {
			ClientLevel clientLevel = it.next();
			if (clientLevel.getLevelName().equals(levelName)) {
				result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
				return result;
			}
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
		return result;
	}

	@Override
	public synchronized Map<String, Object> newLevel(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		Date currentDate = new Date(System.currentTimeMillis());
		ClientLevel cLevel = new ClientLevel();
		cLevel.setLevelName((String)context.get("levelName"));

		cLevel.setLevelPoint(Integer.parseInt((String)context.get("levelPoint")));
		cLevel.setCu((Short)context.get(SYConst.USER_PARAM_NAME.CURRENT_USER));
		cLevel.setUu((Short)context.get(SYConst.USER_PARAM_NAME.CURRENT_USER));
		cLevel.setCt(currentDate);
		cLevel.setUt(currentDate);
		clientLevelMapper.insert(cLevel);
		//更新缓存
		cacheService.clientLevelCache();
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> updateLevel(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		Date currentDate = new Date(System.currentTimeMillis());
		ClientLevel cLevel = new ClientLevel();
		Short level = ((Integer) context.get("level")).shortValue();
		cLevel.setLevel(level);
		cLevel.setLevelName((String)context.get(SYConst.CLIENT_PARAM_NAME.LEVEL_NAME));
		cLevel.setLevelPoint(Integer.parseInt((String)context.get(SYConst.CLIENT_PARAM_NAME.LEVEL_POINT)));
		cLevel.setUu((Short)context.get(SYConst.USER_PARAM_NAME.CURRENT_USER));
		cLevel.setUt(currentDate);
		clientLevelMapper.updateByPrimaryKeySelective(cLevel);
		//更新缓存
		cacheService.clientLevelCache();
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> deleteLevel(String level) {
		Map<String, Object> result = new HashMap<String, Object>();
		clientLevelMapper.deleteByPrimaryKey(level);
		//更新缓存
		cacheService.clientLevelCache();
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}
	
	@Override
	public Map<String, Object> regist(Map<String, Object> context) {
		
		
		String phoneNumber = (String) context.get("phoneNumber");
		String identifyingCode = (String) context.get("identifyingCode");
		String password = (String) context.get("password");
		String passwordConfirm = (String) context.get("passwordConfirm");
		String referralCode = (String) context.get("referralCode");
		
		Client newClient = new Client();
		newClient.setTelphone(phoneNumber);
		newClient.setPassword(password);
		newClient.setCu((short) 0);
		newClient.setUu((short) 0);
		Date currentDate = new Date(System.currentTimeMillis());
		newClient.setCt(currentDate);
		newClient.setUt(currentDate);
		
		clientMapper.insert(newClient);
		
		ClientPoint clientPoint = new ClientPoint();
		clientPoint.setClientId(newClient.getClientId());
		clientPoint.setPoint(0);
		clientPoint.setUsedPoint(0);
		clientPoint.setCu((short) 0);
		clientPoint.setUu((short) 0); 
		clientPoint.setCt(currentDate);
		clientPoint.setUt(currentDate);
		clientPointMapper.insert(clientPoint);
		
		Short newClientId = newClient.getClientId();
		
		if (!StringUtils.isEmpty(referralCode)) {
			Example example = new Example(ClientReferral.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("referralCode", referralCode);
			criteria.andEqualTo("isAvailable", false);
			
			ClientReferral clientReferral = clientReferralMapper.selectOneByExample(example);
			
			if (null == clientReferral) {
				throw new SYException("您所输入的推荐码无效！");
			}
			
			Short clientId = clientReferral.getClientId();
			clientReferral.setUsedClientId(newClientId);
			clientReferral.setIsAvailable(true);
			int resultCount = clientReferralMapper.updateByPrimaryKeySelective(clientReferral);
			
			if (1 != resultCount) {
				throw new SYException("推荐码有问题，请重新注册！");
			}
			
			
		}
		
		
		
		
		return null;
	}

	@Override
	public Map<String, Object> newReferralCode(Map<String, Object> context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> changeClientStatus(Map<String, Object> context) {
		Date currentDate = new Date(System.currentTimeMillis());
		Client record = new Client();
		record.setClientId(Short.valueOf((String)context.get(SYConst.CLIENT_PARAM_NAME.CLIENT_ID)));
		record.setUu((Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID));
		record.setUt(currentDate);
		if (SYConst.EMNU.ACTIVATE.equals((String)context.get(SYConst.PRODUCT_PARAM.STATUS))) {
			record.setStatus(SYConst.EMNU.INACTIVATE);
		}else {
			record.setStatus(SYConst.EMNU.ACTIVATE);
		}
		clientMapper.updateByPrimaryKeySelective(record);
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> getClientPointListData(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		List<VClientPoint> clientPointInfo = clientPointMapper.selectClientPointInfo(context);
		Set<VClientPoint> clientPointList = new HashSet<VClientPoint>();
		List<ClientLevel> levelMap = ClientCache.CLIENT_LEVEL;
		Iterator<VClientPoint> it = clientPointInfo.iterator();
		while (it.hasNext()) {
			VClientPoint vClientPoint = it.next();
			for (int i = 0; i < levelMap.size()-1; i++) {
				if(rangeInDefined(vClientPoint.getPoint(), levelMap.get(i).getLevelPoint(), levelMap.get(i+1).getLevelPoint())) {
					String name = levelMap.get(i).getLevelName();
					vClientPoint.setLevelName(name);
				} 
				clientPointList.add(vClientPoint);
			}
		}
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, clientPointList);
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, clientPointMapper.selectCountClientPointInfo(context));
		return result;
	}
	
	public static boolean rangeInDefined(int current, int min, int max) {  
        return Math.max(min, current) == Math.min(current, max);  
    }

	@Override
	public Map<String, Object> getClientPointDetailList(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, clientPointMapper.selectClientPointDetailInfo(context));
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, clientPointMapper.selectCountClientPointDetailInfo(context));
		return result;
	}   
	

	/**
	 * 推荐管理列表首页
	 */
	@Override
	public Map<String, Object> getClientReferralListData(Map<String, Object> context) {
		
		
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<VClientReferral> newList = new ArrayList<VClientReferral>();
		List<VClientReferral> crList = clientReferralMapper.selectClientReferralInfo(context);
		List<Client>  cList = clientMapper.selectAll();
		Iterator<VClientReferral> it = crList.iterator();
		while(it.hasNext()) {
		VClientReferral vcR = it.next();
			if(vcR.getIsAvailable()) {
				vcR.setAvailableName("已使用");
				newList.add(vcR);
			}
			
		}
		
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, newList);
		
		return result;
	}	

		
		
	

	@Override
	public Map<String, Object> getClientReferralDetailList(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, clientReferralMapper.selectClientReferralDetailInfo(context));
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, clientReferralMapper.selectCountClientReferralDetailInfo(context));
		return result;
		
		

	}
	@Override
	public Map<String, Object> selectLevel(Short level) {


		ClientLevel clientLevel = clientLevelMapper.selectByPrimaryKey(level); 
				

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("clientlevel", clientLevel);
//		当前角色的权限put进入结果集中
		
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	
	}

	@Override
	public Map<String, Object> levelPointConfirm(Integer level) {
		Integer max = 1;
		List<ClientLevel> levellist = ClientCache.CLIENT_LEVEL;
		Map<String, Object> result = new HashMap<String, Object>();
		//做出循环找出level中值最大的数值
        for (int i = 0; i < levellist.size(); i++) {
			if(levellist.get(0).getLevel()>levellist.get(i).getLevel()) {
				max = levellist.get(0).getLevelPoint();
			}else if(i>0) {
				if (levellist.get(i).getLevelPoint()>levellist.get(i-1).getLevelPoint()) {
					max = levellist.get(i).getLevelPoint();
				}	
				
				
			}
		
        }
		if (max >= level) {
			result.put(SYConst.SERVICE.STATUS, "SUCCESS");
		}
		else if(max < level) {
			result.put(SYConst.SERVICE.STATUS, "FALL");
		}
		return result;
	}

	@Override
	public Map<String, Object> updateLevelPointConfirm(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		short level = (short) Integer.parseInt(context.get("level").toString());
		Integer levelPoint = Integer.parseInt(context.get("levePoint").toString());
		//从缓存调出等级的全查询
		List<ClientLevel> levellist = ClientCache.CLIENT_LEVEL;
		//定位出倒数第一个level
		Integer a = levellist.size()-1;
		//q全查询出level的list
		List<ClientLevel> selectAll = clientLevelMapper.selectAll();
		//得到出最后一个level的信息
		ClientLevel clientLevel = selectAll.get(a);
		System.out.println(clientLevel.getLevelName());
	//	做出循环，找出选中等级前一个与后一个的，等级值
		for (int i = 0; i < levellist.size(); i++) {
			//如果选中的是第一个值的时候
			if (level==1) {
				Integer levelafter = levellist.get(i+1).getLevelPoint();
				if(levelPoint>levelafter) {
					result.put(SYConst.SERVICE.STATUS, "FALL");
				}else {
					result.put(SYConst.SERVICE.STATUS, "SUCCESS");
				}
			}
			//如果是最后一个level，进行判断要小于前一个
			 if(level==clientLevel.getLevel()) {
				Integer levelbefore =  levellist.get(i).getLevelPoint();
				if (levelbefore>levelPoint) {
					result.put(SYConst.SERVICE.STATUS, "FALL");
				}else {
					result.put(SYConst.SERVICE.STATUS, "SUCCESS");
				}
			}
		//	如果选中的不是第一个值的时候，进行逻辑判断找出之前一个与后一个
			else if (level!=0&&levellist.get(i).getLevel()==level) {
				Integer levelbefore =  levellist.get(i-1).getLevelPoint();
				Integer levelafter = levellist.get(i+1).getLevelPoint();
				if (levelPoint>levelafter||levelPoint<levelbefore) {
					result.put(SYConst.SERVICE.STATUS, "FALL");
				}else {
					result.put(SYConst.SERVICE.STATUS, "SUCCESS");
				}
				}
			}
		
		return result;
		 
	}


}
