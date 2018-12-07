<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<#include "../../common/bootstrap.ftl">
		<#include "../../common/resource.ftl">
		<#include "../../common/contain_resource.ftl">

		<title></title>
		<script id="attr_temp" type="text/html">
			<div class="kv-item ue-clear">
				<label><%=pAttrName%></label>
				<div class="kv-item-content">
					<div class="a-row-item a-form-input">
						<select id="<%=pAttrNameId%>_value_select" p-attr-name-id="<%=pAttrNameId%>"></select>
					</div>
				</div>
			</div>
		</script>
		
			<div id="search_product_area" style="display: none;" class="admin-scroll">
				<div id="bd">
					<div class="kv-item ue-clear">
						<div class="kv-item-content">
							<div class="a-row-item a-form-input">
								
								<input id="aaa" style="width: calc(50% - 10px) !important; display: inline;" name="aaa" type="text" placeholder="输入商品名" class="input-group form-control a-form-input" />
							</div>
						</div>
						<button id="btn_product_search" style="margin-left: 20px; background-color: #00625A; color: #FFFFFF;" type="button" class="btn btn-default">商品查询</button>
						<button id="btn_product_cancel" style="margin-left: 20px; background-color: #FF0000; color: #FFFFFF; float: right;" type="button" class="btn btn-default">关闭</button>
					</div>
					<div class="subfild-content base-info" style="padding: 0; margin: 0;">
						<div class="kv-item ue-clear" style="padding: 0; margin: 0;">
							<div id="product_list" class="kv-item-content" style="width: 100%;">
							</div>
						</div>
					</div>
				</div>
			</div>
		
		<script id="product_temp" type="text/html">
			<button 
				id="check_button_<%=productId%>" 
				product-id="<%=productId%>" 
				class="search-product-check-button btn btn-default pull-right"
				style="
					display: inline; 
					width: 100%; 
					text-align: center;
					margin-bottom: 5px;
					float: none;
					"><%=productName%></button>
		</script>
		
		
		
		
		<script src="${ctx}/admin/js/as/system/product/sku/sku_detail.js" type="text/javascript"></script>
		<script src="${ctx}/admin/js/as/system/product/sku/sku.js" type="text/javascript"></script>
		
		
	</head>
	<body>
		<div style="padding: 20px;">
			<div class="row">
				<div class="col-xs-8">
					<form class="form-inline">
						<div class="form-group" style="width: auto;">
							<input id="search_productname" type="text" placeholder="请输入商品名称" class="form-control" />
						</div>
						<button id="btn_search" type="button" class="btn btn-default">查询</button>
					</form>
				</div>
				<div class="col-xs-4">
					<button id="btn_newsku" type="button" class="btn btn-default pull-right">新增库存</button>
				</div>
			</div>
			<div class="row" style="margin-top: 10px">
				<div class="col-xs-12">
					<table id="grid"></table>
				</div>
			</div>
		</div>
		
		<script id="sku_detail_temp" type="text/html">
			<!-- sku_detail -->
			<div id="sku_detail" style="display: none;" class="admin-scroll">
				<input type="hidden" id="ID" />
				<div id="bd">
					<div id="main">
						<h2 class="subfild">
							<span>基础信息</span>
						</h2>
						<div class="subfild-content base-info">
							<div class="kv-item ue-clear">
								<label><span class="impInfo">*</span>价格</label>
								<div class="kv-item-content">
									<input id="price" name="price" type="text" placeholder="输入价格" class="input-group form-control a-form-input" />
								</div>
								<span id="tip_price" class="kv-item-tip">请输入数字</span>
							</div>
							<div class="kv-item ue-clear">
								<label><span class="impInfo">*</span>库存</label>
								<div class="kv-item-content">
									<input id="stock" name="stock" type="text" placeholder="输入库存" class="input-group form-control a-form-input" />
								</div>
								<span id="tip_stock" class="kv-item-tip">请输入数字</span>
							</div>
						</div>
						<h2 class="subfild">
							<span class="impInfo">商品信息</span>
							<div style="float: right; margin-top: -20px; margin-left: 80px; font-size: 15px;">
			            		<a href="javascript:void(0)" id="new_product" img-count="0" class="glyphicon glyphicon-plus">选择商品</a>
			            	</div>
						</h2>
						<div class="subfild-content base-info">
							<div class="kv-item ue-clear">
								<label>当前商品</label>
								<div class="kv-item-content">
									<div class="a-row-item a-form-input">
										
										<input id="productName" name="productName" type="text" readonly="readonly" class="input-group form-control a-form-input" />
										
									</div>
								</div>
							</div>
						</div>
						<h2 class="subfild">
							<span class="impInfo">*商品属性</span>
						</h2>
						<div id="attr_container" class="subfild-content base-info">
							
						</div>

					</div>
				</div>
				<div class="buttons">
					<input id="sku_save" class="button" type="button" value="保存" />
					<input id="sku_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
				</div>
			</div><!-- sku_detail -->
		</script>
		<script id="sku_update_temp" type="text/html">
			<!-- sku_detail -->
			<div id="sku_update" style="display: none;" class="admin-scroll">
				<input type="hidden" id="ID" />
				<div id="bd">
					<div id="main">
						<h2 class="subfild">
							<span class="impInfo">商品信息</span>
							
						</h2>
						<div class="subfild-content base-info">
							<div class="kv-item ue-clear">
								<label>当前商品</label>
								<div class="kv-item-content">
									<div class="a-row-item a-form-input">
										<input id="productId" name="productId" type="hidden" class="input-group form-control a-form-input" />
										<input id="productName" name="productName" type="text" readonly="readonly" class="input-group form-control a-form-input" />
									</div>
								</div>
								
								<div class="kv-item-content">
								<label>商品属性</label>
									<div class="a-row-item a-form-input">
										<input id="productId" name="productId" type="hidden" class="input-group form-control a-form-input" />
										<input id="skuCollectionShow" name="skuCollectionShow" type="text" readonly="readonly" class="input-group form-control a-form-input" />
									</div>
								</div>
							</div>
						</div>
						<h2 class="subfild">
							<span>基础信息</span>
						</h2>
						<div class="subfild-content base-info">
							<div class="kv-item ue-clear">
								<label><span class="impInfo">*</span>价格</label>
								<div class="kv-item-content">
									<input id="price" name="price" type="text" placeholder="输入价格" class="input-group form-control a-form-input" />
								</div>
								<span id="tip_price" class="kv-item-tip">请输入数字</span>
							</div>
							<div class="kv-item ue-clear">
								<label><span class="impInfo">*</span>库存</label>
								<div class="kv-item-content">
									<input id="stock" name="stock" type="text" placeholder="输入库存" class="input-group form-control a-form-input" />
								</div>
								<span id="tip_stock" class="kv-item-tip">请输入数字</span>
							</div>
						</div>
						
						

					</div>
				</div>
				<div class="buttons">
					<input id="sku_updated_save" class="button" type="button" value="保存" />
					<input id="sku_update_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
				</div>
			</div><!-- sku_detail -->
		</script>
	</body>
</html>