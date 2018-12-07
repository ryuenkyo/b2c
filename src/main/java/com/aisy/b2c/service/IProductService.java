package com.aisy.b2c.service;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.Brand;
import com.aisy.b2c.pojo.PAttrName;
import com.aisy.b2c.pojo.Product;
import com.aisy.b2c.pojo.Storage;
import com.aisy.b2c.pojo.VStorageProdcutDetail;

/**
 * 商品业务逻辑接口
 * 
 * 
 * @author cailongyang
 *
 */
public interface IProductService {
	
	/**
	 * 取得商品属性相关所有信息
	 * @param context
	 * @return
	 */
	public Map<String, Object> getAttrInfoAll(Map<String, Object> context);
	
	/**
	 * 新增商品属性名
	 * @param context
	 * @return
	 */
	public Map<String, Object> insertAttrName(Map<String, Object> context);
	
	/**
	 * 新增商品属性值
	 * @param context
	 * @return
	 */
	public Map<String, Object> insertAttrValue(Map<String, Object> context);
	
	/**
	 * 删除商品属性值
	 * @param context
	 * @return
	 */
	public Map<String, Object> deleteAttrValue(Map<String, Object> context);
	
	/**
	 * 取得商品分类相关信息
	 * @param context
	 * @return
	 */
	public Map<String, Object> getCategoryInfo(Map<String, Object> context);
	
	/**
	 * 新增商品分类以及分类下的属性
	 * @param context
	 * @return
	 */
	public Map<String, Object> newCategoryInfo(Map<String, Object> context);
	
	/**
	 * 取得商品分类以及分类下的属性
	 * @param context
	 * @return
	 */
	public Map<String, Object> getCategoryDetailInfo(short categoryId);
	
	/**
	 * 更新商品分类
	 * @param context
	 * @return
	 */
	public Map<String, Object> updateCategory(Map<String, Object> context);
	
	/**
	 * 取得状态源数据
	 * @param context
	 * @return
	 */
	public List<Map<String, Object>> getStatus(Map<String, Object> context);
	
	/**
	 * 取得品牌源数据
	 * @param context
	 * @return
	 */
	public List<Brand> getBrands(Map<String, Object> context);
	
	/**
	 * 商品新增
	 */
	public Map<String, Object> newProductInfo(Map<String, Object> context);
	
	/**
	 * 商品货架数据取得
	 */
	public Map<String, Object> getFrontStorageInfo(Map<String, Object> context);
	
	/* add start by liuzhaoyu add new service Method 2018-05-11 18:07:12 */
	/**
	 * 取得商品数据  分页
	 * 
	 * @param context
	 * @return 按分页取得的商品数据
	 */
	public Map<String, Object> getProductData(Map<String, Object> context);
	/* add end by liuzhaoyu add new service Method 2018-05-11 18:07:12 */
	
	public Map<String, Object> getSkuInfo(Map<String, Object> context);
	
	public Map<String, Object> getAttrValueByProductId(Short productId);
	
	public Map<String, Object> newSku(Map<String, Object> context);
	
	public Map<String, Object> getProductDetailData(Map<String, Object> context);
	
	/**
	 * 变更商品状态
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> changeProductStatus(Map<String, Object> context);

	/**
	 * 变更货架状态
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> changeStorageStatus(Map<String, Object> context);
	
	/**
	 * 货架数据取得
	 * @param context
	 * @return List
	 */
	public Map<String, Object> getStorageAll(Map<String, Object> context);
	
	/**
	 * 获取属性信息
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> getAttrInfo(String pAttrNameId);
	
	/**
	 * 修改属性信息
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> updateAttrName(Map<String, Object> context);
	
	/**
	 * 删除商品属性值
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> deleteAttrValue(String pAttrValueId);
	
	/**
	 * 删除商品属性名
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> deleteAttrName(String pAttrValueId);
	
	/**
	 * 取得所有商品属性值
	 * 用于编辑窗口数据回填
	 * @param context
	 * @return List
	 */
	public List<PAttrName> getAttrName(Map<String, Object> context);
	
	/**
	 * 分页获取所有品牌
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> getBrandAllByList(Map<String, Object> context);
	
	/**
	 * 品牌新增
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> newBrand(Map<String, Object> context);
	
	/**
	 * 品牌修改
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> updateBrand(Map<String, Object> context);
	
	/**
	 * 品牌删除
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> deleteBrand(String brandId);
	
	/**
	 * 货架删除
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> deleteStorage(String storageId);
	
	/* start by liuyanqing add new service Method for storage and storage product 2018-06-04 12:00:00 */
	/**
	 * 取得货架及货架商品信息
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> getStorageInfoAll(Map<String, Object> context);
	
	/**
	 * 新增货架及商品
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> newStorageWithProduct(Map<String, Object> context);
	
	/**
	 * 新增货架商品
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> newStorageProduct(Map<String, Object> context);
	
	/**
	 * 修改货架
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> updateStorage(Map<String, Object> context);
	
	/**
	 * 根据货架获取商品
	 * @param storageId
	 * @return Map
	 */
	public List<VStorageProdcutDetail> getStorageProdcutInfoByStorageId(String storageId);
	
	/**
	 * 根据货架取得货架商品信息且不包含当前货架商品信息
	 * @param storageId
	 * @return Map
	 */
	public List<VStorageProdcutDetail> getStorageProdcutInfoExceptId(Map<String, Object> context);
	/**
	 * 货架商品删除
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> deleteStorageProduct(String storageId, String productId);
	
	/**
	 * 货架顺序变更
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> changeStorageSortIndex(Map<String, Object> context);
	
	/**
	 * 变更货架内商品顺序
	 * @param context
	 * @return Map
	 */
	public Map<String, Object> changeStorageProdcutSortIndex(Map<String, Object> context);
	/* end by liuyanqing add new service Method for storage and storage product 2018-06-04 12:00:00 */

	/**
	 * 删除商品库存
	 * @param skuId
	 * @return
	 */
	public Map<String, Object> deleteSku(short skuId);
	
	/**
	 * 更新分类状态
	 */
	public Map<String, Object> changeCategoryStatus(Map<String, Object> context);

	/**
	 * 选择商品页面 商品查询 带出商品属性
	 * @param productId
	 * @return
	 */
	public Map<String, Object> getProductAttrNameValueInfo(short productId);
	
	/**
	 * 选择商品页面 条件查询
	 * @param context
	 * @return
	 */
	public List<Product> getProductNames(Map<String, Object> context);

	
	public Map<String, Object> updateAttrValue(Map<String, Object> context); 


	/**
	 * 修改属性时，通过id进行查询
	 * @param pAttrNameId
	 * @return Map
	 */
	
	public Map<String, Object> getAttrValueInfo (String pAttrNameId); 
	
	/**
	 * 广告轮询查询
	 * @param context
	 * @return
	 */
	public Map<String, Object> getAdImage(Map<String, Object> context);
	/**
	 * 更改分类排序
	 */
	public Map<String, Object> updateCategorySortIndex(Map<String, Object> context);
	
	/**
	 * 库存更新
	 * @param request
	 * @return Map
	 */
	public Map<String, Object> updateSku(Map<String, Object> context);

	Map<String, Object> getBrandWithId(Short brandId);

	
}
