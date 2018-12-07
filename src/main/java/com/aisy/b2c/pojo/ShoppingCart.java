package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    @Column(name = "client_id")
    private Short clientId;

    @Column(name = "sku_id")
    private Short skuId;

    private Integer number;

    private Date ct;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return client_id
     */
    public Short getClientId() {
        return clientId;
    }

    /**
     * @param clientId
     */
    public void setClientId(Short clientId) {
        this.clientId = clientId;
    }

    /**
     * @return sku_id
     */
    public Short getSkuId() {
        return skuId;
    }

    /**
     * @param sku_id
     */
    public void setSkuId(Short skuId) {
        this.skuId = skuId;
    }

    /**
     * @return number
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * @param number
     */
    public void setNumber(Integer number) {
        this.number = number;
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
}