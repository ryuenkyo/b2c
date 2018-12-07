<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">

		<title>修改密码</title>

		<link href="/b2c/front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="/b2c/front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">

		<link href="/b2c/front/css/personal.css" rel="stylesheet" type="text/css">
		<link href="/b2c/front/css/stepstyle.css" rel="stylesheet" type="text/css">

		<script src="/b2c/front/js/jquery-1.7.2.min.js" type="text/javascript"></script>
		<script src="/b2c/front/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>

		<script type="text/javascript">
		
			$(document).ready(function(){			  
				$("#user-old-password").blur(function(){
				var password = $("#user-old-password").val();
				$.ajax({
					type: "GET",
					url: "../front/uppassword.html",					
					data: "password="+password,
					async: true,
					success: function(res) {
					var obj = JSON.parse(res);
						if(obj == false){						
							alert("原密码填写错误");
		     				return false;
						}
					},
					});	
			    
			    
			  });
	  
			});
			
			
			function password(){
			
				
				var	password = $("#user-new-password").val();
				var	confirmpassword = $("#user-confirm-password").val();
				var passwordsize = $("#user-new-password").val().length;
				if(passwordsize<6 || passwordsize>12){
					alert("请输入6到12位密码");
		     				return false;
				}
				if(password != confirmpassword){
				 	alert("两次密码不一致");
		     				return false;
				}
				
				 
				$.ajax({
					
					type: "GET",
					url: "../front/newpassword.html",					
					data: "password="+password,
					async: true,
					success: function(res) {
					$("#updateBegin").removeAttr("class");
					$("#updateFinish").removeAttr("class");
					$("#updateBegin").attr("class","step-2 step");
					$("#updateFinish").attr("class","step-1 step");					
						alert("密码修改成功");
					
						location.href="../front/admin.html";
					},
				});		
			};
			
			
			
			
</script>

	</head>

	<body>
		<!--头 -->
		<header>
			<article>
				<div class="mt-logo">
					<!--顶部导航条 -->
					<div class="am-container header">
						<ul class="message-r">
							<div class="topMessage home">
								<div class="menu-hd"><a href="#" target="_top" class="h">商城首页</a></div>
							</div>
							<div class="topMessage my-shangcheng">
								<div class="menu-hd MyShangcheng"><a href="#" target="_top"><i class="am-icon-user am-icon-fw"></i>个人中心</a></div>
							</div>
							<div class="topMessage mini-cart">
								<div class="menu-hd"><a id="mc-menu-hd" href="#" target="_top"><i class="am-icon-shopping-cart  am-icon-fw"></i><span>购物车</span><strong id="J_MiniCartNum" class="h">0</strong></a></div>
							</div>
						</ul>
					</div>

					<!--悬浮搜索框-->
					<div class="nav white">
						<div class="logoBig">
							<li><img src="/b2c/front/images/logobig.png" /></li>
						</div>

						<div class="search-bar pr">
							<a name="index_none_header_sysc" href="#"></a>
							<form>
								<input id="searchInput" name="index_none_header_sysc" type="text" placeholder="搜索" autocomplete="off">
								<input id="ai-topsearch" class="submit am-btn" value="搜索" index="1" type="submit">
							</form>
						</div>
					</div>

					<div class="clear"></div>
				</div>
			</article>
		</header>
		<b class="line"></b>
		<div class="center">
			<div class="col-main">
				<div class="main-wrap">

					<div class="am-cf am-padding">
						<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">修改密码</strong></div>
					</div>
					<hr/>
					<!--进度条-->
					<div class="m-progress">
						<div class="m-progress-list">
							<span class="step-1 step" id="updateBegin">
                                <em class="u-progress-stage-bg"></em>
                                <i class="u-stage-icon-inner">1<em class="bg"></em></i>
                                <p class="stage-name">重置密码</p>
                            </span>
							<span class="step-2 step" id="updateFinish">
                                <em class="u-progress-stage-bg"></em>
                                <i class="u-stage-icon-inner">2<em class="bg"></em></i>
                                <p class="stage-name">完成</p>
                            </span>
							<span class="u-progress-placeholder"></span>
						</div>
						<div class="u-progress-bar total-steps-2">
							<div class="u-progress-bar-inner"></div>
						</div>
					</div>
					
						<div class="am-form-group">					
							<label for="user-old-password" class="am-form-label" >原密码</label>
							<div class="am-form-content">
								<input type="password" id="user-old-password" placeholder="请输入原登录密码"/>
								
							</div>
						</div>
						<div class="am-form-group">
							<label for="user-new-password" class="am-form-label">新密码</label>
							<div class="am-form-content">						
								<input type="password" id="user-new-password" placeholder="由数字、字母组合">						
							</div>
						</div>
						<div class="am-form-group">
							<label for="user-confirm-password" class="am-form-label">确认密码</label>
							<div class="am-form-content">
								<input type="password" id="user-confirm-password" placeholder="请再次输入上面的密码">
							</div>
						</div>
						<div class="info-btn">
						
							 <input  type="button" onclick="password();" value="确认修改" class="am-btn am-btn-danger"  class="am-btn am-btn-danger">
						</div>

					

				</div>
				<!--底部-->
				<div class="footer">
					<div class="footer-hd ">
						<p>
							<a href="# ">谁的小店</a>
							<b>|</b>
							<a href="# ">谁的公司</a>
							<b>|</b>
							<a href="# ">谁的网站</a>
						</p>
					</div>
				</div>
			</div>

			<aside class="menu">
				<ul>
					<li class="person active">
						<a href=""><i class="am-icon-user"></i>个人中心</a>
					</li>
					<li class="person">
						<p><i class="am-icon-newspaper-o"></i>个人资料</p>
						<ul>
							<li><a href="../front/client.html">个人信息</a></li>
							<li><a href="../front/password.html">安全设置</a></li>
							<li><a href="../front/address.html">地址管理</a></li>
							<li><a href="referral.html">我的推荐</a></li>
						</ul>
					</li>
					<li class="person">
						<p><i class="am-icon-balance-scale"></i>我的交易</p>
						<ul>
							<li><a href="order.html">订单管理</a></li>
						</ul>
					</li>
					<li class="person">
						<p><i class="am-icon-dollar"></i>我的资产</p>
						<ul>
							<li><a href="../front/points.html">我的积分</a></li>
						</ul>
					</li>
				</ul>
			</aside>
		</div>

	</body>

</html>