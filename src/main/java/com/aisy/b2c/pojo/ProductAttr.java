package com.aisy.b2c.pojo;

import javax.persistence.*;

@Table(name = "product_attr")
public class ProductAttr {
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "p_r_id")
    private Short pRId;

    @Id
    @Column(name = "product_id")
    private Short productId;

    @Column(name = "p_attr_name_id")
    private Short pAttrNameId;

    @Column(name = "p_attr_value_id")
    private Short pAttrValueId;

    @Column(name = "is_sku")
    private Boolean isSku;

    /**
     * @return p_r_id
     */
    public Short getpRId() {
        return pRId;
    }

    /**
     * @param pRId
     */
    public void setpRId(Short pRId) {
        this.pRId = pRId;
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
     * @return p_attr_name_id
     */
    public Short getpAttrNameId() {
        return pAttrNameId;
    }

    /**
     * @param pAttrNameId
     */
    public void setpAttrNameId(Short pAttrNameId) {
        this.pAttrNameId = pAttrNameId;
    }

    /**
     * @return p_attr_value_id
     */
    public Short getpAttrValueId() {
        return pAttrValueId;
    }

    /**
     * @param pAttrValueId
     */
    public void setpAttrValueId(Short pAttrValueId) {
        this.pAttrValueId = pAttrValueId;
    }

    /**
     * @return is_sku
     */
    public Boolean getIsSku() {
        return isSku;
    }

    /**
     * @param isSku
     */
    public void setIsSku(Boolean isSku) {
        this.isSku = isSku;
    }
}