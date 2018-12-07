var SERVICE = {
	STATUS: "STATUS",
	SUCCESS: "SUCCESS",
	FAIL: "FAIL",
	LOGIN_FAIL: "LOGIN_FAIL",
	MESSAGE_BODY: "MESSAGE_BODY"
}

var MESSAGE = {
	FORM_UNDONE: "请检查以※开头的必填选项",
	DELETE_SUCCESS: "删除成功",
	UPDATE_SUCCESS: "更新成功",
	INSERT_SUCCESS: "添加成功",
	CHANGE_SUCCESS: "状态变更成功",
	MESSAGE_FAIL: "失败，请重新执行操作！",
	TYPE: {
		INFO: "提示",
		ERROR: "错误",
		WARNING: "警告"
	},
	USER: {
		USER_NAME_NEED: "用户名必填",
		REPEAT_USERNAME: "用户名已被使用",
		USER_NAME_CONFIRM: "请使用字母及下划线组合",
		USER_PASSWORD_NEED: "密码必填",
		CON_PASSWORD_NEED: "请填写确认密码",
		PASSWORD_CONFIRM: "请使用字母数字特殊字符组合",
		USER_PASSWORD_CONFIRM: "密码与确认密码不一致",
		USER_DELETE_CONFIRM: "确定删除当前选择的用户吗？",
		USER_NEED_ROLE: "请为用户指定角色",
		USER_LOGOUT: "是否退出登陆？",
		ROLE: {
			ROLE_NAME_NEED: "角色名必填",
			ROLE_NAME_CONFIRM: "请使用准确的汉字描述角色名",
			REPEAT_ROLENAME: "角名已被使用",
			ROLE_DES_CONFIRM: "请使用简洁的汉字描述角色",
			PERMISSION_NEED: "权限必须指定",
			ROLE_DELETE_CONFIRM: "确定删除当前选择的角色吗？",
		},
		PERMISSION: {
			PERMISSION_NAME_NEED: "权限名必填",
			PERMISSION_NAME_CONFIRM: "请使用准确的汉字描述权限名",
			REPEAT_PERMISSIONNAME: "权限名已被使用",
			PERMISSION_NAME_NEED: "请填写中文字符权限名！",
			PERMISSION_TYPE_NEED: "权限必须指定类型",
			PERMISSION_CONTEXT: "请正确输入权限菜单（只允许包含【/】和字母）！",
			PERMISSION_DELETE_CONFIRM: "确定删除当前选择的权限吗？",
		}
	},
	CLIENT: {
		LEVEL_DELETE_CONFIRM: "确定删除当前选择的等级吗？",
		REPEAT_LEVELNAME: "等级名已被使用",
		LEVEL_NAME_NEED: "等级名不能为空",
		LEVEL_POINT_CONFIRM: "请输入正整数",
		STATUS_CHANGE_WARNNING: "注意：变更会员状态会影响会员登陆！",
	},
	PRODUCT: {
		PRODUCT_DETAL_UNDONE: "请检查以※开头的必填选项",
		PRODUCT_NAME_NEED: "请填写商品名",
		CHINESELATTER_ERROR: "请填写正确商品名(10位以内的英文，汉字，且不可为纯数字)",
		PRODUCT_PRICE_NEED: "请填写商品价格",
		PRODUCT_PRICE_ERROR: "请填写正确的价格，不能以0开头且整数最多10位，小数至多2位",
		BRAND_NAME_NEED: "请指定品牌名",
		CATEGORY_NEED: "必须指定商品分类,请在下拉框为商品指定分类",
		ATTR_NEED: "必须指定商品属性,请为商品指定分类后选择对应的属性",
		COVERIMAGE_NEED: "商品必须有封面图片用于展示,请为商品指定封面图片",
		DETAILIMAGE_NEED: "商品必须有商品详细图片用于商品详细页面,请为商品指定至少一张详细图片",
		STATUS_CHANGE_WARNNING: "注意：变更商品状态会影响目前展示！",
		BILLBOARD_STATUS_CHANGE_WARNNING: "注意：变更状态会影响目前展示！",
		BILLBOARD_DELETE_CONFIRM: "确定删除当前选择的广告图片吗？",
		ADVERTISEMENTURL_NEED: "请填写图片链接路径",
		STORAGE: {
			STORAGE_DELETE_CONFIRM: "确定删除当前选择的货架吗？注意删除已激活货架会影响页面展示！",
			STORAGE_STATUS_CHANGE_WARNNING: "注意：变更货架状态会影响目前展示！",
			REPEAT_STORAGE_PRODUCT: "该商品已经添加",
			STORAGE_NAME_NEED: "请填写货架名",
			REPEAT_STORAGE_NAME: "货架名已被使用",
			REMOVE_STORAGE_PRODCUT: "确定从当前货架移除商品么？",
			STORAGE_INDEX_CHANGE: "注意，变更货架顺序将会影响页面显示",
			STORAGE_PRODUCT_INDEX_CHANGE: "注意，变更商品顺序将会影响货架内商品",		
		},
		ORDER:{
			ORDER_STATUS:"是否改变订单状态！！",
			ORDER_WARN:"用户还未结账，无法更改状态"	
		},
		ATTR: {
			ATTR_DELETE_CONFIRM: "确定删除当前选择的属性值吗？",
			ATTR_NAME_EDIT_CONFIRM: "注意修改属性名会导致站内此属性名全部变更！！",
			ATTR_NAME_DELETE_CONFIRM: "确定删除当前选择的属性名吗？",
			ATTR_NAME_NEED: "请填写属性名",
			REPEAT_ATTR_NAME: "属性名已被使用",
			ATTR_NAME_NEED: "请填写属性值名",
			ATTR_VALUE_NEED: "注意修改属性名会导致站内此属性值全部变更！！"
		},
		BRAND: {
			BRAND_NAME_NEED: "请填写品牌名",
			BRAND_NAME_CONFIRM: "请填写正确品牌名(10位以内的英文，汉字，且不可为纯数字)",
			BRAND_NAME_CONFIRM: "品牌名已被使用",
			BRAND_DELETE_CONFIRM: "确定删除当前选择的品牌吗？"
		},
	},
	SCREEN: {
		NOT_FINISH: "画面存在尚未处理的项目，请按照要求处理完毕！",
	},
	STRING: {
		INCORRECT: "✖",
		CORRECT: "✔",
		LETTER: {
			ENG_H_NEED: "需要大写英文字符",
			ENG_L_NEED: "需要小写英文字符",
			CH_NEED:"已有此属性名",
			ZH_NEED: "需要中文字符"
		},
		LENGTH: {
			OVERSTEP: "长度超出格式",
			NULL: "不能为空",
			NOT_ENOUGH: "长度不够" ,
			MISSZS:"未进行修改"
		}
	}
}


