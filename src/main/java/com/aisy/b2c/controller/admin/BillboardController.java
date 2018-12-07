package com.aisy.b2c.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.service.IBillboardService;
import com.aisy.b2c.util.AnnotationInterceptor.Auth;
import com.aisy.b2c.util.JsonUtil;
import com.aisy.b2c.util.Message;
import com.aisy.b2c.util.SYConst;

import net.sf.json.JSONObject;

/**
 * 广告轮播controller
 * 
 * @author YanqingLiu
 *
 */
@Controller
@RequestMapping("/system/billboard")
public class BillboardController {
	
	@Resource
	IBillboardService billoardService;
	
	@Resource
	Message message;
	
	/**
	 * 返回轮播视图
	 * @return "back/billboard/billboard_list"
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String billboardListPage(
			Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		return "back/billboard/billboard_list";
	}
	
	/**
	 * 按分页取得所有广告数据
	 * @param sortBy 排序关键字
	 * @param direction 排序方式
	 * @param page 页数
	 * @param limit 显示条数
	 * @return Map
	 */	
	@ResponseBody
	@RequestMapping(value="/billboard.json", method=RequestMethod.GET)
	public Map<String, Object> getBillboardList(
            @RequestParam(value="sortBy", required=false) String sortBy,
            @RequestParam(value="direction", required=false) String direction,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="limit", required=false) Integer limit) {
		Map<String, Object> context = new HashMap<String, Object>();
		if (null != page && null != limit) {
			context.put(SYConst.PAGE_PARAM_NAME.PAGE, (page - 1)*limit);
			context.put(SYConst.PAGE_PARAM_NAME.LIMIT, limit);
		}
		context.put(SYConst.PAGE_PARAM_NAME.SORTBY, sortBy);
		context.put(SYConst.PAGE_PARAM_NAME.DIRECTION, direction);
		return billoardService.getBillboardInfo(context);
	}

	/**
	 * 广告新增
	 * @param billboardInfo
	 * @param billboard
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/info", method=RequestMethod.POST)
	public Map<String, Object> newBillboard(
			@RequestParam(value="dataPut") String billboardInfo,
			@RequestParam(value="billboard") MultipartFile billboard,
			HttpServletRequest request
			) throws IOException {
		Map<String, Object> context = new HashMap<String, Object>();
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		JSONObject billBoardJson = JsonUtil.stringToObj(billboardInfo);
		/* 从billboardInfo取出各项数据  */
		String advertisementUrl = billBoardJson.getString(SYConst.BILLBOARD.ADVERTISEMENT_URL);
		String status = billBoardJson.getString(SYConst.PRODUCT_PARAM.P_STATUS);
		/* 定义MAP并将数据存放进去  */
		Map<String, Object> billboardMap = new HashMap<String, Object>();
		billboardMap.put(SYConst.BILLBOARD.ADVERTISEMENT_URL, advertisementUrl);
		billboardMap.put(SYConst.PRODUCT_PARAM.P_STATUS, status);
		context.put(SYConst.BILLBOARD.ADVERTISEMENT, billboardMap);
		/* 获取绝对路径  */
		String uploadRealPath = request.getSession().getServletContext().getRealPath(SYConst.UPLOADPATH.PRODUCT_IMAGE_PATH);
		/* 创建图片MAP */
		Map<String, Object> imageMap = new HashMap<String, Object>();
		/* 当图片不为空时， 将图片存入MAP */
		if (null != billboard) {
			File imageFile = new File(uploadRealPath, System.currentTimeMillis()+billboard.getOriginalFilename());
			FileUtils.copyInputStreamToFile(billboard.getInputStream(), imageFile);
			imageMap.put(SYConst.BILLBOARD.BILLBOARD, imageFile.getName());
		/* 否则抛出异常  */	
		} else {
			throw new SYException(message.MPI03);
		}
		/* 将图片MAP存入context */
		context.put(SYConst.PRODUCT_PARAM.IMAGE, imageMap);
		return billoardService.newBillboard(context);
	}
	
	/**
	 * 广告修改
	 * @param billboardInfo
	 * @param billboard
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/billboardUpdate/info", method=RequestMethod.POST)
	public Map<String, Object> updateBillboard(
			@RequestParam(value="dataPut") String billboardInfo,
			@RequestParam(value="billboard") MultipartFile billboard,
			HttpServletRequest request
			) throws IOException {
		Map<String, Object> context = new HashMap<String, Object>();
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USER, currentLoginUser.getUserId());
		JSONObject billBoardJson = JsonUtil.stringToObj(billboardInfo);
		/* 从billboardInfo取出各项数据  */
		String advertisementId = billBoardJson.getString(SYConst.BILLBOARD.ADVERTISEMENT_ID);
		String advertisementUrl = billBoardJson.getString(SYConst.BILLBOARD.ADVERTISEMENT_URL);
		String status = billBoardJson.getString(SYConst.PRODUCT_PARAM.P_STATUS);
		/* 定义MAP并将数据存放进去  */
		Map<String, Object> billboardMap = new HashMap<String, Object>();
		billboardMap.put(SYConst.BILLBOARD.ADVERTISEMENT_ID, advertisementId);
		billboardMap.put(SYConst.BILLBOARD.ADVERTISEMENT_URL, advertisementUrl);
		billboardMap.put(SYConst.PRODUCT_PARAM.P_STATUS, status);
		context.put(SYConst.BILLBOARD.ADVERTISEMENT, billboardMap);
		/* 获取绝对路径  */
		String uploadRealPath = request.getSession().getServletContext().getRealPath(SYConst.UPLOADPATH.PRODUCT_IMAGE_PATH);
		/* 创建图片MAP */
		Map<String, Object> imageMap = new HashMap<String, Object>();
		/* 当图片不为空时， 将图片存入MAP */
		if (null != billboard) {
			File imageFile = new File(uploadRealPath, System.currentTimeMillis()+billboard.getOriginalFilename());
			FileUtils.copyInputStreamToFile(billboard.getInputStream(), imageFile);
			imageMap.put(SYConst.BILLBOARD.BILLBOARD, imageFile.getName());
		/* 否则抛出异常  */	
		} else {
			throw new SYException(message.MPI03);
		}
		/* 将图片MAP存入context */
		context.put(SYConst.PRODUCT_PARAM.IMAGE, imageMap);
		return billoardService.updateBillboard(context);
	}
	
	/**
	 * 广告删除
	 * @param level
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/info/{advertisementId}", method=RequestMethod.DELETE)
	public Map<String, Object> deleteBillboard(
			@PathVariable("advertisementId") String advertisementId
			) {
		return billoardService.deleteBillboard(advertisementId);
	}

	/**
	 * 广告状态变更
	 * @param context
	 * @param request
	 * @return Map
	 */
	@ResponseBody
	@RequestMapping(value="/changeBillboardStatus.json", method=RequestMethod.POST)
	public Map<String, Object> changeBillboardStatus(
			@RequestBody Map<String, Object> context,
			HttpServletRequest request) {
		SUser currentLoginUser = (SUser) request.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		context.put(SYConst.USER_PARAM_NAME.CURRENT_USERID, currentLoginUser.getUserId());
		return billoardService.changeBillboardStatus(context);
	}
}

