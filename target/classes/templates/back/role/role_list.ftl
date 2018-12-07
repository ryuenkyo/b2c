<!DOCTYPE html>
<html>
	<head>
		<#include "../common/bootstrap.ftl">
		<#include "../common/resource.ftl">
		<#include "../common/contain_resource.ftl">
		<script type="application/javascript" src="${ctx}/admin/js/as/system/user/role.js"></script>
		<script id="role_detail_temp" type="text/html">
			<div id="role_detail" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
		        <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基本信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>角色名</label>
			                	<div class="kv-item-content">
			                    	<input id="role_name" name="roleName" type="text" placeholder="输入角色名" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_role_name" class="kv-item-tip">请使用10位以内的准确汉字描述角色名</span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label>角色描述</label>
			                	<div class="kv-item-content">
			                    	<input id="role_des" name="roleDes" type="text" placeholder="输入角色描述" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_role_des" class="kv-item-tip">请使用20位以内的准确汉字描述角色名</span>
			                </div>
			            </div>
			            
			            <h2 class="subfild">
			            	<span>权限绑定</span>
			            </h2>
			            
			            <div class="subfild-content base-info">
			            	<div id="tree_permission"></div>
			            </div>
			        </div>
			    </div>
			    <div class="buttons">
	                <input id="role_permission_save" class="button" type="button" value="确认" />
	                <input id="role_permission_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
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
	                        <input id="search_rolename" name="roleName" type="text" placeholder="请输入角色名" class="form-control" />
	                    </div>
	                    <button id="btn_search" type="button" class="btn btn-default">查询</button>
	                </form>
	            </div>
	            <div class="col-xs-4">
	                <button id="btn_newrole" type="button" class="btn btn-default pull-right">新增角色</button>
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