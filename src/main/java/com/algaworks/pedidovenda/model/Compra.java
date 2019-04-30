package com.algaworks.pedidovenda.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="compra")
public class Compra implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idCompra;
	
	
	
	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ItemCompra> itens = new ArrayList<>();
	

	private BigDecimal valorTotal = BigDecimal.ZERO;
	private Date dataCompra;
	private String formaPagamento;
	

	public void adicionarItemVazio() {
		Produto produto = new Produto();
		ItemCompra item = new ItemCompra();
		
		item.setProduto(produto);
		item.setCompra(this);
		
		
		
		this.getItens().add(0, item);
	}
	
	public void recalcularValorTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		for (ItemCompra item : this.getItens()) {
			if (item.getProduto() != null ) {
				total = total.add(item.getValorTotal());
			}
		}
		
		this.setValorTotal(total);
	}
	
	public Integer getIdCompra() {
		return idCompra;
	}
	public void setIdCompra(Integer idCompra) {
		this.idCompra = idCompra;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Date getDataCompra() {
		return dataCompra;
	}
	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}
	
	public String getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	public List<ItemCompra> getItens() {
		return itens;
	}
	public void setItens(List<ItemCompra> itens) {
		this.itens = itens;
	}
	
	
	@Transient
	public boolean isNovo() {
		return getIdCompra() == null  ;
	}
	
	@Transient
	public boolean isExistente() {
		return isNovo();
	}
	
	public void removerItemVazio() {
		ItemCompra primeiroItem = this.getItens().get(0);
		
		if(primeiroItem !=null && primeiroItem.getProduto().getIdProduto() ==null) {
			this.getItens().remove(0);
			
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCompra == null) ? 0 : idCompra.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Compra other = (Compra) obj;
		if (idCompra == null) {
			if (other.idCompra != null)
				return false;
		} else if (!idCompra.equals(other.idCompra))
			return false;
		return true;
	}

	@Transient
	public boolean isValorTotalNegativo() {
		return this.getValorTotal().compareTo(BigDecimal.ZERO ) < 0;
	}
}
