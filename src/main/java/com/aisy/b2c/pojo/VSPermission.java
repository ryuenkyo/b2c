package com.aisy.b2c.pojo;

import java.io.Serializable;

import javax.persistence.Column;

public class VSPermission extends SPermission implements Serializable {
	
	private static final long serialVersionUID = 6593695514238170110L;
	
	private String cuName;
	
	 @Column(name = "parent_name")
	private String parentName;
	

	public String getCuName() {
		return cuName;
	}

	public void setCuName(String cuName) {
		this.cuName = cuName;
	}
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
}
