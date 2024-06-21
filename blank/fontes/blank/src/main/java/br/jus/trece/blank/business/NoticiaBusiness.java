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
import br.jus.trece.blank.dao.NoticiaDao;
import br.jus.trece.blank.domain.Noticia;
import br.jus.trece.blank.enumeration.LogEnum;
import br.jus.trece.blank.util.LogUtil;

/**
 * classe de negócio de notícia.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Stateless
public class NoticiaBusiness extends GenericoBusiness<Noticia, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	NoticiaDao noticiaDao;

	@Inject
	LogClient logClient;

	@Override
	@PostConstruct
	protected void init() {
		this.genericoDao = noticiaDao;
	}

	/**
	 * inclui notícia.
	 * loga log.
	 * 
	 * @param noticia
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void incluir(Noticia noticia) {
		super.incluir(noticia);

		// loga log.
		logClient.logar(LogUtil.getLog(noticia, LogEnum.INCLUIR_NOTICIA));
	}

	/**
	 * altera notícia.
	 * loga log.
	 * 
	 * @param noticia
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void alterar(Noticia noticia) {
		super.alterar(noticia);

		// loga log.
		logClient.logar(LogUtil.getLog(noticia, LogEnum.ALTERAR_NOTICIA));
	}

	/**
	 * exclui notícia.
	 * loga log.
	 * 
	 * @param id
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void excluir(Long id) {
		super.excluir(id);

		// loga log.
		logClient.logar(LogUtil.getLog(new Generico<Long>(id), LogEnum.EXCLUIR_NOTICIA));
	}

	/**
	 * lista notícia ativa.
	 * 
	 * @return lista de notícia ativa
	 */
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Noticia> listarAtiva() {
		return noticiaDao.listarAtiva();
	}

}
