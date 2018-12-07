
var attrDetail; 
var attrValueDetail;
var DATA_SOURCE;
/**
 * 修改属性值 ,取出要修改的属性值id-点击事件
 * 对属性值 修改，传入id，调修改函数进行取值
 * @param {Object} e
 */
function editAttrValue(e) {
	console.log(e.currentTarget);
	var pAttrValueId = $(e.currentTarget).attr("p-attr-Value-id");
	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.ATTR.ATTR_VALUE_NEED, 
		MESSAGE.TYPE.INFO, 
	/*	通过ajax,从id把属性值取出*/
		function() {
			$.ajax({
				type: "GET",
				url: "../attrValue/info",
				data: {"pAttrValueId": pAttrValueId},
				async: true,
				success: function(res) {
					console.log(res);
					var pAttrInfo = JSON.parse(res);
					var target = {};
					target.pAttrValue  = pAttrInfo.pAttrNameValue;
					target.pAttrValueId = pAttrValueId;
					updateAttrValue(target);
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

/**
 * 修改属性名，通过id来取出修改的属性名-点击事件
 * 对属性名修改，传入id，调修改函数进行取值
 * @param {Object} e
 */
function editAttrName(e) {
	console.log(e.currentTarget);
	var pAttrNameId = $(e.currentTarget).attr("p-attr-Name-id");
	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.ATTR.ATTR_NAME_EDIT_CONFIRM, 
		MESSAGE.TYPE.INFO, 
		/*通过ajax来取到修改的属性名*/
		function() {
			$.ajax({
				type: "GET",
				url: "../attr/info",
				data: {"pAttrNameId": pAttrNameId},
				async: true,
				success: function(res) {
					console.log(res);
					var pAttrInfo = JSON.parse(res);
					var target = {};
					target.pAttrName = pAttrInfo.pAttrName;
					target.pAttrNameId = pAttrNameId;
					createUpdateAttr(target);
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

/**
 * 属性值修改函数
 * @param {Object} target
 */
function updateAttrValue(target){
	if (attrDetail != undefined) {
		attrDetail.destroy();
		$("#attr_detail").remove();
		attrDetail = undefined;
	}
	/*把属性值这个插件append进body中*/
	$("body").append($("#attrValue_detail_temp").html());

	attrValueDetail = $("#attrValue_detail").dialog({
        uiLibrary: "bootstrap",
        closeButtonInHeader: false,
        width: 360,
        title: "属性值修改"
    });
	
	/*给id = update_p_attrvalue_name的进行赋值*/
	$("#update_p_attrvalue_name").val(target.pAttrValue);
	var dataPost = {};
	dataPost.pAttrValueId = target.pAttrValueId;
	
	
	//to do 是否重名校验
	$("#btn_attr_value_cancel").click(function() {
    	
			attrValueDetail.destroy();
			$("#attrValue_detail").remove();
			attrValueDetail = undefined;
		
    });	
/*	保存点击事件*/
	$("#btn_attr_value_save").click(function() {
		uppost={}
		uppost.pAttrValue = $("#update_p_attrvalue_name").val();   
		/*判断是否对属性值进行了修改*/
		if(target.pAttrValue!= uppost.pAttrValue){
		        /*  是否为中文的校验*/

				/*长度限定校验:小于5*/
			 if ($("#update_p_attrvalue_name").val().length > 5) {
	     		$("#tip_up_attr_value").css("color", "red");
				$("#tip_up_attr_value").html(MESSAGE.STRING.LENGTH.OVERSTEP);
	     		return;
	     	   } 
	     	    /* 为空校验*/
	     	    else if ($("#update_p_attrvalue_name").val().trim().length == 0) {
	     		$("#tip_up_attr_value").css("color", "red");
				$("#tip_up_attr_value").html(MESSAGE.PRODUCT.ATTR.ATTR_NAME_NEED);
	     		return;
	     	}
		dataPost.pAttrValue = $("#update_p_attrvalue_name").val();
	    /*	ajax把修改值传递,进行修改*/
    	$.ajax({
    		type:"POST",
    		url:"../attrvalue/info",
			data: JSON.stringify(dataPost),
			contentType: "application/json",
    		async:true,
    		success: function(res){
    			var obj = JSON.parse(res);
    			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
    				MessageUtils.showMessage(MESSAGE.UPDATE_SUCCESS, MESSAGE.TYPE.INFO, function() {
    					if (attrValueDetail != undefined) {
							attrValueDetail.destroy();
							$("#attrValueDetail").remove();
							attrValueDetail = undefined;
						}
    					location.reload(".container-fluid");
    				});
    			} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
					MessageUtils.showMessage(SERVICE.MESSAGE_BODY, MESSAGE.TYPE.INFO, function() {
						if (attrValueDetail != undefined) {
								attrValueDetail.destroy();
								$("#attrValue_detail").remove();
								attrValueDetail = undefined;
							}
					});
			}
    		},
    		error: function(err){
    			console.log("error");
    		}
    	});
    }	
    else{
	 MessageUtils.showMessage("数据没有变化更新失败", MESSAGE.TYPE.INFO, function() {
        attrValueDetail.destroy();
		$("#attrValue_detail").remove();
		$("body").children(".modal").remove();
		attrValueDetail = undefined;
     });		
    }
    	if (attrValueDetail != undefined) {
			attrValueDetail.destroy();
			$("#attrValue_detail").remove();
			attrValueDetail = undefined;
		}
    });
}

/**
 * 属性名修改函数
 * @param {Object} target
 */
function createUpdateAttr(target){
	if (attrDetail != undefined) {
		attrDetail.destroy();
		$("#attr_detail").remove();
		attrDetail = undefined;
	}
	/*把dom组件append进body中*/
	$("body").append($("#attr_detail_temp").html());

	attrDetail = $("#attr_detail").dialog({
        uiLibrary: "bootstrap",
        closeButtonInHeader: false,
        width: 360,
        title: "属性名修改"
    });
	/*把要修改的属性值带回框中*/
	$("#new_p_attr_name").val(target.pAttrName);
	var dataPost = {};
	dataPost.pAttrNameId = target.pAttrNameId;
	/*属性名的焦点离开事件*/
	$("#new_p_attr_name").focusout(function() {
		/*验证输入值是否为中文的校验*/
     	if (!StringUtils.stringCheckChineseLatter($(this).val())) {
			$("#tip_p_attr_name").css("color", "red");
			$("#tip_p_attr_name").html(MESSAGE.STRING.LETTER.ZH_NEED);
			return;
		} 
		/*验证输入值长度*/
		else if ($(this).val().length > 5) {
     		$("#tip_p_attr_name").css("color", "red");
			$("#tip_p_attr_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
     		return;
        } 
        /* 验证非空校验*/
        else if ($(this).val().trim().length == 0) {
     		$("#tip_p_attr_name").css("color", "red");
			$("#tip_p_attr_name").html(MESSAGE.PRODUCT.ATTR.ATTR_NAME_NEED);
     		return;
     	} 
     	/* 属性名是否重名校验*/
     	  else {
     		$.ajax({
     			url : "../attrNameConfirm.json",
     			type : "get",
     			async : true,
     			data : {
     				"pAttrName" : $("#new_p_attr_name").val()
     			},
     			success : function(res) {
     				if (res[SERVICE.STATUS] === SERVICE.SUCCESS 
     		            && $("#new_p_attr_name").val() != target.pAttrName) {
     					$("#tip_p_attr_name").css("color", "red");
     				
     					$("#tip_p_attr_name").html(MESSAGE.PRODUCT.ATTR.REPEAT_ATTR_NAME);
     					return;
         			} else {
         				$("#tip_p_attr_name").html("");
     					$("#tip_p_attr_name").css("color", "green");
     					$("#tip_p_attr_name").html(MESSAGE.STRING.CORRECT);
         			}
     			}
     		});
			}

 		$("#tip_p_attr_name").html("");
 		dataPost.pAttrName = $(this).val();
     });
	/*关闭窗口*/
	$("#btn_attr_name_cancel").click(function() {
    	if (attrDetail != undefined) {
			attrDetail.destroy();
			$("#attr_detail").remove();
			attrDetail = undefined;
		}
    });	
	/*保存点击事件 */
	$("#btn_attr_name_save").click(function() {
		var oldattrname = $("#new_p_attr_name").val();
		/*判断是否对属性名做出了更新*/
		if(target.pAttrName!=oldattrname){
		/*是否重名校验*/
		 var i ;
		$.ajax({
 			url : "../attrNameConfirm.json",
 			type : "get",
 			async : false,
 			data : {
 				"pAttrName" : $("#new_p_attr_name").val()
 			},
 			success : function(res) {
 				if (res[SERVICE.STATUS] === SERVICE.SUCCESS 
 		            && $("#new_p_attr_name").val() != target.pAttrName) {
 					$("#tip_p_attr_name").css("color", "red");
 					
 					$("#tip_p_attr_name").html(MESSAGE.PRODUCT.ATTR.REPEAT_ATTR_NAME);
 					i = 1;
 					return;
     			} else {
     				i = 0;
     				$("#tip_p_attr_name").html("");
 					$("#tip_p_attr_name").css("color", "green");
 					$("#tip_p_attr_name").html(MESSAGE.STRING.CORRECT);
     			}
 			}
 		});
	  if (i===1) {
        	return;
        }
	  /*  是否为中文校验*/
	  else if (!StringUtils.stringCheckChineseLatter($("#new_p_attr_name").val())) {
			$("#tip_p_attr_name").css("color", "red");
			$("#tip_p_attr_name").html(MESSAGE.STRING.LETTER.ZH_NEED);
			return;
	  } 
	 /*长度校验*/
	  else if ($("#new_p_attr_name").val().length > 5) {
     		$("#tip_p_attr_name").css("color", "red");
			$("#tip_p_attr_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
     		return;
      } 
     /* 是否为空校验*/
      else if ($("#new_p_attr_name").val().trim().length == 0) {
     		$("#tip_p_attr_name").css("color", "red");
			$("#tip_p_attr_name").html(MESSAGE.PRODUCT.ATTR.ATTR_NAME_NEED);
     		return;
     }
     /* ajax传值进行修改*/
    	$.ajax({
    		type:"POST",
    		url:"../attr/info",
			data: JSON.stringify(dataPost),
			contentType: "application/json",
    		async:true,
    		success: function(res){
    			var obj = JSON.parse(res);
    			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
    				MessageUtils.showMessage(MESSAGE.UPDATE_SUCCESS, MESSAGE.TYPE.INFO, function() {
    					if (attrDetail != undefined) {
							attrDetail.destroy();
							$("#attr_detail").remove();
							attrDetail = undefined;
						}
    					/*进行刷新*/
    					location.reload(".container-fluid");
    				});
    			} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
					MessageUtils.showMessage(SERVICE.MESSAGE_BODY, MESSAGE.TYPE.INFO, function() {
						if (attrDetail != undefined) {
								attrDetail.destroy();
								$("#attr_detail").remove();
								attrDetail = undefined;
							}
					});
			}
    		},
    		error: function(err){
    			console.log("error");
    		}
    	});
    	}else{
    		
	    MessageUtils.showMessage("数据没有变化更新失败", MESSAGE.TYPE.INFO, function() {
            attrDetail.destroy();
			$("#attr_detail").remove();
			$("body").children(".modal").remove();
			attrDetail = undefined;
        		});		
    
    	}
    	if (attrDetail != undefined) {
			attrDetail.destroy();
			$("#attr_detail").remove();
			attrDetail = undefined;
		}
    });
}

/**
 * 删除属性值函数
 * @param {Object} e
 */
function deleteAttrValue(e) {
	console.log(e.currentTarget);
	var pAttrValueId = $(e.currentTarget).attr("p-attr-value-id");
	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.ATTR.ATTR_DELETE_CONFIRM, 
		MESSAGE.TYPE.INFO, 
		/*把删除的属性值通过ajax传递*/
		function() {
			$.ajax({
				type: "DELETE",
				url: "../attr/deleteAttr/"+pAttrValueId,
				async: true,
				success: function(res) {
					var obj = JSON.parse(res);
					if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
						MessageUtils.showMessage(MESSAGE.DELETE_SUCCESS, MESSAGE.TYPE.INFO, function() {
							var attrItem = document.getElementById("panel_attr_"+pAttrValueId);
							attrItem.remove();
						    
						});
					} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
						MessageUtils.showMessage(MESSAGE.MESSAGE_FAIL, MESSAGE.TYPE.INFO, function() {
							
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

/**
 * 填价属性值函数
 * @param {Object} e
 */
function addAttrValue(e) {
	console.log(e.currentTarget);
	
	if (attrValueDetail != undefined) {
		attrValueDetail.destroy();
		$("#attr_value_detail").remove();
		attrValueDetail = undefined;
	}
	/*把dom组件放进body中*/
	$("body").append($("#attr_value_detail_temp").html());
	
	attrValueDetail = $("#attr_value_detail").dialog({
        uiLibrary: "bootstrap",
        closeButtonInHeader: false,
        width: 360,
        title: "新增属性值"
    });
    var pAttrNameIdFrom = $(e.currentTarget).attr("p-attr-name-id");
    $("#v_p_attr_name_id").val(pAttrNameIdFrom);

    $("#v_p_attr_name").val(DATA_SOURCE.attrNameMapper[pAttrNameIdFrom].pAttrName);
    
     /*对新增属性值进行焦点离开事件*/
    $("#target").focusout(function() {
		/*判断长度*/
		 if ($(this).val().length > 5) {
     		$("#tip_p_attr_value").css("color", "red");
			$("#tip_p_attr_value").html(MESSAGE.STRING.LENGTH.OVERSTEP);
     		return;
        } 
         /*是否为空的校验*/
        else if ($(this).val().trim().length == 0) {
     		$("#tip_p_attr_value").css("color", "red");
			$("#tip_p_attr_value").html(MESSAGE.PRODUCT.ATTR.ATTR_NAME_NEED);
     		return;
     	} 
 		$("#tip_p_attr_name").html("");
 		dataPost.pAttrName = $(this).val();
    });
    
    /* 保存事件的点击事件*/
    $("#btn_attr_value_save").click(function() {
          if ($("#target").val().length > 5) {
     		$("#tip_p_attr_value").css("color", "red");
			$("#tip_p_attr_value").html(MESSAGE.STRING.LENGTH.OVERSTEP);
     		return;
        } else if ($("#target").val().trim().length == 0) {
     		$("#tip_p_attr_value").css("color", "red");
			$("#tip_p_attr_value").html(MESSAGE.PRODUCT.ATTR.ATTR_NAME_NEED);
     		return;
     	} 
    	
    	var pAttrNameId = $("#v_p_attr_name_id").val();
    
		var pAttrValue = $("#target").val();
	    /*  ajax传值,准备进行添加*/
		$.ajax({
			type:"PUT",
			url:"../attr/value/info",
			async:true,
			contentType: "application/json",
			data: JSON.stringify({"pAttrNameId": pAttrNameId, "pAttrNameValue": pAttrValue}),
			success: function(res) {
				var obj = JSON.parse(res);

				if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
					MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
						
						var attrValueItem = obj.pAttrNameValue;
						var tempAttrValueText = $("#temp_attr_value_item").html();
						var textAttrValueItem = template(tempAttrValueText, {"attrValueItem": attrValueItem});
						$("#panel_body_"+attrValueItem.pAttrNameId).append($(textAttrValueItem));
						
					
					});
				} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
					MessageUtils.showMessage(obj[SERVICE.MESSAGE_BODY], MESSAGE.TYPE.INFO);
				}
				
			},
			error: function(err) {
				
			}
		});
	   	attrValueDetail.destroy();
		$("#attr_value_detail").remove();
		attrValueDetail = undefined;
    	
    });
    /*  取消点击事件*/
    $("#btn_attr_value_cancel").click(function() {
    	
    	
			attrValueDetail.destroy();
			$("#attr_value_detail").remove();
			attrValueDetail = undefined;
		
    	
    });
    
    attrValueDetail.open();
}

/**
 * 删除属性名函数
 * @param {Object} e
 */
function deleteAttrName(e){
	console.log(e.currentTarget);
	var pAttrNameId = $(e.currentTarget).attr("p-attr-name-id");
	
	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.ATTR.ATTR_NAME_DELETE_CONFIRM, 
		MESSAGE.TYPE.INFO, 
		/*把属性名通过ajax进行传递,进行删除*/
		function() {
			$.ajax({
				type: "DELETE",
				url: "../attr/deleteAttrName/"+pAttrNameId,
				async: true,
				success: function(res) {
					var obj = JSON.parse(res);
					if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
						MessageUtils.showMessage(MESSAGE.DELETE_SUCCESS, MESSAGE.TYPE.INFO, function() {
							var attrItem = document.getElementById("panel_attr_"+pAttrValueId);
							attrItem.remove();
							attr.js.reload()   
						});
						location.reload(".container-fluid");
					} else  {
						MessageUtils.showMessage(MESSAGE.MESSAGE_FAIL, MESSAGE.TYPE.INFO, function() {
							
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

$(document).ready(function() {
	/*新增点击事件*/
	$("#btn_new_attr").click(function() {
		if (attrDetail != undefined) {
			attrDetail.destroy();
			$("#attr_detail").remove();
			attrDetail = undefined;
		}
        /*在body中添加dom组件*/
		$("body").append($("#attr_detail_temp").html());
		attrDetail = $("#attr_detail").dialog({
	        uiLibrary: "bootstrap",
	        closeButtonInHeader: false,
	        width: 360,
	        title: "新增属性"
	    });
		/*声明datapost*/
		var dataPost = {};
		/*新增属性名时的焦点离开事件*/
		$("#new_p_attr_name").focusout(function() {
			    /*对文本框中是否为中文字符进行判断*/
	     	if (!StringUtils.stringCheckChineseLatter($(this).val())) {
				$("#tip_p_attr_name").css("color", "red");
				$("#tip_p_attr_name").html(MESSAGE.STRING.LETTER.ZH_NEED);
				return;
				} 
				 /*对文本框中的长度进行判断，是否超过5*/
				else if ($(this).val().length > 5) {
	     		$("#tip_p_attr_name").css("color", "red");
				$("#tip_p_attr_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
	     		return;
	     		 /*是否为空进行校验*/
	     	} else if ($(this).val().trim().length == 0) {
	     		$("#tip_p_attr_name").css("color", "red");
				$("#tip_p_attr_name").html(MESSAGE.PRODUCT.ATTR.ATTR_NAME_NEED);
	     		return;
	     	} 
	     	    /*对是否重名进行校验*/
	     	else {
	     		/*进新ajax把装有数据的集合进异步传递，路径../attrNameConfirm.json，get*/
	     		$.ajax({
	     			url : "../attrNameConfirm.json",
	     			type : "get",
	     			async : true,
	     			data : {
	     				"pAttrName" : $("#new_p_attr_name").val()
	     			},
	     			success : function(res) {
	     				if (res[SERVICE.STATUS] === SERVICE.SUCCESS 
	     						&& $("#new_p_attr_name").val() != target.pAttrName) {
	     					$("#tip_p_attr_name").css("color", "red");
	     					$("#tip_p_attr_name").html(MESSAGE.PRODUCT.ATTR.REPEAT_ATTR_NAME);
	     					return;
	         			} else {
	         				$("#tip_p_attr_name").html("");
	     					$("#tip_p_attr_name").css("color", "green");
	     					$("#tip_p_attr_name").html(MESSAGE.STRING.CORRECT);
	         			}
	     			}
	     		});
				}

	 		$("#tip_p_attr_name").html("");
	 		dataPost.pAttrName = $(this).val();
	     });
	     /*保存点击事件*/
	    $("#btn_attr_name_save").click(function() {
	     /*对文本框中是否为中文字符进行判断*/
	   if (!StringUtils.stringCheckChineseLatter($("#new_p_attr_name").val())) {
			$("#tip_p_attr_name").css("color", "red");
			$("#tip_p_attr_name").html(MESSAGE.STRING.LETTER.ZH_NEED);
			return;
		} 
		 /*对文本框中的长度进行判断，是否超过5*/
		else if ($("#new_p_attr_name").val().length > 5) {
     		$("#tip_p_attr_name").css("color", "red");
			$("#tip_p_attr_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
     		return;
        } 
         /*是否为空进行校验*/
        else if ($("#new_p_attr_name").val().trim().length == 0) {
     		$("#tip_p_attr_name").css("color", "red");
			$("#tip_p_attr_name").html(MESSAGE.PRODUCT.ATTR.ATTR_NAME_NEED);
     		return;
     	} 
     	/*保存时进重名校验，声明参数i，如果重名参数为变为1，不重名参数i则为0*/
     	var i ;
     	/*进新ajax把装有数据的集合进异步传递，路径../attrNameConfirm.json，get*/
     	$.ajax({
     			url : "../attrNameConfirm.json",
     			type : "get",
     			async : false,
     			data : {
     				"pAttrName" : $("#new_p_attr_name").val()
     			},
     			success : function(res) {
     				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
     					$("#tip_p_attr_name").css("color", "red");
     					
     					$("#tip_p_attr_name").html("此属性名已被占用");
     					i = 1;
     					return;
         			} else {
         				i = 0;
         				$("#tip_p_attr_name").html("");
     					$("#tip_p_attr_name").css("color", "green");
     					$("#tip_p_attr_name").html(MESSAGE.STRING.CORRECT);
         			}
     			}
     		});
        
        if (i===1) {
        	return;
        }
     	/*进新ajax把装有数据的集合进异步传递，路径application/json，put*/
	    	$.ajax({
	    		type:"PUT",
	    		url:"../attr/info",
				data: JSON.stringify(dataPost),
				contentType: "application/json",
	    		async:true,
	    		success: function(res){
	    /*对返回函数得到的值进行转型。变成jsson格式*/
	    			var obj = JSON.parse(res);
	    /*判断返回值的类型，并根据返回值进行判断，if状态为成功*/
	    			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
	    				MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
							var pAttrNameItem = obj.newAttrName;
							var tempAttrItem = $("#temp_attr_item").html();
							var textAttrItem = template(tempAttrItem, {"pAttrNameItem":pAttrNameItem});
							$(".container-fluid").append($(textAttrItem));
	        					if (attrDetail != undefined) {
									attrDetail.destroy();
									$("#attr_detail").remove();
									attrDetail = undefined;
								}
	        				});
	    			} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
						MessageUtils.showMessage(SERVICE.MESSAGE_BODY, MESSAGE.TYPE.INFO, function() {
							if (attrDetail != undefined) {
									attrDetail.destroy();
									$("#attr_detail").remove();
									attrDetail = undefined;
								}
						});
	    			}
	    		},
	    		error: function(err){
	    			console.log("error");
	    		}
	    	});
	    	if (attrDetail != undefined) {
				attrDetail.destroy();
				$("#attr_detail").remove();
				attrDetail = undefined;
			}
	    	/*进行刷新*/
	    	location.reload(".container-fluid");
	    });
	     /*点击取消事件*/
	    $("#btn_attr_name_cancel").click(function() {
	    	if (attrDetail != undefined) {
				attrDetail.destroy();
				$("#attr_detail").remove();
				attrDetail = undefined;
			}
	    });	
	     /*打开窗口*/
	    attrDetail.open();
	});
    /*进行ajax，异步取值*/
	$.ajax({
		type:"GET",
		url:"../attr/productAttr.json",
		async:true,
		success: function(res) {
			DATA_SOURCE = {};
			 /*将res的内容，复制到DATA_SOURCE中*/
			$.extend(true, DATA_SOURCE, res);
			 /*对DATA_SOURCE.pAttrNameList进行循环，并取出里面的属性名，与属性id*/
			for (var index in DATA_SOURCE.pAttrNameList) {
				var pAttrNameId = DATA_SOURCE.pAttrNameList[index];
				var pAttrNameItem = DATA_SOURCE.attrNameMapper[pAttrNameId];
				var tempAttrItem = $("#temp_attr_item").html();
				var textAttrItem = template(tempAttrItem, {"pAttrNameItem":pAttrNameItem});
				$(".container-fluid").append($(textAttrItem));
				 /*对DATA_SOURCE.pAttrNameList进行循环，并取出里面的属性值，与属性值id*/
				for (var jndex in DATA_SOURCE.attrValueMapper[pAttrNameItem.pAttrNameId]) {
					var attrValueItem = DATA_SOURCE.attrValueMapper[pAttrNameItem.pAttrNameId][jndex];
					var tempAttrValueText = $("#temp_attr_value_item").html();
					var textAttrValueItem = template(tempAttrValueText, {"attrValueItem": attrValueItem});
					$("#panel_body_"+pAttrNameItem.pAttrNameId).append($(textAttrValueItem));
				    
				}
			}
		},
		error: function(err) {
			console.log("error");
		}
		
	});


});