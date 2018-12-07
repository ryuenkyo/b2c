
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">

		<title>我的推荐码</title>
		
		<script type="text/javascript" src="/b2c/front/basic/js/jquery-1.9.min.js"></script>
		<script type="text/javascript" src="/b2c/front/basic/js/quick_links.js"></script>
		<script type="text/javascript" src="/b2c/front/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
		<script type="text/javascript" src="/b2c/front/js/jquery.imagezoom.min.js"></script>
		<script type="text/javascript" src="/b2c/front/js/jquery.flexslider.js"></script>
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
							<div class="am-fl am-cf">
								<strong class="am-text-danger am-text-lg">我的推荐</strong>
							</div>
							<button class="am-btn" style="position: absolute; right: 20px;">生成推荐码</button>
						</div>
						<hr/>
						<div class="pointlist" style="width: 100%; max-width: calc(100% - 20px) !important;">
							<table style="width: calc(100% - 20px);">
								<thead>
									<tr>												
										<th style="width: 20%;">推荐码</th>
										<th style="width: 20%;">状态</th>
										<th style="width: 20%;">使用者ID</th>
										<th style="width: 20%;">使用者</th>
										<th style="width: 20%;">创建时间</th>
									</tr>
								</thead>										
								<tbody>
									<tr>
									<#if referral??>
									<#list referral?keys as key>
										<#assign referralList = referral[key]>
										<td style="width: 20%;">
										<#if referralList??>
										${referralList.referralCode}
										<#else>
										-
										</#if>
										</td>
										<td style="width: 20%;">
										<#if referralList??>
										${referralList.isAvailable?c}
										<#else>
										-
										</#if>
										</td>
										<td style="width: 20%;">
										<#if referralList??>
										${referralList.usedClientId}
										<#else>
										-
										</#if>
										</td>
										<td style="width: 20%;">
										<#if referralList.usedClientName??>
										${referralList.busedClientName}
										<#else>
										-
										</#if>
										</td>
										<td style="width: 20%;">
										<#if referralList.ct??>
										${referralList.ct}
										<#else>
										-
										</#if>
										</td>
										</#list>
										</#if>
									</tr>
								</tbody>
							</table>
						</div>
						
						<!--分页 -->
						</div>
						    <!--加载更多按钮-->
						    <div class="js-load-more">
						   		<button class="another" style="position: center; right: 20px;">加载更多</button>
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
							<li><a href="information.html">个人信息</a></li>
							<li><a href="safety.html">安全设置</a></li>
							<li><a href="address.html">地址管理</a></li>
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
							<li><a href="points.html">我的积分</a></li>
						</ul>
					</li>
				</ul>
			</aside>
		</div>

	</body>
	
	<script language="javascript">
		$("another").click(function() {
			$.ajax({
				type:"PUT",
				url:"../b2c/newReferral",
				data: {clientId:1},
				contentType: "application/json",
				async:true,
				success: function(res){
					var obj = JSON.parse(res);
					if (obj[SERVICE.STATUS] === SERVICE.SUCCESS) {
						location.reload(".pointlist");
					} 
				}
				
			})
		});
		
		$(function(){

		    /*初始化*/
		    var counter = 0; /*计数器*/
		    var pagecount = 1; /*offset*/
		    var limit = 5; /*size*/
		
		
		
		    /*首次加载*/
		    getData(pagecount, limit);
			var page = {}
		    /*监听加载更多*/
		    $(document).on('click', '.js-load-more', function () {
		        pagecount++
		        getData(pagecount, limit);
		        page[page] = pagecount
		    });
		    function getData(offset,size){
		        $.ajax({
		            type: 'GET',
		            url: "../b2c/referralInfo",
		            async:true,
			    	contentType: "application/json",
			    	data:JSON.stringify(page),
		            success: function(reponse){
		                var data = reponse;
		                var sum = reponse.length;
		                var result = '';
		                /************业务逻辑块：实现拼接html内容并append到页面*****************/
		                //console.log(offset , size, sum);
		
		                for (var i = (offset -1) * size; i < sum; i++) {                
		                    result += '<a href="/PhoneManage/Notice/text?id=' + data[i].F_Id + '"><div class="list"><div class="text"><h3>' + data[i].F_Title + '</h3><p>'
		                            + data[i].F_CreatorTime + '</p></div><div class="back"><img src="/Content/Phone/images/back1.png" width="32" /></div></div></a>';
		                }
		                $('#list-new').append(result);
		                /*******************************************/
		                /*隐藏more*/
		                if ((offset * size) > sum) {                
		                    $(".js-load-more").hide();
		                }else{
		                    $(".js-load-more").show();
		                }
		            },
		            error: function(xhr, type){
		                alert('Ajax error!');
		            }
		        });
		    }
		});
	</script>
    	

</html>