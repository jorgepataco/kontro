package com.kontro.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.kontro.config.interfaces.TitlesModal;
import com.kontro.enums.DirecionamentoModal;
import com.kontro.services.interfaces.CategoriaService;
import com.kontro.services.interfaces.ModalService;
import com.kontro.utils.urls.Views;

@Service
public class ModalServiceImpl implements ModalService{
	
	@Autowired
	CategoriaService categoriaService;
	
	@Autowired
	PromptServiceImpl promptService;
	
	private Map<String, Object> addAllObjectsModalNewPrompt(){
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("categorias", categoriaService.findAll());
		//map.put("prompt", dto);
		//map.put("view_modal", Views.ADD_PROMPT);
		map.put("tituloModal", TitlesModal.NOVO_PROMPT);
		map.put("isLarge", Boolean.FALSE);
		//map.put("app", app);
		return map;
	}
	
	private Map<String, Object> addAllObjectsModalNewParametro(){
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("categorias", categoriaService.findAll());
		//map.put("prompt", dto);
		//map.put("view_modal", Views.ADD_PROMPT);
		map.put("tituloModal", TitlesModal.NOVO_PARAMETRO);
		map.put("isLarge", Boolean.FALSE);
		//map.put("app", app);
		return map;
	}
	
	private Map<String, Object> addAllObjectsModalEditParametro(){
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("categorias", categoriaService.findAll());
		//map.put("prompt", dto);
		//map.put("view_modal", Views.ADD_PROMPT);
		map.put("tituloModal", TitlesModal.EDITAR_PARAMETRO);
		map.put("isLarge", Boolean.FALSE);
		//map.put("app", app);
		return map;
	}
	
	private Map<String, Object> addAllObjectsModalEscolherTabelas(){
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("categorias", categoriaService.findAll());
		//map.put("prompt", dto);
		//map.put("view_modal", Views.ADD_PROMPT);
		map.put("tituloModal", TitlesModal.ESCOLHER_TABELAS);
		map.put("isLarge", Boolean.FALSE);
		//map.put("app", app);
		return map;
	}
	
	private Map<String, Object> addAllObjectsModalEditPrompt(){
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("categorias", categoriaService.findAll());
//		map.put("prompt", new PromptDTO(promptService.findByNomePromptAndApp(nomePrompt, app).get()));
		map.put("tituloModal", TitlesModal.EDITAR_PROMPT);
		map.put("isLarge", Boolean.FALSE);
//		map.put("type", HttpMethod.PUT);
//		map.put("app", app);
//		map.put("view_modal", Views.ADD_PROMPT);
		return map;
	}
	
	private Map<String, Object> addAllObjectsModalRemoverPrompt(){
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("prompt", new PromptDTO(promptService.findByNomePromptAndApp(nomePrompt, app).get()));
		//map.put("app", app);
		map.put("tituloModal", TitlesModal.REMOVER_PROMPT);
		//map.put("view_modal", Views.CONFIRM_REMOVER_PROMPT);
		return map;
	}
	
	private Map<String, Object> addAllObjectsModalImportarPrompts(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tituloModal", TitlesModal.IMPORTAR_PROMPT);
		//map.put("view_modal", Views.IMPORTAR_PROMPT);
		return map;
	}
	
	public ModelAndView openDialog(DirecionamentoModal dir){
		ModelAndView mav = new ModelAndView(Views.MODAL);
		mav.setStatus(HttpStatus.ACCEPTED);
		
		switch (dir) {
			case addPrompt:
				mav.addAllObjects(addAllObjectsModalNewPrompt());
				break;
			case editPrompt:
				mav.addAllObjects(addAllObjectsModalEditPrompt());
				break;
			case importPromtps:
				mav.addAllObjects(addAllObjectsModalImportarPrompts());
				break;
			case deletePrompt:
				mav.addAllObjects(addAllObjectsModalRemoverPrompt());
				break;
			case addParametro:
				mav.addAllObjects(addAllObjectsModalNewParametro());
				break;
			case editParametro:
				mav.addAllObjects(addAllObjectsModalEditParametro());
				break;
			case escolherTabelas:
				mav.addAllObjects(addAllObjectsModalEscolherTabelas());
				break;
			default:
				break;
		}
		return mav;
	}
}
