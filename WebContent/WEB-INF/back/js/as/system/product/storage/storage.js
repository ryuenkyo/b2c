var grid; 
var storageDetail;
var storageProduct;
var gridMapper = {};
var prodcutMapper = new Array();

function initStorageData(where) {
	grid = $('#grid').grid({
        primaryKey: 'storageId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
            { title: "ID", field: 'storageId', width: "5%", align: 'center' },
            { title: '货架主题', field: 'storageName', width: "10%" },
            { title: '货架描述', field: 'storageDes', width: "25%" },
            { title: '', field: 'Update', width: "5%", type: 'icon', icon: 'glyphicon-info-sign', tooltip: '编辑商品', events: { 'click': editStorageProduct }, align: 'center' },
            { title: '顺序', field: 'sortIndex', width: "5%", align: 'center' },
			{ field: '', width: "3%", title: '', type: "icon", icon: "glyphicon-arrow-up", tooltip: '上移', align: 'center', events: { 'click': sortUp }},
			{ field: '', width: "3%", title: '', type: "icon", icon: "glyphicon-arrow-down", tooltip: '下移', align: 'center', events: { 'click': sortDown }},
            { title: '状态', field: 'statusName', width: "10%", tmpl: "<a>{statusName}</a>", tooltip: '点击改变状态', align: 'center', events: { 'click': changeStatus } },
            { title: '创建者', field: 'cuName', width: "10%" },
            { title: '创建时间', field: 'ct', width: "15%", type: "date", format:'yyyy/mm/dd HH:MM:ss', align: 'center' },
            { title: '', field: 'Edit', width: "5%", type: 'icon', icon: 'glyphicon-pencil', tooltip: '编辑货架', events: { 'click': editStorage }, align: 'center' },
            { title: '', field: 'Delete', width: "5%", type: 'icon', icon: 'glyphicon-remove', tooltip: '删除货架', events: { 'click': deleteStorage }, align: 'center' }
        ],
        pager: { limit: 10, sizes: [] }
    });
}

function initStorageProductData(productInfo) {
	grid = $('#storageProduct_update_grid').grid({
        primaryKey: 'productId',
		uiLibrary: 'bootstrap',
        locale: "zh-s",
        dataSource: productInfo,
		columns: [
			{ field: 'productId', width: "5%", title: 'ID' },
			{ field: 'productName', width: "20%", title: '商品名' },
			{ field: 'showPrice', width: "10%", title: '显示价格' },
			{ field: 'salesVolume', width: "15%", title: '销量' },
			{ field: 'sortIndex', width: "4%", title: '顺序', type: 'dropdown', 
				editor: { dataSource: "../getStorageProdcutInfoByStorageIdForupdate.json/"+productInfo.data.storageId, 
				valueField: 'sortIndex', 
				textField: "sortIndex",
				editField: 'sortIndex'  } },
			{ field: '', width: "3%", title: '', type: "icon", icon: "glyphicon-arrow-up", tooltip: '上移', align: 'center', events: { 'click': storageProductSortUp }},
			{ field: '', width: "3%", title: '', type: "icon", icon: "glyphicon-arrow-down", tooltip: '下移', align: 'center', events: { 'click': storageProductSortDown }},
            { title: '', field: 'Delete', width: "5%", type: 'icon', icon: 'glyphicon-trash', tooltip: '移除商品', events: { 'click': deleteStorageProduct }, align: 'center' }
	        
		],
        pager: { limit: 10, sizes: [] }
    });
}

function storageProductSortUp(e) {
	storageProductSortIndexChange("up", e.data.record, e);
}

function storageProductSortDown(e) {
	storageProductSortIndexChange("down", e.data.record, e);
}

