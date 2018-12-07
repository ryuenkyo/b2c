var grid; 
var billboardDetail;		

function initBillboardData(where) {
	grid = $('#grid').grid({
        primaryKey: 'roleId',
        locale: "zh-s",
        dataSource: where,
        uiLibrary: 'bootstrap',
        columns: [
            { title: "ID", field: 'advertisementId', width: "5%" },
            { title: '广告目标地址', field: 'advertisementUrl', width: "20%" },
            { title: '图片地址', field: 'adImgUrl', width: "30%" },
            { title: '状态', field: 'status', width: "10%", tmpl: "<a>{statusName}</a>", tooltip: '点击改变状态', align: 'center', events: { 'click': changeStatus } },
            { title: '创建者', field: 'cuName', width: "15%" },
            { title: '创建时间', field: 'ct', width: "20%", type: "date", format:'yyyy/mm/dd HH:MM:ss' },
            { title: '', field: 'Edit', width: "5%", type: 'icon', icon: 'glyphicon-pencil', tooltip: '编辑角色', events: { 'click': editItem } },
            { title: '', field: 'Delete', width: "5%", type: 'icon', icon: 'glyphicon-remove', tooltip: '删除角色', events: { 'click': deleteItem } }
        ],
        pager: { limit: 10, sizes: [] }
    });
}

function deleteItem(e){
	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.BILLBOARD_DELETE_CONFIRM, 
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "DELETE",
				url: "../billboard/info/"+e.data.record.advertisementId,
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
		},
		function() {
			console.log("取消");
		}
	);
}

