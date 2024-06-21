package br.jus.trece.blank.dao;

import java.util.List;

import javax.persistence.Query;

import br.jus.trece.api.dao.GenericoDao;
import br.jus.trece.blank.domain.Parametro;

/**
 * classe DAO de parâmetro.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public class ParametroDao extends GenericoDao<Parametro, Integer> {

	private static final long serialVersionUID = 1L;

	/**
	 * busca parâmetro.
	 * 
	 * @return parâmetro
	 */
	public Parametro buscar() {
		String sql = "from Parametro p "
				   + "inner join fetch p.sistema s "
				   + "left join fetch s.gestor g "
				   + "left join fetch g.unidadePai up "
				   + "left join fetch up.unidadePai upa "
				   + "left join fetch s.gestorTecnico gt";

		Query query = em.createQuery(sql);

		return (Parametro) query.getSingleResult();
	}
	
	/**
	 * emite parâmetro.
	 * 
	 * @return lista de parâmetro
	 */
	@SuppressWarnings("unchecked")
	public List<Parametro> emitir() {
		String sql = "from Parametro p";

		Query query = em.createQuery(sql);

		List<Parametro> result = query.getResultList();

		return result;
	}

}
