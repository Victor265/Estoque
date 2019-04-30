package com.algaworks.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.algaworks.pedidovenda.model.Fornecedor;
import com.algaworks.pedidovenda.repository.FornecedorDAO;
import com.algaworks.pedidovenda.util.cdi.CDIServiceLocator;


@FacesConverter(forClass=Fornecedor.class)
public class FornecedorConverter implements Converter{
	
	private FornecedorDAO fornecedorDAO;
	
	public FornecedorConverter() {
		fornecedorDAO = CDIServiceLocator.getBean(FornecedorDAO.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Fornecedor retorno = null;
		
		if(value != null) {
			Integer id = Integer.parseInt(value);
			retorno = fornecedorDAO.porId(id);
		}
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value!=null) {
			Fornecedor fornecedor = (Fornecedor) value;
			return fornecedor.getIdFornecedor().toString();
			
		}
		return"";
	}
}
