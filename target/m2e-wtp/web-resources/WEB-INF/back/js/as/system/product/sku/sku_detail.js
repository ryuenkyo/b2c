var skuDetail;
var searchProductArea;
function editSku(e) {
	console.log(e.data.record);

	console.log(e.data.record.price);
	console.log(e.data.record.stock);

	$("body").append($("#sku_update_temp").html());


	var skuUpdate = $("#sku_update").dialog({
	    uiLibrary: 'bootstrap',
	    closeButtonInHeader: false,
	    autoOpen: false,
	    resizable: false,
	    modal: true,
	    width: "50%",
	    height: "60%"
	});

			skuDetail = $("#sku_update").dialog({
			uiLibrary: 'bootstrap',
			closeButtonInHeader: false,
			autoOpen: false,
			resizable: false,
			modal: true,
			width: "90%",
			height: "90%"
			});
		//页面回显值 （input标签里id的值）
		$("#price").val(e.data.record.price);
		$("#stock").val(e.data.record.stock);
		$("#productName").val(e.data.record.productName);
		$("#skuCollectionShow").val(e.data.record.skuCollectionShow);
		
	updateSku = {};
	updateSku.checked = false;


	$("#price").focusout(function() {
		/*价格非空校验*/
		if ($(this).val().trim().length == 0) {
			check = false;
			$("#tip_price").html(MESSAGE.PRODUCT.PRODUCT_PRICE_NEED);
			$("#tip_price").css("color", "red");
			return;
		}
		/*价格格式校验，10位，不以0开头，小数点后两位，不可以小数点结尾，不能为非数字*/
		if (!StringUtils.stringProductPrices($(this).val())) {
			check = false;
			$("#tip_price").css("color", "red");
			$("#tip_price").html(MESSAGE.PRODUCT.PRODUCT_PRICE_ERROR);
			return;
		}
		
		$("#tip_price").html("");
		$("#tip_price").css("color", "");
		check = true;
		updateSku.price = $(this).val();
	});

	$("#stock").focusout(function() {
		/*库存非空校验*/
		if ($(this).val().trim().length == 0) {
			check = false;
			$("#tip_stock").html(MESSAGE.PRODUCT.PRODUCT_PRICE_NEED);
			$("#tip_stock").css("color", "red");
			return;
		}
		/*库存格式校验，10位，不可以小数点结尾，不能为非数字*/
		if (!StringUtils.stringProductStock($(this).val())) {
			check = false;
			$("#tip_stock").css("color", "red");
			$("#tip_stock").html(MESSAGE.PRODUCT.PRODUCT_STOCK_ERROR);
			return;
		}
		
		$("#tip_stock").html("");
		$("#tip_stock").css("color", "");
		
		check = true;
		updateSku.stock = $(this).val();
	});

	$("#sku_update_cancel").click(function() {
		skuUpdate.close();
		
		if (skuUpdate != undefined) {
			skuUpdate.destroy();
			$("#sku_update").remove();
			$("body").children(".modal").remove();
			skuUpdate = undefined;
		}
	});
		
		
		
		$("#sku_updated_save").click(function() {
			updateSku.skuId = e.data.record.skuId;
			//修改时input标签里id的值
			updateSku.price = $("#price").val();
			updateSku.stock = $("#stock").val();
			if ($("#price").val().length === 0) {
//				MessageUtils.showMessage(MESSAGE.PRODUCT.STORAGE.STORAGE_NAME_NEED, MESSAGE.TYPE.INFO);
	    		return;
			}
			
			
			
			if ($("#stock").val().length === 0) {
				//MessageUtils.showMessage(MESSAGE.PRODUCT.STORAGE.STORAGE_NAME_NEED, MESSAGE.TYPE.INFO);
	    		return;
			}
			
			
			//将json对象转换成json字符串
	    	var dataPut = JSON.stringify(updateSku);
		$.ajax({
			type: "POST",
			url: "../sku1/info",
			//表示异步方式运行
			async: true,
			data: dataPut,
			contentType: "application/json",
			success: function(res) {
				MessageUtils.showMessage(MESSAGE.UPDATE_SUCCESS, MESSAGE.TYPE.INFO, function() {
					if (skuUpdate != undefined) {
						skuUpdate.destroy();
						$("#sku_update").remove();
						$("body").children(".modal").remove();
						skuUpdate = undefined;
						grid.reload();
						location.reload(true);
					}
				});
			},
				error: function(err) {
					console.log("error");
				}
			});
		});
		skuDetail.open("库存修改");


	}


