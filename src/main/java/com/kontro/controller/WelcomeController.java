package com.kontro.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.kontro.beans.AppBean;
import com.kontro.beans.PropriedadesBean;
import com.kontro.beans.ReqNewValues;
import com.kontro.beans.TabelaBean;
import com.kontro.database.manager.ApplicationDatabaseDAO;
import com.kontro.dto.PromptDTO;
import com.kontro.entities.parametros.Parametro;
import com.kontro.repositories.prompts.CategoriaPromptRepository;
import com.kontro.repositories.prompts.PromptRepository;
import com.kontro.utils.urls.Views;

@Controller
public class WelcomeController {
	private static final ObjectMapper fieldMapper = new ObjectMapper();
	
	@Autowired
	CategoriaPromptRepository categoriaPromptRepository;
		
	static {
		try {
			fieldMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			fieldMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
			fieldMapper.setDateFormat(dateFormat);

			VisibilityChecker<?> defaultVisibilityChecker = fieldMapper.getSerializationConfig()
					.getDefaultVisibilityChecker();
			defaultVisibilityChecker.withFieldVisibility(Visibility.ANY);
			defaultVisibilityChecker.withGetterVisibility(Visibility.NONE);
			defaultVisibilityChecker.withSetterVisibility(Visibility.NONE);
			defaultVisibilityChecker.withCreatorVisibility(Visibility.NONE);
			defaultVisibilityChecker.withIsGetterVisibility(Visibility.NONE);

			fieldMapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
			fieldMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			fieldMapper.setTimeZone(TimeZone.getDefault());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// inject via application.properties
	@Value("${welcome.message}")
	private String message;
	private PropriedadesBean propriedadesBean;
	private String view_service;

	@Autowired
	private Environment env;

	@GetMapping("/")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView(Views.INICIO);
		mav.addObject("view_service", "home");
		return mav;
	}

//	@GetMapping("tabela")
//	String tables() {
//		this.view_service = "services/view-tabela-lista";
//		return "tables";
//	}

//	@GetMapping("/resultado")
//	public String someMethod(@RequestParam("app") String app, @RequestParam("server") String server, Model model)
//			throws Exception {
//		model.addAttribute("app", app);
//		model.addAttribute("server", server);
//		System.out.println("Valor de app: " + app);
//		System.out.println("Valor de servidor: " + server);
//
//		this.propriedadesBean = getPropriedades(app, server);
//
//		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
//				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);
//
//		String owner = propriedadesBean.getUsername();
//
//		List<TabelaBean> retorno = new ArrayList<TabelaBean>();
//
//		TabelaBean bean = dao.getListaTabelas(owner);
//
//		if (bean != null) {
//			retorno.add(bean);
//		}
//
//		model.addAttribute("server", server);
//		model.addAttribute("app", app);
//		model.addAttribute("tabelasRecvoz", retorno);
//		this.view_service = "services/view-tabela-lista";
//		model.addAttribute("view_service", this.view_service);
//		return "tables"; // view
//
//	}

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

//	@ModelAttribute("apps")
//	public List<AppBean> getListaApps() throws JsonMappingException, JsonProcessingException {
//		System.out.println("Apps aqui1");
//		String jsonApps = env.getProperty("apps");
//		JsonNode node = fieldMapper.readTree(jsonApps);
//		JavaType type = fieldMapper.getTypeFactory().constructCollectionType(List.class, AppBean.class);
//		List<AppBean> apps = fieldMapper.readValue(node.get("aplicacoes").toString(), type);
//		return apps;
//	}

	@ModelAttribute("getAppName")
	public String getAppName() throws JsonMappingException, JsonProcessingException {
		return env.getProperty("application.name");
	}

	@ModelAttribute("view_service")
	public String getViewService() throws JsonMappingException, JsonProcessingException {
		// System.out.println("entrou2");
		return view_service;
	}

//	@GetMapping("/parametros/{server}/{app}")
//	public String getParametrosServerAndApp(@PathVariable(required = true) String server,
//			@PathVariable(required = true) String app, Model model) throws Exception {
//
//		this.propriedadesBean = getPropriedades(app, server);
//
//		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
//				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);
//		model.addAttribute("server", server);
//		model.addAttribute("app", app);
//
//		String[] tabelas = {};
//
//		if (propriedadesBean.isListaParametro()) {
//			// model.addAttribute("parametros", retorno);
//			tabelas = this.propriedadesBean.getTabelasParametros();
//			List<TabelaBean> retorno = new ArrayList<TabelaBean>();
//
//			for (String str : tabelas) {
//				try {
//					TabelaBean bean = dao.getValoresTableRecvoz(str);
//
//					if (bean != null) {
//						retorno.add(bean);
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//
//			}
//			model.addAttribute("tabelasRecvoz", retorno);
//			this.view_service = "services/view-parametros-lista";
//		} else {
//			List<Parametro> retorno = dao.getParametros(propriedadesBean.getTabelaNome(),
//					propriedadesBean.getColunaNome(), propriedadesBean.getColunaValor());
//			model.addAttribute("parametros", retorno);
//			this.view_service = "services/view-parametros";
//		}
//
//		model.addAttribute("view_service", this.view_service);
//
//		return "view"; // view
//	}
	
//	@GetMapping("/prompts/{app}")
//	public String getPromptsByApp(@PathVariable(required = true) String app, Model model) throws Exception {
//		
//		this.view_service = "services/view-prompts";
//		model.addAttribute("app", app);
//		model.addAttribute("view_service", this.view_service);
//		model.addAttribute("render_footer", false);
//		model.addAttribute("prompt", new PromptDTO());
//		return "view"; // view
//	}
	
//	@GetMapping("/prompts/view/add/{app}")
//	public String viewAddPrompts(Model model, @PathVariable(required = true) String app) throws Exception {
//		
//		model.addAttribute("categorias", categoriaPromptRepository.findAll());
//		model.addAttribute("prompt", new PromptDTO());
//		model.addAttribute("app", app);
//		return "cadastro/add-prompt"; // view
//	}
	
//	@GetMapping("/prompts/view/importar")
//	public String viewimportarPrompts(Model model) throws Exception {
//		this.view_service = "services/view-prompts";
//		model.addAttribute("categorias", categoriaPromptRepository.findAll());
//		return "cadastro/importar-prompts"; // view
//	}

//	@GetMapping("/recvoz/{server}/{app}")
//	public String getRecvozServerAndApp(@PathVariable(required = true) String server,
//			@PathVariable(required = true) String app, Model model) throws Exception {
//
//		this.propriedadesBean = getPropriedades(app, server);
//
//		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
//				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);
//
//		String strTabelas = "";
//		try {
//			strTabelas = dao.getTabelasNLU(propriedadesBean.getTabelaNome(), propriedadesBean.getColunaNome(),
//					propriedadesBean.getColunaValor());
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO: handle exception
//		}
//		System.out.println("Pataco2");
//		String[] tabelas = {};
//		if (strTabelas != null && strTabelas != "") {
//			tabelas = strTabelas.split(";");
//		} else {
//			tabelas = this.propriedadesBean.getTabelasRecvoz();
//		}
//
//		List<TabelaBean> retorno = new ArrayList<TabelaBean>();
//
//		for (String str : tabelas) {
//			try {
//				TabelaBean bean = dao.getValoresTableRecvoz(str);
//
//				if (bean != null) {
//					retorno.add(bean);
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//
//		}
//
//		model.addAttribute("server", server);
//		model.addAttribute("app", app);
//		model.addAttribute("tabelasRecvoz", retorno);
//		this.view_service = "services/view-recvoz";
//		model.addAttribute("view_service", this.view_service);
//
//		return "view"; // view
//	}

//	@GetMapping("/tabela/{server}/{app}/{table}/dados")
//	public String getRecvozServerAndApp(@PathVariable(required = true) String server,
//			@PathVariable(required = true) String app, @PathVariable(required = true) String table, Model model)
//			throws Exception {
//
//		this.propriedadesBean = getPropriedades(app, server);
//
//		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
//				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);
//
//		String[] tabelas = { table };
//
//		List<TabelaBean> retorno = new ArrayList<TabelaBean>();
//
//		for (String str : tabelas) {
//			TabelaBean bean = dao.getValoresTableRecvoz(str);
//
//			if (bean != null) {
//				retorno.add(bean);
//			}
//		}
//
//		model.addAttribute("server", server);
//		model.addAttribute("app", app);
//		model.addAttribute("tabelasRecvoz", retorno);
//		this.view_service = "services/view-tabela-dados";
//		model.addAttribute("view_service", this.view_service);
//
//		return "view"; // view
//	}

//	@GetMapping("/tabela/{server}/{app}/{table}/type")
//	public String getTypeTabela(@PathVariable(required = true) String server, @PathVariable(required = true) String app,
//			@PathVariable(required = true) String table, Model model) throws Exception {
//
//		this.propriedadesBean = getPropriedades(app, server);
//
//		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
//				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);
//
//		String[] tabelas = { table };
//		String owner = propriedadesBean.getUsername();
//
//		List<TabelaBean> retorno = new ArrayList<TabelaBean>();
//
//		for (String str : tabelas) {
//			TabelaBean bean = dao.getTypeTabela(owner, table);
//
//			if (bean != null) {
//				retorno.add(bean);
//			}
//		}
//
//		model.addAttribute("server", server);
//		model.addAttribute("app", app);
//		model.addAttribute("tabelasRecvoz", retorno);
//		this.view_service = "services/view-tabela-type";
//		model.addAttribute("view_service", this.view_service);
//
//		return "view"; // view
//	}

//	@GetMapping("/tabela/{server}/{app}")
//	public String getListaTabelas(@PathVariable(required = true) String server,
//			@PathVariable(required = true) String app, Model model) throws Exception {
//
//		this.propriedadesBean = getPropriedades(app, server);
//
//		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
//				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);
//
//		String owner = propriedadesBean.getUsername();
//
//		List<TabelaBean> retorno = new ArrayList<TabelaBean>();
//
//		TabelaBean bean = dao.getListaTabelas(owner);
//
//		if (bean != null) {
//			retorno.add(bean);
//		}
//
//		model.addAttribute("server", server);
//		model.addAttribute("app", app);
//		model.addAttribute("tabelasRecvoz", retorno);
//		this.view_service = "services/view-tabela-lista";
//		model.addAttribute("view_service", this.view_service);
//
//		return "tables"; // view
//	}

//	@PostMapping("/parametros/deletar/{server}/{app}/{parametro}")
//	public String callDeleteParametros(@PathVariable(required = true) String server,
//			@PathVariable(required = true) String app, @PathVariable(required = true) String parametro)
//			throws Exception {
//
//		this.propriedadesBean = getPropriedades(app, server);
//		System.out.println(server);
//		System.out.println(app);
//		System.out.println(parametro);
//
//		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
//				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);
//
//		dao.deleteParametro(propriedadesBean, parametro);
//
//		return "view";
//	}

//	@PostMapping("/parametros/att/{server}/{app}")
//	public String callUpdateParametros(@PathVariable(required = true) String server,
//			@PathVariable(required = true) String app, @Valid @RequestBody Parametro parametro) throws Exception {
//
//		this.propriedadesBean = getPropriedades(app, server);
//
//		System.out.println(server);
//		System.out.println(app);
//		System.out.println(parametro.getName());
//		System.out.println(parametro.getValue());
//
//		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
//				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);
//		dao.attParametro(propriedadesBean, parametro.getName(), parametro.getValue());
//
//		return "view";
//	}

//	@PostMapping("/parametros/add/{server}/{app}")
//	public String callAddParametros(@PathVariable(required = true) String server,
//			@PathVariable(required = true) String app, @Valid @RequestBody Parametro parametro) throws Exception {
//
//		this.propriedadesBean = getPropriedades(app, server);
//
//		System.out.println(server);
//		System.out.println(app);
//		System.out.println(parametro.getName());
//		System.out.println(parametro.getValue());
//
//		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
//				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);
//
//		dao.addParametro(propriedadesBean, parametro.getName(), parametro.getValue());
//
//		return "view";
//	}

//	@PostMapping("/recvoz/att/{server}/{app}")
//	public String callUpdateRecvozTable(@PathVariable(required = true) String server,
//			@PathVariable(required = true) String app, @Valid @RequestBody ReqNewValues parametro) throws Exception {
//		this.propriedadesBean = getPropriedades(app, server);
//		System.out.println(server);
//		System.out.println(app);
//		System.out.println(parametro.toString());
//
//		ApplicationDatabaseDAO dao = new ApplicationDatabaseDAO(propriedadesBean.getJdbcClassName(),
//				propriedadesBean.getUrl(), propriedadesBean.getUsername(), propriedadesBean.getPassword(), 3);
//		dao.attValoresRecvoz(parametro);
//
//		return "view";
//	}

	@RequestMapping(value = "/GTC/descerAcao/{idxCenario}/{idxAcao}", method = RequestMethod.GET)
	public String descerAcao(ModelMap map, @PathVariable(required = true) Integer idxCenario,
			@PathVariable(required = true) Integer idxAcao) {

		System.out.println(idxCenario);
		System.out.println(idxAcao);

		// TODO: retrieve the new value here so you can add it to model map
		// map.addAttribute("numDeviceEventsWithAlarm", count);
		System.out.println("entrou2");

		List<String> testes = new ArrayList<String>();
		testes.add("Teste06");
		testes.add("Teste07");
		testes.add("Teste08");
		testes.add("Teste09");
		testes.add("Teste10");
		map.addAttribute("testes", testes);
		return "view :: #eventCount0";
	}

	@GetMapping("/GTC")
		public String getGTC(Model model) throws Exception {
			List<String> testes = new ArrayList<String>();
			testes.add("Teste01");
			testes.add("Teste02");
			testes.add("Teste03");
			testes.add("Teste04");
			testes.add("Teste05");
			System.out.println("ENTROU1");
			this.view_service = "services/view-gtc";
			model.addAttribute("view_service", this.view_service);
			model.addAttribute("testes", testes);
			return "view"; // view
		}

//	    @ModelAttribute("testes")
//	    public List<String> getTestes() throws JsonMappingException, JsonProcessingException{
//	    	//System.out.println("entrou2");
//	    	List<String> testes = new ArrayList<String>();
//	    	
//	    	testes.add("Teste01");
//	    	testes.add("Teste02");
//	    	testes.add("Teste03");
//	    	testes.add("Teste04");
//	    	testes.add("Teste05");
//	    	
//	    	return testes;
//	    }
	
	@GetMapping("/legado/fibra/{nomeLegado}/{massa}")
	public ResponseEntity getGTC(@PathVariable("nomeLegado") String nomeLegado, @PathVariable("massa") String massa) throws Exception {
		System.out.println("aqui1");
		String comand = "C:/dev/WORKSPACE_IVP/URAOiFibra/WebContent/SCE-INF/WEB-INF/lib/BusinessOiFibra.jar br.com.everis.oifibra.legacy.externo.Kontro";
		Process proc = Runtime.getRuntime().exec("java -cp ".concat(comand).concat(" ").concat(nomeLegado));
		// Then retreive the process output
		InputStream in = proc.getInputStream();
		InputStream err = proc.getErrorStream();
		StringBuilder textBuilder = new StringBuilder();
	    try (Reader reader = new BufferedReader(new InputStreamReader
	      (in, Charset.forName(StandardCharsets.UTF_8.name())))) {
	        int c = 0;
	        while ((c = reader.read()) != -1) {
	            textBuilder.append((char) c);
	        }
	    }
	    System.out.println(textBuilder.toString());
        return  ResponseEntity.ok(HttpStatus.OK);
		//return "";
	}
	}
