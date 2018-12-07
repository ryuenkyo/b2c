package com.aisy.b2c.service.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aisy.b2c.cache.SUserCache;
import com.aisy.b2c.dao.EmnuMapper;
import com.aisy.b2c.dao.SPermissionMapper;
import com.aisy.b2c.dao.SRoleMapper;
import com.aisy.b2c.dao.SRolePermissionMapper;
import com.aisy.b2c.dao.SUserMapper;
import com.aisy.b2c.dao.SUserRoleMapper;
import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.pojo.Emnu;
import com.aisy.b2c.pojo.SPermission;
import com.aisy.b2c.pojo.SRole;
import com.aisy.b2c.pojo.SRolePermission;
import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.pojo.SUserRole;
import com.aisy.b2c.pojo.VPermissionMenu;
import com.aisy.b2c.pojo.VSPermission;
import com.aisy.b2c.service.ICacheService;
import com.aisy.b2c.service.IUserService;
import com.aisy.b2c.util.MD5Util;
import com.aisy.b2c.util.Message;
import com.aisy.b2c.util.SYConst;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class UserServiceImpl implements IUserService {

	@Resource
	SUserMapper sUserMapper;
	
	@Resource
	SUserRoleMapper sUserRoleMapper;
	
	@Resource
	SRoleMapper sRoleMapper;
	
	@Resource
	SRolePermissionMapper sRolePermissionMapper;
	
	@Resource
	SPermissionMapper sPermissionMapper;
	
	@Resource
	EmnuMapper emnuMapper;
	
	@Resource(name="message")
	Message message;
	
	@Resource
	SUserCache susercache;
	
	@Resource
	ICacheService cacheService;

	private List<SPermission> pERMISSION_LIST;
	/**
	 * @see IUserService.getUserListByPage
	 */
	public List<SUser> getUserListByPage(int currentPage, int limit, Map<String, Object> whereMap) {
		return null;
	}
	
	public SUser selectByPrimaryKey(Short userId) {
		return sUserMapper.selectByPrimaryKey(userId);
	}
	
	@Override
	public List<VPermissionMenu> getAllPermissionList() {
		List<VPermissionMenu> result = new ArrayList<VPermissionMenu>();
		Map<Short, VPermissionMenu> pMap = getPermissionMenuByUser(null);
		for (Iterator<VPermissionMenu> it = pMap.values().iterator(); it.hasNext();) {
			result.add(it.next());
		}
		return result;
	}

	@Override
	public Map<Short, VPermissionMenu> getPermissionMenuByUser(Short userId) {
//		System.out.print(this.hashCode());
//		System.out.println(":"+Thread.currentThread().getId());	
		Map<Short, VPermissionMenu> result = new LinkedHashMap<Short, VPermissionMenu>();
		Example example = new Example(SUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.USER_PARAM_NAME.USER_ID, userId);
		List<SUserRole> roleList = sUserRoleMapper.selectByExample(example);
		if (null == roleList && roleList.size() == 0) {
			throw new SYException("当前用户尚未指定角色！");
		}
		SUserRole role = roleList.get(0);
		Example exampleRP = new Example(SRolePermission.class);
		Criteria criteriaRP = exampleRP.createCriteria();
		criteriaRP.andEqualTo(SYConst.USER_PARAM_NAME.ROLE_ID, role.getRoleId());
		List<SRolePermission> rpList = sRolePermissionMapper.selectByExample(exampleRP);
		Set<Short> pIdSet = new HashSet<Short>();
		for (SRolePermission srp : rpList) {
			pIdSet.add(srp.getPermissionId());
		}
		Example exampleSP = new Example(SPermission.class);
		Criteria criteriaSP = exampleSP.createCriteria();
		criteriaSP.andIn(SYConst.USER_PARAM_NAME.PERMISSION_ID, pIdSet);
		List<SPermission> permissionList = sPermissionMapper.selectByExample(exampleSP);
		if (null != permissionList && permissionList.size() > 0) {
			long s = System.currentTimeMillis();
			for (Iterator<SPermission> it = permissionList.iterator(); it.hasNext();) {
				SPermission sPermission = it.next();
				VPermissionMenu vpm = new VPermissionMenu();
				BeanUtils.copyProperties(sPermission, vpm);
				if (0 == sPermission.getParentId()) {
					result.put(vpm.getPermissionId(), vpm);
				} else if (result.containsKey(sPermission.getParentId())) {
					result.get(sPermission.getParentId()).getChildren().add(vpm);
				}
			}
			long e = System.currentTimeMillis();
			System.out.println(e - s);
		}
		return result;
	}

	@Override
	public synchronized Map<String, Object> getUserRoleData(Map<String, Object> context) {
		/*System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, sUserMapper.selectUserInfoWithRole(context));
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, sUserMapper.selectCountUserInfoWithRole(context));
		return result;
	}

	@Override
	public List<SRole> getRoleList(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
//		if (SUserCache.SUSER_TABLE.size() == 0) {
//			List<SUser> userList = sUserMapper.selectAll();
//			if (null != userList && userList.size() > 0) {
//				for (Iterator<SUser> it = userList.iterator(); it.hasNext();) {
//					SUser sUser = it.next();
//					SUserCache.SUSER_TABLE.put(sUser.getUserId(), sUser);
//				}
//			}
//		}
		List<SRole> roleList = sRoleMapper.selectAll();
//		List<VSRole> vsRoleList = new ArrayList<VSRole>();
//		
//		for (Iterator<SRole> it = roleList.iterator(); it.hasNext(); ) {
//			SRole sRole = it.next();
//			VSRole vsRole = new VSRole();
//			BeanUtils.copyProperties(sRole, vsRole);
//			vsRole.setCuName(SUserCache.SUSER_TABLE.get(vsRole.getCu()).getUserName());
//			vsRoleList.add(vsRole);
//		}
		return roleList;
	}

	@Override
	public Map<String, Object> getRoleData(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		Map<String, Object> result = new HashMap<String, Object>();
		Example example = new Example(SRole.class);
		Criteria criteria = example.createCriteria();
		if (context.containsKey(SYConst.USER_PARAM_NAME.ROLE_NAME)) {
			String roleName = (String) context.get(SYConst.USER_PARAM_NAME.ROLE_NAME);
			if (!StringUtil.isEmpty(roleName)) {
				criteria.andEqualTo(SYConst.USER_PARAM_NAME.ROLE_NAME, roleName);
			}
		}
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, sRoleMapper.selectCountByExample(example));
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, sRoleMapper.selectRoleDataWithCuName(context));
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> newRoleWithPermission(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		String roleName = (String) context.get(SYConst.USER_PARAM_NAME.ROLE_NAME);
		String roleDes = (String) context.get(SYConst.USER_PARAM_NAME.ROLE_DES);
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		Map<String, Object> rolePermission = (Map<String, Object>) context.get(SYConst.USER_PARAM_NAME.ROLE_PERMISSION);
		Date currentDate = new Date(System.currentTimeMillis());
		SRole srRecord = new SRole();
		srRecord.setRoleName(roleName);
		srRecord.setRoleDes(roleDes);
		srRecord.setCu(currentUserId);
		srRecord.setCt(currentDate);
		srRecord.setUu(currentUserId);
		srRecord.setUt(currentDate);
		int resultCount = sRoleMapper.insert(srRecord);
		Short newRoleId = srRecord.getRoleId();
		for (Iterator<String> it = rolePermission.keySet().iterator(); it.hasNext();) {
			Short permessionId = Short.valueOf(it.next());
			SRolePermission srpRecord = new SRolePermission();
			srpRecord.setPermissionId(permessionId);
			srpRecord.setRoleId(newRoleId);
			srpRecord.setCu(currentUserId);
			srpRecord.setCt(currentDate);
			srpRecord.setUu(currentUserId);
			srpRecord.setUt(currentDate);
			sRolePermissionMapper.insert(srpRecord);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> getRoleWithId(Short roleId) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		SRole sRole = sRoleMapper.selectByPrimaryKey(roleId);
		Example example = new Example(SRolePermission.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.USER_PARAM_NAME.ROLE_ID, roleId);
		List<SRolePermission> permissionList = sRolePermissionMapper.selectByExample(example);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.USER_PARAM_NAME.ROLE, sRole);
		result.put(SYConst.USER_PARAM_NAME.ROLE_PERMISSION, permissionList);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> getPermissionData(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		Map<String, Object> result = new HashMap<String, Object>();
		List<VSPermission> list =  sPermissionMapper.selectPermissionDataWithCuName(context);
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, sPermissionMapper.selectAll().size());
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS,list );
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> updateRoleWithPermission(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, Object> updateRoleMap =  (Map<String, Object>) context.get(SYConst.USER_PARAM_NAME.UPDATE_ROLE);
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		Date currentDate = new Date(System.currentTimeMillis());
		if (updateRoleMap.size() == 0) {
			result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
			result.put(SYConst.SERVICE.MESSAGE_BODY, message.MUI01);
			return result;
		}
		Map<String, Object> originalRoleMap =  (Map<String, Object>) context.get(SYConst.USER_PARAM_NAME.ORIGINAL_ROLE);
		Short roleId = ((Integer) originalRoleMap.get(SYConst.USER_PARAM_NAME.ROLE_ID)).shortValue();
		SRole sRole = null;
		if (updateRoleMap.containsKey(SYConst.USER_PARAM_NAME.ROLE_NAME)) {
			String newRoleName = (String) updateRoleMap.get(SYConst.USER_PARAM_NAME.ROLE_NAME);
			String oldRoleName = (String) originalRoleMap.get(SYConst.USER_PARAM_NAME.ROLE_NAME);
			if (!newRoleName.equals(oldRoleName)) {
				sRole = new SRole();
				sRole.setRoleId(roleId);
				sRole.setRoleName(newRoleName);
			}
		}
		if (updateRoleMap.containsKey(SYConst.USER_PARAM_NAME.ROLE_DES)) {
			String newRoleDes = (String) updateRoleMap.get(SYConst.USER_PARAM_NAME.ROLE_DES);
			String oldRoleDes = (String) originalRoleMap.get(SYConst.USER_PARAM_NAME.ROLE_DES);
			if (!newRoleDes.equals(oldRoleDes)) {
				if (null == sRole) {
					sRole = new SRole();
					sRole.setRoleId(roleId);
				}
				sRole.setRoleDes(newRoleDes);
			}
		}
		if (null != sRole) {
			sRole.setUu(currentUserId);
			sRole.setUt(currentDate);
			sRoleMapper.updateByPrimaryKeySelective(sRole);
		}
		if (updateRoleMap.containsKey(SYConst.USER_PARAM_NAME.PERMISSION)) {
			Example example = new Example(SRolePermission.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo(SYConst.USER_PARAM_NAME.ROLE_ID, roleId);
			sRolePermissionMapper.deleteByExample(example);
			Map<String, Object> updatePermission = (Map<String, Object>) updateRoleMap.get(SYConst.USER_PARAM_NAME.PERMISSION);
			for (Iterator<Entry<String, Object>> it = updatePermission.entrySet().iterator(); it.hasNext();) {
				Entry<String, Object> entry = it.next();
				Short permissionId = (Short) ((Integer) entry.getValue()).shortValue();
				SRolePermission sRolePermission = new SRolePermission();
				sRolePermission.setRoleId(roleId);
				sRolePermission.setPermissionId(permissionId);
				sRolePermission.setCu(currentUserId);
				sRolePermission.setCt(currentDate);
				sRolePermission.setUu(currentUserId);
				sRolePermission.setUt(currentDate);
				sRolePermissionMapper.insert(sRolePermission);
			}
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> deleteRole(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		Map<String, Object> result = new HashMap<String, Object>();
		Short roleId = (Short) context.get(SYConst.USER_PARAM_NAME.ROLE_ID);
		Example example = new Example(SUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.USER_PARAM_NAME.ROLE_ID, roleId);
		int userCount = sUserRoleMapper.selectCountByExample(example);
		if (userCount > 0) {
			throw new SYException(message.MUI02);
		}
		SRolePermission srp = new SRolePermission();
		srp.setRoleId(roleId);
		sRolePermissionMapper.delete(srp);
		sRoleMapper.deleteByPrimaryKey(roleId);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public List<SPermission> getPermissionList(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		List<SPermission> result = new ArrayList<SPermission>();
		SPermission s = new SPermission();
		s.setPermissionId((short) 0);
		s.setPermissionName(message.MUI03);
		result.add(s);
		if (context.containsKey(SYConst.USER_PARAM_NAME.PARENT_ID)) {
			Short parentId = (Short) context.get(SYConst.USER_PARAM_NAME.PARENT_ID);
			Example example = new Example(SPermission.class);
			example.createCriteria().andEqualTo(SYConst.USER_PARAM_NAME.PARENT_ID, parentId);
			result.addAll(sPermissionMapper.selectByExample(example));
			return result;
		}
		result.addAll(sPermissionMapper.selectAll());
		return result;
	}

	@Override
	public Map<String, Object> newPermission(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		SPermission sp = new SPermission();
		Date currentDate = new Date(System.currentTimeMillis());
		sp.setPermissionName((String) context.get(SYConst.USER_PARAM_NAME.PERMISSION_NAME));
		sp.setPermissionContext((String) context.get(SYConst.USER_PARAM_NAME.PERMISSION_CONTEXT));
		sp.setPermissionType((String) context.get(SYConst.USER_PARAM_NAME.PERMISSION_TYPE));
		sp.setParentId(Short.valueOf((String) context.get(SYConst.USER_PARAM_NAME.PARENT_ID)));
		sp.setCu((Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER));
		sp.setUu((Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER));
		sp.setCt(currentDate);
		sp.setUt(currentDate);
		sPermissionMapper.insert(sp);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		result.put(SYConst.USER_PARAM_NAME.PERMISSION_ID, sp.getPermissionId());
		return result;
	}

	@Override
	public Map<String, Object> getPermission(short permissionId) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		SPermission sp = sPermissionMapper.selectByPrimaryKey(permissionId);
		result.put(SYConst.USER_PARAM_NAME.PERMISSION, sp);
		return result;
	}

	@Override
	public Map<String, Object> deletePermission(short permissionId) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		Map<String, Object> result = new HashMap<String, Object>();
		Example example = new Example(SRolePermission.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.USER_PARAM_NAME.PERMISSION_ID, permissionId);
		int resutCount = sRolePermissionMapper.selectCountByExample(example);
		if (resutCount > 0) {
			throw new SYException(message.MUI04);
		}
		sPermissionMapper.deleteByPrimaryKey(permissionId);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> updatePermission(Map<String, Object> context) {
		System.out.print(this.hashCode());System.out.println(":"+Thread.currentThread().getId());
		Map<String, Object> result = new HashMap<String, Object>();
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		Date currentDate = new Date(System.currentTimeMillis());
		Map<String, Object> originalPermission 
			= (Map<String, Object>) context.get(SYConst.USER_PARAM_NAME.ORIGINAL_PERMISSION);
		Map<String, Object> updatePermission 
			= (Map<String, Object>) context.get(SYConst.USER_PARAM_NAME.UPDATE_PERMISSION);
		Integer tempPermissionId = (Integer) originalPermission.get(SYConst.USER_PARAM_NAME.PERMISSION_ID);
		SPermission sp = null;
		if (updatePermission.containsKey(SYConst.USER_PARAM_NAME.PERMISSION_NAME)
			&& !originalPermission.get(SYConst.USER_PARAM_NAME.PERMISSION_NAME)
				.equals(updatePermission.get(SYConst.USER_PARAM_NAME.PERMISSION_NAME))) {
			if (null == sp) {
				sp = new SPermission();
				sp.setPermissionId(tempPermissionId.shortValue());
			}
			sp.setPermissionName((String) updatePermission.get(SYConst.USER_PARAM_NAME.PERMISSION_NAME));
		}
		if (updatePermission.containsKey(SYConst.USER_PARAM_NAME.PERMISSION_CONTEXT)
				&& !originalPermission.get(SYConst.USER_PARAM_NAME.PERMISSION_CONTEXT)
					.equals(updatePermission.get(SYConst.USER_PARAM_NAME.PERMISSION_CONTEXT))) {
			if (null == sp) {
				sp = new SPermission();
				sp.setPermissionId(tempPermissionId.shortValue());
			}
			sp.setPermissionContext((String) updatePermission.get(SYConst.USER_PARAM_NAME.PERMISSION_CONTEXT));
		}
		if (updatePermission.containsKey(SYConst.USER_PARAM_NAME.PARENT_ID)) {
			String updateParentIdStr = (String) updatePermission.get(SYConst.USER_PARAM_NAME.PARENT_ID);
			Integer originalParentIdInt = (Integer) originalPermission.get(SYConst.USER_PARAM_NAME.PARENT_ID);
			if (! (Integer.valueOf(updateParentIdStr).intValue() == originalParentIdInt.intValue())) {
				if (null == sp) {
					sp = new SPermission();
					sp.setPermissionId(tempPermissionId.shortValue());
				}
				sp.setParentId(Short.valueOf(updateParentIdStr));
			}
		}
		if (null != sp) {
			sp.setUu(currentUserId);
			sp.setUt(currentDate);
			sPermissionMapper.updateByPrimaryKeySelective(sp);
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> newUserWithRole(Map<String, Object> context) throws NoSuchAlgorithmException {
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		String userName = (String) context.get(SYConst.USER_PARAM_NAME.USER_NAME);
		String nickName = (String) context.get(SYConst.USER_PARAM_NAME.NICK_NAME);
		String password = (String) context.get(SYConst.USER_PARAM_NAME.PASSWORD);
		String md5Password = MD5Util.encoderByMd5(password);
		Date currentDate = new Date(System.currentTimeMillis());
		SUser sUser = new SUser();
		sUser.setUserName(userName);
		sUser.setNickName(nickName);
		sUser.setPassword(md5Password);
		sUser.setCt(currentDate);
		sUser.setUt(currentDate);
		sUser.setCu(currentUserId);
		sUser.setUu(currentUserId);
		int resp = sUserMapper.insert(sUser);
		if (resp == 1 ) {
			SUserRole userRole = new SUserRole();
			userRole.setUserId(sUser.getUserId());
			userRole.setRoleId(Short.valueOf((String)context.get(SYConst.USER_PARAM_NAME.ROLE_ID)));
			userRole.setCt(currentDate);
			userRole.setUt(currentDate);
			userRole.setCu(currentUserId);
			userRole.setUu(currentUserId);
			sUserRoleMapper.insert(userRole);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> getUserWithId(Short userId) {
		SUser sUser = sUserMapper.selectByPrimaryKey(userId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.USER_PARAM_NAME.USER_ID, sUser.getUserId());
		result.put(SYConst.USER_PARAM_NAME.USER_NAME, sUser.getUserName());
		result.put(SYConst.USER_PARAM_NAME.NICK_NAME, sUser.getNickName());
		result.put(SYConst.USER_PARAM_NAME.PASSWORD, sUser.getPassword());
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> updateUser(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		@SuppressWarnings("unchecked") //告诉编译器忽略 unchecked 警告信息，如使用List，Map等未进行参数化产生的警告信息
		Map<String, Object> updateUser =  (Map<String, Object>) context.get(SYConst.USER_PARAM_NAME.UPDATE_USER);
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		Date currentDate = new Date(System.currentTimeMillis());
		if (updateUser.size() == 0) {
			result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
			result.put(SYConst.SERVICE.MESSAGE_BODY, message.MU01);
			return result;
		}
		@SuppressWarnings("unchecked")
		Map<String, Object> originalUser =  (Map<String, Object>) context.get(SYConst.USER_PARAM_NAME.ORIGINAL_USER);
		Short userID = ((Integer) originalUser.get(SYConst.USER_PARAM_NAME.USER_ID)).shortValue();
		SUser sUser = null;
		
		if (updateUser.containsKey(SYConst.USER_PARAM_NAME.USER_NAME)) {
			String newUserName = (String) updateUser.get(SYConst.USER_PARAM_NAME.USER_NAME);
			String oldUserName = (String) originalUser.get(SYConst.USER_PARAM_NAME.USER_NAME);
			if (!newUserName.equals(oldUserName)) {
				sUser = new SUser();
				sUser.setUserId(userID);
				sUser.setUserName(newUserName);
			}

		}
		if (updateUser.containsKey(SYConst.USER_PARAM_NAME.NICK_NAME)) {
			String newNickName = (String) updateUser.get(SYConst.USER_PARAM_NAME.NICK_NAME);
			String oldNickName = (String) originalUser.get(SYConst.USER_PARAM_NAME.NICK_NAME);
			if (!newNickName.equals(oldNickName)) {
				if (null == sUser) {
					sUser = new SUser();
					sUser.setUserId(userID);
				}
				sUser.setNickName(newNickName);
			}
		}
		if (updateUser.containsKey(SYConst.USER_PARAM_NAME.PASSWORD)) {
			String newPassword = (String) updateUser.get(SYConst.USER_PARAM_NAME.PASSWORD);
			String oldPassword = (String) originalUser.get(SYConst.USER_PARAM_NAME.PASSWORD);
			if (!newPassword.equals(oldPassword)) {
				if (null == sUser) {
					sUser = new SUser();
					sUser.setUserId(userID);
				}
				sUser.setPassword(newPassword);
			}
		}
		if (null != sUser) {
			sUser.setUu(currentUserId);
			sUser.setUt(currentDate);
			sUserMapper.updateByPrimaryKeySelective(sUser);
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> updateUserRole(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		Short userID = Short.parseShort((String) context.get(SYConst.USER_PARAM_NAME.USER_ID));
		Short roleID = Short.parseShort((String) context.get(SYConst.USER_PARAM_NAME.ROLE_ID));
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		Date currentDate = new Date(System.currentTimeMillis());
		Example example = new Example(SUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.USER_PARAM_NAME.USER_ID, userID);
		int resutCount = sUserRoleMapper.selectCountByExample(example);
		if (resutCount > 0) {
			sUserRoleMapper.deleteByExample(example);
			SUserRole sUserRole = new SUserRole();
			sUserRole.setUserId(userID);
			sUserRole.setRoleId(roleID);
			sUserRole.setUu(currentUserId);
			sUserRole.setUt(currentDate);
			sUserRole.setCu(currentUserId);
			sUserRole.setCt(currentDate);
			sUserRoleMapper.insertSelective(sUserRole);
			result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
			return result;
		}
		SUserRole sUserRole = new SUserRole();
		sUserRole.setUserId(userID);
		sUserRole.setRoleId(roleID);
		sUserRole.setUu(currentUserId);
		sUserRole.setUt(currentDate);
		sUserRole.setCu(currentUserId);
		sUserRole.setCt(currentDate);
		sUserRoleMapper.insertSelective(sUserRole);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> deleteUser(short userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Example example = new Example(SUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.USER_PARAM_NAME.USER_ID, userId);
		int resutCount = sUserRoleMapper.selectCountByExample(example);
		if (resutCount > 0) {
			throw new SYException(message.MU04);
		}
		sUserMapper.deleteByPrimaryKey(userId);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> deleteRole(short roleId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Example example = new Example(SUserRole.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.USER_PARAM_NAME.ROLE_ID, roleId);
		int resutCount = sUserRoleMapper.selectCountByExample(example);
		if (resutCount > 0) {
			throw new SYException(message.MU04);
		}
		sRoleMapper.deleteByPrimaryKey(roleId);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public List<Emnu> getPermissionType() {
		Example example = new Example(Emnu.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.EMNU.EMNU_TYPE, "PERMISSION_TYPE");
		List<Emnu> list = emnuMapper.selectByExample(example);
		return list;
	}

	@Override
	public Map<String, Object> getPermissionWithId(Short PermissionId) {
		SPermission selectByPrimaryKey = sPermissionMapper.selectByPrimaryKey(PermissionId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.USER_PARAM_NAME.PERMISSION, selectByPrimaryKey);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public List<SPermission> getAllPermissionType(Map<String, Object> context) {
		if (context.containsKey(SYConst.USER_PARAM_NAME.PARENT_ID)) {
			Short parentId = (Short) context.get(SYConst.USER_PARAM_NAME.PARENT_ID);
			Example example = new Example(SPermission.class);
			example.createCriteria().andEqualTo(SYConst.USER_PARAM_NAME.PARENT_ID, parentId);
			return sPermissionMapper.selectByExample(example);
		}
		return sPermissionMapper.selectAll();
	}

}
