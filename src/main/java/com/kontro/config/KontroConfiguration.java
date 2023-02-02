package com.kontro.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kontro.beans.AppBean;
import com.kontro.beans.header.HeaderConfigProperties;
import com.kontro.config.impl.FragmentsNamesImpl;
import com.kontro.config.impl.HeaderNavImpl;
import com.kontro.config.impl.MsgKontroImpl;
import com.kontro.config.impl.PathsKontroImpl;
import com.kontro.config.interfaces.FragmentsNames;
import com.kontro.config.interfaces.HeaderNav;
import com.kontro.config.interfaces.MsgKontro;
import com.kontro.config.interfaces.PathsKontro;
import com.kontro.services.PropriedadesService;
import com.kontro.utils.business.Common;

@Configuration
public class KontroConfiguration {
	
	@Autowired
	private Environment env;
	
	@Bean("appsTabelas")
	public List<AppBean> getListaApps() throws JsonMappingException, JsonProcessingException {
		final ObjectMapper fieldMapper = new ObjectMapper();
		String jsonApps = env.getProperty("apps");
		JsonNode node = fieldMapper.readTree(jsonApps);
		JavaType type = fieldMapper.getTypeFactory().constructCollectionType(List.class, AppBean.class);
		List<AppBean> apps = fieldMapper.readValue(node.get("aplicacoes").toString(), type);
		return apps;
	}
	
	@Bean("serversTabelas")
	public Map<String, String> getListaServers() throws JsonMappingException, JsonProcessingException {
		Map<String, String> servers = new HashMap<String, String>();
		servers.put(Common.HML_LABEL, Common.HML_VALUE);
		servers.put(Common.PRD_LABEL, Common.PRD_VALUE);
		return servers;
	}
	
	@Autowired
	HeaderConfigProperties headerConfigProperties;
	
	@Bean(name = "headerInfo")
	public HeaderConfigProperties testeT() {
		return headerConfigProperties;
	}
	
	@Bean(name = "headerNav")
	public HeaderNav getConfiguracoesHeader() {
		return new HeaderNavImpl();
	}
	
	@Bean(name = "paths")
	public PathsKontro gePàths() {
		return new PathsKontroImpl();
	}
	
	@Bean(name = "msg")
	public MsgKontro getMsgs() {
		return new MsgKontroImpl();
	}
	
	@Bean(name = "fragments")
	public FragmentsNames getFragments() {
		return new FragmentsNamesImpl();
	}
	
	@Autowired
	PropriedadesService service;
	
//	@Bean(name = "headerNav")
//    public HeaderNav getConfiguracoesHeader() {
//        return () -> service.getListaApps();
//    }
	
//	@Bean(name = "urls")
//	public UrlService2 urlService() {
//		return new UrlService2();
//    }
	
	@Bean
	public MessageSource messageSource() {
	    ReloadableResourceBundleMessageSource messageSource
	      = new ReloadableResourceBundleMessageSource();
	    
	    String[] basenames = {"classpath:/messages", "classpath:/application"};
	    messageSource.setBasenames(basenames);
	    //messageSource.setBasename("classpath:urls");
	    messageSource.setDefaultEncoding("UTF-8");
	    return messageSource;
	}
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    bean.setValidationMessageSource(messageSource());
	    return bean;
	}

}
