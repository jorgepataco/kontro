package com.kontro.database.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kontro.beans.ColunaBean;
import com.kontro.beans.PropriedadesBean;
import com.kontro.beans.ReqNewValues;
import com.kontro.beans.TabelaBean;
import com.kontro.entities.parametros.Parametro;

public class ApplicationDatabaseDAO {
	
	private Connection connection;
	private int QUERY_TIMEOUT = 3;
	private static final ConnectionManager connectionManager = new ConnectionManager();
	
	
	public ApplicationDatabaseDAO(String jdbcClassName, 
			String url, String username, String pass, int qUERY_TIMEOUT) throws SQLException {
		super();
		this.connection = connectionManager.getAppDatabaseConnection(jdbcClassName, url, username, pass);
		QUERY_TIMEOUT = qUERY_TIMEOUT;
	}


	public List<Parametro> getParametros(String tabelaNome, String colunaNome, String colunaValor) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		List<Parametro> retorno = new ArrayList<Parametro>();
		try {
			
			String query = "SELECT * FROM ".concat(tabelaNome);
			selectStatement = connectionManager.prepareStatement(this.connection, query, QUERY_TIMEOUT);
			resultSet = selectStatement.executeQuery();
			while (resultSet.next()) {
				Parametro parametroURA = new Parametro();
				String name = resultSet.getString(colunaNome);
				String value = resultSet.getString(colunaValor);
				
				parametroURA.setName(name);
				parametroURA.setValue(value);
				
				retorno.add(parametroURA);
			}
				
				
		} catch (SQLException e) {
			throw e;
		} finally {
			connectionManager.closePreparedStatement(selectStatement);
			connectionManager.closePreparedStatement(updateStatement);
			connectionManager.closeConnection(connection);
		}
		
