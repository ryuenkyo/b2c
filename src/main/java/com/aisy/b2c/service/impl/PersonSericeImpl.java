package com.aisy.b2c.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.aisy.b2c.dao.ClientMapper;
import com.aisy.b2c.dao.DeliveryAddressMapper;
import com.aisy.b2c.dao.RcDistrictMapper;
import com.aisy.b2c.pojo.Client;
import com.aisy.b2c.pojo.DeliveryAddress;
import com.aisy.b2c.pojo.RcDistrict;
import com.aisy.b2c.pojo.SRolePermission;
import com.aisy.b2c.pojo.VDeliveryAddress;
import com.aisy.b2c.service.IPersonSerice;
import com.aisy.b2c.util.SYConst;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


@Service
public class PersonSericeImpl implements IPersonSerice{

	@Resource
	ClientMapper clientMapper;
	
	@Resource
	DeliveryAddressMapper deliveryAddressMapper;
	
	@Resource
	RcDistrictMapper rcDistrictMapper;
	
	
	
	// renweisong  2018-6-1  起始
	/**
	 * 根据ID取得用户的全部信息
	 */
	@Override
	public	List<Object> getclient (Short clientId) {
		//通过ID查找用户链接DAO
		Client client = clientMapper.selectByPrimaryKey(clientId);			
		List<Object> result = new ArrayList<Object>();
		result.add(client);	
		
		
		
		return result;
	}

	/**
	 * 根据用户ID取得用户地址，在用地址号找到RC表中的中文显示出来
	 */
	@Override
	public List<Object> getdeliveryAddresses(Short clientId) {
		List<Object> result = new ArrayList<Object>();
		//全查询rc 表
		List<RcDistrict> rcDistrict = rcDistrictMapper.selectAll();
		//带ID查询de表
		DeliveryAddress deliveryAddresses = deliveryAddressMapper.selectByPrimaryKey(clientId);
		//将de表中的街道字段传给新建的POJO，使得新的POJO中带有街道数据。
		VDeliveryAddress vdeliveryAddress = new VDeliveryAddress();
		vdeliveryAddress.setStreet(deliveryAddresses.getStreet());
		//循环找到想对应的ID，把汉字赋值。
		for (Iterator<RcDistrict> it = rcDistrict.iterator(); it.hasNext();) {
			RcDistrict getaddress = it.next();
			
			if (getaddress.getDistrictId() == deliveryAddresses.getpCode()) {
				vdeliveryAddress.setProviceName(getaddress.getDistrict());
				
			}
			if(getaddress.getDistrictId().equals(deliveryAddresses.getcCode())) {
				vdeliveryAddress.setCityName(getaddress.getDistrict());
				
			}
			if(getaddress.getDistrictId().equals(deliveryAddresses.getaCode())) {
				vdeliveryAddress.setAreaName(getaddress.getDistrict());
				result.add(vdeliveryAddress);
			}
			
			
		}
		return result;
	}

	/**
	 * 根据ID修改数据
	 * 
	 */

	public List<Object> upclient(Client form){
		if(form.getNickName() == "" && form.getNickName()== null) {
			
		}
		
		Example example = new Example(Client.class);		
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.PRODUCT_PARAM.CLIENT_ID, form.getClientId());
		clientMapper.updateByExampleSelective(form, example);
		
		return null;
	}
	// renweisong  2018-6-1  
}



