var grid;
var initClientData;

function initClientData(where) {
	grid = $('#grid').grid({
        primaryKey: 'clientId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap4',
        columns: [
            { title: "ID", field: 'clientId', width: "5%" },
            { title: '会员名', field: 'clientName', width: "15%", sortable: true },
            { title: '昵称', field: 'nickName', width: "15%", sortable: true },
            { title: '年龄', field: 'age', width: "5%", },
            { title: '电话', field: 'telphone', width: "25%" },
            { title: '状态', field: 'status', width: "10%", tmpl: "<a>{statusName}</a>", tooltip: '点击改变状态', align: 'center', events: { 'click': changeStatus } },
            { title: '创建时间', field: 'ct', width: "12.5%", type: "date", format:'yyyy/mm/dd HH:MM:ss', sortable: true},
            { title: '更新时间', field: 'ut', width: "12.5%", type: "date", format:'yyyy/mm/dd HH:MM:ss', sortable: true}
        ],
        pager: { limit: 10, sizes: [] }
    });
}
	
function changeStatus(e) {
	console.log(e.data.record);
	
	dataPost = {};
	dataPost.clientId = e.data.record.clientId;
	dataPost.status = e.data.record.status;
	
	MessageUtils.showConfirmMessage(
		MESSAGE.CLIENT.STATUS_CHANGE_WARNNING, 
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "POST",
				url: "../client/changeClientStatus.json",
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

$(document).ready(function() {
	initClientData({
    	url: "../client/clientList.json", 
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
		data.clientinfo = $("#clientinfo").val();
		
		initClientData({
        	url: "../client/clientList.json", 
        	"data": data, 
        	success: function(res) {
        		grid.render(res);
        	}, 
        	error: function(err) {
        		
        	}
        });
	});
});