package com.aisy.b2c.pojo;

/**
 * statusName 枚举表的值
 * emnuName 枚举表的名字
 * @author Alan_Liu
 *
 */
public class VClient extends Client {

    private String statusName;
    
    private String emnuName;
    
	public String getStatusName() {
		return statusName;
	}
	
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public String getEmnuName() {
		return emnuName;
	}
	
	public void setEmnuName(String emnuName) {
		this.emnuName = emnuName;
	}
	
	@Override
	public String toString() {
		return "VClient [statusName=" + statusName + ", emnuName=" + emnuName + ", getStatusName()=" + getStatusName()
				+ ", getEmnuName()=" + getEmnuName() + ", getHeaderImg()=" + getHeaderImg() + ", getClientId()="
				+ getClientId() + ", getClientName()=" + getClientName() + ", getNickName()=" + getNickName()
				+ ", getAge()=" + getAge() + ", getTelphone()=" + getTelphone() + ", getPassword()=" + getPassword()
				+ ", getCu()=" + getCu() + ", getCt()=" + getCt() + ", getUu()=" + getUu() + ", getUt()=" + getUt()
				+ ", toString()=" + super.toString() + ", getStatus()=" + getStatus() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}
	
}
