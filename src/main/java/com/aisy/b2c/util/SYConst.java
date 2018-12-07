package com.aisy.b2c.util;

public class SYConst {
	
	public static class EMNU {
		public final static String STATUS = "STATUS";
		/* add start by liuzhaoyu 2018-05-11 18:33:50 */
		//枚举表的分类
		public final static String EMNU_TYPE ="emnuType";
		
		//枚举表的名
		public final static String EMNU_NAME ="emnuName";
		public final static String EMNU_VALUE = "emnuValue";
		/* add end by liuzhaoyu 2018-05-11 18:34:50 */
		public final static String ACTIVATE ="ACTIVATE";
		public final static String INACTIVATE = "INACTIVATE";
	}
	
	public static class SYMBOL {
		public final static String OCTOTHORPE = "#";
		public final static String COLON = ":";
		public final static String SEMICOLON = ";";
		public final static String HTML_NEWLINE = "<br/>";
		public final static int DEFAULTINT = 0;
		public final static String COMMA = ":";
		public final static String PERCENT = "%";
	}

	public static class SESSION {
		public final static String LOGIN_USER = "LOGIN_USER";
		public final static String LOGIN_USER_ID = "0";
		public final static String FRONT_LOGIN_USER = "FRONT_LOGIN_USER";
		public final static String CURRENT_URL = "CURRENT_URL";
		public final static String OP_CLOSE_FLG = "OP_CLOSE_FLG";
		public final static String CART = "CART";
		public final static String KAPTCHA_SESSION_KEY = "kaptchaSessionKey";
	}
	
	public static class PAGE_PARAM_NAME {
		public final static String PAGE = "page";
		public final static String LIMIT = "limit";
		public final static String RECORDS = "records";
		public final static String TOTAL = "total";
		public final static String SORTBY = "sortBy";
		public final static String DIRECTION = "direction";
		public final static String FRECORDS = "Frecords";
		public final static String FTOTAL = "Ftotal";
	}
	
	public static class CLIENT_PARAM_NAME {
		public final static String CLIENT_ID = "clientId";
		public final static String CLIENT_NAME = "clientName";
		//任蔚松  2018/6/19 开始
		public final static String PASSWORD = "password";
		public final static String HEADERIMG = "headerImg";
		//任蔚松  2018/6/19 结束
		
		public final static String CLIENT_MODEL = "clientModel";
		public final static String CLIENT = "client";
		public final static String CLIENT_NICK_NAME = "nickName";
		public final static String AGE = "age";
		public final static String TELPHONE = "telphone";
		public final static String LEVEL_ID = "level";
		public final static String LEVEL_NAME = "leveName";
		public final static String LEVEL_POINT = "levePoint";
		public final static String CLIENT_LEVEL= "clientlevel";
		public final static String CLIENT_POINTS = "clientPoints";
		public final static String CLIENT_REFERRAL = "clientReferral";
	}
	
	public static class USER_PARAM_NAME {
		public final static String CURRENT_USER = "currentUser";
		public final static String USER_ID = "userId";
		public final static String USER_NAME = "userName";
		public final static String PASSWORD = "password";
		public final static String NICK_NAME = "nickName";
		public final static String CURRENT_USERID = "currentUserId";
		public final static String USER = "user";
		public final static String USER_ROLE = "userRole";
		public final static String UPDATE_USER = "updateUser";
		public final static String ORIGINAL_USER = "originalUser";

		public final static String ROLE_LIST = "roleList";
		public final static String ROLE = "role";
		public final static String ROLE_ID = "roleId";
		public final static String ROLE_NAME = "roleName";
		public final static String ROLE_DES = "roleDes";
		public final static String ROLE_PERMISSION = "rolePermission";
		public final static String UPDATE_ROLE = "updateRole";
		public final static String ORIGINAL_ROLE = "originalRole";
		
		public final static String PERMISSION = "permission";
		public final static String PERMISSION_ID = "permissionId";
		public final static String PARENT_ID = "parentId";
		public final static String PERMISSION_NAME = "permissionName";
		public final static String PERMISSION_CONTEXT = "permissionContext";
		public final static String PERMISSION_TYPE = "permissionType";
		public final static String UPDATE_PERMISSION = "updatePermission";
		public final static String ORIGINAL_PERMISSION = "originalPermission";
		
	}
	
