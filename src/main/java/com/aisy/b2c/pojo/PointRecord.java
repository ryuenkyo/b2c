package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "point_record")
public class PointRecord {
    @Id
    @Column(name = "record_id")
    private Short recordId;

    @Column(name = "client_id")
    private Short clientId;

    @Column(name = "pm_id")
    private Short pmId;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    /**
     * @return record_id
     */
    public Short getRecordId() {
        return recordId;
    }

    /**
     * @param recordId
     */
    public void setRecordId(Short recordId) {
        this.recordId = recordId;
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