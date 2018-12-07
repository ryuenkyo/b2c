<!DOCTYPE html>
<html>
	<head>
		<#include "../common/bootstrap.ftl">
		<#include "../common/resource.ftl">
		<#include "../common/contain_resource.ftl">
		<script id="user_detail_temp" type="text/html">
			<div id="user_detail" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
			    <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基本信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>账户</label>
			                	<div class="kv-item-content">
			                    	<input id="user_name" name="userName" type="text" placeholder="输入用户名" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_user_name" class="kv-item-tip">请以字母数字组合</span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label>用户昵称</label>
			                	<div class="kv-item-content">
			                    	<input id="nick_name" name="nickName" type="text" placeholder="输入昵称" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_nick_name" class="kv-item-tip"></span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>用户密码</label>
			                	<div class="kv-item-content">
			                    	<input id="password" name="password" type="password" placeholder="输入密码" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_password" class="kv-item-tip">请使用字母数字特殊字符组合</span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>确认密码</label>
			                	<div class="kv-item-content">
			                    	<input id="con_password" type="password" placeholder="再次输入密码" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_con_password" class="kv-item-tip">请输入与密码一直的确认密码</span>
			                </div>
			            </div>
			            
			            <h2 class="subfild">
			            	<span>角色绑定</span>
			            </h2>
			            
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>角色</label>
			                	<div class="kv-item-content">
			                		<div class="a-row-item a-form-input">
			                			<select id="confirmroleid" name="roleId">
				                        </select>
			                		</div>
			                    </div>
			                    <span id="tip_role_id" class="kv-item-tip success"></span>
			               </div>
			            </div>
			            
			            <div class="buttons">
			                <input id="user_save" class="button" type="button" value="确认修改" />
			                <input id="user_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
			            </div>
			        </div>
		   	 	</div>
			</div>	
		</script>
		<script id="user_update_temp" type="text/html">
			<div id="user_update" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
			    <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基本信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo"></span>账户</label>
			                	<div class="kv-item-content">
			                    	<input id="user_name" name="userName" type="text" disabled="disabled" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_user_name" class="kv-item-tip">请以字母数字组合</span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label>用户昵称</label>
			                	<div class="kv-item-content">
			                    	<input id="nick_name" name="nickName" type="text" placeholder="输入昵称" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_nick_name" class="kv-item-tip"></span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>原密码</label>
			                	<div class="kv-item-content">
			                    	<input id="user_password" name="user_password" type="password" placeholder="输入原密码" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_user_password" class="kv-item-tip">请输入原密码</span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>用户密码</label>
			                	<div class="kv-item-content">
			                    	<input id="password" name="password" type="password" placeholder="输入密码" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_password" class="kv-item-tip">请使用字母数字特殊字符组合</span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>确认密码</label>
			                	<div class="kv-item-content">
			                    	<input id="con_password" type="password" placeholder="再次输入密码" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_con_password" class="kv-item-tip">请输入与密码一直的确认密码</span>
			                </div>
			            </div>
			            
			            <div class="buttons">
			                <input id="user_update_save" class="button" type="button" value="确认修改" />
			                <input id="user_update_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
			            </div>
			        </div>
		   	 	</div>
			</div>	
		</script>
		<script id="user_role_temp" type="text/html">
			<div id="user_role" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
			    <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>角色绑定</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>角色</label>
			                	<div class="kv-item-content">
			                		<div class="a-row-item a-form-input">
			                			<select id="user_role_dropdown" name="roleId">
				                        </select>
			                		</div>
			                    </div>
			                    <span id="tip_role_id" class="kv-item-tip success"></span>
			               </div>
			            </div>
			            <div class="buttons">
			                <input id="user_role_save" class="button" type="button" value="确认修改" />
			                <input id="user_role_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
			            </div>
			        </div>
		   	 	</div>
			</div>	
		</script>
		<script type="application/javascript" src="${ctx}/admin/js/as/system/user/user.js"></script>
	</head>
	<body>
	    <div style="padding: 20px;">
	        <div class="row">
	            <div class="col-xs-8">
	                <form class="form-inline">
	                    <div class="form-group" style="width: auto;">
	                        <input id="search_username" type="text" placeholder="请输入用户名" class="form-control" />
	                        <input id="search_nickname" type="text" placeholder="请输入昵称" class="form-control" />
	                        <div style="position: relative; display: inline-block; vertical-align: top;">
	                        	<select id="search_roleid" width="200">
							    </select>
	                        </div>
	                    </div>
	                    <button id="btn_search" type="button" class="btn btn-default">查询</button>
	                </form>
	            </div>
	            <div class="col-xs-4">
	                <button id="btn_newuser" type="button" class="btn btn-default pull-right">新增用户</button>
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