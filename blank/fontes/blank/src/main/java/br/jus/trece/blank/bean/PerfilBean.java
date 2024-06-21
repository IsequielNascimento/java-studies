package br.jus.trece.blank.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.jus.trece.api.domain.pandora.Perfil;
import br.jus.trece.lib.util.FacesUtil;
import br.jus.trece.blank.util.PaginaUtil;
import br.jus.trece.blank.util.AmbienteUtil;

/**
 * bean de perfil.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Named
@ViewScoped
public class PerfilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Perfil perfil = null;
	private List<Perfil> perfis = new ArrayList<Perfil>();
	
	@Inject
	SessaoBean sessaoBean;
	
	@PostConstruct
	protected void init() {
		this.perfil = sessaoBean.getPerfil();
		this.perfis = sessaoBean.getPerfis();
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}
	
	public void onCancelar() {
		if (this.perfil == null) {
			sessaoBean.setPerfil(new Perfil());
		}
	}
	
	/**
	 * confirma perfil.
	 */
	@SuppressWarnings("deprecation")
	public void confirmar() {
		List<String> funcionalidades = sessaoBean.getFuncionalidades();
		
		boolean inicial = !br.jus.trece.lib.util.ColecaoUtil.isPreenchida(funcionalidades);
		
		if ((inicial && isConfirmar()) || !inicial) {
			sessaoBean.setPerfil(this.perfil);
			
			RequestContext.getCurrentInstance().execute("PF('dlg_selecionar_perfil').hide();");
			
			try {
				FacesUtil.getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
				FacesUtil.getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, AmbienteUtil.getMsgProperty("sucesso.perfil.CONFIRMAR"), null));
				
				if (sessaoBean.getRedirecionamento() == null) {
					if (!inicial) {
						RequestContext.getCurrentInstance().execute("PF('dlg_usuario').hide();");
						
						FacesUtil.getFacesContext().getExternalContext().redirect(FacesUtil.getHttpServletRequest().getContextPath() + "/pages/home.xhtml");
					}
					
					RequestContext.getCurrentInstance().update("frmHome:msgHome");
				} else {
					FacesUtil.getFacesContext().getExternalContext().redirect(FacesUtil.getHttpServletRequest().getContextPath() + sessaoBean.getRedirecionamento());
					
					if (PaginaUtil.isAutorizada(sessaoBean.getRedirecionamento())) {
						sessaoBean.setRedirecionamento(null);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean isConfirmar() {
		boolean result = true;
		
		if (this.perfil == null) {
			FacesUtil.getFacesContext().addMessage("msgSelecionarPerfil", new FacesMessage(FacesMessage.SEVERITY_ERROR, AmbienteUtil.getMsgProperty("validacao.perfil.SELECAO"), null));
			
			result = false;
		}
		
		return result;
	}
	
}
