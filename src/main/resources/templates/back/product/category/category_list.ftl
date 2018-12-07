<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<#include "../../common/bootstrap.ftl">
		<#include "../../common/resource.ftl">
		<#include "../../common/contain_resource.ftl">
		<script id="from_prent_attr_name_item" type="text/html">
			<a class="from-parent-attr-name" category-id="<%=categoryId%>" level="<%=currentLevel%>" href="javascript:void(0)">
				<label p-attr-name-id="<%=pAttrNameItem.pAttrNameId%>"><%=pAttrNameItem.pAttrName%></label>
			</a>
		</script>
		<script id="new_attr_name_item" type="text/html">
			<span class="new-attr-name" level="<%=currentLevel%>" href="javascript:void(0)">
				<%=pAttrNameItem.pAttrName%><a id="new_self_attr_name_<%=pAttrNameItem.pAttrNameId%>" p-attr-name-id="<%=pAttrNameItem.pAttrNameId%>" class="glyphicon glyphicon-remove"></a>
			</span>
		</script>
		<script id="attr_select_detail" type="text/html">
			<div id="attr_select" style="display: none;" class="admin-scroll">
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>属性名称</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<%
			            		for (var index in attrList) {
			            			var attrNameItem = attrList[index];
			            	%>
			            	<input class="attr-check" type="checkbox" id="<%=attrNameItem.pAttrNameId%>" />
			            	<label for="<%=attrNameItem.pAttrNameId%>"><%=attrNameItem.pAttrName%></label>
			            	<%
			            		}
			            	%>
			            </div>
			            
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="attr_save" class="button" type="button" value="确认" />
	                <input id="attr_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
	            </div>
			</div>
		</script>
		<script type="application/javascript" src="${ctx}/admin/js/as/system/product/category/category_detail.js"></script>
		<script type="application/javascript" src="${ctx}/admin/js/as/system/product/category/category.js"></script>
		
	</head>
	<body>
		<div style="padding: 20px;">
			<div class="row">
				<div class="col-xs-8">
					
				</div>
				<div class="col-xs-4">
					<button id="btn_newcategory" type="button" class="btn btn-default pull-right">新增分类</button>
				</div>
			</div>
			<div class="row" style="margin-top: 10px">
				<div class="col-xs-12">
					<table id="grid"></table>
				</div>
			</div>
		</div>
		
		<!-- category_detail -->
		<script id="categoryDetailTemp" type="text/html">
		    <div id="category_detail" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>分类信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>分类名称</label>
			                	<div class="kv-item-content">
			                    	<input id="category_name" name="categoryName" type="text" placeholder="输入分类名称" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_category_name" class="kv-item-tip">请输入汉字</span>
			            	</div>
			                <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>状态</label>
			                	<div class="kv-item-content">
			                		<div class="a-row-item">
			                			<input type="checkbox" id="status" /><label for="status">已激活</label>
			                		</div>
			                    </div>
			                    <span id="tip_category_type" class="kv-item-tip success"></span>
			               	</div>
							<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>分类等级</label>
			                	<div class="kv-item-content">
			                    	<input id="category_level" name="categoryLevel" readonly="readonly" type="text" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_category_level" class="kv-item-tip">自动</span>
			            	</div>
			            </div>
			            
						<h2 class="subfild">
			            	<span>父级分类</span>
			            </h2>
			            <div class="subfild-content base-info">
			                <div class="kv-item ue-clear">
			                	<div class="kv-item-content">
			                		<div id="p_category_container" class="a-row-item a-form-input">
			                			<select id="p_category_1"></select>
				                        
			                		</div>
			                	</div>
			               </div>
			            </div>
			            
						<h2 class="subfild">
			            	<span>属性</span>
			            	<div style="float: right; margin-top: -20px; margin-left: 80px; font-size: 15px;">
			            		<a href="javascript:void(0)" id="new_attr_name" class="glyphicon glyphicon-plus">新增属性</a>
			            	</div>
			            </h2>
			            <div class="subfild-content base-info">
			                <div class="kv-item ue-clear">
			                	<div class="kv-item-content">
			                		<div id="p_attr_container" class="a-row-item a-form-input">
			                			<!--<a class="from-parent-attr-name" href="javascript:void(0)">
			                				<label p-attr-name-id="1">属性1</label>
			                			</a>
			                			<span class="new-attr-name" href="javascript:void(0)">
			                				属性2
			                				<a p-attr-name-id="2" class="glyphicon glyphicon-remove"></a>
			                			</span>-->
			                		</div>
			                	</div>
			               </div>
			            </div>
			            
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="category_save" class="button" type="button" value="确认" />
	                <input id="category_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
	            </div>
			</div>
		</script><!-- category_detail -->
		
	</body>
</html>