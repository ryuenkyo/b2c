package com.aisy.b2c.pojo;

/**
 * 货架新增字段
 * 
 * @author YanqingLiu
 *
 */
public class VStorageSUser extends Storage{
	private String cuName;
	private String statusName;

    public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

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

}
