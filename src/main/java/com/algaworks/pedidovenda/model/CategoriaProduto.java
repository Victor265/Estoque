package com.algaworks.pedidovenda.model;

public enum CategoriaProduto {
	COMPUTADORES("Computadores"),
	IMPRESSORAS("Impressoras"),
	MONITORES("Monitores"),
	MOUSES("Mouses");
	
private String descricao;
	
	CategoriaProduto(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
