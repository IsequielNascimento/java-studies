package br.jus.trece.blank.client;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.ViewExpiredException;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.jus.trece.api.domain.pandora.Erro;
import br.jus.trece.blank.enumeration.RespostaEnum;
import br.jus.trece.blank.exception.RespostaException;
import br.jus.trece.blank.util.FacesUtil;
import br.jus.trece.lib.util.StringUtil;

/**
 * cliente web service de erro.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@RequestScoped
public class ErroClient {

	/**
	 * loga erro.
	 * 
	 * @param erro
	 * @return código de operação
	 */
	public int logar(Erro erro) {
		if (StringUtil.isPreenchida(FacesUtil.getSessaoBean().getParametro().getUrlLogTre())) {
			Client client = Client.create();
			
			WebResource webResource = client.resource(FacesUtil.getSessaoBean().getParametro().getUrlLogTre()
					+ "/erros");
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
												 .header("Authorization", FacesUtil.getSessaoBean().getToken().toString())
												 .post(ClientResponse.class, erro.getAsJson());
	
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
