<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>支付成功</title>
		<#include "../common/header.ftl">
		<link href="${ctx}/front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/css/seastyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/front/basic/js/jquery-1.9.min.js"></script>
		<script type="text/javascript" src="${ctx}/front/js/script.js"></script>
		<link href="${ctx}/front/css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/front/basic/js/quick_links.js"></script>
		</script>
	</head>

	<body>
		<#include "../common/top.ftl">
		<div class="navCir">
			<li><a href="home2.html"><i class="am-icon-home "></i>首页</a></li>
			<li><a href="sort.html"><i class="am-icon-list"></i>分类</a></li>
			<li><a href="../shopping/showCart.html"><i class="am-icon-shopping-basket"></i>购物车</a></li>	
			<li><a href="../person/index.html"><i class="am-icon-user"></i>我的</a></li>					
		</div>
		<div class="status">
			 	<h1 style="font-size:30px;
		 	 	font-family:STHupo;
		 	 	color:#EE3B3B;
		 	 	Margin:100px;
		 	 	text-align:center;">您已成功付款!</h1>
			 <div class="successInfo"style="
	 	 		 text-align:center;
	 	 		 Margin:100px">
				 <div class="option"style="
	 	 		 text-align:center;
	 	 		 Margin:100px">
					<span class="info">您可以</span>
					<a href="../person/order.html" class="J_MakePoint">查看<span style="font-size:20px;
				 	 	font-family:YouYuan;Margin:13px">已买到的宝贝</span></a><br/><br/>
					<a href="../person/orderinfo.html" class="J_MakePoint">查看<span style="font-size:20px;
				 	 	font-family:YouYuan;Margin:10px">交易详情</span></a>
				 </div>
			   </div>
			</div>
		<!--菜单 -->
		<div class=tip>
			<div id="sidebar">
				<div id="wrap">
					<div id="prof" class="item">
						<a href="../person/index.html">
							<span class="setting"></span>
						</a>
						<div class="ibar_login_box status_login">
							<div class="avatar_box">
								<p class="avatar_imgbox"><img src="../images/no-img_mid_.jpg" /></p>
								<ul class="user_info">
									<li>用户名：sl1903</li>
									<li>级&nbsp;别：普通会员</li>
								</ul>
							</div>
							<div class="login_btnbox">
								<a href="#" class="login_order">我的订单</a>
							</div>
							<i class="icon_arrow_white"></i>
						</div>

					</div>
					<div id="shopCart" class="item" onclick="window.open('../shopping/showCart.html')">
						<a href="#">
							<span class="message"></span>
						</a>
						<p>
							购物车
						</p>
						<p class="cart_num">0</p>
					</div>
					<!--回到顶部 -->
					<div id="quick_links_pop" class="quick_links_pop hide"></div>

				</div>

			</div>
			<div id="prof-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					我
				</div>
			</div>
			<div id="shopCart-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					购物车
				</div>
			</div>
			<div id="asset-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					资产
				</div>

				<div class="ia-head-list">
					<a href="#" target="_blank" class="pl">
						<div class="num">0</div>
						<div class="text">优惠券</div>
					</a>
					<a href="#" target="_blank" class="pl">
						<div class="num">0</div>
						<div class="text">红包</div>
					</a>
					<a href="#" target="_blank" class="pl money">
						<div class="num">￥0</div>
						<div class="text">余额</div>
					</a>
				</div>

			</div>
			<div id="foot-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					足迹
				</div>
			</div>
			<div id="brand-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					收藏
				</div>
			</div>
			<div id="broadcast-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					充值
				</div>
			</div>
		</div>
		
		
	</body>

</html>