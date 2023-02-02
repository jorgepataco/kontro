package com.kontro.services.interfaces;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Pageable;
import org.springframework.web.servlet.ModelAndView;

import com.kontro.beans.paging.PagingRequest;
import com.kontro.entities.parametros.Parametro;

public interface ParametroService {
	List<Parametro> findAll();
	Map<String, Object> findByPageable(Pageable pageable, PagingRequest pagingRequest);
	ModelAndView getViewAddParametro(String server, String app, Parametro parametro);
	ModelAndView getViewSaveParametro(String server, String app, Parametro parametro);
	ModelAndView getViewDeleteParametro(String server, String app, String nomeParametro);
	ModelAndView getViewEditar(String server, String app, String nomeParametro);
	Parametro create(Parametro parametro);
	void remove(String nomeParametro);
	Parametro update(Parametro parametro);
	void exportar(HttpServletResponse response);
}
