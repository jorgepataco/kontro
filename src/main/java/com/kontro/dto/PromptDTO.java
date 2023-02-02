package com.kontro.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.kontro.embeddables.PromptNomeAppID;
import com.kontro.entities.prompts.Prompt;

@JsonIgnoreProperties
public class PromptDTO {
	
	@NotBlank
	@Size(min=4, max=85, message="Nome deve conter entre 4 e 85 letras")
	private String nome;
	@NotBlank
	@Size(max=1050, message="Conteúdo deve conter no máximo 1050 letras")
	private String conteudo;
	private String categoria;
	private String aplicacao;
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
	private Date dataAtt;
	
	public Date getDataAtt() {
		return dataAtt;
	}

	public void setDataAtt(Date dataAtt) {
		this.dataAtt = dataAtt;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getAplicacao() {
		return aplicacao;
	}

	public void setAplicacao(String aplicacao) {
		this.aplicacao = aplicacao;
	}

	public PromptDTO(Prompt prompt) {
		super();
		this.nome = prompt.getPromptNomeAppID().getNome();
		this.conteudo = prompt.getConteudo();
		this.categoria = prompt.getCategoria().getNome();
		this.aplicacao = prompt.getPromptNomeAppID().getApp().getNome();
		this.dataAtt = prompt.getDataAtt();
	}
	
	public PromptDTO() {
		super();
	}
	
	public PromptDTO(String app) {
		super();
		this.aplicacao = app;
	}
	
	@Override
	public String toString() {
		return "PromptDTO [nome=" + nome + ", conteudo=" + conteudo + ", categoria=" + categoria + ", aplicacao="
				+ aplicacao + ", dataAtt=" + dataAtt + "]";
	}

	public static void main(String[] args) throws JsonProcessingException {
		Prompt p1 = new Prompt();
		PromptNomeAppID id = new PromptNomeAppID();
		id.setNome("teste1");
		p1.setPromptNomeAppID(id);
		Prompt p2 = new Prompt();
		PromptNomeAppID id2 = new PromptNomeAppID();
		id.setNome("teste2");
		p2.setPromptNomeAppID(id2);
		
		List<Prompt> prts = new ArrayList<Prompt>();
		prts.add(p1);
		prts.add(p2);
		
		List<PromptDTO> dtos = prts.stream()
				.map(prompt -> new PromptDTO(prompt))
				.collect(Collectors.toList());
		
//		String json = new ObjectMapper().writeValueAsString(prts);
//		
//		System.out.println(json);
//		List<PromptDTO> dto = new ObjectMapper().readerFor(PromptDTO[].class).readValue(json);
		System.out.println(dtos.size());
	}

}
