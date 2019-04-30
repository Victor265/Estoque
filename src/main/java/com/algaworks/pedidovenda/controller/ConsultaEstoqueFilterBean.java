package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Estoque;
import com.algaworks.pedidovenda.model.Fornecedor;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.EstoqueDAO;
import com.algaworks.pedidovenda.repository.FornecedorDAO;
import com.algaworks.pedidovenda.repository.ProdutoDAO;
import com.algaworks.pedidovenda.repository.filters.EstoqueFilter;

@Named
@ViewScoped
public class ConsultaEstoqueFilterBean implements Serializable {

	private static final long serialVersionUID=1L;
	
	@Inject EstoqueDAO estoqueDAO;
	@Inject ProdutoDAO produtoDAO;
	@Inject FornecedorDAO fornecedorDAO;
	private EstoqueFilter filtro;
	private Estoque estoque;
	private Fornecedor fornecedor;
	private Produto produto;
	private List<Estoque> estoques;
	
	

	@PostConstruct
	public void init() {
		filtro = new EstoqueFilter();	
	}
	
	public void pesquisar() {
	
		try {
		estoques=estoqueDAO.filtrados(filtro);
		}catch(NullPointerException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocorreu um problema!!"));
			e.printStackTrace();
		}
	}
	
	public void listarTodosProdutos() {
		estoques = estoqueDAO.consulta();
	}
	
	public List<Estoque> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<Estoque> estoques) {
		this.estoques = estoques;
	}

	public EstoqueFilter getFiltro() {
		return filtro;
	}
	
	public void setFiltro(EstoqueFilter filtro) {
		this.filtro=filtro;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}	
	
	
}
