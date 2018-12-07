package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.AdvertisementImage;
import com.aisy.b2c.pojo.VAdvertisementImage;

import tk.mybatis.mapper.common.Mapper;

/**
 * 广告轮播DAO
 * @author 刘笑楠
 *
 */
public interface AdvertisementImageMapper extends Mapper<AdvertisementImage> {
	
	/**
	 * 分页搜索轮播信息
	 * @param context
	 * @return List
	 */
	public List<VAdvertisementImage> selectAdvertisement (Map<String, Object> context);
	
	/**
	 * 搜索轮播信息条数
	 * @param context
	 * @return int
	 */
	public int selectCountAdvertisement (Map<String, Object> context);
}