package com.kontro.embeddables;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.kontro.entities.prompts.AplicacaoPrompt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PromptNomeAppID implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue
	@Column(name = "nome", nullable = false, updatable = false)
	private String nome;
	@ManyToOne
    @JoinColumn(name = "APP_ID", referencedColumnName = "ID", nullable = false)
	private AplicacaoPrompt app;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public AplicacaoPrompt getApp() {
		return app;
	}
	public void setApp(AplicacaoPrompt app) {
		this.app = app;
	}
	@Override
	public String toString() {
		return "PromptNomeAppID [nome=" + nome + ", app=" + app + "]";
	}
	public PromptNomeAppID(String nome, AplicacaoPrompt app) {
		super();
		this.nome = nome;
		this.app = app;
	}
	public PromptNomeAppID() {
		super();
	}
}
