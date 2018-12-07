package com.aisy.b2c.controller.admin;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aisy.b2c.cache.SUserCache;
import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.service.IUserService;
import com.aisy.b2c.util.Message;
import com.aisy.b2c.util.SYConst;

@Controller
@RequestMapping("/system/user")
public class UserController {
	
	@Resource
	IUserService userService;
	
	@Resource
	Message message;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String userListPage() {
		return "back/user/user_list";
	}
	
	@RequestMapping(value="/info/{userId}", method=RequestMethod.GET)
	public String userDetailPage(@PathVariable short userId, Model model) {
		SUser user = userService.selectByPrimaryKey(userId);
		
		//ModelAndView result = new ModelAndView("back/user/user_detail");
		model.addAttribute("user", user);
		return "back/user/user_detail";
	}
	
	/**
	 * 按分页取得用户数据
	 * 
	 * @param userName 用户名
	 * @param nickName 昵称
	 * @param roleId 角色ID
	 * @param page 页数
	 * @param limit 每页限制数
	 * @return json
	 */
	@ResponseBody
	@RequestMapping(value="/user.json", method=RequestMethod.GET)
	public Map<String, Object> getUserListData(
		String userName,
		String nickName,
		String roleId,
		int page,
		int limit) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.USER_PARAM_NAME.USER_NAME, userName);
		context.put(SYConst.USER_PARAM_NAME.NICK_NAME, nickName);
		context.put(SYConst.USER_PARAM_NAME.ROLE_ID, roleId);
		context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
		context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		return userService.getUserRoleData(context);
		
	}
	
	/**
	 * 用户新增
	 * @param context
	 * @param request
	 * @return Map
	 * @throws NoSuchAlgorithmException 
	 */
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.PUT)
	public Map<String, Object> newPermission(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) throws NoSuchAlgorithmException {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return userService.newUserWithRole(context);
	}
	
	/**
	 * 用户取得 修改页面回填数据
	 * @param userId
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.GET)
	public Map<String, Object> getUser(@RequestParam String userId) {
		if (StringUtils.isEmpty(userId)) {
			throw new SYException(message.MU11);
		}
		return userService.getUserWithId(Short.valueOf(userId));
	}
	
	/**
	 * 用户更新
	 * @param request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.POST)
	public Map<String, Object> updateUser(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return userService.updateUser(context);
	}
	
	/**
	 * 用户绑定角色更新
	 * @param context
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/roleinfo", method=RequestMethod.POST)
	public Map<String, Object> updateUserRole(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return userService.updateUserRole(context);
	}
	
	/**
	 * 用户删除
	 * @param userId 用户ID
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/info/{userId}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteUser(
			@PathVariable("userId") String userId
			) {
		return userService.deleteUser(Short.valueOf(userId));
	}
	
	/**
	 * 用户名重复校验
	 * @param userName
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/userNameConfirm.json", method=RequestMethod.GET)
	public Map<String, Object> userNameConfirm(@RequestParam String userName) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (Iterator<SUser> it = SUserCache.SUSER_LIST.iterator(); it.hasNext();) {
			SUser sUser = it.next();
			if (sUser.getUserName().equals(userName)) {
				result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
				return result;
			}
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.FAIL);
		return result;
	}
}
