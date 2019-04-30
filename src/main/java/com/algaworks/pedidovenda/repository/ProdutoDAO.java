package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.algaworks.pedidovenda.model.Produto;

public class ProdutoDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject private EntityManager manager;
	
	public void salvar(Produto produto) {
		try {
			manager.getTransaction().begin();
			manager.merge(produto);
			manager.getTransaction().commit();
		}catch(Exception e) {
			manager.getTransaction().rollback();
		}
	}
	
	public List<Produto> consulta(){
		return this.manager.createQuery("from Produto",Produto.class).getResultList();
	}
	
	public List<Produto> consultaDisponivelEstoque(){
		return this.manager.createQuery("from Produto p left join fetch p.estoque e where e.quantidade > 0",Produto.class).getResultList();
	}

	
	public List<Produto> porNome(String nome){
		return this.manager.createQuery("from Produto where upper(nome) like :nome",Produto.class )
				.setParameter("nome", nome.toUpperCase() + "%")
				.getResultList();
	}
	
	
	public Produto porId(Integer idProduto) {
		return manager.find(Produto.class, idProduto);
	}
	
	public void excluir(Integer id) {

		try {
			manager.getTransaction().begin();
			Produto produto = manager.find(Produto.class, id);
			
			manager.remove(produto);
			manager.getTransaction().commit();

		} catch (Exception e) {
			manager.getTransaction().rollback();
			throw e;
		}

	}

}
