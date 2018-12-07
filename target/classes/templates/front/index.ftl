<#list category?keys as key>
	
	<#if key == 'categoryInfo'>
		<#assign categoryInfo = category[key] />
	</#if>
	
	<#if key == 'children'>
		<#assign categoryChildren = category[key] />
	</#if>
	
	<#if key == 'levelOne'>
		<#assign levelOne = category[key] />
	</#if>
	
</#list>

<#list storage?keys as key>
	
	<#if key == 'storageList'>
		<#assign storageList = storage[key] />
	</#if>
	
	<#if key == 'storageProductsMap'>
		<#assign storageProductsMap = storage[key] />
	</#if>

</#list>

<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<title>首页</title>
		<#include "./common/header.ftl">

		<link href="${ctx}/front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/css/hmstyle.css" rel="stylesheet" type="text/css" media="all"/>
		
		<script src="${ctx}/front/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
		<script src="${ctx}/front/AmazeUI-2.4.2/assets/js/amazeui.min.js"></script>
		<script src="${ctx}/front/basic/js/quick_links.js"></script>
		<script type="application/javascript">
			$(document).ready(function() {
				
				//轮播 
				$('.am-slider').flexslider();
				$("li").hover(
					function() {
						$(".category-content .category-list li.first .menu-in").css("display", "none");
						$(".category-content .category-list li.first").removeClass("hover");
						$(this).addClass("hover");
						$(this).children("div.menu-in").css("display", "block")
					}, 
					function() {
						$(this).removeClass("hover")
						$(this).children("div.menu-in").css("display", "none")
					}
				);
				
			});
		</script>

	</head>

	<body>
		<div class="hmtop">
			<#include "./common/top.ftl">
		</div>

		<div class="banner">
            <!--轮播 -->
			<div class="am-slider am-slider-default scoll" data-am-flexslider id="demo-slider-0">
				<ul class="am-slides">
					<li class="banner1"><a href="introduction.html"><img src="../../front/images/ad1.jpg" /></a></li>
					<li class="banner2"><a><img src="../../front/images/ad2.jpg" /></a></li>
					<li class="banner3"><a><img src="../../front/images/ad3.jpg" /></a></li>
					<li class="banner4"><a><img src="../../front/images/ad4.jpg" /></a></li>
				</ul>
			</div>
			<div class="clear"></div>
		</div>						
			
		<div class="shopNav">
			<div class="slideall">
				<div class="long-title">
					<span class="all-goods">全部分类</span>
				</div>
				<div class="nav-cont">
					<ul>
						<#list storageList as storage>
							<li class="index">
								<a href="#rack${storage.storageId}">${storage.storageName}</a>
							</li>
						</#list>
					</ul>
				</div>
		        				
				<!--侧边导航 -->
				<div id="nav" class="navfull">
					<div class="area clearfix">
						<div class="category-content" id="guide_2">
							<div class="category">
								<!-- 商品一级分类列表 -->
								<ul class="category-list" id="js_climit_li">
									<!-- 商品一级分类ITEM -->
									<#list levelOne as categoryIdLevelOne>
										<#assign categoryIdLevelOneStr = categoryIdLevelOne?string />
									
									<li class="appliance js_toggle relative first">
										<div class="category-info">
											<h3 class="category-name b-category-name">
												<a href="${ctx}/front/product/search?categoryId=${categoryInfo[categoryIdLevelOneStr].categoryId}" 
													class="ml-22" title="一级分类:${categoryInfo[categoryIdLevelOneStr].categoryName}">
													${categoryInfo[categoryIdLevelOneStr].categoryName}</a>
											</h3>
										</div>
										<div class="menu-item menu-in top">
											<div class="area-in">
												<div class="area-bg">
													<div class="menu-srot">
														<div class="sort-side">
															
															<#if categoryChildren[categoryIdLevelOneStr]??>
															<#list categoryChildren[categoryIdLevelOneStr] as categoryIdLevelTwo>
																<#assign categoryIdLevelTwoStr = categoryIdLevelTwo?string />
															<dl class="dl-sort">
																<dt>
																	<a href="${ctx}/front/product/search?categoryId=${categoryInfo[categoryIdLevelTwoStr].categoryId}">
																		<span title="二级分类:${categoryInfo[categoryIdLevelTwoStr].categoryName}">
																			${categoryInfo[categoryIdLevelTwoStr].categoryName}</span>
																	</a>
																</dt>
																
																<#if categoryChildren[categoryIdLevelTwoStr]??>
																<#list categoryChildren[categoryIdLevelTwoStr] as categoryIdLevelThree>
																	<#assign categoryIdLevelThreeStr = categoryIdLevelThree?string />
																<dd>
																	<a title="三级分类:${categoryInfo[categoryIdLevelTwoStr].categoryName}" 
																		href="${ctx}/front/product/search?categoryId=${categoryInfo[categoryIdLevelThreeStr].categoryId}">
																		<span>${categoryInfo[categoryIdLevelThreeStr].categoryName}</span>
																	</a>
																</dd>
																</#list>
																</#if>
															</dl>
															</#list>
															</#if>
														</div>
														<!--<div class="brand-side">
															<dl class="dl-sort"><dt><span>实力商家</span></dt>
																<dd><a rel="nofollow" title="呵官方旗舰店" target="_blank" href="#" rel="nofollow"><span  class="red" >呵官方旗舰店</span></a></dd>
																<dd><a rel="nofollow" title="格瑞旗舰店" target="_blank" href="#" rel="nofollow"><span >格瑞旗舰店</span></a></dd>
															</dl>
														</div>-->
													</div>
												</div>
											</div>
										</div>
										<b class="arrow"></b>	
									</li><!-- 商品一级分类ITEM -->
									</#list>
								</ul><!-- 商品一级分类列表 -->
							</div><!-- div.category -->
						</div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</div>
		
		<div class="shopMainbg">
			<div class="shopMain" id="shopmain">
				<#list storageList as storage>
				<#assign storageIdStr = storage.storageId?string />
				<!-- 货架标签 -->
				<div class="am-container ">
					<div class="shopTitle ">
						<h4><a name="rack${storage.storageId}" style="font-size: larger;">${storage.storageName}</a></h4>
						<h3>${storage.storageDes}</h3>
					</div>
				</div>
				
				<!-- 货架内容 -->
				<div class="am-g am-g-fixed flood method3 ">
					<ul class="am-thumbnails ">
						<#if storageProductsMap[storageIdStr]??>
						<#list storageProductsMap[storageIdStr] as sproduct>
					
						<li>
							<div class="list ">
								<a href="front/product/detail/${sproduct.productId}.html ">
									<img src=".${sproduct.coverImageUrl}" />
									<div class="pro-title ">${sproduct.productName}</div>
									<span class="e-price ">￥${sproduct.showPrice}</span>
								</a>
							</div>
						</li>
						</#list>
						</#if>
					</ul>
				</div>
				</#list>

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
		</div>

		<!--引导 
		<div class="navCir">
			<li class="active"><a href="home3.html"><i class="am-icon-home "></i>首页</a></li>
			<li><a href="sort.html"><i class="am-icon-list"></i>分类</a></li>
			<li><a href="shopcart.html"><i class="am-icon-shopping-basket"></i>购物车</a></li>	
			<li><a href="../person/index.html"><i class="am-icon-user"></i>我的</a></li>					
		</div>
		-->
		<!--右侧菜单 -->
		<div class="tip">
			<div id="sidebar">
				<div id="wrap">
					<!-- 用户区域 -->
					<div id="prof" class="item ">
						<a href="# ">
							<span class="setting "></span>
						</a>
						<div class="ibar_login_box status_login ">
							<div class="avatar_box ">
								<p class="avatar_imgbox "><img src="../../front/images/no-img_mid_.jpg " /></p>
								<ul class="user_info ">
									<li>用户名：sl1903</li>
									<li>级&nbsp;别：普通会员</li>
								</ul>
							</div>
							<div class="login_btnbox ">
								<a href="# " class="login_order ">我的订单</a>
								<a href="# " class="login_favorite ">我的收藏</a>
							</div>
							<i class="icon_arrow_white "></i>
						</div>
					</div>
					
					<!-- 购物车区域 -->
					<div id="shopCart" class="item ">
						<a href="# ">
							<span class="message "></span>
						</a>
						<p>购物车</p>
						<p class="cart_num ">0</p>
					</div>
					
					<div id="foot " class="item ">
						<a href="# ">
							<span class="zuji "></span>
						</a>
						<div class="mp_tooltip ">
							我的足迹
							<i class="icon_arrow_right_black "></i>
						</div>
					</div>


					<!--回到顶部 -->
					<div id="quick_links_pop " class="quick_links_pop hide "></div>

				</div>

			</div>
			
		</div>
	</body>
</html>