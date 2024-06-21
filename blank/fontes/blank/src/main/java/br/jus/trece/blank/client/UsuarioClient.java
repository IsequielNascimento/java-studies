package br.jus.trece.blank.client;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.ViewExpiredException;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.jus.trece.api.domain.corporativo.Usuario;
import br.jus.trece.blank.enumeration.RespostaEnum;
import br.jus.trece.blank.exception.RespostaException;
import br.jus.trece.blank.util.FacesUtil;
import br.jus.trece.lib.util.StringUtil;

/**
 * cliente web service de usuário.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@RequestScoped
public class UsuarioClient {

	/**
	 * busca usuário por id.
	 * 
	 * @param matricula
	 * @return usuário
	 */
	public Usuario buscarPorId(String matricula) {
		if (StringUtil.isPreenchida(FacesUtil.getSessaoBean().getParametro().getUrlEntidadeTre())) {
			Client client = Client.create();
			
			WebResource webResource = client.resource(FacesUtil.getSessaoBean().getParametro().getUrlEntidadeTre()
					+ "/usuarios/" + matricula);
			ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
												 .header("Authorization", FacesUtil.getSessaoBean().getToken().toString())
												 .get(ClientResponse.class);
	
			// resposta ok.
			if (response.getStatus() == RespostaEnum.OK.getId()) {
				JsonObject jo = new Gson().fromJson(response.getEntity(String.class), JsonObject.class);
	
				return new Gson().fromJson(jo, new TypeToken<Usuario>() {}.getType());
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
