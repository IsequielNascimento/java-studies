package br.jus.trece.blank.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import br.jus.trece.api.dao.GenericoDao;
import br.jus.trece.api.domain.pandora.Perfil;

/**
 * classe DAO de perfil.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public class PerfilDao extends GenericoDao<Perfil, Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * lista perfil.
	 * 
	 * @param sistema
	 * @param descricoes
	 * @return lista de perfil
	 */
	public List<Perfil> listar(Long sistema, List<String> descricoes) {
		String sql = "select distinct p from Perfil p "
				   + "left join fetch p.unidadesDados usds "
				   + "left join fetch usds.zona z where p.sistema.id = :sistema and p.descricao in :descricoes order by p.descricao";
		
		TypedQuery<Perfil> query = (TypedQuery<Perfil>) em.createQuery(sql, getTipo());

		query.setParameter("sistema", sistema);
		query.setParameter("descricoes", descricoes);

		List<Perfil> result = query.getResultList();

		return result;
	}

}
