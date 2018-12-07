package com.aisy.b2c.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.aisy.b2c.service.ICacheService;
import com.aisy.b2c.util.ApplicationContextUtil;

@Component
public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (null == event.getApplicationContext().getParent()) {
			ApplicationContextUtil.getInstance().init(event.getApplicationContext());
			
			ICacheService cacheService = ApplicationContextUtil.getInstance().getBean(ICacheService.class);
			
			cacheService.userCache();
			cacheService.roleCache();
			cacheService.permissionCache();
			cacheService.emnuCache();
			cacheService.pAttrNameCache();
			cacheService.pAttrValueCache();
			cacheService.categoryCache();
			cacheService.categoryAttrCache();
			cacheService.brandCache();
			cacheService.imageCache();
			cacheService.productCategoryCache();
			cacheService.productCache();
			cacheService.storageCache();
			cacheService.storageProductsCache();
			cacheService.categoryProductCache();
			cacheService.attrProductCache();
			cacheService.rcDistrictCache();
			cacheService.clientLevelCache();
			cacheService.userTableCache();
			cacheService.MonitorInfoCache();
			cacheService.payCache();
			cacheService.logisticsCache();
		}
	}

}
