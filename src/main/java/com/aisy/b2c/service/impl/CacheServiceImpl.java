package com.aisy.b2c.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aisy.b2c.cache.ClientCache;
import com.aisy.b2c.cache.CommonCache;
import com.aisy.b2c.cache.ProductCache;
import com.aisy.b2c.cache.SUserCache;
import com.aisy.b2c.dao.BrandMapper;
import com.aisy.b2c.dao.CategoryAttrMapper;
import com.aisy.b2c.dao.CategoryMapper;
import com.aisy.b2c.dao.ClientLevelMapper;
import com.aisy.b2c.dao.EmnuMapper;
import com.aisy.b2c.dao.LogisticsMapper;
import com.aisy.b2c.dao.OrderHeaderMapper;
import com.aisy.b2c.dao.PAttrNameMapper;
import com.aisy.b2c.dao.PAttrValueMapper;
import com.aisy.b2c.dao.PayMapper;
import com.aisy.b2c.dao.ProductAttrMapper;
import com.aisy.b2c.dao.ProductCategoryMapper;
import com.aisy.b2c.dao.ProductImageMapper;
import com.aisy.b2c.dao.ProductMapper;
import com.aisy.b2c.dao.RcDistrictMapper;
import com.aisy.b2c.dao.SPermissionMapper;
import com.aisy.b2c.dao.SRoleMapper;
import com.aisy.b2c.dao.SUserMapper;
import com.aisy.b2c.dao.SkuMapper;
import com.aisy.b2c.dao.StorageMapper;
import com.aisy.b2c.dao.StorageProductsMapper;
import com.aisy.b2c.pojo.Brand;
import com.aisy.b2c.pojo.Category;
import com.aisy.b2c.pojo.CategoryAttr;
import com.aisy.b2c.pojo.ClientLevel;
import com.aisy.b2c.pojo.Emnu;
import com.aisy.b2c.pojo.PAttrName;
import com.aisy.b2c.pojo.PAttrValue;
import com.aisy.b2c.pojo.Product;
import com.aisy.b2c.pojo.ProductAttr;
import com.aisy.b2c.pojo.ProductCategory;
import com.aisy.b2c.pojo.ProductImage;
import com.aisy.b2c.pojo.RcDistrict;
import com.aisy.b2c.pojo.SUser;
import com.aisy.b2c.pojo.Sku;
import com.aisy.b2c.pojo.Storage;
import com.aisy.b2c.pojo.StorageProducts;
import com.aisy.b2c.service.ICacheService;
import com.aisy.b2c.util.SYConst;

import tk.mybatis.mapper.entity.Example;

@Service
public class CacheServiceImpl implements ICacheService {
	
	@Resource
	CommonCache COMMON_CACHE;
	
	@Resource
	ProductCache PRODUCT_CACHE;
	
	@Resource
	EmnuMapper emnuMapper;
	
	@Resource
	PAttrNameMapper pAttrNameMapper;
	
	@Resource
	PAttrValueMapper pAttrValueMapper;
	
	@Resource
	CategoryMapper categoryMapper;
	
	@Resource
	CategoryAttrMapper categoryAttrMapper;
	
	@Resource
	BrandMapper brandMapper;
	
	@Resource
	ProductCategoryMapper productCategoryMapper;
	
	@Resource
	ProductImageMapper productImageMapper;
	
	@Resource
	ProductMapper productMapper;
	
	@Resource
	StorageMapper storageMapper;
	
	@Resource
	StorageProductsMapper storageProductsMapper;
	
	@Resource
	SkuMapper skuMapper;
	
	@Resource
	ProductAttrMapper productAttrMapper;
	
	@Resource
	RcDistrictMapper rcDistrictMapper;
	
	@Resource
	ClientLevelMapper clientLevelMapper;
	
	@Resource
	SUserMapper sUserMapper;
	
	@Resource
	SRoleMapper sRoleMapper;
	
	@Resource
	SPermissionMapper sPermissionMapper;
	
	@Resource
	OrderHeaderMapper orderHeaderMapper;
	
