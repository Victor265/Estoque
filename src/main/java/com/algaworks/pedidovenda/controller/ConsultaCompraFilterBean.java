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
import com.algaworks.pedidovenda.repository.CompraDAO;
import com.algaworks.pedidovenda.repository.ProdutoDAO;
import com.algaworks.pedidovenda.repository.filters.CompraFilter;

@Named
@ViewScoped
public class ConsultaCompraFilterBean implements Serializable {

	private static final long serialVersionUID=1L;
	
	@Inject CompraDAO compraDAO;
	@Inject ProdutoDAO produtoDAO;
	private CompraFilter filtro;
	private List<Compra> compras;
	
	

	@PostConstruct
	public void init() {
		filtro = new CompraFilter();
		pesquisaCompras();
	}

	private void pesquisaCompras() {
		compras = compraDAO.consulta();
	}
	
	public void listarTodosProdutos() {
		compras = compraDAO.consulta();
	}
	public void pesquisar() {
	
		try {
		compras=compraDAO.filtrados(filtro);
		}catch(NullPointerException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocorreu um problema!!"));
			e.printStackTrace();
		}
	}
	
	public void excluir(Compra compra) {
		try {
			
			compraDAO.excluir(compra.getIdCompra());
			pesquisaCompras();
				
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Compra Excluida com Sucesso"));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocorreu um problema "));
			e.printStackTrace();
		}
	}
	
	public void listarTodasCompras() {
		pesquisaCompras();
	}
	
	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public CompraFilter getFiltro() {
		return filtro;
	}
	
	public void setFiltro(CompraFilter filtro) {
		this.filtro=filtro;
	}

}
