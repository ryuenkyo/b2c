var productDetail;

function editProduct(e) {
	console.log(e.data);
}

function createNewProduct() {
	var dropDownMapper = {};
	var check = false;
	var newProductObject = {};
	newProductObject.category = {};
	newProductObject.attr = {};
	var image = {};
	
	var productDetailText = $("#product_detail_temp").html();
	var productDetailTemp = $(productDetailText);
	
	$("body").append(productDetailTemp);
	
	productDetail = $("#product_detail").dialog({
	    uiLibrary: 'bootstrap',
	    closeButtonInHeader: false,
	    autoOpen: false,
	    resizable: false,
	    modal: true,
	    width: "90%",
	    height: "90%"
	});
	
	var brandIdDropdown = $('#brand_id').dropdown({ 
		uiLibrary: "bootstrap",
		dataSource: "../product/brand.json",
		valueField: "brandId",
		textField: "brandName",
		selectedField: 'selected'
	});
	
	brandIdDropdown.on("change", function() {
		newProductObject.brandId = brandIdDropdown.value();
	});
	
	var statusCheckBox = $("#status").checkbox({
		uiLibrary: "bootstrap"
	});
	
	statusCheckBox.on("change", function(){
		if ("unchecked" === statusCheckBox.state()) {
			newProductObject.status = "INACTIVATE";
		} else {
			newProductObject.status = "ACTIVATE";
		}
	});
	
	statusCheckBox.state("checked");

	$("#product_name").focusout(function() {
		/*商品名称非空校验*/
		if ($(this).val().trim().length == 0) {
			check = false;
			$("#tip_product_name").html(MESSAGE.PRODUCT.PRODUCT_NAME_NEED);
			$("#tip_product_name").css("color", "red");
			return;
		}
		/*商品名称10位以内的英文，汉字，数字，且不可为纯数字校验*/
		if (!StringUtils.stringProductName($(this).val())) {
			check = false;
			$("#tip_product_name").css("color", "red");
			$("#tip_product_name").html(MESSAGE.PRODUCT.CHINESELATTER_ERROR);
			return;
		}
		/*商品名称长度校验*/
		if ($(this).val().length > 10) {
			check = false;
			$("#tip_product_name").css("color", "red");
			$("#tip_product_name").html(MESSAGE.STRING.LENGTH.OVERSTEP);
			return;
		}
		
		$("#tip_product_name").html("");
		$("#tip_product_name").css("color", "");
		newProductObject.productName = $(this).val();
		check = true;
	});
	
	$("#show_price").focusout(function() {
		/*价格非空校验*/
		if ($(this).val().trim().length == 0) {
			check = false;
			$("#tip_show_price").html(MESSAGE.PRODUCT.PRODUCT_PRICE_NEED);
			$("#tip_show_price").css("color", "red");
			return;
		}
		/*价格格式校验，10位，不以0开头，小数点后两位，不可以小数点结尾，不能为非数字*/
		if (!StringUtils.stringProductPrices($(this).val())) {
			check = false;
			$("#tip_show_price").css("color", "red");
			$("#tip_show_price").html(MESSAGE.PRODUCT.PRODUCT_PRICE_ERROR);
			return;
		}
		
		$("#tip_show_price").html("");
		$("#tip_show_price").css("color", "");
		newProductObject.showPrice = $(this).val();
		check = true;
	});
	
	
	var categoryText = $("#category_temp").html();
	
	var categoryTemp = template(categoryText, {"categoryLevel": 1});
	
	$("#category_container").append($(categoryTemp));
	
	// 取得1级（顶级）分类数据
	var level1Data = getLevel1SourceArray(LEVEL_ONE_CATEGORY, CATEGORY_INFO);
	
	// 在1级分类数据的前方加入 选择提示项目
	level1Data.unshift({
		categoryId: "0",
		categoryName: "请选择父级1级分类",
		selected: true
	});
	
	var pCategory1 = $('#category_id_1').dropdown({ 
		uiLibrary: "bootstrap",
		valueField: "categoryId",
		textField: "categoryName",
		dataSource: level1Data,
		selectedField: 'selected'
	});
	
	$('#category_id_1').attr("product-category-level", "1");
	
	// 注册父级分类变化事件
	var productCategoryChange = function(pCategory) {
		return function(e) {
			// 取得当前变化的分类等级
			var currentLevel = parseInt($(e.currentTarget).attr("product-category-level"));
			
			var changeBeforCategoryId = newProductObject.category[currentLevel]
			
			if (changeBeforCategoryId == pCategory.value()) {
				return;
			}
			
			newProductObject.category[currentLevel] = pCategory.value();
			newProductObject.categoryLevel = currentLevel;
			
			
			var counter = currentLevel;
			while (dropDownMapper[counter] !== undefined) {
				
				if (counter > currentLevel) {
					var target = dropDownMapper[counter].destroy();
					delete dropDownMapper[counter];
					delete newProductObject.category[counter];
					$("#category_id_"+counter).parent().parent().parent().remove();
				}

				for (var pAttrNameId in newProductObject.attr[counter]) {
					$("#attr_container").children("#"+pAttrNameId).remove();
				}

				delete newProductObject.attr[counter];
				
				counter++;
			}
			

			/* 当选择分类下拉列表没有选择任何分类的时候 */
			if ("0" === pCategory.value()) {

			/* 当选择分类下拉列表选择真实分类的时候 */
			} else {
				
				// 取得当前选择分类下的属性
				var categoryAttrInfoList = CATEGORY_ATTR_INFO[pCategory.value()];
				
				for (var index in categoryAttrInfoList) {
									
					var attrNameId = categoryAttrInfoList[index];
					
					var attrNameItem = PRODUCT_ATTR_INFO.attrNameMapper[attrNameId];
					
					if (undefined === newProductObject.attr[currentLevel]) {
						newProductObject.attr[currentLevel] = {};
					}
					
					if (undefined === newProductObject.attr[currentLevel][attrNameId]) {
						newProductObject.attr[currentLevel][attrNameId] = {};
						newProductObject.attr[currentLevel][attrNameId].isSku = false;
					}
					
					//需要显示商品分类属性名  包含是否影响sku属性选择
					var attrText = $("#attr_temp").html();
					var attrTemp = template(attrText, {
						"pAttrNameId": attrNameId,
						"pAttrName": attrNameItem.pAttrName,
						"categoryLevel": currentLevel,
						"categoryName": CATEGORY_INFO[pCategory.value()].categoryName,
						"categoryId": pCategory.value()
					});
					
					$("#attr_container").append($(attrTemp));
					
					//属性名对应的属性值（暂定checkbox组合)
					var attrValueList = PRODUCT_ATTR_INFO.attrValueMapper[attrNameId];
					for (var av_index in attrValueList) {
						
						var attrValueItem = attrValueList[av_index];
						var attrValueText = $("#attr_value_temp").html();
						var attrValueTemp = template(attrValueText, {
							"pAttrNameId": attrNameId,
							"pAttrValueId": attrValueItem.pAttrValueId,
							"pAttrValue": attrValueItem.pAttrValue
						});
						
						$("#attr_value_container_"+attrNameId).append($(attrValueTemp));
						
					}
					
					
					
				}
				
				
				$(".is-sku").change(function() {
					var pAttrNameIdCurrent = $(this).parent().attr("p-attr-name-id");
					var currentCategoryLevel = $(this).parent().parent().parent().attr("category-level");

					newProductObject.attr[currentCategoryLevel][pAttrNameIdCurrent].isSku 
						= $(this).prop("checked");
				});
				
				$(".p-attr-value").change(function() {
					
					var pAttrNameIdCurrent = $(this).parent().attr("p-attr-name-id");
					var currentCategoryLevel = $(this).parent().parent().parent().attr("category-level");
				
					var newPAttrNameItem = newProductObject.attr[currentCategoryLevel][pAttrNameIdCurrent];
					if (undefined === newPAttrNameItem) {
						newPAttrNameItem = {};
					}
					
					if ($(this).prop("checked")) {
						newPAttrNameItem[$(this).attr("id")] = $(this).attr("id");
					} else {
						delete newPAttrNameItem[$(this).attr("id")];
					}
				});
				
				// 取得当前选择分类下的所有子分类
				var childrenArray = CATEGORY_CHILDREN[pCategory.value()];
				
				/* 当前选择子分类下没有子分类的时候  没必要进行子分类加载 因此返回 */
				if (undefined === childrenArray || 0 === childrenArray.length) {
					return;
				}
				
				// 取得新分类等级
				var newChildCategoryLevel = currentLevel + 1;
				/* 构建子分类下拉列表数据 */
				var dataSourceArray = [];
				dataSourceArray.push({
					"categoryId": "0",
					"categoryName": "请选择父级"+ newChildCategoryLevel +"级分类",
					"selected": true
				});
				
				for (var index in childrenArray) {
					var categoryId = childrenArray[index];
					var newItem = {};
					$.extend(true, newItem, CATEGORY_INFO[categoryId]);
					dataSourceArray.push(newItem);
				}
				
				// 初始化子分类dom组件的id
				var newChildCategoryId = "category_id_" + newChildCategoryLevel;
				
				var categoryText = $("#category_temp").html();
	
				var categoryTemp = template(categoryText, {"categoryLevel": newChildCategoryLevel});
				
				$("#category_container").append($(categoryTemp));

				// 创建子分类对象的jquery对象
				var selectChildren = $("#category_id_"+newChildCategoryLevel);

				// 注册子分类gijgo下拉列表对象
				var selectChildrenDropDown = selectChildren.dropdown({ 
					uiLibrary: "bootstrap",
					valueField: "categoryId",
					textField: "categoryName",
					dataSource: dataSourceArray,
					selectedField: 'selected'
				});
				
				// 使用递归注册子分类选择事件
				selectChildrenDropDown.on("change", productCategoryChange(selectChildrenDropDown));
				
				// 将子分类下拉列表添加到父级分类下拉列表集合中
				dropDownMapper[newChildCategoryLevel] = selectChildrenDropDown;
				
				
			} // end of [当选择分类下拉列表选择真实分类的时候]
			
		}
	}
	
	// 使用递归注册父级分类选择事件
	pCategory1.on("change", productCategoryChange(pCategory1));
	
	// 将父级分类下拉列表添加到父级分类下拉列表集合中
	dropDownMapper["1"] = pCategory1;
	
	
	$("#file_cover").on("change", function() {
		
		image.cover = this.files[0];
		var reader = new FileReader();
		reader.onload = function(e) {
			$("#cover_show_img").attr("src",  e.target.result);
		};
		
		reader.readAsDataURL(this.files[0]);
	});
	
	$("#new_product_image").click(function () {
		var currentCount = parseInt($(this).attr("img-count"));
		var detailImgText = $("#detail_img_temp").html();
		var detailImgTemp = template(detailImgText, {
			"imageId": currentCount + 1
		});
		
		$("#img_container").append($(detailImgTemp));
		
		$("#detail_"+(currentCount + 1)).on("change", function() {
			console.log(this.files[0]);
			
			if (undefined === image.detail) {
				image.detail = {};
			}
			
			image.detail[$(this).attr("id")] = this.files[0];
			var imageId = $(this).attr("id").split("_")[1];
			var reader = new FileReader();
			reader.onload = function(e) {
				$("#detail_show_"+imageId).attr("src",  e.target.result);
			};
			
			reader.readAsDataURL(this.files[0]);
			
		});
		
		$("#delete_img_"+(currentCount + 1)).click(function() {
			
			var imageId = $(this).attr("id").split("_")[2];
			
			delete image.detail["detail_"+imageId];
			
			$(this).parent().remove();
			
		});
		
		$(this).attr("img-count", currentCount + 1);
		
	});
	
	var edit = CKEDITOR.replace("editor");
	
	$("#product_save").click(function() {

		newProductObject.des = edit.getData();
		console.log(image);
		console.log(JSON.stringify(newProductObject));
		// return;
		// TODO 校验
		// 提交
		/* 提交验证产品名与价格  */
		if (newProductObject.productName == null 
				|| newProductObject.productName.length == 0 
				|| newProductObject.showPrice.lenght == 0 
				|| newProductObject.showPrice == null) {
			$("#product_detail").animate({scrollTop: $("#tip_product_name").offset().top}, 1000);
			check = false;
			$("#tip_product_name").html(MESSAGE.PRODUCT.PRODUCT_NAME_NEED);
			$("#tip_product_name").css("color", "red");
			$("#tip_show_price").css("color", "red");
			$("#tip_show_price").html(MESSAGE.PRODUCT.PRODUCT_PRICE_ERROR);
			return;
		}
		/*品牌下拉框选择校验*/
		if (newProductObject.brandId == null) {
			check = false;
			$("#tip_brand").html(MESSAGE.PRODUCT.BRAND_NAME_NEED);
			$("#tip_brand").css("color", "red");
			$("#product_detail").animate({scrollTop: $("#tip_product_name").offset().top}, 1000);
			return;
		}else {
			$("#tip_brand").html("");
			$("#tip_brand").css("color", "");
			check = true;
		};
		/*分类下拉框选择校验*/
		var cate = Object.getOwnPropertyNames(newProductObject.category);
		if (cate.length == 0) {
			check = false;
			$("#tip_category").html(MESSAGE.PRODUCT.CATEGORY_NEED);
			$("#tip_category").css("color", "red");
			$("#product_detail").animate({scrollTop: $("#tip_product_status").position().top}, 1000);
	        return;
		}else {
			check = true;
		};
		/*属性框选择校验 */
		// TODO 选择后再取消判断不准确
		var atr = Object.getOwnPropertyNames(newProductObject.attr);
		if (atr.length == 0) {
			check = false;
			$("#tip_attr").html(MESSAGE.PRODUCT.ATTR_NEED);
			$("#tip_attr").css("color", "red");
			$("#product_detail").animate({scrollTop: $("#tip_product_status").position().top}, 1000);
			return;
		}else {
			check = true;
		};
		/*商品封面图片校验 */
		if (image.cover == null){  
			check = false;
			$("#product_detail").animate({scrollTop: $("#attr_container").position().top}, 1000);
			return;
			/*MessageUtils.warningMessage(MESSAGE.PRODUCT.COVERIMAGE_NEED, MESSAGE.TYPE.INFO, function() {
				check = false;
				window.location.hash = "#caoverImg_callback";
				return;
			});*/
	    }else {
			check = true;
		};
		/*商品详细图片校验 */
		if (image.detail == null){  
			check = false;
			$("#product_detail").animate({scrollTop: $("#img_cover_container").position().top}, 1000);
			return;
			/*MessageUtils.warningMessage(MESSAGE.PRODUCT.DETAILIMAGE_NEED, MESSAGE.TYPE.INFO, function() {
				check = false;
				window.location.hash = "#detailImg_callback";
				return;
			});*/
	    }else {
			check = true;
		};
		if (check == true){
			var productBody = JSON.stringify(newProductObject);
			
			var productForm = new FormData();
			
			productForm.append("productBody", productBody);
			
			if (undefined !== image.cover) {
				productForm.append("cover", image.cover, image.cover.name);
			}
	
			for (var file_id in image.detail) {
				productForm.append("files", image.detail[file_id], image.detail[file_id].name);
			}
			
			var xhr = new XMLHttpRequest();
			xhr.open("POST", "../product/info", true);
			xhr.onload = function() {
				console.log("上传完成");
				
				
				if (undefined === productDetail) {
					productDetail.destroy();
					productDetail = undefined;
				}
				
				$("#product_detail_temp").nextAll().each(function() {
					$(this).remove();
				});
			}
			
			xhr.upload.addEventListener("progress", function(event) {
				var completePercent = Math.round(event.loaded / event.total * 100)+ "%";
				console.log(completePercent);
			}, false);
			xhr.send(productForm);
		}else {
			MessageUtils.showMessage(MESSAGE.PRODUCT.PRODUCT_DETAL_UNDONE, MESSAGE.TYPE.INFO, function() {
				check = false;
				$("#product_detail").animate({scrollTop: $("#product_detail").offset().top}, 1000);
				//window.location.hash = "#product_detail";
				return;
			});
		};
	});
	
	$("#product_cancel").click(function() {
		if (undefined === productDetail) {
			productDetail.destroy();
			productDetail = undefined;
		}
		
		$("#product_detail_temp").nextAll().each(function() {
			$(this).remove();
		});
	});

	productDetail.open("商品详细");

}