<script type="text/javascript">
	$(document).ready(function() {
		
		<#if Session.LOGIN_USER?exists>
		<#else>
			$("#login_button").click(function() {
				$.ajax({
					type:"POST",
					url:"./frontLogin/loginAjax",
					contentType: "application/json",
					data: JSON.stringify({}),
					async:true,
					success: function(res) {
						
					},
					error: function(err) {
						
					}
				});
			});
			
			$("#regist_button").click(function() {
				console.log("跳转注册页面");
			});
		
		</#if>
	});
</script>

<#if Session.LOGIN_USER?exists>

<div class="am-container header">
	<ul class="message-r">
		<div class="topMessage home">
			<div class="menu-hd"><a href="#" target="_top" class="h">商城首页</a></div>
		</div>
		<div class="topMessage my-shangcheng">
			<div class="menu-hd MyShangcheng"><a href="#" target="_top"><i class="am-icon-user am-icon-fw"></i>个人中心</a></div>
		</div>
	</ul>
</div>

<#else>


<!--顶部导航条 -->
<div class="am-container header">
	<ul class="message-r">
		<div class="topMessage">
			<div class="menu-hd">
				<a id="login_button" href="javascript:void(0);">亲，请登录</a>
				<a id="regist_button" href="javascript:void(0);">免费注册</a>
			</div>
		</div>
	</ul>
</div>

</#if>

<!--悬浮搜索框-->
<div class="nav white">
	<div class="logo"><img src="${ctx}/front/images/logo.png" /></div>
	<div class="logoBig">
		<li><img src="${ctx}/front/images/logobig.png" /></li>
	</div>

	<div class="search-bar pr">
		<a name="index_none_header_sysc" href="javascript:void(0);"></a>
		<form action="${ctx}/front/product/search" method="GET">
			<input id="search_product_name" name="productName" type="text" placeholder="搜索" value="${productName!''}" autocomplete="off" />
			<input id="search_catetory_id" name="categoryId" value="${categoryId!''}" type="hidden" />
			<input id="search_attr" name="pAttrObject" value="${pAttrObject!''}" type="hidden" />
			<input id="search_brand_id" name="brandId" value="${brandId!''}" type="hidden" />
			<input id="ai-topsearch" class="submit am-btn"  value="搜索" index="1" type="submit" />
		</form>
	</div>
</div>
<div class="clear"></div>