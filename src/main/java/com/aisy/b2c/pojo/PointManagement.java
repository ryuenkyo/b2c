package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "point_management")
public class PointManagement {
    @Id
    @Column(name = "pm_id")
    private Short pmId;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "point_type")
    private String pointType;

    @Column(name = "point_reason")
    private String pointReason;

    private Integer point;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    /**
     * @return pm_id
     */
    public Short getPmId() {
        return pmId;
    }

    /**
     * @param pmId
     */
    public void setPmId(Short pmId) {
        this.pmId = pmId;
    }

    /**
     * @return is_available
     */
    public Boolean getIsAvailable() {
        return isAvailable;
    }

    /**
     * @param isAvailable
     */
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    /**
     * @return point_type
     */
    public String getPointType() {
        return pointType;
    }

    /**
     * @param pointType
     */
    public void setPointType(String pointType) {
        this.pointType = pointType == null ? null : pointType.trim();
    }

    /**
     * @return point_reason
     */
    public String getPointReason() {
        return pointReason;
    }

    /**
     * @param pointReason
     */
    public void setPointReason(String pointReason) {
        this.pointReason = pointReason == null ? null : pointReason.trim();
    }

    /**
     * @return point
     */
    public Integer getPoint() {
        return point;
    }

    /**
     * @param point
     */
    public void setPoint(Integer point) {
        this.point = point;
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