package com.aisy.b2c.pojo;

import javax.persistence.*;

@Table(name = "product_category")
public class ProductCategory {
    @Id
    @Column(name = "product_id")
    private Short productId;

    @Id
    @Column(name = "category_id")
    private Short categoryId;

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
}