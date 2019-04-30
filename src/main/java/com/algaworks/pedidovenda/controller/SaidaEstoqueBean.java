package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import com.algaworks.pedidovenda.model.Estoque;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.EstoqueDAO;
import com.algaworks.pedidovenda.repository.ProdutoDAO;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class SaidaEstoqueBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer diminuirQuantidade;
	private Produto produtoSelecionado;
	private Estoque estoque;
	private List<Produto> produtos;
	private List<Estoque> estoques;

	@Inject
	private ProdutoDAO produtoDAO;
	@Inject
	private EstoqueDAO estoqueDAO;

	@PostConstruct
	public void init() {
		this.produtos = this.produtoDAO.consultaDisponivelEstoque();
		this.setEstoques(this.estoqueDAO.consulta());
	}

	public void lancar() {
		try {
			// faz a mesma coisa que o entrada estoque, porém aqui só diminui a quantidade
			// de diferente
			try {

				estoque = estoqueDAO.porIdProduto(produtoSelecionado.getIdProduto());
				Integer total = estoque.getQuantidade() - diminuirQuantidade;

				if (total < 0) {
					FacesUtil.addErrorMessage(
							"Impossível realizar operação a quantidade de produto retirada foi superior na do que tem no estoque");
					return;
				} else {
					estoque.setQuantidade(total);
				}

			} catch (NoResultException e) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocorreu um problema"));
				e.printStackTrace();
			}

			estoqueDAO.salvar(estoque);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Produto Retirado com Sucesso"));

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocorreu um problema"));
			e.printStackTrace();
		}
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public List<Produto> getProdutos() {

		return produtos;

	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Integer getDiminuirQuantidade() {
		return diminuirQuantidade;
	}

	public void setDiminuirQuantidade(Integer diminuirQuantidade) {
		this.diminuirQuantidade = diminuirQuantidade;
	}

	public ProdutoDAO getProdutoDAO() {
		return produtoDAO;
	}

	public void setProdutoDAO(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}

	public EstoqueDAO getEstoqueDAO() {
		return estoqueDAO;
	}

	public void setEstoqueDAO(EstoqueDAO estoqueDAO) {
		this.estoqueDAO = estoqueDAO;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public List<Estoque> getEstoques() {
		return estoques;
	}

	public void setEstoques(List<Estoque> estoques) {
		this.estoques = estoques;
	}

}
