package com.aisy.b2c.pojo;

import javax.persistence.*;

@Table(name = "p_attr_name")
public class PAttrName {
	
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "p_attr_name_id")
    private Short pAttrNameId;

    @Column(name = "p_attr_name")
    private String pAttrName;

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
     * @return p_attr_name
     */
    public String getpAttrName() {
        return pAttrName;
    }

    /**
     * @param pAttrName
     */
    public void setpAttrName(String pAttrName) {
        this.pAttrName = pAttrName == null ? null : pAttrName.trim();
    }
}