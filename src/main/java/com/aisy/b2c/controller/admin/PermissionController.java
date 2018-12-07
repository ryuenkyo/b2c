package com.aisy.b2c.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aisy.b2c.cache.SUserCache;
import com.aisy.b2c.pojo.Emnu;
import com.aisy.b2c.pojo.SPermission;
import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.pojo.VPermissionMenu;
import com.aisy.b2c.service.IUserService;
import com.aisy.b2c.util.SYConst;

@Controller
@RequestMapping("/system/permission")
public class PermissionController {
	
	@Resource
	IUserService userService;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String getPermissionPage() {
		return "back/permission/permission_list";
	}
	
	@ResponseBody
	@RequestMapping(value="/menu.json", method=RequestMethod.GET)
	public Map<String, Object> getMenuData(HttpServletRequest request) {
		// 实例化返回对象
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		// 从session中取得当前登录用户信息
		SUser currentUser = 
				(SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
//		TODO 检查session登录用户
		Map<String, Object> context = new HashMap<String, Object>();		
		context.put(SYConst.SESSION.LOGIN_USER_ID, currentUser.getUserId());
		Short userId = (Short)context.get(SYConst.SESSION.LOGIN_USER_ID);
		System.out.println("CONTROLLER___ABCD"+userId);
		// 取得当前用户的权限
		Map<Short, VPermissionMenu> permissionMap = userService.getPermissionMenuByUser(userId);	
		// 将取得结果存入返回实例化对象
		result.put("permissionList", permissionMap);
		
		// 返回实例化对象
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.PUT)
	public Map<String, Object> newPermission(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request
			) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return userService.newPermission(context);
	}
	
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.POST)
	public Map<String, Object> updatePermission(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request
			) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return userService.updatePermission(context);
	}
	
	@ResponseBody
	@RequestMapping(value="/info/{permissionId}", method=RequestMethod.DELETE)
	public Map<String, Object> deletePermission(
			@PathVariable("permissionId") String permissionId
			) {
		return userService.deletePermission(Short.valueOf(permissionId));
	}
	
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.GET)
	public Map<String, Object> getPermission(@RequestParam("permissionId") String permissionId) {
		short pId = Short.valueOf(permissionId);
		return userService.getPermission(pId);
	}
	
	@ResponseBody
	@RequestMapping(value="/permissionType.json", method=RequestMethod.GET)
	public List<Emnu> getAllPermissionList() {
		
		List<Emnu> result = new ArrayList<Emnu>();
		Emnu e1 = new Emnu();
		e1.setEmnuId(Short.valueOf("1"));
		e1.setEmnuName("MENU");
		e1.setEmnuValue("权限类型-菜单");
		result.add(e1);
		return result;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/rolePermission.json", method=RequestMethod.GET)
	public List<VPermissionMenu> getPermissionTypeList() {
		return userService.getAllPermissionList();
	}
	
	@ResponseBody
	@RequestMapping(value="/permissionParent.json", method=RequestMethod.GET)
	public List<SPermission> getPermissionParentList() {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.USER_PARAM_NAME.PARENT_ID, new Integer(0).shortValue());
		return userService.getPermissionList(context);
	}
	
	
	/**
	 * 取得权限    分页
	 * 
	 * @param roleName 角色名
	 * @param page 当前页
	 * @param limit 每页限制数
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value="/permissionList.json", method=RequestMethod.GET)
	public Map<String, Object> getPermissionListData(
			int page,
			int limit) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1) * limit);
		context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		return userService.getPermissionData(context);
	}

	/**
	 * 权限名重复校验
	 * @param permissionName
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/permissionNameConfirm.json", method=RequestMethod.GET)
	public Map<String, Object> permissionNameConfirm(@RequestParam String permissionName) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Iterator<SPermission> it = SUserCache.PERMISSION_LIST.iterator(); it.hasNext();) {
			SPermission sPermission = it.next();
			if (sPermission.getPermissionName().equals(permissionName)) {
				result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
				return result;
			}
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
		return result;
	}
}
