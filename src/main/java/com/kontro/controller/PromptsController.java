package com.kontro.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kontro.beans.header.HeaderConfigProperties;
import com.kontro.beans.paging.PagingRequest;
import com.kontro.dto.PromptDTO;
import com.kontro.entities.prompts.Prompt;
import com.kontro.services.PromptServiceImpl;
import com.kontro.utils.urls.Paths;
import com.kontro.utils.urls.Views;

@RequestMapping(Paths.PROMPTS)
@Controller
public class PromptsController {
	@Autowired
	PromptServiceImpl promptService;
	
	@Autowired
	HeaderConfigProperties headerConfigProperties;
	
	@GetMapping(Paths.APP)
	public ModelAndView getPrompts(@PathVariable(required = true) String app){
		ModelAndView mav = new ModelAndView(Views.INICIO);
		mav.addObject("view_service", Views.PROMPTS);
		return mav;
	}
	
	@GetMapping(Paths.ADD+Paths.APP)
	public ModelAndView viewAddPrompt(@PathVariable(required = true) String app){
		return promptService.getViewAddPrompt(app, new PromptDTO(app));
	}
	
	@GetMapping(Paths.DELETE+Paths.APP+Paths.NOMEPRPT)
	public ModelAndView viewDeletePrompt(@PathVariable(required = true) String app,
			@PathVariable(required = true) String nomePrompt){
		Prompt prompt = promptService.findByNomePromptAndApp(nomePrompt, app).get();
		return promptService.getViewDeletePrompt(app, new PromptDTO(prompt));
	}
	
	@GetMapping(Paths.EDIT+Paths.APP+Paths.NOMEPRPT)
	public ModelAndView viewEditPrompt(@PathVariable(required = true) String app,
			@PathVariable(required = true) String nomePrompt){
		Prompt prompt = promptService.findByNomePromptAndApp(nomePrompt, app).get();
		return promptService.getViewEditPrompt(app, new PromptDTO(prompt));
	}
	
	@GetMapping(Paths.IMPORTAR)
	public ModelAndView viewImportarPrompts(){
		return promptService.getViewImportarPrompts();
	}
	
	@PostMapping(Paths.APP)
	public ModelAndView saveApp(@Valid @RequestBody PromptDTO prompt, 
			BindingResult result, 
			@PathVariable(required = true) String app, Model model){

		if (result.hasErrors()) {
			return promptService.getViewAddPrompt(app, prompt); 
        }
		return promptService.getViewSaveNewPrompt(app, new PromptDTO(promptService.create(prompt)));
	}
	
	@PutMapping(Paths.APP)
	public ModelAndView updateApp(@Valid @RequestBody PromptDTO prompt, 
			BindingResult result, 
			@PathVariable(required = true) String app){
		
		if (result.hasErrors()) {
			return promptService.getViewAddPrompt(app, prompt); 
        }
		
		return promptService.getViewSaveNewPrompt(app, new PromptDTO(promptService.update(prompt)));
	}
	
	@DeleteMapping(Paths.APP+Paths.NOMEPRPT)
	public String delete(@PathVariable(required = true) String app,
			@PathVariable(required = true) String nomePrompt){
		promptService.removePrompt(nomePrompt, app);
		return "redirect:";
	}
	
	@PatchMapping(Paths.APP)
	public ResponseEntity<Map<String, Object>> getPrompts(Pageable pageable, 
			@RequestBody PagingRequest pagingRequest, @PathVariable(required = true) String app){
		
		return new ResponseEntity<>(promptService.
				findByPageable(app, pageable, pagingRequest)
				, HttpStatus.OK);
	}
	
	@PostMapping(path=Paths.UPLOAD, consumes ={MediaType.MULTIPART_FORM_DATA_VALUE})
	@ResponseBody
	public List<Prompt> importPromptsByExcel(@RequestPart("file") MultipartFile obj){
		return promptService.importarByExcel(obj);
	}
	
	@GetMapping(Paths.DOWNLOAD+Paths.APP)
	public void exportPromptsByExcel(HttpServletResponse response,
			@PathVariable(required = true) String app) throws JsonProcessingException{
		promptService.exportarPromptsByApp(response, app);
	}
	
}
