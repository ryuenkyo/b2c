package com.aisy.b2c.pojo;

import javax.persistence.*;

@Table(name = "rc_district")
public class RcDistrict {
    /**
     * 自增id
     */
    @Id
    @Column(name = "district_id")
    private Short districtId;

    /**
     * 父及关系
     */
    private Short pid;

    /**
     * 地区名称
     */
    private String district;

    /**
     * 子属关系
     */
    private Short level;

    /**
     * 获取自增id
     *
     * @return district_id - 自增id
     */
    public Short getDistrictId() {
        return districtId;
    }

    /**
     * 设置自增id
     *
     * @param districtId 自增id
     */
    public void setDistrictId(Short districtId) {
        this.districtId = districtId;
    }

    /**
     * 获取父及关系
     *
     * @return pid - 父及关系
     */
    public Short getPid() {
        return pid;
    }

    /**
     * 设置父及关系
     *
     * @param pid 父及关系
     */
    public void setPid(Short pid) {
        this.pid = pid;
    }

    /**
     * 获取地区名称
     *
     * @return district - 地区名称
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置地区名称
     *
     * @param district 地区名称
     */
    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    /**
     * 获取子属关系
     *
     * @return level - 子属关系
     */
    public Short getLevel() {
        return level;
    }

    /**
     * 设置子属关系
     *
     * @param level 子属关系
     */
    public void setLevel(Short level) {
        this.level = level;
    }
}