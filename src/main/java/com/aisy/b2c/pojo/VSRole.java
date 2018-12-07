package com.aisy.b2c.pojo;

import java.io.Serializable;

public class VSRole extends SRole implements Serializable {

	private static final long serialVersionUID = -8330785684899316355L;
	
	private String cuName;

	public String getCuName() {
		return cuName;
	}

	public void setCuName(String cuName) {
		this.cuName = cuName;
	}
	
}
