<!DOCTYPE html PUBLIC>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>B2C电商后台管理系统</title>
		<#include "./common/resource.ftl">
		<#include "./common/frame_resource.ftl">
		<script type="text/javascript" src="${ctx}/admin/js/gijgo.js"></script>
		<style>
			.login-header img{
				width: 120px;
				margin-left: auto;
				margin-right: auto;
				display: block;
			}
			.login-item {
				width: calc(100% - 10px);
				height: calc(25% - 15px);
				margin-left: auto;
				margin-right: auto;
				display: inline-block;
			}
			
			.login-item .login-input {
				width: calc(100% - 20px);
	            height: calc(100% - 15px);
				margin: 5px 15px;	
	            background-color: rgba(255, 255, 255, 0.5);
	            font-size: 20px;
			    border-radius: 4px;
				display: inline-block;
			}
			
			.login-item .login-input-half {
				width: calc(50% - 55px);
	            height: calc(100% - 18px);
	            margin: 5px 15px;
	            display: inline-block;
	            vertical-align: top;
			    border-radius: 4px;
			    font-size: 15px;
			}
			
			.login-item input{
				width: 340px;
				height: 32px;
				margin-left: 18px;
				border: 1px solid #dcdcdc;
				border-radius: 4px;
				padding-left: 42px;
			}
			
			.login-item input:hover{
			    border: 1px solid #ff7d0a;
			}
			
			.login-item input:after{
			    border: 1px solid #ff7d0a;
			}
			
			.login-item .icon{
			    width: 26px;
			    height: 26px;
			    margin: 14px 10px 6px 22px;
			    background-color: #ff7d0a;
			    display: inline-block;
			    position: absolute;
			    border-right: 1px solid #dcdcdc;
			}
			
			.login-item .icon.icon-user{
			    background: url("../admin/img/user.png");
			}
			
			.login-item .icon.icon-password{
			    background: url("../admin/img/password.png");
			}
			.login-item button {
				margin-top: 5px;
				width: 100%;
				margin-left: auto;
				margin-right: auto;
				display: inline-block;
				background-color: #ff7d0a;
				color: #ffffff;
				font-size: 16px;
				width: 386px;
				height: 40px;
				margin-left: 18px;
				border: 1px solid #ff7d0a;
				border-radius: 4px;
			}
			
			.login-item button:hover{
				background-color: #ee7204;
			}
	
			.login-item button:active{
				background-color: #ee7204;
			}
		</style>
		<script type="text/javascript">
			$(document).ready(function() {
				<#if currentUser?? >
					$("#login_area").hide();
					getPermission();
				<#else>
					$("#login_area").show();
				</#if>
				// 创建一个menu对象
				var menu = new Menu({
					defaultSelect: $("#first_page")
				});
				//menu.openNewTab($("#hahaha"));
				
				 $(function() {    
			        $('#kaptchaImage').click(    
		                function() {    
		                    $(this).hide().attr('src',    
		                            '../captcha/captchaImage?' + Math.floor(Math.random() * 100)).fadeIn();    
		                });    
			    }); 
			    
			    $("#vali_code").change(function() {
			    	$.ajax({
	        			url : "../admin/kaptchaImageConfirm.json",
	        			type : "get",
	        			async : true,
	        			data : {
	        					"valiCode" : $(this).val()
	        				},
	        			success : function(res) {
	        				if (res[SERVICE.STATUS] === SERVICE.SUCCESS) {
	            				$("#tip_vali_code").html("");
	        					$("#tip_vali_code").css("color", "green");
	        					$("#tip_vali_code").html(MESSAGE.STRING.CORRECT);
	        					login.checked = true;
	            			} else {
	            				$("#tip_vali_code").html("");
	            				$("#tip_vali_code").css("color", "red");
	        					$("#tip_vali_code").html(MESSAGE.STRING.INCORRECT);
	        					login.checked = false;
	        					return;
	            			}
	        			}
	        		});
			    });
			    
				$("#login").click(function() {
					if (!login.checked) {
						MessageUtils.showMessage(MESSAGE.SCREEN.NOT_FINISH, MESSAGE.TYPE.INFO);
			    		return;
					}
					
					var loginObject = {};
					loginObject.userName = $("#user_name").val();
					loginObject.password = $("#password").val();
					loginObject.valiCode = $("#vali_code").val();
					
					$.ajax({
						type:"PUT",
						url:"../admin/login",
						async:true,
						contentType: "application/json",
						data: JSON.stringify(loginObject),
						success: function(res) {
							console.log(res);
							var obj = JSON.parse(res);
							if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
								MessageUtils.showMessage(obj[SERVICE.MESSAGE_BODY], MESSAGE.TYPE.INFO, function() {
									getPermission();
									$("#login_area").hide();
								});
								
							} else if (obj[SERVICE.STATUS] === SERVICE.FAIL) {
								
							}
						},
						error: function(err) {
							
						}
					});
				});
			});
			
			function getPermission() {
				$.ajax({
					type: "GET",
					url: "../system/permission/menu.json",
					async: true,
					success: function(res) {
						for (var index in res.permissionList) {
							var item = res.permissionList[index];
							var navLi = $("<li class=\"nav-li\"></li>");
							var a = $("<a href=\"javascript:void(0);\" class=\"ue-clear\"><i class=\"nav-ivon\"></i><span class=\"nav-text\">"+item.permissionName+"</span></a>");
							navLi.append(a);
							var ul = $("<ul class=\"subnav\"></ul>");
							for (var jndex in item.children) {
								var subItem = item.children[jndex];
								var subLi = $("<li class=\"subnav-li\" href=\"./"+subItem.permissionContext+".html\" data-id=\""+subItem.permissionId+"\"></li>");
								var subA = $("<a href=\"javascript:void(0);\" class=\"ue-clear\"><i class=\"subnav-icon\"></i><span class=\"subnav-text\">"+subItem.permissionName+"</span></a>");
								subLi.append(subA);
								subLi.click(function() {
									menu.openNewTab($(this));
								});
								ul.append(subLi);
							}
							navLi.append(ul);
							$("#menuList").append(navLi);

						}
					},
					error: function(err) {
						
					}
				});
			}
			
			function openLoginScreen() {
				$("#login_area").show();
			}
			
			function logout() {
				MessageUtils.showConfirmMessage(
					MESSAGE.USER.USER_LOGOUT, 
					MESSAGE.TYPE.INFO, 
					function() {
						$.ajax({
							type: "GET",
							url: "../admin/logout",
							headers : {  
				                'Content-Type' : 'application/json;charset=utf-8'  
				            },  
							success: function(res) {
								window.parent.location.reload();
							},
							error: function(err) {
							}
						});
					},
					function() {
						console.log("取消");
					}
				);
			}
			
		</script>
	</head>

	<body>
		<!-- 后台系统欢迎页 -->
		<li id="first_page" style="display: none;" href="../system/index" data-id="1">
    		<a href="javascript:;" class="ue-clear">
    			<i class="subnav-icon"></i><span class="subnav-text">首页</span>
    		</a>
    	</li>
		<div id="container">
			<div id="hd">
		    	<div class="hd-top">
		            <h1 class="logo">老蔡商城</h1>
		            <div class="user-info">
		                <a href="javascript:;" class="user-avatar">
		                	<span><!--<i class="info-num">0</i>--></span>
		                </a>
		                <#if currentUser?? >
		                <span class="user-name">${currentUser.userName}</span>
		                <a href="javascript:;" class="more-info"></a>
		                </#if>
		            </div>
		            <div class="setting ue-clear">
		                <ul class="setting-main ue-clear">
		                    <li><a href="javascript:;">帮助</a></li>
		                    <li><a href="javascript:;" class="close-btn exit" onclick="logout()" style="font-size: large;"></a></li>
		                </ul>
		            </div>
		       </div>
		    </div>
			<div style="height: calc(95% - 70px) !important;">
		    	<div class="sidebar">
		        	<div class="sidebar-bg"></div>
		            <h2><a href="javascript:;"><i class="h2-icon" title="切换到树型结构"></i><span>菜单</span></a></h2>
		            <ul id="menuList" class="nav">
		                <!--<li class="nav-li">
		                	<a href="javascript:;" class="ue-clear"><i class="nav-ivon"></i><span class="nav-text">新闻管理</span></a>
		                    <ul class="subnav">
		                    	<li class="subnav-li" href="./user/user_list.html" data-id="8">
		                    		<a href="javascript:;" class="ue-clear">
		                    			<i class="subnav-icon"></i><span class="subnav-text">新闻视频管理</span>
		                    		</a>
		                    	</li>
		                    </ul>
		                </li>
		                <li class="nav-li">
		                	<a href="javascript:;" class="ue-clear"><i class="nav-ivon"></i><span class="nav-text">用户信息设置</span></a>
		                	<ul class="subnav">
		                    	
		                    </ul>
		                </li>
		                <li class="nav-li ">
		                	<a href="javascript:;" class="ue-clear"><i class="nav-ivon"></i><span class="nav-text">数据管理</span></a>
		                    <ul class="subnav">
		                    	<li class="subnav-li" data-id="12">
		                    		<a href="javascript:;" class="ue-clear">
		                    			<i class="subnav-icon"></i><span class="subnav-text">工作安排查询2</span>
		                    		</a>
		                    	</li>
		                    </ul>
		                </li>-->
		            </ul>
		            <div class="tree-list outwindow">
		            	<div class="tree ztree"></div>
		            </div>
		        </div>
		        <div class="main">
		        	<div class="title">
		                <i class="sidebar-show"></i>
		                <ul class="tab ue-clear"></ul>
		                <i class="tab-more"></i>
		                <i class="tab-close"></i>
		            </div>
		            <div class="content">
		            </div>
		        </div>
		    </div>
		</div>
		<div id="login_area" style="
			display: none;
			position: fixed;
			width: 100%;
			height: 100%;
			top: 0;
			left: 0;
			background-image: url(../admin/img/bc.jpeg);
			background-attachment: fixed;
			background-repeat: no-repeat;
			background-size: cover;
			z-index: 9;
			">
			<div style="
				background-color: rgba(255, 255, 255, 0.5);
			    width: 420px;
			    height: 300px;
			    border: 1px solid #000000;
			    border-radius: 6px;
			    padding: 10px;
			    margin-top: 15%;
			    margin-left: auto;
			    margin-right: auto;
			    display: block;
				">
				<div class="login-header" style="
						width: 100%;
						height: 45px;
						margin-bottom: 20px;
						border-bottom: 1px solid #dcdcdc;
						text-align: center;">
			        <h1 style="font-size: 30px; ">商城后台管理系统</h1><!-- <img src=""> -->
			    </div>
				<div class="login-item">
            		<span class="icon icon-user"></span>
	                <input class="login-input" type="text" id="user_name" placeholder="请输入用户名" required="required" />
	            </div>
	            <div class="login-item">
            		<span class="icon icon-password"></span>
	                <input class="login-input" type="password" id="password" placeholder="请输入密码" required="required" />
	            </div>
	            <div class="login-item">
	            	<span class="icon icon-password"></span>
	                <input class="login-input-half v-code" style="float:left;" type="text" id="vali_code" placeholder="请输入验证码" required="required" />
	                <span id="tip_vali_code" style="float:left; font-size:32px; width:4%; vertical-align: middle;"></span>
	                <img src="../captcha/captchaImage" id="kaptchaImage" style="float:right; margin-right: 25px; margin-top: 3px; display: block; width:40%; height: 42px; vertical-align: middle;"/>
	            </div>
	            <div class="login-item">
	            	<button id="login">登录</button>
	            </div>
			</div>
		</div>
	</body>
</html>