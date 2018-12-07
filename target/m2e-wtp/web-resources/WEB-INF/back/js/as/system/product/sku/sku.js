
var grid; 
var skuDetail;
function initSkuData(where) {
	grid = $('#grid').grid({
    primaryKey: 'skuId',
    locale: "zh-s",
    dataSource: where,

    uiLibrary: 'bootstrap',
    columns: [
    	{ title: "ID", field: 'skuId', width: "8%" },
        { title: '商品名', field: 'productName', width: "8%" },
        { title: '属性', field: 'skuCollectionShow', width: "25%" },
        { title: '<span class="glyphicon glyphicon-sort">价格</span>', field: 'price', width: "10%", sortable: true },
        { title: '<span class="glyphicon glyphicon-sort">库存量</span>', field: 'stock', width: "10%", sortable: true },
        { title: '<span class="glyphicon glyphicon-sort">销量</span>', field: 'salesVolume', width: "10%", sortable: true },
        { title: '', field: 'Edit', width: "5%", type: 'icon', icon: 'glyphicon-pencil', tooltip: '点击编辑库存', events: { 'click': editSku } },
        { title: '', field: 'Delete', width: "5%", type: 'icon', icon: 'glyphicon-remove', tooltip: '点击删除库存', events: { 'click': deleteSku } }
    ],
    pager: { limit: 5, sizes: [] }
    });
}


$(document).ready(function() {
	initSkuData({
    	url: "../sku.json", 
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
		data.productName = $("#search_productname").val();
		initSkuData({
        	url: "../sku.json", 
        	data: data, 
        	success: function(res) {
        		grid.render(res);
        	}, 
        	error: function(err) {
        		
        	}
        });
		
    });
	
	$("#btn_newsku").click(function() {
		createNewSku();
	});
	

});

