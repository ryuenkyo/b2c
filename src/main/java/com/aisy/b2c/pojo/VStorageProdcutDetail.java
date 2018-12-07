package com.aisy.b2c.pojo;

import java.math.BigDecimal;

/**
 * 
 * @author Yanqing_Liu
 *
 */
public class VStorageProdcutDetail extends StorageProducts{
    private String productName;
    private BigDecimal showPrice;
    private String status;
    private Integer salesVolume;

    public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getShowPrice() {
		return showPrice;
	}

	public void setShowPrice(BigDecimal showPrice) {
		this.showPrice = showPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

}
