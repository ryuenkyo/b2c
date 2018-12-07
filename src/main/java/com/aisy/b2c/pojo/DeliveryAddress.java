package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "delivery_address")
public class DeliveryAddress {
    @Id
    @Column(name = "address_id")
    private Short addressId;

    @Column(name = "client_id")
    private Short clientId;

    @Column(name = "recive_name")
    private String reciveName;

    private String telphone;

    @Column(name = "telphone_backup")
    private String telphoneBackup;

    @Column(name = "p_code")
    private Short pCode;

    @Column(name = "c_code")
    private Short cCode;

    @Column(name = "a_code")
    private Short aCode;

    private String street;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "is_default")
    private Boolean isDefault;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    /**
     * @return address_id
     */
    public Short getAddressId() {
        return addressId;
    }

    /**
     * @param addressId
     */
    public void setAddressId(Short addressId) {
        this.addressId = addressId;
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
     * @return recive_name
     */
    public String getReciveName() {
        return reciveName;
    }

    /**
     * @param reciveName
     */
    public void setReciveName(String reciveName) {
        this.reciveName = reciveName == null ? null : reciveName.trim();
    }

    /**
     * @return telphone
     */
    public String getTelphone() {
        return telphone;
    }

    /**
     * @param telphone
     */
    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    /**
     * @return telphone_backup
     */
    public String getTelphoneBackup() {
        return telphoneBackup;
    }

    /**
     * @param telphoneBackup
     */
    public void setTelphoneBackup(String telphoneBackup) {
        this.telphoneBackup = telphoneBackup == null ? null : telphoneBackup.trim();
    }

    /**
     * @return p_code
     */
    public Short getpCode() {
        return pCode;
    }

    /**
     * @param pCode
     */
    public void setpCode(Short pCode) {
        this.pCode = pCode;
    }

    /**
     * @return c_code
     */
    public Short getcCode() {
        return cCode;
    }

    /**
     * @param cCode
     */
    public void setcCode(Short cCode) {
        this.cCode = cCode;
    }

    /**
     * @return a_code
     */
    public Short getaCode() {
        return aCode;
    }

    /**
     * @param aCode
     */
    public void setaCode(Short aCode) {
        this.aCode = aCode;
    }

    /**
     * @return street
     */
    public String getStreet() {
        return street;
    }

    /**
     * @param street
     */
    public void setStreet(String street) {
        this.street = street == null ? null : street.trim();
    }

    /**
     * @return zip_code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    /**
     * @return is_default
     */
    public Boolean getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault
     */
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
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