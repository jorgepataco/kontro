package com.kontro.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kontro.beans.AppBean;

@Service
public class PropriedadesService {
	
	@Autowired
	private Environment env;
	
	private static final ObjectMapper fieldMapper = new ObjectMapper();
	
	public List<AppBean> getListaApps()  {
		
		String jsonApps = env.getProperty("apps");
		List<AppBean> apps = new ArrayList<AppBean>();
		try {
			JsonNode node = fieldMapper.readTree(jsonApps);
			JavaType type = fieldMapper.getTypeFactory().constructCollectionType(List.class, AppBean.class);
			apps = fieldMapper.readValue(node.get("aplicacoes").toString(), type);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return apps;
	}

}
