<!DOCTYPE html>
<html>
	<head>
		<#include "../common/bootstrap.ftl">
		<#include "../common/resource.ftl">
		<#include "../common/contain_resource.ftl">
		<script type="application/javascript" src="${ctx}/admin/js/as/system/client/client.js"></script>
	</head>
	<body>
	    <div style="padding: 20px;">
	        <div class="row">
	            <div class="col-xs-8">
	                <form class="form-inline">
	                    <div class="form-group" style="width: auto;">
	                        <input id="clientinfo" type="text" placeholder="请输入检索条件:  会员名或昵称" class="form-control" style="width:280px;"/>
	                    </div>
	                    <button id="btn_search" type="button" class="btn btn-default">查询</button>
	                </form>
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