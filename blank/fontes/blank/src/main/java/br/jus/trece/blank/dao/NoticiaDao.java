package br.jus.trece.blank.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import br.jus.trece.api.dao.GenericoDao;
import br.jus.trece.blank.domain.Noticia;

/**
 * classe DAO de notícia.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public class NoticiaDao extends GenericoDao<Noticia, Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * lista notícia ativa.
	 * 
	 * @return lista de notícia ativa
	 */
	public List<Noticia> listarAtiva() {
		String sql = "from Noticia n where n.ativa is true order by n.data desc";

		TypedQuery<Noticia> query = (TypedQuery<Noticia>) em.createQuery(sql, getTipo());

		List<Noticia> result = query.getResultList();

		return result;
	}

}
