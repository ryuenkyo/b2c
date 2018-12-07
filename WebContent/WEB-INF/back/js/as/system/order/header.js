var grid;			
var Header;
var itemDetail;
var itemGrid
var initOrderData
function initOrderData(where) {
	grid = $('#grid').grid({
        primaryKey: 'orderId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
            { title: "订单ID", field: 'orderId', width: "10%" },
            { title: '状态', field: 'orderStatus', width: "15%", tmpl: "<a>{orderStatus}</a>", tooltip: '点击改变状态', align: 'center', events: { 'click': changeStatus } },
            { title: '总价', field: 'amount', width: "15%", sortable: true },
            { title: '会员名', field: 'clientName', width: "15%", sortable: true },
            { title: '支付方式', field: 'payChannel', width: "25%"},
            { title: '订单时间', field: 'orderTime', width: "25%"},
            { title: '地址', field: 'address', width: "25%"},
            { title: '物流方式', field: 'logisticsType', width: "25%"},
            { title: '物流价格', field: 'logisticsAmount', width: "20%"},
            { title: '商品数量', field: 'productNum', width: "25%"},
            { title: '商品总价', field: 'productAmount', width: "25%"},
            { title: '发货时间', field: 'sendTime', width: "20%", type: "date", format:'yyyy/mm/dd HH:MM:ss' },
            { title: '接收时间', field: 'settlementTime', width: "20%", type: "date", format:'yyyy/mm/dd HH:MM:ss' },
            { title: '详情', field: 'Update', width: "10%", type: 'icon', icon: 'glyphicon-info-sign', tooltip: '订单详情', events: { 'click': moreDetail }, align: 'center' },
        ],
        pager: { limit: 3, sizes: [3] }
    });
}
/**
 * 订单详情展示
 * 
 * @author ming
 *
 */
function initClientDetailData(where) {
	itemGrid = $('#item_grid').grid({
		//详情页面主键
        primaryKey: 'orderId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
        	 { title: "订单号", field: 'orderId', width: "5%" },
        	 { title: '商品名', field: 'productName', width: "15%" },
             { title: '商品展示价', field: 'productShowPrice', width: "15%" },
             { title: '商品售出价', field: 'skuPrice', width: "15%" },
             { title: '商品属性', field: 'skuCollection', width: "15%" },
             { title: '库存数量', field: 'subTotal', width: "15%" },
             { title: '数量', field: 'number', width: "15%" },
             { title: '创建时间', field: 'ct', width: "15%", type: "date", format:'yyyy/mm/dd HH:MM:ss', sortable: true}
            
        ],
      
    });
}
/**
 * 详情的显示
 * @param {Object} e
 */
function moreDetail(e){
	$("body").append($("#item_detail_temp").html());

	var itemDetail = $("#item_detail").dialog({
        uiLibrary: 'bootstrap',
        closeButtonInHeader: false,
        autoOpen: false,
        modal: true,
        width: "50%",
        height: "60%"
	});
	
	//详情页面
	initClientDetailData({
    	url: "../order/orderItem.json/"+e.data.record.orderId, 
		async: true,
    	success: function(res) {
    		var obj = JSON.parse(res);
    		itemGrid.render(obj);
    		console.log(obj);
    	}, 
    	error: function(err) {
    	}
    });
	
	$("#item_detail_button").click(function() {
		itemDetail.close();
		if (itemDetail != undefined) {
			itemDetail.destroy();
			$("#item_detail").remove();
			$("body").children(".modal").remove();
			itemDetail = undefined;
		}
	});
	
	itemDetail.open("订单详情");
}

function changeStatus(e) {
	console.log(e.data.record);
	
	Header = {};
	Header.orderStatus = e.data.record.orderStatus;
	Header.orderId = e.data.record.orderId;

	
	if(e.data.record.orderStatus!="已支付"){
	    MessageUtils.showMessage("只能更改已支付的状态", MESSAGE.TYPE.INFO, function() {});		
		return;
	}

	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.ORDER.ORDER_STATUS, 
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "POST",
				url: "../order/updateOrderHeaderStatus",
				data: JSON.stringify(Header),
				async: true,
				
				headers : {  
                    'Content-Type' : 'application/json;charset=utf-8'  
                },  
				success: function(res) {
					MessageUtils.showMessage(MESSAGE.CHANGE_SUCCESS, MESSAGE.TYPE.INFO, function() {
						grid.reload();
					});

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

	
	initOrderData({
    	url: "../order/orderHeader.json", 
    	data: {}, 
    	success: function(res) {
    		grid.render(res);
    	}, 
    	error: function(err) {
    		
    	}
    });
     $("#btn_search").click(function() {
	        grid.clear();
	        grid.destroy();
	 $("#grid").children().each(function() {
			$(this).remove();
		});
	data = {};
	data.clientName = $("#search_Header").val();
	console.log(data.clientName);
	initOrderData({
    	url: "../order/orderHeader.json", 
    	"data": data, 
    	success: function(res) {
    		grid.render(res);
    	}, 
    	error: function(err) {
    		
    	}
    });
});
});