	@Resource
	PayMapper payMapper;
	
	@Resource
	LogisticsMapper logisticsMapper;
	
	@Override
	public void emnuCache() {
		COMMON_CACHE.EMNU.clear();
		List<Emnu> list = emnuMapper.selectAll();
		
		for (Iterator<Emnu> it = list.iterator(); it.hasNext();) {
			Emnu bean = it.next();
			if (COMMON_CACHE.EMNU.containsKey(bean.getEmnuType())) {
				Map<String, Emnu> typeMap = COMMON_CACHE.EMNU.get(bean.getEmnuType());
				typeMap.put(bean.getEmnuName(), bean);
			} else {
				Map<String, Emnu> typeMap = new LinkedHashMap<String, Emnu>();
				typeMap.put(bean.getEmnuName(), bean);
				COMMON_CACHE.EMNU.put(bean.getEmnuType(), typeMap);
			}
		}
	}

	@Override
	public void pAttrNameCache() {
		List<PAttrName> list = pAttrNameMapper.selectAll();
		PRODUCT_CACHE.P_ATTR_NAME.clear();
		for (Iterator<PAttrName> it = list.iterator(); it.hasNext();) {
			PAttrName bean = it.next();
			PRODUCT_CACHE.P_ATTR_NAME.put(String.valueOf(bean.getpAttrNameId()), bean);
		}
	}

	@Override
	public void pAttrValueCache() {
		List<PAttrValue> list = pAttrValueMapper.selectAll();
		PRODUCT_CACHE.P_ATTR_VALUE.clear();
		for (Iterator<PAttrValue> it = list.iterator(); it.hasNext();) {
			PAttrValue bean = it.next();
			PRODUCT_CACHE.P_ATTR_VALUE.put(String.valueOf(bean.getpAttrValueId()), bean);
		}
	}

	@Override
	public void categoryCache() {
		List<Category> list = categoryMapper.selectAll();
		PRODUCT_CACHE.CATEGORY.clear();
		PRODUCT_CACHE.CATEGORY_SORTINDEX.clear();
		PRODUCT_CACHE.CATEGORY_PARENT.clear();
		for (Iterator<Category> it = list.iterator(); it.hasNext();) {
			Category bean = it.next();
			PRODUCT_CACHE.CATEGORY.put(String.valueOf(bean.getCategoryId()), bean);
			
			PRODUCT_CACHE.CATEGORY_PARENT.put(bean.getCategoryId(), bean.getpCategoryId());
			
			if (PRODUCT_CACHE.CATEGORY_SORTINDEX.containsKey(String.valueOf(bean.getpCategoryId()))) {
				short sortIndex = PRODUCT_CACHE.CATEGORY_SORTINDEX.get(String.valueOf(bean.getpCategoryId()));
				short currentIndex = bean.getSortIndex();
				
				if (currentIndex > sortIndex) {
					PRODUCT_CACHE.CATEGORY_SORTINDEX.put(String.valueOf(bean.getpCategoryId()), currentIndex);
				}
				
			} else {
				PRODUCT_CACHE.CATEGORY_SORTINDEX.put(String.valueOf(bean.getpCategoryId()), bean.getSortIndex());
			}
		}
	}

	@Override
	public void categoryAttrCache() {
		List<CategoryAttr> list = categoryAttrMapper.selectAll();
		PRODUCT_CACHE.CATEGORY_ATTR.clear();
		for (Iterator<CategoryAttr> it = list.iterator(); it.hasNext();) {
			CategoryAttr bean = it.next();
			StringBuffer sbKey = new StringBuffer()
					.append(String.valueOf(bean.getCategoryId()))
					.append(SYConst.SYMBOL.OCTOTHORPE)
					.append(String.valueOf(bean.getpAttrNameId()));
			PRODUCT_CACHE.CATEGORY_ATTR.put(sbKey.toString(), bean);
		}
	}

	@Override
	public void brandCache() {
		List<Brand> list = brandMapper.selectAll();
		
		for (Iterator<Brand> it = list.iterator(); it.hasNext();) {
			Brand bean = it.next();
			PRODUCT_CACHE.BRAND.put(String.valueOf(bean.getBrandId()), bean);
		}
		
	}
	
