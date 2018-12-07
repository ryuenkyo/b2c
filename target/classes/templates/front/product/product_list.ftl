<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>商品列表</title>
		<#include "../common/header.ftl">
		<style type="text/css"> 
/*		body { margin: 0 auto; padding: 0; width: 570px; font: 75%/120% Arial, Helvetica, sans-serif; } */
		a:focus { outline: none; } 
		#panel { display: none; } 
		.slide { margin: 0; padding: 0; border-top: solid 4px ; } 
		.btn-slide { background: #F27613 url(http://files.jb51.net/file_images/article/201212/20121225165932118.gif) no-repeat right -50px;; text-align: center; width: 144px; height: 31px; padding: 10px 10px 0 0; margin: 0 auto; display: block; font: bold 120%/100% Arial, Helvetica, sans-serif; color: #fff; text-decoration: none; } 
		.active { background-position: right 12px; } 
		</style> 
		<link href="${ctx}/front/AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/basic/css/demo.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/front/css/seastyle.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/front/basic/js/jquery-1.9.min.js"></script>
		<script type="text/javascript" src="${ctx}/front/js/script.js"></script>
		<link href="${ctx}/front/css/style.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${ctx}/front/basic/js/quick_links.js"></script>
		<script type="text/javascript">
			var searchCategoryId = "${categoryId!''}";
			var searchProductName = "${productName!''}";
			var searchPAttrObjectStr = "${pAttrObject!''}";
			var searchBrandId = "${brandId!''}";
			
			var searchPAttrObject;
			if (undefined === searchPAttrObject || "" === searchPAttrObject) {
				searchPAttrObject = {};
			} else {
				searchPAttrObject = JSON.parse(searchPAttrObjectStr);
			}
			
			var CATEGORY_INFO, 
				LEVEL_ONE_CATEGORY, 
				CATEGORY_CHILDREN, 
				CATEGORY_ATTR_INFO, 
				BRAND_LIST, 
				ATTR_NAME_MAPPER, 
				ATTR_VALUE_MAPPER;
				
			var searchObject = {};
			
			searchObject.categoryId = searchCategoryId;
			searchObject.productName = searchProductName;
			searchObject.pAttrObject = searchPAttrObject;
			searchObject.brandId = searchBrandId;
			
			searchObject.page = 0;
			searchObject.limit = 20;
			searchObject.sortBy = "ct";
			searchObject.direction = "desc";
			
			
			$(document).ready(function() {
				var test = window.location.href;
				console.log(test);
				$(".btn-slide").click(function(){ 
					$("#panel").slideToggle("slow"); 
					$(this).toggleClass("active"); return false; 
				}); 
				$.ajax({
					type: "GET",
					url: "../product/searchInfo",
					async: true,
					success: function(res) {
						var resObj = JSON.parse(res);
						CATEGORY_INFO = resObj.category.categoryInfo;
						LEVEL_ONE_CATEGORY = resObj.category.levelOne;
						CATEGORY_CHILDREN = resObj.category.children;
						CATEGORY_ATTR_INFO = resObj.category.categoryAttrInfo;
						
						BRAND_LIST = resObj.brand;
						
						ATTR_NAME_MAPPER = resObj.attrInfo.attrNameMapper;
						
						ATTR_VALUE_MAPPER = resObj.attrInfo.attrValueMapper;
						
						buildCategoryArea(searchCategoryId);
						
						buildBrandArea(searchBrandId);

						searchProduct(searchObject);
						
						$(".search-content").children(".sort").children("li").click(function() {
							$(this).addClass("first").siblings().removeClass("first");
							var colum = $(this).attr("sort-id");
							var option = $(this).attr("option");
							
							searchObject.sortBy = colum;
							
							if ("desc" === searchObject.direction) {
								searchObject.direction = "asc";
							} else {
								searchObject.direction = "desc";
							}
							searchObject.page = 0;
							searchProduct(searchObject);
							
						});
						
						
					},
					error: function(err) {
						
					}
				});
			});
			
			function buildCategoryArea(categoryId) {
				$("#header_category_container").html("");
				$("#s_category_children").html("");
				$("#attr_container").html("");
				var headerCategory = findParentCategory(categoryId, []);		
				var allCategoryTemp = "<a id='all_category' href='javascript:void(0)'>全部分类</a>";
				$("#header_category_container").append($(allCategoryTemp));
				var levelOneCategoryTemp = "<dl id='category_level_one' style='display: none; "+
								" position:fixed;"+
								"background-color: #FFFFFF;"+
								"width: 114px;"+
								"padding: 10px 25px;"+  
								"border: 2px solid #d2364c;"+
								"z-index: 99;'>";
					for (var index in LEVEL_ONE_CATEGORY) {
						var categoryOneId = LEVEL_ONE_CATEGORY[index];
						var categoryItem = CATEGORY_INFO[categoryOneId];
						var categoryOneTemp = "<dd class='level-one' style='text-align: center; margin-top: 5px; cursor: pointer;' "+
						"category-id='"+categoryOneId+"'"+
						">"+categoryItem.categoryName+"</dd>";
						levelOneCategoryTemp += categoryOneTemp;
					}
								
				levelOneCategoryTemp += "</dl>";
				$("#header_category_container").append($(levelOneCategoryTemp));
				
				
				$("#all_category").click(function() {
					searchObject.categoryId = "";
					searchObject.pAttrObject = {};
					searchObject.page = 0;
					searchObject.limit = 20;
					searchObject.sortBy = "ct";
					searchObject.direction = "desc";
					buildCategoryArea(0);
					searchProduct(searchObject);
					
				});
				
				$("#all_category").on({
					mouseover: function() {
						$("#category_level_one").show();
					},
					mouseout: function() {
					    $("#category_level_one").hide();
					}
				});
				
				$("#category_level_one").on({
					mouseover: function() {
						$(this).show();
					},
					mouseout: function() {
						$(this).hide();
					}
				});
				
				$(".level-one").click(function() {
					searchObject.categoryId = $(this).attr("category-id");
					searchObject.pAttrObject = {};
					buildCategoryArea(searchObject.categoryId);
					searchProduct(searchObject);
				});
				
				
				if (0 == categoryId) {
					$("#s_category_area").hide();
					$("#attr_container").hide();
					return;
				}
				
				for (var index in headerCategory) {
					var categoryItem = headerCategory[index];
					var temp = "<span>&gt;</span><a id='header_category' category-id='"+categoryItem.categoryId+
					"' href='javascript:void(0)'>"+categoryItem.categoryName+"</a>";
					$("#header_category_container").append($(temp));
				}
				var divId="#header_category"+categoryItem.categoryId;
				console.log(divId);
				$(header_category).click(function() {
				console.log(divId);
					searchObject.categoryId = $(this).attr("category-id");
					searchObject.pAttrObject = {};
					searchObject.sortBy = "ct";
					searchObject.direction = "desc";
					buildCategoryArea(searchObject.categoryId);
					searchProduct(searchObject);
				});
				
				$("#s_category").html(CATEGORY_INFO[categoryId].categoryName);
				$("#s_category").attr("category-id", categoryId);
				$("#s_category").click(function() {
					searchObject.categoryId = $(this).attr("category-id");
					searchObject.pAttrObject = {};
					searchObject.sortBy = "ct";
					searchObject.direction = "desc";
					$("#attr_container").html("");
					buildCategoryArea(searchObject.categoryId);
					searchProduct(searchObject);
				});
				
				var childrenCategoryList = CATEGORY_CHILDREN[categoryId];
				
				if (undefined === childrenCategoryList || childrenCategoryList.length === 0) {
					$("#s_category_area").hide();
				} else {
					
					for (var index in childrenCategoryList) {
						var categoryChindId = childrenCategoryList[index];
						var categoryChildItem = CATEGORY_INFO[categoryChindId];
						var temp = "<dd><a class='child-category' category-id='"+categoryChindId+"' href='javascript:void(0)'>"+categoryChildItem.categoryName+"</a></dd>";
						$("#s_category_children").append($(temp));
					}
					
					$(".child-category").click(function() {
						searchObject.categoryId = $(this).attr("category-id");
						searchObject.pAttrObject = {};
						searchObject.sortBy = "ct";
						searchObject.direction = "desc";
						buildCategoryArea(searchObject.categoryId);
						searchProduct(searchObject);
					});
					$("#s_category_area").show();
				}
				var pAttrNameIdList = CATEGORY_ATTR_INFO[categoryId];
				getPid(categoryId);
				function getPid (pId) {
					var pattrId = CATEGORY_INFO[pId].pCategoryId;
					var ppAttrNameIdList = CATEGORY_ATTR_INFO[pattrId];
					if(undefined != ppAttrNameIdList && ppAttrNameIdList.length != 0){
						for (var index in ppAttrNameIdList) {
							var pAttrNameId = ppAttrNameIdList[index];
							pAttrNameIdList.push(pAttrNameId);
						}
					}
					if(undefined != pattrId && pattrId != 0){
						getPid(pattrId);
					}
				}
				pAttrNameIdList=uniq(pAttrNameIdList);
				function uniq(array){
				    var temp = [];
				    var index = [];
				    var l = array.length;
				    for(var i = 0; i < l; i++) {
				        for(var j = i + 1; j < l; j++){
				            if (array[i] === array[j]){
				                i++;
				                j = i;
				            }
				        }
				        temp.push(array[i]);
				        index.push(i);
				    }
				    return temp;
				}
				if (undefined === pAttrNameIdList || pAttrNameIdList.length === 0) {
					$("#attr_container").hide();
				} else {
					for (var index in pAttrNameIdList) {
						var pAttrNameId = pAttrNameIdList[index];
						var pAttrNameItem = ATTR_NAME_MAPPER[pAttrNameId];
						var temp = "<li class='select-list' style='border-top: #eee 0px dashed;'>"+
							"<dl id='select2'>"+
							"<dt class='am-badge am-round'>"+pAttrNameItem.pAttrName+"</dt>"+
							"<div class='dd-conent'>"+
							"<dd p-attr-name-id='"+pAttrNameId+"' class='attr-value-all select-all selected'><a href='javascript:void(0)'>全部</a></dd>";
							var pAttrValueList = ATTR_VALUE_MAPPER[pAttrNameId];
							for (var index in pAttrValueList) {
								var pAttrValueItem = pAttrValueList[index];
								temp += "<dd><a class='attr-value' p-attr-name-id='"+pAttrNameId+"' p-attr-value-id='"+pAttrValueItem.pAttrValueId+"' href='javascript:void(0)'>"+pAttrValueItem.pAttrValue+"</a></dd>";
							}
						
						temp += "</div></dl></li>";
						
						$("#attr_container").append($(temp));
						
						$(".attr-value").click(function() {
							if (undefined === searchObject.pAttrObject) {
								searchObject.pAttrObject = {};
							}
							searchObject.pAttrObject[$(this).attr("p-attr-name-id")] = $(this).attr("p-attr-value-id");
							$(this).parent().addClass("selected").siblings().removeClass("selected");
							searchProduct(searchObject);
						});
						
						$(".attr-value-all").click(function() {
							if (undefined === searchObject.pAttrObject) {
								searchObject.pAttrObject = {};
							}
							searchObject.pAttrObject[$(this).attr("p-attr-name-id")] = "";
							$(this).addClass("selected").siblings().removeClass("selected");
							searchProduct(searchObject);
						});
						
					}
					$("#attr_container").show();
				}
				
			}
			
			function searchProduct(searchObject) {
				$.ajax({
					type:"POST",
					url:"../product/searchProductInfo",
					async:true,
					data: JSON.stringify(searchObject),
					contentType: "application/json",
					success: function(res) {
						
						var resObj = JSON.parse(res);
	 					console.log(resObj);
						$("#product_container").html("");
						for (var index in resObj.Frecords) {
							var productItem = resObj.Frecords[index];
							var salesvolume=$("#hotprosalevalume").html();
							if(productItem.salesVolume >= salesvolume){
								document.getElementById('hotprosalevalume').innerText = productItem.salesVolume;
								document.getElementById('hotproname').innerText = productItem.productName;
								document.getElementById('hotproprice').innerText = productItem.showPrice;
								$("#hotproId").val(productItem.productId);
								$("#hotproimage").attr("src",productItem.imgCover);
								$("#hotproduct").on("click",function(){
								var productId = $("#hotproId").val();
								console.log(productId);
									var newLink = $('<a href="detail/'+productId+'.html" target="_blank" id="openWin"></a>');
									$('body').append(newLink);
									document.getElementById("openWin").click();
									$('#openWin').remove();
								});
							}
							var productItemStr = 
							"<li product-id='"+productItem.productId+"' class='product-item'>"+
							"<div class='i-pic limit'>"+
							"<img product-id='"+productItem.productId+"' class='product-img' src='.."+productItem.imgCover+"' />"+
							"<p product-id='"+productItem.productId+"' class='product-name title fl'>"+productItem.productName+"</p>"+
							"<p class='price fl'>"+
							"<b>¥</b>"+
							"<strong>"+productItem.showPrice+"</strong>"+
							"</p>"+
							"<p class='number fl'>"+
							"销量<span>"+productItem.salesVolume+"</span>"+
							"</p>"+
							"</div>"+
							"</li>";
							
							$("#product_container").append($(productItemStr));
						
						}
						
						$(".product-item").click(function() {
							var productId = $(this).attr("product-id");
							var newLink = $('<a href="detail/'+productId+'.html" target="_blank" id="openWin"></a>');
							$('body').append(newLink);
							document.getElementById("openWin").click();
							$('#openWin').remove();
						});
						
						
						
						$("#product_totals").html(resObj.total);
						
						var pageNumber = Math.ceil(resObj.total / 20);
						
						$("#current_page").val(searchObject.page / 20 + 1);
						
						$("#pages").val(pageNumber);
						
						$("#page_container").html("");
						
						var frontPage = $('<li id="front_page"><a href="javascript:void(0)">&laquo;</a></li>');
						$("#page_container").append(frontPage);
						frontPage.click(function() {
							var currentPage = parseInt($("#current_page").val());
							if (1 === currentPage) {
								return;
							} else if (currentPage > 1) {
								searchObject.page = (currentPage - 1 -1)*20;
								searchProduct(searchObject);
							} else {
								return;
							}
						});
						for (var index = 1; index < pageNumber + 1; index++) {
							//class="am-active"
							
							var pageItem = $('<li id="page_item_'+index+'" current-page="'+index+'" class="page-item"><a href="javascript:void(0)">'+index+'</a></li>');
							if (1 === index) {
								pageItem.addClass("am-active");
							}
							$("#page_container").append(pageItem);
						}

						var backPage = $('<li id="back_page"><a href="javascript:void(0)">&raquo;</a></li>');
						$("#page_container").append(backPage);
						backPage.click(function() {
							var currentPage = parseInt($("#current_page").val());
							console.log(currentPage);
							var pages = parseInt($("#pages").val());
							if (pages === currentPage) {
								return;
							} else if (currentPage < pages) {
								searchObject.page = (currentPage + 1 -1)*20;
								searchProduct(searchObject);
							} else {
								return;
							}
						});
						
						$(".page-item").click(function() {
							var attrCurrentPage = parseInt($(this).attr("current-page"));
							searchObject.page = (attrCurrentPage-1)*20;			
							searchProduct(searchObject);
							$("#page_item_"+attrCurrentPage).addClass("am-active").siblings().removeClass("am-active");
							
						});
						var qqq = searchObject.page/20 + 1;
						$("#page_item_"+qqq).addClass("am-active").siblings().removeClass("am-active");
						
						checkPage();
					},
					error: function(err) {
						
					}
				});
			}
			
			function checkPage() {
				var currentPage = parseInt($("#current_page").val());
				var pages = parseInt($("#pages").val());
				
				if (1 === currentPage) {
					$("#front_page").addClass("am-disabled");
				} else if (currentPage > 1) {
					$("#front_page").removeClass("am-disabled");
				}
				
				
				if (pages === currentPage) {
					$("#back_page").addClass("am-disabled");
				} else if (currentPage < pages) {
					$("#back_page").removeClass("am-disabled");
				}
			}
			
			function buildBrandArea(brandId) {
				var selectedAll = "";
				if (undefined === brandId || "" === brandId.trim()) {
					selectedAll = "selected";
				}
				var allTemp = "<dd class='brand-all "+selectedAll+"'><a href='javascript:void(0);'>全部</a></dd>";
				$("#brand_container").append($(allTemp));
				for (var index in BRAND_LIST) {
					var brandItem = BRAND_LIST[index];
					var brandTemp = "<dd brand-id='"+brandItem.brandId+
						"' class='brand-item'><a href='javascript:void(0)'>"+
						brandItem.brandName+
						"</a></dd>";
					$("#brand_container").append($(brandTemp));
				}
				
				$(".brand-all").click(function() {
					searchObject.brandId = "";
					$(this).addClass("selected").siblings().removeClass("selected");
					searchProduct(searchObject);
				});
				
				$(".brand-item").click(function() {
					searchObject.brandId = $(this).attr("brand-id");
					$(this).addClass("selected").siblings().removeClass("selected");
					searchProduct(searchObject);
				});
			}
			
			function findParentCategory(categoryId, dataSource) {
				var categoryItem = CATEGORY_INFO[categoryId];
				
				if (undefined === categoryItem) {
					return dataSource;
				}
				
				dataSource.unshift(categoryItem);
				
				if (categoryItem.pCategoryId == 0) {
					return dataSource;
				} else {
					return findParentCategory(categoryItem.pCategoryId, dataSource);
				}
			}
			
		</script>
	</head>

	<body>
		<#include "../common/top.ftl">

		<div class="clear"></div>
		<b class="line"></b>
    	<div class="search">
			<div class="search-list">
				<div class="nav-table">
					<div class="nav-cont" style="padding-left: 0 !important;">
						<li id="header_category_container" class="index">
							
						</li>
					</div>
				</div>
				<div class="am-g am-g-fixed">
					<div class="am-u-sm-12 am-u-md-12">
					<div  style="display: none;" id="panel">
			          	<div class="theme-popover">														
							<div class="searchAbout"></div>
							<ul id="s_category_area" class="select">
								<li id="s_category_container" class="select-list" style="border-top: #eee 0px dashed;">
									<dl>
										<dt id="s_category" class="am-badge am-round"></dt>
										<div id="s_category_children" class="dd-conent">
											
										</div>
									</dl>
								</li>
							</ul>
							<ul id="attr_container" class="select">
								
							</ul>
							
							<ul class="select">
								<li class="select-list" style="border-top: #eee 0px dashed;">
									<dl>
										<dt class="am-badge am-round">品牌</dt>
										<div id="brand_container" class="dd-conent">
										</div>
									</dl>
								</li>
							</ul>						
							<div class="clear"></div>
			            </div>
			         </div>
			            <p class="slide"><a href="javascript:;" class="btn-slide active">查询条件</a></p> 
			            	<ul class="select">
								<p class="title font-normal"><span class="total fl">共搜索到<strong id="product_totals" class="num">997</strong>件相关商品</span></p>
							</ul>
						<div class="search-content">
							<!-- $(".search-content").children(".sort").children("li") -->
							<div class="sort">
								<li sort-id="ct" option="ASC" class="first"><a href="javascript:void(0)" title="综合">综合排序</a></li>
								<li sort-id="showPrice" option="ASC" ><a href="javascript:void(0)" title="价格">价格优先</a></li>
								<li sort-id="salesVolume" option="ASC" ><a href="javascript:void(0)" title="销量">销量排序</a></li>
							</div>
							<div class="clear"></div>
		
							<ul id="product_container" class="am-avg-sm-2 am-avg-md-3 am-avg-lg-4 boxes">
								
				
							</ul>
						</div>
						<div class="search-side"  id="hotproduct">
							<div class="side-title">热卖商品</div>
							<li>
								<div class="i-pic check">
									<img id="hotproimage" />
									<p class="check-title" id="hotproname"></p>
									<p class="price fl"><b>¥</b><strong id="hotproprice"></strong></p>
									<p class="number fl">销量<span id="hotprosalevalume"></span></p>
									<input id="hotproId" type="hidden" value="0" />
								</div>
							</li>
						</div>
						<div class="clear"></div>
						<!--分页 -->
						<input id="current_page" type="hidden" value="1" />
						<input id="pages" type="hidden" value="0" />
						<ul id="page_container" class="am-pagination am-pagination-right">
							
						</ul>
		
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

</div>

		<!--引导
		<div class="navCir">
			<li><a href="../b2c/index.html"><i class="am-icon-home "></i>首页</a></li>
			<li><a href="sort.html"><i class="am-icon-list"></i>分类</a></li>
			<li><a href="../product/shopping/showCart.html"><i class="am-icon-shopping-basket"></i>购物车</a></li>	
			<li><a href="../person/index.html"><i class="am-icon-user"></i>我的</a></li>					
		</div>
		 -->
		<!--菜单 -->
		<div class=tip>
			<div id="sidebar">
				<div id="wrap">
					<div id="prof" class="item">
						<a href="/b2c/front/client.html">
							<span class="setting"></span>
						</a>
					</div>
					<div id="shopCart" class="item" onclick="window.open('../product/shopping/showCart.html')">
						<a href="#">
							<span class="message"></span>
						</a>
						<p>
							购物车
						</p>
					</div>
					<!--回到顶部 -->
					<div id="quick_links_pop" class="quick_links_pop hide"></div>

				</div>

			</div>
			<div id="prof-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					我
				</div>
			</div>
			<div id="shopCart-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					购物车
				</div>
			</div>
			<div id="asset-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					资产
				</div>

				<div class="ia-head-list">
					<a href="#" target="_blank" class="pl">
						<div class="num">0</div>
						<div class="text">优惠券</div>
					</a>
					<a href="#" target="_blank" class="pl">
						<div class="num">0</div>
						<div class="text">红包</div>
					</a>
					<a href="#" target="_blank" class="pl money">
						<div class="num">￥0</div>
						<div class="text">余额</div>
					</a>
				</div>

			</div>
			<div id="foot-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					足迹
				</div>
			</div>
			<div id="brand-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					收藏
				</div>
			</div>
			<div id="broadcast-content" class="nav-content">
				<div class="nav-con-close">
					<i class="am-icon-angle-right am-icon-fw"></i>
				</div>
				<div>
					充值
				</div>
			</div>
		</div>
		
		
	</body>

</html>