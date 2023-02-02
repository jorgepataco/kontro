package com.kontro.enums;

import com.kontro.utils.TableName;

public enum DataSourceEnum {

	hml_fibra, 
	hml_uvercenter, 
	hml_raco, 
	hml_tv, 
	hml_oitotal, 
	hml_emp, 
	hml_fixa,
	hml_102(TableName.PARAMETROS_102),
	hml_pos,
	hml_shortcode(TableName.PARAMETROS_SC),
	hml_cadastro(TableName.PARAMETROS_CADASTRO),
	hml_672,
	hml_ouvidoria,
	hml_padigital;
	
	private String nomeTabela = TableName.PARAMETROS;
	
	private DataSourceEnum() {
	}
	
	private DataSourceEnum(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	public String getNomeTabela() {
		return nomeTabela;
	}
	
}
