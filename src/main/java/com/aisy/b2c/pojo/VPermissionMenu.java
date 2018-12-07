package com.aisy.b2c.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VPermissionMenu extends SPermission implements Serializable {

	private static final long serialVersionUID = 3817358427817607866L;
	
	private List<VPermissionMenu> children = new ArrayList<VPermissionMenu>();
    
    public List<VPermissionMenu> getChildren() {
    	return this.children;
    }

}
