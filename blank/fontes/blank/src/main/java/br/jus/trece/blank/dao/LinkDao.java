package br.jus.trece.blank.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import br.jus.trece.api.dao.GenericoDao;
import br.jus.trece.blank.domain.Link;

/**
 * classe DAO de link.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public class LinkDao extends GenericoDao<Link, Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * lista link ativo.
	 * 
	 * @return lista de link ativo
	 */
	public List<Link> listarAtivo() {
		String sql = "from Link l where l.ativo is true order by l.data desc";

		TypedQuery<Link> query = (TypedQuery<Link>) em.createQuery(sql, getTipo());

		List<Link> result = query.getResultList();

		return result;
	}

}
