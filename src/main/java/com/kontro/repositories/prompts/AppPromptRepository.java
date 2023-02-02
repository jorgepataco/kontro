package com.kontro.repositories.prompts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kontro.entities.prompts.AplicacaoPrompt;

@Repository
@Transactional
public interface AppPromptRepository extends JpaRepository<AplicacaoPrompt, Long>{
	
	Optional<AplicacaoPrompt> findByNome(String nome);
}
