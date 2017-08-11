package com.gome.wa.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PropertyHolder {
	
	@Value("${durid.url}")
	private String duridUrl;
	
	
	public String getDuridUrl() {
		return duridUrl;
	}
	
	
	static PropertyHolder propertyHolder;
	
	@PostConstruct  
	public void init() {  
		propertyHolder = this;  
	}  
	
	public static PropertyHolder values(){
		return propertyHolder;
	}
}
