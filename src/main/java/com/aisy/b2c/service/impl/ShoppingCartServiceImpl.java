package com.aisy.b2c.service.impl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aisy.b2c.cache.CommonCache;
import com.aisy.b2c.cache.ProductCache;
import com.aisy.b2c.dao.DeliveryAddressMapper;
import com.aisy.b2c.dao.OrderHeaderMapper;
import com.aisy.b2c.dao.OrderItemMapper;
import com.aisy.b2c.dao.ProductMapper;
import com.aisy.b2c.dao.ShoppingCartMapper;
import com.aisy.b2c.dao.SkuMapper;
import com.aisy.b2c.exception.SYException;
import com.aisy.b2c.model.CartModel;
import com.aisy.b2c.model.ClientModel;
import com.aisy.b2c.pojo.DeliveryAddress;
import com.aisy.b2c.pojo.Logistics;
import com.aisy.b2c.pojo.OrderHeader;
import com.aisy.b2c.pojo.OrderItem;
import com.aisy.b2c.pojo.Pay;
import com.aisy.b2c.pojo.Product;
import com.aisy.b2c.pojo.ProductImage;
import com.aisy.b2c.pojo.ShoppingCart;
import com.aisy.b2c.pojo.Sku;
import com.aisy.b2c.pojo.VDeliveryAddress;
import com.aisy.b2c.service.IShoppingCartService;
import com.aisy.b2c.util.JsonUtil;
import com.aisy.b2c.util.Message;
import com.aisy.b2c.util.SYConst;

