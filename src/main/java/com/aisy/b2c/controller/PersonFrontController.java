package com.aisy.b2c.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aisy.b2c.pojo.Client;
import com.aisy.b2c.pojo.DeliveryAddress;
import com.aisy.b2c.pojo.RcDistrict;

import com.aisy.b2c.pojo.VDeliveryAddress;
import com.aisy.b2c.service.IPersonService;
import com.aisy.b2c.util.CodeUtil;
import com.aisy.b2c.util.SYConst;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Controller
@RequestMapping("/front")
public class PersonFrontController {
	
	
	
	
	
	@Resource
	IPersonService personServiceImpl;
	
	
	// renweisong  2018-6-1  起始
		/**
		 * 查看个人资料和个人地址和个人等级
		 * @param clientId
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="/client.html", method=RequestMethod.GET)
		public ModelAndView getPerson(Short clientId) {
			
			ModelAndView view = new ModelAndView("front/Person/information");
			
			List<Object> client = personServiceImpl.getclient();		
			view.addObject(SYConst.PRODUCT_PARAM.CLIENT, client);
			
			 
			List<Object> grade = personServiceImpl.getclientgrade();		
			view.addObject(SYConst.PRODUCT_PARAM.GRADE, grade);

			List<Object> delivery = personServiceImpl.getdeliveryAddresses();	
			view.addObject(SYConst.PRODUCT_PARAM.DELIVERY, delivery);
			
			return view;
		}
		
			
		
		/**
		 * 修改个人资料
		 * @param form
		 * @return
		 * @throws Exception
		 */
		@ResponseBody
		@RequestMapping(value="front/upclient.html")
		public ModelAndView getPerson(@ModelAttribute("form") Client form) throws Exception{					
			personServiceImpl.upclient(form);
			return new ModelAndView("redirect:/front/client.html");
			
				
		}
		// renweisong  2018-6-1  结束
		
		
		 /**
		  * 短信发送
		  * @param phone
		  * @return
		  * @throws Exception
		  */
		 @ResponseBody
		 @RequestMapping(value="/information.html")
		 public static  Map<String,Object> sendMsg(String phone) throws Exception {
			 
			 
			
	        return CodeUtil.sendMsg(phone);
	    }  

		    
		    /**
		     * 判断验证码是否正确
		     */
		 @ResponseBody
		 @RequestMapping(value="/code.html")
		 public static   Boolean Code(String code)  {
			 HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			 String codees = (String) request.getSession().getAttribute(SYConst.PRODUCT_PARAM.CHECKCODE);
			 System.out.println(codees);	
			 if(code.equals(codees)) {
				
				 return true;
			 }else {
				
				 return false ;
			 }
	     
	    }  
		    
		    
		    /**
		     * 根据用户ID地址页查询
		     * @param clientId
		     * @return
		     */
		 	@ResponseBody
		    @RequestMapping("/address.html")
		    public ModelAndView getaddress(Short districtId) {
		    	
				ModelAndView view = new ModelAndView("front/Person/address");
				
				List<Object> client = personServiceImpl.getclient();		
				view.addObject(SYConst.PRODUCT_PARAM.CLIENT, client);
				

				List<Object> delivery = personServiceImpl.getdeliveryAddresses();	
				view.addObject(SYConst.PRODUCT_PARAM.DELIVERY, delivery);
				
				List<Object> rcDistrict =	personServiceImpl.getrcdistrict();
				view.addObject(SYConst.PRODUCT_PARAM.RCDISTRICT, rcDistrict);
				System.out.println(rcDistrict);
										
				return view;
			}
		    
		    /**
		     * 根据ID查询省地址用于添加
		     */
		    
		    @ResponseBody
			@RequestMapping(value="/deleteCart.html", method=RequestMethod.GET)
			public Map<String, Object> getrcdis(Short districtId){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	System.out.println(districtId);
		    	List<Short> idList = new ArrayList<Short>();
		    	List<String> cityList = new ArrayList<String>();
		    	List<RcDistrict> getrcdistrictc = personServiceImpl.getrcdistrictc(districtId);
			for (int i = 0; i < getrcdistrictc.size(); i++) {
				Short districtId2 = getrcdistrictc.get(i).getDistrictId();
				idList.add(districtId2);
				String district = getrcdistrictc.get(i).getDistrict();
				cityList.add(district);
			}
				result.put("idList", idList);
				result.put("cityList", cityList);
				 return  result ;
		    }
		    
