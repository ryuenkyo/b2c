<!DOCTYPE html>
<html>
	<head>
		<#include "../common/bootstrap.ftl">
		<#include "../common/resource.ftl">
		<#include "../common/contain_resource.ftl">
		<script id="level_detail_temp" type="text/html">
			<div id="level_detail" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
			    <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基本信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>等级名称</label>
			                	<div class="kv-item-content">
			                    	<input id="level_name" name="levelName" type="text" placeholder="输入等级名" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_level_name" class="kv-item-tip">请以准确的汉字描述等级名</span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>等级点数</label>
			                	<div class="kv-item-content">
			                    	<input id="level_point" name="nickName" type="text" placeholder="输入等级点数" class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_level_point" class="kv-item-tip"></span>
			                </div>
			            </div>
			            <div class="buttons">
			                <input id="level_save" class="button" type="button" value="确认修改" />
			                <input id="level_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
			            </div>
			        </div>
		   	 	</div>
			</div>	
		</script>
		
		
      <script id="level_updetail_temp" type="text/html">
			<div id="level_updetail" style="display: none;" class="admin-scroll">
		        <input type="hidden" id="ID" />
			    <div id="bd">
			    	<div id="main">
			            <h2 class="subfild">
			            	<span>基本信息</span>
			            </h2>
			            <div class="subfild-content base-info">
			            	<div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>等级名称</label>
			                	<div class="kv-item-content">
			                    	<input id="uplevel_name" name="uplevelName" type="text"  class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_uplevel_name" class="kv-item-tip"></span>
			                </div>
			                <div class="kv-item ue-clear">
			                	<label><span class="impInfo">*</span>等级点数</label>
			                	<div class="kv-item-content">
			                    	<input id="uplevel_point" name="nickName" type="text"  class="input-group form-control a-form-input" />
			                    </div>
			                    <span id="tip_level_point" class="kv-item-tip"></span>
			                </div>
			            </div>
			            <div class="buttons">
			                <input id="uplevel_save" class="button" type="button" value="确认修改" />
			                <input id="uplevel_cancel" class="button" type="button" style="background: #FF3333 !important;" value="取消" />
			            </div>
			        </div>
		   	 	</div>
			</div>	
		</script>
		
		
		
		<script type="application/javascript" src="${ctx}/admin/js/as/system/client/level.js"></script>
	</head>
	<body>
	    <div style="padding: 20px;">
	        <div class="row">
	            <div class="col-xs-8">
	            </div>
	            <div class="col-xs-4">
	                <button id="btn_newlevel" type="button" class="btn btn-default pull-right">新增等级</button>
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