<!DOCTYPE html>
<html>
	<head>	
		<#include "../common/bootstrap.ftl">
		<#include "../common/resource.ftl">
		<#include "../common/contain_resource.ftl">
		<title>权限列表</title>
		<script id="permission_detail_temp" type="text/html">
		    <div id="permission_detail" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>权限信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>权限名</label>
			                	<div class="kv-item-content">
			                    	<input id="permission_name" name="permissionName" type="text" placeholder="输入权限名" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_permission_name" class="kv-item-tip">请输入汉字</span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label>权限内容</label>
			                	<div class="kv-item-content">
			                    	<input id="permission_context" name="permissionContext" type="text" placeholder="输入权限内容" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_permission_context" class="kv-item-tip">/xxxxx/xxxx.html</span>
			                </div>
							<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>权限类型</label>
			                	<div class="kv-item-content">
			                		<div class="a-row-item a-form-input">
			                			<select id="permission_type" name="permissionType">
				                        </select>
			                		</div>
			                    </div>
			                    <span id="tip_permission_type" class="kv-item-tip success"></span>
			               </div>
						   <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>父级权限</label>
			                	<div class="kv-item-content">
			                		<div class="a-row-item a-form-input">
			                			<select id="permission_parent" name="parentId">
				                        </select>
			                		</div>
			                    </div>
			                    <span id="tip_permission_type" class="kv-item-tip success"></span>
			               </div>
			            </div>
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="role_permission_save" class="button" type="button" value="确认新增" />
	                <input id="role_permission_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
	            </div>
			</div>
		</script>
		<script id="permission_update_temp" type="text/html">
		    <div id="permission_update" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>权限信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>权限名</label>
			                	<div class="kv-item-content">
			                    	<input id="permission_name" name="permissionName" type="text" placeholder="输入权限名" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_permission_name" class="kv-item-tip">请输入汉字</span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label>权限内容</label>
			                	<div class="kv-item-content">
			                    	<input id="permission_context" name="permissionContext" type="text" placeholder="输入权限内容" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_permission_context" class="kv-item-tip">/xxxxx/xxxx.html</span>
			                </div>
							<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>权限类型</label>
			                	<div class="kv-item-content">
			                		<div class="a-row-item a-form-input">
			                			<select id="permission_type_update" name="permissionType">
				                        </select>
			                		</div>
			                    </div>
			                    <span id="tip_permission_type" class="kv-item-tip success"></span>
			               </div>
						   <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>父级权限</label>
			                	<div class="kv-item-content">
			                		<div class="a-row-item a-form-input">
			                			<select id="permission_parent_update" name="parentId">
				                        </select>
			                		</div>
			                    </div>
			                    <span id="tip_permission_type" class="kv-item-tip success"></span>
			               </div>
			            </div>
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="role_permission_save" class="button" type="button" value="确认修改" />
	                <input id="role_permission_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
	            </div>
			</div>
		</script>
		<script type="application/javascript" src="${ctx}/admin/js/as/system/user/permission.js"></script>
	</head>
	<body>
		<div style="padding: 20px;">
	        <div class="row">
	            <div class="col-xs-8">
	                
	            </div>
	            <div class="col-xs-4">
	                <button id="btn_newpermission" type="button" class="btn btn-default pull-right">新增权限</button>
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
