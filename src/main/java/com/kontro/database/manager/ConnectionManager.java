package com.kontro.database.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionManager {
	
	public static Connection createJdbcConnection(String jdbcClassName, String url, String user, String password) throws SQLException {
		try {
			Class.forName(jdbcClassName);
			DriverManager.setLoginTimeout(1);
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException | ClassNotFoundException | RuntimeException e) {
			//TODO Criar Exceptions da Aplicacao
			throw new SQLException("Fail Creating Connection From JDBC " + url, e);
		}
	}
	
	public Connection getAppDatabaseConnection(String jdbcClassName, 
			String url, String username, String pass) throws SQLException {
		return createJdbcConnection(jdbcClassName, //
				url, //
				username, //
				pass//
		);
	}
	
	public PreparedStatement prepareStatement(Connection connection, String query, int queryTimeout) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setQueryTimeout(queryTimeout);
		return statement;
	}
	
	public void closePreparedStatement(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (Exception e) {
		}
	}

	public void closeConnection(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
		}
	}

}
