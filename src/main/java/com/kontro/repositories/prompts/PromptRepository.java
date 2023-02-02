package com.kontro.repositories.prompts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kontro.embeddables.PromptNomeAppID;
import com.kontro.entities.prompts.Prompt;

@Repository
@Transactional
public interface PromptRepository extends PagingAndSortingRepository<Prompt, PromptNomeAppID>{
	//Page<Prompt> findByTitleContaining(String title, Pageable pageable);
	//List<Prompt> findAllByConteudoOrCategoriaContains(String name, Pageable pageable);
	
	@Query("select p from Prompt p where (p.promptNomeAppID.nome like %?1% "
			+ " or p.conteudo like %?1% "
			+ " or p.categoria.nome like %?1%)"
			+ " and p.promptNomeAppID.app.nome = ?2")
	Page<Prompt> searchFilter(String search, String nomeApp, Pageable pageable);
	
	@Query("select p from Prompt p where p.promptNomeAppID.app.nome = ?1 ")
	Page<Prompt> findAllbyNomeApp(String nomeApp, Pageable pageable);
	
	@Query("select p from Prompt p where p.promptNomeAppID.app.nome = ?1 ")
	List<Prompt> findAllbyNomeApp(String nomeApp);
	
	List<Prompt> findAll();
	
	
}
