<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Wopop</title>
<link href="/b2c/front/css/style_log.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="/b2c/front/css/style.css">
<link rel="stylesheet" type="text/css" href="/b2c/front/css/userpanel.css">
<link rel="stylesheet" type="text/css" href="/b2c/front/css/jquery.ui.all.css">
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
<script language="javascript">

			function admin(){
			 
				var client = {};
				
				client.clientName = $("#clientName").val();
				client.password = $("#password").val();		
				$.ajax({
					type: "POST",
					url: "../frontLogin/loginAjax",					
					data: JSON.stringify(client),
					contentType: "application/json",
					async: true,
					success: function(res) {
						var obj = JSON.parse(res);
						if (obj.STATUS ==="SUCCESS") {
							location.href="../index.html";
        					return	true;
							
						}else{
							alert("账户或密码不对");
							return false;
						}
					},
				});		
				
				
			};

 </script>

</head>

<body class="login" mycollectionplug="bind">
<div class="login_m">
<div class="login_logo"><img src="/b2c/front/images/logoo.png" width="196" height="46"></div>
<div class="login_boder">

<div class="login_padding" id="login_model">

  <h2>USERNAME</h2>
  <label>
    <input type="text" id="clientName" name="clientName"  class="txt_input txt_input2"  >
  </label>
  <h2>PASSWORD</h2>
  <label>
    <input type="password" name="password" id="password" class="txt_input"  >
  </label>
 
 

 
  <p class="forgot"><a id="iforget" href="../front/register.html">Registered user</a></p>
  <div class="rem_sub">
 
    <label>
      <input type="button" onclick="admin();" class="sub_button" name="button" id="button" value="登陆"    style="background-color:blue"  >
      
    </label>
  </div>
</div>



<div id="forget_model" class="login_padding" style="display:none">
<br>

   <h1>Forgot password</h1>
   <br>
   <div class="forget_model_h2">(Please enter your registered email below and the system will automatically reset users’ password and send it to user’s registered email address.)</div>
    <label>
    <input type="text" id="usrmail" class="txt_input txt_input2">
   </label>

 
  <div class="rem_sub">
  <div class="rem_sub_l">
   </div>
    <label>
     <input type="submit" class="sub_buttons" name="button" id="Retrievenow" value="Retrieve now" style="opacity: 0.7;">
     　　　
     <input type="submit" class="sub_button" name="button" id="denglou" value="Return" style="opacity: 0.7;">　　
    
    </label>
  </div>
</div>






<!--login_padding  Sign up end-->
</div><!--login_boder end-->
</div><!--login_m end-->
 <br> <br>



</body></html>