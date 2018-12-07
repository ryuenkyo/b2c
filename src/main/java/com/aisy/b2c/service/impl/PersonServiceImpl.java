package com.aisy.b2c.service.impl;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.stereotype.Service;

import com.aisy.b2c.dao.ClientLevelMapper;
import com.aisy.b2c.dao.ClientMapper;
import com.aisy.b2c.dao.ClientPointMapper;
import com.aisy.b2c.dao.ClientReferralMapper;
import com.aisy.b2c.dao.DeliveryAddressMapper;
import com.aisy.b2c.dao.EvaluationMapper;
import com.aisy.b2c.dao.OrderHeaderMapper;
import com.aisy.b2c.dao.OrderItemMapper;
import com.aisy.b2c.dao.PointManagementMapper;
import com.aisy.b2c.dao.PointRecordMapper;
import com.aisy.b2c.dao.RcDistrictMapper;
import com.aisy.b2c.dao.SOrderHeaderItemClientEmnuMapper;
import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.pojo.Client;
import com.aisy.b2c.pojo.ClientLevel;
import com.aisy.b2c.pojo.ClientPoint;
import com.aisy.b2c.pojo.ClientReferral;
import com.aisy.b2c.pojo.DeliveryAddress;
import com.aisy.b2c.pojo.Evaluation;
import com.aisy.b2c.pojo.OrderHeader;
import com.aisy.b2c.pojo.OrderItem;
import com.aisy.b2c.pojo.PointManagement;
import com.aisy.b2c.pojo.PointRecord;
import com.aisy.b2c.pojo.RcDistrict;
import com.aisy.b2c.pojo.VDeliveryAddress;
import com.aisy.b2c.pojo.VOrderHeaderItemClientEmnu;
import com.aisy.b2c.service.IPersonService;
import com.aisy.b2c.util.MD5Util;
import com.aisy.b2c.util.RandomUtil;
import com.aisy.b2c.util.SYConst;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;


@Service
public class PersonServiceImpl implements IPersonService{

	@Resource
	ClientMapper clientMapper;
	
	@Resource
	DeliveryAddressMapper deliveryAddressMapper;
	
	@Resource
	RcDistrictMapper rcDistrictMapper;
	
	@Resource
	ClientReferralMapper clientReferralMapper;
	
	@Resource
	OrderItemMapper orderItemMapper;
	
	@Resource
	SOrderHeaderItemClientEmnuMapper sOrderHeaderMapper;
	
	@Resource
	OrderHeaderMapper orderHeaderMapper;
	
	@Resource
	EvaluationMapper evaluationMapper;
	
	
	
	@Resource
	PointRecordMapper pointRecordMapper;
	
	@Resource
	PointManagementMapper pointManagementMapper;
	
