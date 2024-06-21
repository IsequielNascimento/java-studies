package br.jus.trece.blank.client;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.ViewExpiredException;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import br.jus.trece.blank.enumeration.RespostaEnum;
import br.jus.trece.blank.exception.RespostaException;
import br.jus.trece.blank.util.FacesUtil;
import br.jus.trece.lib.util.StringUtil;

/**
 * cliente web service de unidade.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@RequestScoped
public class UnidadeClient {

	/**
	 * lista unidade para autoridade por matrícula.
	 * 
	 * @param matricula
	 * @return lista de unidade para autoridade
	 */
	public List<String> listarParaAutoridadePorMatricula(String matricula) {
		if (StringUtil.isPreenchida(FacesUtil.getSessaoBean().getParametro().getUrlEntidadeTre())) {
			Client client = Client.create();
			
			WebResource webResource = client.resource(FacesUtil.getSessaoBean().getParametro().getUrlEntidadeTre()
					+ "/unidades" + "/autoridades");
			ClientResponse response = webResource.queryParam("matricula", matricula)
												 .type(MediaType.APPLICATION_JSON)
												 .header("Authorization", FacesUtil.getSessaoBean().getToken().toString())
												 .get(ClientResponse.class);
	
			// resposta ok.
			if (response.getStatus() == RespostaEnum.OK.getId()) {
				JsonArray ja = new Gson().fromJson(response.getEntity(String.class), JsonArray.class);
	
				return new Gson().fromJson(ja, new TypeToken<List<String>>() {}.getType());
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
