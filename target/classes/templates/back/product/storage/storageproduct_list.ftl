<!DOCTYPE html>
<html>
	<head>
		<#include "../../common/bootstrap.ftl">
		<#include "../../common/resource.ftl">
		<#include "../../common/contain_resource.ftl">
		<script type="application/javascript" src="${ctx}/admin/js/as/system/product/storage/storage.js"></script>
		<script id="storage_detail_temp" type="text/html">
			<div id="storage_detail" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基本信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>货架名</label>
			                	<div class="kv-item-content">
			                    	<input id="storage_name" name="storageName" type="text" placeholder="输入货架名" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_storage_name" class="kv-item-tip"></span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label>货架描述</label>
			                	<div class="kv-item-content">
			                    	<input id="storage_des" name="storageDes" type="text" placeholder="输入货架描述" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_storage_des" class="kv-item-tip"></span>
			                </div>
			                <h2 class="subfild">
								<span class="impInfo">货架商品</span>
								<div style="float: right; margin-top: -20px; margin-left: 80px; font-size: 15px;">
				            		<a href="javascript:void(0)" id="new_product" img-count="0" class="glyphicon glyphicon-plus">选择商品</a>
				            	</div>
							</h2>
							<div class="subfild-content base-info">
								<div class="kv-item ue-clear">
									<label>当前商品</label>
									<div class="kv-item-content">
										<!-- <div class="a-row-item a-form-input">
											<input id="productId" name="productId" type="hidden" class="input-group form-control a-form-input" />
											<input id="productName" name="productName" type="text" readonly="readonly" class="input-group form-control a-form-input" />
										</div> -->
										<div id="panel_body_onbody" class="panel-body">
										</div>
									</div>
								</div>
							</div>
			            </div>
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="storage_save" class="button" type="button" value="确认" />
	                <input id="storage_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
	            </div>
			</div>
		</script>	
		<script id="search_product_temp" type="text/html">
			<div id="search_product_area" style="display: none;" class="admin-scroll">
				<div id="bd">
					<div class="kv-item ue-clear">
						<!-- <div class="kv-item-content">
							<div class="a-row-item a-form-input">
								<input id="productIdSearch" style="width: calc(50% - 10px) !important; display: inline;" name="productIdSearch" type="text" placeholder="输入商品ID" class="input-group form-control a-form-input" />
								<input id="productNameSearch" style="width: calc(50% - 10px) !important; display: inline;" name="productNameSearch" type="text" placeholder="输入商品名" class="input-group form-control a-form-input" />
							</div>
						</div> -->
						<input id="productNameSearch" style="width: 30%; display: inline;" name="productNameSearch" type="text" placeholder="输入商品名" class="input-group form-control a-form-input" />
						<button id="btn_product_search" style="margin-left: 20px; background-color: #00625A; color: #FFFFFF;" type="button" class="btn btn-default">商品查询</button>
						<button id="btn_product_add" style="margin-left: 20px; background-color: #3facde; color: #FFFFFF;" type="button" class="btn btn-default">确认添加</button>
						<button id="btn_product_cancel" style="margin-left: 20px; background-color: #FF0000; color: #FFFFFF; float: right;" type="button" class="btn btn-default">关闭</button>
					</div>
					<div class="subfild-content base-info" style="padding: 0; margin: 0;">
						<div id="storage_item">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h3 id="p_attr_name" style="display: inline-block;" class="panel-title">货架商品</h3>
								</div>
								<div id="panel_body" class="panel-body">
								</div>
							</div>
						</div>
						<div class="col-xs-12">
			                <table id="productgrid"></table>
			            </div>
						<div class="kv-item ue-clear" style="padding: 0; margin: 0;">
							<div id="product_list" class="kv-item-content" style="width: 100%;">
							</div>
						</div>
					</div>
				</div>
			</div>
		</script>
		<script id="product_item_temp" type="text/html">
			<div class="col-xs-2 col-sm-3 col-md-4" style="padding: 0px !important;" id = "panel_attr_<%:=productItem.productId%>">
				<a href="javascript:void(0)" p-attr-id="<%:=productItem%>">
					<label><%=productItem.productName%></label>
					<span 
						onclick="deleteProductValue(event)" 
						p-value-id="<%:=productItem.productId%>" 
						class="glyphicon glyphicon-remove"></span>
				</a>
			</div>
		</script>
		<script id="product_temp" type="text/html">
			<button
				id="check_button_<%=productId%>" 
				product-id="<%=productId%>" 
				class="search-product-check-button btn btn-default pull-right"
				style="
					display: inline; 
					width: 33.3%; 
					text-align: center;
					margin-bottom: 5px;
					float: left;
					"><%=productName%></button>
		</script>
		<script id="storage_update_temp" type="text/html">
			<div id="storage_update" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基本信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>货架名</label>
			                	<div class="kv-item-content">
			                    	<input id="storage_update_name" name="storageName" type="text" placeholder="输入货架名" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_storage_name" class="kv-item-tip"></span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label>货架描述</label>
			                	<div class="kv-item-content">
			                    	<input id="storage_update_des" name="storageDes" type="text" placeholder="输入货架描述" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_storage_des" class="kv-item-tip"></span>
			                </div>
			            </div>
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="storage_updated_save" class="button" type="button" value="确认" />
	                <input id="storage_update_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
	            </div>
			</div>
		</script>	
		<script id="storageProduct_update_temp" type="text/html">
			<div id="storageProduct_update" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基本信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label>货架名:</label>
			                	<div class="kv-item-content">
			                    	<input id="storage_name" name="storageName" style="border-style:none;font-size:18px;" type="text" readonly="readonly" />
			                    	<button id="update_product" style="float: right; background-color: #3facde; color: #FFFFFF;" type="button" class="btn btn-default">选择商品</button>
			                    </div>
			                    <br/><br/>
					            <table id="storageProduct_update_grid"></table>
			                </div>
			            </div>
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="storage_updated_save" class="button" type="button" value="确认" />
	                <input id="storage_update_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
	            </div>
			</div>
		</script>	
	</head>
	<body>
	    <div style="padding: 20px;">
	        <div class="row">
	            <div class="col-xs-8">
	                <form class="form-inline">
	                    <div class="form-group" style="width: auto;">
	                        <input id="search_storagename" name="storageName" type="text" placeholder="请输入货架名" class="form-control" />
	                    </div>
	                    <button id="btn_search" type="button" class="btn btn-default">查询</button>
	                </form>
	            </div>
	            <div class="col-xs-4">
	                <button id="btn_newstorage" type="button" class="btn btn-default pull-right">新增货架</button>
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