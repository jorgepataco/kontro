package com.kontro.beans;

import java.util.List;

public class ColunaBean {
	private String nomeColuna;
	private List<Object> valores;
	public String getNomeColuna() {
		return nomeColuna;
	}
	public void setNomeColuna(String nomeColuna) {
		this.nomeColuna = nomeColuna;
	}
	public List<Object> getValores() {
		return valores;
	}
	public void setValores(List<Object> valores) {
		this.valores = valores;
	}
	@Override
	public String toString() {
		return "ColunaBean [nomeColuna=" + nomeColuna + ", valores=" + valores + "]";
	}
	
	
}
