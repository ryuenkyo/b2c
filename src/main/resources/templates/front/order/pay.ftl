<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0 ,minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<title>订单确认</title>
		<#include "../common/header.ftl">
		<link href="${ctx}/front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/css/cartstyle.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/css/jsstyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/front/js/jquery.js"></script>
		<script src="${ctx}/front/js/address.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				$("#paySuccess").click(function() {
					var orderId = ${orderId};
					var status = '已支付';
					var countMap = '${countMapString}';
					$.ajax({
						type:"POST",
						url:"../shopping/payStatus",
						async:true,
						data: {
							"orderId": orderId,
							"status": status,
							"countMap":countMap
							},
						success: function(res) {
							var resObj = JSON.parse(res);
							console.log(resObj);
							console.log(res);
							if (resObj["STATUS"] === "SUCCESS") {
								  window.location.href = '../shopping/paySuccess.html';				
							} else if (resObj["STATUS"] === "FAIL") {
								console.log(resObj["MESSAGE_BODY"]);
							} else if (resObj["STATUS"] === "LOGIN_FAIL") {
								
							}
						},
						error: function(err) {
							
						}
					});
						
				});
				$("#payCannel").click(function() {
					var orderId = ${orderId};
					var status = '已取消';
					var countMap = '${countMapString}';
					console.log(countMap);
					$.ajax({
						type:"POST",
						url:"../shopping/payStatus",
						async:true,
						data: {
							"orderId": orderId,
							"status": status,
							"countMap":countMap
							},
						success: function(res) {
							var resObj = JSON.parse(res);
							console.log(resObj);
							console.log(res);
							if (resObj["STATUS"] === "SUCCESS") {
								  window.location.href = '../search';
							} else if (resObj["STATUS"] === "FAIL") {
								console.log(resObj["MESSAGE_BODY"]);
								
								
							} else if (resObj["STATUS"] === "LOGIN_FAIL") {
								
							}
						},
						error: function(err) {
							
						}
					});
						
				});
			});
		</script>
	</head>

	<body>
		<#include "../common/top.ftl">
		<div class="clear"></div>
		<div class="concent">
				<!--订单 -->
				<div class="concent"  style="margin:0px auto; width:200px;">
						<!--信息 -->
						<div class="order-go clearfix">
							<div class="pay-confirm clearfix">
								<div class="box">
									<div tabindex="0" id="holyshit267" class="realPay"><em class="t">实付款：</em>
										<span class="price g_price ">
                                <span>¥</span> <em class="style-large-bold-red " id="J_ActualFee">${total}</em>
										</span>
									</div>
								</div>

								<div id="holyshit269" class="submitOrder">
									<div class="go-btn-wrap">
										<a id="paySuccess" href="javascript:void(0)" class="btn-go" tabindex="0" title="点击此按钮，付款">付款</a>
										<a id="payCannel" href="javascript:void(0)" class="btn-go" tabindex="0" title="点击此按钮，取消订单">取消</a>
									</div>
								</div>
								<div class="clear"></div>
							</div>
						</div>
					</div>

					<div class="clear"></div>
				</div>
			</div>
			<!--底部-->
			<div class="footer">
				<div class="footer-hd ">
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
		
		<div class="theme-popover-mask"></div>
		<div class="theme-popover">
			<hr/>
		</div>
		<div class="clear"></div>
	</body>
</html>