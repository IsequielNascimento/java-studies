package br.jus.trece.blank.exception;

import javax.ejb.ApplicationException;

/**
 * exceção de banco de dados.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@ApplicationException(rollback = true)
public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DatabaseException(Throwable erro) {
		super(erro);
	}
	
}
