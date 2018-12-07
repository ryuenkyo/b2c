package com.aisy.b2c.service.impl;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aisy.b2c.dao.ClientMapper;
import com.aisy.b2c.dao.SUserMapper;
import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.model.ClientModel;
import com.aisy.b2c.pojo.Client;
import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.service.ILoginService;
import com.aisy.b2c.util.MD5Util;
import com.aisy.b2c.util.SYConst;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class LoginServiceImpl implements ILoginService {
	
	@Resource
	SUserMapper sUserMapper;
	
	
	@Resource
	ClientMapper clientMapper;
	
	@Override
	public Map<String, Object> frontLogin(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String clientName = (String) context.get(SYConst.CLIENT_PARAM_NAME.CLIENT_NAME);
		String password = (String) context.get(SYConst.CLIENT_PARAM_NAME.PASSWORD);	
		
		try {
			String md5Password = MD5Util.encoderByMd5(password);
			Example example = new Example(Client.class);
			Criteria criteria = example.createCriteria();
			
			criteria.andEqualTo(SYConst.CLIENT_PARAM_NAME.CLIENT_NAME, clientName);
			criteria.andEqualTo(SYConst.CLIENT_PARAM_NAME.PASSWORD, md5Password);
			
			List<Client> clientList = clientMapper.selectByExample(example);
			
			if (null != clientList && clientList.size() == 1) {
				Client client = clientList.get(0);
				ClientModel cm = new ClientModel();
				cm.setClient(client);
				
				result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
				result.put(SYConst.SERVICE.MESSAGE_BODY, "登录成功！");
				result.put(SYConst.CLIENT_PARAM_NAME.CLIENT_MODEL, cm);
			} else if (null != clientList && clientList.size() > 1) {
				throw new SYException("登录出现错误！");
			} else {
				throw new SYException("用户名或密码不正确！");
			}
			
		} catch (NoSuchAlgorithmException e) {
			throw new SYException("登录出现错误！", e);
		}
		
		return result;
	}
		
//		// TODO 登录逻辑
//		Short clientId = 0;// 登录成功后获得会员ID

//		
//		
//		result.put(SYConst.CLIENT_PARAM_NAME.CLIENT_MODEL, cm);
//		// TODO 取得用户逻辑
//		
//		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
//		
//		return result;
//	}

	@Override
	public Map<String, Object> systemLogin(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		
		
		
		
		String userName = (String) context.get(SYConst.USER_PARAM_NAME.USER_NAME);
		String password = (String) context.get(SYConst.USER_PARAM_NAME.PASSWORD);
		
		try {
			String md5Password = MD5Util.encoderByMd5(password);
			Example example = new Example(SUser.class);
			Criteria criteria = example.createCriteria();
			
			criteria.andEqualTo(SYConst.USER_PARAM_NAME.USER_NAME, userName);
			criteria.andEqualTo(SYConst.USER_PARAM_NAME.PASSWORD, md5Password);
			
			List<SUser> userList = sUserMapper.selectByExample(example);
			
			if (null != userList && userList.size() == 1) {
				SUser sUser = userList.get(0);
				result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
				result.put(SYConst.SERVICE.MESSAGE_BODY, "登录成功！");
				result.put(SYConst.SESSION.LOGIN_USER, sUser);
			} else if (null != userList && userList.size() > 1) {
				throw new SYException("登录出现错误！");
			} else {
				throw new SYException("用户名或密码不正确！");
			}
			
		} catch (NoSuchAlgorithmException e) {
			throw new SYException("登录出现错误！", e);
		}
		
		return result;
	}

}
