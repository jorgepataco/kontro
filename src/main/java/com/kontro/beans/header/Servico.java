package com.kontro.beans.header;

import java.util.List;

import org.springframework.context.annotation.PropertySource;

@PropertySource(value ="classpath:application.properties" ,encoding = "UTF-8")
public class Servico {
	
	private String nome;
	private List<Server> servers;

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Server> getServers() {
		return servers;
	}
	public void setServers(List<Server> servers) {
		this.servers = servers;
	}
}
