package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "category_id")
    private Short categoryId;

    @Column(name = "p_category_id")
    private Short pCategoryId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_level")
    private Integer categoryLevel;

    private String status;

    @Column(name = "sort_index")
    private Short sortIndex;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

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
     * @return p_category_id
     */
    public Short getpCategoryId() {
        return pCategoryId;
    }

    /**
     * @param pCategoryId
     */
    public void setpCategoryId(Short pCategoryId) {
        this.pCategoryId = pCategoryId;
    }

    /**
     * @return category_name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * @param categoryName
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    /**
     * @return category_level
     */
    public Integer getCategoryLevel() {
        return categoryLevel;
    }

    /**
     * @param categoryLevel
     */
    public void setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * @return sort_index
     */
    public Short getSortIndex() {
        return sortIndex;
    }

    /**
     * @param sortIndex
     */
    public void setSortIndex(Short sortIndex) {
        this.sortIndex = sortIndex;
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
}