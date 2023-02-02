package com.kontro.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kontro.entities.prompts.CategoriaPrompt;
import com.kontro.repositories.prompts.CategoriaPromptRepository;
import com.kontro.services.interfaces.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	private CategoriaPromptRepository repository;
	
	@Override
	public List<CategoriaPrompt> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

}
