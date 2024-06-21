package br.jus.trece.blank.business;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.jus.trece.api.business.GenericoBusiness;
import br.jus.trece.api.domain.pandora.Perfil;
import br.jus.trece.blank.client.LogClient;
import br.jus.trece.blank.dao.PerfilDao;

/**
 * classe de negócio de perfil.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Stateless
public class PerfilBusiness extends GenericoBusiness<Perfil, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	PerfilDao perfilDao;

	@Inject
	LogClient logClient;

	@Override
	@PostConstruct
	protected void init() {
		this.genericoDao = perfilDao;
	}

	/**
	 * lista perfil.
	 * 
	 * @param sistema
	 * @param descricoes
	 * @return lista de perfil
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Perfil> listar(Long sistema, List<String> descricoes) {
		return perfilDao.listar(sistema, descricoes);
	}

}
