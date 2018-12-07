<!DOCTYPE html>
<html>
	<head>
		<#include "../common/bootstrap.ftl">
		<#include "../common/resource.ftl">
		<#include "../common/contain_resource.ftl">
		<style>
	    	.img-item {
	    		position: relative;
            	display: inline-block; 
            	margin: 10px;
            	width: 100px; 
            	height: 100px;
	    	}
	    	
	    	.img-item > img {
	    		position: absolute;
    			top: 50%;
    			display: block; 
    			transform: translateY(-50%);
    			width: 100%; 
    			max-height: 100%;
    			height: auto;
	    	}
	    	
	    	.img-item > label {
	    		position: absolute;
    			display: block;
    			width: 100%;
    			height: 100%;
    			background-color: rgba(255,255,255,0.8);
    			text-align: center;
    			border: #0055AA solid 1px;
    			line-height: 100px;
    			color: #0055AA;
	    	}
	    	
	    	.img-item > label:hover {
	    		cursor: pointer;
	    	}
	    	
	    	.img-item > span:hover {
	    		cursor: pointer;
	    	}
	    </style>
		<script type="application/javascript" src="${ctx}/admin/js/as/system/billboard/billboard.js"></script>
		<script id="billboard_detail_temp" type="text/html">
			<div id="billboard_detail" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基本信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			                <div class="kv-item ue-clear">
			                	<label>广告目标地址</label>
			                	<div class="kv-item-content">
			                    	<input id="billboard_link" name="billboardDes" type="text" placeholder="广告目标地址" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_billboard_des" class="kv-item-tip">请使用准确地址描述</span>
			                </div>
			            </div>
						<h2 class="subfild">
			            	<span class="impInfo">*轮播图片</span>
			            </h2>
			            <div id="img_cover_container" class="subfild-content base-info">
			                <div class="img-item" style="width: 600px; height: 280px;">
			                	<input 
			                		id="file_billboard" 
			                		type="file" 
			                		accept="image/*" 
			                		style="display: none;" />
			                	<img id="billboard_show_img" src=""/>
			                	<label for="file_billboard" >点击选择图片</label>
			                </div>
			            </div>
			            <div class="kv-item ue-clear">
		                	<label style="font-size:15px;"><span class="impInfo">*</span>是否激活</label>
		                	<div class="kv-item-content">
		                		<div class="a-row-item">
		                			<input type="checkbox" id="status" style="font-size:18px;"/><label for="status" style="font-size:18px;">激活</label>
		                		</div>
		                    </div>
		                    <span id="tip_product_status" class="kv-item-tip success"></span>
		               	</div>
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="billboard_save" class="button" type="button" value="确认" />
	                <input id="billboard_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
	            </div>
			</div>
		</script>	
	</head>
	<body>
	    <div style="padding: 20px;">
	        <div class="row">
	            <div class="col-xs-8">
	            </div>
	            <div class="col-xs-4">
	                <button id="btn_newbillboard" type="button" class="btn btn-default pull-right">新增轮播图片</button>
	            </div>
	        </div>
	        <div class="row" style="margin-top: 10px">
	            <div class="col-xs-12">
	                <table id="grid"></table>
	            </div>
	        </div>
	    </div>
	</body>
</html>