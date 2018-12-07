<!DOCTYPE html PUBLIC>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>B2C电商后台管理系统</title>
		<#include "./common/resource.ftl">
		<#include "./common/frame_resource.ftl">
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<div id="login_area">
		    <div id="bd">
		    	<div id="main">
		        	<div class="login-box">
		                <div id="logo"></div>
		                <h1></h1>
		                <div class="input username" id="username">
		                    <label for="userName">用户名</label>
		                    <span></span>
		                    <input type="text" id="userName" />
		                </div>
		                <div class="input psw" id="psw">
		                    <label for="password">密&nbsp;&nbsp;&nbsp;&nbsp;码</label>
		                    <span></span>
		                    <input type="password" id="password" />
		                </div>
		                <div class="input validate" id="validate">
		                    <label for="valiDate">验证码</label>
		                    <input type="text" id="valiDate" />
		                    <div class="value">X3D5</div>
		                </div>
		                <div class="styleArea">
		                    <div class="styleWrap">
		                        <select name="style">
		                            <option value="默认风格">默认风格</option>
		                            <option value="红色风格">红色风格</option>
		                            <option value="绿色风格">绿色风格</option>
		                        </select>
		                    </div>
		                </div>
		                <div id="btn" class="loginButton">
		                    <input type="button" class="button" value="登录"  />
		                </div>
		            </div>
				</div>
			</div>
		</div>
	</body>
</html>