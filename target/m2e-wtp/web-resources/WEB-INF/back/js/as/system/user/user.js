var grid;
var userDetail;
var roleSelect; 
var userUpdate; 
var userRoleUpdate;
			
function editUser(e) {
	console.log(e.data.record.userId);
	
	$.ajax({
		type: "GET",
		url: "../user/info",
		data: {"userId": e.data.record.userId},
		async: true,
		success: function(res) {
			console.log(res);
			var userInfo = JSON.parse(res);
			var target = {};
			target.userId = userInfo.userId;
			target.userName = userInfo.userName;
			target.nickName = userInfo.nickName;
			target.password = userInfo.password;
			createUpdateUser(target);
		},
		error: function(err) {
			
		}
	}); 
}

function createUpdateUser(target) {
	updateUser = {};
	updateUser.check = false;
	if (userUpdate != undefined) {
		userUpdate.destroy();
		$("#user_update").remove();
		$("body").children(".model").remove();
		userUpdate = undefined;
	} 
	
	$("body").append($("#user_update_temp").html());
	
	userUpdate = $("#user_update").dialog({
	    uiLibrary: 'bootstrap',
	    closeButtonInHeader: false,
	    autoOpen: false,
	    resizable: false,
	    modal: true,
	    width: "60%",
	    height: "90%"
	});
	
	$("#user_name").val(target.userName);
	$("#nick_name").val(target.nickName);
	
	$("#nick_name").focusout(function() {
		if ($(this).val().length > 10) {
			updateUser.check = false;
    		$("#tip_nick_name").css("color", "red");
			$("#tip_nick_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
    		return;
    	}
    	$("#tip_nick_name").html("");
    	updateUser.nickName = $(this).val();
	});
	
	$("#user_password").focusout(function() {
		if($(this).val() != target.password){
			updateUser.checked = false;
			$("#tip_user_password").html("");
			$("#tip_user_password").css("color", "red");
			$("#tip_user_password").html(MESSAGE.STRING.INCORRECT);
			return;
		} else {
			$("#tip_user_password").html("");
			$("#tip_user_password").css("color", "green");
			$("#tip_user_password").html(MESSAGE.STRING.CORRECT);
			updateUser.checked = true;
		}
	});

	$("#password").focusout(function() {
		if (!StringUtils.stringPassword($(this).val())) {
			updateUser.checked = false;
			$("#tip_password").css("color", "red");
			$("#tip_password").html(MESSAGE.USER.PASSWORD_CONFIRM);
			return;
		}
		if ($(this).val().trim().length == 0) {
			updateUser.checked = false;
    		$("#tip_password").css("color", "red");
			$("#tip_password").html(MESSAGE.USER.USER_PASSWORD_NEED);
    		return;
    	}
		$("#tip_password").html("");
		updateUser.checked = true;
		updateUser.password = $(this).val();
	});
	
	$("#con_password").focusout(function() {
		if ($(this).val() != $("#password").val()) {
			updateUser.checked = false;
    		$("#tip_con_password").css("color", "red");
			$("#tip_con_password").html(MESSAGE.USER.USER_PASSWORD_CONFIRM);
    		return;
    	}
		if ($(this).val().trim().length == 0) {
			updateUser.checked = false;
    		$("#tip_con_password").css("color", "red");
			$("#tip_con_password").html(MESSAGE.USER.CON_PASSWORD_NEED);
    		return;
    	}
		$("#tip_con_password").html("");
		updateUser.checked = true;
	});
	
	$("#user_update_cancel").click(function() {
		userUpdate.close();
		if (userUpdate != undefined) {
			userUpdate.destroy();
			$("#user_update").remove();
			$("body").children(".modal").remove();
			userUpdate = undefined;
		}
	});
	
	$("#user_update_save").click(function() {
		if ($("#user_password").val() == null 
				|| $("#user_password").val() == "") {
			updateUser.checked = false;
			$("#tip_user_password").html("");
			$("#tip_user_password").html(MESSAGE.USER.USER_PASSWORD_NEED);
			$("#tip_user_password").css("color", "red");
			return;
		}
		if ($("#password").val() == null 
				|| $("#password").val() == "") {
			updateUser.checked = false;
			$("#tip_password").html("");
			$("#tip_password").html(MESSAGE.USER.USER_PASSWORD_NEED);
			$("#tip_password").css("color", "red");
			return;
		}
		if ($("#con_password").val() == null 
				|| $("#con_password").val() == "") {
			updateUser.checked = false;
			$("#tip_con_password").html("");
			$("#tip_con_password").html(MESSAGE.USER.USER_PASSWORD_NEED);
			$("#tip_con_password").css("color", "red");
			return;
		}
		var dataPost = {};
		dataPost.originalUser = target;
		dataPost.updateUser= updateUser;
	
		$.ajax({
			type: "POST",
			url: "../user/info",
			async: true,
			data: JSON.stringify(dataPost),
			contentType: "application/json",
			success: function(res) {
				var obj = JSON.parse(res);
				if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
					
					MessageUtils.showMessage(MESSAGE.UPDATE_SUCCESS, MESSAGE.TYPE.INFO, function() {
						if (userUpdate != undefined) {
							userUpdate.destroy();
							$("#user_update").remove();
							$("body").children(".modal").remove();
							userUpdate = undefined;
							grid.reload();
						}
					});
				} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
					MessageUtils.showMessage(SERVICE.MESSAGE_BODY, MESSAGE.TYPE.INFO, function() {
						if (userUpdate != undefined) {
							userUpdate.destroy();
							$("#user_update").remove();
							$("body").children(".modal").remove();
							userUpdate = undefined;
						}
					});
				}
			},
			error: function(err) {
				console.log("error");
			}
		});
	});
	userUpdate.open("编辑用户");
}

