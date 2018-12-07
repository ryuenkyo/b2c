package com.aisy.b2c.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.Emnu;
import com.aisy.b2c.pojo.SPermission;
import com.aisy.b2c.pojo.SRole;
import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.pojo.VPermissionMenu;

/**
 * 用户业务逻辑接口
 * 
 * 
 * @author cailongyang
 * @version 1.0.1
 *
 */
public interface IUserService {

	/**
	 * 取得用户列表信息 分页方式
	 * @param currentPage 当前页
	 * @param limit 记录数
	 * @param whereMap 条件容器
	 * @return 分页完成的用户列表数据
	 */
	public List<SUser> getUserListByPage(int currentPage, int limit, Map<String, Object> whereMap);
	
	public SUser selectByPrimaryKey(Short userId);
	
	/**
	 * 根据用户取得用户的权限
	 * 
	 * @param context 传参使用上下文
	 * @return 用户的菜单权限结构
	 */
	public Map<Short, VPermissionMenu> getPermissionMenuByUser(Short userId);
	
	/**
	 * 按层级取得所有权限
	 * 
	 * @return 所有权限数据
	 */
	public List<VPermissionMenu> getAllPermissionList();
	
	/**
	 * 取得带角色的用户数据  分页
	 * 
	 * @param context 查询条件
	 * @return 带角色的用户数据
	 */
	public Map<String, Object> getUserRoleData(Map<String, Object> context);
	
	/**
	 * 取得所有角色
	 * @param context 查询条件
	 * @return 所有角色数据
	 */
	public List<SRole> getRoleList(Map<String, Object> context);
	
	/**
	 * 取得角色数据  分页
	 * 
	 * @param context
	 * @return 按分页取得的角色数据
	 */
	public Map<String, Object> getRoleData(Map<String, Object> context);
	
	/**
	 * 新建带权限的角色
	 * @param context
	 * @return
	 */
	public Map<String, Object> newRoleWithPermission(Map<String, Object> context);
	
	/**
	 * 修改带权限的角色
	 * @param context
	 * @return
	 */
	public Map<String, Object> updateRoleWithPermission(Map<String, Object> context);
	
	/**
	 * 删除角色
	 * @param context
	 * @return
	 */
	public Map<String, Object> deleteRole(Map<String, Object> context);
	
	/**
	 * 根据角色ID取得角色详细信息
	 * 
	 * @param roleId
	 * @return
	 */
	public Map<String, Object> getRoleWithId(Short roleId);
	
	/**
	 * 取得权限数据  分页
	 * 
	 * @param context
	 * @return 按分页取得的角色数据
	 */
	public Map<String, Object> getPermissionData(Map<String, Object> context);
	
	public List<SPermission> getPermissionList(Map<String, Object> context);
	
	public Map<String, Object> newPermission(Map<String, Object> context);
	
	public Map<String, Object> getPermission(short permissionId);
	
	public Map<String, Object> deletePermission(short permissionId);
	
	public Map<String, Object> updatePermission(Map<String, Object> context);
	
	/**
	 * 新建带角色的用户
	 * @param context
	 * @return Map
	 * @throws NoSuchAlgorithmException 
	 */
	public Map<String, Object> newUserWithRole(Map<String, Object> context) throws NoSuchAlgorithmException;
	
	/**
	 * 根据用户ID取得角色详细信息
	 * @param userId
	 * @return Map
	 */
	public Map<String, Object> getUserWithId(Short userId);
	
	/**
	 * 修改用户
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> updateUser(Map<String, Object> context);
	
	/**
	 * 绑定用户角色
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> updateUserRole(Map<String, Object> context);
	
	/**
	 * 删除用户
	 * @param userId
	 * @return Map
	 */
	public Map<String, Object> deleteUser(short userId);
	
	/**
	 * 删除角色
	 * @param roleId
	 * @return Map
	 */
	public Map<String, Object> deleteRole(short roleId);
	
	/**
	 * 取得枚举数据
	 * @return List
	 */
	public List<Emnu> getPermissionType();
	
	/**
	 * 根据权限ID取得权限详细信息
	 * @param PermissionId
	 * @return Map
	 */
	public Map<String, Object> getPermissionWithId(Short PermissionId);
	
	/**
	 * 取得所有所有权限类型
	 * @param context
	 * @return List
	 */
	public List<SPermission> getAllPermissionType(Map<String, Object> context);
	
	
}
