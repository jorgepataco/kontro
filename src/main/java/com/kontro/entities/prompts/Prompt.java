package com.kontro.entities.prompts;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.kontro.embeddables.PromptNomeAppID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Prompt {
	
	@EmbeddedId
	private PromptNomeAppID promptNomeAppID;
	
	@Column(nullable = false, length = 3000, unique=false, name="CONTEUDO")
    private String conteudo;
	
	@ManyToOne
    @JoinColumn(name = "CAT_ID", referencedColumnName = "ID", nullable = false)
	private CategoriaPrompt categoria;

	@Column(name="ATT_DATE", nullable = false, length = 3000, unique=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date dataAtt = new Date();
	
	public Date getDataAtt() {
		return dataAtt;
	}

	public void setDataAtt(Date dataAtt) {
		this.dataAtt = dataAtt;
	}

	public PromptNomeAppID getPromptNomeAppID() {
		return promptNomeAppID;
	}

	public void setPromptNomeAppID(PromptNomeAppID promptNomeAppID) {
		this.promptNomeAppID = promptNomeAppID;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public CategoriaPrompt getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaPrompt categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Prompt [promptNomeAppID=" + promptNomeAppID + ", conteudo=" + conteudo + ", categoria=" + categoria
				+ "]";
	}

	public Prompt() {
		super();
	}

	public Prompt(PromptNomeAppID promptNomeAppID, String conteudo, CategoriaPrompt categoria) {
		super();
		this.promptNomeAppID = promptNomeAppID;
		this.conteudo = conteudo;
		this.categoria = categoria;
	}
}
