package com.algaworks.pedidovenda.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.pedidovenda.model.ItemCompra;

public class ItemDAO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject private EntityManager manager;
	
	public ItemCompra porIdProduto(Integer idProduto) {
		return manager.createQuery("from ItemCompra where produto.idProduto = :idProduto", ItemCompra.class)
				.setParameter("idProduto", idProduto).getSingleResult();
	}


}