	@Resource
	ClientPointMapper clientPointMapper;
	

	
	@Resource
	ClientLevelMapper clientLevelMapper;
	

	
	// renweisong  2018-6-1  起始
		/**
		 * 根据ID取得用户的全部信息
		 */
		@Override
		public	List<Object> getclient () {
			
			//从Session 里取出登陆时储存的用户ID
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			Short clientId = (Short) request.getSession().getAttribute(SYConst.PRODUCT_PARAM.CLIENT_ID);
			//根据主键ID查找用户资料
			Client client = clientMapper.selectByPrimaryKey(clientId);			
			List<Object> result = new ArrayList<Object>();			
			result.add(client);	
			
			
			
			return result;
		}
		/**
		 * 根据ID查询用户等级
		 */
		public	List<Object> getclientgrade () {
			//从Session 里取出登陆时储存的用户ID
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			Short clientId = (Short) request.getSession().getAttribute(SYConst.PRODUCT_PARAM.CLIENT_ID);
			//根据主键ID查找用户资料
			Client client = clientMapper.selectByPrimaryKey(clientId);	
			List<Object> result = new ArrayList<Object>();	
			//全查询等级表
			List<ClientLevel> grade =	clientLevelMapper.selectAll();
			//全查询积分表
			List<ClientPoint> all = clientPointMapper.selectAll();
			//遍历积分表
			for (Iterator<ClientPoint> it = all.iterator(); it.hasNext();) {
				ClientPoint clientPoint = it.next();
				//判断积分表里的用户ID和当前登陆的用户ID是否相同
				if(clientPoint.getClientId().equals(clientId)) {
					//循环用户等级表
					for (Iterator<ClientLevel> itt = grade.iterator(); itt.hasNext();) {
						ClientLevel clientLevel = itt.next();
						//判断用户积分表里的积分值和用户等级里的积分值
							if(clientPoint.getPoint()<=clientLevel.getLevelPoint()) {
								//如果条件成立就把等级表中的等级ADD，然后结束循环。
								result.add(clientLevel.getLevelName());
								break;
							}	
					}
					
				}
				
			}
		
			return result;
		}
		/**
		 * 根据用户ID取得用户地址，在用地址号找到RC表中的中文显示出来
		 */
		@Override
		public List<Object> getdeliveryAddresses() {
			
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			Short clientId = (Short) request.getSession().getAttribute(SYConst.PRODUCT_PARAM.CLIENT_ID);
			
			String  newvd =null;
			List<Object> result = new ArrayList<Object>();
			//全查询RcDistrict 表
			List<RcDistrict> rcDistrict = rcDistrictMapper.selectAll();
		    //全查询DeliveryAddress表
			List<DeliveryAddress> allid	=deliveryAddressMapper.selectAll();	
			
			VDeliveryAddress vdeliveryAddress = new VDeliveryAddress();
			
			
			//循环用户地址
			for (Iterator<DeliveryAddress> itt = allid.iterator(); itt.hasNext();) {
				
				DeliveryAddress deliveryAddresses = itt.next();
				//地址的用户ID是否等于当前用户ID
				if(deliveryAddresses.getClientId() == clientId) {
					//如果等于就将详细地址Street，赋值过去
					vdeliveryAddress.setStreet(deliveryAddresses.getStreet());
					VDeliveryAddress vdeliveryAddre= new VDeliveryAddress(); 
					//循环地址表
					for (Iterator<RcDistrict> it = rcDistrict.iterator(); it.hasNext();) {
									
						RcDistrict getaddress = it.next();
						//判断用户地址的省值是否等于地址表中的DistrictId
						if (getaddress.getDistrictId() == deliveryAddresses.getpCode()) {
							//如果等于就将这个ID下的中文赋给ProviceName
							vdeliveryAddre.setProviceName(getaddress.getDistrict());
							System.out.println(vdeliveryAddre.getProviceName());
							
						}
						//同上判断市级
						if(getaddress.getDistrictId().equals(deliveryAddresses.getcCode())) {
							vdeliveryAddre.setCityName(getaddress.getDistrict());
							
						}
						//判断区级
						if(getaddress.getDistrictId().equals(deliveryAddresses.getaCode())) {
							
							vdeliveryAddre.setAreaName(getaddress.getDistrict());
							vdeliveryAddre.setAddressId(deliveryAddresses.getAddressId());
							vdeliveryAddre.setReciveName(deliveryAddresses.getReciveName());
							vdeliveryAddre.setTelphone(deliveryAddresses.getTelphone());
							vdeliveryAddre.setStreet(deliveryAddresses.getStreet());
							vdeliveryAddre.setIsDefault(deliveryAddresses.getIsDefault());
							result.add(vdeliveryAddre);
							//新new一个，防止二次循环的时候地址值覆盖。
							vdeliveryAddre= new VDeliveryAddress();
						}	
						
					}
					
					
				}
				
			}
			
			
			
					
			
			return result;
		}

