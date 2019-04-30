package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.pedidovenda.model.Fornecedor;

public class FornecedorDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject private EntityManager manager;
	
	public void salvar(Fornecedor fornecedor) {
		try {
			manager.getTransaction().begin();
			manager.merge(fornecedor);
			manager.getTransaction().commit();
		}catch(Exception e) {
			manager.getTransaction().rollback();
		}
	}
	
	public List<Fornecedor> fornecedores(){
		return this.manager.createQuery("from Fornecedor",Fornecedor.class).getResultList();
	}

	
	public Fornecedor porId(Integer idFornecedor) {
		return manager.find(Fornecedor.class, idFornecedor);
	}
	
	public void excluir(Integer id) {

		try {
			manager.getTransaction().begin();
			Fornecedor fornecedor = manager.find(Fornecedor.class, id);
			
			manager.remove(fornecedor);
			manager.getTransaction().commit();

		} catch (Exception e) {
			manager.getTransaction().rollback();
			throw e;
		}

	}
}
