package com.kontro.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kontro.beans.ExcelExport;
import com.kontro.beans.paging.PagingRequest;
import com.kontro.config.interfaces.TitlesModal;
import com.kontro.dto.PromptDTO;
import com.kontro.embeddables.PromptNomeAppID;
import com.kontro.entities.prompts.AplicacaoPrompt;
import com.kontro.entities.prompts.CategoriaPrompt;
import com.kontro.entities.prompts.Prompt;
import com.kontro.enums.TargetExt;
import com.kontro.repositories.prompts.AppPromptRepository;
import com.kontro.repositories.prompts.CategoriaPromptRepository;
import com.kontro.repositories.prompts.PromptRepository;
import com.kontro.services.interfaces.PromptService;
import com.kontro.utils.business.Conversor;
import com.kontro.utils.urls.Components;
import com.kontro.utils.urls.Views;

@Service
public class PromptServiceImpl implements PromptService{
	
	@Autowired
	PromptRepository promptRepository;
	
	@Autowired
	AppPromptRepository appPromptRepository;
	
	@Autowired
	CategoriaPromptRepository categoriaPromptRepository;

	private Prompt save(Prompt p){
		return promptRepository.save(p);
	}
	
//	public Prompt update(PromptDTO dto){
//		PromptNomeAppID id = getIdPrompt(dto);
//		CategoriaPrompt categoria = categoriaPromptRepository.findByNome(dto.getCategoria()).get(0);
//		Prompt p = new Prompt();
//		p.setCategoria(categoria);
//		p.setConteudo(dto.getConteudo());
//		p.setPromptNomeAppID(id);
//		return promptRepository.save(p);
//	}
	
//	private PromptNomeAppID getIdPrompt(String nomePrompt, String app){
//		AplicacaoPrompt aplicacaoPrompt = appPromptRepository.findByNome(app).get(0);
//		return new PromptNomeAppID(nomePrompt, aplicacaoPrompt);
//	}
	
//	private PromptNomeAppID getIdPrompt(PromptDTO dto){
//		return getIdPrompt(dto.getNome(), dto.getAplicacao());
//	}
	
	private List<PromptDTO> convertToDTO(List<Prompt> page){
		return page.stream()
				.map(prompt -> new PromptDTO(prompt))
				.collect(Collectors.toList());
	}
	
	public void exportarPromptsByApp(HttpServletResponse response, String nomeApp){
		List<PromptDTO> dtos = findAllbyNomeApp(nomeApp);
		ExcelExport<PromptDTO> excelExport = new ExcelExport<PromptDTO>();
		excelExport.setSheet(dtos);
		Conversor c = new Conversor();
		c.jsonFileToExcelFile(excelExport, TargetExt.xls, response);
	}
	
	public List<PromptDTO> findAllbyNomeApp(String nomeApp){
		return convertToDTO(promptRepository.findAllbyNomeApp(nomeApp));
	}
	
//	public PromptDTO findById(PromptNomeAppID id){
//		Prompt p = promptRepository.findById(id).get();
//		return new PromptDTO(p);
//	}
	
	private void removeById(PromptNomeAppID id){
		promptRepository.deleteById(id);
	}
	
	public void removePrompt(String nomePrompt, String app){
		Optional<AplicacaoPrompt> aplicacaoPrompt = appPromptRepository.findByNome(app);
		PromptNomeAppID id = new PromptNomeAppID(nomePrompt, aplicacaoPrompt.get());
		removeById(id);
	}
	
