package com.kontro.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kontro.beans.PropriedadesBean;
import com.kontro.beans.TabelaBean;
import com.kontro.beans.header.HeaderConfigProperties;
import com.kontro.beans.header.ProdutosOi;
import com.kontro.database.manager.ApplicationDatabaseDAO;
import com.kontro.utils.urls.Paths;
import com.kontro.utils.urls.Views;

@RequestMapping(Paths.TABELAS)
@Controller
public class TabelasController {

	@Autowired
	HeaderConfigProperties headerConfigProperties;
	
	@Autowired
	private Environment env;
	
	private PropriedadesBean propriedadesBean;
	
	@GetMapping
	public ModelAndView viewTabelas() {
		ModelAndView mav = new ModelAndView(Views.TABELAS_ESCOLHER);
		List<String> produtos = new ArrayList<String>();
		for (ProdutosOi prd : headerConfigProperties.getProdutos()) {
			produtos.add(prd.getNome());
		}
		mav.addObject("produtos", produtos);
		return mav;
	}
	
	private PropriedadesBean getPropriedades(String app, String server) {

		String jdbcClassName = env.getProperty("oracle.hml.jdbcClassName");
		String url = env.getProperty("oracle.".concat(server).concat(".").concat(app).concat(".url"));
		String username = env.getProperty("oracle.".concat(server).concat(".").concat(app).concat(".username"));
		String password = env.getProperty("oracle.".concat(server).concat(".").concat(app).concat(".password"));
		String tabelaNome = env.getProperty("parametro.".concat(app).concat(".").concat("tabela.nome"));
		String colunaNome = env.getProperty("parametro.".concat(app).concat(".").concat("coluna.nome"));
		String colunaValor = env.getProperty("parametro.".concat(app).concat(".").concat("coluna.valor"));
		String lista = env.getProperty("parametro.".concat(app).concat(".").concat("lista"));

		Boolean isListaParametro = (lista != null && lista != "") ? Boolean.parseBoolean(lista) : false;

		String[] tabelasRecvoz = env.getProperty("recvoz.".concat(app).concat(".").concat("tabelas")) != null
				? env.getProperty("recvoz.".concat(app).concat(".").concat("tabelas")).split(";")
				: null;

		String[] tabelasParametro = env.getProperty("parametro.".concat(app).concat(".").concat("tabelas")) != null
				? env.getProperty("parametro.".concat(app).concat(".").concat("tabelas")).split(";")
				: null;

		System.out.println(tabelasRecvoz);
		System.out.println(jdbcClassName);
		System.out.println(url);
		System.out.println(username);
		System.out.println(password);
		System.out.println(server);
		System.out.println(app);
		System.out.println(tabelaNome);
		System.out.println(colunaNome);
		System.out.println(colunaValor);
		System.out.println(tabelasParametro);
		System.out.println(isListaParametro);

		return new PropriedadesBean(jdbcClassName, url, username, password, server, app, tabelaNome, colunaNome,
				colunaValor, tabelasRecvoz, tabelasParametro, isListaParametro);
	}
	
	@GetMapping(Paths.SERVER + Paths.APP)
	public ModelAndView someMethod(@PathVariable(required = true) String server,
			@PathVariable(required = true) String app, Model model)
			throws Exception {
		
		ModelAndView mav = new ModelAndView(Views.INICIO);

		this.propriedadesBean = getPropriedades(app, server);

		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);

		String owner = propriedadesBean.getUsername();

		List<TabelaBean> retorno = new ArrayList<TabelaBean>();

		TabelaBean bean = dao.getListaTabelas(owner);

		if (bean != null) {
			retorno.add(bean);
		}

		mav.addObject("view_service", Views.TABELAS_APP);
		mav.addObject("app", app);
		mav.addObject("server", server);
		mav.addObject("server", server);
		mav.addObject("app", app);
		mav.addObject("tabelasRecvoz", retorno);
		return mav; // view
	}
	
	@GetMapping(Paths.SERVER + Paths.APP + Paths.TABLE + Paths.DADOS)
	public ModelAndView getRecvozServerAndApp(@PathVariable(required = true) String server,
			@PathVariable(required = true) String app, @PathVariable(required = true) String table, Model model)
			throws Exception {
		
		ModelAndView mav = new ModelAndView(Views.INICIO);
		this.propriedadesBean = getPropriedades(app, server);

		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);

		String[] tabelas = { table };

		List<TabelaBean> retorno = new ArrayList<TabelaBean>();

		for (String str : tabelas) {
			TabelaBean bean = dao.getValoresTableRecvoz(str);

			if (bean != null) {
				retorno.add(bean);
			}
		}

		mav.addObject("server", server);
		mav.addObject("app", app);
		mav.addObject("tabelasRecvoz", retorno);
		mav.addObject("view_service", Views.TABELAS_DADOS);

		return mav; // view
	}
	
	@GetMapping(Paths.SERVER + Paths.APP + Paths.TABLE + Paths.TYPE)
	public ModelAndView getTypeTabela(@PathVariable(required = true) String server, @PathVariable(required = true) String app,
			@PathVariable(required = true) String table, Model model) throws Exception {

		ModelAndView mav = new ModelAndView(Views.INICIO);
		this.propriedadesBean = getPropriedades(app, server);

		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);

		String[] tabelas = { table };
		String owner = propriedadesBean.getUsername();

		List<TabelaBean> retorno = new ArrayList<TabelaBean>();

		for (String str : tabelas) {
			TabelaBean bean = dao.getTypeTabela(owner, table);

			if (bean != null) {
				retorno.add(bean);
			}
		}

		mav.addObject("server", server);
		mav.addObject("app", app);
		mav.addObject("tabelasRecvoz", retorno);
		mav.addObject("view_service", Views.TABELAS_TYPE);

		return mav; // view
	}
}
