package br.jus.trece.blank.business;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import br.jus.trece.api.business.GenericoBusiness;
import br.jus.trece.api.domain.Generico;
import br.jus.trece.blank.client.LogClient;
import br.jus.trece.blank.dao.LinkDao;
import br.jus.trece.blank.domain.Link;
import br.jus.trece.blank.enumeration.LogEnum;
import br.jus.trece.blank.util.LogUtil;

/**
 * classe de negócio de link.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Stateless
public class LinkBusiness extends GenericoBusiness<Link, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	LinkDao linkDao;

	@Inject
	LogClient logClient;

	@Override
	@PostConstruct
	protected void init() {
		this.genericoDao = linkDao;
	}

	/**
	 * inclui link.
	 * loga log.
	 * 
	 * @param link
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void incluir(Link link) {
		super.incluir(link);

		// loga log.
		logClient.logar(LogUtil.getLog(link, LogEnum.INCLUIR_LINK));
	}

	/**
	 * altera link.
	 * loga log.
	 * 
	 * @param link
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void alterar(Link link) {
		super.alterar(link);

		// loga log.
		logClient.logar(LogUtil.getLog(link, LogEnum.ALTERAR_LINK));
	}

	/**
	 * exclui link.
	 * loga log.
	 * 
	 * @param id
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void excluir(Long id) {
		super.excluir(id);

		// loga log.
		logClient.logar(LogUtil.getLog(new Generico<Long>(id), LogEnum.EXCLUIR_LINK));
	}

	/**
	 * lista link ativo.
	 * 
	 * @return lista de link ativo
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Link> listarAtivo() {
		return linkDao.listarAtivo();
	}

}
