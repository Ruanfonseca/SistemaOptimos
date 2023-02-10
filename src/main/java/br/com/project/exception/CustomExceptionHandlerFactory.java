package br.com.project.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/*Esta classe representa uma fábrica de manipuladores de exceções personalizados para o 
 * framework JavaServer Faces. Ela herda da classe ExceptionHandlerFactory e fornece um manipulador de 
 * exceções personalizado que é criado a partir de um manipulador de exceções existente fornecido pelo 
 * manipulador de exceções pai. Quando uma exceção é lançada, o manipulador de exceções personalizado é 
 * responsável por capturá-la e tratá-la de acordo com as necessidades da aplicação. 
 * */

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;

	public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler handler = new CustomExceptionHandler(parent.getExceptionHandler());
		return handler;
	}

}
