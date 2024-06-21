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
import br.jus.trece.blank.business.NoticiaBusiness;
import br.jus.trece.blank.domain.Noticia;
import br.jus.trece.blank.util.AmbienteUtil;
import br.jus.trece.lib.util.FacesUtil;

/**
 * bean de cadastro de notícia.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Named
@ViewScoped
public class CadastroNoticiaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Noticia noticia;
	private List<Noticia> noticias;

	// filtro.
	private List<Noticia> filtradas;

	private List<Noticia> ativas;

	@EJB
	NoticiaBusiness noticiaBusiness;

	@PostConstruct
	protected void init() {
	}

	public Noticia getNoticia() {
		return noticia;
	}

	public void setNoticia(Noticia noticia) {
		this.noticia = noticia;
	}

	public List<Noticia> getNoticias() {
		if (noticias == null) {
			// inicia página de tabela.
			((DataTable) FacesUtil.getViewRoot().findComponent("frmNoticia:dtbNoticia")).setFirst(0);

			noticias = noticiaBusiness.listarTudo("data", DirecaoEnum.DESC);
		}

		return noticias;
	}

	public List<Noticia> getFiltradas() {
		return filtradas;
	}

	public void setFiltradas(List<Noticia> filtradas) {
		this.filtradas = filtradas;
	}

	public List<Noticia> getAtivas() {
		if (ativas == null) {
			ativas = noticiaBusiness.listarAtiva();
		}

		return ativas;
	}

	public void onFiltrar() {
	}

	public void onIncluir() {
		this.noticia = new Noticia();
		
		this.noticia.setAtiva(true);
	}

	public void onDetalhar(Noticia noticia) {
		this.noticia = noticia;

		// TODO: utilizar buscarPorId, caso necessário.
	}

	public void onAlterar(Noticia noticia) {
		this.noticia = noticia;

		// TODO: utilizar buscarPorId, caso necessário.
	}

	/**
	 * salva notícia.
	 */
	public void salvar() {
		this.noticia.setData(new Date());

		if (this.noticia.getId() == null) {
			noticiaBusiness.incluir(this.noticia);

			FacesUtil.getFacesContext().addMessage("msgCadastrar", new FacesMessage(FacesMessage.SEVERITY_INFO,	AmbienteUtil.getMsgProperty("sucesso.cadastro.INCLUIR"), null));

			this.noticia = new Noticia();
			
			this.noticia.setAtiva(true);
		} else {
			noticiaBusiness.alterar(this.noticia);

			FacesUtil.getFacesContext().addMessage("msgCadastrar", new FacesMessage(FacesMessage.SEVERITY_INFO, AmbienteUtil.getMsgProperty("sucesso.cadastro.ALTERAR"), null));
		}

		this.noticias = null;
	}

	/**
	 * exclui notícia.
	 * 
	 * @param noticia
	 */
	public void excluir(Noticia noticia) {
		noticiaBusiness.excluir(noticia.getId());

		FacesUtil.getFacesContext().addMessage("msgNoticia", new FacesMessage(FacesMessage.SEVERITY_INFO, AmbienteUtil.getMsgProperty("sucesso.cadastro.EXCLUIR"), null));

		this.noticias = null;

		this.ativas = null;
	}

}
