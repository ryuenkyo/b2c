var grid;
var permissionDetail; 
var permissionType; 
var permissionParent;

function editPermission(e) {
	console.log(e.data.record.permissionId);
	
	$.ajax({
		type: "GET",
		url: "../permission/info",
		contentType: "application/json",
		data: {"permissionId": e.data.record.permissionId},
		async: true,
		success: function(res) {
			var obj = JSON.parse(res);
			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
				createUpdatePermission(obj.permission);
			}
		},
		error: function(err) {
		}
	});
}

function createUpdatePermission(targetPermission) {
	
	var updatePermission = {};

	$("body").append($("#permission_update_temp").html());
	
	permissionDetail = $("#permission_update").dialog({
        uiLibrary: 'bootstrap',
        closeButtonInHeader: false,
        autoOpen: false,
        resizable: false,
        modal: true,
        width: "60%",
        height: "80%"
    });
    
    $("#permission_name").val(targetPermission.permissionName);
    
    $("#permission_name").focusout(function() {

		if (!StringUtils.stringCheckChineseLatter($(this).val())) {
			updatePermission.checked = false;
			$("#tip_permission_name").css("color", "red");
			$("#tip_permission_name").html(MESSAGE.USER.PERMISSION.PERMISSION_NAME_CONFIRM);
			return;
		} else if ($(this).val().length > 10) {
			updatePermission.checked = false;
    		$("#tip_permission_name").css("color", "red");
			$("#tip_permission_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
    		return;
    	} else {
    		$.ajax({
    			url : "../permission/permissionNameConfirm.json",
    			type : "get",
    			async : true,
    			data : {
    				"permissionName" : $("#permission_name").val()
    			},
    			success : function(res) {
    				if (res[SERVICE.STATUS] === SERVICE.SUCCESS
    						&& $("#permission_name").val() != targetPermission.permissionName) {
    					$("#tip_permission_name").css("color", "red");
    					$("#tip_permission_name").html(MESSAGE.USER.PERMISSION.REPEAT_PERMISSIONNAME);
    					updatePermission.checked = false;
    					return;
        			} else {
        				$("#tip_permission_name").html("");
    					$("#tip_permission_name").css("color", "green");
    					$("#tip_permission_name").html(MESSAGE.STRING.CORRECT);
    					updatePermission.checked = true;
        			}
    			}
    		});
    	}

		$("#tip_permission_name").html("");
		updatePermission.checked = true;
		updatePermission.permissionName = $(this).val();
	});

    $("#permission_context").val(targetPermission.permissionContext);
    
	$("#permission_context").focusout(function() {
		
		if (!StringUtils.stringCheckUrl($(this).val())) {
			$("#tip_permission_context").css("color", "red");
			$("#tip_permission_context").html(MESSAGE.USER.PERMISSION.PERMISSION_CONTEXT);
			return;
		}
    	if ($(this).val().length > 20) {
			$("#tip_permission_context").css("color", "red");
			$("#tip_permission_context").html(MESSAGE.STRING.LENGTH.OVERSTEP);
			return;
		}
    	$("#tip_permission_context").html("/xxxxx/xxxx.html");
		updatePermission.checked = true;
		updatePermission.permissionContext = $(this).val();
	});

	permissionType = $('#permission_type_update').dropdown({ 
		uiLibrary: "bootstrap",
		dataSource: "../permission/PermissionType.json",
		valueField: "emnuName",
		textField: "emnuValue",
		dataBound: function(e) {
			permissionType.value(targetPermission.permissionType);
		}
	});
	
	permissionType.on("change", function(e) {
		updatePermission.permissionType = permissionType.value();
	});
	
	permissionParent = $('#permission_parent_update').dropdown({ 
		uiLibrary: "bootstrap",
		dataSource: "../permission/permissionParent.json",
		valueField: "permissionId",
		textField: "permissionName",
		dataBound: function(e) {
			permissionParent.value(targetPermission.parentId);
		}
	});
	
	permissionParent.on("change", function(e) {
		updatePermission.parentId = permissionParent.value();
	});
	
	$("#role_permission_cancel").click(function() {
		permissionDetail.close();
		
		if (permissionDetail != undefined) {
			permissionDetail.destroy();
			$("#permission_update").remove();
			$("body").children(".modal").remove();
			permissionDetail = undefined;
		}
	});
	
	$("#role_permission_save").click(function() {

		if (typeof(updatePermission.checked) === "boolean" && !updatePermission.checked) {
			MessageUtils.showMessage(MESSAGE.SCREEN.NOT_FINISH, MESSAGE.TYPE.INFO);
			return;
		}
		
		if ($("#permission_name").val().length === 0) {
			MessageUtils.showMessage(MESSAGE.USER.PERMISSION.PERMISSION_NAME_NEED, MESSAGE.TYPE.INFO);
			return;
		}
		
		if (permissionType.val().length == 0) {
			MessageUtils.showMessage(MESSAGE.USER.ROLE.PERMISSION_TYPE_NEED, MESSAGE.TYPE.INFO);
			return;
		}
		
		if (permissionParent.val().length == 0) {
			MessageUtils.showMessage(MESSAGE.USER.ROLE.PERMISSION_NEED, MESSAGE.TYPE.INFO);
			return;
		}
		
		var dataPut = {};
		dataPut.originalPermission = targetPermission;
		dataPut.updatePermission = updatePermission;
		$.ajax({
			type:"POST",
			url:"../permission/info",
			contentType: "application/json",
			async:true,
			data: JSON.stringify(dataPut),
			success: function(res) {
				var obj = JSON.parse(res);
				if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
					
					MessageUtils.showMessage(MESSAGE.UPDATE_SUCCESS, MESSAGE.TYPE.INFO, function() {
						if (permissionDetail != undefined) {
							permissionDetail.destroy();
							$("#permission_update").remove();
							$("#permission_update_temp").nextAll().each(function() {
								$(this).remove();
							});
							permissionDetail = undefined;
							grid.reload();
						}
					});
				} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
					MessageUtils.showMessage(SERVICE.MESSAGE_BODY, MESSAGE.TYPE.INFO, function() {
						if (permissionDetail != undefined) {
							permissionDetail.destroy();
							$("#permission_update").remove();
							$("body").children(".modal").remove();
							permissionDetail = undefined;
						}
					});
				}
			},
			error: function(err) {
				
			}
		});
		
	});
	permissionDetail.open("权限修改");
}

