package com.kontro.beans;

import java.util.List;

public class TabelaBean {
	
	private String nomeTabela;
	private int qtdLinhas = 0;
	private int qtdColunas = 0;
	private List<ColunaBean> colunas;
	public String getNomeTabela() {
		return nomeTabela;
	}
	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}
	public List<ColunaBean> getColunas() {
		return colunas;
	}
	public void setColunas(List<ColunaBean> colunas) {
		this.colunas = colunas;
	}
	public int getQtdLinhas() {
		return qtdLinhas;
	}
	public void setQtdLinhas(int qtdLinhas) {
		this.qtdLinhas = qtdLinhas;
	}
	public int getQtdColunas() {
		return qtdColunas;
	}
	public void setQtdColunas(int qtdColunas) {
		this.qtdColunas = qtdColunas;
	}
	@Override
	public String toString() {
		return "TabelaBean [nomeTabela=" + nomeTabela + ", qtdLinhas=" + qtdLinhas + ", qtdColunas=" + qtdColunas
				+ ", colunas=" + colunas + "]";
	}
	
}
