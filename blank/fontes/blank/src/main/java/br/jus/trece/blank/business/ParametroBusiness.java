package br.jus.trece.blank.business;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.jus.trece.api.business.GenericoBusiness;
import br.jus.trece.blank.dao.ParametroDao;
import br.jus.trece.blank.domain.Parametro;

/**
 * classe de negócio de parâmetro.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Stateless
public class ParametroBusiness extends GenericoBusiness<Parametro, Integer> {

	private static final long serialVersionUID = 1L;

	@Inject
	ParametroDao parametroDao;

	@Override
	@PostConstruct
	protected void init() {
		this.genericoDao = parametroDao;
	}

	/**
	 * busca parâmetro.
	 * 
	 * @return parâmetro
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Parametro buscar() {
		return parametroDao.buscar();
	}
	
	/**
	 * emite parâmetro.
	 * 
	 * @return lista de parâmetro
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Parametro> emitir() {
		return parametroDao.emitir();
	}

}
