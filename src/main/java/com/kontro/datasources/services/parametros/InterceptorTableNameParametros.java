package com.kontro.datasources.services.parametros;

import org.hibernate.EmptyInterceptor;
import org.springframework.util.StringUtils;

import com.kontro.enums.DataSourceEnum;
import com.kontro.utils.TableName;

public class InterceptorTableNameParametros extends EmptyInterceptor{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String onPrepareStatement(String sql) {
		try {
			DataSourceEnum dataSourceEnum = DBContextHolderParametros.getCurrentDb();
			
			String nomeTabela = dataSourceEnum.getNomeTabela();
			if(!StringUtils.isEmpty(nomeTabela) && !nomeTabela.equals(TableName.PARAMETROS)){
				sql = sql.replace(TableName.PARAMETROS, nomeTabela);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return sql;
	}

}