function deleteUser(e) {
    MessageUtils.showConfirmMessage(
		MESSAGE.USER.USER_DELETE_CONFIRM, 
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "DELETE",
				url: "../user/info/"+e.data.record.userId,
				async: true,
				success: function(res) {
					var obj = JSON.parse(res);
					if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
						MessageUtils.showMessage(MESSAGE.DELETE_SUCCESS, MESSAGE.TYPE.INFO, function() {
							grid.reload();
						});
					} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
						MessageUtils.showMessage(obj[SERVICE.MESSAGE_BODY], MESSAGE.TYPE.INFO, function() {
							
						});
					}
				},
				error: function(err) {
					
				}
			});
			
		},
		function() {
			console.log("取消");
		}
	);
}

function bindUserRole(e) {
	
	if (userRoleUpdate != undefined) {
		userRoleUpdate.destroy();
		$("#user_role").remove();
		$("body").children(".model").remove();
		userRoleUpdate = undefined;
	} 
	
	$("body").append($("#user_role_temp").html());
	
	userRoleUpdate = $("#user_role").dialog({
        uiLibrary: 'bootstrap',
        closeButtonInHeader: false,
        autoOpen: false,
        modal: true,
        width: "50%",
        height: "60%"
    });
    
	$.ajax({
		type:"get",
		url:"../role/role.json",
		async:true,
		success: function(res) {
			res.unshift({
				roleId: e.data.record.roleId,
				roleName: e.data.record.roleName,
				selected: true
			});
			
			searchUserRoleId = $('#user_role_dropdown').dropdown({ 
				uiLibrary: "bootstrap",
				dataSource: res,
				valueField: "roleId",
				textField: "roleName"
			});
		},
		error: function(err) {
			
		}
	});

    $("#user_role_cancel").click(function() {
		userRoleUpdate.close();
		
		if (userRoleUpdate != undefined) {
			userRoleUpdate.destroy();
			$("#user_role").remove();
			$("body").children(".model").remove();
			userRoleUpdate = undefined;
		}
	});
	
	$("#user_role_save").click(function() {
		var newUserRole = {};
		newUserRole.userId = e.data.record.userId;
		newUserRole.roleId = searchUserRoleId.val();
		if(newUserRole.roleId == "null"){
			MessageUtils.showMessage(MESSAGE.USER.USER_NEED_ROLE, MESSAGE.TYPE.INFO, function() {
				return;
			});
		}
		var dataPut = JSON.stringify(newUserRole);
    	$.ajax({
    		type: "POST",
    		url: "../user/roleinfo",
    		async: true,
    		data: dataPut,
    		contentType: "application/json",
    		success: function(res) {
    			var obj = JSON.parse(res);
    			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
    				MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
    					if (userRoleUpdate != undefined) {
    						userRoleUpdate.destroy();
	        				$("#user_role").remove();
	        				$("body").children(".modal").remove();
	        				userRoleUpdate = undefined;
	        				grid.reload();
	        			}
    				});
    			}
    		},
    		error: function(err) {
    			console.log("error");
    		}
    	});
    });
	userRoleUpdate.open("绑定角色")
}
		
