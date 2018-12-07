<!DOCTYPE html>
<html>
	<head>
		<#include "../../common/bootstrap.ftl">
		<#include "../../common/resource.ftl">
		<#include "../../common/contain_resource.ftl">
		<script id="brand_detail_temp" type="text/html">
			<div id="brand_detail" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基本信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>品牌名</label>
			                	<div class="kv-item-content">
			                    	<input id="brand_name" name="brandName" type="text" placeholder="输入品牌名" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_brand_name" class="kv-item-tip"></span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label>品牌描述</label>
			                	<div class="kv-item-content">
			                    	<input id="brand_des" name="brandDes" type="text" placeholder="输入品牌描述" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_brand_des" class="kv-item-tip"></span>
			                </div>
			            </div>
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="brand_save" class="button" type="button" value="确认" />
	                <input id="brand_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
	            </div>
			</div>
		</script>	
		<script id="brand_update_temp" type="text/html">
			<div id="brand_update" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基本信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>品牌名</label>
			                	<div class="kv-item-content">
			                    	<input id="update_brand_name" name="brandName" type="text" placeholder="输入品牌名" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_brand_name" class="kv-item-tip"></span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label>品牌描述</label>
			                	<div class="kv-item-content">
			                    	<input id="update_brand_des" name="brandDes" type="text" placeholder="输入品牌描述" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_brand_des" class="kv-item-tip"></span>
			                </div>
			            </div>
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="update_brand_save" class="button" type="button" value="确认" />
	                <input id="update_brand_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
	            </div>
			</div>
		</script>	
		<script type="application/javascript" src="${ctx}/admin/js/as/system/product/brand/brand.js"></script>
	</head>
	<body>
	    <div style="padding: 20px;">
	        <div class="row">
	            <div class="col-xs-8">
	                <form class="form-inline">
	                    <div class="form-group" style="width: auto;">
	                        <input id="search_brandname" name="brandName" type="text" placeholder="请输入品牌名" class="form-control" />
	                    </div>
	                    <button id="btn_search" type="button" class="btn btn-default">查询</button>
	                </form>
	            </div>
	            <div class="col-xs-4">
	                <button id="btn_newbrand" type="button" class="btn btn-default pull-right">新增品牌</button>
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