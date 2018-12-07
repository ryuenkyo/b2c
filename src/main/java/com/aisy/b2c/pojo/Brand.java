package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "brand")
public class Brand {
    @Id
    @Column(name = "brand_id")
    private Short brandId;

    @Column(name = "brand_name")
    private String brandName;

    private String des;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    /**
     * @return brand_id
     */
    public Short getBrandId() {
        return brandId;
    }

    /**
     * @param brandId
     */
    public void setBrandId(Short brandId) {
        this.brandId = brandId;
    }

    /**
     * @return brand_name
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * @param brandName
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    /**
     * @return des
     */
    public String getDes() {
        return des;
    }

    /**
     * @param des
     */
    public void setDes(String des) {
        this.des = des == null ? null : des.trim();
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