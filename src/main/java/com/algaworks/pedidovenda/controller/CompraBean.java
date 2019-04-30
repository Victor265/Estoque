package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Compra;
import com.algaworks.pedidovenda.model.ItemCompra;
import com.algaworks.pedidovenda.model.Produto;
import com.algaworks.pedidovenda.repository.CompraDAO;
import com.algaworks.pedidovenda.repository.ItemDAO;
import com.algaworks.pedidovenda.repository.ProdutoDAO;
import com.algaworks.pedidovenda.service.CadastroCompraService;
import com.algaworks.pedidovenda.util.jsf.FacesUtil;

@Named
@ViewScoped
public class CompraBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Inject ProdutoDAO produtoDAO;
	@Inject CompraDAO compraDAO;
	@Inject ItemDAO itemDAO;
	@Inject CadastroCompraService cadastroCompraService;
	

	private Compra compra;
	private Produto produtoLinhaEditavel;
	

	
	@PostConstruct
	public void init() {
		String idCompra = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("idCompra");
	
		

		if (idCompra != null) {
			int idCompra2 = Integer.parseInt(idCompra);

			compra = compraDAO.porId(idCompra2);
		
		} else {
		compra = new Compra();
		this.compra.adicionarItemVazio();	
		this.recalcularCompra();
		}	
	}
	
	public void recalcularCompra() {
		if (this.compra != null) {
			this.compra.recalcularValorTotal();
		}
	}
	
	public void carregarProdutoLinhaEditavel() {
		ItemCompra item = this.compra.getItens().get(0);
		
		if(this.produtoLinhaEditavel != null) {
			if(this.existeItemComProduto(this.produtoLinhaEditavel)) {
				FacesUtil.addErrorMessage("Produto Já adicionado na compra");
			}else {
			item.setProduto(this.produtoLinhaEditavel);
			item.setValorUnitario(this.produtoLinhaEditavel.getPreco());
			
			this.compra.adicionarItemVazio();
			this.produtoLinhaEditavel=null;
			
			this.compra.recalcularValorTotal();
		}
	}
}

	private boolean existeItemComProduto(Produto produto) {
		boolean existeItem = false;
		
		for(ItemCompra item: this.getCompra().getItens()) {
			if(produto.equals(item.getProduto())) {
				existeItem = true;
				break;
			}
		}
		return existeItem;
	}

	public void salvar() {
		this.compra.removerItemVazio();
		try {
	
			
			this.compra = this.cadastroCompraService.salvar(this.compra);
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage ("Compra Efetuada Com Sucesso!!!"));
		}catch(Exception e) {
			FacesUtil.addErrorMessage("Ocorreu um problema");
			e.printStackTrace();
			
		}
	}
	

	public List<Produto> completarProduto(String nome){
		return this.produtoDAO.porNome(nome);
	}
	
	public void atualizarQuantidade(ItemCompra item, int linha) {
		if(item.getQuantidade() <1) {
			if(linha == 0) {
				item.setQuantidade(1);
			}else {
				this.getCompra().getItens().remove(linha);
			}
		}
		this.compra.recalcularValorTotal();
	}
	
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	public Produto getProdutoLinhaEditavel() {
		return produtoLinhaEditavel;
	}
	public void setProdutoLinhaEditavel(Produto produtoLinhaEditavel) {
		this.produtoLinhaEditavel = produtoLinhaEditavel;
	}
}
