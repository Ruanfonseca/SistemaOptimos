package br.com.project.listener;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/*Esta classe é uma extensão da classe ContextLoaderListener do Spring Framework, e é marcada como
 *  @ApplicationScoped, o que significa que haverá apenas uma instância desta classe durante o ciclo de vida da 
 *  aplicação. A classe implementa dois métodos estáticos, getBean (String idNomeBean) e 
 *  getBean (Class<?> classe), que permitem a recuperação de beans registrados no contexto da aplicação
 *   através do método getWebApplicationContext.
 *  Esses métodos permitem que beans sejam recuperados do contexto da aplicação com base em seu nome ou classe.
 * 
 * */

@ApplicationScoped // instancia unica
public class ContextLoaderListenerCaixakiUtils extends ContextLoaderListener implements Serializable {

	private static final long serialVersionUID = 1L;

	private static WebApplicationContext getWac() {
		return WebApplicationContextUtils
				.getWebApplicationContext(getCurrentWebApplicationContext().getServletContext());
	}

	public static Object getBean(String idNomeBean) {
		return getWac().getBean(idNomeBean);
	}

	public static Object getBean(Class<?> classe) {
		return getWac().getBean(classe);
	}
}
