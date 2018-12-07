var grid; 
var brandDetail;	
var targetBrand;

function initBrandData(where) {
	grid = $('#grid').grid({
        primaryKey: 'brandId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
            { title: "ID", field: 'brandId', width: "5%" },
            { title: '品牌名', field: 'brandName', width: "20%" },
            { title: '品牌描述', field: 'des', width: "30%" },
            { title: '创建者', field: 'cuName', width: "15%" },
            { title: '创建时间', field: 'ct', width: "20%", type: "date", format:'yyyy/mm/dd HH:MM:ss' },
            { title: '', field: 'Edit', width: "5%", type: 'icon', icon: 'glyphicon-pencil', tooltip: '编辑品牌', events: { 'click': editBrand } },
            { title: '', field: 'Delete', width: "5%", type: 'icon', icon: 'glyphicon-remove', tooltip: '删除品牌', events: { 'click': deleteBrand } }
        ],
        pager: { limit: 10, sizes: [] }
    });
}

$(document).ready(function() {
	
	initBrandData({
    	url: "../brandList.json", 
    	data: {}, 
    	success: function(res) {
    		grid.render(res);
    	}, 
    	error: function(err) {
    	}
    });
    /*搜索点击事件*/
    $("#btn_search").click(function(){
    	grid.clear();
		grid.destroy();
		$("#grid").children().each(function() {
			$(this).remove();
		});
		
		var data = {};
		data.brandName = $("#search_brandname").val();
		
		initBrandData({
        	url: "../brandList.json", 
        	data: data,
        	success: function(res) {
        		grid.render(res);
        	}, 
        	error: function(err) {
        		
        	}
        });
		
    });
    /* 新增按钮点击事件*/
    $("#btn_newbrand").click(function() {

		if (brandDetail != undefined) {
			brandDetail.destroy();
			$("#brand_detail").remove();
			$("body").children(".model").remove();
			brandDetail = undefined;
		}

		$("body").append($("#brand_detail_temp").html());

		brandDetail = $("#brand_detail").dialog({
            uiLibrary: 'bootstrap',
            closeButtonInHeader: false,
            autoOpen: false,
            resizable: false,
            modal: true,
            width: "50%",
            height: "60%"
		});
 
        var newBrand = {};
        newBrand.checked = true;
		/*  品牌名焦点离开事件*/
	    $("#brand_name").focusout(function() {
		    var val =$("#brand_name").val();  
		    var patrn = /^[0-9]*$/;  
		  /*  验证属性名是否为空*/
	       if ($("#brand_name").val().length == 0) {
			$("#tip_brand_name").css("color", "red");
			$("#tip_brand_name").html("品牌名不能为空");
			return;
	    	}  
		    /*验证属性名是否为纯数字*/
	        else if (patrn.test(val)) {  
	      	$("#tip_brand_name").css("color", "red");
	      	$("#tip_brand_name").html("品牌名不能为纯数字1");
			return; 
	        }  
	         
	         /*对品牌名的长度进行验证*/
		
			else if ($("#brand_name").val().length > 10) {
				
	    		$("#tip_brand_name").css("color", "red");
				$("#tip_brand_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
	    		return;
	    	}     
           /*  是否重名验证*/
			
	    	else {
	    		$.ajax({
	    			url : "../brandNameConfirm.json",
	    			type : "get",
	    			async : true,
	    			data : {
	    				"brandName" : $("#brand_name").val()
	    			},
	    			success : function(res) {
	    				if (res[SERVICE.STATUS] === SERVICE.SUCCESS ) {
	    					$("#tip_brand_name").css("color", "red");
	    					$("#tip_brand_name").html(MESSAGE.PRODUCT.BRAND.BRAND_NAME_CONFIRM);
	    					
	    					return;
	        			} else {
	        				$("#tip_brand_name").html("");
	    					$("#tip_brand_name").css("color", "green");
	    					$("#tip_brand_name").html(MESSAGE.STRING.CORRECT);
	    					
	        			}
	    			}
	    		});
	    	}
			$("#tip_brand_name").html("");
    		newBrand.brandName = $(this).val();
        });
     /*   品牌说明的焦点离开事件*/
        $("#brand_des").focusout(function() {
        	
        	var des = 0;
		    var val =$('#brand_des').val();  
		    var patrn = /^[0-9]*$/;  
        	/* 品牌说明的非空验证*/
		
        	if ($("#brand_des").val().length == 0) {
				
	    		$("#tip_brand_des").css("color", "red");
				$("#tip_brand_des").html("品牌说明不能为空");
	    		return;
	    	}     
	    	/* 对品牌说明进行是否为纯数字的验证*/
			
			
	       else if (patrn.test(val)) {  
	      	$("#tip_brand_des").css("color", "red");
	      	$("#tip_brand_des").html("品牌说明不能为数字");
	      	des = 1;
			return; 
	        }  
            /**
			 * 对品牌说明进行是否为大于15
			 * @param {Object} e
			 */
        	else if ($(this).val().length > 15) {
				$("#tip_brand_des").css("color", "red");
				$("#tip_brand_des").html("品牌说明不能超过15个字符");
				return;
			}	
    		$("#tip_brand_des").html("");
    		newBrand.des = $(this).val();
        });
		
		$("#brand_cancel").click(function() {
			brandDetail.close();
			
			if (brandDetail != undefined) {
				brandDetail.destroy();
				$("#brand_detail").remove();
				$("body").children(".modal").remove();
				brandDetail = undefined;
			}
		});		
	     /*	品牌点击保存事件*/
		$("#brand_save").click(function() {
			/*点击重名验证*/
			var val =$("#brand_name").val();  
		    var patrn = /^[0-9]*$/; 
			var r = 0;
			$.ajax({
    			url : "../brandNameConfirm.json",
    			type : "get",
    			async : false,
    			data : {
    				"brandName" : $("#brand_name").val()
    			},
    			success : function(res) {
    				if (res[SERVICE.STATUS] === SERVICE.SUCCESS ) {
    					r =1;
    					return;
        			} 
    			}
    		});
			if(r===1){
				return;
			}
			/*品牌名是否为空验证*/
			else if ($("#brand_name").val().length == 0) {
			$("#tip_brand_name").css("color", "red");
			$("#tip_brand_name").html("品牌名不能为空");
			return;
	    	}  
			/*品牌名是否为数字*/
			else if (patrn.test(val)) {  
	      	$("#tip_brand_name").css("color", "red");
	      	$("#tip_brand_name").html("品牌名不能为纯数字");
			return; 
	        }  
	       /* 品牌名是否爲空的点击事件*/
			else if ($("#brand_des").val().length == 0) {
	    		$("#tip_brand_des").css("color", "red");
				$("#tip_brand_des").html("品牌说明不能为空");
	    		return;
	    	} 
	    	/*品牌说明长度验证*/
	    	else if ($("#brand_des").val().length > 15) {
				$("#tip_brand_des").css("color", "red");
				$("#tip_brand_des").html("品牌说明不能超过15个字符");
				return;
			}	
			
		    /* ajax传值 准备进行新增*/
    		var dataPut = JSON.stringify(newBrand);
    			$.ajax({
        		type: "PUT",
        		url: "../brand/info",
        		async: true,
        		data: dataPut,
        		contentType: "application/json",
        		success: function(res) {
        			var obj = JSON.parse(res);
        			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
        				MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
        					if (brandDetail != undefined) {
        						brandDetail.destroy();
        						$("#brand_detail").remove();
        						$("body").children(".modal").remove();
        						brandDetail = undefined;
	        					grid.reload();
        					}
        					else{
        						$("#tip_brand_des").html("新增失败");
        					}
        				});
        			}
        		},
        		error: function(err) {
        			console.log("error");
        		}
        		
        });
        });
		brandDetail.open("新增品牌");
	});
   
});
/**
 * 修改品牌 ,取出要修改的属性值id-点击事件
 * 对属性值 修改，传入id，调修改函数进行取值
 * @param {Object} e
 */
