package br.jus.trece.blank.exception;

import javax.ws.rs.WebApplicationException;

/**
 * exceção de resposta a web service.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public class RespostaException extends WebApplicationException {

	private static final long serialVersionUID = 1L;
	
	public RespostaException(int status) {
		super(status);
	}

}
