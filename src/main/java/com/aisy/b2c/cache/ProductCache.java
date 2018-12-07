package com.aisy.b2c.cache;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.aisy.b2c.pojo.AdvertisementImage;
import com.aisy.b2c.pojo.Brand;
import com.aisy.b2c.pojo.Category;
import com.aisy.b2c.pojo.CategoryAttr;
import com.aisy.b2c.pojo.PAttrName;
import com.aisy.b2c.pojo.PAttrValue;
import com.aisy.b2c.pojo.Product;
import com.aisy.b2c.pojo.ProductImage;
import com.aisy.b2c.pojo.Sku;
import com.aisy.b2c.pojo.Storage;
import com.aisy.b2c.pojo.StorageProducts;
/**
 * 产品相关缓存
 * 
 * @author YanqingLiu
 *
 */
@Component
public class ProductCache {
	/**
	 * 属性名缓存
	 */
	public Map<String, PAttrName> P_ATTR_NAME = new LinkedHashMap<String, PAttrName>();
	/**
	 * 属性值缓存
	 */
	public Map<String, PAttrValue> P_ATTR_VALUE = new LinkedHashMap<String, PAttrValue>();
	/**
	 * 分类缓存
	 */
	public Map<String, Category> CATEGORY = new LinkedHashMap<String, Category>();
	/**
	 * 分类排序缓存
	 */
	public Map<String, Short> CATEGORY_SORTINDEX = new HashMap<String, Short>();
	/**
	 * 父级分类缓存
	 */
	public Map<Short, Short> CATEGORY_PARENT = new HashMap<Short, Short>();
	/**
	 * 分类对应属性缓存
	 */
	public Map<String, CategoryAttr> CATEGORY_ATTR = new LinkedHashMap<String, CategoryAttr>();
	/**
	 * 品牌缓存
	 */
	public Map<String, Brand> BRAND = new HashMap<String, Brand>();
	/**
	 * 分类对应产品缓存
	 */
	public Map<String, Set<Short>> CATEGORY_PRODUCT = new HashMap<String, Set<Short>>();
	/**
	 * 产品缓存
	 */
	public Map<String, Product> PRODUCT = new HashMap<String, Product>();
	/**
	 * 货架缓存
	 */
	public Map<String, Storage> STORAGE = new LinkedHashMap<String, Storage>();
	/**
	 * 货架商品缓存
	 */
	public Map<String, StorageProducts> STORAGE_PRODUCTS = new LinkedHashMap<String, StorageProducts>();
	/**
	 * 库存缓存
	 */
	public Map<String, Sku> SKU_PRODUCT = new LinkedHashMap<String, Sku>();
	/**
	 * 产品图片缓存
	 */
	public Map<String, Map<String, List<ProductImage>>> PRODUCT_IMAGE = new HashMap<String, Map<String, List<ProductImage>>>();
	/**
	 * 产品分类缓存
	 */
	public Map<String, List<Short>> PRODUCT_CATEGORY = new HashMap<String, List<Short>>();
	/**
	 * 属性名对应产品缓存
	 */
	public Map<String, Set<Short>> ATTR_NAME_PRODUCT = new HashMap<String, Set<Short>>();
	/**
	 * 属性值对应产品缓存
	 */
	public Map<String, Set<Short>> ATTR_VALUE_PRODUCT = new HashMap<String, Set<Short>>();
	/**
	 * 广告轮播缓存
	 */
	public Map<String, AdvertisementImage> ADVERTISEMENT_IMAGE = new HashMap<String, AdvertisementImage>();
}
