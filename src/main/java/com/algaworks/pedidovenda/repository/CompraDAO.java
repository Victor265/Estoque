package com.algaworks.pedidovenda.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.algaworks.pedidovenda.model.Compra;
import com.algaworks.pedidovenda.repository.filters.CompraFilter;

public class CompraDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public Compra salvar(Compra compra) {
		Compra compraInserir = null;
		try{

		manager.getTransaction().begin();
		compraInserir = manager.merge(compra);
		manager.getTransaction().commit();
		return compraInserir;
		
		}catch(Exception e) {
			manager.getTransaction().rollback();
		}
		return compraInserir;
	}

	public Compra porIdProduto(Integer idProduto) {
		return manager.createQuery("from Compra where produto.idProduto = :idProduto", Compra.class)
				.setParameter("idProduto", idProduto).getSingleResult();
	}

	public Compra porId(Integer idCompra) {
		return manager.find(Compra.class, idCompra);
	}

	public List<Compra> consulta() {
		return this.manager.createQuery("from Compra", Compra.class).getResultList();
	}

	public void excluir(Integer id) {
		try {
			manager.getTransaction().begin();
			Compra compra = manager.find(Compra.class, id);
			
			manager.remove(compra);
			manager.getTransaction().commit();
		}catch(Exception e) {
			manager.getTransaction().rollback();
			throw e;
		}
	}
	
	public List<Compra> filtrados(CompraFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Compra> criteriaQuery = builder.createQuery(Compra.class);
		List<Predicate> predicates = new ArrayList<>();

		Root<Compra> compraRoot = criteriaQuery.from(Compra.class);

		if (filtro.getIdCompra() != null) {
			predicates.add(builder.equal(compraRoot.get("idCompra"), filtro.getIdCompra()));
		}

		if (filtro.getDataCompra() !=null) {
			predicates.add(builder.equal(compraRoot.get("dataCompra"),filtro.getDataCompra()));
		}

		criteriaQuery.select(compraRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(compraRoot.get("idCompra")));

		TypedQuery<Compra> query = manager.createQuery(criteriaQuery);
		return query.getResultList();

	}
}