	@Override
	public void productCategoryCache() {
		List<ProductCategory> list = productCategoryMapper.selectAll();
		PRODUCT_CACHE.CATEGORY_PRODUCT.clear();
		for (Iterator<ProductCategory> it = list.iterator(); it.hasNext();) {
			
			ProductCategory pc = it.next();
			Short categoryId = pc.getCategoryId();

			if (PRODUCT_CACHE.CATEGORY_PRODUCT.containsKey(String.valueOf(categoryId))) {
				Set<Short> listProduct = PRODUCT_CACHE.CATEGORY_PRODUCT.get(String.valueOf(categoryId));
				listProduct.add(pc.getProductId());
			} else {
				Set<Short> listProduct = new HashSet<Short>();
				listProduct.add(pc.getProductId());
				PRODUCT_CACHE.CATEGORY_PRODUCT.put(String.valueOf(categoryId), listProduct);
			}
			
		}
	}

	/* created start by YanqingLiu for product imageCache 2018-05-14 10:00:30 */
	@Override
	public void imageCache() {
		/* update start by cailongyang imageCache 2018-05-15 10:38:30 */
		List<ProductImage> list = productImageMapper.selectAll();
		PRODUCT_CACHE.PRODUCT_IMAGE.clear();
		for (Iterator<ProductImage> it = list.iterator(); it.hasNext();) {
			ProductImage bean = it.next();
			/* 设置key为productId */
			if (PRODUCT_CACHE.PRODUCT_IMAGE.containsKey(String.valueOf(bean.getProductId()))) {
				/* 根据prodcutId分类，将iamgeType为key，并将image其他属性存入Map */
				/* update start by YanqingLiu getProductId 2018-05-15 16:48:30 */
				Map<String, List<ProductImage>> imageMap = PRODUCT_CACHE.PRODUCT_IMAGE.get(String.valueOf(bean.getProductId()));
				/* update end by YanqingLiu getProductId 2018-05-15 16:48:30 */
				if (imageMap.containsKey(bean.getImageType())) {
					List<ProductImage> piList = imageMap.get(bean.getImageType());
					piList.add(bean);
				} else {
					List<ProductImage> piList = new ArrayList<ProductImage>();
					piList.add(bean);
					imageMap.put(bean.getImageType(), piList);
				}
			} else {
				Map<String, List<ProductImage>> imageMap = new LinkedHashMap<String, List<ProductImage>>();
				List<ProductImage> piList = new ArrayList<ProductImage>();
				piList.add(bean);
				imageMap.put(bean.getImageType(), piList);
				PRODUCT_CACHE.PRODUCT_IMAGE.put(String.valueOf(bean.getProductId()), imageMap);
			}
		}
		/* update end by cailongyang imageCache 2018-05-15 10:38:30 */
	}
	/* add end by YanqingLiu for product imageCache 2018-05-14 10:00:30 */

	@Override
	public void productCache() {
		PRODUCT_CACHE.PRODUCT.clear();
		List<Product> listResult = productMapper.selectAll();
		for (Iterator<Product> it = listResult.iterator(); it.hasNext();) {
			Product product = it.next();
			PRODUCT_CACHE.PRODUCT.put(String.valueOf(product.getProductId()), product);
		}
		
		PRODUCT_CACHE.SKU_PRODUCT.clear();
		List<Sku> listSkuResult = skuMapper.selectAll();
		for (Iterator<Sku> it = listSkuResult.iterator(); it.hasNext();) {
			Sku sku = it.next();
			PRODUCT_CACHE.SKU_PRODUCT.put(String.valueOf(sku.getSkuId()), sku);
		}
	}

	@Override
	public void storageCache() {
		PRODUCT_CACHE.STORAGE.clear();
		Example example = new Example(Storage.class);
		example.setOrderByClause("`sort_index` ASC");
		List<Storage> listResult = storageMapper.selectByExample(example);
		for (Iterator<Storage> it = listResult.iterator(); it.hasNext();) {
			Storage storage = it.next();
			PRODUCT_CACHE.STORAGE.put(String.valueOf(storage.getStorageId()), storage);
		}
	}