	public static class PRODUCT_PARAM {
		
		public final static String CU = "cu";
		public final static String CT = "ct";
		public final static String UU = "uu";
		public final static String UT = "ut";
		
		public final static String P_ATTR_NAME = "pAttrName";
		public final static String P_ATTR_NAME_ID = "pAttrNameId";
		public final static String P_ATTR_NAME_VALUE_ID = "pAttrNameValueId";
		public final static String P_ATTR_NAME_VALUE = "pAttrNameValue";
		public final static String NEW_ATTR_NAME = "NEW_ATTR_NAME";
		public final static String P_ATTR_NAME_LIST = "pAttrNameList";
		public final static String ATTR_NAME_MAPPER  = "attrNameMapper";
		public final static String ATTR_VALUE_MAPPER = "attrValueMapper";
		public final static String P_ATTR_VALUE_MAP = "pAttrValueMap";
		public final static String P_ATTR_NAME_MAPPER = "attrNameMapper";
		public final static String P_ATTR_VALUE_MAPPER = "attrValueMapper";
		public final static String P_CATEGORY_CHILDREN_LIST = "children";
		public final static String P_CATEGORY_INFO_MAPPER = "categoryInfo";
		public final static String P_CATEGORY_ATTR_INFO_MAPPER = "categoryAttrInfo";
		public final static String P_CATEGORY_LEVEL = "levelOne";
		public final static String P_ATTR_VALUE = "pAttrValue";
		public final static String P_ATTR_VALUE_ID = "pAttrValueId";
		public final static String NEW_ATTR_VALUE = "newAttrValue";
		public final static String P_STATUS = "status";
		public final static String ACTIVATE = "ACTIVATE";

		public final static String PRODUCT = "product";
		public final static String ATTR = "attr";
		public final static String PRODUCT_ID = "productId";
		public final static String PRODUCT_NAME = "productName";
		public final static String BRAND_ID = "brandId";
		public final static String BRAND_NAME = "brandName";
		public final static String SHOW_PRICE = "showPrice";
		public final static String STATUS_NAME = "statusName";
		public final static String SALES_VOLUME = "salesVolume";
		public final static String DESCRIPTION = "des";
		public final static String IMAGE = "image";
		public final static String IMAGECOVER = "cover";
		public final static String IMAGEDETAIL = "detail";
		public final static String COVER = "COVER";
		public final static String DETAIL = "DETAIL";
		public final static String ISSKU = "isSku";
		
		public final static String STORAGE = "storage";
		public final static String STORAGE_LIST = "storageList";
		public final static String STORAGE_PRODUCTS_MAP = "storageProductsMap";
		public final static String STORAGE_PRODUCT = "storageProduct";		
		public final static String SKU_ID ="skuId";
		public final static String STORAGE_NAME = "storageName";
		public final static String STORAGE_DES = "storageDes";
		public final static String STORAGE_ID = "storageId";
		public final static String SORT_INDEX = "sortIndex";
		public final static String CATEGORY_INFO = "categoryInfo";
		public final static String CHILDREN = "children";
		public final static String LEVEL_ONE = "levelOne";
		public final static String CATEGORY_ATTR_INFO = "categoryAttrInfo";
		public final static String SORT_INDEX_LIST = "sortIndexList";
		public final static String CATEGORY = "category";
		public final static String CATEGORY_ID = "categoryId";
		public final static String CATEGORY_LEVEL = "categoryLevel";
		public final static String CATEGORY_NAME = "categoryName";
		public final static String PARENT_CATEGORY = "parentCategory";
		public final static String PARENT_ATTR = "parentAttr";
		public final static String SELF_ATTR = "selfAttr";
		public final static String STATUS = "status";
		public final static String UPDATE_CATEGORY = "updateCategory";
		public final static String ORIGINAL_CATEGORY = "originalCategory";
		public final static String OPTION = "option";
		public final static String OPTION_UP = "up";
		public final static String OPTION_DOWN = "down";
		public final static String IS_SKU = "isSku";
		public final static String TARGET_CATEGORY = "targetCategory";
		public final static String CURRENT_CATEGORY = "currentCategory";
		public final static String ORDER_ID = "orderId";

