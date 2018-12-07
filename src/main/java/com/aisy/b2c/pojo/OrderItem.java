package com.aisy.b2c.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "order_item")
public class OrderItem {
    @Id
    @Column(name = "order_item_id")
    private Short orderItemId;

    @Id
    @Column(name = "order_id")
    private Short orderId;
    
    @Column(name = "sku_collection")
    private String skuCollection;
    
    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_show_price")
    private BigDecimal productShowPrice;

    @Column(name = "sku_price")
    private BigDecimal productPrice;

    @Column(name = "sub_total")
    private BigDecimal subTotal;

    private Short number;

    private String remark;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    /**
     * @return order_item_id
     */
    public Short getOrderItemId() {
        return orderItemId;
    }

    /**
     * @param orderItemId
     */
    public void setOrderItemId(Short orderItemId) {
        this.orderItemId = orderItemId;
    }

    /**
     * @return order_id
     */
    public Short getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Short orderId) {
        this.orderId = orderId;
    }

   
    /**
     * @return product_name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * @return product_show_price
     */
    public BigDecimal getProductShowPrice() {
        return productShowPrice;
    }

    /**
     * @param productShowPrice
     */
    public void setProductShowPrice(BigDecimal productShowPrice) {
        this.productShowPrice = productShowPrice;
    }

  

    /**
     * @return sub_total
     */
    public BigDecimal getSubTotal() {
        return subTotal;
    }

    /**
     * @param subTotal
     */
    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    /**
     * @return number
     */
    public Short getNumber() {
        return number;
    }

    /**
     * @param number
     */
    public void setNumber(Short number) {
        this.number = number;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * @return cu
     */
    public Short getCu() {
        return cu;
    }

    /**
     * @param cu
     */
    public void setCu(Short cu) {
        this.cu = cu;
    }

    /**
     * @return ct
     */
    public Date getCt() {
        return ct;
    }

    /**
     * @param ct
     */
    public void setCt(Date ct) {
        this.ct = ct;
    }

    /**
     * @return uu
     */
    public Short getUu() {
        return uu;
    }

    /**
     * @param uu
     */
    public void setUu(Short uu) {
        this.uu = uu;
    }

    /**
     * @return ut
     */
    public Date getUt() {
        return ut;
    }

	public String getSkuCollection() {
		return skuCollection;
	}

	public void setSkuCollection(String skuCollection) {
		this.skuCollection = skuCollection;
	}

	public BigDecimal getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	/**
     * @param ut
     */
    public void setUt(Date ut) {
        this.ut = ut;
    }
}