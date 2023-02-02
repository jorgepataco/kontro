package com.kontro.services.interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kontro.beans.paging.PagingRequest;
import com.kontro.dto.PromptDTO;
import com.kontro.entities.prompts.Prompt;

public interface PromptService {

	ModelAndView getViewDeletePrompt(String app, PromptDTO dto);
	ModelAndView getViewAddPrompt(String app, PromptDTO dto);
	ModelAndView getViewSaveNewPrompt(String app, PromptDTO dto);
	ModelAndView getViewEditPrompt(String app, PromptDTO dto);
	ModelAndView getViewImportarPrompts();
	Prompt create(PromptDTO dto);
	Prompt update(PromptDTO dto);
	//PromptNomeAppID getIdPrompt(String nomePrompt, String app);
	void exportarPromptsByApp(HttpServletResponse response, String nomeApp);
	List<PromptDTO> findAllbyNomeApp(String nomeApp);
	Optional<Prompt> findByNomePromptAndApp(String nomePrompt, String app);
	void removePrompt(String nomePrompt, String app);
	List<Prompt> importarByExcel(MultipartFile obj);
	Map<String, Object> findByPageable(String app,Pageable pageable, PagingRequest pagingRequest);
	
}