		// renweisong  2018-6-1  起始
		public final static String CLIENT = "client";
		public final static String CLIENT_ID ="clientId";	
		public final static String DELIVERY = "delivery";
// renweisong  2018-6-1  结束

		
		public final static String RCDISTRICT ="rcDistrict";	
		public final static String RCDISTRICTSE ="districtse";	
		public final static String DISTRICT_ID ="districtId";	
		public final static String DISTRICT ="district";
		
		
		
		
		
		public final static String CHECKCODE ="checkCode";
		
		
		public final static String POINTMANAGEMENT ="PointManagement";
		
		public final static String CLIENTPOINT ="clientPoint";
		
		//手机验证码 codename
		
		public final static String CODENAME ="codename";
		
		//推荐码 referralCode
		public final static String REFERRALCODE ="referralCode";
		//个人等级 grade
		public final static String GRADE ="grade";
		
		
		public final static String OTHER_OBJECT = "otherObject";		
		public final static String SKU_OBJECT = "skuObject";
		public final static String CART_OBJECT = "cartObject";
		public final static String CART_MODEL= "cartModel";
		public final static String COUNT = "count";
		public final static String ADDRESS_ID = "addressId";
		public final static String CART_UT = "cartUt";
		public final static String SKU_PRO = "skuPro";
		public final static String COUNT_MAP = "countMap";
		public final static String P_CATEGORY_ID = "pCategoryId";

	}
	
	public static class CART {
		public final static String CART_MODEL = "CART_MODEL";
		public final static String CART_ID = "ID";
		public final static String NUMBER ="number";
		public final static String CART_LIST = "cartList";
		public final static String PLUS = "PLUS";
		public final static String MINUS = "MINUS";
		public final static String OPTION = "OPTION";
	}
	
	public static class BILLBOARD {
		public final static String ADVERTISEMENT = "advertisement";
		public final static String BILLBOARD = "billboard";
		public final static String ADVERTISEMENT_URL = "advertisementUrl";
		public final static String AD_IMG_URL = "adImgUrl";
		public final static String ADVERTISEMENT_ID = "advertisementId";
	}
	
	public static class UPLOADPATH {
		public final static String PRODUCT_IMAGE_PATH = "/WEB-INF/front/assets/";
	}
	
	public static class SERVICE {
		public final static String STATUS = "STATUS";
		public final static String SUCCESS = "SUCCESS";
		public final static String FAIL = "FAIL";
		public final static String LOGIN_FAIL = "LOGIN_FAIL";
		public final static String MESSAGE_BODY = "MESSAGE_BODY";
	}
	
	public static class HEADER {
		public final static String PAYCHANNEL= "payChannel";
		public final static String CLIENTNAME= "clientName";
		public final static String EMNUVALUE  = "emnuValue";
		public final static String TIMEMAX  = "timeMax";
		public final static String EMNUORDERSTATUS  = "emnuOrderStatus";
		public final static String PAYORDERID  = "payOrderId";
		public final static String TIMEMIN  = "timeMin";
		public final static String AMOUNTMAX  = "amountMax";
		public final static String AMOUNTMIX  = "amountMin";
		public final static String ADDRESS  = "address";
	}
	
	public static class MONITORINFO {
		public final static String TOTAL_ORDER_SUM = "totalOrderSum";
		public final static String SALES_VOLUME = "salesVolume";
		public final static String LINUX_VERSION = "Get usage rate of CUP , linux version: ";
		public final static String WINDOWS = "windows";
		public final static String LINUX_ORDER = "top -b -n 1";
		public final static String WIN_DIR = "windir";
		public final static String WIN_DIR_PATH = "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
		public final static String CAPTION = "Caption";
		public final static String COMMANDLINE = "CommandLine";
		public final static String READOPERATIONCOUNT = "ReadOperationCount";
		public final static String USERMODETIME = "UserModeTime";
		public final static String KERNELMODETIME = "KernelModeTime";
		public final static String WRITEOPERATIONCOUNT = "WriteOperationCount";
		public final static String WMIC_EXE = "wmic.exe";
		public final static String SYSTEM_IDLE_PROCESS = "System Idle Process";
		public final static String SYSTEM = "System";
		
	}
}
