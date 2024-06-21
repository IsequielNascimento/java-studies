package br.jus.trece.blank.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * gerador de manipulador de exceção.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;

	@SuppressWarnings("deprecation")
	public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		ExceptionHandler handler = new CustomExceptionHandler(this.parent.getExceptionHandler());

		return handler;
	}

}