function editBrand(e) {
	console.log(e.data.record.brandId);
	/*通过得到id，来得到相关信息*/
	$.ajax({
			type: "GET",
			url: "../brand/selectbrandid",
			data: {"brandId": e.data.record.brandId},
			async: true,
			success: function(res) {
			console.log(res);
			var brandInfo = JSON.parse(res);
			var target = {};
			target.brandId = brandInfo.brand.brandId;
			target.brandName = brandInfo.brand.brandName;
			target.brandDes = brandInfo.brand.des;
            console.log(target);
			createUpdateBrand(target);
					},
					error: function(err) {
						
					}
				});
}	
/**
 * 修改品牌函数 
 * @param {Object} target
 */
function createUpdateBrand(target)	{
	
	updateBrand = {};
	$("body").append($("#brand_update_temp").html());

	var brandUpdate = $("#brand_update").dialog({
        uiLibrary: 'bootstrap',
        closeButtonInHeader: false,
        autoOpen: false,
        resizable: false,
        modal: true,
        width: "50%",
        height: "60%"
	});
	/*给品牌名中修改空中传值*/
	$("#update_brand_name").val(target.brandName);
	/*给品牌说明传递修改值*/
	$("#update_brand_des").val(target.brandDes);
	
	updateBrand = {};
	
	/*品牌名焦点离开事件*/
	$("#update_brand_name").focusout(function() {
		    var val =$("#update_brand_name").val();  
		    var patrn = /^[0-9]*$/;  
		   /*  品牌名不能为空验证*/
	       if ($("#update_brand_name").val().length == 0) {
			$("#tip_brand_name").css("color", "red");
			$("#tip_brand_name").html("品牌名不能为空");
			return;
	    	}  
		    /* 品牌名不能为纯数字验证*/
	        else if (patrn.test(val)) {  
	      	$("#tip_brand_name").css("color", "red");
	      	$("#tip_brand_name").html("品牌名不能为纯数字");
			return; 
	        }  
	       /* 品牌名长度验证*/
			else if ($("#update_brand_name").val().length > 10) {
				
	    		$("#tip_brand_name").css("color", "red");
				$("#tip_brand_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
	    		return;
	    	}
			/*是否重名验证*/
			else {
    		$.ajax({
    			url : "../brandNameConfirm.json",
    			type : "get",
    			async : true,
    			data : {
    				"brandName" : $("#update_brand_name").val()
    			},
    			success : function(res) {
    				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
    					$("#tip_brand_name").css("color", "red");
    					$("#tip_brand_name").html(MESSAGE.PRODUCT.BRAND.BRAND_NAME_CONFIRM);
    					updateBrand.checked = false;
    					return;
        			} else {
        				$("#tip_brand_name").html("");
    					$("#tip_brand_name").css("color", "green");
    					$("#tip_brand_name").html(MESSAGE.STRING.CORRECT);
    					updateBrand.checked = true;
        			}
    			}
    		});
    	}
		$("#update_brand_name").html("");
		updateBrand.brandName = $(this).val();
    	
	});
	/*品牌说明焦点离开事件*/
	$("#update_brand_des").focusout(function() {
        	
        	var des = 0;
		    var val =$('#update_brand_des').val();  
		    var patrn = /^[0-9]*$/;  
        	/**
			 * 品牌说明的非空验证
			 * @param {Object} e
			 */
        	if ($("#update_brand_des").val().length == 0) {
				
	    		$("#tip_brand_des").css("color", "red");
				$("#tip_brand_des").html("品牌说明不能为空");
	    		return;
	    	}     
	    	/**
			 * 对品牌说明进行是否为纯数字的验证
			 * @param {Object} e
			 */
			
	       else if (patrn.test(val)) {  
	      	$("#tip_brand_des").css("color", "red");
	      	$("#tip_brand_des").html("品牌说明不能为数字");
	      	des = 1;
			return; 
	        }  
            /**
			 * 对品牌说明进行是否为大于15
			 * @param {Object} e
			 */
        	else if ($(this).val().length > 15) {
				$("#tip_brand_des").css("color", "red");
				$("#tip_brand_des").html("品牌说明不能超过15个字符");
				return;
			}	
    		$("#tip_brand_des").html("");
    		updateBrand.brandDes = $(this).val();
        });
	/*修改保存点击事件*/
	$("#update_brand_save").click(function() {
		    updateBrand.brandName = $("#update_brand_name").val();
		    updateBrand.brandDes = $("#update_brand_des").val();
		    var dataPost = {};
		    dataPost.originalBrand = target;
			dataPost.update_brand = updateBrand;
		    var val =$("#update_brand_name").val();  
		    var patrn = /^[0-9]*$/; 
			var r = 0;
			/*判断是否之间点击确定事件*/
			if(updateBrand.brandName != target.brandName
				&&updateBrand.brandDes||target.brandDes!=updateBrand.brandDes){
			/*重名校验*/
			$.ajax({
    			url : "../brandNameConfirm.json",
    			type : "get",
    			async : false,
    			data : {
    				"brandName" : $("#update_brand_name").val()
    			},
    			success : function(res) {
    				if (res[SERVICE.STATUS] === SERVICE.SUCCESS ) {
    					r =1;
    					return;
        			} 
    			}
    		});
			if(r===1){
				return;
			}
			/*品牌名为空校验*/
			else if ($("#update_brand_name").val().length == 0) {
			$("#tip_brand_name").css("color", "red");
			$("#tip_brand_name").html("品牌名不能为空");
			return;
	    	}  
		  /*	品牌是否为纯数字校验*/
			else if (patrn.test(val)) {  
	      	$("#tip_brand_name").css("color", "red");
	      	$("#tip_brand_name").html("品牌名不能为纯数字");
			return; 
	        }  
	      /*  品牌说明为空校验*/
			else if ($("#update_brand_des").val().length == 0) {
	    		$("#tip_brand_des").css("color", "red");
				$("#tip_brand_des").html("品牌说明不能为空");
	    		return;
	    	} 
	    	/*品牌说明长度验证*/
	    	else if ($("#update_brand_des").val().length > 15) {
				$("#tip_brand_des").css("color", "red");
				$("#tip_brand_des").html("品牌说明不能超过15个字符");
				return;
			}	
	  /*  进行ajax传值,准备进行修改*/
    	$.ajax({
    		type: "POST",
    		url: "../brand/info",
    		async: true,
    		data: JSON.stringify(dataPost),
    		contentType: "application/json",
    		success: function(res) {
    			var obj = JSON.parse(res);
    			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
    				MessageUtils.showMessage("品牌更新成功", MESSAGE.TYPE.INFO, function() {
    					if (brandUpdate != undefined) {
    						brandUpdate.destroy();
    						$("#brand_update").remove();
    						$("body").children(".modal").remove();
    						brandUpdate = undefined;
        					grid.reload();
    					}
    				});
    			}
    		},
    		error: function(err) {
    			console.log("error");
    		}
    	});
    }else{
	 MessageUtils.showMessage("数据没有变化更新失败", MESSAGE.TYPE.INFO, function() {
		                    brandUpdate.destroy();
							$("#brand_update").remove();
							$("body").children(".modal").remove();
							brandUpdate = undefined;
        		});		
    }
    });
    	
	$("#update_brand_cancel").click(function() {
        brandUpdate.destroy();
		$("#brand_update").remove();
		$("body").children(".modal").remove();
		brandUpdate = undefined;
     });
	
	brandUpdate.open("编辑品牌");
	
}
/**
 * 删除品牌 
 * @param {Object} e
 */
function deleteBrand(e) {
	MessageUtils.showConfirmMessage(
			MESSAGE.PRODUCT.BRAND.BRAND_DELETE_CONFIRM, 
			MESSAGE.TYPE.INFO, 
			/*把要删除的brandid进行传值,准备进行删除*/
			function() {
				$.ajax({
					type: "DELETE",
					url: "../brand/info/"+e.data.record.brandId,
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