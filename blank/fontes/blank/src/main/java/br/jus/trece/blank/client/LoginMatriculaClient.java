package br.jus.trece.blank.client;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.ViewExpiredException;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.jus.trece.blank.enumeration.RespostaEnum;
import br.jus.trece.blank.exception.RespostaException;
import br.jus.trece.blank.util.FacesUtil;
import br.jus.trece.lib.util.StringUtil;

/**
 * cliente web service de login/matrícula.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@RequestScoped
public class LoginMatriculaClient {

	/**
	 * busca matrícula de usuário por login.
	 * 
	 * @param login
	 * @return matrícula de usuário
	 */
	public Integer buscarMatriculaPorLogin(String login) {
		if (StringUtil.isPreenchida(FacesUtil.getSessaoBean().getParametro().getUrlSegurancaTre())) {
			Client client = Client.create();
			
			WebResource webResource = client.resource(FacesUtil.getSessaoBean().getParametro().getUrlSegurancaTre()
					+ "/matricula");
			ClientResponse response = webResource.queryParam("login", login)
												 .type(MediaType.APPLICATION_JSON)
												 .get(ClientResponse.class);
	
			// resposta ok.
			if (response.getStatus() == RespostaEnum.OK.getId()) {
				JsonPrimitive jp = new Gson().fromJson(response.getEntity(String.class), JsonPrimitive.class);
	
				return new Gson().fromJson(jp, new TypeToken<Integer>() {}.getType());
			// token expirado.
			} else if (response.getStatus() == RespostaEnum.RECURSO_PROIBIDO.getId()) {
				throw new ViewExpiredException();
			// resposta vazia.
			} else if (response.getStatus() == RespostaEnum.NENHUM_DADO_ENCONTRADO.getId()) {
				return null;
			// erro geral.
			} else {
				throw new RespostaException(response.getStatus());
			}
		} else {
			// url vazia. 
			return null;
		}
	}

}