var StringUtils = {
		
	isEmpty: function(target) {
		
		if (target === undefined || target === null || target.trim() === "") {
			return true;
		}
		
		return false;
	},
	
	stringCheckUrl: function(target) {
		var p = /[a-zA-Z\/]+$/g;
		return p.test(target);
	},
	
	stringUserName: function(target) {
		var p = /^\w+$/g;
		return p.test(target);
	},
	
	stringPassword: function(target) {
		var p = /^(\w){6,20}$/g;
		return p.test(target);
	},
	stringProductName: function(target) {
		var p = /(?!\d+)[a-zA-Z0-9\u4e00-\u9fa5]/g;
		return p.test(target);
	},
	stringProductPrices: function(target) {
		var p = /^(0|[1-9][0-9]{0,9})(\.[0-9]{1,2})?$/g;
		return p.test(target);
	},
	stringCheckLowCaseLatter: function(target) {
		var p = /[a-z]+$/g;
		return p.test(target);
	},
	
	stringCheckHighCaseLatter: function(target) {
		var p = /^[A-Z]+$/g;
		return p.test(target);
	},
	
	stringCheckChineseLatter: function(target) {
		var p = /^[\u4e00-\u9fa5]+$/g;
		return p.test(target);
	}
};


var MessageUtils = {
	showConfirmMessage: function(message, title, confirmCallBack, cancelCallBack) {
		var tempParam = {"message": message, "title": title};
		if (typeof confirmCallBack === "function") {
			tempParam.confirmFlg = true;
		}
		
		if (typeof cancelCallBack === "function") {
			tempParam.cancelFlg = true;
		}
		
		
		var msDom = $(template($("#message_area").html(), tempParam));

		$("body").append(msDom);

		var mc = $("#message_container").dialog({
            uiLibrary: 'bootstrap',
            closeButtonInHeader: false,
        });
        
        if (typeof confirmCallBack === "function") {
        
	        $("#m_c_confrim").click(function() {
	        	mc.destroy();
	        	$("#message_container").remove();
	        	confirmCallBack();
	        });
        
        }
        
        if (typeof cancelCallBack === "function") {
        	$("#m_c_cancel").click(function() {
	        	mc.destroy();
	        	$("#message_container").remove();
	        	cancelCallBack();
	        });
        }
	},
	showMessage: function(message, title, callback) {
		var msDom = $(
			template(
				$("#message_area").html(),
				{
					"message": message, 
					"title": title,
					"confirmFlg": true,
					"cancelFlg": false
				}
			)
		);

		$("body").append(msDom);

		var mc = $("#message_container").dialog({
            uiLibrary: 'bootstrap',
            closeButtonInHeader: false,
        });
        
        $("#m_c_confrim").click(function() {
        	mc.destroy();
        	$("#message_container").remove();
        	if (typeof callback === "function") {
        		callback();
        	}
        });
	},
	warningMessage: function(message, title, callback) {
		var msDom = $(
			template(
				$("#message_area").html(),
				{
					"message": message, 
					"title": title,
					"confirmFlg": true,
					"cancelFlg": false
				}
			)
		);

		$("body").append(msDom);

		var mc = $("#message_container").dialog({
            uiLibrary: 'bootstrap',
            closeButtonInHeader: false,
        });
        
        $("#m_c_confrim").click(function() {
        	mc.destroy();
        	$("#message_container").remove();
        	if (typeof callback === "function") {
        		callback();
        	}
        });
	}
	
};
