package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "emnu")
public class Emnu {
    @Id
    @Column(name = "emnu_id")
    private Short emnuId;

    @Id
    @Column(name = "emnu_type")
    private String emnuType;

    @Id
    @Column(name = "emnu_name")
    private String emnuName;

    @Column(name = "emnu_value")
    private String emnuValue;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    /**
     * @return emnu_id
     */
    public Short getEmnuId() {
        return emnuId;
    }

    /**
     * @param emnuId
     */
    public void setEmnuId(Short emnuId) {
        this.emnuId = emnuId;
    }

    /**
     * @return emnu_type
     */
    public String getEmnuType() {
        return emnuType;
    }

    /**
     * @param emnuType
     */
    public void setEmnuType(String emnuType) {
        this.emnuType = emnuType == null ? null : emnuType.trim();
    }

    /**
     * @return emnu_name
     */
    public String getEmnuName() {
        return emnuName;
    }

    /**
     * @param emnuName
     */
    public void setEmnuName(String emnuName) {
        this.emnuName = emnuName == null ? null : emnuName.trim();
    }

    /**
     * @return emnu_value
     */
    public String getEmnuValue() {
        return emnuValue;
    }

    /**
     * @param emnuValue
     */
    public void setEmnuValue(String emnuValue) {
        this.emnuValue = emnuValue == null ? null : emnuValue.trim();
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