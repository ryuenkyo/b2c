package com.aisy.b2c.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.model.RoleModel;
import com.aisy.b2c.pojo.SRole;
import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.service.IUserService;
import com.aisy.b2c.util.SYConst;

@Controller
@RequestMapping("/system/role")
public class RoleController {

	@Resource
	private IUserService userService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String roleListPage() {
		return "back/role/role_list";
	}
	
	/**
	 * 角色新增
	 * 
	 * @param roleModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.PUT)
	public Map<String, Object> newRole(
			@RequestBody RoleModel roleModel,
			HttpServletRequest request) {
		Map<String, Object> context = new HashMap<String, Object>();
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.ROLE_NAME, roleModel.roleName);
		context.put(SYConst.USER_PARAM_NAME.ROLE_DES, roleModel.roleDes);
		context.put(SYConst.USER_PARAM_NAME.ROLE_PERMISSION, roleModel.permission);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return userService.newRoleWithPermission(context);
	}
	
	/**
	 * 角色取得
	 * 
	 * @param roleModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.GET)
	public Map<String, Object> getRole(@RequestParam String roleId) {
		
		if (StringUtils.isEmpty(roleId)) {
			throw new SYException("角色ID必须输入");
		}
		
		return userService.getRoleWithId(Short.valueOf(roleId));
	}
	
	/**
	 * 角色更新
	 * 
	 * @param roleModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.POST)
	public Map<String, Object> updateRole(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return userService.updateRoleWithPermission(context);
	}
	
	/**
	 * 角色删除
	 * 
	 * @param roleModel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/info/{permissionId}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteRole(@PathVariable("permissionId") String permissionId) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.USER_PARAM_NAME.ROLE_ID, Short.valueOf(permissionId));
		return userService.deleteRole(context);
	}
	
	
	
	/**
	 * 取得所有角色数据（下拉列表使用)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/role.json", method=RequestMethod.GET)
	public List<SRole> getRoleData() {
		return userService.getRoleList(null);
	}

	/**
	 * 取得角色    分页
	 * 
	 * @param roleName 角色名
	 * @param page 当前页
	 * @param limit 每页限制数
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value="/roleList.json", method=RequestMethod.GET)
	public Map<String, Object> getRoleListData(
			String roleName,
			int page,
			int limit) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.USER_PARAM_NAME.ROLE_NAME, roleName);
		context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1) * limit);
		context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		return userService.getRoleData(context);
	}
	
	
}
