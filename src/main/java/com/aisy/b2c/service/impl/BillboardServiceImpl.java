package com.aisy.b2c.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.aisy.b2c.cache.CommonCache;
import com.aisy.b2c.dao.AdvertisementImageMapper;
import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.pojo.AdvertisementImage;
import com.aisy.b2c.pojo.Emnu;
import com.aisy.b2c.pojo.Product;
import com.aisy.b2c.pojo.ProductAttr;
import com.aisy.b2c.pojo.ProductCategory;
import com.aisy.b2c.pojo.ProductImage;
import com.aisy.b2c.pojo.VAdvertisementImage;
import com.aisy.b2c.service.IBillboardService;
import com.aisy.b2c.util.SYConst;

@Service
public class BillboardServiceImpl implements IBillboardService {
	
	@Resource
	AdvertisementImageMapper advertisementImageMapper;
	
	@Resource
	CommonCache COMMON_CACHE;

	@Override
	public Map<String, Object> getBillboardInfo(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		List<VAdvertisementImage> advertisementImageList = advertisementImageMapper.selectAdvertisement(context);
		Map<String, Emnu> map = COMMON_CACHE.EMNU.get(SYConst.EMNU.STATUS);
		Iterator<VAdvertisementImage> it = advertisementImageList.iterator();
		while (it.hasNext()) {
			VAdvertisementImage next = it.next();
			String statusName = map.get(next.getStatus()).getEmnuValue();
			next.setStatusName(statusName);
		}
		result.put(SYConst.PAGE_PARAM_NAME.RECORDS, advertisementImageList);
		result.put(SYConst.PAGE_PARAM_NAME.TOTAL, advertisementImageMapper.selectCountAdvertisement(context));
		return result;
	}

	@Override
	public Map<String, Object> newBillboard(Map<String, Object> context) {
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		Date currentDate = new Date(System.currentTimeMillis());
		Map<String, Object> billboardMap = (Map<String, Object>) context.get(SYConst.BILLBOARD.ADVERTISEMENT);
		Map<String, Object> imageMap = (Map<String, Object>) context.get(SYConst.PRODUCT_PARAM.IMAGE);
		String fileCoverName = (String) imageMap.get(SYConst.BILLBOARD.BILLBOARD);
		if (null == fileCoverName) {
			throw new SYException("图片缺失！");
		}
		AdvertisementImage advertisementImager = new AdvertisementImage();
		advertisementImager.setAdImgUrl("/front/assets/"+fileCoverName);
		advertisementImager.setAdvertisementUrl((String) billboardMap.get(SYConst.BILLBOARD.ADVERTISEMENT_URL));
		advertisementImager.setStatus((String) billboardMap.get(SYConst.PRODUCT_PARAM.P_STATUS));
		advertisementImager.setCu(currentUserId);
		advertisementImager.setCt(currentDate);
		advertisementImager.setUu(currentUserId);
		advertisementImager.setUt(currentDate);
		advertisementImageMapper.insert(advertisementImager);		
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> updateBillboard(Map<String, Object> context) {
		Short currentUserId = (Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USER);
		Date currentDate = new Date(System.currentTimeMillis());
		Map<String, Object> billboardMap = (Map<String, Object>) context.get(SYConst.BILLBOARD.ADVERTISEMENT);
		Map<String, Object> imageMap = (Map<String, Object>) context.get(SYConst.PRODUCT_PARAM.IMAGE);
		String fileCoverName = (String) imageMap.get(SYConst.BILLBOARD.BILLBOARD);
		if (null == fileCoverName) {
			throw new SYException("图片缺失！");
		}
		AdvertisementImage advertisementImager = new AdvertisementImage();
		advertisementImager.setAdvertisementId(Short.valueOf((String)billboardMap.get(SYConst.BILLBOARD.ADVERTISEMENT_ID)));
		advertisementImager.setAdImgUrl("/front/assets/"+fileCoverName);
		advertisementImager.setAdvertisementUrl((String) billboardMap.get(SYConst.BILLBOARD.ADVERTISEMENT_URL));
		advertisementImager.setStatus((String) billboardMap.get(SYConst.PRODUCT_PARAM.P_STATUS));
		advertisementImager.setUu(currentUserId);
		advertisementImager.setUt(currentDate);
		advertisementImageMapper.updateByPrimaryKeySelective(advertisementImager);		
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> deleteBillboard(String advertisementId) {
		Map<String, Object> result = new HashMap<String, Object>();
		advertisementImageMapper.deleteByPrimaryKey(advertisementId);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> changeBillboardStatus(Map<String, Object> context) {
		Date currentDate = new Date(System.currentTimeMillis());
		AdvertisementImage advertisementImage = new AdvertisementImage();
		advertisementImage.setAdvertisementId(Short.valueOf((String)context.get(SYConst.BILLBOARD.ADVERTISEMENT_ID)));
		advertisementImage.setUu((Short) context.get(SYConst.USER_PARAM_NAME.CURRENT_USERID));
		advertisementImage.setUt(currentDate);
		if (SYConst.EMNU.ACTIVATE.equals((String)context.get(SYConst.PRODUCT_PARAM.STATUS))) {
			advertisementImage.setStatus(SYConst.EMNU.INACTIVATE);
		}else {
			advertisementImage.setStatus(SYConst.EMNU.ACTIVATE);
		}
		advertisementImageMapper.updateByPrimaryKeySelective(advertisementImage);
		Map<String, Object> result = new HashMap<String, Object>(1);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}
	
}