		/**
		 * 根据ID修改数据
		 * 
		 */
		@Override
		public List<Object> upclient(Client form){			
			if(form.getNickName() == "" && form.getNickName()== null) {
				
			}
			System.out.println(form.getNickName());
			String headerImg = form.getHeaderImg();
			String replaceAll = headerImg.replaceAll("\"","");
			System.out.println(headerImg);
			form.setHeaderImg(replaceAll);
			System.out.println(replaceAll);
			Example example = new Example(Client.class);		
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo(SYConst.PRODUCT_PARAM.CLIENT_ID, form.getClientId());
			clientMapper.updateByExampleSelective(form, example);
			
			return null;
		}
		// renweisong  2018-6-1  

		
		
		
		/**
		 * 一级地址全查询 
		 */
		@Override
		public List<Object> getrcdistrict() {
			List<RcDistrict> rcDistrict = rcDistrictMapper.selectAll();
			List<Object> result = new ArrayList<Object>();
			for (Iterator<RcDistrict> it = rcDistrict.iterator(); it.hasNext();) {
				RcDistrict district = it.next();
				
				if(district.getLevel() == 1) {			
					result.add(district);
					
				}
		 
			}

			return result;
		}
		
		/**
		 * 根据一级地址ID查二级省地址
		 */
		@Override
		public List<RcDistrict> getrcdistrictc(Short districtId) {
			System.out.println(districtId);
			List<RcDistrict> all = rcDistrictMapper.selectAll();		
			List<RcDistrict> result = new ArrayList<RcDistrict>();		
			for (Iterator<RcDistrict> it = all.iterator(); it.hasNext();) {
				RcDistrict district = it.next();
				if(district.getPid() == districtId) {
					result.add(district);
				}	
			}
			return result;
		}

		@Override
		public List<String> getrcat(String district) {
			List<RcDistrict> all = rcDistrictMapper.selectAll();
			List<String> result = new ArrayList<String>();	
			for (Iterator<RcDistrict> it = all.iterator(); it.hasNext();) {
				RcDistrict trat = it.next();
				
				if(trat.getDistrict().equals(district)) {
					for (int i = 0; i < all.size(); i++) {
						if(trat.getDistrictId().equals(all.get(i).getPid())) {
							
						
							result.add(all.get(i).getDistrict());
						}
						
						
					}
					
				}
					
			}	
			
			return result;
		}

		@Override
		public List<Object> delivery(VDeliveryAddress from) {
			
			
			
		    boolean isdefault =false;
			List<RcDistrict> rcDistrict = rcDistrictMapper.selectAll();
			DeliveryAddress deliveryAddress = new VDeliveryAddress();
			for (Iterator<RcDistrict> it = rcDistrict.iterator(); it.hasNext();) {
				RcDistrict id =it.next();
				if(id.getDistrict().equals(from.getCityName())) {
					deliveryAddress.setcCode(id.getDistrictId());				
				}
				if(id.getDistrict().equals(from.getAreaName())) {
					deliveryAddress.setaCode(id.getDistrictId());
				}
			}
			deliveryAddress.setZipCode(from.getZipCode());
			deliveryAddress.setStreet(from.getStreet());
			deliveryAddress.setClientId(from.getClientId());
			deliveryAddress.setReciveName(from.getReciveName());
			deliveryAddress.setpCode(from.getpCode());
			deliveryAddress.setTelphone(from.getTelphone());
			deliveryAddress.setTelphoneBackup(from.getTelphone());
			deliveryAddress.setIsDefault(isdefault);
			deliveryAddress.setCu(from.getClientId());
			deliveryAddress.setUu(from.getClientId());
			
			deliveryAddressMapper.insert(deliveryAddress);
			return null;
		}
		
		@Override
		public Object datadelivery(DeliveryAddress addressId) {
			deliveryAddressMapper.delete(addressId);
			return null;
		}

