package com.kontro.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kontro.beans.PropriedadesBean;
import com.kontro.beans.ReqNewValues;
import com.kontro.beans.TabelaBean;
import com.kontro.database.manager.ApplicationDatabaseDAO;
import com.kontro.utils.urls.Paths;

@RequestMapping(Paths.RECVOZ)
@Controller
public class RecvozController {

	private PropriedadesBean propriedadesBean;
	
	@Autowired
	private Environment env;
	private String view_service;
	
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
	
	@GetMapping(Paths.SERVER+Paths.APP)
	public String getRecvozServerAndApp(@PathVariable(required = true) String server,
			@PathVariable(required = true) String app, Model model) throws Exception {

		this.propriedadesBean = getPropriedades(app, server);

		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);

		String strTabelas = "";
		try {
			strTabelas = dao.getTabelasNLU(propriedadesBean.getTabelaNome(), propriedadesBean.getColunaNome(),
					propriedadesBean.getColunaValor());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		System.out.println("Pataco2");
		String[] tabelas = {};
		if (strTabelas != null && strTabelas != "") {
			tabelas = strTabelas.split(";");
		} else {
			tabelas = this.propriedadesBean.getTabelasRecvoz();
		}

		List<TabelaBean> retorno = new ArrayList<TabelaBean>();

		for (String str : tabelas) {
			try {
				TabelaBean bean = dao.getValoresTableRecvoz(str);

				if (bean != null) {
					retorno.add(bean);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}

		model.addAttribute("server", server);
		model.addAttribute("app", app);
		model.addAttribute("tabelasRecvoz", retorno);
		this.view_service = "services/view-recvoz";
		model.addAttribute("view_service", this.view_service);

		return "view"; // view
	}
	
	@PostMapping(Paths.SERVER+Paths.APP)
	@ResponseBody
	public ReqNewValues callUpdateRecvozTable(@PathVariable(required = true) String server,
			@PathVariable(required = true) String app, @Valid @RequestBody ReqNewValues parametro) throws Exception {
		this.propriedadesBean = getPropriedades(app, server);
		System.out.println(server);
		System.out.println(app);
		System.out.println(parametro.toString());

		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);
		dao.attValoresRecvoz(parametro);
		return parametro;
	}
}