		    /**
		     * 用二级名字查找三级区级
		     * @param district
		     * @return
		     */
		    
		    @ResponseBody
			@RequestMapping(value="/deleteCarts.html", method=RequestMethod.GET)
			public Map<String, Object> getrcdiss(String district){
		    	Map<String, Object> result = new HashMap<String, Object>();
		    	System.out.println(district);	    
		    	List<String> cityList = personServiceImpl.getrcat(district);	
				result.put("cityList", cityList);
				return  result ;
		    }
		    /**
		     * 地址添加
		     * @param form
		     * @return
		     * @throws Exception
		     */
		    @ResponseBody
			@RequestMapping(value="/delivery.html")
			public List<Object> delivery(@ModelAttribute("form") VDeliveryAddress form) throws Exception{

				
				return personServiceImpl.delivery(form);
				
			}
		    
		    
		    /**
		     * 地址删除
		     * @param <Shord>
		     */
		    @ResponseBody
			@RequestMapping(value="/datadelivery.html")
			public  ModelAndView delivery(DeliveryAddress addressId) {
		    	System.out.println(addressId);
		    	personServiceImpl.datadelivery(addressId);			
				return  new ModelAndView("redirect:/front/address.html");
				
			}
		    
		   
		    
			@RequestMapping(value="/password.html")
		    public String password(Client password) {
				return "front/Person/password";
			}
			
			 /**
		     * 用户密码修改 查询原密码
		     */
			@ResponseBody
			@RequestMapping(value="/uppassword.html")
		    public Object selectpassword(Client password) {
				return personServiceImpl.getpassword(password);
			
			}
			
			/**
			 * 修改新密码
			 */
			@ResponseBody
			@RequestMapping(value="/newpassword.html")
		    public Object uppassword(Client password) {
				personServiceImpl.uppassword(password);
				
				return new ModelAndView("redirect:/front/admin.html");
			
			
			
			}
			
			/**
			 * 设置默认地址
			 */
		    
			
			@RequestMapping(value="/upisDefault.html")
		    public Object upisDefault(Short addressId) {
				personServiceImpl.upisdefault(addressId);				
				return new ModelAndView("redirect:/front/address.html");
			}
			
			
			/**
			 * 
			 * 地址修改取值
			 * @param addressId
			 * @return
			 */
			@ResponseBody
			@RequestMapping(value="/upisDefaultadd.html")
		    public Object upisDefaultadd(VDeliveryAddress addressId) {
				return	personServiceImpl.upisdefaultadd(addressId);				
				
				 
			}	
			
			/**
			 *根据ID地址修改
			 */
			@ResponseBody
			@RequestMapping(value="/upisDefaultid.html")
		    public Object upisDefaultaddid (VDeliveryAddress form)  {	 
				return	personServiceImpl.updeliveryaddress(form);		
								
				
				 
			}
			
			/**
			 * 积分查询
			 */
			@ResponseBody
		    @RequestMapping("/points.html")
		    public ModelAndView clientpoint() {
		    	
		    	
		    	
				ModelAndView view = new ModelAndView("front/Person/points");			
				List<Object> PointManagement =	personServiceImpl.getpoints();
				view.addObject(SYConst.PRODUCT_PARAM.POINTMANAGEMENT, PointManagement);
				
				
				List<Object> clientPoint =	personServiceImpl.getclientPoint();
				view.addObject(SYConst.PRODUCT_PARAM.CLIENTPOINT, clientPoint);
				
				
				return view;
			}
			
			/**
			 * 注册页面
			 */
			
		    @RequestMapping("/register.html")
		    public ModelAndView register() {
		    	
				ModelAndView view = new ModelAndView("front/register");			
			
				
				
				return view;
			}
		    
		    
		    /**
		     * 注册前查看用户名是否已有
		     */
		 @ResponseBody
		 @RequestMapping(value="/user.html")
		 public  Boolean user(String user)  {		
			 return  personServiceImpl.getuser(user);
	     }  
		 
		 
		 
