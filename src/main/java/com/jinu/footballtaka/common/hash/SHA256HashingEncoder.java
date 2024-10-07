package com.jinu.footballtaka.common.hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

@Component("sha256Hashing")
public class SHA256HashingEncoder implements HashingEncoder {
	
	public String encode(String message) {
		String result = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("sha256");
			
			byte[] bytes = message.getBytes();
			
			messageDigest.update(bytes);
			
			byte[] digest = messageDigest.digest();
			
			for(int i = 0; i < digest.length; i++) {
				
				result += Integer.toHexString(digest[i] & 0xff);
			}
			
			
		} catch (NoSuchAlgorithmException e) {
			
			return null;
		}
		
		return result;
	}

}
