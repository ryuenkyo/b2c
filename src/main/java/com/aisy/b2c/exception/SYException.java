package com.aisy.b2c.exception;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SYException extends RuntimeException {
	private static final long serialVersionUID = 2644851094665838577L;
	private String messageBody;
	private Exception e;
	private Map<String, Object> param;
	
	public SYException(String messageBody) {
		this.messageBody = messageBody;
	}
	
	public SYException(String messageBody, Map<String, Object> param) {
		this.messageBody = messageBody;
		this.param = param;
	}
	
	public SYException(String messageBody, Exception e) {
		this.messageBody = messageBody;
		this.e = e;
	}
	
	public SYException(Exception e) {
		this.messageBody = e.getMessage();
		this.e = e;
	}
	
	public String getMessageBody() {
		return this.messageBody;
	}
	
	public Exception getException() {
		return this.e;
	}
	
	public Map<String, Object> getParam() {
		return this.param;
	}
}