		return retorno;
	}
	
	public void addParametro(PropriedadesBean propriedadesBean, String nome, String valor) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		PreparedStatement updateStatement = null;
		try {
			
			String query = "INSERT INTO  "
					+ propriedadesBean.getTabelaNome()
					+ "("
					+ propriedadesBean.getColunaNome()
					+ ","
					+ propriedadesBean.getColunaValor()
					+ ") VALUES (?,?)";
			
			selectStatement = connectionManager.prepareStatement(this.connection, query, QUERY_TIMEOUT);
			selectStatement.setString(1, nome);
			selectStatement.setString(2, valor);
			
			selectStatement.executeUpdate();
			
			this.connection.commit();
				
		} catch (SQLException e) {
			throw e;
		} finally {
			connectionManager.closePreparedStatement(selectStatement);
			connectionManager.closePreparedStatement(updateStatement);
			connectionManager.closeConnection(connection);
		}
	}
	
	public void deleteParametro(PropriedadesBean propriedadesBean, String nome) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		PreparedStatement updateStatement = null;
		try {
			
			String query = "DELETE FROM  "
					+ propriedadesBean.getTabelaNome()
					+ " WHERE "
					+ propriedadesBean.getColunaNome()
					+ " = ?";
			
			selectStatement = connectionManager.prepareStatement(this.connection, query, QUERY_TIMEOUT);
			selectStatement.setString(1, nome);
			
			selectStatement.executeUpdate();
			
			this.connection.commit();
				
		} catch (SQLException e) {
			throw e;
		} finally {
			connectionManager.closePreparedStatement(selectStatement);
			connectionManager.closePreparedStatement(updateStatement);
			connectionManager.closeConnection(connection);
		}
	}
	
	public void attParametro(PropriedadesBean propriedadesBean, String nome, String valor) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		PreparedStatement updateStatement = null;
		try {
			
			String query = "UPDATE  "
					+ propriedadesBean.getTabelaNome()
					+ " SET "
					+ propriedadesBean.getColunaValor()
					+" = ?"
					+ " WHERE "
					+ propriedadesBean.getColunaNome()
					+ " = ?";
			System.out.println(query);
			selectStatement = connectionManager.prepareStatement(this.connection, query, QUERY_TIMEOUT);
			selectStatement.setString(1, valor);
			selectStatement.setString(2, nome);
			selectStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
		} finally {
			connectionManager.closePreparedStatement(selectStatement);
			connectionManager.closePreparedStatement(updateStatement);
			connectionManager.closeConnection(connection);
		}
	}
	
	public void attValoresRecvoz(ReqNewValues newValues) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		PreparedStatement updateStatement = null;
		try {
			String nomeTabela = newValues.getNomeTabela();
			List<String> listaColunasModificadas = new ArrayList<String>();
			List<String> listaValoresModificados = new ArrayList<String>();
			for (ColunaBean col : newValues.getTdsModificado()) {
				listaColunasModificadas.add(col.getNomeColuna().concat(" = ?"));
				listaValoresModificados.add(col.getValores().get(0).toString());
			}
			
			String setValues = String.join(",", listaColunasModificadas);
			String colunas = String.join(",", newValues.getColunas());
			
			String query = "UPDATE "
					.concat(nomeTabela).concat(" SET ")
					.concat(setValues)
					.concat(" WHERE (")
					.concat(colunas)
					.concat(") = (")
					.concat("SELECT ")
					.concat(colunas)
					.concat(" FROM (")
					.concat("SELECT ROWNUM AS RN, P.* FROM ")
					.concat(nomeTabela)
					.concat(" P) ")
					.concat("WHERE rn = ?)");
			
			System.out.println(query);
			selectStatement = connectionManager.prepareStatement(this.connection, query, QUERY_TIMEOUT);
			int idx = 1;
			
			for (String valor : listaValoresModificados) {
				selectStatement.setString(idx, valor);
				idx = idx + 1;
			}
			selectStatement.setInt(idx, newValues.getRownum());
			selectStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw e;
		} finally {
			connectionManager.closePreparedStatement(selectStatement);
			connectionManager.closePreparedStatement(updateStatement);
			connectionManager.closeConnection(connection);
		}
	}
	
	public TabelaBean getValoresTableRecvoz(String tabelaNome) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		TabelaBean bean = null;
		
		try {
			System.out.println(tabelaNome);
			String query = "SELECT * FROM ".concat(tabelaNome);
			System.out.println(query);
			selectStatement = connectionManager.prepareStatement(this.connection, query, QUERY_TIMEOUT);
			resultSet = selectStatement.executeQuery();
			
			ResultSetMetaData rsmd = resultSet.getMetaData();
			 List<ColunaBean> colunas = new ArrayList<ColunaBean>();
			
			int numColumns = rsmd.getColumnCount();
			  
			for (int i=1; i<=numColumns; i++) {
			  String column_name = rsmd.getColumnName(i);
			  ColunaBean colunaBean = new ColunaBean();
			  colunaBean.setNomeColuna(column_name);
			  colunaBean.setValores(new ArrayList<Object>());
			  colunas.add(colunaBean);
			}
			
			int qtdLinhas = 0;
			while (resultSet.next()) {
			  qtdLinhas = qtdLinhas + 1;	
			  for (int i = 0; i < colunas.size(); i++) {
				  String column_name = colunas.get(i).getNomeColuna();
				  colunas.get(i).getValores().add(resultSet.getObject(column_name));
			  }
			}
			
			if(colunas.size() > 0){
				bean = new TabelaBean();
				bean.setNomeTabela(tabelaNome);
				bean.setColunas(colunas);
				bean.setQtdLinhas(qtdLinhas);
				bean.setQtdColunas(numColumns);
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			connectionManager.closePreparedStatement(selectStatement);
			connectionManager.closePreparedStatement(updateStatement);
			connectionManager.closeConnection(connection);
		}
		
		return bean;
	}
	
	public TabelaBean getTypeTabela(String owner, String nomeTabela) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		TabelaBean bean = null;
		
		try {
			String query = "SELECT COLUMN_NAME, DATA_TYPE FROM "
					+ "ALL_TAB_COLUMNS "
					+ "WHERE OWNER = ? AND TABLE_NAME = ?";

			
			selectStatement = connectionManager.prepareStatement(this.connection, query, QUERY_TIMEOUT);
			selectStatement.setString(1, owner);
			selectStatement.setString(2, nomeTabela);
			resultSet = selectStatement.executeQuery();
			
			ResultSetMetaData rsmd = resultSet.getMetaData();
			 List<ColunaBean> colunas = new ArrayList<ColunaBean>();
			
			int numColumns = rsmd.getColumnCount();
			  
			for (int i=1; i<=numColumns; i++) {
			  String column_name = rsmd.getColumnName(i);
			  ColunaBean colunaBean = new ColunaBean();
			  colunaBean.setNomeColuna(column_name);
			  colunaBean.setValores(new ArrayList<Object>());
			  colunas.add(colunaBean);
			}
			
			int qtdLinhas = 0;
			while (resultSet.next()) {
			  qtdLinhas = qtdLinhas + 1;	
			  for (int i = 0; i < colunas.size(); i++) {
				  String column_name = colunas.get(i).getNomeColuna();
				  colunas.get(i).getValores().add(resultSet.getObject(column_name));
			  }
			}
			
			if(colunas.size() > 0){
				bean = new TabelaBean();
				bean.setNomeTabela("TYPES");
				bean.setColunas(colunas);
				bean.setQtdLinhas(qtdLinhas);
				bean.setQtdColunas(numColumns);
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			connectionManager.closePreparedStatement(selectStatement);
			connectionManager.closePreparedStatement(updateStatement);
			connectionManager.closeConnection(connection);
		}
		
		return bean;
	}
	
	public TabelaBean getListaTabelas(String owner) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		TabelaBean bean = null;
		
		try {
			String query = "SELECT TABLE_NAME FROM all_tables WHERE OWNER = ?";

			
			selectStatement = connectionManager.prepareStatement(this.connection, query, QUERY_TIMEOUT);
			selectStatement.setString(1, owner.toUpperCase());
			resultSet = selectStatement.executeQuery();
			
			ResultSetMetaData rsmd = resultSet.getMetaData();
			 List<ColunaBean> colunas = new ArrayList<ColunaBean>();
			
			int numColumns = rsmd.getColumnCount();
			  
			for (int i=1; i<=numColumns; i++) {
			  String column_name = rsmd.getColumnName(i);
			  ColunaBean colunaBean = new ColunaBean();
			  colunaBean.setNomeColuna(column_name);
			  colunaBean.setValores(new ArrayList<Object>());
			  colunas.add(colunaBean);
			}
			
			int qtdLinhas = 0;
			while (resultSet.next()) {
			  qtdLinhas = qtdLinhas + 1;	
			  for (int i = 0; i < colunas.size(); i++) {
				  String column_name = colunas.get(i).getNomeColuna();
				  colunas.get(i).getValores().add(resultSet.getObject(column_name));
			  }
			}
			
			if(colunas.size() > 0){
				bean = new TabelaBean();
				bean.setNomeTabela("TABELAS");
				bean.setColunas(colunas);
				bean.setQtdLinhas(qtdLinhas);
				bean.setQtdColunas(numColumns);
			}
				
		} catch (SQLException e) {
			throw e;
		} finally {
			connectionManager.closePreparedStatement(selectStatement);
			connectionManager.closePreparedStatement(updateStatement);
			connectionManager.closeConnection(connection);
		}
		
		return bean;
	}
	
	public String getTabelasNLU(String tabelaNome, String colunaNome, String colunaValor) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement selectStatement = null;
		PreparedStatement updateStatement = null;
		ResultSet resultSet = null;
		String retorno = "";
		try {
			
			String query = "SELECT * FROM ".
					concat(tabelaNome).
					concat(" WHERE ".concat(colunaNome).
					concat(" = ?"));
			selectStatement = connectionManager.prepareStatement(this.connection, query, QUERY_TIMEOUT);
			selectStatement.setString(1, "paramNLUTabelas");
			resultSet = selectStatement.executeQuery();
			System.out.println("pataco");
			System.out.println(query);
			while (resultSet.next()) {
				retorno = resultSet.getString(colunaValor);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			connectionManager.closePreparedStatement(selectStatement);
			connectionManager.closePreparedStatement(updateStatement);
			connectionManager.closeConnection(connection);
		}
		
		return retorno;
	}

}
