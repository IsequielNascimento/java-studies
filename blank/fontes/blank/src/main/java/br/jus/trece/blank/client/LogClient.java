package br.jus.trece.blank.client;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.ViewExpiredException;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.jus.trece.api.domain.pandora.Log;
import br.jus.trece.blank.enumeration.RespostaEnum;
import br.jus.trece.blank.exception.RespostaException;
import br.jus.trece.blank.util.FacesUtil;
import br.jus.trece.lib.util.StringUtil;

/**
 * cliente web service de log.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@RequestScoped
public class LogClient {

	/**
	 * loga log.
	 * 
	 * @param log
	 * @return código de operação
	 */
	public int logar(Log log) {
		if (StringUtil.isPreenchida(FacesUtil.getSessaoBean().getParametro().getUrlLogTre())) {
			Client client = Client.create();
			
			WebResource webResource = client.resource(FacesUtil.getSessaoBean().getParametro().getUrlLogTre()
					+ "/logs");
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
												 .header("Authorization", FacesUtil.getSessaoBean().getToken().toString())
												 .post(ClientResponse.class, log.getAsJson());
	
			// recurso criado.
			if (response.getStatus() == RespostaEnum.CRIADO.getId()) {
				return response.getStatus();
			// token expirado.
			} else if (response.getStatus() == RespostaEnum.RECURSO_PROIBIDO.getId()) {
				throw new ViewExpiredException();
			// erro geral.
			} else {
				throw new RespostaException(response.getStatus());
			}
		} else {
			// url vazia. 
			return RespostaEnum.REQUISICAO_INVALIDA.getId();
		}
	}

}
