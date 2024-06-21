package br.jus.trece.blank.client;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.jus.trece.api.domain.Token;
import br.jus.trece.blank.enumeration.RespostaEnum;
import br.jus.trece.blank.exception.AutenticacaoException;
import br.jus.trece.blank.exception.RespostaException;
import br.jus.trece.blank.util.FacesUtil;
import br.jus.trece.lib.util.StringUtil;

/**
 * cliente web service de autenticação.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@RequestScoped
public class AutenticacaoClient {

	/**
	 * autentica usuário.
	 * 
	 * @param login
	 * @param senha
	 * @return token
	 */
	public Token autenticar(String login, String senha) {
		if (StringUtil.isPreenchida(FacesUtil.getSessaoBean().getParametro().getUrlSegurancaTre())) {
			Client client = Client.create();
			
			WebResource webResource = client.resource(FacesUtil.getSessaoBean().getParametro().getUrlSegurancaTre()
					+ "/autenticar");
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
												 .post(ClientResponse.class, getUsuarioJson(login, senha));
	
			// resposta ok.
			if (response.getStatus() == RespostaEnum.OK.getId()) {
				JsonObject jo = new Gson().fromJson(response.getEntity(String.class), JsonObject.class);
	
				return new Gson().fromJson(jo, new TypeToken<Token>() {}.getType());
			// erro de autenticação.
			} else if (response.getStatus() == RespostaEnum.NAO_AUTORIZADO.getId()) {
				throw new AutenticacaoException();
			// erro geral.
			} else {
				throw new RespostaException(response.getStatus());
			}
		} else {
			// url vazia. 
			return null;
		}
	}
	
	private String getUsuarioJson(String login, String senha) {
		JsonObject jo = new JsonObject();
		jo.addProperty("login", login);
		jo.addProperty("senha", senha);

		return new Gson().toJson(jo, JsonObject.class);
	}

}
