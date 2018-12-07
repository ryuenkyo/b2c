package com.aisy.b2c.model;

import java.math.BigDecimal;

public class CartModel extends ProductModel {
	private static final long serialVersionUID = 2099108234666626732L;
	
	private Integer id;
	private Short skuId;
	private String skuPro;
	private Integer count;
	private Integer stock;
	private BigDecimal sum;
	private BigDecimal skuPrice;
	private Long cartUt;
	private boolean isCheck;
	
	public Long getCartUt() {
		return cartUt;
	}
	public void setCartUt(Long cartUt) {
		this.cartUt = cartUt;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	public Short getSkuId() {
		return skuId;
	}
	public void setSkuId(Short skuId) {
		this.skuId = skuId;
	}
	
	public String getSkuPro() {
		return skuPro;
	}
	public void setSkuPro(String skuPro) {
		this.skuPro = skuPro;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	public BigDecimal getSkuPrice() {
		return skuPrice;
	}
	public void setSkuPrice(BigDecimal skuPrice) {
		this.skuPrice = skuPrice;
	}
}
