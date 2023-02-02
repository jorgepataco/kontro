package com.kontro.repositories.prompts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kontro.entities.prompts.CategoriaPrompt;

//@Repository(value="categoriaPromptRepository")
@Repository
@Transactional
public interface CategoriaPromptRepository extends JpaRepository<CategoriaPrompt, Long>{

	List<CategoriaPrompt> findByNome(String nome);
}
