package com.kontro.beans;

public class PropriedadesBean {
	private String jdbcClassName;
	private String url;
	private String username;
	private String password;
	private String server;
	private String app;
	private String tabelaNome;
	private String colunaNome;
	private String colunaValor;
	private String[] tabelasRecvoz;
	private String[] tabelasParametros;
	boolean listaParametro = false;
	
	public String[] getTabelasParametros() {
		return tabelasParametros;
	}
	public void setTabelasParametros(String[] tabelasParametros) {
		this.tabelasParametros = tabelasParametros;
	}
	public boolean isListaParametro() {
		return listaParametro;
	}
	public void setListaParametro(boolean listaParametro) {
		this.listaParametro = listaParametro;
	}
	public String[] getTabelasRecvoz() {
		return tabelasRecvoz;
	}
	public void setTabelasRecvoz(String[] tabelasRecvoz) {
		this.tabelasRecvoz = tabelasRecvoz;
	}
	public String getTabelaNome() {
		return tabelaNome;
	}
	public void setTabelaNome(String tabelaNome) {
		this.tabelaNome = tabelaNome;
	}
	public String getJdbcClassName() {
		return jdbcClassName;
	}
	public void setJdbcClassName(String jdbcClassName) {
		this.jdbcClassName = jdbcClassName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getColunaNome() {
		return colunaNome;
	}
	public void setColunaNome(String colunaNome) {
		this.colunaNome = colunaNome;
	}
	public String getColunaValor() {
		return colunaValor;
	}
	public void setColunaValor(String colunaValor) {
		this.colunaValor = colunaValor;
	}
	public PropriedadesBean(String jdbcClassName, String url, String username, String password, String server,
			String app, String tabelaNome, String colunaNome, String colunaValor, String[] tabelasRecvoz, String[] tabelasParametros, Boolean isListaParametro) {
		super();
		this.jdbcClassName = jdbcClassName;
		this.url = url;
		this.username = username;
		this.password = password;
		this.server = server;
		this.app = app;
		this.tabelaNome = tabelaNome;
		this.colunaNome = colunaNome;
		this.colunaValor = colunaValor;
		this.tabelasRecvoz = tabelasRecvoz;
		this.tabelasParametros = tabelasParametros;
		this.listaParametro = isListaParametro;
	}
}
