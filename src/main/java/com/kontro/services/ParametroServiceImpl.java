package com.kontro.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.kontro.beans.ExcelExport;
import com.kontro.beans.paging.PagingRequest;
import com.kontro.entities.parametros.Parametro;
import com.kontro.enums.TargetExt;
import com.kontro.repositories.parametros.ParametroRepository;
import com.kontro.repositories.prompts.AppPromptRepository;
import com.kontro.services.interfaces.ParametroService;
import com.kontro.utils.business.Conversor;
import com.kontro.utils.urls.Views;

@Service
public class ParametroServiceImpl implements ParametroService {

	@Autowired
	ParametroRepository parametroRepository;

	@Autowired
	AppPromptRepository appPromptRepository;

	@Override
	public List<Parametro> findAll() {
		// TODO Auto-generated method stub
		return parametroRepository.findAll();
	}

	@Override
	public Map<String, Object> findByPageable(Pageable pageable, PagingRequest pagingRequest) {
		Page<Parametro> page;
		Map<String, Object> response = new HashMap<>();
		Integer posicaoColunaOrder = pagingRequest.getOrder().get(0).getColumn();
		String direction = pagingRequest.getOrder().get(0).getDir().toString();
		String columnOrder = pagingRequest.getColumns().get(posicaoColunaOrder).getName();
		String search = pagingRequest.getSearch().getValue();
		Sort sort = Sort.by(Direction.valueOf(direction.toUpperCase()), columnOrder);
		pageable = PageRequest.of(pagingRequest.getStart() / pagingRequest.getLength(), pagingRequest.getLength(),
				sort);

		// page = parametroRepository.findAll(pageable);

		if (org.apache.commons.lang3.StringUtils.isNoneBlank(search)) {
			page = parametroRepository.findByNameOrValueContains(search, pageable);
		} else {
			page = parametroRepository.findAll(pageable);
		}
		response.put("data", page.getContent());
		response.put("recordsTotal", page.getTotalElements());
		response.put("recordsFiltered", page.getTotalElements());
		return response;
	}

	private ModelAndView viewAddParametro() {
		ModelAndView mav = new ModelAndView(Views.ADD_PARAMETRO);
		mav.setStatus(HttpStatus.ACCEPTED);
		return mav;
	}

	private Map<String, Object> addAllObjectsSaveParametro(String server, String app, Parametro dto, HttpMethod type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parametro", dto);
		map.put("type", type);
		map.put("app", app);
		map.put("server", server);
		map.put("statusTransaction", "success");
		return map;
	}
	
	private Map<String, Object> addAllObjectsAddParametro(String server, String app, Parametro dto, HttpMethod type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parametro", dto);
		map.put("type", type);
		map.put("app", app);
		map.put("server", server);
	//	map.put("statusTransaction", "success");
		return map;
	}

	@Override
	public ModelAndView getViewAddParametro(String server, String app, Parametro parametro) {
		// TODO Auto-generated method stub
		ModelAndView mav = viewAddParametro();
		mav.addAllObjects(addAllObjectsAddParametro(server, app, parametro, HttpMethod.POST));
		return mav;
	}
	
	@Override
	public ModelAndView getViewSaveParametro(String server, String app, Parametro parametro) {
		// TODO Auto-generated method stub
		ModelAndView mav = viewAddParametro();
		mav.addAllObjects(addAllObjectsSaveParametro(server, app, parametro, HttpMethod.POST));
		return mav;
	}

	@Override
	public Parametro create(Parametro parametro) {
		// TODO Auto-generated method stub
		Optional<Parametro> prOptional = parametroRepository.findById(parametro.getName());

		if (!prOptional.isPresent()) {

			return parametroRepository.save(parametro);
		}

		return null;
	}

	private Map<String, Object> addAllObjectsDeleteParametro(String server, String app, String nomeParametro) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nomeParametro", nomeParametro);
		map.put("app", app);
		map.put("server", server);
		return map;
	}

	private ModelAndView viewDeleteParametro() {
		ModelAndView mav = new ModelAndView(Views.DELETE_PARAMETRO_CONFIRM);
		mav.setStatus(HttpStatus.ACCEPTED);
		return mav;
	}

	@Override
	public ModelAndView getViewDeleteParametro(String server, String app, String nomeParametro) {
		// TODO Auto-generated method stub
		ModelAndView mav = viewDeleteParametro();
		mav.addAllObjects(addAllObjectsDeleteParametro(server, app, nomeParametro));
		return mav;
	}

	@Override
	public void remove(String nomeParametro) {
		// TODO Auto-generated method stub
		parametroRepository.deleteById(nomeParametro);
	}

	@Override
	public ModelAndView getViewEditar(String server, String app, String nomeParametro) {
		// TODO Auto-generated method stub
		ModelAndView mav = viewAddParametro();
		mav.addAllObjects(addAllObjectsAddParametro(server, 
				app, 
				parametroRepository.findById(nomeParametro).get(),
				HttpMethod.PUT));
		return mav;
	}
	
	public Parametro update(Parametro parametro) {
		// TODO Auto-generated method stub
		Optional<Parametro> prOptional = parametroRepository.findById(parametro.getName());
		
		if(prOptional.isPresent()){
						
			return parametroRepository.save(parametro);
		}
		
		return null;
	}

	@Override
	public void exportar(HttpServletResponse response) {
		// TODO Auto-generated method stub
		List<Parametro> dtos = parametroRepository.findAll();
		ExcelExport<Parametro> excelExport = new ExcelExport<Parametro>();
		excelExport.setSheet(dtos);
		Conversor c = new Conversor();
		c.jsonFileToExcelFile(excelExport, TargetExt.xls, response);
	}
}
