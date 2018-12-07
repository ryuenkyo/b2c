var grid;
var pointGrid;
var initClientData;
var initClientDetailData;

function initClientData(where) {
	grid = $('#grid').grid({
        primaryKey: 'clientId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
            { title: "ID", field: 'clientId', width: "5%" },
            { title: '会员名', field: 'clientName', width: "15%", sortable: true },
            { title: '昵称', field: 'nickName', width: "15%", sortable: true },
            { title: '积分', field: 'point', width: "15%", sortable: true},
            { title: '等级', field: 'levelName', width: "15%" },
            { title: '', field: 'Update', width: "5%", type: 'icon', icon: 'glyphicon-info-sign', tooltip: '编辑商品', events: { 'click': moreDetail }, align: 'center' },
            { title: '创建时间', field: 'ct', width: "15%", type: "date", format:'yyyy/mm/dd HH:MM:ss', sortable: true},
            { title: '更新时间', field: 'ut', width: "15%", type: "date", format:'yyyy/mm/dd HH:MM:ss', sortable: true}
        ],
        pager: { limit: 10, sizes: [] }
    });
}

function initClientDetailData(where) {
	pointGrid = $('#point_grid').grid({
        primaryKey: 'recordId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
            { title: "ID", field: 'recordId', width: "5%" },
            { title: '会员名', field: 'clientName', width: "15%" },
            { title: '昵称', field: 'nickName', width: "15%" },
            { title: '积分途径', field: 'pointReason', width: "15%" },
            { title: '获得分值', field: 'point', width: "15%" },
            { title: '更新时间', field: 'ut', width: "15%", type: "date", format:'yyyy/mm/dd HH:MM:ss', sortable: true}
        ],
        pager: { limit: 10, sizes: [] }
    });
}
	
function moreDetail(e){
	$("body").append($("#point_detail_temp").html());

	var pointDetail = $("#point_detail").dialog({
        uiLibrary: 'bootstrap',
        closeButtonInHeader: false,
        autoOpen: false,
        modal: true,
        width: "50%",
        height: "60%"
	});
	
	initClientDetailData({
    	url: "../client/clientPointDetailList.json/"+e.data.record.clientId, 
		async: true,
    	success: function(res) {
    		var obj = JSON.parse(res);
    		pointGrid.render(obj);
    		console.log(obj);
    	}, 
    	error: function(err) {
    		
    	}
    });
	
	$("#point_detail_button").click(function() {
		pointDetail.close();
		
		if (pointDetail != undefined) {
			pointDetail.destroy();
			$("#point_detail").remove();
			$("body").children(".modal").remove();
			pointDetail = undefined;
		}
	});
	
	pointDetail.open();
}

$(document).ready(function() {
	initClientData({
    	url: "../client/clientPointList.json", 
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
		data.clientPoints = $("#clientPoints").val();
		
		initClientData({
	    	url: "../client/clientPointList.json", 
        	"data": data, 
        	success: function(res) {
        		grid.render(res);
        	}, 
        	error: function(err) {
        		
        	}
        });
	});
});

