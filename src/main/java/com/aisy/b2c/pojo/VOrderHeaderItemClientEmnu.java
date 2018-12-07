package com.aisy.b2c.pojo;

import java.math.BigDecimal;

public class VOrderHeaderItemClientEmnu extends OrderHeader {
	private String productName;
	
	private BigDecimal productShowPrice;
	
	private BigDecimal skuPrice;
	
	private BigDecimal subTotal;
	
	private int number;
	
	private String skuCollection;
	
	private String clientName;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductShowPrice() {
		return productShowPrice;
	}

	public void setProductShowPrice(BigDecimal productShowPrice) {
		this.productShowPrice = productShowPrice;
	}

	public BigDecimal getSkuPrice() {
		return skuPrice;
	}

	public void setSkuPrice(BigDecimal skuPrice) {
		this.skuPrice = skuPrice;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getSkuCollection() {
		return skuCollection;
	}

	public void setSkuCollection(String skuCollection) {
		this.skuCollection = skuCollection;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
}
