package br.com.project.bean.geral;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.web.context.request.FacesRequestAttributes;

/*
 * A classe ViewScope é um objeto de escopo que é implementado com base na interface Scope do 
 * 
 *Spring Framework. Ele é usado para gerenciar o estado de objetos em uma aplicação JSF (JavaServer Faces). 
 *Ele define o escopo da visualização, o que significa que os objetos registrados com esse escopo só são acessí
 *veis durante a exibição da página atual. Ele implementa métodos para obter, remover, registrar 
 * e resolver objetos contextualmente no escopo da visualização.
 * 
 * */

public class ViewScope implements Scope, Serializable {

	private static final long serialVersionUID = 1L;
	public static final String VIEW_SCOPE_CALLBACKS = "viewScope.callBacks";

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Object instance = getViewMap().get(name);
		if (instance == null) {
			instance = objectFactory.getObject();
			getViewMap().put(name, instance);
		}

		return instance;
	}

	@Override
	public String getConversationId() {

		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAtributtes = new FacesRequestAttributes(facesContext);

		return facesRequestAtributtes.getSessionId() + " - " + facesContext.getViewRoot().getViewId();
	}

	@Override
	public void registerDestructionCallback(String name, Runnable runnable) {
		Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
		if (callbacks != null) {
			callbacks.put(VIEW_SCOPE_CALLBACKS, runnable);
		}
	}

	@Override
	public Object remove(String name) {
		Object instance = getViewMap().remove(name);
		if (instance != null) {
			Map<String, Runnable> callbacks = (Map<String, Runnable>) getViewMap().get(VIEW_SCOPE_CALLBACKS);
			if (callbacks != null) {
				callbacks.remove(name);
			}
		}

		return instance;
	}

	@Override
	public Object resolveContextualObject(String name) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesRequestAttributes facesRequestAttributes = new FacesRequestAttributes(facesContext);
		return facesRequestAttributes.resolveReference(name);
	}

	// getViewRoot
	// Retorna o componente raiz que está associado a esta solicitação , Request
	// getViewMap retorna um map que atua como a interface para o armazenamento de
	// dados
	private Map<String, Object> getViewMap() {
		return FacesContext.getCurrentInstance() != null ? FacesContext.getCurrentInstance().getViewRoot().getViewMap()
				: new HashMap<String, Object>();
	}
}
