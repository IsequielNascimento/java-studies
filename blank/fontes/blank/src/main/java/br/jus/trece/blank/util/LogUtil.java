package br.jus.trece.blank.util;

import java.util.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.jus.trece.api.domain.pandora.Log;
import br.jus.trece.blank.enumeration.LogEnum;

/**
 * classe utilitária para log.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public class LogUtil {

	public static Log getLog(Object objeto, LogEnum tipo) {
		Log log = new Log();
		log.setSistema(FacesUtil.getSessaoBean().getParametro().getSistema());
		log.setOperacao(tipo.getId());
		log.setLogin(FacesUtil.getSessaoBean().getUsuario().getLogin());
		log.setMatricula(FacesUtil.getSessaoBean().getUsuario().getMatricula());
		log.setUnidade(FacesUtil.getSessaoBean().getUsuario().getUnidade());
		log.setData(new Date());
		try {
			log.setDescricao(new ObjectMapper().writeValueAsString(objeto));
		} catch (JsonProcessingException e) {
			log.setDescricao(null);
		}

		return log;
	}

}