function storageProductSortIndexChange(option, currentStorageRecord, e) {
	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.STORAGE.STORAGE_PRODUCT_INDEX_CHANGE, 
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "POST",
				url: "../changeStorageProdcutSortIndex/info/"
					+option+"/"
					+e.data.record.storageId+"/"
					+e.data.record.productId+"/"
					+e.data.record.sortIndex,
				async: true,
				success: function(res) {
					var obj = JSON.parse(res);
					if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
						MessageUtils.showMessage(MESSAGE.UPDATE_SUCCESS, MESSAGE.TYPE.INFO, function() {
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

function deleteStorageProduct (e){
	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.STORAGE.REMOVE_STORAGE_PRODCUT, 
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "DELETE",
				url: "../storageProduct/info/"+e.data.record.storageId+"/"+e.data.record.productId,
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

function editStorageProduct (e) {
	var storageId = e.data.record.storageId;
	console.log(e);
	var searchStorage = {};
	$("body").append($("#storageProduct_update_temp").html());

	var storageProdcutUpdate = $("#storageProduct_update").dialog({
        uiLibrary: 'bootstrap',
        closeButtonInHeader: false,
        autoOpen: false,
        resizable: false,
        modal: true,
        width: "70%",
        height: "90%"
	});
	
	$("#storage_name").val(e.data.record.storageName);

	initStorageProductData({
		type:"GET",
		async:true,
 		url: "../getStorageProdcutInfoById.json",
 		data: {"storageId": e.data.record.storageId},
		headers : {  
            'Content-Type' : 'application/json;charset=utf-8'  
        },  
     	success: function(res) {
     		grid.render(res);
     	}, 
     	error: function(err) {
     	}
     });
	 
	updateStorageProdcut = {};
	updateStorageProdcut.checked = false;
	
	$("#update_product").click(function() {
		var searchProductText = $("#search_product_temp").html();
		var searchProductTemp = $(searchProductText);
		$("body").append(searchProductTemp);
		
		searchProductArea = $("#search_product_area").dialog({
			closeButtonInHeader: false,
		    uiLibrary: 'bootstrap',
		    autoOpen: false,
		    resizable: false,
		    modal: true,
		    width: "70%",
		    height: "90%"
		});
		
		$("#btn_product_search").click(function() {
			
			var searchProductName = $("#productNameSearch").val();
			var product = {};
			product.productName = searchProductName;
			product.storageId = e.data.record.storageId;
			initProductData({
	        	url: '../selectStorageProdcutInfoExceptId.json', 
	        	"data": product, 
	        	success: function(res) {
	        		productgrid.render(res);
	        	}, 
	        	error: function(err) {
	        		
	        	}
	        });
		});
		
		$("#btn_product_add").click(function(e) {
			var productIdMapper = new Array();
			if (prodcutMapper.length > 0){
				for (var i = 0; i < prodcutMapper.length; i++) {
					var productItem = prodcutMapper[i].productId;
					productIdMapper.push(productItem);
    				var tempProductText = $("#product_item_temp").html();
    				var textProductItem = template(tempProductText, {"productItem": productItem});
					$("#panel_body_onbody").append($(textProductItem));
				}
			} 
			
			var dataPut = JSON.stringify(productIdMapper);
			
        	$.ajax({
        		type: "PUT",
        		url: "../updateStorageProduct/info/"+storageId,
        		async: true,
        		data: dataPut,
        		contentType: "application/json",
        		success: function(res) {
					if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
						MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
							if (searchProductArea != undefined) {
								searchProductArea.destroy();
								$("#search_product_area").remove();
								$("body").children(".modal").remove();
								searchProductArea = undefined;
								productgrid.reload();
							}
						});
					}
        		},
        		error: function(err) {
        			console.log("error");
        		}
        	});
			
			searchProductArea.close();
			if (undefined !== searchProductArea) {
				searchProductArea.destroy();
				searchProductArea = undefined;
			}
			$("#search_product_area").parent().remove();
		});

		$("#btn_product_cancel").click(function() {
			searchProductArea.close();
			if (undefined !== searchProductArea) {
				searchProductArea.destroy();
				searchProductArea = undefined;
			}
			prodcutMapper.length = 0; 
			$("#search_product_area").parent().remove();
		});
		
		searchProductArea.open("添加商品");
		
	});
	
	$("#storage_update_cancel").click(function() {
		storageProdcutUpdate.close();
		
		if (storageProdcutUpdate != undefined) {
			storageProdcutUpdate.destroy();
			$("#storageProduct_update").remove();
			$("body").children(".modal").remove();
			storageProdcutUpdate = undefined;
		}
	});
	
	$("#storage_updated_save").click(function() {
		updateStorageProdcut.storageId = e.data.record.storageId;

    	var dataPut = JSON.stringify(updateStorageProdcut);
    	
    	$.ajax({
    		type: "POST",
    		url: "../",
    		async: true,
    		data: dataPut,
    		contentType: "application/json",
    		success: function(res) {
				MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
					if (storageProdcutUpdate != undefined) {
						storageProdcutUpdate.destroy();
						$("#storageProduct_update").remove();
						$("body").children(".modal").remove();
						storageProdcutUpdate = undefined;
						grid.reload();
					}
				});
    		},
    		error: function(err) {
    			console.log("error");
    		}
    	});
    });
	

	storageProdcutUpdate.open();
}

