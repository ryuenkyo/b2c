package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "s_role")
public class SRole {
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "role_id")
    private Short roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_des")
    private String roleDes;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

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
     * @return role_name
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * @return role_des
     */
    public String getRoleDes() {
        return roleDes;
    }

    /**
     * @param roleDes
     */
    public void setRoleDes(String roleDes) {
        this.roleDes = roleDes == null ? null : roleDes.trim();
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