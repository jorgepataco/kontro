package com.kontro.beans;

import java.util.List;

public class ReqNewValues {
	
	private Integer rownum;
	private String nomeTabela;
	private List<String> colunas;
	private List<ColunaBean> tdsModificado;
	
	public Integer getRownum() {
		return rownum;
	}
	public void setRownum(Integer rownum) {
		this.rownum = rownum;
	}
	public List<ColunaBean> getTdsModificado() {
		return tdsModificado;
	}
	public void setTdsModificado(List<ColunaBean> tdsModificado) {
		this.tdsModificado = tdsModificado;
	}
	public String getNomeTabela() {
		return nomeTabela;
	}
	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}
	public List<String> getColunas() {
		return colunas;
	}
	public void setColunas(List<String> colunas) {
		this.colunas = colunas;
	}
	@Override
	public String toString() {
		return "ReqNewValues [rownum=" + rownum + ", nomeTabela=" + nomeTabela + ", colunas=" + colunas
				+ ", tdsModificado=" + tdsModificado + "]";
	}
}