function initUserData(where) {
	grid = $('#grid').grid({
        primaryKey: 'userId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
            { title: "ID", field: 'userId', width: "5%" },
            { title: '用户名', field: 'userName', width: "15%", sortable: true },
            { title: '昵称', field: 'nickName', width: "15%", sortable: true },
            { title: '角色', field: 'roleName', width: "15%", sortable: true },
            { title: '创建者', field: 'cuName', width: "15%" },
            { title: '创建时间', field: 'ct', width: "25%", type: "date", format:'yyyy/mm/dd HH:MM:ss' },
            { title: '', field: 'BindRole', width: "3.33333333%", type: 'icon', icon: 'glyphicon-user', tooltip: '绑定角色', events: { 'click': bindUserRole } },
            { title: '', field: 'Edit', width: "3.33333333%", type: 'icon', icon: 'glyphicon-pencil', tooltip: '编辑用户', events: { 'click': editUser } },
            { title: '', field: 'Delete', width: "3.33333333%", type: 'icon', icon: 'glyphicon-remove', tooltip: '删除用户', events: { 'click': deleteUser } }
        ],
        pager: { limit: 10, sizes: [] }
    });
}
			
$(document).ready(function() {
	$.ajax({
		type:"get",
		url:"../role/role.json",
		async:true,
		success: function(res) {
			res.unshift({
				roleId: "",
				roleName: "请选择角色",
				selected: true
			});
			
			roleDropDown = $('#search_roleid').dropdown({ 
				uiLibrary: "bootstrap",
				dataSource: res,
				valueField: "roleId",
				textField: "roleName"
			});
		},
		error: function(err) {
			
		}
	});
	
	initUserData({
    	url: "../user/user.json", 
    	data: {}, 
    	success: function(res) {
    		grid.render(res);
    	}, 
    	error: function(err) {
    		
    	}
    });

	$("#btn_search").click(function() {
		grid.clear();
		grid.destroy();
		$("#grid").children().each(function() {
			$(this).remove();
		});

		data = {};
		data.userName = $("#search_username").val();
		data.nickName = $("#search_nickname").val();
		data.roleId = roleDropDown.value();
		
		initUserData({
        	url: "../user/user.json", 
        	"data": data, 
        	success: function(res) {
        		grid.render(res);
        	}, 
        	error: function(err) {
        		
        	}
        });
	});
	
	$("#btn_newuser").click(function() {
		
		if (userDetail != undefined) {
			userDetail.destroy();
			$("#user_detail").remove();
			$("body").children(".model").remove();
			userDetail = undefined;
		} 
		
		$("body").append($("#user_detail_temp").html());
		
		userDetail = $("#user_detail").dialog({
            uiLibrary: 'bootstrap',
            closeButtonInHeader: false,
            autoOpen: false,
            resizable: false,
            modal: true,
            width: "60%",
            height: "90%"
        });
        
		var newUser = {};
		 newUser.user = {};
		 newUser.checked = true;
        
        $("#user_name").focusout(function() {
        	if (!StringUtils.stringUserName($(this).val())) {
				newUser.checked = false;
				$("#tip_user_name").css("color", "red");
				$("#tip_user_name").html(MESSAGE.USER.USER_NAME_CONFIRM);
				return;
			} else if ($(this).val().length > 10) {
        		newUser.checked = false;
        		$("#tip_user_name").css("color", "red");
				$("#tip_user_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
        		return;
        	} else if ($(this).val().trim().length == 0) {
        		newUser.checked = false;
        		$("#tip_user_name").css("color", "red");
				$("#tip_user_name").html(MESSAGE.USER.USER_NAME_NEED);
        		return;
        	} else {
        		$.ajax({
        			url : "../user/userNameConfirm.json",
        			type : "get",
        			async : true,
        			data : {
        				"userName" : $("#user_name").val()
        			},
        			success : function(res) {
        				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
        					$("#tip_user_name").css("color", "red");
        					$("#tip_user_name").html(MESSAGE.USER.REPEAT_USERNAME);
        					newUser.checked = false;
        					return;
            			} else {
            				$("#tip_user_name").html("");
        					$("#tip_user_name").css("color", "green");
        					$("#tip_user_name").html(MESSAGE.STRING.CORRECT);
        					newUser.checked = true;
            			}
        			}
        		});
			}

    		$("#tip_user_name").html("");
        	newUser.checked = true;
        	newUser.userName = $(this).val();
        });
        
        $("#nick_name").focusout(function() {
        	if ($(this).val().length > 10) {
        		$("#tip_nick_name").css("color", "red");
				$("#tip_nick_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
        		return;
        	}
        	$("#tip_nick_name").html("");
    		newUser.nickName = $(this).val();
        });
		 
		$("#password").focusout(function() {
			if (!StringUtils.stringPassword($(this).val())) {
				newUser.checked = false;
				$("#tip_password").css("color", "red");
				$("#tip_password").html(MESSAGE.USER.PASSWORD_CONFIRM);
				return;
			}
			
			if ($(this).val().trim().length == 0) {
        		newUser.checked = false;
        		$("#tip_password").css("color", "red");
				$("#tip_password").html(MESSAGE.USER.USER_PASSWORD_NEED);
        		return;
        	}
			$("#tip_password").html("");
    		newUser.checked = true;
    		newUser.password = $(this).val();
        });
		
		$("#con_password").focusout(function() {
    		if ($(this).val() != $("#password").val()) {
        		newUser.checked = false;
        		$("#tip_con_password").css("color", "red");
				$("#tip_con_password").html(MESSAGE.USER.USER_PASSWORD_CONFIRM);
        		return;
        	}
    		if ($(this).val().trim().length == 0) {
        		newUser.checked = false;
        		$("#tip_con_password").css("color", "red");
				$("#tip_con_password").html(MESSAGE.USER.CON_PASSWORD_NEED);
        		return;
        	}
    		$("#tip_con_password").html("");
    		newUser.checked = true;
        });
        
		$.ajax({
			type:"get",
			url:"../role/role.json",
			async:true,
			success: function(res) {
				res.unshift({
					roleId: "",
					roleName: "请选择角色",
					selected: true
				});
				
				searchRoleId = $('#confirmroleid').dropdown({ 
					uiLibrary: "bootstrap",
					dataSource: res,
					valueField: "roleId",
					textField: "roleName"
				});
			},
			error: function(err) {
				
			}
		});
		
		$("#user_cancel").click(function() {
			userDetail.close();
			
			if (userDetail != undefined) {
				userDetail.destroy();
				$("#user_detail").remove();
				$("body").children(".model").remove();
				userDetail = undefined;
			}
		});
		
		$("#user_save").click(function() {
			var newUser = {};
        	newUser.userName =  $("#user_name").val();
    		newUser.nickName =  $("#nick_name").val();
			newUser.password= $("#password").val();
			newUser.roleId= searchRoleId.val();
			
			/* 提交验证用户名密码及角色  */
			if (newUser.userName == null 
					|| newUser.userName == "") {
        		newUser.checked = false;
    			$("#tip_user_name").html("");
				$("#tip_user_name").html(MESSAGE.USER.USER_NAME_NEED);
				$("#tip_user_name").css("color", "red");
				return;
			}
			if (newUser.password == null 
					|| newUser.password == "") {
        		newUser.checked = false;
    			$("#tip_password").html("");
				$("#tip_password").html(MESSAGE.USER.USER_PASSWORD_NEED);
				$("#tip_password").css("color", "red");
				return;
			}
			if ($("#con_password").val() == null 
					|| $("#con_password").val() == "") {
        		newUser.checked = false;
    			$("#tip_con_password").html("");
				$("#tip_con_password").html(MESSAGE.USER.CON_PASSWORD_NEED);
				$("#tip_con_password").css("color", "red");
				return;
			}
			if (newUser.roleId == null
					|| newUser.roleId == "") {
				newUser.checked = false;
				$("#tip_role_id").html("");
				$("#tip_role_id").html(MESSAGE.USER.USER_NEED_ROLE);
				$("#tip_role_id").css("color", "red");
				return;
			}
		
        	var dataPut = JSON.stringify(newUser);
        	
        	$.ajax({
        		type: "PUT",
        		url: "../user/info",
        		async: true,
        		data: dataPut,
        		contentType: "application/json",
        		success: function(res) {
        			var obj = JSON.parse(res);
        			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
        				MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
        					if (userDetail != undefined) {
        						userDetail.destroy();
		        				$("#user_detail").remove();
		        				$("body").children(".modal").remove();
		        				userDetail = undefined;
		        				grid.reload();
		        			}
        				});
        			}
        		},
        		error: function(err) {
        			console.log("error");
        		}
        	});
        });
		userDetail.open("新增角色");
	});
});