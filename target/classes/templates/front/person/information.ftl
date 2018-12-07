
<!DOCTYPE html>
<html>


	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">

		<title>个人资料</title>
		

		

		<link href="/b2c/front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css" />
		<link href="/b2c/front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="/b2c/front/css/personal.css" rel="stylesheet" type="text/css">
		<link href="/b2c/front/css/infstyle.css" rel="stylesheet" type="text/css">
		<link href="/b2c/front/css/box.css" rel="stylesheet" type="text/css">
		<script src="/b2c/front/AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
		<script src="/b2c/front/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
		
		
		<script src="https://www.promisejs.org/polyfills/promise-6.1.0.js"></script>
  		<script src="http://gosspublic.alicdn.com/aliyun-oss-sdk-4.4.4.min.js"></script>
  		<script src="/b2c/front/js/jquery.js"></script>
  		<link rel="stylesheet" href="/b2c/front/css/bootstrap.css" />
  		
		


		<script type="application/javascript">
  				$(document).ready(function() {
  					
  					var ossConfig = {
					  region: 'oss-cn-beijing',
					  accessKeyId: 'LTAIYXb54sMwHfQ6',
					  accessKeySecret: 'LzJW046Zs9Zc2Q5S8t3DGI6IlYTpYY',
					  bucket: '1801t'
					}
  					
  					var progressFunc = function (p) {
					  return function (done) {
					    var bar = document.getElementById('progress-bar');
					    bar.style.width = Math.floor(p * 100) + '%';
					    bar.innerHTML = Math.floor(p * 100) + '%';
					    done();
					  }
					};
  					
  					$("#file-button").click(function() {
  						
  						var client = new OSS.Wrapper(ossConfig);
  						client.multipartUpload(
  							$("#object-key-file").val().trim(), 
  							document.getElementById('file').files[0], 
  							{
					    		progress: progressFunc
					    	}).then(function (res) {
					    	
					    		if(res.etag !=null){
					    			$("#headerImg").val(res.etag)
					    		}else{
					    			$("#headerImg").val(res.res.headers.etag)
					    		
					    		}
					    		
					    		
					    		
					    		
					    		
					    		
					    	}).catch(function (err) {					    		
					    		console.log(err);
					    	
					    	});
  						
  					});
  					
  				});
  			</script>
		
		
		
		<script language="javascript">
		  function toVaild(){
		  var nickName = document.getElementById("user-name").value;
		  var nickNamesize = document.getElementById("user-name").value.length;
		  var age = document.getElementById("age").value;
		  var telphone = document.getElementById("user-phone").value;
		  var telphonesize = document.getElementById("user-phone").value.length;
		  var agesize = document.getElementById("age").value.length
		  var patrn = /^[0-9]*$/; 
		  var codena= document.getElementById("code-name").value.length
		  if(nickName != "" && age != "" && telphone != ""){
		  	
		  	if(agesize>2){
		  		alert("请填写正确的年龄");
		     	return false;
		  	}
		  	if(nickNamesize>10){
		  		alert("请填写正确的昵称");
		     	return false;
		  	}
		  	if(!patrn.test(age)){
		  	   alert('年龄不是纯数字');
		  	   return false;
		  	}
		  	if(!patrn.test(telphone)){
		  		alert('手机号不是纯数字');
		  		return false;
		  	}
		  	if(telphonesize != 11){
		  		alert('手机号不正确');
		  		return false;
		  	}
		  	if(codena != 6){
		  		alert('请填写正确的短信验证');
		  		return false;
		  	}
		  		  	
		  	alert("修改成功，页面即将刷新。");
		 	return true;
		  }
		   else{
		     alert("请完整填写信息");
		     return false;
		   }
	   
		}
		
		
		function code(){
		var	phone = $("#user-phone").val();
			$.ajax({
					type: "GET",
					url: "../front/information.html",					
					data: "phone="+phone,
					async: true,
					success: function(res) {},
			});		
		};

		$(document).ready(function(){
			
  			$("#code-name").focus(function(){
    			$("#code-name").css("background-color","#FFFFCC");
  			});
 			$("#code-name").blur(function(){
 				var	code = $("#code-name").val();
   			 	$.ajax({
					type: "GET",
					url: "../front/code.html",					
					data: "code="+code,
					async: true,
					success: function(res) {
						var obj = JSON.parse(res);
						if (obj === true) {
							
        					return	true;           
						
						}else{
							alert("验证码不正确");
							return false;
						}
					},
			});	
   			 	
   			 	
   			 	
   			 	
  			});
  			
		});
		
		function aaa(){
			var	code = $("#code-name").val();
   			 	$.ajax({
					type: "GET",
					url: "../front/code.html",					
					data: "code="+code,
					async: true,
					success: function(res) {
						var obj = JSON.parse(res);
						if (obj === true) {
							$("#Client").submit();
        					return	true;           
						
						}else{
							alert("验证码不正确");
							return false;
						}
					},
			});	
	
		
		};	
	 </script>
	</head>
	
	
	
	

			



	<body style="background-color: #FAFAFA">
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
					<div class="user-info">
						<!--标题 -->
						<div class="am-cf am-padding">
							<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">个人资料</strong></div>
						</div>
						<hr/>

						<!--头像 -->
						<#list client as key>
							<form action="front/upclient.html?clientId=${key.clientId}" class="am-form am-form-horizontal" method="post" onsubmit="return toVaild()" id="Client">
							</#list>
							
							
							 <input type="text" class="form-control" id="headerImg" name="headerImg"  style="visibility: hidden;"/>
							 <#list client as key>
							 <div class="filePic">
								<img src="http://1801t.oss-cn-beijing.aliyuncs.com/object?${key.headerImg}"	style="width:100px; height:100px; margin-left:310px; margin-black:150px;" />
							</div>
						<div class="user-infoPic">
							
							
							
							
							
						 
						
					
						<div class="form-group">
   						
						  <input type="text" class="form-control" id="object-key-file" value="object"  style="visibility: hidden;"/>
						</div>
						<div class="form-group">
						<input type="file" id="file" />
						<br/>
						<input type="button" class="btn btn-primary" id="file-button" value="上传头像" />
						</div>
						<div class="form-group">
						  <div class="progress">
						    <div id="progress-bar"
						         class="progress-bar"
						         role="progressbar"
						         aria-valuenow="0"
						         aria-valuemin="0"
						         aria-valuemax="100" style="min-width: 0em;">
						      0%
						    </div>
						  </div>
						</div>
							
							
							
							
							
							<p class="am-form-help">头像</p>

							<div class="info-m" style="margin-left:320px;">
								<div><b>用户名：<i>${key.clientName }</i></b></div>
								<div class="vip">
								<#if grade??>
								<#list grade as key>
                                      <span></span><a href="#" class="box">${key}</a>
                                </#list>
							    </#if>
								</div>
							</div>
						</div>
						
						
						<!--个人信息 -->
						<div class="info-main">
							<form class="am-form am-form-horizontal">

								<div class="am-form-group">
									<label for="user-name2" class="am-form-label">昵称</label>
									<div class="am-form-content">
										<input type="text" id="user-name" name="nickName"placeholder="请填写昵称" value="${key.nickName}">
                                          <small>昵称长度不能超过5个汉字</small>
									</div>
								</div>

								<div class="am-form-group">
									<label for="user-name" class="am-form-label">年龄</label>
									<div class="am-form-content">
										<input type="text" id="age" name="age" placeholder="请填写年龄" value="${key.age}" >
                                         
									</div>
								</div>

								<div class="am-form-group">
									<label for="user-name" class="am-form-label">电话</label>
									<div class="am-form-content">
										<input id="user-phone" placeholder="请填写电话" name="telphone" type="text" value="${key.telphone}">
                                         
									</div>
								</div>

								
								<div class="am-form-group">
									<label for="user-name" class="am-form-label">短信验证</label>
									<div class="am-form-content">
                                         <input type="text" id="code-name" name="code-name" placeholder="请填写验证码" value="">
										
                                          <small>验证码长度不能超过6个数字</small> 
									</div>
								</div>
								
								</#list>
								
								
								
								
								
								<div class="info-btn">
									
								<input type="button" onclick="code();" class="am-btn am-btn-danger" pull-right" value="短信验证" />
									 <input type="button"   onclick="aaa();" value="确认修改" class="am-btn am-btn-danger" >
									
								
								</div>

							</form>
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

			<aside class="menu" >
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