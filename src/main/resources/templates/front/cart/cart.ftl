<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

		<title>购物车页面</title>
		<#include "../common/header.ftl">

		<link href="${ctx}/front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/css/cartstyle.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/css/optstyle.css" rel="stylesheet" type="text/css" />

		<script type="text/javascript" src="${ctx}/front/js/jquery.js"></script>
		<script type="text/javascript">
			
			var cartObject = {};
			
			function calculateCart(cartCheck) {
				var skuId = $(cartCheck).attr("value");
				if ($(cartCheck).prop("checked")) {
					var counts = parseInt($("#selected_item_counts").html());
					counts = counts + parseInt($("#"+skuId+"_count").val());
					$("#selected_item_counts").html(counts);
					
					var sum = parseFloat($("#total").html());
					sum = sum + parseFloat($("#sum_"+skuId).val());
					$("#total").html(sum);
				}
			}
			
			function calculateCarts() {
				$("#selected_item_counts").html("0");
				$("#total").html("0.00");
				$(".cart-check").each(function() {
					
					calculateCart(this);
					
				});
			}
			
			$(document).ready(function() {
				var test = window.location.href;
				console.log(test);
				var form = $("<form></form>");
				$(document.body).append(form);
				$(".cart-delete").click(function() {
					var skuId = $(this).attr("sku-id");
					console.log(skuId);
					$.ajax({
						type:"POST",
						url:"../shopping/deleteCart",
						async:true,
						data: {
							"skuId": skuId
						},
						success: function(res) {
							var resObj = JSON.parse(res);
							console.log(resObj);
							console.log(res);
							if (resObj["STATUS"] === "SUCCESS") {
								$("#cart_item_"+skuId).remove();
								location.reload();
							} else if (resObj["STATUS"] === "FAIL") {
								console.log(resObj["MESSAGE_BODY"]);
								
								
							} else if (resObj["STATUS"] === "LOGIN_FAIL") {
								
							}
							
							calculateCarts();
						},
						error: function(err) {
							
						}
					});
					
				});
				
				$(".deleteAll").click(function() {
					var checkFlg = false;
					for (var skuId in cartObject) {
						checkFlg = checkFlg || cartObject[skuId];
					}
					if (!checkFlg) {
						alert("请至少选择一个商品");
						return;
					}
					console.log(cartObject);
					$.ajax({
						type: "POST",
						url: "../shopping/deleteCartAll",
						async: true,
						data: JSON.stringify(cartObject),
						contentType: "application/json",
						success: function(res) {
							var resObj = JSON.parse(res);
							console.log(resObj);
							console.log(res);
							if (resObj["STATUS"] === "SUCCESS") {
								$("#cart_item_"+skuId).remove();
								location.reload();
							} else if (resObj["STATUS"] === "FAIL") {
								console.log(resObj["MESSAGE_BODY"]);
								
								
							} else if (resObj["STATUS"] === "LOGIN_FAIL") {
								
							}
							
							calculateCarts();
						},
						error: function(err) {
							
						}
					});
					
				});
				
				$(".cart-check").on("change", function() {
					cartObject[$(this).attr("value")] = $(this).prop("checked");
					calculateCarts();
				});
				
				$("#cart_check_all").on("change", function() {
					$(".cart-check").prop("checked", $(this).prop("checked"));
					calculateCarts();
				});
				
				$(".cart-count").focusout(function() {
					var option = "HAND";
					var skuId = $(this).attr("sku-id");
					var count = $(this).val();
					
					$.ajax({
						type:"POST",
						url:"../shopping/updateCart",
						async:true,
						data: {
							"skuId": skuId,
							"count": count,
							"option": option
						},
						success: function(res) {
							var resObj = JSON.parse(res);
							if (resObj["STATUS"] === "SUCCESS") {
								$("#"+skuId+"_count").css("border-color", "#444");
								var cartItem = resObj["CART_MODEL"];
								$("#"+cartItem.skuId+"_count").val(cartItem.count);
								
								$("#"+skuId+"_sum").html(cartItem.sum);
								$("#sum_"+skuId).val(cartItem.sum);
								
								
							} else if (resObj["STATUS"] === "FAIL") {
								console.log(resObj["MESSAGE_BODY"]);
								
								var cartItem = resObj["CART_MODEL"];
								$("#"+cartItem.skuId+"_count").val(cartItem.count);
								
								$("#"+skuId+"_sum").html(cartItem.sum);
								$("#sum_"+skuId).val(cartItem.sum);
								$("#"+skuId+"_count").css("border-color", "red");
								
							} else if (resObj["STATUS"] === "LOGIN_FAIL") {
								
							}
							
							calculateCarts();
						},
						error: function(err) {
							
						}
					});
				});
				
				$(".cart-opt").click(function() {
					var option = $(this).attr("name");
					var skuId = $(this).attr("sku-id");
					$(this).hide();
					var self = $(this);
					
					if ($("#"+skuId+"_count").val() == 0 && option == "MINUS") {
						return;
					}
					
					$.ajax({
						type:"POST",
						url:"../shopping/updateCart",
						async:true,
						data: {
							"skuId": skuId,
							"option": option
						},
						success: function(res) {
							var resObj = JSON.parse(res);
							if (resObj["STATUS"] === "SUCCESS") {
								var cartItem = resObj["CART_MODEL"];
								$("#"+cartItem.skuId+"_count").val(cartItem.count);
								
								$("#"+skuId+"_sum").html(cartItem.sum);
								$("#sum_"+skuId).val(cartItem.sum);
								
							} else if (resObj["STATUS"] === "FAIL") {
								
							} else if (resObj["STATUS"] === "LOGIN_FAIL") {
								
							}
							self.show();
							calculateCarts();
						},
						error: function(err) {
							self.show();
						}
					});
				});
				
				$("#go_confirm").click(function() {
					var checkFlg = false;
					for (var skuId in cartObject) {
						checkFlg = checkFlg || cartObject[skuId];
					}
					if (!checkFlg) {
						alert("请至少选择一个商品");
						return;
					}
					
					form.attr("action", "../shopping/goConfirm");
					form.attr("method", "POST");
					
					var inputObject = $("<input type='hidden' />");
					inputObject.attr("name", "cartObject");
					inputObject.val(JSON.stringify(cartObject));
					form.append(inputObject);
					
					form.submit();
				});
			});
		
		</script>

	</head>

	<body>
		<#include "../common/top.ftl">

		<div class="clear"></div>

		<!--购物车 -->
		<div class="concent">
			<div id="cartTable" >
				<div class="cart-table-th">
					<div class="wp">
						<div class="th th-chk">
							<div id="J_SelectAll1" class="select-all J_SelectAll">

							</div>
						</div>
						<div class="th th-item">
							<div class="td-inner">商品信息</div>
						</div>
						<div class="th th-price">
							<div class="td-inner">单价</div>
						</div>
						<div class="th th-amount">
							<div class="td-inner">数量</div>
						</div>
						<div class="th th-sum">
							<div class="td-inner">金额</div>
						</div>
						<div class="th th-op">
							<div class="td-inner">操作</div>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<#if cartList??>
				<#list cartList as cartItem>
				<tr class="item-list" id="cart_item_${cartItem.skuId}">
					<input id="sku_price_${cartItem.skuId}" type="hidden" value="${cartItem.skuPrice}" />
					<input id="price_${cartItem.skuId}" type="hidden" value="${cartItem.showPrice}" />
					<input id="sum_${cartItem.skuId}" type="hidden" value="${cartItem.sum}" />
					<div class="bundle  bundle-last ">
						<div class="clear"></div>
						<div class="bundle-main">
							<ul class="item-content clearfix">
								<li class="td td-chk">
									<div class="cart-checkbox ">
										<input class="check cart-check" id="cart_check_${cartItem.skuId}" name="items[]" value="${cartItem.skuId}" type="checkbox">
										<label for="cart_check_${cartItem.skuId}"></label>
									</div>
								</li>
								<li class="td td-item">
									<div class="item-pic">
										<a href="#" target="_blank" data-title="${cartItem.productName}" class="J_MakePoint" data-point="tbcart.8.12">
											<img style="width: 100%; height: 100%;" src="${ctx}${cartItem.coverImageUrl}" class="itempic J_ItemImg"></a>
									</div>
									<div class="item-info">
										<div class="item-basic-info">
											<a href="#" target="_blank" title="${cartItem.productName}" class="item-title J_MakePoint" data-point="tbcart.8.11">${cartItem.productName}</a>
										</div>
									</div>
								</li>
								<li class="td td-info">
									<div class="item-props">
										<span class="sku-line">${cartItem.skuPro}</span>
									</div>
								</li>
								<li class="td td-price">
									<div class="item-price price-promo-promo">
										<div class="price-content">
											<div class="price-line">
												<em class="price-original">${cartItem.showPrice}</em>
											</div>
											<div class="price-line">
												<em class="J_Price price-now" tabindex="0">${cartItem.skuPrice}</em>
											</div>
										</div>
									</div>
								</li>
								<li class="td td-amount">
									<div class="amount-wrapper ">
										<div class="item-amount ">
											<div class="sl">
												<input sku-id="${cartItem.skuId}" class="cart-opt am-btn" name="MINUS" type="button" value="-" />
												<input sku-id="${cartItem.skuId}" 
													id="${cartItem.skuId}_count" 
													class="cart-count"  
													name="" 
													type="text" 
													value="${cartItem.count}" 
													style="width:30px; text-align: center;" />
												<input sku-id="${cartItem.skuId}" class="cart-opt am-btn" name="PLUS" type="button" value="+" />
											</div>
										</div>
									</div>
								</li>
								<li class="td td-sum">
									<div class="td-inner">
										<em tabindex="0" id="${cartItem.skuId}_sum" class="J_ItemSum number">${cartItem.sum}</em>
									</div>
								</li>
								<li class="td td-op">
									<div class="td-inner">
										<a href="javascript:;" sku-id="${cartItem.skuId}" data-point-url="#" class="delete cart-delete">删除</a>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</tr>
				</#list>
				</#if>
			</div>
			<div class="clear"></div>

			<div class="float-bar-wrapper">
				<div id="J_SelectAll2" class="select-all J_SelectAll">
					<div class="cart-checkbox">
						<input style="margin-top: 0;" class="check-all check" id="cart_check_all" name="select-all" value="true" type="checkbox" />
						
					</div>
					<label for="cart_check_all"><span>全选</span></label>
				</div>
				<div class="operations">
					<a href="javascript:void(0);" hidefocus="true" class="deleteAll">删除</a>
				</div>
				<div class="float-bar-right">
					<div class="amount-sum">
						<span class="txt">已选商品</span>
						<em id="selected_item_counts">0</em><span class="txt">件</span>
						<div class="arrow-box">
							<span class="selected-items-arrow"></span>
							<span class="arrow"></span>
						</div>
					</div>
					<div class="price-sum">
						<span class="txt">合计:</span>
						<strong class="price">¥<em id="total" style="vertical-align: top;">0.00</em></strong>
					</div>
					<div class="btn-area">
						<a href="javascript:void(0)" id="go_confirm" class="submit-btn submit-btn-disabled" aria-label="请注意如果没有选择宝贝，将无法结算">
							<span>结&nbsp;算</span></a>
					</div>
				</div>

			</div>

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

		<!--操作页面-->

		<div class="theme-popover-mask"></div>

		<div class="theme-popover">
			<div class="theme-span"></div>
			<div class="theme-poptit h-title">
				<a href="javascript:;" title="关闭" class="close">×</a>
			</div>
			<div class="theme-popbod dform">
				<form class="theme-signin" name="loginform" action="" method="post">

					<div class="theme-signin-left">

						<li class="theme-options">
							<div class="cart-title">颜色：</div>
							<ul>
								<li class="sku-line selected">12#川南玛瑙<i></i></li>
								<li class="sku-line">10#蜜橘色+17#樱花粉<i></i></li>
							</ul>
						</li>
						<li class="theme-options">
							<div class="cart-title">包装：</div>
							<ul>
								<li class="sku-line selected">包装：裸装<i></i></li>
								<li class="sku-line">两支手袋装（送彩带）<i></i></li>
							</ul>
						</li>
						<div class="theme-options">
							<div class="cart-title number">数量</div>
							<dd>
								<input class="min am-btn am-btn-default" name="" type="button" value="-" />
								<input class="text_box" name="" type="text" value="1" style="width:30px;" />
								<input class="add am-btn am-btn-default" name="" type="button" value="+" />
								<span  class="tb-hidden">库存<span class="stock">1000</span>件</span>
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
							<img src="../../../front/images/kouhong.jpg_80x80.jpg" />
						</div>
						<div class="text-info">
							<span class="J_Price price-now">¥39.00</span>
							<span id="Stock" class="tb-hidden">库存<span class="stock">1000</span>件</span>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--引导 -->
		<div class="navCir">
			<li><a href="home2.html"><i class="am-icon-home "></i>首页</a></li>
			<li><a href="sort.html"><i class="am-icon-list"></i>分类</a></li>
			<li class="active"><a href="shopcart.html"><i class="am-icon-shopping-basket"></i>购物车</a></li>	
			<li><a href="../person/index.html"><i class="am-icon-user"></i>我的</a></li>					
		</div>
	</body>
</html>