	public List<Prompt> importarByExcel(MultipartFile obj){
		List<CategoriaPrompt> categoriaPrompts = categoriaPromptRepository.findAll();
		List<AplicacaoPrompt> aplicacaoPrompts = appPromptRepository.findAll();
		
		List<Prompt> prompts = new ArrayList<Prompt>();
		
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(obj.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			
			int rowNumber = 0;
			while (rows.hasNext()) {
		        Row currentRow = rows.next();
		        
				if (rowNumber == 0) {
					rowNumber++;
					continue;
			    }
	
				rowNumber++;
			    Iterator<Cell> cellsInRow = currentRow.iterator();
			    Prompt prompt = new Prompt();
			    PromptNomeAppID promptNomeAppID = new PromptNomeAppID();
			    String nomePrompt = "";
				int cellIdx = 0;
			        while (cellsInRow.hasNext()) {
			            Cell currentCell = cellsInRow.next();
		
			            switch (cellIdx) {
			            case 0:
			            	nomePrompt = currentCell.getStringCellValue();
			            	break;
		
			            case 1:
			            	prompt.setConteudo(currentCell.getStringCellValue());
			            	break;
		
			            case 2:
			            	if(org.apache.commons.lang3.StringUtils.isNoneBlank(currentCell.getStringCellValue())){
			            		CategoriaPrompt c = 
					            		categoriaPrompts
					            		.stream()
					            		.filter(categoria -> categoria.getNome().equals(currentCell.getStringCellValue())).findFirst().get();
					        		
					            	prompt.setCategoria(c);
			            	}
			            	
			            	break;
		
			            case 3:
			            	AplicacaoPrompt a = 
			            		aplicacaoPrompts
			            		.stream()
			            		.filter(app -> app.getNome().equals(currentCell.getStringCellValue())).findFirst().get();
			            	
			            	promptNomeAppID.setApp(a);
			            	promptNomeAppID.setNome(nomePrompt);
			            	prompt.setPromptNomeAppID(promptNomeAppID);
			              	break;
		
			            default:
			              break;
			            }
		
			            cellIdx++;
		        	}
			        
			        if(prompt.getPromptNomeAppID()!=null){
			        	 prompts.add(prompt);
			        }
			       
				}
	        workbook.close();
	        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return (List<Prompt>) promptRepository.saveAll(prompts);
	}
	
	public Map<String, Object> findByPageable(String app,Pageable pageable, PagingRequest pagingRequest){
		Page<Prompt> page;
		Map<String, Object> response = new HashMap<>();
		Integer posicaoColunaOrder = pagingRequest.getOrder().get(0).getColumn();
		String direction = pagingRequest.getOrder().get(0).getDir().toString();
		String columnOrder = pagingRequest.getColumns().get(posicaoColunaOrder).getName();
		String search = pagingRequest.getSearch().getValue();
		Sort sort = Sort.by(Direction.valueOf(direction.toUpperCase()), columnOrder);
		pageable = PageRequest.of(pagingRequest.getStart()/pagingRequest.getLength(), pagingRequest.getLength(), sort);

		if(org.apache.commons.lang3.StringUtils.isNoneBlank(search)){
			page = promptRepository.searchFilter(search, app, pageable);
		}else{
			page = promptRepository.findAllbyNomeApp(app, pageable);
		}
		response.put("data", convertToDTO(page.getContent()));
		response.put("recordsTotal", page.getTotalElements());
		response.put("recordsFiltered", page.getTotalElements());
		return response;
	}

	@Override
	public Optional<Prompt> findByNomePromptAndApp(String nomePrompt, String app) {
		// TODO Auto-generated method stub
		Optional<AplicacaoPrompt> aplicacaoPrompt = appPromptRepository.findByNome(app);
		PromptNomeAppID id = new PromptNomeAppID(nomePrompt, aplicacaoPrompt.get());
		return promptRepository.findById(id);
	}

	@Override
	public Prompt create(PromptDTO dto) {
		Optional<AplicacaoPrompt> app = appPromptRepository.findByNome(dto.getAplicacao());
		
		if(app.isPresent()){
			PromptNomeAppID id = new PromptNomeAppID();
			id.setApp(app.get());
			id.setNome(dto.getNome());
			
			CategoriaPrompt categoria = categoriaPromptRepository.findByNome(dto.getCategoria()).get(0);
			
			Prompt p = new Prompt(id, dto.getConteudo(), categoria);
			return save(p);
		}
		return null;
	}

	@Override
	public Prompt update(PromptDTO dto) {
		// TODO Auto-generated method stub
		Optional<Prompt> prOptional = findByNomePromptAndApp(dto.getNome(), dto.getAplicacao());
		
		if(prOptional.isPresent()){
			CategoriaPrompt categoria = categoriaPromptRepository.findByNome(dto.getCategoria()).get(0);
			
			Prompt p = new Prompt(prOptional.get().getPromptNomeAppID(),
					dto.getConteudo(), 
					categoria);
			
			return save(p);
		}
		
		return null;
	}
	
	private ModelAndView viewAddPrompt(){
		ModelAndView mav = new ModelAndView(Views.ADD_PROMPT);
		mav.setStatus(HttpStatus.ACCEPTED);
		return mav;
	}
	
	private ModelAndView viewImportarPrompt(){
		ModelAndView mav = new ModelAndView(Views.IMPORTAR_PROMPT);
		mav.setStatus(HttpStatus.ACCEPTED);
		return mav;
	}
	
	private ModelAndView viewDeletePrompt(){
		ModelAndView mav = new ModelAndView(Views.DELETE_PROMPT_CONFIRM);
		mav.setStatus(HttpStatus.ACCEPTED);
		return mav;
	}
	
	@Override
	public ModelAndView getViewAddPrompt(String app, PromptDTO dto){
		ModelAndView mav = viewAddPrompt();
		mav.addAllObjects(addAllObjectsAddPrompt(app, dto));
		return mav;
	}
	
	@Override
	public ModelAndView getViewEditPrompt(String app, PromptDTO dto){
		ModelAndView mav = viewAddPrompt();
		mav.addAllObjects(addAllObjectsEditPrompt(app, dto));
		return mav;
	}
	
	@Override
	public ModelAndView getViewImportarPrompts(){
		ModelAndView mav = viewImportarPrompt();
		mav.addAllObjects(addAllObjectsImportarPrompts());
		return mav;
	}
	
	@Override
	public ModelAndView getViewDeletePrompt(String app, PromptDTO dto){
		ModelAndView mav = viewDeletePrompt();
		mav.addAllObjects(addAllObjectsDeletePrompt(app, dto));
		return mav;
	}
	
	private Map<String, Object> addAllObjectsEditPrompt(String app, PromptDTO dto){
		dto.setAplicacao(app);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categorias", categoriaPromptRepository.findAll());
		map.put("promptDTO", dto);
		map.put("tituloModal", TitlesModal.EDITAR_PROMPT);
		map.put("type", HttpMethod.PUT);
		map.put("app", app);
		return map;
	}
	
	private Map<String, Object> addAllObjectsImportarPrompts(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("view_modal", Views.IMPORTAR_PROMPT);
		return map;
	}
	
	private Map<String, Object> addAllObjectsSaveNewPrompt(String app, PromptDTO dto){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categorias", categoriaPromptRepository.findAll());
		map.put("promptDTO", dto);
		//map.put("tituloModal", TitlesModal.NOVO_PROMPT);
		map.put("type", HttpMethod.POST);
		map.put("app", app);
		map.put("statusTransaction", "success");
		return map;
	}
	
	private Map<String, Object> addAllObjectsAddPrompt(String app, PromptDTO dto){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("categorias", categoriaPromptRepository.findAll());
		map.put("promptDTO", dto);
		//map.put("tituloModal", TitlesModal.NOVO_PROMPT);
		map.put("type", HttpMethod.POST);
		map.put("app", app);
		//map.put("statusTransaction", "success");
		return map;
	}
	
	private Map<String, Object> addAllObjectsDeletePrompt(String app, PromptDTO dto){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("promptDTO", dto);
		map.put("app", app);
		return map;
	}

	@Override
	public ModelAndView getViewSaveNewPrompt(String app, PromptDTO dto) {
		//ModelAndView mav = new ModelAndView(Views.COMPONENTS + Components.TOAST_SUCESSO);
		//mav.setStatus(HttpStatus.ACCEPTED);
		ModelAndView mav = viewAddPrompt();
		mav.addAllObjects(addAllObjectsSaveNewPrompt(app, dto));
		return mav;
	}
}
