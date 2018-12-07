package com.aisy.b2c.service;

import java.util.Map;

public interface ILoginService {
	public Map<String, Object> frontLogin(Map<String, Object> context);
	public Map<String, Object> systemLogin(Map<String, Object> context);
}
