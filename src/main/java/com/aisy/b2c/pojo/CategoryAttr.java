package com.aisy.b2c.pojo;

import javax.persistence.*;

@Table(name = "category_attr")
public class CategoryAttr {
    @Id
    @Column(name = "category_id")
    private Short categoryId;

    @Id
    @Column(name = "p_attr_name_id")
    private Short pAttrNameId;

    /**
     * @return category_id
     */
    public Short getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId
     */
    public void setCategoryId(Short categoryId) {
        this.categoryId = categoryId;
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
}