function deletePermission(e) {
     MessageUtils.showConfirmMessage(
		MESSAGE.USER.PERMISSION.PERMISSION_DELETE_CONFIRM,
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "DELETE",
				url: "../permission/info/"+e.data.record.permissionId,
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

function initPermissionData(where) {
	grid = $('#grid').grid({
        primaryKey: 'permissionId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
            { title: "ID", field: 'permissionId', width: "5%" },
            { title: '权限名', field: 'permissionName', width: "10%" },
            { title: '权限类型', field: 'permissionType', width: "10%" },
            { title: '权限内容', field: 'permissionContext', width: "30%" },
            { title: '父级权限', field: 'parentName', width: "10%" },
            { title: '创建者', field: 'cuName', width: "10%" },
            { title: '创建时间', field: 'ct', width: "15%", type: "date", format:'yyyy/mm/dd HH:MM:ss'},
            { title: '', field: 'Edit', width: "5%", type: 'icon', icon: 'glyphicon-pencil', tooltip: '编辑权限', events: { 'click': editPermission } },
            { title: '', field: 'Delete', width: "5%", type: 'icon', icon: 'glyphicon-remove', tooltip: '删除权限', events: { 'click': deletePermission } }
        ],
        pager: { limit: 10, sizes: [] }
    });
}

$(document).ready(function() {

	initPermissionData({
    	url: "../permission/permissionList.json", 
    	data: {}, 
    	success: function(res) {
    		grid.render(res);
    	}, 
    	error: function(err) {
    	}
    });
    
   
    
    $("#btn_newpermission").click(function() {
		
		if (permissionDetail != undefined) {
			permissionDetail.destroy();
			$("#permission_detail").remove();
			$("body").children(".model").remove();
			permissionDetail = undefined;
		}
		
		$("body").append($("#permission_detail_temp").html());
		
		 permissionDetail = $("#permission_detail").dialog({
            uiLibrary: 'bootstrap',
            closeButtonInHeader: false,
            autoOpen: false,
            resizable: false,
            modal: true,
            width: "60%",
            height: "90%"
        });
		
		 var newPermission = {};
		 newPermission.permission = {};
		 newPermission.checked = true;
        
        $("#permission_name").focusout(function() {
        	if (!StringUtils.stringCheckChineseLatter($(this).val())) {
        		newPermission.checked = false;
				$("#tip_permission_name").css("color", "red");
				$("#tip_permission_name").html(MESSAGE.USER.PERMISSION.PERMISSION_NAME_CONFIRM);
				return;
			} else if ($(this).val().length > 10) {
				newPermission.checked = false;
        		$("#tip_permission_name").css("color", "red");
				$("#tip_permission_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
        		return;
        	} else {
        		$.ajax({
        			url : "../permission/permissionNameConfirm.json",
        			type : "get",
        			async : true,
        			data : {
        				"permissionName" : $("#permission_name").val()
        			},
        			success : function(res) {
        				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
        					$("#tip_permission_name").css("color", "red");
        					$("#tip_permission_name").html(MESSAGE.USER.PERMISSION.REPEAT_PERMISSIONNAME);
        					newPermission.checked = false;
        					return;
            			} else {
            				$("#tip_permission_name").html("");
        					$("#tip_permission_name").css("color", "green");
        					$("#tip_permission_name").html(MESSAGE.STRING.CORRECT);
        					newPermission.checked = true;
            			}
        			}
        		});
        	}
			$("#tip_permission_name").html("");
        	newPermission.checked = true;
        	newPermission.permissionName = $(this).val();
        });
        
        $("#permission_context").focusout(function() {
        	if (!StringUtils.stringCheckUrl($(this).val())) {
    			$("#tip_permission_context").css("color", "red");
    			$("#tip_permission_context").html(MESSAGE.USER.PERMISSION.PERMISSION_CONTEXT);
    			return;
    		}
        	if ($(this).val().length > 20) {
				$("#tip_permission_context").css("color", "red");
				$("#tip_permission_context").html(MESSAGE.STRING.LENGTH.OVERSTEP);
				return;
			}
        	$("#tip_permission_context").html("/xxxxx/xxxx.html");
    		newPermission.permissionContext = $(this).val();
        });

        $.ajax({
    		type:"get",
    		url:"../permission/permissionType.json",
    		async:true,
    		success: function(res) {
    			res.unshift({
    				emnuName: "",
    				emnuValue: "请选择权限类型",
    				selected: true
    			});
    			
    			permissionType = $('#permission_type').dropdown({ 
    				uiLibrary: "bootstrap",
    				dataSource: res,
    				valueField: "emnuName",
    				textField: "emnuValue"
    			});
    		},
    		error: function(err) {
    			
    		}
    	});
        
        $.ajax({
    		type:"get",
    		url:"../permission/permissionParent.json",
    		async:true,
    		success: function(res) {
    			res.unshift({
    				permissionId: "",
    				permissionName: "请选择父级权限",
    				selected: true
    			});
    			
    			permissionParent = $('#permission_parent').dropdown({ 
    				uiLibrary: "bootstrap",
    				dataSource: res,
    				valueField: "permissionId",
    				textField: "permissionName"
    			});
    		},
    		error: function(err) {
    			
    		}
    	});
    
		$("#role_permission_cancel").click(function() {
			permissionDetail.close();
			
			if (permissionDetail != undefined) {
				permissionDetail.destroy();
				$("#permission_detail").remove();
				$("body").children(".model").remove();
				permissionDetail = undefined;
			}
		});
		
		$("#role_permission_save").click(function() {
			var newPermission = {};
        	newPermission.permissionName =  $("#permission_name").val();
    		newPermission.permissionContext =  $("#permission_context").val();
			newPermission.parentId= permissionParent.val();
			newPermission.permissionType = permissionType.val();
			
			if ($("#permission_name").val().length === 0) {
				MessageUtils.showMessage(MESSAGE.USER.PERMISSION.PERMISSION_NAME_NEED, MESSAGE.TYPE.INFO);
				return;
			}
			
			if (permissionType.val().length == 0) {
				MessageUtils.showMessage(MESSAGE.USER.ROLE.PERMISSION_TYPE_NEED, MESSAGE.TYPE.INFO);
				return;
			}
			
			if (permissionParent.val().length == 0) {
				MessageUtils.showMessage(MESSAGE.USER.ROLE.PERMISSION_NEED, MESSAGE.TYPE.INFO);
				return;
			}
			
        	var dataPut = JSON.stringify(newPermission);
        	
        	$.ajax({
        		type: "PUT",
        		url: "../permission/info",
        		async: true,
        		data: dataPut,
        		contentType: "application/json",
        		success: function(res) {
        			var obj = JSON.parse(res);
        			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
        				MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
        					if (permissionDetail != undefined) {
        						permissionDetail.destroy();
		        				$("#permission_detail").remove();
		        				$("body").children(".modal").remove();
		        				permissionDetail = undefined;
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
		permissionDetail.open("新增权限");
	});
});