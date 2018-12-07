package com.aisy.b2c.dao;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.StorageProducts;
import com.aisy.b2c.pojo.VStorageProdcutDetail;

import tk.mybatis.mapper.common.Mapper;

public interface StorageProductsMapper extends Mapper<StorageProducts> {
	public List<VStorageProdcutDetail> selectStorageProdcutInfoByStorageId(String storageId);
	public List<VStorageProdcutDetail> selectStorageProdcutInfoExceptId(Map<String, Object> context);
	public int selectSortIndxeInfoByStorageId (String storageId);
	public int selectStorageSortIndxeInfo();
	public int selectStorageSortIndxeInfoById (String sortIndex);
}