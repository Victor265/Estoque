package com.algaworks.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.algaworks.pedidovenda.model.Compra;
import com.algaworks.pedidovenda.repository.CompraDAO;

@FacesConverter(forClass = Compra.class)
public class CompraConverter implements Converter {

	@Inject private CompraDAO compraDAO;
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Compra retorno = null;
		
		if (StringUtils.isNotEmpty(value)) {
		  Integer id = new Integer(value);
			retorno = compraDAO.porId(id);
		}
		
		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Compra compra = (Compra) value;
			return compra.getIdCompra() == null ? null : compra.getIdCompra().toString();
		}
		
		return "";
	}

}