	@Override
	public void storageProductsCache() {
		PRODUCT_CACHE.STORAGE_PRODUCTS.clear();
		Example example = new Example(StorageProducts.class);
		example.setOrderByClause("`sort_index` ASC");
		List<StorageProducts> listResult = storageProductsMapper.selectByExample(example);
		for (Iterator<StorageProducts> it = listResult.iterator(); it.hasNext();) {
			StorageProducts storageProducts = it.next();
			StringBuffer sbKey = 
					new StringBuffer()
						.append(storageProducts.getStorageId())
						.append(SYConst.SYMBOL.OCTOTHORPE)
						.append(storageProducts.getProductId());
			PRODUCT_CACHE.STORAGE_PRODUCTS.put(sbKey.toString(), storageProducts);
		}
	}
	/* 根据product分类，将productID为key，分类list作为值 */
	@Override
	public void categoryProductCache() {
		List<ProductCategory> list = productCategoryMapper.selectAll();
		PRODUCT_CACHE.PRODUCT_CATEGORY.clear();
		for (Iterator<ProductCategory> it = list.iterator(); it.hasNext();) {
			ProductCategory pc = it.next();
			Short productId = pc.getProductId();
			if (PRODUCT_CACHE.PRODUCT_CATEGORY.containsKey(String.valueOf(productId))) {
				List<Short> listCategory = PRODUCT_CACHE.PRODUCT_CATEGORY.get(String.valueOf(productId));
				listCategory.add(pc.getCategoryId());
			} else {
				List<Short> listCategory = new ArrayList<Short>();
				listCategory.add(pc.getCategoryId());
				PRODUCT_CACHE.PRODUCT_CATEGORY.put(String.valueOf(productId), listCategory);
			}
		}
	}

	@Override
	public void attrProductCache() {
		List<ProductAttr> productAttrList = productAttrMapper.selectAll();
		PRODUCT_CACHE.ATTR_NAME_PRODUCT.clear();
		PRODUCT_CACHE.ATTR_VALUE_PRODUCT.clear();
		for (Iterator<ProductAttr> it = productAttrList.iterator(); it.hasNext(); ) {
			ProductAttr productAttr = it.next();
			
			String pAttrNameIdStr = String.valueOf(productAttr.getpAttrNameId());
			String pAttrValueIdStr = String.valueOf(productAttr.getpAttrValueId());
			
			Set<Short> productSetName;
			if (PRODUCT_CACHE.ATTR_NAME_PRODUCT.containsKey(pAttrNameIdStr)) {
				productSetName = PRODUCT_CACHE.ATTR_NAME_PRODUCT.get(pAttrNameIdStr);
			} else {
				productSetName = new HashSet<Short>();
				PRODUCT_CACHE.ATTR_NAME_PRODUCT.put(pAttrNameIdStr, productSetName);
			}
			productSetName.add(productAttr.getProductId());
			
			Set<Short> productSetValue;
			if (PRODUCT_CACHE.ATTR_VALUE_PRODUCT.containsKey(pAttrValueIdStr)) {
				productSetValue = PRODUCT_CACHE.ATTR_VALUE_PRODUCT.get(pAttrValueIdStr);
			} else {
				productSetValue = new HashSet<Short>();
				PRODUCT_CACHE.ATTR_VALUE_PRODUCT.put(pAttrValueIdStr, productSetValue);
			}
			productSetValue.add(productAttr.getProductId());
			
		}
	}

