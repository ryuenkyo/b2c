<!DOCTYPE html>
<html>
	<head>
		<#include "../common/bootstrap.ftl">
		<#include "../common/resource.ftl">
		<#include "../common/contain_resource.ftl">
		<script type="application/javascript" src="${ctx}/admin/js/as/system/client/point.js"></script>
	    <script id="point_detail_temp" type="text/html">
			<div id="point_detail" style="display: none;" class="admin-scroll">
				<button id="point_detail_button" style="margin-left: 20px; background-color: #FF0000; color: #FFFFFF; float: right;" type="button" class="btn btn-default">关闭</button>
				<br/><br/>
				<table id="point_grid"></table>
			</div>	
		</script>
	</head>
	<body>
	    <div style="padding: 20px;">
	        <div class="row">
	            <div class="col-xs-8">
	                <form class="form-inline">
	                    <div class="form-group" style="width: auto;">
	                        <input id="clientPoints" type="text" placeholder="请输入检索条件:  会员名或昵称" class="form-control" style="width:280px;"/>
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