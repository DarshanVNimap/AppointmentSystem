package com.apointmentManagementSystem.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class CacheOperationService {
	
	@Autowired
	private RedisTemplate<String, Object> template;
	
	
	
	public Boolean hasKey(String key) {
		
		return template.hasKey(key);
	}
	
	public Boolean isKeyExist(String key1 , String key2) {
		return template.opsForHash().hasKey(key1, key2);
	}

	public void addInCache(String key1 , String key2 , Object val) {
		template.opsForHash().put(key1, key2, val);
	}
	
	public Object getFromCache(String key1, String key2) {
		return  template.opsForHash().get(key1, key2);
	}
	
	public List<SimpleGrantedAuthority> getAuthority(String key1, String key2) {
		return  (List<SimpleGrantedAuthority>) template.opsForHash().get(key1, key2);
	}
	
	public void removeKeyFromCache(String key) {
		template.delete(key);
	}
	
	public void removeAllKeyStartWith() {
		
		Set<String> keys = template.keys("*");
		template.delete(keys);
		
	}


}
