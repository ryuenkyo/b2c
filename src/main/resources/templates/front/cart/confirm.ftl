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
				var SkuObject = {
					<#list confirmList as confirm>
						'${confirm.skuId}': {
							count:${confirm.count},
							ut:${confirm.cartUt},		
							skuPro:'${confirm.skuPro}'
						},
					</#list>			
				};	
			$(document).ready(function() {
			console.log(SkuObject);
				var form = $("<form></form>");
				$(document.body).append(form);
				var logisticsObject;
				var payObject;
				var addressObject;
				<#list logisticsList as logistics>
				 $("#logistics_"+${logistics.logisticsId}).click(function() {
					 logisticsObject='${logistics.logisticsName}';
				 });
				</#list>
				<#list payList as pay>
				 $("#pay_"+${pay.payId}).click(function() {
					 payObject='${pay.payName}';
				 });
				</#list>
				<#list addressList as address>
				 $("#addr_"+${address.addressId}).click(function() {
					 addressObject=${address.addressId};
					 $('#provinceAdd').html('${address.proviceName}');
					 $('#cityAdd').html('${address.cityName}');
					 $('#distAdd').html('${address.areaName}');
					 $('#streetAdd').html('${address.street}');
					 $('#reciverAdd').html('${address.reciveName}');
					 $('#phoneAdd').html('${address.telphone}');
				 });
				 <#if address.isDefault>
				 addressObject=${address.addressId};
				 $('#provinceAdd').html('${address.proviceName}');
				 $('#cityAdd').html('${address.cityName}');
				 $('#distAdd').html('${address.areaName}');
				 $('#streetAdd').html('${address.street}');
				 $('#reciverAdd').html('${address.reciveName}');
				 $('#phoneAdd').html('${address.telphone}');
				 </#if>
				</#list>
				
				$("#J_Go").click(function() {
					var productPrice = ${orderAmount};
					var message = document.getElementById("message").value;
					if(undefined == logisticsObject||undefined == payObject){
						alert("物流方式与支付方式不能为空");
					}else{					
						var OtherObject = {
						"payObject": {payObject},
						"addressObject": {addressObject},
						"logisticsObject": {logisticsObject},
						"productPrice": {productPrice},
						"message": {message}	
						};	
						console.log(OtherObject);
						form.attr("action", "../shopping/order");
						form.attr("method", "POST");
						var skuObject = $("<input type='hidden' />");
						skuObject.attr("name", "skuObject");
						skuObject.val(JSON.stringify(SkuObject));
						form.append(skuObject);
						var otherObject = $("<input type='hidden' />");
						otherObject.attr("name", "otherObject");
						otherObject.val(JSON.stringify(OtherObject));
						form.append(otherObject);
						form.submit();
					}
			});
		});
		</script>

	</head>

	<body>
		<#include "../common/top.ftl">
		<div class="clear"></div>
		<div class="concent">
			<!--地址 -->
			<div class="paycont">
				<div class="address">
					<h3>确认收货地址 </h3>
					<div class="control">
						<a class="tc-btn createAddr theme-login am-btn am-btn-danger" href="/b2c/front/address.html">使用新地址</a>
						<a class="tc-btn createAddr theme-login am-btn am-btn-danger" href="/b2c/front/address.html">地址编辑</a>
					</div>
					<div class="clear"></div>
					<ul>
						<#list addressList as address>
						<li id="addr_${address.addressId}" class="user-addresslist 
							<#if address.isDefault>
							defaultAddr
							</#if>
							">
							<div class="address-left">
								<div class="user DefaultAddr">
									<span class="buy-address-detail">   
                   						<span class="buy-user">${address.reciveName}</span>
										<span class="buy-phone">${address.telphone}</span>
									</span>
								</div>
								<div class="default-address DefaultAddr">
									<span class="buy-line-title buy-line-title-type">收货地址：</span>
									<span class="buy--address-detail">
										<span class="province">${address.proviceName}</span>
										<span class="city">${address.cityName}</span>
										<span class="dist">${address.areaName}</span>
										<span class="street">${address.street}</span>
										<span class="street">${address.zipCode}</span>
									</span>
								</div>
								<#if address.isDefault>
								<ins class="deftip">默认地址</ins>
								</#if>
							</div>
							<div class="address-right">
								<a href="../person/address.html">
									<span class="am-icon-angle-right am-icon-lg"></span>
								</a>
							</div>
							<div class="clear"></div>
						</li>
						</#list>
						
					</ul>
					<div class="clear"></div>
				</div>
				<!--物流 -->
				<div class="logistics">
					<h3>选择物流方式</h3>
					<ul class="op_express_delivery_hot">
						<#list logisticsList as logistics>
							<li class="OP_LOG_BTN" id="logistics_${logistics.logisticsId}"><img src="${logistics.imageUrl}" />${logistics.logisticsName}<span></span></li>
							
						</#list>
					</ul>
				</div>
				<div class="clear"></div>
				<!--支付方式-->
				<div class="logistics">
					<h3>选择支付方式</h3>
					<ul class="pay-list">
						<#list payList as pay>
							<li class="pay taobao" id="pay_${pay.payId}" ><img src="${pay.imageUrl}" />${pay.payName}<span></span></li>
						</#list>
						
					</ul>
				</div>
				<div class="clear"></div>

				<!--订单 -->
				<div class="concent">
					<div id="payTable">
						<h3>确认订单信息</h3>
						<div class="cart-table-th">
							<div class="wp">

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
								<div class="th th-oplist">
									<div class="td-inner">配送方式</div>
								</div>

							</div>
						</div>
						<div class="clear"></div>
                       <#list confirmList as confirm>
						<tr class="item-list">
							<div class="bundle  bundle-last">

								<div class="bundle-main">
									<ul class="item-content clearfix">
										<div class="pay-phone">
											<li class="td td-item">
												<div class="item-pic">
													<a href="#" class="J_MakePoint">
														<img src="${confirm.coverImageUrl}" class="itempic J_ItemImg"></a>
												</div>
												<div class="item-info">
													<div class="item-basic-info">
														<a href="#" class="item-title J_MakePoint" data-point="tbcart.8.11">${confirm.productName}</a>
													</div>
												</div>
											</li>
											<li class="td td-info">
												<div class="item-props">
													<span class="sku-line">${confirm.skuPro}</span>
												</div>
											</li>
											<li class="td td-price">
												<div class="item-price price-promo-promo">
													<div class="price-content">
														<em class="J_Price price-now">${confirm.skuPrice}</em>
													</div>
												</div>
											</li>
										</div>
										<li class="td td-amount">
											<div class="amount-wrapper ">
												<div class="item-amount ">
													<span class="phone-title">购买数量</span>
													<div class="sl">
														<input class="text_box" disabled="disabled"  type="text" value="${confirm.count}" style="width:30px;" />

													</div>
												</div>
											</div>
										</li>
										<li class="td td-sum">
											<div class="td-inner">
												<em tabindex="0" class="J_ItemSum number" id="sum">${confirm.sum}</em>
											</div>
										</li>
										<li class="td td-oplist">
											<div class="td-inner">
												<span class="phone-title">配送方式</span>
												<div class="pay-logis">
													快递<b class="sys_item_freprice">0</b>元
												</div>
											</div>
										</li>

									</ul>
									<div class="clear"></div>
								</div>
						</tr>
						</#list>
						<div class="clear"></div>
						</div>
						<div class="pay-total">
					<!--留言-->
						<div class="order-extra">
							<div class="order-user-info">
								<div id="holyshit257" class="memo">
									<label>买家留言：</label>
									<input id="message" type="text" title="选填,对本次交易的说明（建议填写已经和卖家达成一致的说明）" placeholder="选填,建议填写和卖家达成一致的说明" class="memo-input J_MakePoint c2c-text-default memo-close">
									<div class="msg hidden J-msg">
										<p class="error">最多输入500个字符</p>
									</div>
								</div>
							</div>

						</div>
						<!--优惠券 -->
						<div class="buy-agio">
						</div>
						<div class="clear"></div>
						</div>
						<!--含运费小计 -->
						<div class="buy-point-discharge ">
							<p class="price g_price ">
								合计（含运费） <span>¥</span><em class="pay-sum" id="productPrice">${orderAmount}</em>
							</p>
						</div>

						<!--信息 -->
						<div class="order-go clearfix">
							<div class="pay-confirm clearfix">
								<div class="box">
									<div tabindex="0" id="holyshit267" class="realPay"><em class="t">实付款：</em>
										<span class="price g_price ">
                                <span>¥</span> <em class="style-large-bold-red " id="J_ActualFee">${orderAmount}</em>
										</span>
									</div>

									<div id="holyshit268" class="pay-address">

										<p class="buy-footer-address">
											<span class="buy-line-title buy-line-title-type">寄送至：</span>
											<span class="buy--address-detail">
							   <span class="province" id="provinceAdd"></span>
											<span class="city" id="cityAdd"></span>
											<span class="dist" id="distAdd"></span>
											<span class="street" id="streetAdd"></span>
											</span>
											</span>
										</p>
										<p class="buy-footer-address">
											<span class="buy-line-title">收货人：</span>
											<span class="buy-address-detail">   
                                     <span class="buy-user" id="reciverAdd"></span>
											<span class="buy-phone" id="phoneAdd"></span>
											</span>
										</p>
									</div>
								</div>

								<div id="holyshit269" class="submitOrder">
									<div class="go-btn-wrap">
										<a id="J_Go" href="javascript:void(0)" class="btn-go" tabindex="0" title="点击此按钮，提交订单">提交订单</a>
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

			<!--标题 -->
			<div class="am-cf am-padding">
				<div class="am-fl am-cf"><strong class="am-text-danger am-text-lg">新增地址</strong> / <small>Add address</small></div>
			</div>
			<hr/>

			<div class="am-u-md-12">
				<form class="am-form am-form-horizontal">

					<div class="am-form-group">
						<label for="user-name" class="am-form-label">收货人</label>
						<div class="am-form-content">
							<input type="text" id="user-name" placeholder="收货人">
						</div>
					</div>

					<div class="am-form-group">
						<label for="user-phone" class="am-form-label">手机号码</label>
						<div class="am-form-content">
							<input id="user-phone" placeholder="手机号必填" type="email">
						</div>
					</div>

					<div class="am-form-group">
						<label for="user-phone" class="am-form-label">所在地</label>
						<div class="am-form-content address">
							<select data-am-selected>
								<option value="a">浙江省</option>
								<option value="b">湖北省</option>
							</select>
							<select data-am-selected>
								<option value="a">温州市</option>
								<option value="b">武汉市</option>
							</select>
							<select data-am-selected>
								<option value="a">瑞安区</option>
								<option value="b">洪山区</option>
							</select>
						</div>
					</div>

					<div class="am-form-group">
						<label for="user-intro" class="am-form-label">详细地址</label>
						<div class="am-form-content">
							<textarea class="" rows="3" id="user-intro" placeholder="输入详细地址"></textarea>
							<small>100字以内写出你的详细地址...</small>
						</div>
					</div>

					<div class="am-form-group theme-poptit">
						<div class="am-u-sm-9 am-u-sm-push-3">
							<div class="am-btn am-btn-danger">保存</div>
							<div class="am-btn am-btn-danger close">取消</div>
						</div>
					</div>
				</form>
			</div>

		</div>
		<div class="clear"></div>
	</body>
</html>