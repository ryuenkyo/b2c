package com.aisy.b2c.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.aisy.b2c.controller.CoreUtilsController;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;


public class CodeUtil {

	 /**
	  * 短信发送
	  * @param phone
	  * @return
	  * @throws Exception
	  */
	 @ResponseBody
	 @RequestMapping(value="/information.html")
	 public static  Map<String,Object> sendMsg(String phone) throws Exception {
		 Map<String,Object> result = new HashMap<String,Object>();
		 System.out.println(phone);
		//设置超时时间-可自行调整
		 System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		 System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		 //初始化ascClient需要的几个参数
		 final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		 final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		 //替换成你的AK
		 final String accessKeyId = "LTAIYXb54sMwHfQ6";//你的accessKeyId,参考本文档步骤2
		 final String accessKeySecret = "LzJW046Zs9Zc2Q5S8t3DGI6IlYTpYY";//你的accessKeySecret，参考本文档步骤2
		 //初始化ascClient,暂时不支持多region（请勿修改）
		 IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
		 accessKeySecret);
		 DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		 IAcsClient acsClient = new DefaultAcsClient(profile);
		  //组装请求对象
		  SendSmsRequest request = new SendSmsRequest();
		  //使用post提交
		  request.setMethod(MethodType.POST);
		  //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
		  request.setPhoneNumbers(phone);
		  //必填:短信签名-可在短信控制台中找到
		  request.setSignName("b2c商城短信验证");
		  //必填:短信模板-可在短信控制台中找到
		  request.setTemplateCode("SMS_136386637");
		  //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		  //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		  String checkCode = CoreUtilsController.randomString(6, true);
		  
		  request.setTemplateParam("{\"code\":\""+checkCode+"\"}");
		  //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		  //request.setSmsUpExtendCode("90997");
		  //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		  request.setOutId("yourOutId");
		 //请求失败这里会抛ClientException异常
		 SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		 System.out.println(sendSmsResponse.getCode());
		 if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {			
			 result.put(SYConst.PRODUCT_PARAM.CHECKCODE, checkCode);
		 //请求成功
		 }
		 //将验证码存入Session
		 HttpServletRequest request1 = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		 request1.getSession().setAttribute(SYConst.PRODUCT_PARAM.CHECKCODE, checkCode);
		 
		 
		   // 取值
   //    String a = (String) request1.getSession().getAttribute(SYConst.SESSION.LOGIN_USER);
		 

		 
       return result;  
   }  
	
	
}