function deleteSku(e) {
	console.log(e.data.record.skuId);


			$.ajax({
				type: "DELETE",
				url: "../info/"+e.data.record.skuId,
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
		
		}

function createNewSku() {
	var newSku = {};
	var skuDetailText = $("#sku_detail_temp").html();
	var skuDetailTemp = $(skuDetailText);
	
	$("body").append(skuDetailTemp);
	
	skuDetail = $("#sku_detail").dialog({
		closeButtonInHeader: false,
	    uiLibrary: 'bootstrap',
	    autoOpen: false,
	    resizable: false,
	    modal: true,
	    width: "90%",
	    height: "90%"
	});
	
	$("#price").focusout(function() {
		/*价格非空校验*/
		if ($(this).val().trim().length == 0) {
			check = false;
			$("#tip_price").html(MESSAGE.PRODUCT.PRODUCT_PRICE_NEED);
			$("#tip_price").css("color", "red");
			return;
		}
		/*价格格式校验，10位，不以0开头，小数点后两位，不可以小数点结尾，不能为非数字*/
		if (!StringUtils.stringProductPrices($(this).val())) {
			check = false;
			$("#tip_price").css("color", "red");
			$("#tip_price").html(MESSAGE.PRODUCT.PRODUCT_PRICE_ERROR);
			return;
		}
		
		$("#tip_price").html("");
		$("#tip_price").css("color", "");
		
		check = true;
		newSku.price = $(this).val();
	});
	
	$("#stock").focusout(function() {
		/*库存非空校验*/
		if ($(this).val().trim().length == 0) {
			check = false;
			$("#tip_stock").html(MESSAGE.PRODUCT.PRODUCT_STOCK_NEED);
			$("#tip_stock").css("color", "red");
			return;
		}
		/*库存格式校验，10位，不以0开头，不可以小数点结尾，不能为非数字*/
		if (!StringUtils.stringProductStock($(this).val())) {
			check = false;
			$("#tip_stock").css("color", "red");
			$("#tip_stock").html(MESSAGE.PRODUCT.PRODUCT_STOCK_ERROR);
			return;
		}
		
		$("#tip_stock").html("");
		$("#tip_stock").css("color", "");
		
		check = true;
		newSku.stock = $(this).val();
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
		    height: "70%"
		});
		
		$("#btn_product_search").click(function() {
			productSearch(newSku);
		});
		
		searchProductArea.open("请选择商品");
		
	});
	
	$("#sku_save").click(function() {
		console.log(newSku);
		
		$.ajax({
    		type: "PUT",
    		url: "../sku/info",
    		async: true,
    		contentType: "application/json",
    		data: JSON.stringify(newSku),
    		success: function(res) {
    			var obj = JSON.parse(res);
				if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
					MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
						if (undefined !== skuDetail) {
							skuDetail.destroy();
							skuDetail = undefined;
							grid.reload();
							location.reload(true);
						}
						
						$("#sku_detail_temp").nextAll().remove();
		            	
					});
				} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
					MessageUtils.showMessage(obj[SERVICE.MESSAGE_BODY], MESSAGE.TYPE.INFO, function() {
						if (undefined !== skuDetail) {
							skuDetail.destroy();
							skuDetail = undefined;
						}
						
						$("#sku_detail_temp").nextAll().remove();
					});
				}
    		},
    		error: function(err) {
    			
    		}
    	});
		
	});
	
	$("#sku_cancel").click(function() {
		if (undefined === skuDetail) {
			skuDetail.destroy();
			skuDetail = undefined;
		}
		
		$("#sku_detail_temp").nextAll().each(function() {
			$(this).remove();
		});
	});
	
	skuDetail.open("新增库存");
}

function productSearch(newSku) {
	
	$("#btn_product_cancel").click(function() {
		searchProductArea.close();
		if (undefined !== searchProductArea) {
			searchProductArea.destroy();
			searchProductArea = undefined;
		}
		prodcutMapper.length = 0; 
		$("#search_product_area").parent().remove();
	});
	
	
	
	var searchProductName = $("#aaa").val();
	if ( StringUtils.isEmpty(searchProductName)) {
		console.log("请输入商品名");
		return;
	}
	
	
	var product = {};
	
	product.productName = searchProductName;
	
	$.ajax({
		type:"GET",
		url:"../productName.json",
		data:product,
		async:true,
		success: function(res) {
			console.log(res);
			$("#product_list").html("");
			var productList = res;
			for (var index in productList) {
				var product = productList[index];
				
				var productTempText = $("#product_temp").html();
				var productTemp = template(productTempText, product);
				
				$("#product_list").append($(productTemp));
				
			}
			
			$(".search-product-check-button").click(function() {
				var productId = $(this).attr("product-id");
				
				$("#productName").val($(this).text());
				
				newSku.productId = productId;
				
				productAttrValueSearch(productId, newSku);
				
				if (undefined !== searchProductArea) {
					searchProductArea.destroy();
					searchProductArea = undefined;
				}
				
				$("#search_product_area").parent().remove();
				
			});
			
    		
			
		},
		
		
		error: function(res) {
			
		}
	});
	
	
	
	
}




function productAttrValueSearch(productId, newSku) {
	$.ajax({
		type:"GET",
		url:"../productAttrNameValue1.json",
		data: {
			"productId": productId
		},
		
		async:true,
		success: function(res) {
			
			var objSource = res;
			
			for (var index in objSource.pAttrNameList) {
				var pAttrNameItem = objSource.pAttrNameList[index];
				var attrText = $("#attr_temp").html();
				var attrTemp = template(attrText, pAttrNameItem);
				$("#attr_container").append($(attrTemp));
				
				objSource.pAttrValueMap[pAttrNameItem.pAttrNameId].unshift({
					"pAttrValueId": 0,
					"pAttrValue": "请选择"+pAttrNameItem.pAttrName,
					"selected": true
				});
				
				var attrValueDrowdown = $("#"+pAttrNameItem.pAttrNameId+"_value_select").dropdown({ 
					uiLibrary: "bootstrap",
					dataSource: objSource.pAttrValueMap[pAttrNameItem.pAttrNameId],
					valueField: "pAttrValueId",
					textField: "pAttrValue",
					selectedField: 'selected'
				});
				
				attrValueDrowdown.on("change", attrValueDrowdownChange(attrValueDrowdown, newSku));
				
			}

		},
		error: function(err) {
			
		}
	});
}

function attrValueDrowdownChange(attrValueDrowdown, newSku) {
	return function(e) {
		if (undefined === newSku.skuCollection) {
			newSku.skuCollection = {};
		}
		
		var currentPAttrNameId = $(this).attr("p-attr-name-id");
		newSku.skuCollection[currentPAttrNameId] = attrValueDrowdown.value();
		
	};
}
