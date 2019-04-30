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

import org.apache.commons.lang3.StringUtils;

import com.algaworks.pedidovenda.model.Estoque;
import com.algaworks.pedidovenda.repository.filters.EstoqueFilter;

public class EstoqueDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void salvar(Estoque estoque) {
		try {
			manager.getTransaction().begin();
			manager.merge(estoque);
			manager.getTransaction().commit();
		} catch (Exception e) {
			manager.getTransaction().rollback();
		}
	}

	public List<Estoque> consulta() {
		return this.manager.createQuery("from Estoque", Estoque.class).getResultList();
	}

	public Estoque porId(Integer idEstoque) {
		return manager.find(Estoque.class, idEstoque);
	}

	public Estoque porIdProduto(Integer idProduto) {
		return manager.createQuery("from Estoque where produto.idProduto = :idProduto", Estoque.class)
				.setParameter("idProduto", idProduto).getSingleResult();
	}


	public List<Estoque> filtrados(EstoqueFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Estoque> criteriaQuery = builder.createQuery(Estoque.class);
		List<Predicate> predicates = new ArrayList<>();
		
		Root<Estoque> estoqueRoot = criteriaQuery.from(Estoque.class);
		
		if (filtro.getIdEstoque() != null) {
			predicates.add(builder.equal(estoqueRoot.get("idEstoque"), filtro.getIdEstoque()));
		}
		
		if (StringUtils.isNotBlank(filtro.getNomeFantasia())) {
			predicates.add(builder.like(builder.lower(estoqueRoot.get("fornecedor").get("nomeFantasia")), 
					"%" + filtro.getNomeFantasia().toLowerCase() + "%"));
		}
		
		if (StringUtils.isNotBlank(filtro.getNome())) {
			predicates.add(builder.like(builder.lower(estoqueRoot.get("produto").get("nome")), 
					"%" + filtro.getNome().toLowerCase() + "%"));
		}
		criteriaQuery.select(estoqueRoot);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		criteriaQuery.orderBy(builder.asc(estoqueRoot.get("idEstoque")));
		
		TypedQuery<Estoque> query = manager.createQuery(criteriaQuery);
		return query.getResultList();
		
	}
}