function changeStatus(e){
console.log(e.data.record.advertisementId);
	
	dataPost = {};
	dataPost.advertisementId = e.data.record.advertisementId;
	dataPost.status = e.data.record.status;
	dataPost.statusName = e.data.record.statusName;
	
	MessageUtils.showConfirmMessage(
		MESSAGE.PRODUCT.BILLBOARD_STATUS_CHANGE_WARNNING, 
		MESSAGE.TYPE.INFO, 
		function() {
			$.ajax({
				type: "POST",
				url: "../billboard/changeBillboardStatus.json",
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

function editItem(e){
    console.log(e.data.record);
    if (billboardDetail != undefined) {
		billboardDetail.destroy();
		$("#billboard_detail").remove();
		$("body").children(".model").remove();
		billboardDetail = undefined;
	}

	$("body").append($("#billboard_detail_temp").html());

	billboardDetail = $("#billboard_detail").dialog({
        uiLibrary: 'bootstrap',
        closeButtonInHeader: false,
        autoOpen: false,
        resizable: false,
        modal: true,
        width: "70%",
        height: "95%"
	});

    var updateBillboard = {};
    updateBillboard.checked = true;
    
    $("#billboard_url").val(e.data.record.advertisementUrl);
    $("#billboard_link").val(e.data.record.adImgUrl);
    if (e.data.record.status === "ACTIVATE") {
		$("#status").attr("checked",true);
	} else {
		$("#status").attr("checked",false);
	}

    $("#billboard_link").focusout(function() {
		if ($(this).val().length > 20) {
			$("#tip_billboard_des").css("color", "red");
			$("#tip_billboard_des").html(MESSAGE.STRING.LENGTH.OVERSTEP);
			return;
		}
		$("#tip_billboard_des").html("");
		updateBillboard.advertisementUrl = $(this).val();
    });		
	
    var statusCheckBox = $("#status").checkbox({
		uiLibrary: "bootstrap"
	});
	
	statusCheckBox.on("change", function(){
		if ("unchecked" === statusCheckBox.state()) {
			updateBillboard.status = "INACTIVATE";
		} else {
			updateBillboard.status = "ACTIVATE";
		}
	});
    
    $("#file_billboard").on("change", function() {
    	updateBillboard.billboard = this.files[0];
		var reader = new FileReader();
		reader.onload = function(e) {
			$("#billboard_show_img").attr("src",  e.target.result);
		};
		
		reader.readAsDataURL(this.files[0]);
	});
    
	$("#billboard_cancel").click(function() {
		billboardDetail.close();
		
		if (billboardDetail != undefined) {
			billboardDetail.destroy();
			$("#billboard_detail").remove();
			$("body").children(".modal").remove();
			billboardDetail = undefined;
		}
	});
	
	$("#billboard_save").click(function() {
		updateBillboard.advertisementId = e.data.record.advertisementId;
		if (!updateBillboard.checked) {
			MessageUtils.showMessage(MESSAGE.SCREEN.NOT_FINISH, MESSAGE.TYPE.INFO);
    		return;
		}

    	var dataPut = JSON.stringify(updateBillboard);

    	var formData = new FormData();
		
    	formData.append("dataPut", dataPut);
    	formData.append("billboard", updateBillboard.billboard, updateBillboard.billboard.name);
    	
    	var xhr = new XMLHttpRequest();
		xhr.open("POST", "../billboard/billboardUpdate/info", true);
		xhr.onload = function() {
			console.log("上传完成");
			if (undefined === billboardDetail) {
				billboardDetail.destroy();
				billboardDetail = undefined;
			}
			$("#billboard_detail_temp").nextAll().each(function() {
				$(this).remove();
			});
		}
		
		xhr.upload.addEventListener("progress", function(event) {
			var completePercent = Math.round(event.loaded / event.total * 100)+ "%";
			console.log(completePercent);
		}, false);
		xhr.send(formData);
		
    });
	billboardDetail.open("修改广告图片");
}

$(document).ready(function() {
	
	initBillboardData({
    	url: "../billboard/billboard.json", 
    	data: {}, 
    	success: function(res) {
    		grid.render(res);
    	}, 
    	error: function(err) {
    	}
    });
    
    $("#btn_newbillboard").click(function(e) {
		if (billboardDetail != undefined) {
			billboardDetail.destroy();
			$("#billboard_detail").remove();
			$("body").children(".model").remove();
			billboardDetail = undefined;
		}

		$("body").append($("#billboard_detail_temp").html());

		billboardDetail = $("#billboard_detail").dialog({
            uiLibrary: 'bootstrap',
            closeButtonInHeader: false,
            autoOpen: false,
            resizable: false,
            modal: true,
            width: "70%",
            height: "95%"
		});
 
        var newBillboard = {};
        newBillboard.checked = true;

        $("#billboard_link").focusout(function() {
			if ($(this).val().length > 20) {
				$("#tip_billboard_des").css("color", "red");
				$("#tip_billboard_des").html(MESSAGE.STRING.LENGTH.OVERSTEP);
				return;
			}
    		$("#tip_billboard_des").html("");
        	newBillboard.advertisementUrl = $(this).val();
        });		
		
        var statusCheckBox = $("#status").checkbox({
    		uiLibrary: "bootstrap"
    	});
    	
    	statusCheckBox.on("change", function(){
    		if ("unchecked" === statusCheckBox.state()) {
    			newBillboard.status = "INACTIVATE";
    		} else {
    			newBillboard.status = "ACTIVATE";
    		}
    	});
        
        $("#file_billboard").on("change", function() {
        	newBillboard.billboard = this.files[0];
    		var reader = new FileReader();
    		reader.onload = function(e) {
    			$("#billboard_show_img").attr("src",  e.target.result);
    		};
    		
    		reader.readAsDataURL(this.files[0]);
    	});
        
		$("#billboard_cancel").click(function() {
			billboardDetail.close();
			
			if (billboardDetail != undefined) {
				billboardDetail.destroy();
				$("#billboard_detail").remove();
				$("body").children(".modal").remove();
				billboardDetail = undefined;
			}
		});
		
		$("#billboard_save").click(function() {

			if (!newBillboard.checked) {
				MessageUtils.showMessage(MESSAGE.SCREEN.NOT_FINISH, MESSAGE.TYPE.INFO);
        		return;
			}

        	var dataPut = JSON.stringify(newBillboard);

        	var formData = new FormData();
			
        	formData.append("dataPut", dataPut);
        	formData.append("billboard", newBillboard.billboard, newBillboard.billboard.name);
        	
        	var xhr = new XMLHttpRequest();
			xhr.open("POST", "../billboard/info", true);
			xhr.onload = function() {
				console.log("上传完成");
				if (undefined === billboardDetail) {
					billboardDetail.destroy();
					billboardDetail = undefined;
				}
				$("#billboard_detail_temp").nextAll().each(function() {
					$(this).remove();
				});
			}
			
			xhr.upload.addEventListener("progress", function(event) {
				var completePercent = Math.round(event.loaded / event.total * 100)+ "%";
				console.log(completePercent);
			}, false);
			xhr.send(formData);
			
        });
		billboardDetail.open("新增广告图片");
	});
});