import net.sf.json.JSONObject;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class ShoppingCartServiceImpl implements IShoppingCartService {

	@Resource
	CommonCache COMMON_CACHE;

	@Resource
	ProductCache PRODUCT_CACHE;

	@Resource
	ShoppingCartMapper shoppingCartMapper;

	@Resource
	SkuMapper skuMapper;

	@Resource
	DeliveryAddressMapper deliveryAddressMapper;

	@Resource
	OrderItemMapper orderItemMapper;

	@Resource
	OrderHeaderMapper orderHeaderMapper;
	
	@Resource
	ProductMapper productMapper;

	@Resource(name = "message")
	Message message;

	@Override
	public Map<String, Object> getCartInfo(Map<String, Object> context) {

		Map<String, Object> result = new HashMap<String, Object>();

		Short clientId = (Short) context.get(SYConst.CLIENT_PARAM_NAME.CLIENT_ID);
		Example example = new Example(ShoppingCart.class);
		example.setOrderByClause("`ct` ASC");
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.CLIENT_PARAM_NAME.CLIENT_ID, clientId);
		List<ShoppingCart> cartList = shoppingCartMapper.selectByExample(example);
		List<CartModel> showCartList = new ArrayList<CartModel>();

		if (null != cartList && cartList.size() > 0) {
			for (ShoppingCart cart : cartList) {
				String skuIdStr = String.valueOf(cart.getSkuId());
				Sku sku = PRODUCT_CACHE.SKU_PRODUCT.get(skuIdStr);
				String productIdStr = String.valueOf(sku.getProductId());
				Product product = PRODUCT_CACHE.PRODUCT.get(productIdStr);
				Map<String, List<ProductImage>> imageMap = PRODUCT_CACHE.PRODUCT_IMAGE.get(productIdStr);
				String imageUrl = imageMap.get(SYConst.PRODUCT_PARAM.COVER).get(0).getImageUrl();

				CartModel cm = new CartModel();
				cm.setId(cart.getId());
				cm.setProductId(product.getProductId());
				cm.setSkuId(cart.getSkuId());
				cm.setSkuPro(JsonUtil.attrNameValue(sku.getSkuCollection(), PRODUCT_CACHE));

				cm.setShowPrice(product.getShowPrice());
				cm.setSkuPrice(sku.getPrice());
				cm.setCount(cart.getNumber());
				cm.setSum(sku.getPrice().multiply(new BigDecimal(cart.getNumber())));
				cm.setProductName(product.getProductName());
				cm.setCoverImageUrl(imageUrl);

				showCartList.add(cm);
			}
		}

		result.put(SYConst.CART.CART_LIST, showCartList);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);

		return result;
	}

	@Override
	public Map<String, Object> newCartItem(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();

		Short clientId = (Short) context.get(SYConst.CLIENT_PARAM_NAME.CLIENT_ID);
		Short skuId = (Short) context.get(SYConst.PRODUCT_PARAM.SKU_ID);
		Integer count = (Integer) context.get(SYConst.CART.NUMBER);

		result.put("ShoppingCart", newShoppingCart(clientId, skuId, count));
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	private ShoppingCart newShoppingCart(Short clientId, Short skuId, Integer count) {
		ShoppingCart sc = new ShoppingCart();
		sc.setClientId(clientId);
		sc.setSkuId(skuId);
		sc.setNumber(count);
		sc.setCt(new Date(System.currentTimeMillis()));
		shoppingCartMapper.insert(sc);
		return sc;
	}

	@Override
	public Map<String, Object> updateCart(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();

		Integer cartId = (Integer) context.get(SYConst.CART.CART_ID);
		Integer count = (Integer) context.get(SYConst.CART.NUMBER);
		ShoppingCart sc = new ShoppingCart();
		sc.setId(cartId);
		sc.setNumber(count);

		shoppingCartMapper.updateByPrimaryKeySelective(sc);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> deleteCartItem(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		Integer cartId = (Integer) context.get(SYConst.CART.CART_ID);
		shoppingCartMapper.deleteByPrimaryKey(cartId);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}
	/*确认订单*/
	@Override
	public Map<String, Object> confirmOrder(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		//取出购物车集合
		List<CartModel> cartList = (List<CartModel>) context.get(SYConst.CART.CART_LIST);
		ClientModel currentClient = (ClientModel) context.get(SYConst.SESSION.FRONT_LOGIN_USER);
		String cartObject = (String) context.get(SYConst.PRODUCT_PARAM.CART_OBJECT);
		String Count = (String) context.get(SYConst.PRODUCT_PARAM.COUNT);
		String skuObjectId = (String) context.get(SYConst.PRODUCT_PARAM.SKU_ID);
		//购物车已选中的商品
		JSONObject cartJsonObject = JsonUtil.stringToObj(cartObject);
		BigDecimal orderAmount = BigDecimal.valueOf(0);
		List<CartModel> confirmList = new ArrayList<CartModel>();
		Set<Short> skuIdSet = new HashSet<Short>();
		//count是只有点击立即购买才不为空，判断count是否为空判断是从购物车确认订单还是直接购买
		if (Count == null || Short.valueOf(Count) == 0) {
			//循环购物车集合，将购物车里选中提交的商品放到订单集合
			for (Iterator<CartModel> it = cartList.iterator(); it.hasNext();) {
				CartModel cm = it.next();
				Short skuId = cm.getSkuId();
				if (cartJsonObject.containsKey(String.valueOf(skuId))
						&& cartJsonObject.getBoolean(String.valueOf(skuId))) {
	                    BigDecimal sum = cm.getSum();
	                    orderAmount =  orderAmount.add(sum);
	                    confirmList.add(cm);
	                    //将符合条件的skuid放入skuid集合中
						skuIdSet.add(skuId);
				}
			}
		} else {
			Long currentDateLong = System.currentTimeMillis();
			Date currentDate = new Date(System.currentTimeMillis());
			CartModel cm = new CartModel();
			//根据立即购买传来的skuid放入订单集合
			String skuIdStr = String.valueOf(skuObjectId);
			Sku skuProduct = PRODUCT_CACHE.SKU_PRODUCT.get(skuIdStr);
			String productIdStr = String.valueOf(skuProduct.getProductId());
			Sku sku = skuMapper.selectByPrimaryKey(skuObjectId);
			Product product = PRODUCT_CACHE.PRODUCT.get(productIdStr);
			String productId = String.valueOf(product.getProductId());
			Map<String, List<ProductImage>> imageMap = PRODUCT_CACHE.PRODUCT_IMAGE.get(productId);
			String imageUrl = imageMap.get(SYConst.PRODUCT_PARAM.COVER).get(0).getImageUrl();
			cm.setProductId(product.getProductId());
			cm.setSkuId(Short.valueOf(skuObjectId));
			cm.setSkuPro(JsonUtil.attrNameValue(sku.getSkuCollection(), PRODUCT_CACHE));
			cm.setShowPrice(product.getShowPrice());
			cm.setSkuPrice(sku.getPrice());
			cm.setCount(Integer.valueOf(Count));
			cm.setSum(sku.getPrice().multiply(new BigDecimal(Count)));
			cm.setProductName(product.getProductName());
			cm.setCoverImageUrl(imageUrl);
			cm.setCartUt(currentDateLong);
			cm.setUt(currentDate);
			orderAmount = sku.getPrice().multiply(new BigDecimal(Count));
			confirmList.add(cm);
			skuIdSet.add(Short.valueOf(skuObjectId));
		}

		if (0 == confirmList.size()) {
			throw new SYException("数据出现错误, 请联系管理员！");
		}

		Example example = new Example(Sku.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("skuId", skuIdSet);
		//根据skuid集合取出库存集合
		List<Sku> skuList = skuMapper.selectByExample(example);
		Map<String, Sku> skuMap = new HashMap<String, Sku>();
		for (Iterator<Sku> it = skuList.iterator(); it.hasNext();) {
			Sku sku = it.next();
			//将取出的skuid作为key，sku对象作为值放入map中
			skuMap.put(String.valueOf(sku.getSkuId()), sku);
		}

		/* 回写 confirmList 最新的库存 和最新的价格 最新的最后更新时间 */
		for (Iterator<CartModel> it = confirmList.iterator(); it.hasNext();) {
			CartModel cm = it.next();
			Sku sku = skuMap.get(String.valueOf(cm.getSkuId()));

			if (cm.getCount().intValue() > sku.getStock().intValue()) {
				throw new SYException(cm.getProductName() + ":" + cm.getSkuPro() + " " + "库存不够了");
			}

			cm.setUt(sku.getUt());
			java.util.Date ut = sku.getUt();
			Long time = ut.getTime();
			cm.setCartUt(time);
			cm.setStock(sku.getStock());
		}
		result.put("confirmList", confirmList);
		result.put("orderAmount", orderAmount);

		/* 收货地址 物流方式 */
		Example exampleAddress = new Example(DeliveryAddress.class);
		Criteria criteriaAddress = exampleAddress.createCriteria();
		criteriaAddress.andEqualTo("clientId", currentClient.getClient().getClientId());
		List<DeliveryAddress> addressList = deliveryAddressMapper.selectByExample(exampleAddress);
		List<VDeliveryAddress> vAddressList = new ArrayList<VDeliveryAddress>();
		for (Iterator<DeliveryAddress> it = addressList.iterator(); it.hasNext();) {
			DeliveryAddress da = it.next();
			VDeliveryAddress vda = new VDeliveryAddress();
			BeanUtils.copyProperties(da, vda);
			vda.setProviceName(COMMON_CACHE.DISTRICT_MAP.get(String.valueOf(da.getpCode())).getDistrict());
			vda.setCityName(COMMON_CACHE.DISTRICT_MAP.get(String.valueOf(da.getcCode())).getDistrict());
			vda.setAreaName(COMMON_CACHE.DISTRICT_MAP.get(String.valueOf(da.getaCode())).getDistrict());
			vda.setStreet(da.getStreet());
			vAddressList.add(vda);
		}

		result.put("addressList", vAddressList);
		List<Pay> payList = COMMON_CACHE.PAY_LIST;
		result.put("payList", payList);
		List<Logistics> logisticsList = COMMON_CACHE.LOGISTICS_LIST;
		result.put("logisticsList", logisticsList);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> order(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		/* 同步购物车 */
		List<CartModel> cartList = (List<CartModel>) context.get(SYConst.CART.CART_LIST);
		ClientModel currentClient = (ClientModel) context.get(SYConst.SESSION.FRONT_LOGIN_USER);
		Date currentDate = new Date(System.currentTimeMillis());
		if(cartList!=null) {
			for (Iterator<CartModel> it = cartList.iterator(); it.hasNext();) {
				CartModel cm = it.next();
				ShoppingCart newSc = this.newShoppingCart(currentClient.getClient().getClientId(), cm.getSkuId(),
						cm.getCount());
				cm.setId(newSc.getId());
			}
		}
		// 减缓存
		String skuObject = (String) context.get(SYConst.PRODUCT_PARAM.SKU_OBJECT);
		String objextObject = (String) context.get(SYConst.PRODUCT_PARAM.OTHER_OBJECT);

		JSONObject skuJsonObject = JsonUtil.stringToObj(skuObject);
		JSONObject objextIsonObject = JsonUtil.stringToObj(objextObject);

		List<Short> skuList = new ArrayList<Short>();
		List<OrderItem> orderItemList = new ArrayList<OrderItem>();
		Map<String,Object> countMap = new HashMap<String,Object>();
		Map<String,Object> skuProMap = new HashMap<String,Object>();
		//新建一个值，利用循环增加数量的方式取到订单里商品的总数量。
        int a  = 0;
		/* 现取的库存的方式减库存 */
		for (Iterator<Entry<String, Object>> it = skuJsonObject.entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> entry = it.next();
			String skuIdStr = entry.getKey();
			Map<String, Object> skuMap = (Map<String, Object>) entry.getValue();
			Integer count = (Integer) skuMap.get(SYConst.PRODUCT_PARAM.COUNT);
			String skuPro = (String) skuMap.get(SYConst.PRODUCT_PARAM.SKU_PRO);
			Long ut =(Long)skuMap.get(SYConst.PRODUCT_PARAM.UT);
			a+=count;
			countMap.put(skuIdStr, count);
			skuProMap.put(skuIdStr, skuPro);
			Sku skuDB = skuMapper.selectByPrimaryKey(Short.valueOf(skuIdStr));
			if (ut == skuDB.getUt().getTime()) {
				skuDB.setStock(skuDB.getStock() - count);
				skuDB.setUt(currentDate);
			} else {
				if (skuDB.getStock() >= count) {
					skuDB.setStock(skuDB.getStock() - count);
					skuDB.setUt(currentDate);
				} else {
					throw new SYException("库存不足");
				}
			}
			int resultCount = skuMapper.updateByPrimaryKeySelective(skuDB);
			if (0 == resultCount) {
				throw new SYException("下单失败");
			}

			skuList.add(Short.valueOf(skuIdStr));

		}

		Example example = new Example(ShoppingCart.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.CLIENT_PARAM_NAME.CLIENT_ID,1);
		criteria.andIn(SYConst.PRODUCT_PARAM.SKU_ID, skuList);
		shoppingCartMapper.deleteByExample(example);
		Short zero = Short.valueOf((short) 0);	
		//将前台传来的信息填入orderheader表里。
		Map<String, Object> payMap = (Map<String, Object>)objextIsonObject.get("payObject");
		//支付方式
		String payName = (String)payMap.get("payObject");
		Map<String, Object> logisticsMap = (Map<String, Object>)objextIsonObject.get("logisticsObject");
		//物流方式
		String logisticsName = (String)logisticsMap.get("logisticsObject");
		Map<String, Object> addressMap = (Map<String, Object>)objextIsonObject.get("addressObject");
		//邮寄地址id
		Integer addressId = (Integer)addressMap.get("addressObject");
		Map<String, Object> productPrice = (Map<String, Object>)objextIsonObject.get("productPrice");
		//订单总价
		Object amount=productPrice.get("productPrice");
		Map<String, Object> message = (Map<String, Object>)objextIsonObject.get("message");
		//客户备注
		DeliveryAddress da = deliveryAddressMapper.selectByPrimaryKey(addressId);
		String clientLog = (String)message.get("message");
		OrderHeader orderHeader = new OrderHeader();
		orderHeader.setAddress(JsonUtil.deliveryAddress(da, COMMON_CACHE));
		String amountStr=String.valueOf(amount);
		BigDecimal amountBigdecimal = new BigDecimal(amountStr);
		orderHeader.setAmount(amountBigdecimal);
		orderHeader.setClientId(currentClient.getClient().getClientId());
		orderHeader.setClientLog(clientLog);
		orderHeader.setLogisticsAmount(BigDecimal.valueOf(zero));
		orderHeader.setLogisticsType(logisticsName);
		orderHeader.setPayChannel(payName);
		orderHeader.setOrderStatus("待支付");
		orderHeader.setOrderTime(currentDate);
		orderHeader.setPayTime(currentDate);
		orderHeader.setProductAmount(amountBigdecimal);
		orderHeader.setProductNum(Short.valueOf(String.valueOf(a)));
		orderHeader.setUt(currentDate);
		orderHeader.setUu(currentClient.getClient().getClientId());
		orderHeader.setCt(currentDate);
		orderHeader.setCu(currentClient.getClient().getClientId());
		orderHeader.setFlag(Short.valueOf("1"));
		orderHeader.setClientName(currentClient.getClient().getClientName());
		orderHeader.setTel(currentClient.getClient().getTelphone());
		orderHeaderMapper.insert(orderHeader);
		//取到新增订单的订单id
        Short orderId = orderHeader.getOrderId();
		BigDecimal productAmount = BigDecimal.valueOf(0);
		//循环订单里面具体的商品写入订单详细表里
		for (Iterator<Short> it = skuList.iterator(); it.hasNext();) {
			Short skuId = it.next();
			OrderItem orderItem = new OrderItem();
			Sku sku = PRODUCT_CACHE.SKU_PRODUCT.get(String.valueOf(skuId));
			Short productId = sku.getProductId();
			Integer count = (Integer)countMap.get(String.valueOf(skuId));
			String skuPro = (String)skuProMap.get(String.valueOf(skuId));
			Product product = PRODUCT_CACHE.PRODUCT.get(String.valueOf(productId));
			Sku sKuBase = new Sku();
			sKuBase.setSkuId(Short.valueOf(skuId));
			sKuBase.setSalesVolume(sku.getSalesVolume()+count);
			skuMapper.updateByPrimaryKeySelective(sKuBase);
			Product productBase = new Product();
			productBase.setProductId(productId);
			productBase.setSalesVolume(product.getSalesVolume()+count);
			productMapper.updateByPrimaryKeySelective(productBase);
			orderItem.setNumber(count.shortValue());
			orderItem.setProductName(product.getProductName());
			orderItem.setOrderId(orderId);
			orderItem.setSkuCollection(JsonUtil.attrNameValue(sku.getSkuCollection(), PRODUCT_CACHE));
			orderItem.setProductName(product.getProductName());
			orderItem.setProductPrice(sku.getPrice());
			orderItem.setProductShowPrice(product.getShowPrice());
			orderItem.setRemark("");
			BigDecimal price = sku.getPrice();
			BigDecimal multiply = price.multiply(BigDecimal.valueOf(count));
			BigDecimal pAmount = product.getShowPrice().multiply(BigDecimal.valueOf(count));
			productAmount=productAmount.add(pAmount);
			orderItem.setSubTotal(multiply);
			orderItem.setSkuCollection(skuPro);
			orderItem.setCt(currentDate);
			orderItem.setCu(currentClient.getClient().getClientId());
			orderItem.setUt(currentDate);
			orderItem.setUu(currentClient.getClient().getClientId());
			orderItemList.add(orderItem);
			orderItemMapper.insert(orderItem);
		}
		//将商品展示价格总价写入订单表里
		OrderHeader updateOrderHeader = new OrderHeader();
		updateOrderHeader.setOrderId(orderId);
		updateOrderHeader.setProductAmount(productAmount);
		orderHeaderMapper.updateByPrimaryKeySelective(updateOrderHeader);
		String countMapString = countMap.toString();
		result.put("countMapString", countMapString);
		result.put("orderId", orderId);
		result.put("total", amount);
		
		return result;
	}

	@Transactional
	@Override
	public Map<String, Object> updateBack(Map<String, Object> context) {
		Short clientId = (Short) context.get(SYConst.CLIENT_PARAM_NAME.CLIENT_ID);
		List<CartModel> cartList = (List<CartModel>) context.get(SYConst.CART.CART_LIST);

		Example example = new Example(ShoppingCart.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(SYConst.CLIENT_PARAM_NAME.CLIENT_ID, clientId);
		shoppingCartMapper.deleteByExample(example);

		if (null != cartList && cartList.size() > 0) {
			for (CartModel cm : cartList) {
				ShoppingCart sc = new ShoppingCart();
				if (null == cm.getId()) {
					sc.setClientId(clientId);
					sc.setNumber(cm.getCount());
					sc.setSkuId(cm.getSkuId());
					sc.setCt(new Date(System.currentTimeMillis()));
					shoppingCartMapper.insert(sc);
				} else {
					sc.setId(cm.getId());
					sc.setNumber(cm.getCount());
					sc.setCt(new Date(System.currentTimeMillis()));
					shoppingCartMapper.updateByPrimaryKeySelective(sc);
				}
			}
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> updateCartSession(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();

		Short skuId = (Short) context.get(SYConst.PRODUCT_PARAM.SKU_ID);
		Integer count = (Integer) context.get(SYConst.CART.NUMBER);
		List<CartModel> cartList = (List<CartModel>) (context.get(SYConst.CART.CART_LIST));
		//判断是加还是减
		String option = (String) context.get(SYConst.CART.OPTION);

		for (Iterator<CartModel> it = cartList.iterator(); it.hasNext();) {
			CartModel cm = it.next();
			if (skuId.equals(cm.getSkuId())) {
				if (SYConst.CART.MINUS.equals(option)) {
					if (cm.getCount() > 0) {
						cm.setCount(cm.getCount() - 1);
					}
				} else if (SYConst.CART.PLUS.equals(option)) {
					Sku sku = skuMapper.selectByPrimaryKey(cm.getSkuId());
					if (sku.getStock().intValue() > cm.getCount()) {
						cm.setCount(cm.getCount() + 1);
					} else {
						throw new SYException(message.MSI01);
					}
				} else {
					Sku sku = skuMapper.selectByPrimaryKey(cm.getSkuId());
					if (sku.getStock().intValue() >= count.intValue()) {
						cm.setCount(count);
					} else {
						cm.setCount(sku.getStock());
						cm.setSum(cm.getSkuPrice().multiply(BigDecimal.valueOf(cm.getCount())));
						result.put(SYConst.CART.CART_MODEL, cm);
						throw new SYException(message.MSI01, result);
					}
				}

				cm.setSum(cm.getSkuPrice().multiply(BigDecimal.valueOf(cm.getCount())));
				result.put(SYConst.CART.CART_MODEL, cm);
			}
		}

		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> newCartSession(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		Short skuId = (Short) context.get(SYConst.PRODUCT_PARAM.SKU_ID);
		Integer count = (Integer) context.get(SYConst.CART.NUMBER);
		List<CartModel> cartList = (List<CartModel>) (context.get(SYConst.CART.CART_LIST));
		ClientModel currentClient = (ClientModel) context.get(SYConst.SESSION.FRONT_LOGIN_USER);
		//判断添加购物车之前购物车集合里有没有这件商品，有的话在原基础上数量加一，没有新建进去
		boolean alreadyEx = false;
		CartModel cm = null;
		if (cartList != null) {
			for (Iterator<CartModel> it = cartList.iterator(); it.hasNext();) {
				CartModel cartM = it.next();
				if (cartM.getSkuId().equals(skuId)) {
					cartM.setCount(cartM.getCount() + count.intValue());
					cm = cartM;
					alreadyEx = true;
				}
			}
		}

		if (!alreadyEx) {
			Sku sku = skuMapper.selectByPrimaryKey(skuId);
			Date currentDate = new Date(System.currentTimeMillis());
			Long currentDateLong = System.currentTimeMillis();
			String productIdStr = String.valueOf(sku.getProductId());
			Product product = PRODUCT_CACHE.PRODUCT.get(productIdStr);
			Map<String, List<ProductImage>> imageMap = PRODUCT_CACHE.PRODUCT_IMAGE.get(productIdStr);
			String imageUrl = imageMap.get(SYConst.PRODUCT_PARAM.COVER).get(0).getImageUrl();
			cm = new CartModel();
			cm.setProductId(product.getProductId());
			cm.setSkuId(skuId);
			cm.setSkuPro(JsonUtil.attrNameValue(sku.getSkuCollection(), PRODUCT_CACHE));
			cm.setShowPrice(product.getShowPrice());
			cm.setSkuPrice(sku.getPrice());
			cm.setCount(count);
			cm.setSum(sku.getPrice().multiply(new BigDecimal(count)));
			cm.setProductName(product.getProductName());
			cm.setCoverImageUrl(imageUrl);
			cm.setCartUt(currentDateLong);
			cm.setUt(currentDate);
			cartList.add(cm);
		}
		result.put(SYConst.CART.CART_MODEL, cm);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> deleteCartSession(Map<String, Object> context) {
		Map<String, Object> result = new HashMap<String, Object>();
		Short skuId = (Short) context.get(SYConst.PRODUCT_PARAM.SKU_ID);
		List<CartModel> cartList = (List<CartModel>) (context.get(SYConst.CART.CART_LIST));
		for (Iterator<CartModel> it = cartList.iterator(); it.hasNext();) {
			CartModel cm = it.next();
			if (cm.getSkuId().equals(skuId)) {
				it.remove();
			}
		}

		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

	@Override
	public Map<String, Object> updateStatus(Map<String, Object> context) {
		//前台点击的付款或取消改变订单的状态
		Map<String, Object> result = new HashMap<String, Object>();
		String orderId =(String)context.get(SYConst.PRODUCT_PARAM.ORDER_ID);
		String status =(String)context.get(SYConst.PRODUCT_PARAM.STATUS);
		String countMap =(String)context.get(SYConst.PRODUCT_PARAM.COUNT_MAP);
		List<CartModel> cartList = (List<CartModel>) (context.get(SYConst.CART.CART_LIST));
		JSONObject countJsonObject = JsonUtil.stringToObj(countMap);
		//如果点击取消付款将库存和销量返还
		for (Iterator<Entry<String, Object>> it = countJsonObject.entrySet().iterator(); it.hasNext();) {
			Entry<String, Object> next = it.next();
			String skuId = next.getKey();
			if(status.equals("已取消")) {
				Integer count = (Integer)next.getValue();
				Sku sku = PRODUCT_CACHE.SKU_PRODUCT.get(String.valueOf(skuId));
				Short productId = sku.getProductId();
				Product product = PRODUCT_CACHE.PRODUCT.get(String.valueOf(productId));
				Sku sKuBase = new Sku();
				sKuBase.setSkuId(Short.valueOf(skuId));
				sKuBase.setSalesVolume(sku.getSalesVolume()-count);
				sKuBase.setStock(sku.getStock()+count);
				skuMapper.updateByPrimaryKeySelective(sKuBase);
				Product productBase = new Product();
				productBase.setProductId(productId);
				productBase.setSalesVolume(product.getSalesVolume()-count);
				productMapper.updateByPrimaryKeySelective(productBase);
			
			}else {
				//支付成功时删除购物车
				for (Iterator<CartModel> it1 = cartList.iterator(); it1.hasNext();) {
					CartModel cm = it1.next();
					if (cm.getSkuId()==Short.valueOf(skuId)) {
						it1.remove();
					}	
				}
			}
		}
		OrderHeader orderHeader = new OrderHeader();
		orderHeader.setOrderId(Short.valueOf(orderId));
		orderHeader.setOrderStatus(status);
		orderHeaderMapper.updateByPrimaryKeySelective(orderHeader);
		result.put(SYConst.SERVICE.STATUS, SYConst.SERVICE.SUCCESS);
		return result;
	}

}
