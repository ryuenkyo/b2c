<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
	<meta name="keywords" content="">
	<meta name="content" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <link href="/b2c/front/AmazeUI-2.4.2/assets/css/login.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    
    <script src="https://www.promisejs.org/polyfills/promise-6.1.0.js"></script>
  	<script src="http://gosspublic.alicdn.com/aliyun-oss-sdk-4.4.4.min.js"></script>
  	<script src="/b2c/front/js/jquery.js"></script>

    
    

    
    
    
	<script language="javascript">
		function code(){
			var	phone = $("#telphone").val();
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
		
		$(document).ready(function(){
  			$("#code-name").focus(function(){
    			$("#clientName").css("background-color","#FFFFCC");
  			});
 			$("#clientName").blur(function(){
 				var	user = $("#clientName").val();
   			 	$.ajax({
					type: "GET",
					url: "../front/user.html",					
					data: "user="+user,
					async: true,
					success: function(res) {
						var obj = JSON.parse(res);
						if (obj === true) {
							    
        					return	true;           
							
						}else{
							alert("用户名已存在");
							return false;
						}
					},
				});	 			 	
  			});
		});
		
		
		function register(){
		var client = {};
				
				client.clientName = $("#clientName").val();
				client.nickName = $("#nickName").val();				
				client.age = $("#age").val();
				client.telphone = $("#telphone").val();
				client.password = $("#password").val();
				client.referralCode = $("#referralCode").val();
				client.codename = $("#code-name").val();
				client.headerImg = $("#headerImg").val();
	
				var Name = $("#clientName").val();
				var Namesize = $("#clientName").val().length;
				
				var nick = $("#nickName").val();
				var nicksize = $("#nickName").val().length;
				
				var age = $("#age").val();
				var agesize = $("#age").val().length;
				var patrn = /^[0-9]*$/; 
				
				var telphone = $("#telphone").val();
				var telphonesize = $("#telphone").val().length;
				
				var pw = $("#password").val();
				var pws = $("#posswordss").val();
				var img = $("#headerImg").val();
				
				
					if(Name == ""){
						alert("请填写用户名");
						return false;
					}
					if(nick == ""){
						alert("请填写昵称");
						return false;
					}
					if(age == ""){
						alert("请填写年龄");
						return false;
					}
					if(telphone == ""){
						alert("请填写电话号码");
						return false;
					}
					if(pw == ""){
						alert("请填写密码");
						return false;
					}
					if(pw != pws){
						alert("两次密码不同");
						return false;
					}
					if(Namesize>10){
						alert("用户名超过十个字符");
						return false;
					}
					if(nicksize>5){
						alert("昵称超过五个字符");
						return false;
					}
					if(agesize>=3){
						alert("年龄超过范围");
						return false;
					}
					if(!patrn.test(age)){
						alert('年龄不是纯数字');
		  	   			return false;
					}
					if(telphonesize>=12 || !patrn.test(telphone)){
						alert('手机号不正确');
		  	   			return false;
					}
					
	
			$.ajax({
				type: "POST",
				url: "../front/inuser.html",					
				data: JSON.stringify(client),
				contentType: "application/json",
				async: true,
				success: function(res) {
					var obj = JSON.parse(res);
						if (obj === true) {
							alert("注册成功");
							location.href="../front/admin.html";
        					          
							
						}else{
							alert("手机验证码不正确");
							return false;
						}
				
				},
			});		
		};
		
		
		
		$(document).ready(function(){
			
  			$("#referralCode").focus(function(){
    			$("#referralCode").css("background-color","#FFFFCC");
  			});
 			$("#referralCode").blur(function(){
 				var	referralCode = $("#referralCode").val();
   			 	$.ajax({
					type: "GET",
					url: "../front/referralCode.html",					
					data: "referralCode="+referralCode,
					async: true,
					success: function(res) {
						var obj = JSON.parse(res);
						if (obj === true) {
							    
        					return	true;           
							
						}else{
							alert("推荐码不正确或已被使用");
							return false;
						}
					},
				});	 			 	
  			});			  			
		});
		
		
   </script>


</head>
<body class="login_bj" >

<div class="zhuce_body">
	<div class="logo"><a href="#"><img src="images/logo.png" width="114" height="54" border="0"></a></div>
    <div class="zhuce_kong">
    	<div class="zc">
        	<div class="bj_bai" style="margin-top:-45px";>
          
       	  	  <form action="inuser.html" method="post" >
          
               
				<input type="text" class="form-control" id="headerImg" name="headerImg"  style="visibility: hidden;"/>
                <input  type="text" id="clientName" name="clientName" class="kuang_txt user" placeholder="用户名,最多是个字符">
                <input  type="text" id="nickName" name="nickName" class="kuang_txt name" placeholder="昵称,最多五个字符">
                <input  type="text" id="age" name="age" class="kuang_txt possword" placeholder="年龄">
                <input  type="text" id="password" name="password" class="kuang_txt possword" placeholder="密码,最少6个位">
                <input  type="text" id="posswordss" name="posswordss" class="kuang_txt posswordss" placeholder="确认密码">
                <input  type="text" id="referralCode" name="referralCode" class="kuang_txt possword" placeholder="推荐码，可不填">
              	<input  type="text" id="telphone" name="telphone" class="kuang_txt phone" placeholder="手机号">
              	<input  type="text" id="code-name" name="code-name" class="kuang_txt yanzm" placeholder="填写手机验证码">
               
                <div style="height:30px"></div>
                <input name="注册" type="button"  onclick="register();" class="btn_zhuce" value="注册" style="width:133px;">
                <input type="button" onclick="code();" class="btn_zhuce" value="短信验证" style="width:133px;"/>
                
                </form>
            </div>
        	<div class="bj_right">
            	<p>使用以下账号直接登录</p>
                <a href="#" class="zhuce_qq">QQ注册</a>
                <a href="#" class="zhuce_wb">微博注册</a>
                <a href="#" class="zhuce_wx">微信注册</a>
                <p>已有账号？<a href="login.html">立即登录</a></p>
            
            </div>
        </div>
     
    </div>

</div>
    

</body>
</html>