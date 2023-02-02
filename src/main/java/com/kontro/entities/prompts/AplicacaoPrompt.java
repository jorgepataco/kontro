package com.kontro.entities.prompts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="APLICACAO_PROMPT")
public class AplicacaoPrompt {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SEQ_PRPT_APP")
	@SequenceGenerator(name="SEQ_PRPT_APP", sequenceName="SEQ_PRPT_APP")
    private Long id;
	
	@Column(nullable = false, length = 50, unique=true, name="NOME")
    private String nome;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public AplicacaoPrompt(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public AplicacaoPrompt() {
		super();
	}
}
