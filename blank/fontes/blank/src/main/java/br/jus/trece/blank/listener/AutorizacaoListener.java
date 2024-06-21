package br.jus.trece.blank.listener;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.jus.trece.blank.util.FacesUtil;
import br.jus.trece.blank.util.PaginaUtil;

/**
 * gerenciador de autorização.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public class AutorizacaoListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	private List<String> paginasAutorizadas = new ArrayList<String>();

	public AutorizacaoListener() {
		this.paginasAutorizadas.add("/pages/login.xhtml");
		
		this.paginasAutorizadas.add("/pages/timeout.xhtml");
		
		this.paginasAutorizadas.add("/pages/nao_autorizada.xhtml");
		this.paginasAutorizadas.add("/pages/nao_encontrada.xhtml");
		this.paginasAutorizadas.add("/pages/erro.xhtml");
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		final FacesContext context = event.getFacesContext();

		NavigationHandler nh = context.getApplication().getNavigationHandler();

		String paginaAtual = context.getViewRoot().getViewId();

		if (!this.paginasAutorizadas.contains(paginaAtual)) {
			if (FacesUtil.getSessaoBean() == null || !FacesUtil.getSessaoBean().isLogado()) {
				if (!paginaAtual.equals("/pages/home.xhtml")) {
					FacesUtil.getSessaoBean().setRedirecionamento(paginaAtual);
				}
				
				nh.handleNavigation(context, null, "/pages/login.xhtml" + "?faces-redirect=true");
			} else if (!PaginaUtil.isAutorizada(paginaAtual)) {
				nh.handleNavigation(context, null, "/pages/nao_autorizada.xhtml");
				
				if (FacesUtil.getSessaoBean().getRedirecionamento() != null) {
					FacesUtil.getSessaoBean().setRedirecionamento(null);
				}
			}
		} else {
			if (FacesUtil.getSessaoBean() != null && FacesUtil.getSessaoBean().isLogado()) {
				if (paginaAtual.equals("/pages/login.xhtml")) {
					nh.handleNavigation(context, null, "/pages/home.xhtml" + "?faces-redirect=true");
				} 
			}
		}
	}

	@Override
	public void afterPhase(PhaseEvent event) {
	}

}
