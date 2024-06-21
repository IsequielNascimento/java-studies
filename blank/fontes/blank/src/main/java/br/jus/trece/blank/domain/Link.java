package br.jus.trece.blank.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * domínio link.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Entity
@Table(name = "LINK")
@SequenceGenerator(name = "sqLink", sequenceName = "SQ_LINK", allocationSize = 1)
public class Link implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String titulo;
	private String url;
	private Date data;
	private Boolean ativo;

	public Link() {
	}

	public Link(Long id) {
		this.id = id;
	}

	public Link(Long id, String titulo, String url, Date data, Boolean ativo) {
		this.id = id;
		this.titulo = titulo;
		this.url = url;
		this.data = data;
		this.ativo = ativo;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqLink")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "DS_TITULO")
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column(name = "DS_URL")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_CRIACAO")
	@JsonFormat(pattern = "dd/MM/yyyy")
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(name = "FG_ATIVO")
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (!(obj instanceof Link)) {
			return false;
		}

		Link other = (Link) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.getId())) {
			return false;
		}

		return true;
	}

}