function initProductData(anywhere) {
	productgrid = $('#productgrid').grid({
        primaryKey: 'productId',
        locale: "zh-s",
        dataSource: anywhere,
        uiLibrary: 'bootstrap',
        columns: [
            { title: '', field: 'Edit', width: "5%", type: 'icon', icon: 'glyphicon-plus', tooltip: '添加商品', align: 'center', events: { 'click': checkup } },
            { title: "ID", field: 'productId', width: "auto", hidden: true },
            { title: '商品名', field: 'productName', width: "auto" },
            { title: '<span class="glyphicon glyphicon-sort">显示价格</span>', field: 'showPrice', width: "20%", sortable: true },
            { title: '<span class="glyphicon glyphicon-sort">销量</span>', field: 'salesVolume', width: "20%", sortable: true },
            { title: '状态', field: 'statusName', width: "10%", tmpl: "{statusName}", align: 'center' }
        ]
    });
}

function changeStatus(e) {
	console.log(e.data.record);
	
	dataPost = {};
	dataPost.storageId = e.data.record.storageId;
	dataPost.status = e.data.record.status;
	
	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.STORAGE.STORAGE_STATUS_CHANGE_WARNNING, 
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "POST",
				url: "../changeStorageStatus.json",
				data: JSON.stringify(dataPost),
				async: true,
				
				headers : {  
                    'Content-Type' : 'application/json;charset=utf-8'  
                },  
				success: function(res) {
					if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
						MessageUtils.showMessage(MESSAGE.CHANGE_SUCCESS, MESSAGE.TYPE.INFO, function() {
							grid.reload();
						});
					} else if (res[SERVICE.STATUS] === SERVICE.FAIL) {
						MessageUtils.showMessage(res[SERVICE.MESSAGE_BODY], MESSAGE.TYPE.INFO, function() {
							
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

function sortUp(e) {
	sortIndexChange("up", e.data.record, e);
}

function sortDown(e) {
	sortIndexChange("down", e.data.record, e);
}

function sortIndexChange(option, currentStorageRecord, e) {
	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.STORAGE.STORAGE_INDEX_CHANGE, 
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "POST",
				url: "../changeStorageSortIndex/info/"
					+option+"/"
					+e.data.record.storageId+"/"
					+e.data.record.sortIndex,
				async: true,
				success: function(res) {
					var obj = JSON.parse(res);
					if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
						MessageUtils.showMessage(MESSAGE.UPDATE_SUCCESS, MESSAGE.TYPE.INFO, function() {
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

$(document).ready(function() {
	
	initStorageData({
    	url: "../storageList.json", 
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
		data.storageName = $("#search_storagename").val();
		initStorageData({
        	url: "../storageList.json", 
        	data: data,
        	success: function(res) {
        		grid.render(res);
        	}, 
        	error: function(err) {
        		
        	}
        });
		
    });

    $("#btn_newstorage").click(function() {

		if (storageDetail != undefined) {
			storageDetail.destroy();
			$("#storage_detail").remove();
			$("body").children(".model").remove();
			storageDetail = undefined;
		}

		$("body").append($("#storage_detail_temp").html());

		storageDetail = $("#storage_detail").dialog({
            uiLibrary: 'bootstrap',
            closeButtonInHeader: false,
            autoOpen: false,
            resizable: false,
            modal: true,
            width: "70%",
            height: "80%"
		});
 
        var newStorage = {};
        newStorage.checked = true;
		            
		 $("#storage_name").focusout(function() {
        	if ($(this).val().length > 10) {
        		newStorage.checked = false;
        		$("#tip_storage_name").css("color", "red");
				$("#tip_storage_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
        		return;
        	} else if ($(this).val().trim().length == 0) {
        		newStorage.checked = false;
        		$("#tip_storage_name").css("color", "red");
				$("#tip_storage_name").html(MESSAGE.PRODUCT.STORAGE.STORAGE_NAME_NEED);
        		return;
        	} else {
        		$.ajax({
        			url : "../storageNameConfirm.json",
        			type : "get",
        			async : true,
        			data : {
        				"storageName" : $(this).val()
        			},
        			success : function(res) {
        				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
        					$("#tip_storage_name").css("color", "red");
        					$("#tip_storage_name").html(MESSAGE.PRODUCT.STORAGE.REPEAT_STORAGE_NAME);
        					newStorage.checked = false;
        					return;
            			} else {
            				$("#tip_storage_name").html("");
        					$("#tip_storage_name").css("color", "green");
        					$("#tip_storage_name").html(MESSAGE.STRING.CORRECT);
        					newStorage.checked = true;
            			}
        			}
        		});
			}
    		$("#tip_storage_name").html("");
    		newStorage.checked = true;
    		newStorage.storageName = $(this).val();
        });
        
        $("#storage_des").focusout(function() {
        	if ($(this).val().length > 0 && !StringUtils.stringCheckChineseLatter($(this).val())) {
				$("#tip_storage_des").css("color", "red");
				$("#tip_storage_des").html(MESSAGE.STRING.LETTER.ZH_NEED);
				newStorage.checked = false;
				return;
			}
			
			if ($(this).val().length > 20) {
				newStorage.checked = false;
				$("#tip_storage_des").css("color", "red");
				$("#tip_storage_des").html(MESSAGE.STRING.LENGTH.OVERSTEP);
				return;
			}
			
    		$("#tip_storage_des").html("");
    		newStorage.checked = true;
    		newStorage.storageDes = $(this).val();
        });
		
        $("#new_product").click(function() {
    		var searchProductText = $("#search_product_temp").html();
    		var searchProductTemp = $(searchProductText);
    		$("body").append(searchProductTemp);
    		
    		searchProductArea = $("#search_product_area").dialog({
    			closeButtonInHeader: false,
    		    uiLibrary: 'bootstrap',
    		    autoOpen: false,
    		    resizable: false,
    		    modal: true,
    		    width: "70%",
    		    height: "90%"
    		});
    		
    		$("#btn_product_search").click(function() {
    			
    			var searchProductName = $("#productNameSearch").val();
    			var product = {};
    			product.productName = searchProductName;
    			
    			initProductData({
    	        	url: '../productList.json', 
    	        	"data": product, 
    	        	success: function(res) {
    	        		productgrid.render(res);
    	        	}, 
    	        	error: function(err) {
    	        		
    	        	}
    	        });
    		});
    		
    		$("#btn_product_add").click(function(e) {
				console.log(prodcutMapper);
				var productIdMapper = new Array();
				if (prodcutMapper.length > 0){
    				for (var i = 0; i < prodcutMapper.length; i++) {
    					var productItem = prodcutMapper[i];
    					productIdMapper.push(productItem.productId);
        				var tempProductText = $("#product_item_temp").html();
        				var textProductItem = template(tempProductText, {"productItem": productItem});
    					$("#panel_body_onbody").append($(textProductItem));
					}
				} 
				newStorage.storageProductsMap = productIdMapper;
				console.log(productIdMapper);
				searchProductArea.close();
    			if (undefined !== searchProductArea) {
    				searchProductArea.destroy();
    				searchProductArea = undefined;
    			}
    			$("#search_product_area").parent().remove();
			});

    		$("#btn_product_cancel").click(function() {
    			searchProductArea.close();
    			if (undefined !== searchProductArea) {
    				searchProductArea.destroy();
    				searchProductArea = undefined;
    			}
    			prodcutMapper.length = 0; 
    			$("#search_product_area").parent().remove();
    		});
    		
    		searchProductArea.open("添加商品");
    		
    	});
        
		$("#storage_cancel").click(function() {
			storageDetail.close();
			
			if (storageDetail != undefined) {
				storageDetail.destroy();
				$("#storage_detail").remove();
				$("body").children(".modal").remove();
				storageDetail = undefined;
			}
		});
		
		$("#storage_save").click(function() {
			console.log(newStorage);
			if ($("#storage_name").val().length === 0) {
				MessageUtils.showMessage(MESSAGE.PRODUCT.STORAGE.STORAGE_NAME_NEED, MESSAGE.TYPE.INFO);
        		return;
			}
			
			
			if (!newStorage.checked) {
				MessageUtils.showMessage(MESSAGE.SCREEN.NOT_FINISH, MESSAGE.TYPE.INFO);
        		return;
			}
			
        	var dataPut = JSON.stringify(newStorage);
        	
        	$.ajax({
        		type: "PUT",
        		url: "../storageProduct/info",
        		async: true,
        		data: dataPut,
        		contentType: "application/json",
        		success: function() {
    				MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
    					if (storageDetail != undefined) {
    						storageDetail.destroy();
    						$("#storage_detail").remove();
    						$("body").children(".modal").remove();
    						storageDetail = undefined;
        					grid.reload();
    					}
    				});
        		},
        		error: function(err) {
        			console.log("error");
        		}
        	});
        });
		storageDetail.open("新增货架");
	});
});

function checkup(e) {
	console.log(e.data);
	var productItem = e.data.record;
	var tempProductText = $("#product_item_temp").html();
	var textProductItem = template(tempProductText, {"productItem": productItem});
	if (prodcutMapper.length== 0){
		prodcutMapper.push(productItem);
		$("#panel_body").append($(textProductItem));
	} else {
		var n;
		for(i = 0; i<prodcutMapper.length; i++){
			n = 0;
			if (prodcutMapper[i] == e.data.record) {
				n ++ ;
				if (n!=0){
					MessageUtils.showMessage(MESSAGE.PRODUCT.STORAGE.REPEAT_STORAGE_PRODUCT, MESSAGE.TYPE.INFO);
					return;
				}
			} 
		}
		if(n == 0){
			prodcutMapper.push(productItem);
			$("#panel_body").append($(textProductItem));
		} else {
			return;
		}
	}
	
}

function deleteProductValue(e) {
	console.log(e.currentTarget);
	var pValueId = $(e.currentTarget).attr("p-value-id");
	var b;
	for(i = 0; i<prodcutMapper.length; i++){
		if (prodcutMapper[i] == pValueId) {
			b = i;
		} 
	}
	prodcutMapper.splice(b,1);
	var productattrItem = document.getElementById("panel_attr_"+pValueId);
	productattrItem.remove();
	
}

function editStorage(e) {
	console.log(e.data.record);
	
	$("body").append($("#storage_update_temp").html());

	var storageUpdate = $("#storage_update").dialog({
        uiLibrary: 'bootstrap',
        closeButtonInHeader: false,
        autoOpen: false,
        resizable: false,
        modal: true,
        width: "50%",
        height: "60%"
	});
	
	var currentName = e.data.record.storageName;
	$("#storage_update_name").val(e.data.record.storageName);
	$("#storage_update_des").val(e.data.record.storageDes);
	
	updateStorage = {};
	updateStorage.checked = false;
	
	$("#storage_update_name").focusout(function() {
    	if ($(this).val().length > 10) {
    		updateStorage.checked = false;
    		$("#tip_storage_name").css("color", "red");
			$("#tip_storage_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
    		return;
    	} else if ($(this).val().trim().length == 0) {
    		updateStorage.checked = false;
    		$("#tip_storage_name").css("color", "red");
			$("#tip_storage_name").html(MESSAGE.PRODUCT.STORAGE.STORAGE_NAME_NEED);
    		return;
    	} else {
    		if ( $("#storage_update_name").val() == currentName){
    			$("#tip_storage_name").html("");
				$("#tip_storage_name").css("color", "green");
				$("#tip_storage_name").html(MESSAGE.STRING.CORRECT);
				updateStorage.storageName = $(this).val();
				updateStorage.checked = true;
    		} else {
    			$.ajax({
        			url : "../storageNameConfirm.json",
        			type : "get",
        			async : true,
        			data : {
        				"storageName" : $(this).val()
        			},
        			success : function(res) {
        				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
        					$("#tip_storage_name").css("color", "red");
        					$("#tip_storage_name").html(MESSAGE.PRODUCT.STORAGE.REPEAT_STORAGE_NAME);
        					updateStorage.checked = false;
        					return;
            			} else {
            				$("#tip_storage_name").html("");
        					$("#tip_storage_name").css("color", "green");
        					$("#tip_storage_name").html(MESSAGE.STRING.CORRECT);
        					updateStorage.storageName = $(this).val();
        					updateStorage.checked = true;
            			}
        			}
        		});
			}
		}
    });
	
	$("#storage_update_des").focusout(function() {
     	if ($(this).val().length > 0 && !StringUtils.stringCheckChineseLatter($(this).val())) {
				$("#tip_storage_des").css("color", "red");
				$("#tip_storage_des").html(MESSAGE.STRING.LETTER.ZH_NEED);
				updateStorage.checked = false;
				return;
			}
			
			if ($(this).val().length > 20) {
				updateStorage.checked = false;
				$("#tip_storage_des").css("color", "red");
				$("#tip_storage_des").html(MESSAGE.STRING.LENGTH.OVERSTEP);
				return;
			}
			
 		$("#tip_storage_des").html("");
 		updateStorage.checked = true;
 		updateStorage.storageDes = $(this).val();
    });
	
	$("#storage_update_cancel").click(function() {
		storageUpdate.close();
		
		if (storageUpdate != undefined) {
			storageUpdate.destroy();
			$("#storage_update").remove();
			$("body").children(".modal").remove();
			storageUpdate = undefined;
		}
	});
	
	$("#storage_updated_save").click(function() {
		updateStorage.storageId = e.data.record.storageId;
		if ($("#storage_update_name").val().length === 0) {
			MessageUtils.showMessage(MESSAGE.PRODUCT.STORAGE.STORAGE_NAME_NEED, MESSAGE.TYPE.INFO);
    		return;
		}
		
		if (!updateStorage.checked) {
			MessageUtils.showMessage(MESSAGE.SCREEN.NOT_FINISH, MESSAGE.TYPE.INFO);
    		return;
		}

    	var dataPut = JSON.stringify(updateStorage);
    	
    	$.ajax({
    		type: "POST",
    		url: "../storage/info",
    		async: true,
    		data: dataPut,
    		contentType: "application/json",
    		success: function(res) {
				MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
					if (storageUpdate != undefined) {
						storageUpdate.destroy();
						$("#storage_update").remove();
						$("body").children(".modal").remove();
						storageUpdate = undefined;
						grid.reload();
					}
				});
    		},
    		error: function(err) {
    			console.log("error");
    		}
    	});
    });
	
	storageUpdate.open("编辑货架");
}

function deleteStorage(e) {
	MessageUtils.showConfirmMessage(
			MESSAGE.PRODUCT.STORAGE.STORAGE_DELETE_CONFIRM, 
			MESSAGE.TYPE.INFO, 
			function() {
				$.ajax({
					type: "DELETE",
					url: "../storage/info/"+e.data.record.storageId,
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