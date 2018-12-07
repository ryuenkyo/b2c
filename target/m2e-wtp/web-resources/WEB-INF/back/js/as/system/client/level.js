var grid;
var levelDetail;

function initlevelData(where) {
	grid = $('#grid').grid({
        primaryKey: 'level',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
            { title: "ID", field: 'level', width: "5%" },
            { title: '等级名称', field: 'levelName', width: "15%" },
            { title: '等级点数', field: 'levelPoint', width: "15%" },
            { title: '更新者', field: 'uu', width: "15%", },
            { title: '更新时间', field: 'ut', width: "25%", type: "date", format:'yyyy/mm/dd HH:MM:ss' },
            { title: '', field: 'Edit', width: "5%", type: 'icon', icon: 'glyphicon-pencil', tooltip: '编辑等级', events: { 'click': editLevel } },
            { title: '', field: 'Delete', width: "5%", type: 'icon', icon: 'glyphicon-remove', tooltip: '删除等级', events: { 'click': deleteLevel } }
        ]
    });
    
}
			
$(document).ready(function() {
	initlevelData({
    	url: "../client/levelList.json", 
    	data: {}, 
    	success: function(res) {
    		grid.render(res);
    	}, 
    	error: function(err) {
    		
    	}
    });
    /*新增按钮点击事件*/
	$("#btn_newlevel").click(function() {
		if (levelDetail != undefined) {
			levelDetail.destroy();
			$("#level_detail").remove();
			$("body").children(".model").remove();
			levelDetail = undefined;
		}

		$("body").append($("#level_detail_temp").html());

		levelDetail = $("#level_detail").dialog({
            uiLibrary: 'bootstrap',
            closeButtonInHeader: false,
            autoOpen: false,
            resizable: false,
            modal: true,
            width: "60%",
            height: "60%"
		});
 
        var newLevel = {};
        newLevel.levelName = {};
        
        /*新增等级名焦点离开*/
        $("#level_name").focusout(function() {
        	/*汉字验证*/
        	if (!StringUtils.stringCheckChineseLatter($(this).val())) {
        		newLevel.checked = false;
				$("#tip_level_name").css("color", "red");
				$("#tip_level_name").html(MESSAGE.USER.ROLE.ROLE_NAME_CONFIRM);
				return;
			}
        	/*长度验证*/
			else if ($(this).val().length > 5) {
        		newLevel.checked = false;
        		$("#tip_level_name").css("color", "red");
				$("#tip_level_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
        		return;
        	}
			/*重名校验*/
			else {
        		$.ajax({
        			url : "../client/levelNameConfirm.json",
        			type : "get",
        			async : true,
        			data : {
        				"levelName" : $("#level_name").val()
        			},
        			success : function(res) {
        				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
        					$("#tip_level_name").css("color", "red");
        					$("#tip_level_name").html(MESSAGE.CLIENT.REPEAT_LEVELNAME);
        					newLevel.checked = false;
        					return;
            			} else {
            				$("#tip_level_name").html("");
        					$("#tip_level_name").css("color", "green");
        					$("#tip_level_name").html(MESSAGE.STRING.CORRECT);
        					newLevel.checked = true;
            			}
        			}
        		});
        	}
			$("#tip_level_name").html("");
	    	newLevel.levelName = $(this).val();
        });
       /*  新增等级焦点离开事件*/
        $("#level_point").focusout(function() {
        	var number = $('#level_point').val(),
       	             n = /^[1-9]\d*$/; 
       	/*    是否是纯数字验证*/
       	    if(!n.test(number)){
       		    $("#tip_level_point").css("color", "red");
				$("#tip_level_point").html(MESSAGE.CLIENT.LEVEL_POINT_CONFIRM);
                return;
           }
       	  /*  长度验证*/
       	    if ($(this).val().length > 11) {
				$("#tip_level_point").css("color", "red");
				$("#tip_level_point").html(MESSAGE.STRING.LENGTH.OVERSTEP);
				return;
		   }
          /* 判断增加的等级是否在超过上一个区间*/
            else {
	    		$.ajax({
	    			url : "../client/levelPointConfirm.json",
	    			type : "get",
	    			async : true,
	    			data : {
	    				"level" : $('#level_point').val()
	    			},
	    			success : function(res) {
	    				if (res[SERVICE.STATUS] === SERVICE.SUCCESS ) {
	    					$("#tip_level_point").css("color", "red");
	    					$("#tip_level_point").html("增添等级请大于上一个区间");
	    					
	    					return;
	        			} else {
	        				$("#tip_level_point").html("");
	    					$("#tip_level_point").css("color", "green");
	    					$("#tip_level_point").html("可添加此数据");
	    					
	        			}
	    			}
	    		});
            }
           
        	newLevel.levelPoint = $(this).val();
        });
		/*取消点击事件*/
		$("#level_cancel").click(function() {
			levelDetail.close();
			/*等级说明非验证*/
			if (levelDetail != undefined) {
				levelDetail.destroy();
				$("#level_detail").remove();
				$("body").children(".modal").remove();
				levelDetail = undefined;
			}
		});
		/*保存点击事件*/
		$("#level_save").click(function() {
			var number = $('#level_point').val(),
	        n = /^[1-9]\d*$/; 
	       /*  汉字验证*/
			if (!StringUtils.stringCheckChineseLatter($("#level_name").val())) {
        		newLevel.checked = false;
				$("#tip_level_name").css("color", "red");
				$("#tip_level_name").html(MESSAGE.USER.ROLE.ROLE_NAME_CONFIRM);
				return;
			}
		  /*	长度验证*/
			else if ($("#level_name").val().length > 5) {
        		newLevel.checked = false;
        		$("#tip_level_name").css("color", "red");
				$("#tip_level_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
        		return;
        	}
		/*	纯数字验证*/
			else if(!n.test(number)){
		        $("#tip_level_point").css("color", "red");
		        $("#tip_level_point").html(MESSAGE.CLIENT.LEVEL_POINT_CONFIRM);		
                return;
          /*  等级长度验证*/
			}else if($('#level_point').val().length > 11) {
				$("#tip_level_point").css("color", "red");
				$("#tip_level_point").html(MESSAGE.STRING.LENGTH.OVERSTEP);
				return;
			}
			/*是否重名验证*/
           var i ;
			$.ajax({
        			url : "../client/levelNameConfirm.json",
        			type : "get",
        			async : false,
        			
        			data : {
        				"levelName" : $("#level_name").val()
        			},
        			success : function(res) {
        				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {        					
        					return;
            			} 
        			}
        		});		
	        	if(i ==1){
	        		$("#tip_level_name").css("color", "red");
					$("#tip_level_name").html(MESSAGE.CLIENT.REPEAT_LEVELNAME);
					i = 1;
	        		return
	        	}var a ;
	        	  /* 判断增加的等级是否在超过上一个区间*/
	         		$.ajax({
	    			url : "../client/levelPointConfirm.json",
	    			type : "get",
	    			async : false,
	    			data : {
	    				"level" : $('#level_point').val()
	    			},
	    			success : function(res) {
	    				if (res[SERVICE.STATUS] === "SUCCESS" ) {
	    				
	    					a = 1;
	    					return;
	        		} else {
	    					a = 0;
	        			}
	    			}
	    		});
	    	if(a == 1){
	    		$("#tip_level_point").css("color", "red");
	    		$("#tip_level_point").html("增添等级请大于上一个区间");
	    		return;
	    	}
	    	/*把dataPut传进url,准备进行put*/
			var dataPut = JSON.stringify(newLevel);
        	$.ajax({
        		type: "PUT",
        		url: "../client/level/info",
        		async: false,
        		data: dataPut,
        		contentType: "application/json",
        		success: function(res) {
        			var obj = JSON.parse(res);
        			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
        				MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
        					if (levelDetail != undefined) {
        						levelDetail.destroy();
        						$("#level_detail").remove();
        						$("body").children(".modal").remove();
        						levelDetail = undefined;
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
		levelDetail.open("新增等级");
	});
});
/**
 * 修改等级时取修改等级的id，通过id，查信息
 * @param target
 */
function editLevel(e) {				
console.log(e.data.record.level);
/*ajax取值准备进行付值*/
$.ajax({
	type: "GET",
	url: "../client/postLevelName",
	data: {"level": e.data.record.level},
	async: true,
	success: function(res) {
		console.log(res);
		var levelInfo = JSON.parse(res);
		var target = {};
		target.level = levelInfo.clientlevel.level;
		target.levelName = levelInfo.clientlevel.levelName;
		target.levelPoint = levelInfo.clientlevel.levelPoint;
		console.log(target);
		createUpdateLevel(target);
	},
	error: function(err) {
		
	}
});
}
/**
 * 修改等级函数
 * @param target
 */
function createUpdateLevel(target) {
	updateLeve = {};
	
	$("body").append($("#level_updetail_temp").html());

	levelUpDetail = $("#level_updetail").dialog({
		uiLibrary: 'bootstrap',
		closeButtonInHeader: false,
		autoOpen: false,
		resizable: false,
		modal: true,
		width: "90%",
		height: "90%"
	});
	upLevelConfirm ={};
	upLevelConfirm.level = target.level 
	updateLeve.level = target.level;
	/*把值带到修改空上*/
	$("#uplevel_name").val(target.levelName);
	$("#uplevel_point").val(target.levelPoint);
	/*	取消点击事件*/
	$("#uplevel_cancel").click(function() {	
		levelUpDetail.destroy();
		$("#level_updetail").remove();
		$("body").children(".modal").remove();
		levelUpDetail = undefined;
		grid.reload();});
	/*修改等级名焦点离开事件*/
	$("#uplevel_name").focusout(function(){
		/*验证是否为纯汉字验证*/
		if (!StringUtils.stringCheckChineseLatter($(this).val())) {
        
				$("#tip_uplevel_name").css("color", "red");
				$("#tip_uplevel_name").html(MESSAGE.USER.ROLE.ROLE_NAME_CONFIRM);
				return;
		}
		/*长度验证*/
		else if ($(this).val().length > 5) {
        		newLevel.checked = false;
        		$("#tip_uplevel_name").css("color", "red");
				$("#tip_uplevel_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
        		return;
        }
	/*	重名验证*/
		else {
        		$.ajax({
        			contentType: "application/json",
        			url : "../client/levelNameConfirm.json",
        			type : "GET",
        			async : true,
        			data : {
        				"levelName" : $("#uplevel_name").val()
        			},
        			success : function(res) {
        				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
        					$("#tip_uplevel_name").css("color", "red");
        					$("#tip_uplevel_name").html(MESSAGE.CLIENT.REPEAT_LEVELNAME);
        				
        					return;
            			} else {
            				$("#tip_uplevel_name").html("");
        					$("#tip_uplevel_name").css("color", "green");
        					$("#tip_uplevel_name").html(MESSAGE.STRING.CORRECT);
        					
            			}
        			}
        		});
        	}
		
		updateLeve.leveName = $(this).val();
	});
	/*修改等级对应值的焦点离开事件*/
	$("#uplevel_point").focusout(function(){
	  	var number = $(this).val(),
	             n = /^[1-9]\d*$/; 
	    if(!n.test(number)){
		    $("#tip_level_point").css("color", "red");
			$("#tip_level_point").html(MESSAGE.CLIENT.LEVEL_POINT_CONFIRM);
	        return;
	   }if ($(this).val().length > 11) {
			$("#tip_level_point").css("color", "red");
			$("#tip_level_point").html(MESSAGE.STRING.LENGTH.OVERSTEP);
			return;
	   }
	    
	    upLevelConfirm.leveName = $(this).val();
		upLevelConfirm.levePoint = $("#uplevel_point").val();
		/*验证输入值要大于上一个区间*/
		       $.ajax({
        			url : "../client/updateLevelPointConfirm.json",
        			type : "POST",
        			async : true,
        			data: JSON.stringify(upLevelConfirm),
        			headers : {  
                        'Content-Type' : 'application/json;charset=utf-8'  
                    },  
        			success : function(res) {
        				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
        					$("#tip_level_point").css("color", "green");
        				
        					$("#tip_level_point").html("修改值在区间");
        				
        				
            			} else {
            				$("#tip_level_point").html("");
            				$("#tip_level_point").css("color", "red");
        					$("#tip_level_point").html("修改值不在区间");
        					return;
        					
            			}
        			}
        		});
		updateLeve.levePoint = $(this).val();
	});
    /*修改点击事件*/
	$("#uplevel_save").click(function() {
		updateLeve.leveName = $("#uplevel_name").val();
		updateLeve.levePoint = $("#uplevel_point").val();
		var number = $("#uplevel_point").val(),
        n = /^[1-9]\d*$/; 
    /*    是否为纯汉字验证*/
		if (!StringUtils.stringCheckChineseLatter($("#uplevel_name").val())) {
    	
			$("#tip_uplevel_name").css("color", "red");
			$("#tip_uplevel_name").html(MESSAGE.USER.ROLE.ROLE_NAME_CONFIRM);
			return;
		}
		/*修改名的长度验证*/
		else if ($("#uplevel_name").val().length > 5) {
    	
    		$("#uplevel_name").css("color", "red");
			$("#tip_uplevel_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
    		return;
    	}
		/*是否为纯数字验证*/
		else if(!n.test(number)){
	        $("#tip_level_point").css("color", "red");
	        $("#tip_level_point").html(MESSAGE.CLIENT.LEVEL_POINT_CONFIRM);		
            return;
        }
	/*修改后的长度验证*/
		else if($("#uplevel_point").val().length > 11) {
			$("#tip_level_point").css("color", "red");
			$("#tip_level_point").html(MESSAGE.STRING.LENGTH.OVERSTEP);
			return;
		}else if ($("#uplevel_point").val().length > 11) {
			$("#tip_level_point").css("color", "red");
			$("#tip_level_point").html(MESSAGE.STRING.LENGTH.OVERSTEP);
			return;
		}
		upLevelConfirm.level = target.level;
		upLevelConfirm.levePoint = $("#uplevel_point").val();
		/*点击事件,修改数值是否在这个区间内*/
		
		var a = 0;
		 $.ajax({
        			url : "../client/updateLevelPointConfirm.json",
        			type : "POST",
        			async : false,
        			data: JSON.stringify(upLevelConfirm),
        			headers : {  
                        'Content-Type' : 'application/json;charset=utf-8'  
                    },  
        			success : function(res) {
        				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
        					$("#tip_level_point").css("color", "green");
        					$("#tip_level_point").html("修改值在区间");
            			} else {
            				a = 1;
            			}
        			}
        		});
		
		if (a==1) {
			$("#tip_level_point").html("");
           	$("#tip_level_point").css("color", "red");
     		$("#tip_level_point").html("修改值不在区间");
        	return;
		}
		/*是否重名验证*/
		var a = $("#uplevel_name").val();
		var b = target.levelName
		/*确保在未做修改时直接点击确定*/
		if (a!=b) {
		var p = 0;
		$.ajax({
    			contentType: "application/json",
    			url : "../client/levelNameConfirm.json",
    			type : "GET",
    			async : false,
    			data : {
    				"levelName" : $("#uplevel_name").val()
    			},
    			success : function(res) {
    				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
    					p = 1;
    					
    					return;
        			} else {
        				p = 0;
        			}
    			}
        		});
		 if (p == 1) {
			$("#tip_uplevel_name").css("color", "red");
    		$("#tip_uplevel_name").html(MESSAGE.CLIENT.REPEAT_LEVELNAME);		
        	return;
		}
		}

		/*角色更新*/
		$.ajax({
			type: "POST",
			url: "../client/level/info",
			async: true,
			data: JSON.stringify(updateLeve),
			contentType: "application/json",
			success: function(res) {
				var obj = JSON.parse(res);
				if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
					
					MessageUtils.showMessage(MESSAGE.UPDATE_SUCCESS, MESSAGE.TYPE.INFO, function() {
						
						levelUpDetail.destroy();
						$("#level_updetail").remove();
						$("body").children(".modal").remove();
						levelUpDetail = undefined;
						grid.reload();
					});
				} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
					MessageUtils.showMessage(SERVICE.MESSAGE_BODY, MESSAGE.TYPE.INFO, function() {
						if (levelUpDetail != undefined) {
							$("#level_updetail_temp").remove();
							$("body").children(".modal").remove();
							levelUpDetail = undefined;
						}
					});
				}
			},
			error: function(err) {
				console.log("error");
			}
		});

		
	});

	levelUpDetail.open("修改角色");
	
}
/**
 * 删除等级
 * @param {Object} e
 */
function deleteLevel(e) {
	MessageUtils.showConfirmMessage(
		MESSAGE.CLIENT.LEVEL_DELETE_CONFIRM, 
		MESSAGE.TYPE.INFO, 
	/*	ajax把id传入准备进行删除*/
		function() {
			$.ajax({
				type: "DELETE",
				url: "../client/level/info/"+e.data.record.level,
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
