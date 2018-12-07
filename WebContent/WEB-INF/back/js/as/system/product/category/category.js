/* for info */
var CATEGORY_INFO; //categoryInfo
var LEVEL_ONE_CATEGORY; //levelOne
var CATEGORY_CHILDREN; //categoryChildren
var PRODUCT_ATTR_INFO; //productAttrInfo
var CATEGORY_ATTR_INFO; //categoryAttrInfo

/* for list */
var grid;
var gridMapper = {};


$(document).ready(function() {
	
	$.ajax({
		type:"GET",
		url:"../attr/productAttr.json",
		async:true,
		success: function(res) {
			PRODUCT_ATTR_INFO = res;
			
			grid = $("#grid").grid({
				primaryKey: 'categoryId',
				uiLibrary: 'bootstrap',
				dataSource: {
					url: "../category/categoryInfo.json",
					success: function(res) {
						CATEGORY_INFO = res.categoryInfo;
						LEVEL_ONE_CATEGORY = res.levelOne;
						CATEGORY_CHILDREN = res.children;
						CATEGORY_ATTR_INFO = res.categoryAttrInfo;
						grid.render(getLevel1SourceArray(LEVEL_ONE_CATEGORY, CATEGORY_INFO));
						gridMapper.levelOut = grid;
					}
				},
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
			
			grid.on("detailExpand", detailExpand);
			
			grid.on('detailCollapse', detailCollapse);

		},
		error: function(err) {
			
		}
	});
	
	$("#btn_newcategory").click(function() {
		createNewCategory();
	});
	
	
});