var CATEGORY_INFO; //categoryInfo
var LEVEL_ONE_CATEGORY; //levelOne
var CATEGORY_CHILDREN; //categoryChildren
var PRODUCT_ATTR_INFO; //productAttrInfo
var CATEGORY_ATTR_INFO; //categoryAttrInfo
//var ATTR_VALUE_MAPPER //attrValueMapper

function initProductData(where) {
	grid = $('#grid').grid({
        primaryKey: 'ID',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap4',
        columns: [
            { title: "ID", field: 'productId', width: "3%" },
            { title: '商品名', field: 'productName', width: "auto" },
            { title: '品牌名', field: 'brandName', width: "10%" },
            { title: '分类', field: 'categoryName', width: "5%" },
            { title: '<span class="glyphicon glyphicon-sort">显示价格</span>', field: 'showPrice', width: "10%", sortable: true },
            { title: '<span class="glyphicon glyphicon-sort">销量</span>', field: 'salesVolume', width: "10%", sortable: true },
            { title: '<span class="glyphicon glyphicon-sort">创建时间</span>', field: 'ct', width: "15%", sortable: true ,type:"date",format: ' yyyy/mm/dd HH:MM:ss'},
            { title: '状态', field: 'statusName', width: "5%", tmpl: "<a>{statusName}</a>", tooltip: '点击改变状态', align: 'center', events: { 'click': changeStatus } },
            { title: '', field: 'Edit', width: "3%", type: 'icon', icon: 'glyphicon-pencil', tooltip: '点击编辑商品', events: { 'click': editProduct } },
        ],
        pager: { limit: 5, sizes: [] }
    });
}


function changeStatus(e) {
	console.log(e.data.record.productId);
	
	dataPost = {};
	dataPost.productId = e.data.record.productId;
	dataPost.status = e.data.record.status;
	dataPost.statusName = e.data.record.statusName;
	
	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.STATUS_CHANGE_WARNNING, 
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "POST",
				url: "../product/changeProductStatus.json",
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

$(document).ready(function() {
	
	initProductData({
    	url: '../product/productList.json', 
    	data: {}, 
    	success: function(res) {
    		grid.render(res);
    	}, 
    	error: function(err) {
    		
    	}
    });
	
	$.ajax({
		type:"GET",
		url:"../product/attr/productAttr.json",
		async:true,
		success: function(res) {
			PRODUCT_ATTR_INFO = res;
		},
		error: function(err) {
			
		}
	});
	
	$.ajax({
		type:"GET",
		url:"../product/category/categoryInfo.json",
		async:true,
		success: function(res) {
			CATEGORY_INFO = res.categoryInfo;
			LEVEL_ONE_CATEGORY = res.levelOne;
			CATEGORY_CHILDREN = res.children;
			CATEGORY_ATTR_INFO = res.categoryAttrInfo;
		},
		error: function(err) {
			
		}
	});
	
	$('#search_status').dropdown({ 
		uiLibrary: "bootstrap",
		dataSource: "../product/status.json",
		valueField: "type",
		textField: "name",
		selectedField: 'selected'
	});
	
	$.ajax({
		type:"get",
		url:"../product/brand.json",
		async:true,
		success: function(res) {
			//将lis变成json
			//在list前加一个元素。
			res.unshift({
				brandId: "",
				brandName: "请选择品牌",
				selected: true
			});
			
			brandDropDown = $('#search_brand').dropdown({ 
				uiLibrary: "bootstrap",
				dataSource: res,
				valueField: "brandId",
				textField: "brandName"
//					selectedField: 'selected'
			});
		},
		error: function(err) {
			
		}
	});
	
	/* add start by liuzhaoyu for product search  2018-05-11 18:42:30 */
	//搜索按钮点击事件
	$("#btn_search").click(function() {
		grid.clear();
		grid.destroy();
		$("#grid").children().each(function() {
			$(this).remove();
		});
		//取得搜索框的值
		data = {};
		data.productName = $("#search_productname").val();
		data.status = $("#search_status").val();
		data.brandId = brandDropDown.value();
        //将值传到方法里，让grid重新带值加载
		initProductData({
        	url: '../product/productList.json', 
        	"data": data, 
        	success: function(res) {
        		grid.render(res);
        	}, 
        	error: function(err) {
        		
        	}
        });
	});
	/* add end by liuzhaoyu for product search  2018-05-11 18:42:30 */
	$("#btn_newproduct").click(function() {
		createNewProduct();
	});
	
	
});