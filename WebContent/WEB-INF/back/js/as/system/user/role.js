var grid; 
var roleDetail;		

function editRole(e) {
	console.log(e.data.record.roleId);
	
	$.ajax({
		type: "GET",
		url: "../role/info",
		data: {"roleId": e.data.record.roleId},
		async: true,
		success: function(res) {
			console.log(res);
			var roleInfo = JSON.parse(res);
			var target = {};
			target.roleId = roleInfo.role.roleId;
			target.roleName = roleInfo.role.roleName;
			target.roleDes = roleInfo.role.roleDes;
			target.permission = {};
			for (var index in roleInfo.rolePermission) {
				var permissionItem = roleInfo.rolePermission[index];
				target.permission[permissionItem.permissionId] = permissionItem.permissionId;
			}
			createUpdateRole(target);
		},
		error: function(err) {
			
		}
	});
}

function createUpdateRole(targetRole) {

	updateRole = {};
	if (roleDetail != undefined) {
		roleDetail.destroy();
		$("#role_detail").remove();
		$("body").children(".model").remove();
		roleDetail = undefined;
	}

	$("body").append($("#role_detail_temp").html());

	roleDetail = $("#role_detail").dialog({
		uiLibrary: 'bootstrap',
		closeButtonInHeader: false,
		autoOpen: false,
		resizable: false,
		modal: true,
		width: "60%",
		height: "90%"
	});
	
	$("#role_id").val(targetRole.roleId);
	$("#role_name").val(targetRole.roleName);
	$("#role_des").val(targetRole.roleDes);
			
	$("#role_name").focusout(function() {
		
		if (!StringUtils.stringCheckChineseLatter($(this).val())) {
			updateRole.checked = false;
			$("#tip_role_name").css("color", "red");
			$("#tip_role_name").html(MESSAGE.USER.ROLE.ROLE_NAME_CONFIRM);
			return;
		} else if ($(this).val().length > 10) {
			updateRole.checked = false;
    		$("#tip_role_name").css("color", "red");
			$("#tip_role_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
    		return;
    	} else {
    		$.ajax({
    			url : "../role/roleNameConfirm.json",
    			type : "get",
    			async : true,
    			data : {
    				"roleName" : $("#role_name").val()
    			},
    			success : function(res) {
    				if (res[SERVICE.STATUS] === SERVICE.SUCCESS 
    						&& $("#role_name").val() != targetRole.roleName) {
    					$("#tip_role_name").css("color", "red");
    					$("#tip_role_name").html(MESSAGE.USER.ROLE.REPEAT_ROLENAME);
    					updateRole.checked = false;
    					return;
        			} else {
        				$("#tip_role_name").html("");
    					$("#tip_role_name").css("color", "green");
    					$("#tip_role_name").html(MESSAGE.STRING.CORRECT);
    					updateRole.checked = true;
        			}
    			}
    		});
    	}
		$("#tip_role_name").html("");
		updateRole.checked = true;
		updateRole.roleName = $(this).val();
    	
	});
	
	$("#role_des").focusout(function() {

		if ($(this).val().length > 0 && !StringUtils.stringCheckChineseLatter($(this).val())) {
			$("#tip_role_des").css("color", "red");
			$("#tip_role_des").html(MESSAGE.USER.ROLE.ROLE_DES_CONFIRM);
			updateRole.checked = false;
			return;
		}
		
		if ($(this).val().length > 20) {
			updateRole.checked = false;
			$("#tip_role_des").css("color", "red");
			$("#tip_role_des").html(MESSAGE.STRING.LENGTH.OVERSTEP);
			return;
		}
		
		$("#tip_role_des").html("");
		updateRole.checked = true;
		updateRole.roleDes = $(this).val();
	});

	console.log(targetRole.permission);
	
	var treePermission;
	
	$.ajax({
		type: "GET",
		url: "../permission/rolePermission.json",
		async: true,
		success: function(res) {
			console.log(res);
			
			for(var index in res) {
				var permissionItem = res[index];
				
				for(var jndex in permissionItem.children) {
					var childPermission = permissionItem.children[jndex];
					if (undefined !== targetRole.permission[childPermission.permissionId]) {
						childPermission.checked = true;
					}
				}
				
				if (undefined !== targetRole.permission[permissionItem.permissionId]) {
					permissionItem.checked = true;
				}
			}

			treePermission = $("#tree_permission").tree({
				primaryKey: 'id',
				uiLibrary: 'bootstrap',
				dataSource: res,
				checkboxes: true,
				textField: "permissionName",
				primaryKey: "permissionId"
			});

			treePermission.on('checkboxChange', function (e, $node, record, state) {
				
				if (undefined === updateRole.permission) {
					updateRole.permission = {};
					$.extend(true, updateRole.permission, targetRole.permission);
				}

				if ("checked" === state) {
					updateRole.permission[record.permissionId] = record.permissionId;
				} else if ("unchecked" === state) {
					delete updateRole.permission[record.permissionId];
				}
				
			});

		},
		error: function(err) {
		}
	});
	
	$("#role_permission_cancel").click(function() {
		treePermission.destroy();
		roleDetail.close();
		
		if (roleDetail != undefined) {
			roleDetail.destroy();
			$("#role_detail").remove();
			$("body").children(".modal").remove();
			roleDetail = undefined;
		}
	});
	
	$("#role_permission_save").click(function() {
		
		if ($("#role_name").val().length === 0) {
			MessageUtils.showMessage(MESSAGE.USER.ROLE.ROLE_NAME_NEED, MESSAGE.TYPE.INFO);
			return;
		}

		if (treePermission.getCheckedNodes().length == 0) {
			MessageUtils.showMessage(MESSAGE.USER.ROLE.PERMISSION_NEED, MESSAGE.TYPE.INFO);
			return;
		}
		
		var dataPost = {};
		dataPost.originalRole = targetRole;
		dataPost.updateRole= updateRole;

		$.ajax({
			type: "POST",
			url: "../role/info",
			async: true,
			data: JSON.stringify(dataPost),
			contentType: "application/json",
			success: function(res) {
				var obj = JSON.parse(res);
				if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
					
					MessageUtils.showMessage(MESSAGE.UPDATE_SUCCESS, MESSAGE.TYPE.INFO, function() {
						if (roleDetail != undefined) {
							roleDetail.destroy();
							$("#role_detail").remove();
							$("body").children(".modal").remove();
							roleDetail = undefined;
							grid.reload();
						}
					});
				} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
					MessageUtils.showMessage(SERVICE.MESSAGE_BODY, MESSAGE.TYPE.INFO, function() {
						if (roleDetail != undefined) {
							roleDetail.destroy();
							$("#role_detail").remove();
							$("body").children(".modal").remove();
							roleDetail = undefined;
						}
					});
				}
			},
			error: function(err) {
				console.log("error");
			}
		});
	});
	roleDetail.open("修改角色");
}