		@Override
		public Object getpassword(Client password) {
			Boolean status = true;
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			Short clientId = (Short) request.getSession().getAttribute(SYConst.PRODUCT_PARAM.CLIENT_ID);
			Client client =	clientMapper.selectByPrimaryKey(clientId);
			try {
				String md5Password = MD5Util.encoderByMd5(password.getPassword());
				if(client.getPassword().equals(md5Password)) {
					status = true;
				}else {
					status = false;
				}
				
				
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				
			
			return status;
		}

		/**
		 * 修改用户密码
		 */

		@Override
		public Object uppassword(Client password) {	
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			Short clientId = (Short) request.getSession().getAttribute(SYConst.PRODUCT_PARAM.CLIENT_ID);
			
			try {
				String md5Password = MD5Util.encoderByMd5(password.getPassword());
				password.setPassword(md5Password);
				Example example = new Example(Client.class);		
				Criteria criteria = example.createCriteria();
				criteria.andEqualTo(SYConst.PRODUCT_PARAM.CLIENT_ID, clientId);
				clientMapper.updateByExampleSelective(password, example);
				
				
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			return null;
		}

		@Override
		public Object upisdefault(Short addressId) {
			// TOBO 从sission里取出当前用户ID
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			Short clientId = (Short) request.getSession().getAttribute(SYConst.PRODUCT_PARAM.CLIENT_ID);
			// 查询这个用户下的所有地址
			List<DeliveryAddress> deliveryAddresses = deliveryAddressMapper.selectAll();
			for (Iterator<DeliveryAddress> it = deliveryAddresses.iterator(); it.hasNext();) {
				DeliveryAddress isdefault = it.next();
				if(isdefault.getClientId() == clientId) {
					isdefault.setIsDefault(false);					
					Example example = new Example(DeliveryAddress.class);		
					Criteria criteria = example.createCriteria();
					criteria.andEqualTo(SYConst.PRODUCT_PARAM.ADDRESS_ID, isdefault.getAddressId());
					deliveryAddressMapper.updateByExampleSelective(isdefault, example);			
				}
			}
			List<DeliveryAddress> newisdefault = deliveryAddressMapper.selectAll();
			for (Iterator<DeliveryAddress> itt = newisdefault.iterator(); itt.hasNext();) {
				DeliveryAddress isdefault = itt.next();
				if(isdefault.getAddressId() == addressId) {
					isdefault.setIsDefault(true);
					Example example = new Example(DeliveryAddress.class);		
					Criteria criteria = example.createCriteria();
					criteria.andEqualTo(SYConst.PRODUCT_PARAM.ADDRESS_ID, addressId);
					deliveryAddressMapper.updateByExampleSelective(isdefault, example);		
				}
			}
			return null;
		}
		
		
		@Override
		public VDeliveryAddress upisdefaultadd(VDeliveryAddress addressId) {	
			DeliveryAddress deliveryAddress = deliveryAddressMapper.selectByPrimaryKey(addressId);	
			List<RcDistrict> rcDistrict = rcDistrictMapper.selectAll();
			VDeliveryAddress vdeliveryAddress = new VDeliveryAddress ();
			for (Iterator<RcDistrict> it = rcDistrict.iterator(); it.hasNext();) {
				RcDistrict rcDistrictadd =it.next();
				if(deliveryAddress.getpCode() == rcDistrictadd.getDistrictId()) {
					vdeliveryAddress.setProviceName(rcDistrictadd.getDistrict());							
					vdeliveryAddress.setpCode(rcDistrictadd.getDistrictId());							
				}
				if(deliveryAddress.getcCode().equals(rcDistrictadd.getDistrictId())) {
					vdeliveryAddress.setCityName(rcDistrictadd.getDistrict());
				}
				if(deliveryAddress.getaCode().equals(rcDistrictadd.getDistrictId()) ) {
					vdeliveryAddress.setAreaName(rcDistrictadd.getDistrict());
					vdeliveryAddress.setReciveName(deliveryAddress.getReciveName());
					vdeliveryAddress.setAddressId(deliveryAddress.getAddressId());
					vdeliveryAddress.setTelphone(deliveryAddress.getTelphone());
					vdeliveryAddress.setStreet(deliveryAddress.getStreet());
					vdeliveryAddress.setZipCode(deliveryAddress.getZipCode());
					
				}
			}
			return vdeliveryAddress;
		}
		
		
		
		/**
		 * 地址修改
		 */
		@Override
		public VDeliveryAddress updeliveryaddress(VDeliveryAddress form) {
			DeliveryAddress DeliveryAddress = new DeliveryAddress();
			List<RcDistrict> rcDistrict = rcDistrictMapper.selectAll();
			for (Iterator<RcDistrict> it = rcDistrict.iterator(); it.hasNext();) {
				RcDistrict all =it.next();
				if(all.getDistrict().equals(form.getCityName())) {
					DeliveryAddress.setcCode(all.getDistrictId());			
				}
				if(all.getDistrict().equals(form.getAreaName())) {
					DeliveryAddress.setaCode(all.getDistrictId());	
					DeliveryAddress.setReciveName(form.getReciveName());
					DeliveryAddress.setTelphone(form.getTelphone());
					DeliveryAddress.setpCode(form.getpCode());
					DeliveryAddress.setStreet(form.getStreet());
					DeliveryAddress.setZipCode(form.getZipCode());
				}
			}
			
			
			Example example = new Example(DeliveryAddress.class);		
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo(SYConst.PRODUCT_PARAM.ADDRESS_ID, form.getAddressId());	
			deliveryAddressMapper.updateByExampleSelective(DeliveryAddress, example);
			
			
			return null;
		}
		
		
		/**
		 *积分详情查询
		 */
		
		@Override
		public List<Object> getpoints() {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			Short clientId = (Short) request.getSession().getAttribute(SYConst.PRODUCT_PARAM.CLIENT_ID);
			
			List<Object> result = new ArrayList<Object>();	
			List<PointRecord> all =	pointRecordMapper.selectAll();	
			List<PointManagement> pmid = pointManagementMapper.selectAll();
			
			for (Iterator<PointRecord> it = all.iterator(); it.hasNext();) {
				PointRecord pointRecord = it.next();	
					if(pointRecord.getClientId().equals(clientId)) {
						for (Iterator<PointManagement> itt = pmid.iterator(); itt.hasNext();) {
							PointManagement Management = itt.next();
							if(pointRecord.getPmId() == Management.getPmId()) {
								Management.setCt(pointRecord.getCt());			
								result.add(Management);					
							}
						}
					}
				
				
				
				
				
			}		
			return result;
		}
		/**
		 * 总积分查询
		 */
		@Override
		public List<Object> getclientPoint() {
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			Short clientId = (Short) request.getSession().getAttribute(SYConst.PRODUCT_PARAM.CLIENT_ID);
			List<Object> result = new ArrayList<Object>();	
			ClientPoint clientPoint = clientPointMapper.selectByPrimaryKey(clientId);
			result.add(clientPoint.getPoint());
			return result;
		}
		
		/**
		 * 用户名查询是否已有
		 */
		
		public Boolean getuser(String user){
			Boolean status = null;
			List<Client> all = clientMapper.selectAll();
			for (Iterator<Client> it = all.iterator(); it.hasNext();) {
				Client client  =it.next();
				if(client.getClientName().equals(user)) {
					status = false;
					break;
				}else {
					
					status = true ;
					
				 }
				
			}
			return status;
			
		}
		
		/**
		 * 推荐码查询
		 */
		public Boolean referralCode(String referralCode){
			Boolean status = true;
			List<ClientReferral> all = clientReferralMapper.selectAll();
			for (Iterator<ClientReferral> it = all.iterator(); it.hasNext();) {
				ClientReferral clientReferral  =it.next();
				if(clientReferral.getReferralCode().equals(referralCode)) {				
					if(clientReferral.getIsAvailable() == false) {
						status = true;
						break;
					}else {
						status = false;
						break;
					}												
				}else {
					status = false ;
				}			
					
			
				
			}
			return status;
			
		}

		@Override
		public Boolean inclient(Map<String, Object>  context) {
			Boolean judge =true;
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			String codees = (String) request.getSession().getAttribute(SYConst.PRODUCT_PARAM.CHECKCODE);
			String codename = (String) context.get(SYConst.PRODUCT_PARAM.CODENAME);
			String clientName = (String) context.get(SYConst.CLIENT_PARAM_NAME.CLIENT_NAME);
			String nickName = (String) context.get(SYConst.CLIENT_PARAM_NAME.CLIENT_NICK_NAME);
			String telphone = (String) context.get(SYConst.CLIENT_PARAM_NAME.TELPHONE);
			String password = (String) context.get(SYConst.CLIENT_PARAM_NAME.PASSWORD);
			String age = (String) context.get(SYConst.CLIENT_PARAM_NAME.AGE);
			String headerImg = "5190085B81F8B0209B1FAD04D8AE4323";
			String status = "ACTIVATE";
			Client clien  = new Client();
			//短信验证码判断
			if(!codename.equals(codees)) {
				judge= false;
				return judge;	
			}
						
			try {
				String md5Password = MD5Util.encoderByMd5(password);
				//推荐码判断
				String referralCode = (String) context.get(SYConst.PRODUCT_PARAM.REFERRALCODE);
					if(referralCode != "" && referralCode != null) {																			
						ClientReferral clientReferral = clientReferralMapper.selectByPrimaryKey(referralCode);
							if(clientReferral != null) {
								if(clientReferral.getIsAvailable() != false) {
									judge= false;
									return judge;
								}else {					
								
								//会员添加	
									clien.setHeaderImg(headerImg);
									clien.setClientName(clientName);
									clien.setNickName(nickName);
									clien.setTelphone(telphone);
									clien.setPassword(md5Password);
									clien.setAge(Short.valueOf(age));
									clien.setStatus(status);
									clien.setCu(Short.valueOf("1"));
									clien.setUu(Short.valueOf("1"));							
									clientMapper.insert(clien);
							
									clientReferral.setUsedClientId(clien.getClientId());
									clientReferral.setIsAvailable(true);
									
									Example example = new Example(ClientReferral.class);		
									Criteria criteria = example.createCriteria();
									criteria.andEqualTo(SYConst.PRODUCT_PARAM.REFERRALCODE, referralCode);
									clientReferralMapper.updateByExampleSelective(clientReferral, example);
															
									//给会员加积分						
									ClientPoint clientPoint = new ClientPoint();
									clientPoint.setClientId(clien.getClientId());
									clientPoint.setPoint(10);
									clientPoint.setUsedPoint(0);
									clientPoint.setCu(Short.valueOf(clien.getClientId()));
									clientPoint.setUu(Short.valueOf(clien.getClientId()));
									clientPointMapper.insert(clientPoint);
									//给生成推荐码的会员加积分
									List<ClientPoint> all =	clientPointMapper.selectAll();
									for (Iterator<ClientPoint> it = all.iterator(); it.hasNext();) {
										ClientPoint clientPointall =it.next();
										if(clientPointall.getClientId() == clientReferral.getClientId()) {
										int integral = clientPointall.getPoint();
										integral=integral+10;
										clientPointall.setPoint(integral);
										
										Example examplee = new Example(ClientPoint.class);		
										Criteria criteriaa = examplee.createCriteria();
										criteriaa.andEqualTo(SYConst.PRODUCT_PARAM.REFERRALCODE, clientPointall.getClientId());
										clientPointMapper.updateByExampleSelective(clientPointall, example);
										
										}
									}
															
								}
							}
						
						
					}else {
						clien.setClientName(clientName);
						clien.setNickName(nickName);
						clien.setTelphone(telphone);
						clien.setPassword(md5Password);
						clien.setHeaderImg(headerImg);
						clien.setAge(Short.valueOf(age));
						clien.setStatus(status);
						clien.setCu(Short.valueOf("1"));
						clien.setUu(Short.valueOf("1"));
						clientMapper.insert(clien);
						ClientPoint clientPoint = new ClientPoint();
						clientPoint.setClientId(clien.getClientId());
						clientPoint.setPoint(0);
						clientPoint.setUsedPoint(0);
						clientPoint.setCu(Short.valueOf(clien.getClientId()));
						clientPoint.setUu(Short.valueOf(clien.getClientId()));
						clientPointMapper.insert(clientPoint);
						
				}
				
				
				
				
			} catch (NoSuchAlgorithmException e) {
				System.out.println("MD5异常");
				e.printStackTrace();
			}
			
					
			
					
			return judge;
		}				
		
		
		
		/**
		 * 登陆验证
		 */
		@Override
		public Boolean admin(Map<String, Object>  context) {
			Boolean status =true;
			String clientName = (String) context.get(SYConst.CLIENT_PARAM_NAME.CLIENT_NAME);
			String password = (String) context.get(SYConst.CLIENT_PARAM_NAME.PASSWORD);	
			List<Client> clien = clientMapper.selectAll();		
				for (Iterator<Client> it = clien.iterator(); it.hasNext();) {
					Client all =it.next();
					if(all.getClientName().equals(clientName)) {
						if(all.getPassword().equals(password) ) {
							 HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
							 request.getSession().setAttribute(SYConst.CLIENT_PARAM_NAME.CLIENT_ID, all.getClientId());
							 status =true;
							 break;
						}else {
							status = false;
						}
						
					}else {
						status = false;
					}
					
				}		
			return status;
		}
	

	
	

	@Override
	public Map<String, Object> getReferral(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<ClientReferral> referral = clientReferralMapper.selectClinetReferralSql(context);
		List<Client> client = clientMapper.selectAll();
		int count = clientReferralMapper.selectCountClinetReferralSql(context);
		int page = (int) context.get("page");
		int limit = page * 5;
		for(Iterator<ClientReferral> it = referral.iterator();it.hasNext();) {
			ClientReferral re = it.next();
			if(result.containsKey(re.getReferralCode())) {
				Map<String, Object> referralMap = (Map<String, Object>) result.get(re.getReferralCode());
				
				for(Iterator<Client> itc = client.iterator();itc.hasNext();) {
					Client c = itc.next();
					if(String.valueOf(re.getUsedClientId()).equals(String.valueOf(c.getClientId()))) {
						referralMap.put("usedClientName", c.getClientName());
					} else {
						referralMap.put("usedClientName", null);
					}
				}
				
			} else {
				Map<String, Object> referralMap = new HashMap<String, Object>();
				referralMap.put("referralCode", re.getReferralCode());
				referralMap.put("isAvailable", re.getIsAvailable());
				referralMap.put("usedClientId", re.getUsedClientId());
				SimpleDateFormat format =  new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
				String d = format.format(re.getCt());
				referralMap.put("ct", d);
				for(Iterator<Client> itc = client.iterator();itc.hasNext();) {
					Client c = itc.next();
					if(String.valueOf(re.getUsedClientId()).equals(String.valueOf(c.getClientId()))) {
						referralMap.put("usedClientName", c.getClientName());
					} else {
						referralMap.put("usedClientName", null);
					}
				}
				result.put(re.getReferralCode(), referralMap);
			}
			
		}
		return result;
	}

	private SimpleDateFormat newSimpleDateFormat(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> newReferral(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		Short clientId = (Short) context.get("clientId");
		Example example = new Example(ClientReferral.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.PRODUCT_PARAM.CLIENT_ID, clientId);
		List<ClientReferral> referral = clientReferralMapper.selectByExample(example);
		int count = 0;
		for(Iterator<ClientReferral> it = referral.iterator();it.hasNext();) {
			ClientReferral re = it.next();
			if(!re.getIsAvailable()) {
				count = count + 1;
			}
		}
		if(count == 0) {
			ClientReferral clientReferral = new ClientReferral();
			RandomUtil randomUtil = new RandomUtil();
			String referralCode = randomUtil.generateString(10);
			Date currentDate = new Date(System.currentTimeMillis());
			clientReferral.setClientId(clientId);
			clientReferral.setCt(currentDate);
			clientReferral.setReferralCode(referralCode);
			clientReferral.setIsAvailable(false);
			clientReferralMapper.insert(clientReferral);
		} else {
			throw new SYException("有推荐码未使用，请使用后再生成新的推荐码");
			
		}
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> getOrderByStatus(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<VOrderHeaderItemClientEmnu> orderByStatusAll = sOrderHeaderMapper.selectOrderByStatusAll(context);
		List<VOrderHeaderItemClientEmnu> orderByStatusPay = sOrderHeaderMapper.selectOrderByStatusPay(context);
		List<VOrderHeaderItemClientEmnu> orderByStatusGo = sOrderHeaderMapper.selectOrderByStatusGo(context);
		List<VOrderHeaderItemClientEmnu> orderByStatusOk = sOrderHeaderMapper.selectOrderByStatusOk(context);
		List<VOrderHeaderItemClientEmnu> orderByStatusEvaluation = sOrderHeaderMapper.selectOrderByStatusEvaluation(context);
		
		result.put("orderByStatusAll", orderByStatusAll);
		result.put("orderByStatusPay", orderByStatusPay);
		result.put("orderByStatusGo", orderByStatusGo);
		result.put("orderByStatusOk", orderByStatusOk);
		result.put("orderByStatusEvaluation", orderByStatusEvaluation);
		
		return result;
	}
	
	public Map<String, Object> updateOrderStatus(Map<String, Object> context){
		Map<String, Object> result = new HashMap<String, Object>();
		
		String orderStatus = (String) context.get("orderStatus");
		
		Short flag = (Short) context.get("flag");
		
		Short orderId = (Short) context.get("orderId");
		
		Example example = new Example(OrderItem.class);
		
		Criteria criteria = example.createCriteria();
		
		criteria.andEqualTo("orderId", orderId);
		
		OrderHeader orderHeader = new OrderHeader();
		
		if(("已付款").equals(orderStatus)) {
			orderHeader.setOrderStatus("已付款");
			orderHeaderMapper.updateByExampleSelective(orderHeader, example);
		};
		
		if(("已取消").equals(orderStatus)) {
			orderHeader.setOrderStatus("已取消");
			orderHeaderMapper.updateByExampleSelective(orderHeader, example);
		};
		
		if(("已发货").equals(orderStatus)) {
			orderHeader.setOrderStatus("已发货");
			orderHeaderMapper.updateByExampleSelective(orderHeader, example);
		};
		
		if(("已收货").equals(orderStatus)) {
			orderHeader.setOrderStatus("已收货");
			orderHeaderMapper.updateByExampleSelective(orderHeader, example);
		};
		
		if(1 == flag) {
			orderHeader.setFlag((short) 0);
			orderHeaderMapper.updateByExampleSelective(orderHeader, example);
		};
		
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		
		return result;
	}
	
	public Map<String, Object> insertEvaluation(Map<String, Object> context){
		Map<String, Object> result = new HashMap<String, Object>();
		
		Short clientId = (Short) context.get("clientId");
		
		Short productId = (Short) context.get("productId");
		
		Short skuId = (Short) context.get("skuId");
		
		String evaluationContent = (String) context.get("evaluationContent");
		
		String assessType = (String) context.get("assessType");
		
		Date currentDate = new Date(System.currentTimeMillis());
		
		Evaluation e = new Evaluation();
		
		e.setClientId(clientId);
		e.setProductId(productId);
		e.setSkuId(skuId);
		e.setEvaluationContent(evaluationContent);
		e.setAssessType(assessType);
		e.setCt(currentDate);
		
		evaluationMapper.insert(e);
		
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		
		return result;
	}

}