		 /**
		  * 注册前查看推荐吗是否使用或存在
		  */
		 @ResponseBody
		 @RequestMapping(value="/referralCode.html")
		 public  Boolean referralCode(String referralCode)  {		
			 return  personServiceImpl.referralCode(referralCode);
		 }  
			
		 
		
			
		 /**
		  * 用户注册添加
		  */
		 @ResponseBody
		 @RequestMapping(value="/inuser.html", method=RequestMethod.POST)
		 public  Boolean inuser(
				 @RequestBody Map<String, Object> context){	
			 
			 
			 return personServiceImpl.inclient(context);
	     } 
		 
		 
		 /**
		  * 登陆页面
		  */
			
		    @RequestMapping("/admin.html")
		    public ModelAndView admin() {
		    	
				ModelAndView view = new ModelAndView("front/admin");			
			
				
				
				return view;
			}
		 
		    /**
		     * 登陆之前验证账户和密码
		     */
		    
		    @ResponseBody
			@RequestMapping(value="/getadmin.html", method=RequestMethod.POST)
			public  Boolean getadmin(
					@RequestBody Map<String, Object> context){	
				 
				 
				return personServiceImpl.admin(context);
		     }
		    
	     /* 任蔚松  添加注解*/
	
	    
	 	/* 刘笑楠 添加 推荐码查询 开始于2018.6.11 08:28:00:00 */
	    @RequestMapping(value="/referral", method=RequestMethod.GET)
		public ModelAndView referralDetail() {
			Map<String, Object> context = new HashMap<String, Object>();
	    	context.put("clientId", 1);
	    	context.put("page", 1);
			
			Map<String, Object> result = personServiceImpl.getReferral(context);
	
			ModelAndView view = new ModelAndView("front/person/referral");
			
			view.addObject("referral", result.get("referral"));
			
			return view;
		}
	    /* 刘笑楠 添加 广告轮播缓存 结束于2018.6.11 08:28:30:00 */
	    
	    /* 刘笑楠 添加 推荐码新增 开始于2018.6.11 08:29:00:00 */
	    @ResponseBody
		@RequestMapping(value="/newReferral", method=RequestMethod.PUT)
		public Map<String, Object> newReferral(
				Map<String, Object> context
				) {
			return personServiceImpl.newReferral(context);
		}
	    /* 刘笑楠 添加 推荐码新增 结束于2018.6.11 08:29:30:00 */
	    
	    
	    @RequestMapping("/order")
		public String orderList(Map<String, Object> context,
				HttpServletRequest request) {
			
			return "front/order/order";
		}
	    
	    @RequestMapping("/orderInfo")
	    public String orderInfo(Map<String, Object> context,
	    		HttpServletRequest request) {
	    	
	    	return "front/order/order_info";
	    }
	   
	    /* 刘笑楠 添加 订单管理条件查询  开始于2018.6.11 09:21:00:00 */
	    @ResponseBody
		@RequestMapping(value="/orderByStatus", method=RequestMethod.GET)
	    public Map<String, Object> getOrderByStatus(Map<String, Object> context,
	    		HttpServletRequest request) {
	    	Map<String, Object> result = personServiceImpl.getOrderByStatus(context);
	    	
	    	return result;
	    }
	    /* 刘笑楠 添加 订单管理条件查询  结束于2018.6.11 09:21:30:00 */
	    
	    /* 刘笑楠 添加 评价新增  开始于2018.6.11 17:28:00:00 */
	    @ResponseBody
		@RequestMapping(value="/newEvaluation", method=RequestMethod.GET)
	    public Map<String, Object> newEvaluation(Map<String, Object> context,
	    		HttpServletRequest request) {
	    	Client currentLoginClient = (Client) request.getSession().getAttribute("loginClient");
	    	
	    	Short clientId = currentLoginClient.getClientId();
	    	
	    	context.put("clientId", clientId);
	    	
	    	Map<String, Object> result = personServiceImpl.insertEvaluation(context);
	    	
	    	return result;
	    }
	    /* 刘笑楠 添加 订单管理条件查询  结束于2018.6.11 09:21:30:00 */
	}

