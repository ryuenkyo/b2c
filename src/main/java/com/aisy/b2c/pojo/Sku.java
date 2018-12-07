package com.aisy.b2c.pojo;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sku")
public class Sku {
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "sku_id")
    private Short skuId;

    @Column(name = "product_id")
    private Short productId;

    private BigDecimal price;

    private Integer stock;

    @Column(name = "sales_volume")
    private Integer salesVolume;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    @Column(name = "sku_collection")
    private String skuCollection;

    /**
     * @return sku_id
     */
    public Short getSkuId() {
        return skuId;
    }

    /**
     * @param skuId
     */
    public void setSkuId(Short skuId) {
        this.skuId = skuId;
    }

    /**
     * @return product_id
     */
    public Short getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(Short productId) {
        this.productId = productId;
    }

    /**
     * @return price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return stock
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * @param stock
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * @return sales_volume
     */
    public Integer getSalesVolume() {
        return salesVolume;
    }

    /**
     * @param salesVolume
     */
    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
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

    /**
     * @param ut
     */
    public void setUt(Date ut) {
        this.ut = ut;
    }

    /**
     * @return sku_collection
     */
    public String getSkuCollection() {
        return skuCollection;
    }

    /**
     * @param skuCollection
     */
    public void setSkuCollection(String skuCollection) {
        this.skuCollection = skuCollection == null ? null : skuCollection.trim();
    }
}