function deleteRole(e) {
	MessageUtils.showConfirmMessage(
		MESSAGE.USER.ROLE.ROLE_DELETE_CONFIRM, 
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "DELETE",
				url: "../role/info/"+e.data.record.roleId,
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

function initRoleData(where) {
	grid = $('#grid').grid({
        primaryKey: 'roleId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
            { title: "ID", field: 'roleId', width: "5%" },
            { title: '角色名', field: 'roleName', width: "20%" },
            { title: '角色描述', field: 'roleDes', width: "30%" },
            { title: '创建者', field: 'cuName', width: "15%" },
            { title: '创建时间', field: 'ct', width: "20%", type: "date", format:'yyyy/mm/dd HH:MM:ss' },
            { title: '', field: 'Edit', width: "5%", type: 'icon', icon: 'glyphicon-pencil', tooltip: '编辑角色', events: { 'click': editRole } },
            { title: '', field: 'Delete', width: "5%", type: 'icon', icon: 'glyphicon-remove', tooltip: '删除角色', events: { 'click': deleteRole } }
        ],
        pager: { limit: 10, sizes: [] }
    });
}

$(document).ready(function() {
	
    initRoleData({
    	url: "../role/roleList.json", 
    	data: {}, 
    	success: function(res) {
    		grid.render(res);
    	}, 
    	error: function(err) {
    	}
    });
    
    $("#btn_search").click(function(){
    	grid.clear();
		grid.destroy();
		$("#grid").children().each(function() {
			$(this).remove();
		});
		
		var data = {};
		data.roleName = $("#search_rolename").val();
		
		initRoleData({
        	url: "../role/roleList.json", 
        	data: data,
        	success: function(res) {
        		grid.render(res);
        	}, 
        	error: function(err) {
        		
        	}
        });
		
    });
    
    $("#btn_newrole").click(function() {
		if (roleDetail != undefined) {
			roleDetail.destroy();
			$("#role_detail").remove();
			$("body").children(".model").remove();
			roleDetail = undefined;
		}

		$("body").append($("#role_detail_temp").html());

		roleDetail = $("#role_detail").dialog({
            uiLibrary: 'bootstrap',
            closeButtonInHeader: false,
            autoOpen: false,
            resizable: false,
            modal: true,
            width: "60%",
            height: "90%"
		});
 
        var newRole = {};
        newRole.permission = {};
        newRole.checked = true;
        
        $("#role_name").focusout(function() {
        	if (!StringUtils.stringCheckChineseLatter($(this).val())) {
        		newRole.checked = false;
				$("#tip_role_name").css("color", "red");
				$("#tip_role_name").html(MESSAGE.USER.ROLE.ROLE_NAME_CONFIRM);
				return;
			} else if ($(this).val().length > 10) {
        		newRole.checked = false;
        		$("#tip_role_name").css("color", "red");
				$("#tip_role_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
        		return;
        	} else {
        		$.ajax({
        			url : "../role/roleNameConfirm.json",
        			type : "get",
        			async : true,
        			data : {
        				"roleName" : $("#role_name").val()
        			},
        			success : function(res) {
        				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
        					$("#tip_role_name").css("color", "red");
        					$("#tip_role_name").html(MESSAGE.USER.ROLE.REPEAT_ROLENAME);
        					newRole.checked = false;
        					return;
            			} else {
            				$("#tip_role_name").html("");
        					$("#tip_role_name").css("color", "green");
        					$("#tip_role_name").html(MESSAGE.STRING.CORRECT);
        					newRole.checked = true;
            			}
        			}
        		});
        	}
			$("#tip_role_name").html("");
			newRole.checked = true;
	    	newRole.roleName = $(this).val();
        });
        
        $("#role_des").focusout(function() {
        	if (!StringUtils.stringCheckChineseLatter($(this).val())) {
				$("#tip_role_des").css("color", "red");
				$("#tip_role_des").html(MESSAGE.USER.ROLE.ROLE_DES_CONFIRM);
				return;
			}
			if ($(this).val().length > 20) {
				$("#tip_role_des").css("color", "red");
				$("#tip_role_des").html(MESSAGE.STRING.LENGTH.OVERSTEP);
				return;
			}
    		$("#tip_role_des").html("");
        	newRole.roleDes = $(this).val();
        });
		
		var treePermission = $("#tree_permission").tree({
            primaryKey: 'id',
            uiLibrary: 'bootstrap',
            dataSource: '../permission/rolePermission.json',
            checkboxes: true,
            textField: "permissionName",
            primaryKey: "permissionId"
        });
		
		treePermission.on('checkboxChange', function (e, $node, record, state) {
           
			if ("checked" === state) {
				newRole.permission[record.permissionId] = record.permissionId;
			} else if ("unchecked" === state) {
				delete newRole.permission[record.permissionId];
			}
			
        });
		
		$("#role_permission_cancel").click(function() {
			treePermission.destroy();
			roleDetail.close();
			
			if (roleDetail != undefined) {
				roleDetail.destroy();
				$("#role_detail").remove();
				$("body").children(".modal").remove();
				roleDetail = undefined;
			}
		});
		
		$("#role_permission_save").click(function() {
			
			if ($("#role_name").val().trim().length === 0) {
				MessageUtils.showMessage(MESSAGE.USER.ROLE.ROLE_NAME_NEED, MESSAGE.TYPE.INFO);
        		return;
			}
			
			
			if (!newRole.checked) {
				MessageUtils.showMessage(MESSAGE.SCREEN.NOT_FINISH, MESSAGE.TYPE.INFO);
        		return;
			}
			
			
        	if (treePermission.getCheckedNodes().length == 0) {
				MessageUtils.showMessage(MESSAGE.USER.ROLE.PERMISSION_NEED, MESSAGE.TYPE.INFO);
        		return;
        	}
        	
        	var dataPut = JSON.stringify(newRole);
        	
        	$.ajax({
        		type: "PUT",
        		url: "../role/info",
        		async: true,
        		data: dataPut,
        		contentType: "application/json",
        		success: function(res) {
        			var obj = JSON.parse(res);
        			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
        				MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
        					if (roleDetail != undefined) {
		        				roleDetail.destroy();
		        				$("#role_detail").remove();
		        				$("body").children(".modal").remove();
		        				roleDetail = undefined;
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
		roleDetail.open("新增角色");
	});
});