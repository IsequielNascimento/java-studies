package br.jus.trece.blank.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.component.datatable.DataTable;

import br.jus.trece.api.enumeration.DirecaoEnum;
import br.jus.trece.blank.business.LinkBusiness;
import br.jus.trece.blank.domain.Link;
import br.jus.trece.blank.util.AmbienteUtil;
import br.jus.trece.lib.util.FacesUtil;

/**
 * bean de cadastro de link.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Named
@ViewScoped
public class CadastroLinkBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Link link;
	private List<Link> links;

	// filtro.
	private List<Link> filtrados;

	private List<Link> ativos;

	@EJB
	LinkBusiness linkBusiness;

	@PostConstruct
	protected void init() {
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public List<Link> getLinks() {
		if (links == null) {
			// inicia página de tabela.
			((DataTable) FacesUtil.getViewRoot().findComponent("frmLink:dtbLink")).setFirst(0);

			links = linkBusiness.listarTudo("data", DirecaoEnum.DESC);
		}

		return links;
	}

	public List<Link> getFiltrados() {
		return filtrados;
	}

	public void setFiltrados(List<Link> filtrados) {
		this.filtrados = filtrados;
	}

	public List<Link> getAtivos() {
		if (ativos == null) {
			try {
				ativos = linkBusiness.listarAtivo();
			} catch (Exception e) {
				ativos = null;
			}
		}

		return ativos;
	}

	public void onFiltrar() {
	}

	public void onIncluir() {
		this.link = new Link();
		
		this.link.setAtivo(true);
	}

	public void onDetalhar(Link link) {
		this.link = link;

		// TODO: utilizar buscarPorId, caso necessário.
	}

	public void onAlterar(Link link) {
		this.link = link;

		// TODO: utilizar buscarPorId, caso necessário.
	}

	/**
	 * salva link.
	 */
	public void salvar() {
		this.link.setData(new Date());

		if (this.link.getId() == null) {
			linkBusiness.incluir(this.link);

			FacesUtil.getFacesContext().addMessage("msgCadastrar", new FacesMessage(FacesMessage.SEVERITY_INFO, AmbienteUtil.getMsgProperty("sucesso.cadastro.INCLUIR"), null));

			this.link = new Link();
			
			this.link.setAtivo(true);
		} else {
			linkBusiness.alterar(this.link);

			FacesUtil.getFacesContext().addMessage("msgCadastrar", new FacesMessage(FacesMessage.SEVERITY_INFO, AmbienteUtil.getMsgProperty("sucesso.cadastro.ALTERAR"), null));
		}

		this.links = null;

		this.ativos = null;
	}

	/**
	 * exclui link.
	 * 
	 * @param link
	 */
	public void excluir(Link link) {
		linkBusiness.excluir(link.getId());

		FacesUtil.getFacesContext().addMessage("msgLink", new FacesMessage(FacesMessage.SEVERITY_INFO, AmbienteUtil.getMsgProperty("sucesso.cadastro.EXCLUIR"), null));

		this.links = null;

		this.ativos = null;
	}

}
