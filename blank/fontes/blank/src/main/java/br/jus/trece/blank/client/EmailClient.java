package br.jus.trece.blank.client;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.jus.trece.api.domain.Email;
import br.jus.trece.api.enumeration.FormatoEmailEnum;
import br.jus.trece.blank.enumeration.RespostaEnum;
import br.jus.trece.blank.exception.RespostaException;
import br.jus.trece.blank.util.FacesUtil;
import br.jus.trece.lib.util.StringUtil;

/**
 * cliente web service de e-mail.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@RequestScoped
public class EmailClient {

	/**
	 * envia e-mail.
	 * 
	 * @param email
	 * @param formato
	 * @return código de operação
	 */
	public int enviar(Email email, FormatoEmailEnum formato) {
		if (StringUtil.isPreenchida(FacesUtil.getSessaoBean().getParametro().getUrlEmailTre())) {
			Client client = Client.create();
			
			WebResource webResource = client.resource(FacesUtil.getSessaoBean().getParametro().getUrlEmailTre()
					+ "/enviar");
			ClientResponse response = webResource.queryParam("formato", formato.getId())
												 .type(MediaType.APPLICATION_JSON)
												 .post(ClientResponse.class, email.getAsJson());
	
			// resposta ok.
			if (response.getStatus() == RespostaEnum.OK.getId()) {
				return response.getStatus();
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
