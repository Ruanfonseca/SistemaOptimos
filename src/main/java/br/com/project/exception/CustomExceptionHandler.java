package br.com.project.exception;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.hibernate.SessionFactory;
import org.primefaces.context.RequestContext;

import br.com.framework.hibernate.session.HibernateUtil;

/*Esta classe, "CustomExceptionHandler", é um manipulador de exceções personalizado para o JSF 
 * (JavaServer Faces). Ele é uma subclasse de "ExceptionHandlerWrapper" e sobrescreve o método "handle"
 *  para lidar com exceções ocorridas no aplicativo. O construtor desta classe aceita uma instância de
 *   "ExceptionHandler" e a armazena como uma propriedade da classe. O método "handle" recupera todas as
 *    exceções não tratadas na pilha de eventos de exceções do JSF. A exceção é tratada verificando sua mensagem 
 *    e, de acordo com a mensagem, uma mensagem de erro adequada é exibida ao usuário via FacesContext. 
 *    Além disso, se a exceção for uma violação de chave estrangeira ou um objeto desatualizado, uma mensagem de 
 *    aviso é exibida ao usuário. Se a exceção for de outra natureza, uma mensagem de erro inesperado é exibida
 *     ao usuário, 
 * informando-o de que o erro foi corrigido e que ele pode continuar a usar o sistema normalmente.
 * */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapperd;
	final FacesContext facesContext = FacesContext.getCurrentInstance();
	final Map<String, Object> requestMap = facesContext.getExternalContext().getRequestMap();
	final NavigationHandler navigationHandler = facesContext.getApplication().getNavigationHandler();

	public CustomExceptionHandler(ExceptionHandler exceptionHandler) {
		this.wrapperd = exceptionHandler;
	}

	/* Sobrescreve o metodo ExceptionHandler que retorna a pilha de exceções */
	@Override
	public ExceptionHandler getWrapped() {
		return wrapperd;
	}

	/*
	 * Sobrescreve o metodo handle que é reponsavel por manipular as exceções do JSF
	 */
	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();

		while (iterator.hasNext()) {
			ExceptionQueuedEvent event = iterator.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			// recuperando exceção do contexto
			Throwable exception = context.getException();

			// aqui trabalhamos a exceção
			try {

				requestMap.put("exceptionMessage", exception.getMessage());

				// se der problema com chave estrangeira , esse método é disparado
				if (exception != null && exception.getMessage() != null
						&& exception.getMessage().indexOf("ConstraintViolationException") != -1) {

					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Registro não pode ser" + "removido" + "estar associdado.", ""));

				} else if (exception != null && exception.getMessage() != null
						&& exception.getMessage().indexOf("org.hibernate.StaleObjectStateException") != -1) {

					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Registro foi atualizado ou excluído por outro usuário" + "Consulte novamente.", ""));
				} else {
					// Avisa o usuário do erro
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"O sistema recuperou um erro" + "inesperado.", ""));

					// Tranquiliza o usuario para que ele continue usando o sistema
					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Você pode utilizar o sistema normalmente!", ""));

					FacesContext.getCurrentInstance().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"O erro foi causado por:\n" + exception.getMessage(), ""));

					// PrimeFaces
					// esse alert é exibido se a pagina não redirecionar
					RequestContext.getCurrentInstance()
							.execute("alert('O sistema se recuperou de um erro inesperado')");

					RequestContext.getCurrentInstance().showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Erro", "O sistema se recuperou " + "de um erro inesperado!"));

					// redireciona para pagina de erro
					navigationHandler.handleNavigation(facesContext, null,
							"/error/error.jsf?faces-redirect=true&expired=true");

				}
				// Renderiza a pagina de erro e exibe as mensagens
				facesContext.renderResponse();

			} finally {
				SessionFactory sf = HibernateUtil.getSessionFactory();
				if (sf.getCurrentSession().getTransaction().isActive()) {
					sf.getCurrentSession().getTransaction().rollback();
				}
				// imprime o erro no console
				exception.printStackTrace();
				iterator.remove();
			}
		}
		getWrapped().handle();
	}

}
