package com.aisy.b2c.controller.admin;

import java.util.HashMap;
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

import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.pojo.VClientReferral;
import com.aisy.b2c.service.IClientService;
import com.aisy.b2c.util.SYConst;

/**
 * 客户controller
 * 
 * @author YanqingLiu
 *
 */

@Controller
@RequestMapping("/system/client")
public class ClientController {
	
	@Resource
	IClientService clientService;
	
	/**
	 * 返回客户视图页面
	 * @return "back/client/client_list"
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String clientListPage(
			Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return "back/client/client_list";
	}
	
	/**
	 * 返回等级视图页面
	 * @return "back/client/level_list"
	 */
	@RequestMapping(value="/level_list", method=RequestMethod.GET)
	public String levelListPage(
			Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return "back/client/level_list";
	}
	
	/**
	 * 返回积分视图页面
	 * @return "back/client/point_list"
	 */
	@RequestMapping(value="/point_list", method=RequestMethod.GET)
	public String pointListPage(
			Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return "back/client/point_list";
	}
	
	/**
	 * 返回推荐视图页面
	 * @return "back/client/referral_list"
	 */
	@RequestMapping(value="/referral_list", method=RequestMethod.GET)
	public String referralListPage(
			Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return "back/client/referral_list";
	}
	
	/**
	 * 按分页取得所有客户
	 * @param clientinfo 客户属性
	 * @param sortBy 排序关键字
	 * @param direction 排序方式
	 * @param page 页数
	 * @param limit 显示条数
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/clientList.json", method=RequestMethod.GET)
	public Map<String, Object> getclientListData(String clientinfo,
            @RequestParam(value="sortBy", required=false) String sortBy,
            @RequestParam(value="direction", required=false) String direction,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="limit", required=false) Integer limit) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.CLIENT_PARAM_NAME.CLIENT, clientinfo);
		if (null != page && null != limit) {
			context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
			context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		}
		context.put(SYConst.PAGE_PARAM_NAME.SORTBY, sortBy);
		context.put(SYConst.PAGE_PARAM_NAME.DIRECTION, direction);
		return clientService.getClientListData(context);
	}
	
	/**
	 * 取得所有等级
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/levelList.json", method=RequestMethod.GET)
	public Map<String, Object> getLevelListData(Map<String, Object> context) {
		return clientService.getLevelListData(context);
	}
	
	/**
	 * 等级名重复校验
	 * @param levelName
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/levelNameConfirm.json", method=RequestMethod.GET)
	public Map<String, Object> levelNameConfirm(@RequestParam String levelName) {
		return clientService.levelNameConfirm(levelName);
	}
	
	@ResponseBody
	@RequestMapping(value="/levelPointConfirm.json", method=RequestMethod.GET)
	public Map<String, Object> levelPointConfirm(@RequestParam Integer level) {
		return clientService.levelPointConfirm(level);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/postLevelName", method=RequestMethod.GET)
	public Map<String,Object> getLevelName(@RequestParam String level){
		return clientService.selectLevel(Short.valueOf(level));
	}
	
	
	
	/**
	 * 等级新增
	 * @param context
	 * @param request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/level/info", method=RequestMethod.PUT)
	public Map<String, Object> newLevel(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return clientService.newLevel(context);
	}
	
	/**
	 * 等级修改
	 * @param context
	 * @param request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/level/info", method=RequestMethod.POST)
	public Map<String, Object> updateLevel(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		return clientService.updateLevel(context);
	}
	
	/**
	 * 等级删除
	 * @param level
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/level/info/{level}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteLevel(
			@PathVariable("level") String level
			) {
		return clientService.deleteLevel(level);
	}

	/**
	 * 会员状态变更
	 * @param context
	 * @param request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/changeClientStatus.json", method=RequestMethod.POST)
	public Map<String, Object> changeClientStatus(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		return clientService.changeClientStatus(context);
	}
	
	/**
	 * 按分页取得所有客户积分
	 * @param clientinfo 客户属性
	 * @param sortBy 排序关键字
	 * @param direction 排序方式
	 * @param page 页数
	 * @param limit 显示条数
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/clientPointList.json", method=RequestMethod.GET)
	public Map<String, Object> getclientPointListData(String clientPoints,
            @RequestParam(value="sortBy", required=false) String sortBy,
            @RequestParam(value="direction", required=false) String direction,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="limit", required=false) Integer limit) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.CLIENT_PARAM_NAME.CLIENT_POINTS, clientPoints);
		if (null != page && null != limit) {
			context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
			context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		}
		context.put(SYConst.PAGE_PARAM_NAME.SORTBY, sortBy);
		context.put(SYConst.PAGE_PARAM_NAME.DIRECTION, direction);
		return clientService.getClientPointListData(context);
	}
	
	/**
	 * 按分页取得指定客户积分详情
	 * @param clientId 客户ID
	 * @param sortBy 排序关键字
	 * @param direction 排序方式
	 * @param page 页数
	 * @param limit 显示条数
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/clientPointDetailList.json/{clientId}", method=RequestMethod.GET)
	public Map<String, Object> getClientPointDetailList(
			@PathVariable("clientId") String clientId,
            @RequestParam(value="sortBy", required=false) String sortBy,
            @RequestParam(value="direction", required=false) String direction,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="limit", required=false) Integer limit) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.CLIENT_PARAM_NAME.CLIENT_ID, clientId);
		if (null != page && null != limit) {
			context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
			context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		}
		context.put(SYConst.PAGE_PARAM_NAME.SORTBY, sortBy);
		context.put(SYConst.PAGE_PARAM_NAME.DIRECTION, direction);
		return clientService.getClientPointDetailList(context);
	}
	
	/**
	 * 按分页取得所有客户推荐码
	 * @param clientinfo 客户属性
	 * @param sortBy 排序关键字
	 * @param direction 排序方式
	 * @param page 页数
	 * @param limit 显示条数
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/clientReferralList.json", method=RequestMethod.GET)
	public Map<String, Object> getclientReferralListData(
			VClientReferral clientReferral,
            @RequestParam(value="sortBy", required=false) String sortBy,
            @RequestParam(value="direction", required=false) String direction,
            @RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="limit", required=false) Integer limit) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.CLIENT_PARAM_NAME.CLIENT_NAME,clientReferral.getClientName());
		if (null != page && null != limit) {
			context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
			context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		}
		
		context.put(SYConst.PAGE_PARAM_NAME.SORTBY, sortBy);
		context.put(SYConst.PAGE_PARAM_NAME.DIRECTION, direction);
		return clientService.getClientReferralListData(context);
	}
	
	/**
	 * 按分页取得指定客户推荐码详情
	 * @param clientId 客户ID
	 * @param sortBy 排序关键字
	 * @param direction 排序方式
	 * @param page 页数
	 * @param limit 显示条数
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/clientReferralDetailList.json/{clientId}", method=RequestMethod.GET)
	public Map<String, Object> getClientReferralDetailList(
			@PathVariable("clientId") String clientId,
            @RequestParam(value="sortBy", required=false) String sortBy,
            @RequestParam(value="direction", required=false) String direction,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="limit", required=false) Integer limit) {
		Map<String, Object> context = new HashMap<String, Object>();
		context.put(SYConst.CLIENT_PARAM_NAME.CLIENT_ID, clientId);
		if (null != page && null != limit) {
			context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
			context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		}
		context.put(SYConst.PAGE_PARAM_NAME.SORTBY, sortBy);
		context.put(SYConst.PAGE_PARAM_NAME.DIRECTION, direction);
		return clientService.getClientReferralDetailList(context);
	}
	/**
	 * 修改等级，时要在此区间内
	 * @param Map
	 * @param request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/updateLevelPointConfirm.json", method=RequestMethod.POST)
	public Map<String, Object> updateLevelPointConfirm(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		return clientService.updateLevelPointConfirm(context);
	}
	
	
	
}
