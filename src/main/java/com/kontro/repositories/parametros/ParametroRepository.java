package com.kontro.repositories.parametros;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kontro.entities.parametros.Parametro;

@Repository
@Transactional
public interface ParametroRepository extends PagingAndSortingRepository<Parametro, String>{
	
	List<Parametro> findAll();
	@Query("select p FROM Parametro p where (p.name like %?1% "
			+ " or p.value like %?1%)")
	Page<Parametro> findByNameOrValueContains(String search, Pageable pageable);
}
