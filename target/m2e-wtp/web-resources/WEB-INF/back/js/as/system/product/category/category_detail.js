/* for detail */
var categoryDetail;
var statusCheckBox;

function changeStatus(e) {
	console.log(e.data);
}
/*add start by huangjun 2018-06-04 11:07:00*/
function changeStatus(e) {

	var dataPost = {};
	console.log(e.data.record.categoryId);
	

	dataPost.categoryId = e.data.record.categoryId;
	dataPost.status = e.data.record.status;
	dataPost.statusName = e.data.record.statusName;
	
	MessageUtils.showConfirmMessage(
			MESSAGE.PRODUCT.STATUS_CHANGE_WARNNING, 
			MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "POST",
				url: "../changeCategoryStatus.json",
				data: JSON.stringify(dataPost),
				async: true,
				headers : {  
                    'Content-Type' : 'application/json;charset=utf-8'  
                },  
				success: function(res) {
					if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
						MessageUtils.showMessage(MESSAGE.CHANGE_SUCCESS, MESSAGE.TYPE.INFO, function() {
							alert(1);
							grid.reload();
						});
					} else if (res[SERVICE.STATUS] === SERVICE.FAIL) {
						MessageUtils.showMessage(res[SERVICE.MESSAGE_BODY], MESSAGE.TYPE.INFO, function() {
							alert(1);
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
/*add end by huangjun 2018-06-04 11:07:00*/
function editCategory(e) {
	console.log(e.data.record);
	
	$.ajax({
		type: "GET",
		url: "../category/info",
		data: { "categoryId": e.data.record.categoryId },
		async: true,
		success: function(res) {
			console.log(res);
			var obj = JSON.parse(res);
			if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
				createUpdateCategory(obj);
			} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
				
			}
		},
		error: function(err) {
			
		}
	});
}

function detailCollapse(e, $detailWrapper, id) {
	$detailWrapper.find('table').grid('destroy', true, true);
}

function detailExpand(e, $detailWrapper, id) {
	if (undefined === id || undefined === CATEGORY_CHILDREN[id]) return;
	if (CATEGORY_CHILDREN[id].length > 0) {
		
		var categoryChildrenArray = [];
		for (var index in CATEGORY_CHILDREN[id]) {
			var childrenCategoryId = CATEGORY_CHILDREN[id][index];
			var childrenCategoryItem = CATEGORY_INFO[childrenCategoryId];
			categoryChildrenArray.push(childrenCategoryItem);
		}
		
		
		var gridChild = $detailWrapper.find('table').grid({
			primaryKey: 'categoryId',
			uiLibrary: 'bootstrap',
			columns: [
				{ field: 'categoryId', width: "30%", title: '分类ID'},
				{ field: 'categoryName', width: "auto", title: '分类名'},
				{ field: 'categoryLevel', width: "10%", title: '级别'},
				{ field: '', width: "3%", title: '', type: "icon", icon: "glyphicon-arrow-up", tooltip: '上移', align: 'center', events: { 'click': sortUp }},
				{ field: '', width: "3%", title: '', type: "icon", icon: "glyphicon-arrow-down", tooltip: '下移', align: 'center', events: { 'click': sortDown }},
				{ field: 'status', width: "10%", title: '状态', tmpl: "<a>{statusName}</a>" , tooltip: '点击改变状态', align: 'center', events: { 'click': changeStatus }},
				{ field: '', width: "10%", title: '操作', tmpl: "<a>编辑</a>", tooltip: '点击编辑', align: 'center', events: { 'click': editCategory }},
			],
			detailTemplate: "<div><table/></div>"
		});
		
		gridChild.render(categoryChildrenArray);
		
		gridChild.on("detailExpand", detailExpand);
		gridChild.on('detailCollapse', detailCollapse);
		
		gridMapper[id] = gridChild;
	}
		
}
			
function sortUp(e) {
	sortIndexChange("up", e.data.record);
}

function sortDown(e) {
	sortIndexChange("down", e.data.record);
}

function sortIndexChange(option, currentCategoryRecord) {
	var flg = 0;
	if (option === "up") {
		flg--;
	} else {
		flg++;
	}

	var currentGrid = undefined;
	if (currentCategoryRecord.pCategoryId === 0) {
		currentGrid = gridMapper.levelOut;
	} else {
		currentGrid = gridMapper[currentCategoryRecord.pCategoryId];
	}

	var index = currentGrid.getAll().indexOf(currentCategoryRecord);

	if (index === 0 && flg < 0) {
		console.log("already top");
		return;
	} else if (index === (currentGrid.getAll().length - 1) && flg > 0) {
		console.log("already bottom");
		return;
	}

	var targetCategory = currentGrid.getAll()[index+flg];
	var currentCategory = currentGrid.getAll()[index];
	
	console.log(targetCategory);
	console.log(currentCategory);
	// TODO send request to server by change sortIndex for category.
}

function getLevel1SourceArray(pLevelOne, pCategoryInfo) {
	var arraySource = [];
	for (var index in LEVEL_ONE_CATEGORY) {
		var categoryId = pLevelOne[index];
		var newItem = {};
		$.extend(true, newItem, pCategoryInfo[categoryId]);
		arraySource.push(newItem);
	}
	return arraySource;
}
			



function createUpdateCategory(updateCategoryObject) {
	var pCategory1;
	// 初始化父级分类下拉列表集合
	var dropDownMapper = {};
	
	// 取得分类详细弹窗页面源码
	var categoryDetailText = $("#categoryDetailTemp").html();
	
	// 将源码转换成dom组件源码
	var textAttrItem = template(categoryDetailText, {});

	// 将dom组件源码转换成jquerydom对象
	var updateCategoryItem = $(textAttrItem);
	
	// 将分类详细页jquerydom对象添加到body中
	$("body").append(updateCategoryItem);
	
	// 初始化gijgo分类详细页面对象
	categoryDetail = $("#category_detail").dialog({
		closeButtonInHeader: false,
        uiLibrary: 'bootstrap',
        autoOpen: false,
        resizable: false,
        modal: true,
        width: "90%",
        height: "90%"
    });
    
    // 注册取消按钮点击事件
    $("#category_cancel").click(function() {
    	categoryDetail.destroy();
    	delete newCategoryObject;
    	
    	$("#categoryDetailTemp").nextAll().each(function(){
    		$(this).remove();
    	});
    	
    });
    
    // 注册保存按钮点击事件
    $("#category_save").click(function() {
    	
    	console.log(newCategoryObject);
    	console.log(updateCategoryObject);
    	$.ajax({
    		type: "POST",
    		url: "../category/info",
    		async: true,
    		contentType: "application/json",
    		data: JSON.stringify({
    				"updateCategory": newCategoryObject,
    				"originalCategory": updateCategoryObject
    			}),
    		success: function(res) {
    			var obj = JSON.parse(res);
				if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
					MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
						if (undefined !== categoryDetail) {
							categoryDetail.destroy();
			            	delete newCategoryObject;
			        	}
		            	$("#categoryDetailTemp").nextAll().each(function(){
		            		$(this).remove();
		            	});
		            	gridMapper.levelOut.reload();
					});
				} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
					MessageUtils.showMessage(obj[SERVICE.MESSAGE_BODY], MESSAGE.TYPE.INFO, function() {
						if (undefined !== categoryDetail) {
							categoryDetail.destroy();
			            	delete newCategoryObject;
			        	}
		            	$("#categoryDetailTemp").nextAll().each(function(){
		            		$(this).remove();
		            	});
					});
				}
    		},
    		error: function(err) {
    			
    		}
    	});
    	
    	
    	
    });
    
    $("#new_attr_name").click(function() {
    	
    	var attrParentObject = {};
    	for (var categoryLevel in newCategoryObject.parentAttr) {
    		var attrParentItem = newCategoryObject.parentAttr[categoryLevel];
    		attrParentObject = $.extend(attrParentObject, attrParentItem);
    	}
    	
    	var attrDateSource = [];
    	for (var index in PRODUCT_ATTR_INFO.pAttrNameList) {
    		var pAttrNameId = PRODUCT_ATTR_INFO.pAttrNameList[index];
    		
    		if (attrParentObject[pAttrNameId] === undefined
    			&& newCategoryObject.selfAttr[pAttrNameId] === undefined) {
    			attrDateSource.push(PRODUCT_ATTR_INFO.attrNameMapper[pAttrNameId]);
    		}
    	}
    	
    	var attrSelectDetailItem = template($("#attr_select_detail").html(), {"attrList":attrDateSource});
    	$("body").append($(attrSelectDetailItem));
    	var attrDetail = $("#attr_select").dialog({
    		closeButtonInHeader: false,
            uiLibrary: 'bootstrap',
            autoOpen: false,
            resizable: false,
            modal: true,
            width: "70%",
            height: "50%"
        });
        
        $("#attr_save").click(function() {
        	
        	$(".attr-check").each(function() {
        		if ($(this).attr("checked") === "checked") {
        			var pAttrNameId = $(this).attr("id");
        			newCategoryObject.selfAttr[pAttrNameId] = pAttrNameId;
        			
        			
        			var newAttrNameText = $("#new_attr_name_item").html();
	            	var newAttrNameTemp = template(newAttrNameText, {
						"currentLevel": $("#category_level").val(),
						"pAttrNameItem": PRODUCT_ATTR_INFO.attrNameMapper[pAttrNameId]
	            	});
	            	
	            	$("#p_attr_container").append($(newAttrNameTemp));
	            	
	            	
	            	$("#new_self_attr_name_"+pAttrNameId).click(function() {
	            		delete newCategoryObject.selfAttr[pAttrNameId];
	            		$(this).parent().remove();
	            		
	            		if (undefined === newCategoryObject.selfAttr 
	            			|| Object.keys(newCategoryObject.selfAttr).length == 0) {
	            			for (var categoryLevel in dropDownMapper) {
			            		$("#p_category_"+categoryLevel).next().removeAttr("disabled");
			            	}
	            		}
	            		
	            		
	            	});
        		}
        	});
        	
        	if (Object.keys(newCategoryObject.selfAttr).length > 0) {
        		for (var categoryLevel in dropDownMapper) {
            		$("#p_category_"+categoryLevel).next().attr("disabled", "disabled");
            	}
        	}

        	if (undefined != attrDetail) {
        		attrDetail.destroy();
        		$("#attr_select").parent().remove();
        	}
        	
        });
        
        $("#attr_cancel").click(function() {
        	if (undefined != attrDetail) {
        		attrDetail.destroy();
        		$("#attr_select").parent().remove();
        	}
        });
        
        attrDetail.open("添加属性");
    	
    });
    
    // 创建新分类数据对象
    var newCategoryObject = {};
    
    // 创建新分类父级分类数据对象
    newCategoryObject.parentCategory = {};
    
    // 创建新分类父级分类属性对象
    newCategoryObject.parentAttr = {};
    
    // 创建新分类属性对象
    newCategoryObject.selfAttr = {};
    
    // 注册分类名称焦点离开事件：离开时赋值给新分类对象
    $("#category_name").val(updateCategoryObject.category.categoryName);
    $("#category_name").focusout(function() {
    	newCategoryObject.categoryName = $(this).val();
    });

	// 默认设置新分类的分类级别为1级（顶级）
	$("#category_level").val(updateCategoryObject.category.categoryLevel);
	
	// 初始化分类状态gijgo对象
	statusCheckBox = $("#status").checkbox({
		uiLibrary: "bootstrap"
	});
	
	// 默认分类状态为已激活
	if ("ACTIVATE" === updateCategoryObject.category.status) {
		statusCheckBox.state("checked");
	}
	
	// 注册分类状态checkbox选择事件（现在变化时赋值给新分类对象
	statusCheckBox.on("change", function(){
		if ("unchecked" === statusCheckBox.state()) {
			newCategoryObject.status = "INACTIVATE";
		} else {
			newCategoryObject.status = "ACTIVATE";
		}
	});
	
	// 取得1级（顶级）分类数据
	var level1Data = getLevel1SourceArray(LEVEL_ONE_CATEGORY, CATEGORY_INFO);
	
	// 在1级分类数据的前方加入 选择提示项目
	level1Data.unshift({
		categoryId: "0",
		categoryName: "请选择父级1级分类"
	});

	// 注册顶级分类下拉列表gijgo对象
	pCategory1 = $('#p_category_1').dropdown({ 
		uiLibrary: "bootstrap",
		valueField: "categoryId",
		textField: "categoryName",
		dataSource: level1Data,
		selectedField: 'selected'
	});
	
	if (undefined === updateCategoryObject.parentCategory["1"]) {
		pCategory1.value("0");
	} else {
		pCategory1.value(updateCategoryObject.parentCategory["1"]);
	}
	
	// 给顶级分类dom组件添加 分类级别属性
	$('#p_category_1').prop("level", "1");

	// 注册父级分类变化事件
	var pCategoryChange = function(pCategory) {
		
		return function(e) {
			
			// 取得当前变化的父级分类等级
			var currentLevel = parseInt($(e.currentTarget).prop("level"));
			
			// 在新分类对象中删除当前变化的分类等级
			delete newCategoryObject.parentCategory[currentLevel];
			
			delete newCategoryObject.parentAttr[currentLevel];
			var attrSelect = "a[class='from-parent-attr-name'][level='"+currentLevel+"']";
			$(attrSelect).remove();
			
			// 逐次按照当前变化的分类之下的所有分类全部删除
			var counter = currentLevel + 1;
			while (dropDownMapper[counter] !== undefined) {
				var target = dropDownMapper[counter].destroy();
				delete dropDownMapper[counter];
				delete newCategoryObject.parentCategory[counter];
				delete newCategoryObject.parentAttr[counter];
				$("#p_category_"+counter).remove();
				
				var attrSelect = "a[class='from-parent-attr-name'][level='"+counter+"']";
				$(attrSelect).remove();
				counter++;
			}
			
			/* 当选择分类下拉列表没有选择任何分类的时候 */
			if ("0" === pCategory.value()) {
				// 设置新分类对象的分类等级为当前变化分类的等级
				$("#category_level").val(currentLevel);
				// 赋值给新分类对象
				newCategoryObject.categoryLevel = currentLevel;
				
			/* 当选择分类下拉列表选择真实分类的时候 */
			} else {
				// 新分类 为 当前选择分类的子分类 因此 新分类等级为当前选择分类等级加1
				$("#category_level").val(currentLevel + 1);
				// 赋值给新分类对象
				newCategoryObject.parentCategory[currentLevel] = parseInt(pCategory.value());
				newCategoryObject.categoryLevel = currentLevel + 1;
				
				
				// 取得当前选择分类下的属性
				var categoryAttrInfoList = CATEGORY_ATTR_INFO[pCategory.value()];
				for (var index in categoryAttrInfoList) {
					
					var attrNameId = categoryAttrInfoList[index];
					
					var attrNameItem = PRODUCT_ATTR_INFO.attrNameMapper[attrNameId];
					
					if (newCategoryObject.parentAttr[currentLevel] === undefined) {
						newCategoryObject.parentAttr[currentLevel] = {};
					}
					
					newCategoryObject.parentAttr[currentLevel][attrNameId] = attrNameId;
					
					var parentAttrItemText = $("#from_prent_attr_name_item").html();
					
					var parentAttrItemTemp = template(parentAttrItemText, 
						{
							"pAttrNameItem": {
								"pAttrNameId": attrNameId,
								"pAttrName": attrNameItem.pAttrName
							},
							"categoryId": pCategory.value(),
							"currentLevel": currentLevel
						});
					
					$("#p_attr_container").append($(parentAttrItemTemp));
					
				}
			}

			// 取得当前选择分类下的所有子分类
			var childrenArray = CATEGORY_CHILDREN[pCategory.value()];
			
			/* 当前选择子分类下没有子分类的时候  没必要进行子分类加载 因此返回 */
			if (childrenArray === undefined || childrenArray.length === 0) {
				return;
			}
			
			// 取得新分类等级
			var domSelectLevel = parseInt($("#category_level").val());
			
			/* 构建子分类下拉列表数据 */
			var dataSourceArray = [];
			dataSourceArray.push({
				"categoryId": "0",
				"categoryName": "请选择父级"+ domSelectLevel +"级分类",
				"selected": true
			});
			for (var index in childrenArray) {
				var categoryId = childrenArray[index];
				var newItem = {};
				$.extend(true, newItem, CATEGORY_INFO[categoryId]);
				dataSourceArray.push(newItem);
			}
			
			// 初始化子分类dom组件的id
			var domSelectId = "p_category_" + domSelectLevel;
			
			// 创建子分类对象的jquery对象
			var selectChildren = $("<select id=\""+domSelectId+"\"></select>");
			
			// 添加分类等级属性
			selectChildren.prop("level", domSelectLevel);
			
			// 将子分类下拉列表添加到新增分类窗体中
			selectChildren.appendTo($("#p_category_container"));
			
			// 注册子分类gijgo下拉列表对象
			var selectChildrenDropDown = selectChildren.dropdown({ 
				uiLibrary: "bootstrap",
				valueField: "categoryId",
				textField: "categoryName",
				dataSource: dataSourceArray,
				selectedField: 'selected'
			});
			
			// 使用递归注册子分类选择事件
			selectChildrenDropDown.on("change", pCategoryChange(selectChildrenDropDown));
			
			// 将子分类下拉列表添加到父级分类下拉列表集合中
			dropDownMapper[domSelectLevel] = selectChildrenDropDown;

		};
	};

	$.extend(true, newCategoryObject.parentCategory, updateCategoryObject.parentCategory);
	
	for (var currentPLevel in updateCategoryObject.parentCategory) {
		var currentPCategoryId = updateCategoryObject.parentCategory[currentPLevel];
		
		// 取得当前选择分类下的所有子分类
		var childrenArray = CATEGORY_CHILDREN[currentPCategoryId];
		
		/* 当前选择子分类下没有子分类的时候  没必要进行子分类加载 因此返回 */
		if (childrenArray === undefined || childrenArray.length === 0) {
			return;
		}
		
		// 取得新分类等级
		var domSelectLevel = parseInt(currentPLevel)+1;
		
		/* 构建子分类下拉列表数据 */
		var dataSourceArray = [];
		dataSourceArray.push({
			"categoryId": "0",
			"categoryName": "请选择父级"+ domSelectLevel +"级分类",
			"selected": true
		});
		for (var index in childrenArray) {
			var categoryId = childrenArray[index];
			var newItem = {};
			$.extend(true, newItem, CATEGORY_INFO[categoryId]);
			dataSourceArray.push(newItem);
		}
		
		// 初始化子分类dom组件的id
		var domSelectId = "p_category_" + domSelectLevel;
		
		// 创建子分类对象的jquery对象
		var selectChildren = $("<select id=\""+domSelectId+"\"></select>");
		
		// 添加分类等级属性
		selectChildren.prop("level", domSelectLevel);
		
		// 将子分类下拉列表添加到新增分类窗体中
		selectChildren.appendTo($("#p_category_container"));
		
		// 注册子分类gijgo下拉列表对象
		var selectChildrenDropDown = selectChildren.dropdown({ 
			uiLibrary: "bootstrap",
			valueField: "categoryId",
			textField: "categoryName",
			dataSource: dataSourceArray,
			selectedField: 'selected'
		});

		var pCategoryIdDom = updateCategoryObject.parentCategory[domSelectLevel];
		
		if ("number" === typeof(pCategoryIdDom)) {
			selectChildrenDropDown.value(pCategoryIdDom.toString());
		}

		// 使用递归注册子分类选择事件
		selectChildrenDropDown.on("change", pCategoryChange(selectChildrenDropDown));
		
		// 将子分类下拉列表添加到父级分类下拉列表集合中
		dropDownMapper[domSelectLevel] = selectChildrenDropDown;
	}
	
	// 使用递归注册父级分类选择事件
	pCategory1.on("change", pCategoryChange(pCategory1));
	
	// 将父级分类下拉列表添加到父级分类下拉列表集合中
	dropDownMapper["1"] = pCategory1;
	
	$.extend(true, newCategoryObject.parentAttr, updateCategoryObject.parentAttr);

	// 取得当前选择分类下的属性
	for (var level in updateCategoryObject.parentAttr) {
		
		var attrNameMapObject = updateCategoryObject.parentAttr[level];
		
		var currentLevelCategoryId = attrNameMapObject["categoryId"];
		for (var attrNameId in attrNameMapObject) {
			
			if ("categoryId" === attrNameId) {
				continue;
			}
			
			var attrNameItem = PRODUCT_ATTR_INFO.attrNameMapper[attrNameId];
			
			if (newCategoryObject.parentAttr[level] === undefined) {
				newCategoryObject.parentAttr[level] = {};
			}
			
			var parentAttrItemText = $("#from_prent_attr_name_item").html();
			
			var parentAttrItemTemp = template(parentAttrItemText, 
			{
				"pAttrNameItem": {
					"pAttrNameId": attrNameId,
					"pAttrName": attrNameItem.pAttrName
				},
				"categoryId": currentLevelCategoryId,
				"currentLevel": level
			});
		
			$("#p_attr_container").append($(parentAttrItemTemp));
			
		}
		
	}
	
	if (Object.keys(updateCategoryObject.selfAttr).length > 0) {
		for (var categoryLevel in dropDownMapper) {
    		$("#p_category_"+categoryLevel).next().attr("disabled", "disabled");
    	}
	}
	
	$.extend(true, newCategoryObject.selfAttr, updateCategoryObject.selfAttr);

	for (var attrNameId in updateCategoryObject.selfAttr) {
		
		currentLevelCategoryId = updateCategoryObject.selfAttr["categoryId"];
		if ("categoryId" === attrNameId) {
			continue;
		}
		
    	var newAttrNameText = $("#new_attr_name_item").html();
    	var newAttrNameTemp = template(newAttrNameText, {
			"currentLevel": $("#category_level").val(),
			"pAttrNameItem": PRODUCT_ATTR_INFO.attrNameMapper[attrNameId]
    	});
    	
    	$("#p_attr_container").append($(newAttrNameTemp));

    	$("#new_self_attr_name_"+attrNameId).click(function() {
    		
    		var attrNameId = $(this).attr("p-attr-name-id");
    		delete newCategoryObject.selfAttr[attrNameId];
    		$(this).parent().remove();
    		
    		if (undefined === newCategoryObject.selfAttr 
    			|| Object.keys(newCategoryObject.selfAttr).length == 1) {
    			for (var categoryLevel in dropDownMapper) {
            		$("#p_category_"+categoryLevel).next().removeAttr("disabled");
            	}
    		}
    		
    		
    	});

	}
	
	
	
	
	
	
	
	
	
	// 打开新增分类窗体
	categoryDetail.open("新增商品分类");
}

