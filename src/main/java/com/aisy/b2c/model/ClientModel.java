package com.aisy.b2c.model;

import com.aisy.b2c.pojo.Client;
import com.aisy.b2c.pojo.ClientPoint;

public class ClientModel {
	private Client client;
	private ClientPoint clientPoint;
	
	public Client getClient() {
		return client;
	}
	public void setClient(Client client) {
		this.client = client;
	}
	public ClientPoint getClientPoint() {
		return clientPoint;
	}
	public void setClientPoint(ClientPoint clientPoint) {
		this.clientPoint = clientPoint;
	}
}
