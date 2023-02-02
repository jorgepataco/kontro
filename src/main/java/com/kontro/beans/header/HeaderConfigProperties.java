package com.kontro.beans.header;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration("headerConfigProperties")
@ConfigurationPropertiesScan
@PropertySource(value = "classpath:header.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "header.view")
public class HeaderConfigProperties {

	private List<ProdutosOi> produtos;

	public List<ProdutosOi> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutosOi> produtos) {
		this.produtos = produtos;
	}
}
