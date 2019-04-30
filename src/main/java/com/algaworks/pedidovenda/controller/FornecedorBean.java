package com.algaworks.pedidovenda.controller;

import java.io.Serializable;

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
public class FornecedorBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Fornecedor fornecedor;
	@Inject
	private FornecedorDAO fornecedorDAO;

	@PostConstruct
	public void init() {
		String codigo_Fornecedor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
				.get("idFornecedor");

		if (codigo_Fornecedor != null) {
			int codigo_Fornecedor2 = Integer.parseInt(codigo_Fornecedor);

			fornecedor = fornecedorDAO.porId(codigo_Fornecedor2);
		} else {
			fornecedor = new Fornecedor();
		}
	}

	public void salvar() {
		try {
			fornecedorDAO.salvar(fornecedor);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fornecedor Cadastrado com Sucesso"));
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

}
