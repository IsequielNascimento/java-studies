package br.jus.trece.blank.bean;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.jus.trece.api.domain.Token;
import br.jus.trece.api.domain.corporativo.Usuario;
import br.jus.trece.api.domain.pandora.Perfil;
import br.jus.trece.blank.client.AutenticacaoClient;
import br.jus.trece.blank.client.LoginMatriculaClient;
import br.jus.trece.blank.client.PerfilClient;
import br.jus.trece.blank.client.UsuarioClient;
import br.jus.trece.blank.exception.AutenticacaoException;
import br.jus.trece.blank.exception.AutorizacaoException;
import br.jus.trece.blank.util.AmbienteUtil;
import br.jus.trece.lib.util.ColecaoUtil;
import br.jus.trece.lib.util.FacesUtil;
import br.jus.trece.lib.util.NumeroUtil;
import br.jus.trece.lib.util.StringUtil;
import br.jus.trece.blank.business.PerfilBusiness;

/**
 * bean de login.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String login;
	private String senha;

	@Inject
	SessaoBean sessaoBean;

	@Inject
	AutenticacaoClient autenticacaoClient;
	@Inject
	LoginMatriculaClient loginMatriculaClient;
	@Inject
	UsuarioClient usuarioClient;
	@Inject
	PerfilClient perfilClient;
	
	@EJB
	PerfilBusiness perfilBusiness;

	@PostConstruct
	protected void init() {
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * entra no sistema.
	 */
	public void entrar() {
		final FacesContext context = FacesUtil.getFacesContext();
		HttpServletRequest request = FacesUtil.getHttpServletRequest();

		try {
			if (AmbienteUtil.isDesenvolvimento() || StringUtil.isPreenchida(this.senha)) {
				// autenticação de usuário.
				Token token = autenticacaoClient.autenticar(this.login, this.senha);
				
				if (token == null) {
					throw new AutenticacaoException();
				}

				sessaoBean.setToken(token);

				Integer matricula = loginMatriculaClient.buscarMatriculaPorLogin(this.login);

				if (matricula == null) {
					throw new AutenticacaoException();
				}

				Usuario usuario = usuarioClient.buscarPorId(NumeroUtil.getAsString("00000", matricula));

				if (usuario == null) {
					throw new AutenticacaoException();
				}

				// autorização de usuário.
				List<Perfil> perfis = perfilBusiness.listar(sessaoBean.getParametro().getSistema().getId(), perfilClient.listarDescricao(sessaoBean.getParametro().getSistema().getId(), usuario.getMatricula()));

				if (!ColecaoUtil.isPreenchida(perfis)) {
					throw new AutorizacaoException();
				}
				
				sessaoBean.setUsuario(usuario);
				sessaoBean.setPerfis(perfis);

				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage("msgHome", new FacesMessage(FacesMessage.SEVERITY_INFO, MessageFormat.format(AmbienteUtil.getMsgProperty("aviso.LOGIN"), new Object[] { this.login }), null));

				if (sessaoBean.getRedirecionamento() == null || sessaoBean.getPerfil() == null) {
					context.getExternalContext().redirect(request.getContextPath() + "/pages/home.xhtml");
				} else {
					context.getExternalContext().redirect(request.getContextPath() + sessaoBean.getRedirecionamento());
				}
			} else {
				context.addMessage("msgLogin", new FacesMessage(FacesMessage.SEVERITY_ERROR, AmbienteUtil.getMsgProperty("validacao.login.SENHA"), null));
			}
		} catch (AutenticacaoException e) {
			context.addMessage("msgLogin", new FacesMessage(FacesMessage.SEVERITY_ERROR, AmbienteUtil.getMsgProperty("validacao.login.AUTENTICAR"), null));
		} catch (AutorizacaoException e) {
			context.addMessage("msgLogin", new FacesMessage(FacesMessage.SEVERITY_ERROR, AmbienteUtil.getMsgProperty("validacao.login.AUTORIZAR"), null));
		} catch (Exception e) {
			e.printStackTrace();

			context.addMessage("msgLogin", new FacesMessage(FacesMessage.SEVERITY_ERROR, AmbienteUtil.getMsgProperty("erro.LOGIN"), null));
		}
	}

	/**
	 * sai do sistema.
	 */
	public void sair() {
		final FacesContext context = FacesUtil.getFacesContext();
		HttpServletRequest request = FacesUtil.getHttpServletRequest();

		try {
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.invalidate();

			context.getExternalContext().getFlash().setKeepMessages(true);
			context.addMessage("msgLogin", new FacesMessage(FacesMessage.SEVERITY_INFO,	AmbienteUtil.getMsgProperty("aviso.LOGOUT"), null));

			context.getExternalContext().redirect(request.getContextPath() + "/pages/login.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