function createNewCategory() {
	// 初始化父级分类下拉列表集合
	var dropDownMapper = {};
	
	// 取得分类详细弹窗页面源码
	var categoryDetailText = $("#categoryDetailTemp").html();
	
	// 将源码转换成dom组件源码
	var textAttrItem = template(categoryDetailText, {});

	// 将dom组件源码转换成jquerydom对象
	var newCategoryItem = $(textAttrItem);
	
	// 将分类详细页jquerydom对象添加到body中
	$("body").append(newCategoryItem);
	
	// 初始化gijgo分类详细页面对象
	categoryDetail = $("#category_detail").dialog({
		closeButtonInHeader: false,
        uiLibrary: 'bootstrap',
        autoOpen: false,
        resizable: false,
        modal: true,
        width: "90%",
        height: "90%"
    });
    
    // 注册取消按钮点击事件
    $("#category_cancel").click(function() {
    	categoryDetail.destroy();
    	delete newCategoryObject;
    	
    	$("#categoryDetailTemp").nextAll().each(function(){
    		$(this).remove();
    	});
    	
    });
    
    // 注册保存按钮点击事件
    $("#category_save").click(function() {
    	
    	console.log(newCategoryObject);

    	$.ajax({
    		type: "PUT",
    		url: "../category/info",
    		async: true,
    		contentType: "application/json",
    		data: JSON.stringify(newCategoryObject),
    		success: function(res) {
    			var obj = JSON.parse(res);
				if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
					MessageUtils.showMessage(MESSAGE.INSERT_SUCCESS, MESSAGE.TYPE.INFO, function() {
						if (undefined !== categoryDetail) {
							categoryDetail.destroy();
			            	delete newCategoryObject;
			        	}
		            	$("#categoryDetailTemp").nextAll().each(function(){
		            		$(this).remove();
		            	});
		            	gridMapper.levelOut.reload();
					});
				} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
					MessageUtils.showMessage(obj[SERVICE.MESSAGE_BODY], MESSAGE.TYPE.INFO, function() {
						if (undefined !== categoryDetail) {
							categoryDetail.destroy();
			            	delete newCategoryObject;
			        	}
		            	$("#categoryDetailTemp").nextAll().each(function(){
		            		$(this).remove();
		            	});
					});
				}
    		},
    		error: function(err) {
    			
    		}
    	});
    	
    	
    	
    });
    
    $("#new_attr_name").click(function() {
    	
    	var attrParentObject = {};
    	for (var categoryLevel in newCategoryObject.parentAttr) {
    		var attrParentItem = newCategoryObject.parentAttr[categoryLevel];
    		attrParentObject = $.extend(attrParentObject, attrParentItem);
    	}
    	
    	var attrDateSource = [];
    	for (var index in PRODUCT_ATTR_INFO.pAttrNameList) {
    		var pAttrNameId = PRODUCT_ATTR_INFO.pAttrNameList[index];
    		
    		if (attrParentObject[pAttrNameId] === undefined
    			&& newCategoryObject.selfAttr[pAttrNameId] === undefined) {
    			attrDateSource.push(PRODUCT_ATTR_INFO.attrNameMapper[pAttrNameId]);
    		}
    	}
    	
    	var attrSelectDetailItem = template($("#attr_select_detail").html(), {"attrList":attrDateSource});
    	$("body").append($(attrSelectDetailItem));
    	var attrDetail = $("#attr_select").dialog({
    		closeButtonInHeader: false,
            uiLibrary: 'bootstrap',
            autoOpen: false,
            resizable: false,
            modal: true,
            width: "70%",
            height: "50%"
        });
        
        $("#attr_save").click(function() {
        	
        	$(".attr-check").each(function() {
        		if ($(this).attr("checked") === "checked") {
        			var pAttrNameId = $(this).attr("id");
        			newCategoryObject.selfAttr[pAttrNameId] = pAttrNameId;
        			
        			
        			var newAttrNameText = $("#new_attr_name_item").html();
	            	var newAttrNameTemp = template(newAttrNameText, {
						"currentLevel": $("#category_level").val(),
						"pAttrNameItem": PRODUCT_ATTR_INFO.attrNameMapper[pAttrNameId]
	            	});
	            	
	            	$("#p_attr_container").append($(newAttrNameTemp));
	            	
	            	
	            	$("#new_self_attr_name_"+pAttrNameId).click(function() {
	            		delete newCategoryObject.selfAttr[pAttrNameId];
	            		$(this).parent().remove();
	            		
	            		if (undefined === newCategoryObject.selfAttr 
	            			|| Object.keys(newCategoryObject.selfAttr).length == 0) {
	            			for (var categoryLevel in dropDownMapper) {
			            		$("#p_category_"+categoryLevel).next().removeAttr("disabled");
			            	}
	            		}
	            		
	            		
	            	});
        		}
        	});
        	
        	if (Object.keys(newCategoryObject.selfAttr).length > 0) {
        		for (var categoryLevel in dropDownMapper) {
            		$("#p_category_"+categoryLevel).next().attr("disabled", "disabled");
            	}
        	}
        	
        	
        	
        	
        	
        	
        	
        	if (undefined != attrDetail) {
        		attrDetail.destroy();
        		$("#attr_select").parent().remove();
        	}
        	
        });
        
        $("#attr_cancel").click(function() {
        	if (undefined != attrDetail) {
        		attrDetail.destroy();
        		$("#attr_select").parent().remove();
        	}
        });
        
        attrDetail.open("添加属性");
    	
    });
    
    // 创建新分类数据对象
    var newCategoryObject = {};
    
    // 创建新分类父级分类数据对象
    newCategoryObject.parentCategory = {};
    
    // 创建新分类父级分类属性对象
    newCategoryObject.parentAttr = {};
    
    // 创建新分类属性对象
    newCategoryObject.selfAttr = {};
    
    // 注册分类名称焦点离开事件：离开时赋值给新分类对象
    $("#category_name").focusout(function() {
    	newCategoryObject.categoryName = $(this).val();
    	console.log(newCategoryObject);
    });

	// 默认设置新分类的分类级别为1级（顶级）
	$("#category_level").val("1");
	newCategoryObject.categoryLevel = 1;
	
	// 初始化分类状态gijgo对象
	statusCheckBox = $("#status").checkbox({
		uiLibrary: "bootstrap"
	});
	
	// 默认分类状态为已激活
	statusCheckBox.state("checked");
	newCategoryObject.status = "ACTIVATE";
	
	// 注册分类状态checkbox选择事件（现在变化时赋值给新分类对象
	statusCheckBox.on("change", function(){
		if ("unchecked" === statusCheckBox.state()) {
			newCategoryObject.status = "INACTIVATE";
		} else {
			newCategoryObject.status = "ACTIVATE";
		}
	});
	
	// 取得1级（顶级）分类数据
	var level1Data = getLevel1SourceArray(LEVEL_ONE_CATEGORY, CATEGORY_INFO);
	
	// 在1级分类数据的前方加入 选择提示项目
	level1Data.unshift({
		categoryId: "0",
		categoryName: "请选择父级1级分类",
		selected: true
	});

	// 注册顶级分类下拉列表gijgo对象
	var pCategory1 = $('#p_category_1').dropdown({ 
		uiLibrary: "bootstrap",
		valueField: "categoryId",
		textField: "categoryName",
		dataSource: level1Data,
		selectedField: 'selected'
	});
	
	// 给顶级分类dom组件添加 分类级别属性
	$('#p_category_1').prop("level", "1");
	
	// 注册父级分类变化事件
	var pCategoryChange = function(pCategory) {
		
		return function(e) {
			
			// 取得当前变化的父级分类等级
			var currentLevel = parseInt($(e.currentTarget).prop("level"));
			
			// 在新分类对象中删除当前变化的分类等级
			delete newCategoryObject.parentCategory[currentLevel];
			
			delete newCategoryObject.parentAttr[currentLevel];
			var attrSelect = "a[class='from-parent-attr-name'][level='"+currentLevel+"']";
			$(attrSelect).remove();
			
			// 逐次按照当前变化的分类之下的所有分类全部删除
			var counter = currentLevel + 1;
			while (dropDownMapper[counter] !== undefined) {
				var target = dropDownMapper[counter].destroy();
				delete dropDownMapper[counter];
				delete newCategoryObject.parentCategory[counter];
				delete newCategoryObject.parentAttr[counter];
				$("#p_category_"+counter).remove();
				
				var attrSelect = "a[class='from-parent-attr-name'][level='"+counter+"']";
				$(attrSelect).remove();
				counter++;
			}
			
			/* 当选择分类下拉列表没有选择任何分类的时候 */
			if ("0" === pCategory.value()) {
				// 设置新分类对象的分类等级为当前变化分类的等级
				$("#category_level").val(currentLevel);
				// 赋值给新分类对象
				newCategoryObject.categoryLevel = currentLevel;
				
			/* 当选择分类下拉列表选择真实分类的时候 */
			} else {
				// 新分类 为 当前选择分类的子分类 因此 新分类等级为当前选择分类等级加1
				$("#category_level").val(currentLevel + 1);
				// 赋值给新分类对象
				newCategoryObject.parentCategory[currentLevel] = pCategory.value();
				newCategoryObject.categoryLevel = currentLevel + 1;
				
				
				// 取得当前选择分类下的属性
				var categoryAttrInfoList = CATEGORY_ATTR_INFO[pCategory.value()];
				for (var index in categoryAttrInfoList) {
					
					var attrNameId = categoryAttrInfoList[index];
					
					var attrNameItem = PRODUCT_ATTR_INFO.attrNameMapper[attrNameId];
					
					if (newCategoryObject.parentAttr[currentLevel] === undefined) {
						newCategoryObject.parentAttr[currentLevel] = {};
					}
					
					newCategoryObject.parentAttr[currentLevel][attrNameId] = attrNameId;
					
					var parentAttrItemText = $("#from_prent_attr_name_item").html();
					
					var parentAttrItemTemp = template(parentAttrItemText, 
						{
							"pAttrNameItem": {
								"pAttrNameId": attrNameId,
								"pAttrName": attrNameItem.pAttrName
							},
							"categoryId": pCategory.value(),
							"currentLevel": currentLevel
						});
					
					$("#p_attr_container").append($(parentAttrItemTemp));
					
				}
			}

			// 取得当前选择分类下的所有子分类
			var childrenArray = CATEGORY_CHILDREN[pCategory.value()];
			
			/* 当前选择子分类下没有子分类的时候  没必要进行子分类加载 因此返回 */
			if (childrenArray === undefined || childrenArray.length === 0) {
				return;
			}
			
			// 取得新分类等级
			var domSelectLevel = parseInt($("#category_level").val());
			
			/* 构建子分类下拉列表数据 */
			var dataSourceArray = [];
			dataSourceArray.push({
				"categoryId": "0",
				"categoryName": "请选择父级"+ domSelectLevel +"级分类",
				"selected": true
			});
			for (var index in childrenArray) {
				var categoryId = childrenArray[index];
				var newItem = {};
				$.extend(true, newItem, CATEGORY_INFO[categoryId]);
				dataSourceArray.push(newItem);
			}
			
			// 初始化子分类dom组件的id
			var domSelectId = "p_category_" + domSelectLevel;
			
			// 创建子分类对象的jquery对象
			var selectChildren = $("<select id=\""+domSelectId+"\"></select>");
			
			// 添加分类等级属性
			selectChildren.prop("level", domSelectLevel);
			
			// 将子分类下拉列表添加到新增分类窗体中
			selectChildren.appendTo($("#p_category_container"));
			
			// 注册子分类gijgo下拉列表对象
			var selectChildrenDropDown = selectChildren.dropdown({ 
				uiLibrary: "bootstrap",
				valueField: "categoryId",
				textField: "categoryName",
				dataSource: dataSourceArray,
				selectedField: 'selected'
			});
			
			// 使用递归注册子分类选择事件
			selectChildrenDropDown.on("change", pCategoryChange(selectChildrenDropDown));
			
			// 将子分类下拉列表添加到父级分类下拉列表集合中
			dropDownMapper[domSelectLevel] = selectChildrenDropDown;

		};
	};
	
	// 使用递归注册父级分类选择事件
	pCategory1.on("change", pCategoryChange(pCategory1));
	
	// 将父级分类下拉列表添加到父级分类下拉列表集合中
	dropDownMapper["1"] = pCategory1;
	
	// 打开新增分类窗体
	categoryDetail.open("新增商品分类");
				
}