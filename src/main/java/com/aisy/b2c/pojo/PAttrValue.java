package com.aisy.b2c.pojo;

import javax.persistence.*;

@Table(name = "p_attr_value")
public class PAttrValue {
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "p_attr_value_id")
    private Short pAttrValueId;

    @Column(name = "p_attr_name_id")
    private Short pAttrNameId;

    @Column(name = "p_attr_value")
    private String pAttrValue;

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
     * @return p_attr_value
     */
    public String getpAttrValue() {
        return pAttrValue;
    }

    /**
     * @param pAttrValue
     */
    public void setpAttrValue(String pAttrValue) {
        this.pAttrValue = pAttrValue == null ? null : pAttrValue.trim();
    }
}