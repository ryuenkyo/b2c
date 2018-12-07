<#assign ctx=request.getContextPath()>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/admin/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/admin/css/skin_/form.css" />
<script type="application/javascript" src="${ctx}/admin/js/jquery.js"></script>
<script type="application/javascript" src="${ctx}/admin/js/template.js"></script>
<script id="message_area" type="text/html">
	<div id="message_container" title="<%=title%>" width="350" style="
        background-color: #FFFFFF;
		border: 1px solid black;
		border-radius: 4px;
		z-index: 9000
		
		">
        <div data-role="body"
        	style="
        		padding: 10px;
        		font-size: large;
        		font-family: '微软雅黑';
        		text-align: left;
        		width: calc(100% - 20px);
        		height: auto;
        		background-color: #FFFFFF;
        	"><%=message%></div>
        <div data-role="footer"
        	style="
        		margin-top: 0px;
        		background-color: #FFFFFF;
        		text-align: right;
        	"
        	>
        	<%
        		if (confirmFlg) {
        	%>
            <button id="m_c_confrim" class="button" style=" 
            	display: inline-block; 
            	position: relative; 
            	font-size: medium; 
            	margin: 5px; 
            	width: 60px;
            	">确定</button>
            <%
        		}
        		if (cancelFlg) {
        	%>
             <button id="m_c_cancel" class="button" style="
             	background: #FF3333 !important; 
             	display: inline-block; 
             	position: relative; 
             	font-size: medium; 
             	margin: 5px; 
             	width: 60px;
             	">取消</button>
            <%
        		}
        	%>
        </div>
	</div>
</script>
<script type="text/javascript" src="${ctx}/admin/js/as/common.js"></script>