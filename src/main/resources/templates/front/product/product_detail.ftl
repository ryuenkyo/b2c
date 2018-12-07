<!DOCTYPE html>
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>商品详细</title>
		<#include "../common/header.ftl">

		<link href="${ctx}/front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/css/optstyle.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/css/style.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="${ctx}/front/basic/js/jquery-1.9.min.js"></script>
		<script type="text/javascript" src="${ctx}/front/basic/js/quick_links.js"></script>
		<script type="text/javascript" src="${ctx}/front/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
		<script type="text/javascript" src="${ctx}/front/js/jquery.imagezoom.min.js"></script>
		<script type="text/javascript" src="${ctx}/front/js/jquery.flexslider.js"></script>
		<!--<script type="text/javascript" src="${ctx}/front/js/list.js"></script>-->
		
		<script type="application/javascript">
			var skuMap = {
				<#list skuMap?keys as key>
					'${key}': {
						skuId: '${skuMap[key].skuId}',
						price: '${skuMap[key].price}',
						stock: '${skuMap[key].stock}'
					},
				</#list>
				"": {}
			};
			
			var skuSum = ${skuSum};

			$(document).ready(function() {
				
				$(".flexslider").flexslider({
					animation: "slide",
					start: function(slider) {
						$('body').removeClass('loading');
					}
				});

				$(".jqzoom").imagezoom();
				$("#thumblist li a").click(function() {
					$(this).parents("li").addClass("tb-selected").siblings().removeClass("tb-selected");
					$(".jqzoom").attr('src', $(this).find("img").attr("mid"));
					$(".jqzoom").attr('rel', $(this).find("img").attr("big"));
				});
				
				
				$(".sku-line").click(function() {
					if ($(this).hasClass("selected")) {
						
					} else {
						$(this).addClass("selected").siblings("li").removeClass("selected");
						
						
					}
				});
				
				
			});
		</script>
	</head>

	<body>
		<#include "../common/top.ftl">

		<div class="clear"></div>
        <b class="line"></b>
		<div class="listMain">
			<!--分类-->
			<div class="nav-table"></div>
			<ol class="am-breadcrumb am-breadcrumb-slash">
				<!--<li><a href="#">首页</a></li>
				<li><a href="#">分类</a></li>
				<li class="am-active">内容</li>-->
			</ol>
			<div class="scoll">
				<section class="slider">
					<div class="flexslider">
						<ul class="slides">
							<li><img src="../../../front/images/01.jpg" title="pic" /></li>
							<li><img src="../../../front/images/02.jpg" /></li>
							<li><img src="../../../front/images/03.jpg" /></li>
						</ul>
					</div>
				</section>
			</div>

			<!--放大镜-->
			<div class="item-inform">
				<div class="clearfixLeft" id="clearcontent">
					<div class="box">
						<#assign firstImage = imageList[0] />
						<div class="tb-booth tb-pic tb-s310">
							<a href="${firstImage.imageUrl}">
								<img src="${firstImage.imageUrl}" alt="细节展示放大镜特效" rel="${firstImage.imageUrl}" class="jqzoom" />
							</a>
						</div>
						<ul class="tb-thumb" id="thumblist">
							<#list imageList as imageItem>
							<li class="tb-selected">
								<div class="tb-pic tb-s40">
									<a href="javascript:void(0)"><img src="${imageItem.imageUrl}" mid="${imageItem.imageUrl}" big="${imageItem.imageUrl}"></a>
								</div>
							</li>
							</#list>
						</ul>
					</div>
					<div class="clear"></div>
				</div>

				<div class="clearfixRight">
					<!--规格属性-->
					<!--名称-->
					<div class="tb-detail-hd">
						<h1>${product.productName}</h1>
					</div>
					<div class="tb-detail-list">
						<!--价格-->
						<div class="tb-detail-price" style="padding-top: 10px !important;">
							<li id="sku_price_area" class="price iteminfo_price">
								<dt>现价</dt>
								<dd><em>¥</em><b id="sku_price" class="sys_item_price">
								<#if skuProduct??>
								<#else>
								${product.showPrice}
								</#if>
								</b></dd>                                
							</li>
							<li id="show_price_area" class="price iteminfo_mktprice">
								<dt>原价</dt>
								<dd><em>¥</em><b id="show_price" class="sys_item_mktprice">${product.showPrice}</b></dd>									
							</li>
							<div class="clear"></div>
						</div>
						<div class="clear"></div>
						<!--销量-->
						<ul class="tm-ind-panel">
							<li class="tm-ind-item tm-ind-sumCount canClick">
								<div class="tm-indcon"><span class="tm-label">累计销量</span><span class="tm-count">${product.salesVolume}</span></div>
							</li>
							<li class="tm-ind-item tm-ind-reviewCount canClick tm-line3">
								<div class="tm-indcon"><span class="tm-label">累计评价</span><span class="tm-count">
								<#if evaluationCount??>
								${evaluationCount}
								</#if>
								</span></div>
							</li>
						</ul>
						<div class="clear"></div>

						<!--各种规格-->
						<dl class="iteminfo_parameter sys_item_specpara">
							<dt class="theme-login">
								<div class="cart-title">可选规格<span class="am-icon-angle-right"></span>
								</div>
							</dt>
							<dd>
								<!--操作页面-->
								<div class="theme-popover-mask"></div>
								<div class="theme-popover">
									<div class="theme-span"></div>
									<div class="theme-poptit">
										<a href="javascript:;" title="关闭" class="close">×</a>
									</div>
									<div class="theme-popbod dform">
										<form class="theme-signin">
											<div class="theme-signin-left">
												<#list pAttrNameList as pAttrNameItem>
												<div class="theme-options">
													<div class="cart-title">${pAttrNameItem.pAttrName}</div>
													<ul>
														<#list pAttrValueMap[pAttrNameItem.pAttrNameId?string] as pAttrValueItem>
														<li p-attr-name-id="${pAttrValueItem.pAttrNameId}" p-attr-value-id="${pAttrValueItem.pAttrValueId}" class="sku-line">
														${pAttrValueItem.pAttrValue}<i></i></li>
														</#list>
													</ul>
												</div>
												</#list>
												<div class="theme-options">
													<div class="cart-title number">数量</div>
													<dd>
														<input id="min" class="am-btn am-btn-default" name="" type="button" value="-" />
														<input id="text_box" name="" type="text" value="1" style="width:30px;" />
														<input id="add" class="am-btn am-btn-default" name="" type="button" value="+" />
														<span id="Stock" class="tb-hidden">库存<span id="stu_stock" class="stock">
														${skuSum}
														</span>件</span>
													</dd>
												</div>
												<div class="clear"></div>
												<div class="btn-op">
													<div class="btn am-btn am-btn-warning">确认</div>
													<div class="btn close am-btn am-btn-warning">取消</div>
												</div>
											</div>
											<div class="theme-signin-right">
												<div class="img-info">
													<img src="../images/songzi.jpg" />
												</div>
												<div class="text-info">
													<span class="J_Price price-now">¥39.00</span>
													<span id="Stock" class="tb-hidden">库存<span class="stock" id="StockCount">1000</span>件</span>
												</div>
											</div>
										</form>
									</div>
								</div>
							</dd>
						</dl>
						<div class="clear"></div>
					</div>

					<div class="pay">
						<div class="pay-opt">
						<a href="home.html"><span class="am-icon-home am-icon-fw">首页</span></a>
						<a><span class="am-icon-heart am-icon-fw">收藏</span></a>
						
						</div>
						<li>
							<div class="clearfix tb-btn tb-btn-buy theme-login">
								<a id="LikBuy" title="点此按钮到下一步确认购买信息" href="#">立即购买</a>
							</div>
						</li>
						<li>
							<div class="clearfix tb-btn tb-btn-basket theme-login">
								<a id="LikBasket" title="加入购物车" href="#"><i></i>加入购物车</a>
							</div>
						</li>
					</div>

				</div>

				<div class="clear"></div>

			</div>

			<div class="clear"></div>
			<!-- introduce-->
			<div class="introduce">
				<div class="browse">
				    <div class="mc"> 
					     <ul>					    
					     	<div class="mt">            
								<h2>看了又看</h2>        
							</div>
							<#list hotProduct?keys as key>
								<#assign hotProductList = hotProduct[key]/>
								<#if hotProductList??>														
							<li class="first">
								<#list imageHotList as imageHotItem>
								<#if imageHotItem.productId == hotProductList.productId>
								<div class="p-img">                    
						      		<a  href="#"> <img class="" src="${imageHotItem.imageUrl}"> </a>               
								</div>
								</#if>
								</#list>	
						      	<div class="p-name">
						      		<a href="#">${hotProductList.productName}</a>
						      	</div>
								<div class="p-price"><strong>${hotProductList.showPrice}</strong></div>
							</li>
								</#if>
							</#list>
						</ul>					
					</div>
				</div>
				<div class="introduceMain">
					<div class="am-tabs" data-am-tabs>
						<ul class="am-avg-sm-3 am-tabs-nav am-nav am-nav-tabs">
							<li class="am-active">
								<a href="#">
									<span class="index-needs-dt-txt">宝贝详情</span>
								</a>
							</li>
							<li>
								<a href="#">
									<span class="index-needs-dt-txt">全部评价</span>
								</a>
							</li>
						</ul>
						<div class="am-tabs-bd">
							<div class="am-tab-panel am-fade am-in am-active">
								<div class="J_Brand">
									<div class="attr-list-hd tm-clear">
										<h4>产品详细：</h4>
									</div>
									<div class="clear"></div>
										${product.des}
									<div class="clear"></div>
								</div>
								<div class="details">
									<div class="attr-list-hd after-market-hd">
										<h4>商品细节</h4>
									</div>
									<div class="twlistNews">
										<#list imageList as imageItem>
										<img src="${imageItem.imageUrl}" />
										</#list>
									</div>
								</div>
								<div class="clear"></div>
							</div>

							<div class="am-tab-panel am-fade">
                                <div class="actor-new">
                                	<div class="rate">                
                                		<strong>100<span>%</span></strong><br> <span>好评度</span>            
                                	</div>
                                </div>	
                                <div class="clear"></div>
								<div class="tb-r-filter-bar">
									<ul class=" tb-taglist am-avg-sm-4">
										<li class="tb-taglist-li tb-taglist-li-current">
											<div class="comment-info">
												<span>全部评价</span>
												<span class="tb-tbcr-num">(32)</span>
											</div>
										</li>
										<li class="tb-taglist-li tb-taglist-li-1">
											<div class="comment-info">
												<span>好评</span>
												<span class="tb-tbcr-num">(32)</span>
											</div>
										</li>
										<li class="tb-taglist-li tb-taglist-li-0">
											<div class="comment-info">
												<span>中评</span>
												<span class="tb-tbcr-num">(32)</span>
											</div>
										</li>
										<li class="tb-taglist-li tb-taglist-li--1">
											<div class="comment-info">
												<span>差评</span>
												<span class="tb-tbcr-num">(32)</span>
											</div>
										</li>
									</ul>
								</div>
								<div class="clear"></div>

								<ul class="am-comments-list am-comments-list-flip">
									<#if evaluation??>
									<#list evaluation?keys as keye>
									<#assign evaluationList = evaluation[keye]/>
									<#if evaluationList??>
									<li class="am-comment">
										<!-- 评论容器 -->
										<a href="">
											<img class="am-comment-avatar" src="../images/hwbn40x40.jpg" />
											<!-- 评论者头像 -->
										</a>
										
										<div class="am-comment-main">
											<!-- 评论内容容器 -->
											<header class="am-comment-hd">
												<!--<h3 class="am-comment-title">评论标题</h3>-->
												
												<div class="am-comment-meta">
													<!-- 评论元数据 -->
													<a href="#link-to-user" class="am-comment-author">b***1 (匿名)</a>
													<!-- 评论者 -->
													评论于
													<time datetime="">2015年11月02日 17:46</time>
												</div>
											</header>
											
											<div class="am-comment-bd">
												<div class="tb-rev-item " data-id="255776406962">
													<div class="J_TbcRate_ReviewContent tb-tbcr-content ">
														${evaluationList.evaluationContent}
													</div>
													<div class="tb-r-act-bar">
														颜色分类：柠檬黄&nbsp;&nbsp;尺码：S
													</div>
												</div>
												
											</div>
											<!-- 评论内容 -->
										</div>
										
									</li>
									</#if>
									</#list>
									</#if>
								</ul>

								<div class="clear"></div>

								<!--分页 -->
								<ul class="am-pagination am-pagination-right">
									<li class="am-disabled"><a href="#">&laquo;</a></li>
									<li class="am-active"><a href="#">1</a></li>
									<li><a href="#">2</a></li>
									<li><a href="#">3</a></li>
									<li><a href="#">4</a></li>
									<li><a href="#">5</a></li>
									<li><a href="#">&raquo;</a></li>
								</ul>
								<div class="clear"></div>

								<div class="tb-reviewsft">
									<div class="tb-rate-alert type-attention">购买前请查看该商品的 <a href="#" target="_blank">购物保障</a>，明确您的售后保障权益。</div>
								</div>

							</div>
						</div>

					</div>

					<div class="clear"></div>

					<div class="footer">
						<div class="footer-hd">
							<p>
								<a href="#">谁的小店</a>
								<b>|</b>
								<a href="#">谁的公司</a>
								<b>|</b>
								<a href="#">谁的网站</a>
							</p>
						</div>
					</div>
				</div>

			</div>
		</div>
		
		<!--菜单 -->
		<div class=tip>
			<div id="sidebar">
				<div id="wrap">
					<div id="prof" class="item">
						<a href="#">
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
								<a href="#" class="login_favorite">我的收藏</a>
							</div>
							<i class="icon_arrow_white"></i>
						</div>
					</div>
					<div id="shopCart" class="item">
						<a href="#"><span class="message"></span></a>
						<p>购物车</p><p class="cart_num">0</p>
					</div>

					<div class="quick_toggle">
						<li class="qtitem">
							<a href="#top" class="return_top"><span class="top"></span></a>
						</li>
					</div>

					<!--回到顶部 -->
					<div id="quick_links_pop" class="quick_links_pop hide"></div>
				</div>
			</div>
		</div>
	</body>
	
	<script language="javascript">
		var pAttr = {};
		var change = 0;
		var form = $("<form></form>");
		$(document.body).append(form);
		$(".theme-signin-left ul li").click(function(e) {
		    var pAValue=$(this).attr("p-attr-value-id")
		    var pAName=$(this).attr("p-attr-name-id")
		    pAttr[pAName] = pAValue;
			console.log(pAttr);
			var str = JSON.stringify(pAttr);
			
			if ( undefined === skuMap[str] && change != 1) {
				
			} else {
				$("#sku_price").html(skuMap[str].price);
				$("#stu_stock").html(skuMap[str].stock);
			}	
	 	});
	 	
	 	$("#LikBasket").click(function(){
	 		var pAValue=$(this).attr("p-attr-value-id")
		    var pAName=$(this).attr("p-attr-name-id")
		    pAttr[pAName] = pAValue;
			console.log(pAttr);
			var str = JSON.stringify(pAttr);
			
		 	if ( undefined === skuMap[str] && change != 1) {
				alert("请选择商品属性");
				change = 1;
			} else {		
				$.ajax({
					type: "POST",
					url: "../shopping/addCart",
					data: {skuId:skuMap[str].skuId , count:count},
					async: true,
					success: function(res) {
						alert("购物车加入成功");
						console.log(count);
					},
					error: function(err) {
						
					}
				});	
			}
		})
	 	
		var count = $("#text_box").attr("value");
		$("#min").click(function(){
			
			if(count > 1 ){
				count = count - 1;	
			} else if(count == 1){
				count;
			}
			$("#text_box").attr("value",count);
		});
		
		$("#add").click(function(){
			
			if(count < skuSum ){
				count = count + 1;
			} else if(count == skuSum){
				count;
			}
			$("#text_box").attr("value",count);
		});
		
			$("#LikBuy").click(function(){
			var cartObject = {};
			var pAValue=$(this).attr("p-attr-value-id")
		    var pAName=$(this).attr("p-attr-name-id")
		    pAttr[pAName] = pAValue;
			console.log(pAttr);
			var str = JSON.stringify(pAttr);
			var skuId = skuMap[str].skuId;
			form.attr("action", "../shopping/goConfirm");
			form.attr("method", "POST");
			var countObject = $("<input type='hidden' />");
			var skuObject = $("<input type='hidden' />");
			countObject.attr("name", "count");
			skuObject.attr("name", "skuId");
			countObject.val(count);
			skuObject.val(skuId);
			form.append(countObject);
			form.append(skuObject);
			form.submit();		
		});	
	
	</script>
	
</html>
