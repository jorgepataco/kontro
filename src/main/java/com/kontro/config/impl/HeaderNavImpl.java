package com.kontro.config.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.kontro.beans.AppBean;
import com.kontro.config.interfaces.HeaderNav;
import com.kontro.services.PropriedadesService;

public class HeaderNavImpl implements HeaderNav{
	
	@Autowired
	PropriedadesService service;
	
	@Override
	public List<AppBean> getHeader() {
		// TODO Auto-generated method stub
		return service.getListaApps();
	}
}
