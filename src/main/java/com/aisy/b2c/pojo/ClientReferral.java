package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "client_referral")
public class ClientReferral {
    @Id
    @Column(name = "referral_code")
    private String referralCode;

    @Id
    @Column(name = "client_id")
    private Short clientId;

    @Column(name = "is_available")
    private Boolean isAvailable;

    @Column(name = "used_client_id")
    private Short usedClientId;

    private Date ct;

    /**
     * @return referral_code
     */
    public String getReferralCode() {
        return referralCode;
    }

    /**
     * @param referralCode
     */
    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode == null ? null : referralCode.trim();
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
     * @return used_client_id
     */
    public Short getUsedClientId() {
        return usedClientId;
    }

    /**
     * @param usedClientId
     */
    public void setUsedClientId(Short usedClientId) {
        this.usedClientId = usedClientId;
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
}