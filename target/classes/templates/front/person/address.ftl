<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0,maximum-scale=1.0, user-scalable=0">

		<title>地址管理</title>

		<link href="/b2c/front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css">
		<link href="/b2c/front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css">

		<link href="/b2c/front/css/personal.css" rel="stylesheet" type="text/css">
		<link href="/b2c/front/css/addstyle.css" rel="stylesheet" type="text/css">
		<!--在线jquery 需要变成本地的  -->
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
		<script src="/b2c/front/AmazeUI-2.4.2/assets/js/amazeui.js"></script>
		<script type="text/javascript">
		
				
				
			function btnChange(values) {
			$("#areaName").html("");
			$("#cityName").html("");
			var districtId = values ;
				$.ajax({
					type: "GET",
					url: "../front/deleteCart.html",
					data:"districtId="+districtId,					 
					async: true,
					success: function(res) {
					var obj=JSON.parse(res);
					var list= obj.idList;
					var cityList= obj.cityList;
	 				 $.each(cityList, function (i, item) {
	 				 
                     	 jQuery("#cityName").append("<option value="+item+">"+item+"</option>");
                     	 
                     
                      });
					},
				});
				 
			}	
			
			
			function btnat(values) {
			$("#areaName").html("");
			var district = values ;
				$.ajax({
					type: "GET",
					url: "../front/deleteCarts.html",
					data:"district="+district,					 
					async: true,
					success: function(res) {
					var obj=JSON.parse(res);
					var list= obj.idList;
					var cityList= obj.cityList;
	 				 $.each(cityList, function (i, item) {
	 				 
                     	 jQuery("#areaName").append("<option value="+item+">"+item+"</option>");
                     	
                    
                      });
					},
				});
				 
			}
			
			
			
			
			
			
			
			
			
			
			
			
			function upbtnChange(values) {
			$("#upareaName").html("");
			$("#upcityName").html("");
			var districtId = values ;
				$.ajax({
					type: "GET",
					url: "../front/deleteCart.html",
					data:"districtId="+districtId,					 
					async: true,
					success: function(res) {
					var obj=JSON.parse(res);
					var list= obj.idList;
					var cityList= obj.cityList;
	 				 $.each(cityList, function (i, item) {
	 				 
                     	 jQuery("#upcityName").append("<option value="+item+">"+item+"</option>");
                     	 
                     
                      });
					},
				});
				 
			}	
														
			function upbtnat(values) {
			$("#upareaName").html("");
			var district = values ;
				$.ajax({
					type: "GET",
					url: "../front/deleteCarts.html",
					data:"district="+district,					 
					async: true,
					success: function(res) {
					var obj=JSON.parse(res);
					var list= obj.idList;
					var cityList= obj.cityList;
	 				 $.each(cityList, function (i, item) {
	 				 
                     	 jQuery("#upareaName").append("<option value="+item+">"+item+"</option>");
                     	
                    
                      });
					},
				});
				 
			}
				
				
				 
				
				
			function up(e){		
				$('#updas').remove();	
				$("#upda").attr("style","display:none");
				$("#upppCode").html("");		
			 	$("#upcityName").html("");		
				$("#upareaName").html("");
				var html = $('#updas').html();
				$('#inset').append(html);	
					
				$('#updas').remove();	
				$("#upda").attr("style","display:none");
				$("#upppCode").html("");		
			 	$("#upcityName").html("");		
				$("#upareaName").html("");
				var html = $('#updas').html();
				$('#inset').append(html);	

				$.ajax({
					type: "GET",
					url: "../front/upisDefaultadd.html",					
					data: "addressId="+e,
					contentType: "application/json",
					async: true,
					success: function(res) {
						var str = JSON.parse(res);				
					
						$("#addressId").val(str.addressId);
						$("#upreciveName").val(str.reciveName);
						$("#uptelphone").val(str.telphone);
						jQuery("#upppCode").append("<option value="+(str.pCode)+">"+(str.proviceName)+"</option>");
						jQuery("#upcityName").append("<option value="+(str.cityName)+">"+(str.cityName)+"</option>");
						jQuery("#upareaName").append("<option value="+(str.areaName)+">"+(str.areaName)+"</option>");											
						$("#upstreet").val(str.street);
						$("#upzipCode").val(str.zipCode);

					            
					},
				});		
				
				
				
			};
			
				
			function de(){
				alert("默认地址不可以删除");
			
			
		   };
			
			
			function isDefault(){		
				$.ajax({
					type: "GET",
					url: "../front/upisDefaultid.html",					
					data: $("#form1").serialize(),
					async: true,
					success: function(res) {
						location.reload(true);
					},
				});		
				
				
			};
			
			
			
		</script>
		
		<script language="javascript">
		  function preserve(){
		  	var reciveName = $("#reciveName").val();
		  	var userphone = $("#user-phone").val();
		  	var pCode = $("#pCode").val();
		  	var cityName = $("#cityName").val();
		  	var areaName = $("#areaName").val();
		  	var street = $("#street").val();
		  	var zipCode = $("#zipCode").val();
		  	var patrn = /^[0-9]*$/; 
		  	var userphonesize = $("#user-phone").val().length;
		  	var zipCodesize = $("#zipCode").val().length;
		  	var streetsize = $("#street").val().length;
		  	var reciveNamesize = $("#reciveName").val().length;
		  	if(reciveName != "" && userphone != "" && pCode != "" && cityName != null && areaName != null && street != "" && zipCode != "" ){
		  		if(!patrn.test(userphone)){
		  			alert('手机号不是纯数字');
		  			return false;
		  		}
		  		if(userphonesize != 11){
		  			alert('手机号不正确');
		  			return false;
		  		}
		  		if(!patrn.test(zipCode)){
		  			alert('邮编不是纯数字');
		  			return false;
		  		}
		  		if(zipCodesize != 6){
		  			alert('邮编号不正确');
		  			return false;
		  		}
		  		if(streetsize > 100){
		  			alert('地址超过100字');
		  			return false;
		  		}
		  		if(reciveNamesize > 10){
		  			alert('昵称不能超过10个字符');
		  			return false;
		  		}
		  		
		  		$.ajax({
                	type: "POST",
                	dataType: "json",
                	<#list client as key>
               		url: "../front/delivery.html?clientId=${key.clientId}" ,
                	</#list>
                	data: $('#form').serialize(),
                	success: function (res) {        
                		                		
                	},
                   
            	});
		           
		  		
		    }
		    else{
		    	alert('请将资料填写完整');
		  			return false;
		    }
	         window.location.reload();
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
				<div class="main-wrap" id="inset">

					<div class="user-address">
						<!--标题 -->
						<div class="am-cf am-padding">
							<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">地址管理</strong></div>
						</div>
						<hr/>
						<ul class="am-avg-sm-1 am-avg-md-3 am-thumbnails">
						<#list delivery as key>
						<#if ((key.isDefault?c) == "true" ) >
						

							<li class="user-addresslist defaultAddr">
								<span class="new-option-r"><i class="am-icon-check-circle"></i>默认地址</span>
								<p class="new-tit new-p-re">
									
									<span class="new-txt">${key.reciveName}</span>
									<span class="new-txt-rd2">${key.telphone}</span>
								</p>
								<div class="new-mu_l2a new-p-re">
									<p class="new-mu_l2cw">
									
										<span class="title">地址：</span>
										<span class="province">${key.proviceName}</span>
										<span class="city">${key.cityName}</span>市
										<span class="dist">${key.areaName}</span>
										<span class="street">${key.street}</span></p>
																																
											
								</div>
								<div class="new-addr-btn">
								<form action="datadelivery.html?addressId=${key.addressId}" class="am-form am-form-horizontal" method="Post" onsubmit="return toVaild()">								
								<input type="button" onclick="up(${key.addressId});" class="btn btn-default pull-right" value="编辑" />								
								<span class="new-addr-bar">|</span>							
								<input type="button"   onclick="de();" class="btn btn-default pull-right" value="删除" />							
								</form>
								</div>
							</li>
							</#if>
							
							<#if ((key.isDefault?c) == "false" ) >
							

							<li class="user-addresslist">
							<form action="upisDefault.html?addressId=${key.addressId}" class="am-form am-form-horizontal" method="Post" onsubmit="return toVaild()">
								<input class="new-option-r" type="submit" value="设为默认"></input>
							</form>	
								<p class="new-tit new-p-re">
									
									<span class="new-txt">${key.reciveName}</span>
									<span class="new-txt-rd2">${key.telphone}</span>
								</p>
								<div class="new-mu_l2a new-p-re">
									<p class="new-mu_l2cw">
									
										<span class="title">地址：</span>
										<span class="province">${key.proviceName}</span>
										<span class="city">${key.cityName}</span>市
										<span class="dist">${key.areaName}</span>
										<span class="street">${key.street}</span></p>
																																
											
								</div>
								
								<div class="new-addr-btn">
								<form action="datadelivery.html?addressId=${key.addressId}" class="am-form am-form-horizontal" method="Post" onsubmit="return toVaild()">								
								<input type="button" onclick="up(${key.addressId});" class="btn btn-default pull-right" value="编辑" />							
								<span class="new-addr-bar">|</span>
								<input type="submit"  class="btn btn-default pull-right" value="删除" />							
								</form>
								</div>
							</li>
							</#if>
								</#list>
					
						</ul>
						
						
						
						<div class="clear"></div>
						<a class="new-abtn-type" data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0}">添加新地址</a>
						<!--例子-->
						<div id="doc-modal-1">

							<div class="add-dress"  id="updas">

								<!--标题 -->
								<div class="am-cf am-padding">
									<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">新增地址</strong></div>
								</div>
								<hr/>

								<div class="am-u-md-12 am-u-lg-8" style="margin-top: 20px;" >
									<#list client as key>
							<form action="delivery.html?clientId=${key.clientId}" class="am-form am-form-horizontal" method="post" id="form" >
							</#list>

										<div class="am-form-group">
											<label for="user-name" class="am-form-label">收货人</label>
											<div class="am-form-content">
												<input type="text" id="reciveName"  name="reciveName" placeholder="收货人">
											</div>
										</div>

										<div class="am-form-group">
											<label for="user-phone" id="telphone" name="telphone" class="am-form-label">手机号码</label>
											<div class="am-form-content">
												<input id="user-phone" placeholder="手机号必填" type="text" id="telphone" name="telphone">
											</div>
										</div>
										
										
										<div class="am-form-group">
											<label for="user-address" class="am-form-label">所在地</label>
											<div class="am-form-content address">
											
													
												<select data-am-selected="{maxHeight: 200}" id="pCode" name="pCode"   onchange='btnChange(this[selectedIndex].value);' >
												<#if rcDistrict??>
												    <option value="0" selected>中国</option>
													<#list rcDistrict as key>
													
													<option  value="${key.districtId}" >${key.district}</option>
												
													</#list>
												</#if>
												</select>										
												<select name="cityName" data-am-selected="{maxHeight: 200}" id="cityName" onchange='btnat(this[selectedIndex].value);' data-am-selected="{maxHeight: 200}">						
												</select>
												<select name="areaName" data-am-selected="{maxHeight: 200}" id="areaName" data-am-selected="{maxHeight: 200}"></select>
												
											</div>
										</div>
										
										<div class="am-form-group">
											<label for="user-phone"  class="am-form-label">地址邮编</label>
											<div class="am-form-content">
												<input  placeholder="地址邮编" type="text" id="zipCode" name="zipCode">
											</div>
										</div>
										
										
												
										<div class="am-form-group">
											<label for="user-intro" class="am-form-label">详细地址</label>
											<div class="am-form-content">
												<textarea class="" rows="3" id="street" name="street" placeholder="输入详细地址"></textarea>
												<small>100字以内写出你的详细地址...</small>
											</div>
										</div>

										<div class="am-form-group">
											<div class="am-u-sm-9 am-u-sm-push-3">
												<input type="button" value="保存" class="am-btn am-btn-danger" onclick="preserve();">
												<a href="javascript: void(0)" class="am-close am-btn am-btn-danger" data-am-modal-close>取消</a>
											</div>
										</div>
									</form>
								</div>

							</div>

						</div>

					</div>
					<div class="clear"></div>
				
				
						<div class="add-dress" style="display:none" id="upda">
				<div class="clear"></div>
						<a class="new-abtn-type" data-am-modal="{target: '#doc-modal-1', closeViaDimmer: 0}">添加新地址</a>
						<!--例子-->
						<div id="doc-modal-1">

							<div class="add-dress"  id="updas">

								<!--标题 -->
								<div class="am-cf am-padding">
									<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">修改地址</strong></div>
								</div>
								<hr/>

								<form action="upisDefaultid.html" class="am-form am-form-horizontal" method="post" onsubmit="return toVaild()" id="form1">		
										<input type="hidden" id="addressId"  name="addressId" />
										<div class="am-form-group">
											<label for="user-name" class="am-form-label">收货人</label>
											<div class="am-form-content">
												<input type="text" id="upreciveName"  name="reciveName" placeholder="收货人"/>
											</div>
										</div>

										<div class="am-form-group">
											<label for="user-phone" class="am-form-label">手机号码</label>
											<div class="am-form-content">
												<input type="text" id="uptelphone" name="telphone" placeholder="手机号必填" />
											</div>
										</div>
										
										
										<div class="am-form-group">
											<label for="user-address" class="am-form-label">所在地</label>
											<div class="am-form-content address">
											
													
												<select  id="uppCode" name="pCode"   onchange='upbtnChange(this[selectedIndex].value);'>
												<#if rcDistrict??>
												    <option value=""  id="upppCode"  selected></option>
													<#list rcDistrict as key>
														
													
													<option  value="${key.districtId}" >${key.district}</option>
													
												
													</#list>
												</#if>
												</select>
												
												<select name="cityName" id="upcityName" onchange='upbtnat(this[selectedIndex].value);'>
												
												</select>
												<select name="areaName" id="upareaName"></select>
												
											</div>
										</div>
										
										<div class="am-form-group">
											<label for="user-phone"  class="am-form-label">地址邮编</label>
											<div class="am-form-content">
												<input  placeholder="地址邮编" type="text" id="upzipCode" name="zipCode"/>
											</div>
										</div>
										
										
												
										<div class="am-form-group">
											<label for="user-intro" class="am-form-label">详细地址</label>
											<div class="am-form-content">
												<textarea class="" rows="3" id="upstreet" name="street" placeholder="输入详细地址"></textarea>
												<small>100字以内写出你的详细地址...</small>
											</div>
										</div>

										<div class="am-form-group">
											<div class="am-u-sm-9 am-u-sm-push-3">
												<input type="button" onclick="isDefault();" value="保存" class="am-btn am-btn-danger" >
												<a href="javascript: void(0)" class="am-close am-btn am-btn-danger" data-am-modal-close>取消</a>
									</div>
										</div>
									</form>
								</div>

							</div>

						

					</div>
					
				
					<div class="clear"></div>
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
							<li><a href="../b2c/front/address.html">地址管理</a></li>
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