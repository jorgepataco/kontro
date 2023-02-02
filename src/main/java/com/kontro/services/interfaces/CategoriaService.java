package com.kontro.services.interfaces;

import java.util.List;

import com.kontro.entities.prompts.CategoriaPrompt;

public interface CategoriaService {
	List<CategoriaPrompt> findAll();
}