	@Override
	public void rcDistrictCache() {
		List<RcDistrict> list = rcDistrictMapper.selectAll();
		COMMON_CACHE.DISTRICT_MAP.clear();
		COMMON_CACHE.PROVICE_LIST.clear();
		COMMON_CACHE.CITY_MAP.clear();
		COMMON_CACHE.AREA_MAP.clear();
		for (Iterator<RcDistrict> it = list.iterator(); it.hasNext();) {
			RcDistrict rcDistrict = it.next();
			String idStr = String.valueOf(rcDistrict.getDistrictId());
			String pIdStr = String.valueOf(rcDistrict.getPid());
			
			COMMON_CACHE.DISTRICT_MAP.put(idStr, rcDistrict);
			
			if (1 == rcDistrict.getLevel()) {
				COMMON_CACHE.PROVICE_LIST.add(rcDistrict);
				if (!COMMON_CACHE.CITY_MAP.containsKey(idStr)) {
					List<RcDistrict> cityList = new ArrayList<RcDistrict>();
					COMMON_CACHE.CITY_MAP.put(idStr, cityList);
				}
				
				
			} else if (2 == rcDistrict.getLevel()) {
				
				if (!COMMON_CACHE.AREA_MAP.containsKey(idStr)) {
					List<RcDistrict> areaList = new ArrayList<RcDistrict>();
					COMMON_CACHE.CITY_MAP.put(idStr, areaList);
				}
				
				if (!COMMON_CACHE.CITY_MAP.containsKey(pIdStr)) {
					List<RcDistrict> cityList = new ArrayList<RcDistrict>();
					cityList.add(rcDistrict);
					COMMON_CACHE.CITY_MAP.put(idStr, cityList);
				} else {
					List<RcDistrict> cityList = COMMON_CACHE.CITY_MAP.get(pIdStr);
					cityList.add(rcDistrict);
				}
				
			} else if (3 == rcDistrict.getLevel()) {
				
				if (!COMMON_CACHE.AREA_MAP.containsKey(pIdStr)) {
					List<RcDistrict> areaList = new ArrayList<RcDistrict>();
					areaList.add(rcDistrict);
					COMMON_CACHE.AREA_MAP.put(idStr, areaList);
				} else {
					List<RcDistrict> areaList = COMMON_CACHE.AREA_MAP.get(pIdStr);
					areaList.add(rcDistrict);
				}
				
			}
		}
	}

	@Override
	public void clientLevelCache() {
		ClientCache.CLIENT_LEVEL.clear();
		ClientCache.CLIENT_LEVEL = clientLevelMapper.selectAll();
	}

	@Override
	public void userCache() {
		SUserCache.SUSER_LIST.clear();
		SUserCache.SUSER_LIST = sUserMapper.selectAll();
	}

	@Override
	public void roleCache() {
		SUserCache.ROLE_LIST.clear();
		SUserCache.ROLE_LIST = sRoleMapper.selectAll();
	}

	@Override
	public void permissionCache() {
		SUserCache.PERMISSION_LIST.clear();
		SUserCache.PERMISSION_LIST = sPermissionMapper.selectAll();
	}

	@Override
	public void userTableCache() {
		SUserCache.SUSER_TABLE.clear();
		List<SUser> list = sUserMapper.selectAll();
		Iterator<SUser> it = list.iterator();
		while (it.hasNext()) {
			SUser next = it.next();
			SUserCache.SUSER_TABLE.put(next.getUserId(), next);
		}
	}

	@Override
	public void MonitorInfoCache() {
		COMMON_CACHE.MONITORINFO_CACHE.clear();
		int totalOrderSum = orderHeaderMapper.selectCountOrderHeader();
		COMMON_CACHE.MONITORINFO_CACHE.put(SYConst.MONITORINFO.TOTAL_ORDER_SUM, totalOrderSum);
		int salesVolume = orderHeaderMapper.selectSumAmountOrderHeader();
		COMMON_CACHE.MONITORINFO_CACHE.put(SYConst.MONITORINFO.SALES_VOLUME, salesVolume);
	}
	
	@Override
	public void payCache() {
		COMMON_CACHE.PAY_LIST.clear();
		COMMON_CACHE.PAY_LIST = payMapper.selectAll();

	}

	@Override
	public void logisticsCache() {
		COMMON_CACHE.LOGISTICS_LIST.clear();
		COMMON_CACHE.LOGISTICS_LIST = logisticsMapper.selectAll();
	}
}
