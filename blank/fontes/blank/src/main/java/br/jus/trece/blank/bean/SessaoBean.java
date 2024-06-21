package br.jus.trece.blank.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import br.jus.trece.api.domain.Token;
import br.jus.trece.api.domain.corporativo.Usuario;
import br.jus.trece.api.domain.corporativo.Zona;
import br.jus.trece.api.domain.pandora.Perfil;
import br.jus.trece.blank.business.ParametroBusiness;
import br.jus.trece.blank.client.FuncionalidadeClient;
import br.jus.trece.blank.client.UnidadeClient;
import br.jus.trece.blank.client.ZonaClient;
import br.jus.trece.blank.domain.Parametro;
import br.jus.trece.blank.util.AmbienteUtil;
import br.jus.trece.lib.util.FacesUtil;

/**
 * bean de sessão de usuário.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Named
@SessionScoped
public class SessaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// dados de parâmetro.
	private Parametro parametro;

	// dados de token.
	private Token token;

	// dados de usuário.
	private Usuario usuario;
	
	private Perfil perfil = null;
	private List<Perfil> perfis = new ArrayList<Perfil>();

	private List<String> funcionalidades = new ArrayList<String>();
	
	private Zona zona = null;

	private List<String> unidadesParaAutoridade = null;
	private List<Integer> zonasParaAutoridade = null;

	private boolean logado = false;
	
	// dados de redirecionamento.
	private String redirecionamento;

	@Inject
	FuncionalidadeClient funcionalidadeClient;
	@Inject
	UnidadeClient unidadeClient;
	@Inject
	ZonaClient zonaClient;

	@EJB
	ParametroBusiness parametroBusiness;

	@PostConstruct
	protected void init() {
		try {
			this.parametro = parametroBusiness.buscar();
		} catch (Exception e) {
			e.printStackTrace();

			FacesUtil.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, AmbienteUtil.getMsgProperty("erro.PARAMETRO"), null));
		}
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	public Token getToken() {
		return token;
	}
	
	public void setToken(Token token) {
		this.token = token;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		
		this.logado = true;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
		
		if (this.perfil.getId() != null) {
			this.funcionalidades = funcionalidadeClient.listarDescricao(this.parametro.getSistema().getId(), this.perfil.getDescricao());
		}
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}
	
	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
		if (this.perfis.size() == 1) {
			setPerfil(this.perfis.get(0));
		} else {
			for (Perfil perfil : perfis) {
				if (perfil.getPadrao() != null && perfil.getPadrao()) {
					setPerfil(perfil);
					
					break;
				}
			}
		}
	}

	public List<String> getFuncionalidades() {
		return funcionalidades;
	}
	
	public Zona getZona() {
		if (zona == null) {
			zona = (this.perfil.getLotacaoUsuario() ? this.usuario.getZona()
					   								: this.perfil.getUnidadesDados().size() == 1 ? br.jus.trece.lib.util.ColecaoUtil.getAsList(this.perfil.getUnidadesDados()).get(0).getZona() : null);
		}
		
		return zona;
	}
	
	public List<String> getUnidadesParaAutoridade() {
		if (unidadesParaAutoridade == null) {
			unidadesParaAutoridade = unidadeClient.listarParaAutoridadePorMatricula(this.usuario.getMatricula());
		}

		return unidadesParaAutoridade;
	}

	public List<Integer> getZonasParaAutoridade() {
		if (zonasParaAutoridade == null) {
			zonasParaAutoridade = zonaClient.listarParaAutoridadePorMatricula(this.usuario.getMatricula());
		}

		return zonasParaAutoridade;
	}

	public boolean isLogado() {
		return logado;
	}
	
	public String getRedirecionamento() {
		return redirecionamento;
	}

	public void setRedirecionamento(String redirecionamento) {
		this.redirecionamento = redirecionamento;
	}

}
