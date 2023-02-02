package com.kontro.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kontro.beans.paging.PagingRequest;
import com.kontro.entities.parametros.Parametro;
import com.kontro.services.interfaces.ParametroService;
import com.kontro.utils.urls.Paths;
import com.kontro.utils.urls.Views;

@RequestMapping(Paths.PARAMETROS)
@Controller
public class ParametrosController {

	@Autowired
	ParametroService parametroService;

	// @Autowired
	// private DataSourceContextHolder dataSourceContextHolder;

	@GetMapping(Paths.SERVER + Paths.APP)
	public ModelAndView getParametrosServerAndApp(@PathVariable(required = true) String server,
			@PathVariable(required = true) String app) throws Exception {
		ModelAndView mav = new ModelAndView(Views.INICIO);
		mav.addObject("view_service", Views.PARAMETROS);
		return mav;
	}
	
	@PatchMapping(Paths.SERVER + Paths.APP)
	public ResponseEntity<Map<String, Object>> getPrompts(Pageable pageable, @RequestBody PagingRequest pagingRequest,
			@PathVariable(required = true) String server, @PathVariable(required = true) String app) {

		return new ResponseEntity<>(parametroService.findByPageable(pageable, pagingRequest), HttpStatus.OK);
	}

	@GetMapping(Paths.ADD + Paths.SERVER + Paths.APP)
	public ModelAndView viewEditPrompt(@PathVariable(required = true) String server,
			@PathVariable(required = true) String app) {
		return parametroService.getViewAddParametro(server, app, new Parametro());
	}

	@GetMapping(Paths.DELETE + Paths.SERVER + Paths.APP + Paths.NOMEPARAMETRO)
	public ModelAndView viewDelete(@PathVariable(required = true) String server,
			@PathVariable(required = true) String app, @PathVariable(required = true) String nomeParametro) {
		return parametroService.getViewDeleteParametro(server, app, nomeParametro);
	}
	
	@GetMapping(Paths.EDIT + Paths.SERVER + Paths.APP + Paths.NOMEPARAMETRO)
	public ModelAndView viewEdit(@PathVariable(required = true) String app,
			@PathVariable(required = true) String server,
			@PathVariable(required = true) String nomeParametro){
		return parametroService.getViewEditar(server, app, nomeParametro);
	}
	
	@DeleteMapping(Paths.SERVER + Paths.APP + Paths.NOMEPRPT)
	public String delete(@PathVariable(required = true) String app,
			@PathVariable(required = true) String nomePrompt){
		parametroService.remove(nomePrompt);
		return "redirect:";
	}

	@PostMapping(Paths.SERVER + Paths.APP)
	public ModelAndView create(@Valid @RequestBody Parametro parametro, BindingResult result,
			@PathVariable(required = true) String server, @PathVariable(required = true) String app, Model model) {

		if (result.hasErrors()) {
			return parametroService.getViewAddParametro(server, app, parametro);
		}

		return parametroService.getViewSaveParametro(server, app, parametroService.create(parametro));
	}
	
	@PutMapping(Paths.SERVER + Paths.APP)
	public ModelAndView update(@Valid @RequestBody Parametro parametro, BindingResult result,
			@PathVariable(required = true) String server, @PathVariable(required = true) String app, Model model) {

		if (result.hasErrors()) {
			return parametroService.getViewAddParametro(server, app, parametro);
		}

		return parametroService.getViewSaveParametro(server, app, parametroService.update(parametro));
	}
	
	@GetMapping(Paths.DOWNLOAD+ Paths.SERVER + Paths.APP)
	public void exportPromptsByExcel(HttpServletResponse response) throws JsonProcessingException{
		parametroService.exportar(response);
	}
}
