<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<#include "../common/bootstrap.ftl">
		<#include "../common/resource.ftl">
		<#include "../common/contain_resource.ftl">
		<link rel="stylesheet" type="text/css" href="${ctx}/admin/css/gijgo.css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/admin/css/admin.css" />

	    <script src="${ctx}/admin/ckeditor/ckeditor.js"></script>
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
	    
	    
		<title></title>
		<script id="category_temp" type="text/html">
			<div class="kv-item ue-clear">
            	<label><%=categoryLevel%>级分类</label>
            	<div class="kv-item-content">
            		<div class="a-row-item a-form-input">
            			<select id="category_id_<%=categoryLevel%>" product-category-level="<%=categoryLevel%>">
                        </select>
            		</div>
               	</div>
           </div>
		</script>
		<script id="attr_temp" type="text/html">
			<div id="<%=pAttrNameId%>" 
            	class="p-attr-name" 
            	category-level="<%=categoryLevel%>" 
            	category-id="<%=categoryId%>"
            	style="border-top: #000080 solid 1px; border-bottom: #000080 solid 1px;">
            	<div>
            		<span>属性名称：<b><%=pAttrName%></b></span>
            		<span style="margin: 0px 30px;">所属分类：<b><%=categoryName%></b></span>
            		<a style="float: right;" p-attr-name-id="<%=pAttrNameId%>" href="javascript:void(0)">
                		<input id="<%=pAttrNameId%>_issku" style="vertical-align: top;" class="is-sku" type="checkbox"  />
                		<label for="<%=pAttrNameId%>_issku" style="font-weight: 100 !important;">SKU属性</label>
            		</a>
            	</div>
            	<div id="attr_value_container_<%=pAttrNameId%>">
            		
            	</div>
            </div>
		</script>
		<script id="attr_value_temp" type="text/html">
			<span p-attr-name-id="<%=pAttrNameId%>" style="margin-right: 10px;">
    			<input style="vertical-align: top;" 
    				id="<%=pAttrValueId%>" class="p-attr-value" type="checkbox"  />
    			<label for="<%=pAttrValueId%>" style="font-weight: 100 !important;"><%=pAttrValue%></label>
    		</span>
		</script>
		<script id="detail_img_temp" type="text/html">
			<div class="img-item">
            	<input id="detail_<%=imageId%>" type="file" style="display: none;" accept="image/*" />
            	<img id="detail_show_<%=imageId%>" src=""/>
            	<label for="detail_<%=imageId%>" >点击选择图片</label>
            	<span id="delete_img_<%=imageId%>" class="glyphicon glyphicon-remove" 
            		style="width: 20px; height: 20px; position: absolute; display: block; top: 3px; right: 0;"></span>
            </div>
		</script>
		<script src="${ctx}/admin/js/as/system/product/product_detail.js" type="text/javascript"></script>
		<script src="${ctx}/admin/js/as/system/product/product.js" type="text/javascript"></script>
		
		
	</head>
	<body>
		<div style="padding: 20px;">
	        <div class="row">
	            <div class="col-xs-8">
	                <form class="form-inline">
	                    <div class="form-group" style="width: auto;">
	                        <input id="search_productname" type="text" placeholder="请输入商品名称" class="form-control" />
	                        <div style="position: relative; display: inline-block; vertical-align: top;">
	                        	<select id="search_status" width="200">
							    </select>
	                        </div>
	                        <div style="position: relative; display: inline-block; vertical-align: top;">
	                        	<select id="search_brand" width="200">
							    </select>
	                        </div>
	                    </div>
	                    <button id="btn_search" type="button" class="btn btn-default">查询</button>
	                </form>
	            </div>
	            <div class="col-xs-4">
	                <button id="btn_newproduct" type="button" class="btn btn-default pull-right">新增商品</button>
	            </div>
	        </div>
	        <div class="row" style="margin-top: 10px">
	            <div class="col-xs-12">
	                <table id="grid"></table>
	            </div>
	        </div>
	    </div>
		
		<script id="product_detail_temp" type="text/html">
			<!-- product_detail -->
		    <div id="product_detail" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基础信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>商品名称</label>
			                	<div class="kv-item-content">
			                    	<input id="product_name" name="productName" type="text" placeholder="输入商品名" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_product_name" class="kv-item-tip">请输入汉字</span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>显示价格</label>
			                	<div class="kv-item-content">
			                    	<input id="show_price" name="showPrice" type="text" placeholder="输入显示价格" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_show_price" class="kv-item-tip">请输入数字</span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>品牌</label>
			                	<div class="kv-item-content">
			                		<div class="a-row-item a-form-input">
			                			<select id="brand_id" name="brandId">
				                        </select>
			                		</div>
			                    </div>
			                    <span id="tip_permission_type" class="kv-item-tip success"></span>
			            	</div>
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>是否上架</label>
			                	<div class="kv-item-content">
			                		<div class="a-row-item">
			                			<input type="checkbox" id="status" /><label for="status">上架</label>
			                		</div>
			                    </div>
			                    <span id="tip_product_status" class="kv-item-tip success"></span>
			               	</div>
	
			            </div>
						<h2 class="subfild">
			            	<span class="impInfo">*商品分类</span>
			            </h2>
			            <div id="category_container" class="subfild-content base-info">
			                
	
			            </div>
			            <h2 class="subfild">
			            	<span class="impInfo">*商品属性</span>
			            </h2>
			            <div id="attr_container" class="subfild-content base-info">
			                
	
			            </div>
			            
			            <h2 class="subfild">
			            	<span class="impInfo">*商品封面</span>
			            </h2>
			            <div id="img_cover_container" class="subfild-content base-info">
			                <div class="img-item" style="width: 200px; height: 200px;">
			                	<input 
			                		id="file_cover" 
			                		type="file" 
			                		accept="image/*" 
			                		style="display: none;" />
			                	<img id="cover_show_img" src=""/>
			                	<label for="file_cover" >点击选择封面</label>
			                </div>
			            </div>
			            
			            
			            <h2 class="subfild">
			            	<span class="impInfo">*详细图片</span>
			            	<div style="float: right; margin-top: -20px; margin-left: 80px; font-size: 15px;">
			            		<a href="javascript:void(0)" id="new_product_image" img-count="0" class="glyphicon glyphicon-plus">新增详细图片</a>
			            	</div>
			            </h2>
			            <div id="img_container" class="subfild-content base-info">
			                
			            </div>
			            
			            <h2 class="subfild">
			            	<span class="impInfo">介绍</span>
			            </h2>
			            <div class="subfild-content base-info">
			                <textarea name="editor" id="editor" rows="10" cols="80">
			                </textarea>
			            </div>
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="product_save" class="button" type="button" value="保存" />
	                <input id="product_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
	            </div>
			</div><!-- product_detail -->
		</script>
	</body>
</html>
