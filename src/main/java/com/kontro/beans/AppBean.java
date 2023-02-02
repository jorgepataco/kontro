package com.kontro.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppBean {
	
	@JsonProperty("labelApp")
	private String label;
	@JsonProperty("nomeApp")
	private String nome;
	@JsonProperty("servicosApp")
	private List<String> servicos;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<String> getServicos() {
		return servicos;
	}
	public void setServicos(List<String> servicos) {
		this.servicos = servicos;
	}
	@Override
	public String toString() {
		return "AppBean [label=" + label + ", nome=" + nome + ", servicos=" + servicos + "]";
	}
	
}
