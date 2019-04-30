package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.ProdutoDAO;

@Named
@ViewScoped
public class ConsultaProdutoBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject ProdutoDAO produtoDAO;
	private List<Produto> produtos;
	private Produto produtoSelecionado;
	
	@PostConstruct
	public void init() {
		setProdutos(produtoDAO.consulta());

		
	}
	
	public void excluir() {
		try {
			
			produtoDAO.excluir(produtoSelecionado.getIdProduto());
			for(int i=0; i<produtos.size(); i++){
				if(produtos.get(i) == produtoSelecionado) {
					produtos.remove(i);
				}
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto Excluido com Sucesso"));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocorreu um problema "));
			e.printStackTrace();
		}
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}
}