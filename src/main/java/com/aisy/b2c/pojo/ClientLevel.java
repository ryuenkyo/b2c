package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "client_level")
public class ClientLevel {
    @Id
    private Short level;

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "level_point")
    private Integer levelPoint;

    private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    /**
     * @return level
     */
    public Short getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public void setLevel(Short level) {
        this.level = level;
    }

    /**
     * @return level_name
     */
    public String getLevelName() {
        return levelName;
    }

    /**
     * @param levelName
     */
    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    /**
     * @return level_point
     */
    public Integer getLevelPoint() {
        return levelPoint;
    }

    /**
     * @param levelPoint
     */
    public void setLevelPoint(Integer levelPoint) {
        this.levelPoint = levelPoint;
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