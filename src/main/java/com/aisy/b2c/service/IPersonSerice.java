package com.aisy.b2c.service;

import java.util.List;
import java.util.Map;

import com.aisy.b2c.pojo.Client;


public interface IPersonSerice {

	public	List<Object> getclient (Short clientId);
	
	public	List<Object> getdeliveryAddresses (Short clientId);
	 
	public List<Object> upclient(Client from);
}
