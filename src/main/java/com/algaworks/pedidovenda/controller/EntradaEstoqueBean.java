package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import com.algaworks.pedidovenda.model.Estoque;
import com.algaworks.pedidovenda.model.Fornecedor;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.EstoqueDAO;
import com.algaworks.pedidovenda.repository.FornecedorDAO;
import com.algaworks.pedidovenda.repository.ProdutoDAO;

@Named
@ViewScoped
public class EntradaEstoqueBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer aumentarQuantidade;
	private Estoque estoque;
	private List<Produto> produtos;
	private List<Fornecedor> fornecedores;

	private Produto produtoSelecionado;
	private Fornecedor fornecedorSelecionado;
	
	@Inject private ProdutoDAO produtoDAO;
	@Inject private FornecedorDAO fornecedorDAO;
	@Inject private EstoqueDAO estoqueDAO;
	

	@PostConstruct
	public void init() {
		
		this.produtos = this.produtoDAO.consulta();
		this.setFornecedores(this.fornecedorDAO.fornecedores());
	
		}
	

	public void lancar() {
		try {
			//pesquisa cadastro do produto no estoque
			//se tiver cadastrado ele aumenta só o campo quantidade
			// se não tiver cadastrado ele cria um pouco objeto
			// caso não tiver cadastrado o código cai na NoResultException por isso do Try e Catch
			
			
			try{
				estoque = estoqueDAO.porIdProduto(produtoSelecionado.getIdProduto());
				
				Integer total = estoque.getQuantidade() + aumentarQuantidade;
				estoque.setQuantidade(total);
				
			}catch(NoResultException e) {
				estoque = new Estoque();
				estoque.setFornecedor(fornecedorSelecionado);
				estoque.setProduto(produtoSelecionado);
				estoque.setQuantidade(aumentarQuantidade);
			}
			
			estoqueDAO.salvar(estoque);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto Lançado com Sucesso"));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("Ocorreu um problema"));
			e.printStackTrace();
		}
	}


	public List<Produto> getProdutos(){
		if(fornecedorSelecionado !=null) {
			return produtos.stream().filter(item -> item.getFornecedor().getIdFornecedor() == fornecedorSelecionado.getIdFornecedor())
					.collect(Collectors.toList());
		}
		return null;
	}
	
	public void setProdutos(List<Produto> produtos) {
		this.produtos=produtos;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public EstoqueDAO getEstoqueDAO() {
		return estoqueDAO;
	}

	public void setEstoqueDAO(EstoqueDAO estoqueDAO) {
		this.estoqueDAO = estoqueDAO;
	}


	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}


	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}


	public Fornecedor getFornecedorSelecionado() {
		return fornecedorSelecionado;
	}


	public void setFornecedorSelecionado(Fornecedor fornecedorSelecionado) {
		this.fornecedorSelecionado = fornecedorSelecionado;
	}


	public Integer getAumentarQuantidade() {
		return aumentarQuantidade;
	}


	public void setAumentarQuantidade(Integer aumentarQuantidade) {
		this.aumentarQuantidade = aumentarQuantidade;
	}

	
}

