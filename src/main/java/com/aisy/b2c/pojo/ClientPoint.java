package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "client_point")
public class ClientPoint {
    @Id
    @Column(name = "client_id")
    private Short clientId;

    private Integer point;

    @Column(name = "used_point")
    private Integer usedPoint;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

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
     * @return used_point
     */
    public Integer getUsedPoint() {
        return usedPoint;
    }

    /**
     * @param usedPoint
     */
    public void setUsedPoint(Integer usedPoint) {
        this.usedPoint = usedPoint;
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