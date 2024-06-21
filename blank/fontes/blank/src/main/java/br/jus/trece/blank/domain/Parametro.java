package br.jus.trece.blank.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.jus.trece.api.domain.pandora.Sistema;

/**
 * domínio parâmetro.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Entity
@Table(name = "PARAMETRO")
public class Parametro implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Sistema sistema;
	private String urlSegurancaTre;
	private String urlEntidadeTre;
	private String urlLogTre;
	private String urlEmailTre;
	private String urlFoneticaTre;
	private String urlAssinaturaTre;
	private String urlDiplomaTre;

	public Parametro() {
	}

	public Parametro(Integer id) {
		this.id = id;
	}

	public Parametro(Integer id, Sistema sistema, String urlSegurancaTre, String urlEntidadeTre, String urlLogTre,
			String urlEmailTre, String urlFoneticaTre, String urlAssinaturaTre, String urlDiplomaTre) {
		this.id = id;
		this.sistema = sistema;
		this.urlSegurancaTre = urlSegurancaTre;
		this.urlEntidadeTre = urlEntidadeTre;
		this.urlLogTre = urlLogTre;
		this.urlEmailTre = urlEmailTre;
		this.urlFoneticaTre = urlFoneticaTre;
		this.urlAssinaturaTre = urlAssinaturaTre;
		this.urlDiplomaTre = urlDiplomaTre;
	}

	@Id
	@Column(name = "ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_SISTEMA")
	public Sistema getSistema() {
		return sistema;
	}

	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}

	@Column(name = "DS_URL_SEGURANCA_TRE")
	public String getUrlSegurancaTre() {
		return urlSegurancaTre;
	}

	public void setUrlSegurancaTre(String urlSegurancaTre) {
		this.urlSegurancaTre = urlSegurancaTre;
	}

	@Column(name = "DS_URL_ENTIDADE_TRE")
	public String getUrlEntidadeTre() {
		return urlEntidadeTre;
	}

	public void setUrlEntidadeTre(String urlEntidadeTre) {
		this.urlEntidadeTre = urlEntidadeTre;
	}

	@Column(name = "DS_URL_LOG_TRE")
	public String getUrlLogTre() {
		return urlLogTre;
	}

	public void setUrlLogTre(String urlLogTre) {
		this.urlLogTre = urlLogTre;
	}

	@Column(name = "DS_URL_EMAIL_TRE")
	public String getUrlEmailTre() {
		return urlEmailTre;
	}

	public void setUrlEmailTre(String urlEmailTre) {
		this.urlEmailTre = urlEmailTre;
	}

	@Column(name = "DS_URL_FONETICA_TRE")
	public String getUrlFoneticaTre() {
		return urlFoneticaTre;
	}

	public void setUrlFoneticaTre(String urlFoneticaTre) {
		this.urlFoneticaTre = urlFoneticaTre;
	}

	@Column(name = "DS_URL_ASSINATURA_TRE")
	public String getUrlAssinaturaTre() {
		return urlAssinaturaTre;
	}

	public void setUrlAssinaturaTre(String urlAssinaturaTre) {
		this.urlAssinaturaTre = urlAssinaturaTre;
	}
	
	@Column(name = "DS_URL_DIPLOMA_TRE")
	public String getUrlDiplomaTre() {
		return urlDiplomaTre;
	}

	public void setUrlDiplomaTre(String urlDiplomaTre) {
		this.urlDiplomaTre = urlDiplomaTre;
	}

}
