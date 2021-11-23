package com.br.dominio.util;

import javax.faces.context.FacesContext;

public class ApplicationMapUtil {

	public static Object getValueFromApplicationMap(String key) {
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(key);
	}
	
	public static Object setValueInApplicationMap(String key, Object value) {
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put(key, value);
	}
	
}
