<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<#include "../../common/bootstrap.ftl">
		<#include "../../common/resource.ftl">
		<#include "../../common/contain_resource.ftl">
		<style>
			input[type=text] {
				height: 35px;
				border-radius: 3px;
				padding-left: 5px;
				-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
				box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
				-webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow
					ease-in-out .15s;
				-o-transition: border-color ease-in-out .15s, box-shadow ease-in-out
					.15s;
				transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
				border: 1px solid #ccc;
			}
			
			input[type=text]:focus {
				border-color: #66afe9;
				outline: 0;
				-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px
					rgba(102, 175, 233, .6);
				box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075), 0 0 8px
					rgba(102, 175, 233, .6)
			}
		</style>
		<script id="temp_attr_item" type="text/html">
			<div id="attr_item_<%=pAttrNameItem.pAttrNameId%>" class="col-xs-12 col-sm-6 col-md-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 id="p_attr_name" style="display: inline-block;" class="panel-title"><%:=pAttrNameItem.pAttrName%></h3>
						<a href="javascript:void(0)" onclick="addAttrValue(event)" p-attr-name-id="<%:=pAttrNameItem.pAttrNameId%>"><span class="glyphicon glyphicon-plus"></span></a>
						<a href="javascript:void(0)" onclick="editAttrName(event)" p-attr-name-id="<%:=pAttrNameItem.pAttrNameId%>" style="float: right;"><span class="glyphicon glyphicon-pencil"></span></a>
					    <a href="javascript:void(0)" onclick="deleteAttrName(event)" p-attr-name-id="<%:=pAttrNameItem.pAttrNameId%>" style="float: right;"><span class="glyphicon glyphicon-remove"></span></a>
					</div>
					<div id="panel_body_<%:=pAttrNameItem.pAttrNameId%>" class="panel-body">
					</div>
				</div>
			</div>
		</script>
		<script id="temp_attr_value_item" type="text/html">
			<div class="col-xs-2 col-sm-3 col-md-4" style="padding: 0px !important;" id = "panel_attr_<%:=attrValueItem.pAttrValueId%>">
				<a href="javascript:void(0)" p-attr-id="<%:=attrValueItem.pAttrNameId%>">
					<label><%:=attrValueItem.pAttrValue%></label>
					<span 
						onclick="deleteAttrValue(event)" 
						p-attr-value-id="<%:=attrValueItem.pAttrValueId%>" 
						class="glyphicon glyphicon-remove"></span>
				</a>
			    <a href="javascript:void(0)" p-attr-id="<%:=attrValueItem.pAttrNameId%>">
	
					<span 
						onclick="editAttrValue(event)" 
						p-attr-value-id="<%:=attrValueItem.pAttrValueId%>" 
						class="glyphicon glyphicon-pencil"></span>
				</a>	
			</div>
		</script>
		<script id="attr_detail_temp" type="text/html">
			<div id="attr_detail" style="display: none;">
		        <div data-role="body">
		            <input type="hidden" id="p_attr_name_id" />
		            <div class="form-row">
		                <input style="margin: 5px; width: calc(100% - 10px);" type="text" id="new_p_attr_name" placeholder="请输入属性名">
		            </div>
		            <span id="tip_p_attr_name" class="kv-item-tip">请简短汉字表述属性名，长度不超过5</span><br/>
		        </div>
		        <div data-role="footer" class="buttons">
	                <input id="btn_attr_name_save" class="button" type="button" style="width: 60px;" value="保存" />
	                <input id="btn_attr_name_cancel" class="button" type="button" style="width: 60px; background: #FF3333 !important;" value="取消" />
	           </div>
		    </div>
		</script>
		<script id="attrValue_detail_temp" type="text/html">
			<div id="attrValue_detail" style="display: none;">
		        <div data-role="body">
		            <input type="hidden" id="p_attr_name_id" />
		            <div class="form-row">
		                <input style="margin: 5px; width: calc(100% - 10px);" type="text" id="update_p_attrvalue_name" >
		              
		            </div>  
		            <span id="tip_up_attr_value" class="kv-item-tip"></span><br/>
		        </div>
		        <div data-role="footer" class="buttons">
	                <input id="btn_attr_value_save" class="button" type="button" style="width: 60px;" value="保存" />
	                <input id="btn_attr_value_cancel" class="button" type="button" style="width: 60px; background: #FF3333 !important;" value="取消" />
	           </div>
		    </div>
		</script>
		<script id="attr_value_detail_temp" type="text/html">
			<div id="attr_value_detail" style="display: none;">
		        <div data-role="body">
		        	<div class="form-row">
		            	<input type="text" style="margin: 5px; width: calc(100% - 10px);" readonly="readonly" id="v_p_attr_name_id" name="pAttrNameId" />
		            </div>
		            <div class="form-row">
		            	<input type="text" style="margin: 5px; width: calc(100% - 10px);" readonly="readonly" id="v_p_attr_name" name="pAttrName" />
		            </div>
		            <input type="hidden" id="p_attr_value_id" name="pAttrValueId">
		            <div class="form-row">
		                <input type="text" style="margin: 5px; width: calc(100% - 10px);" id="target" name="pAttrValue" placeholder="请输入属性值">
		            </div>
		            <span id="tip_p_attr_value" class="kv-item-tip"></span><br/>
		        </div>
		        <div data-role="footer" class="buttons">
	                <input id="btn_attr_value_save" class="button" type="button" style="width: 60px;" value="保存" />
	                <input id="btn_attr_value_cancel" class="button" type="button" style="width: 60px; background: #FF3333 !important;" value="取消" />
	           </div>
		    </div>
		</script>
		<script type="application/javascript" src="${ctx}/admin/js/as/system/product/attr/attr.js"></script>
	</head>
	<body>
		<div class="col-xs-12" style="padding: 10px;">
			<button id="btn_new_attr" type="button" class="btn btn-default">新增属性</button>
		</div>
		<div class="container-fluid">
		</div>
	</body>
</html>
