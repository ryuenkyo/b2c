package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "s_permission")
public class SPermission {
    @Id
    @Column(name = "permission_id")
    private Short permissionId;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "permission_type")
    private String permissionType;

    @Column(name = "permission_context")
    private String permissionContext;

    @Column(name = "parent_id")
    private Short parentId;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    /**
     * @return permission_id
     */
    public Short getPermissionId() {
        return permissionId;
    }

    /**
     * @param permissionId
     */
    public void setPermissionId(Short permissionId) {
        this.permissionId = permissionId;
    }

    /**
     * @return permission_name
     */
    public String getPermissionName() {
        return permissionName;
    }

    /**
     * @param permissionName
     */
    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    /**
     * @return permission_type
     */
    public String getPermissionType() {
        return permissionType;
    }

    /**
     * @param permissionType
     */
    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    /**
     * @return permission_context
     */
    public String getPermissionContext() {
        return permissionContext;
    }

    /**
     * @param permissionContext
     */
    public void setPermissionContext(String permissionContext) {
        this.permissionContext = permissionContext == null ? null : permissionContext.trim();
    }

    /**
     * @return parent_id
     */
    public Short getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(Short parentId) {
        this.parentId = parentId;
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