var grid;
var referralGrid;
var initClientData;
var initClientDetailData;

function initClientData(where) {
	grid = $('#grid').grid({
		//列表页面主键
        primaryKey: 'clientId',
        locale: "zh-s",
        //重新加载路径
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
            { title: "ID", field: 'clientId', width: "5%" },
            { title: '会员名', field: 'clientName', width: "15%", sortable: true },
            { title: '使用的推荐码', field: 'referralCode', width: "20%", sortable: true},
            { title: '详情', field: 'Update', width: "10%", type: 'icon', icon: 'glyphicon-info-sign', tooltip: '编辑推荐码', events: { 'click': moreDetail }, align: 'center' },
        ],
            pager: { limit: 5, sizes: [] }
        });
    }  
          
           
           
           
 

function initClientDetailData(where) {
		referralGrid = $('#referral_grid').grid({
		//详情页面主键
        primaryKey: 'clientId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
        	{ title: "ID", field: 'clientId', width: "5%" },
            { title: '会员名', field: 'clientName', width: "15%" },
            { title: '生成的推荐码', field: 'referralCode', width: "15%" },
            { title: '创建时间', field: 'ct', width: "15%", type: "date", format:'yyyy/mm/dd HH:MM:ss', sortable: true}
        ],
        pager: { limit: 5, sizes: [] }
    });
}
            
 
	
function moreDetail(e){
	$("body").append($("#referral_detail_temp").html());

	var referralDetail = $("#referral_detail").dialog({
        uiLibrary: 'bootstrap',
        closeButtonInHeader: false,
        autoOpen: false,
        modal: true,
        width: "50%",
        height: "60%"
	});
	
	//详情页面
	initClientDetailData({
    	url: "../client/clientReferralDetailList.json/"+e.data.record.clientId, 
		async: true,
    	success: function(res) {
    		var obj = JSON.parse(res);
    		referralGrid.render(obj);
    		console.log(obj);
    	}, 
    	error: function(err) {
    		
    	}
    });
	
	$("#referral_detail_button").click(function() {
		referralDetail.close();
		
		if (referralDetail != undefined) {
			referralDetail.destroy();
			$("#referral_detail").remove();
			$("body").children(".modal").remove();
			referralDetail = undefined;
		}
	});
	
	referralDetail.open();
}

$(document).ready(function() {
	//推荐码列表页
	initClientData({
    	url: "../client/clientReferralList.json", 
    	data: {}, 
    	success: function(res) {
    		//var obj = JSON.parse(res);
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
		
		var data = {};
		data.clientName = $("#search_clientname").val();
		
		initClientData({
	    	url: "../client/clientReferralList.json", 
	    	data: data, 
        	success: function(res) {
        		grid.render(res);
        	}, 
        	error: function(err) {
        		
        	}
        });
	});
});

