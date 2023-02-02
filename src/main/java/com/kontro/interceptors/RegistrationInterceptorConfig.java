package com.kontro.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kontro.interceptors.handlers.ConnectDataSourceInterceptor;

@Component
public class RegistrationInterceptorConfig implements WebMvcConfigurer{
	
	@Autowired
	ConnectDataSourceInterceptor connectDataSourceInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(connectDataSourceInterceptor);
	}

}
