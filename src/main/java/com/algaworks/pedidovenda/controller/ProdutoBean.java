package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.CategoriaProduto;
import com.algaworks.pedidovenda.model.Fornecedor;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.FornecedorDAO;
import com.algaworks.pedidovenda.repository.ProdutoDAO;

@Named
@ViewScoped
public class ProdutoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Produto produto;
	private Fornecedor fornecedor;
	private List<Fornecedor> fornecedores;

	@Inject
	private ProdutoDAO produtoDAO;
	@Inject
	private FornecedorDAO fornecedorDAO;

	@PostConstruct
	public void init() {
		String codigo_Produto = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("idProduto");
		String codigo_Fornecedor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("fornecedor");

		this.fornecedores = this.fornecedorDAO.fornecedores();
		

		if (codigo_Produto != null && codigo_Fornecedor != null) {
			int codigo_Produto2 = Integer.parseInt(codigo_Produto);
			int codigo_Fornecedor2 = Integer.parseInt(codigo_Fornecedor);

			produto = produtoDAO.porId(codigo_Produto2);
			fornecedor = fornecedorDAO.porId(codigo_Fornecedor2);
		} else {
			produto = new Produto();
		}
	}

	public void salvar() {
		try {
			produtoDAO.salvar(produto);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto Cadastrado com Sucesso"));
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public CategoriaProduto[] getTipos() {
		return CategoriaProduto.values();
	}
}
