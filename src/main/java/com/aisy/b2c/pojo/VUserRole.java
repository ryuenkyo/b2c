package com.aisy.b2c.pojo;

public class VUserRole extends SUser {

    private String cuName;

    private Short roleId;

    private String roleName;

    /**
     * @return cu_name
     */
    public String getCuName() {
        return cuName;
    }

    /**
     * @param cuName
     */
    public void setCuName(String cuName) {
        this.cuName = cuName == null ? null : cuName.trim();
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
}