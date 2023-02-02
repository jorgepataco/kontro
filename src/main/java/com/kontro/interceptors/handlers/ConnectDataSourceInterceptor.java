package com.kontro.interceptors.handlers;

import java.io.IOException;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kontro.controller.ParametrosController;
import com.kontro.controller.PromptsController;
import com.kontro.datasources.services.parametros.DBContextHolderParametros;
import com.kontro.datasources.services.parametros.InterceptorTableNameParametros;
import com.kontro.entities.parametros.Parametro;
//import com.kontro.datasources.DataSourceContextHolder;
import com.kontro.enums.DataSourceEnum;

@Component
public class ConnectDataSourceInterceptor implements HandlerInterceptor {
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	public SessionFactory getSessionFactory() {
		System.out.println("getSessionFactory");
	    if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
	        throw new NullPointerException("factory is not a hibernate factory");
	    }
	    return entityManagerFactory.unwrap(SessionFactory.class);
	}
	
	
	public Session getSessionWithInterceptor(InterceptorTableNameParametros interceptor) 
	  throws IOException {
	    return getSessionFactory().withOptions().interceptor(interceptor).openSession();
	}
	
	private Boolean containsControllerServiceKontro(HandlerMethod method){
		if(method.getBeanType().equals(PromptsController.class)) 
			return true;
		else if(method.getBeanType().equals(ParametrosController.class)) 
			return true;
		else 
			return false;
	}
	
	private DataSourceEnum getDataSourceEnum(Map<String, String> values){
		if(values.containsKey("app") && values.containsKey("server")){
			String app = values.get("app");
			String server = values.get("server");
			return DataSourceEnum.valueOf(String.join("_", server, app));
		}
		
		return DataSourceEnum.hml_uvercenter;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		if (handler instanceof HandlerMethod) {
		    HandlerMethod method = (HandlerMethod) handler;
		    if(containsControllerServiceKontro(method)){
		    	Map<String, String> values = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		    	DBContextHolderParametros.setCurrentDb(getDataSourceEnum(values));
		    }
		}
//		Session session = getSessionWithInterceptor(new InterceptorTableNameParametros());
//		Parametro tx = session.st
//		System.out.println(tx);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) throws Exception {
	}
}
