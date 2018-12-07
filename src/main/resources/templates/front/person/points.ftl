<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">

		<title>积分明细</title>

		<link href="/b2c/front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="/b2c/front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">
		<link href="/b2c/front/css/personal.css" rel="stylesheet" type="text/css">
		<link href="/b2c/front/css/point.css" rel="stylesheet" type="text/css">
		<script src="/b2c/front/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
		<script src="/b2c/front/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
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
							<li><img src="../images/logobig.png" /></li>
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
					<div class="points">
						<!--标题 -->
						<div class="am-cf am-padding">
							<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">我的积分</strong></div>
						</div>
						<hr/>
						<div class="pointsTitle" style="max-width: calc(100% - 40px);">
						  <#list clientPoint as ket>
						   <div class="usable">总积分<span>${ket}</span></div>
						</#list>
						</div>
						<div class="pointlist am-tabs" data-am-tabs style="max-width: calc(100% - 40px);">
							<ul class="am-avg-sm-3 am-tabs-nav am-nav am-nav-tabs">
								<li class="am-active"><a href="#tab1">全部</a></li>
								
								
							</ul>
							<div class="am-tabs-bd">
								<div class="am-tab-panel am-fade am-in am-active" id="tab1">
									<table>
										<b></b>
										<thead>
											<tr>												
												<th class="th1">积分详情</th>
												<th class="th2">积分变动</th>
												<th class="th3">日期</th>
											</tr>
										</thead>										
										<tbody>
												<#if PointManagement??>
												<#list PointManagement as key>
											<tr>
												<td class="pointType">${key.pointReason}</td>
												<td class="pointNum">+${key.point}</td>
												<td class="pointTime">${key.ct?string('yyyy-MM-dd HH:mm:ss')} </td>
											</tr>		
											</#list>
											</#if>
											
										</tbody>
									</table>
									
								</div>
								<div class="am-tab-panel am-fade" id="tab2">
									<table>
										
									</table>
								</div>
								<div class="am-tab-panel am-fade" id="tab3">
									<table>
										<b></b>
										<thead>
											<tr>												
												<th class="th1">积分详情</th>
												<th class="th2">消耗积分</th>
												<th class="th3">日期</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td class="pointType">积分兑换</td>
												<td class="pointNum">-300</td>
												<td class="pointTime">2016-03-10&nbsp15:27</td>
											</tr>
										</tbody>
									</table>
								</div>

							</div>

						</div>
						
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