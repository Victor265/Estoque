package com.algaworks.pedidovenda.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.algaworks.pedidovenda.model.Fornecedor;
import com.algaworks.pedidovenda.repository.FornecedorDAO;

@Named
@ViewScoped
public class ConsultaFornecedorBean implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject FornecedorDAO fornecedorDAO;
	private List<Fornecedor> fornecedores;
	private Fornecedor fornecedorSelecionado;
	
	@PostConstruct
	public void init() {
		setFornecedores(fornecedorDAO.fornecedores());

		
	}
	
	public void excluir() {
		try {
			
			fornecedorDAO.excluir(fornecedorSelecionado.getIdFornecedor());
			for(int i=0; i<fornecedores.size(); i++){
				if(fornecedores.get(i) == fornecedorSelecionado) {
					fornecedores.remove(i);
				}
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fornecedor Excluido com Sucesso"));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ocorreu um problema "));
			e.printStackTrace();
		}
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public Fornecedor getFornecedorSelecionado() {
		return fornecedorSelecionado;
	}

	public void setFornecedorSelecionado(Fornecedor fornecedorSelecionado) {
		this.fornecedorSelecionado = fornecedorSelecionado;
	}
}