package com.aisy.b2c.pojo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "storage")
public class Storage {
    @GeneratedValue(generator = "JDBC")
    @Id
    @Column(name = "storage_id")
    private Short storageId;

    @Column(name = "storage_name")
    private String storageName;

    @Column(name = "storage_des")
    private String storageDes;

    private String status;
    
    @Column(name = "sort_index")
    private Integer sortIndex;

	private Short cu;

    private Date ct;

    private Short uu;

    private Date ut;

    /**
     * @return storage_id
     */
    public Short getStorageId() {
        return storageId;
    }

    /**
     * @param storageId
     */
    public void setStorageId(Short storageId) {
        this.storageId = storageId;
    }

    /**
     * @return storage_name
     */
    public String getStorageName() {
        return storageName;
    }

    /**
     * @param storageName
     */
    public void setStorageName(String storageName) {
        this.storageName = storageName == null ? null : storageName.trim();
    }

    /**
     * @return storage_des
     */
    public String getStorageDes() {
        return storageDes;
    }

    /**
     * @param storageDes
     */
    public void setStorageDes(String storageDes) {
        this.storageDes = storageDes == null ? null : storageDes.trim();
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Integer getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
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