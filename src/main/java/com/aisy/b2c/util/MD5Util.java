package com.aisy.b2c.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static String encoderByMd5(String str) throws NoSuchAlgorithmException {
		
		byte[] secretBytes = MessageDigest.getInstance("md5").digest(  
                str.getBytes()); 
		
		String md5code = new BigInteger(1, secretBytes).toString(16);
		
		for (int i = 0; i < 32 - md5code.length(); i++) {  
            md5code = "0" + md5code;  
        }
		return md5code;
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException {  
        System.out.println(encoderByMd5("123456"));
        System.out.println(encoderByMd5("123456"));
    } 
}
