package com.aisy.b2c.model;

import java.io.Serializable;
import java.util.Map;

public class RoleModel implements Serializable {
	private static final long serialVersionUID = 3408523440662668181L;
	public String roleName;
	public String roleDes;
	public Map<String, Short> permission;
	public Map<String, Object> updateRole;
}
