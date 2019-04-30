package com.algaworks.pedidovenda.service;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Inject;

import com.algaworks.pedidovenda.model.Compra;
import com.algaworks.pedidovenda.repository.CompraDAO;
import com.algaworks.pedidovenda.util.jpa.Transactional;

public class CadastroCompraService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CompraDAO compraDAO;
	
	@Transactional
	public Compra salvar(Compra compra) {
		if (compra.isNovo()) {
			compra.setDataCompra(new Date());
			
		}
	
	
	if(compra.getItens().isEmpty()) {
		throw new NegocioException("A compra está vazia");
	}
	
	if(compra.isValorTotalNegativo()) {
		throw new NegocioException("O Valor da Compra não pode ser Negativa");
	}
	
	return compraDAO.salvar(compra);
	
}
}