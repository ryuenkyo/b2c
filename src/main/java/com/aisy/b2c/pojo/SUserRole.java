package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "s_user_role")
public class SUserRole {
    @Id
    @Column(name = "user_id")
    private Short userId;

    @Id
    @Column(name = "role_id")
    private Short roleId;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    /**
     * @return user_id
     */
    public Short getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Short userId) {
        this.userId = userId;
    }

    /**
     * @return role_id
     */
    public Short getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Short roleId) {
        this.roleId = roleId;
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