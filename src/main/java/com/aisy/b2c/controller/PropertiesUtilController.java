package com.aisy.b2c.controller;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtilController {
	  private static Properties p = new Properties();  
	  
	    /** 
	     * 读取properties配置文件信息 
	     */  
	    static{  
	        try {  
	            p.load(PropertiesUtilController.class.getClassLoader().getResourceAsStream("global.properties"));  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	    /** 
	     * 根据key得到value的值 
	     */  
	    public static String getValue(String key)  
	    {  
	        return p.getProperty(key);  
	    }  
	}  

