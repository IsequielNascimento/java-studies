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
 * domínio notícia.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Entity
@Table(name = "NOTICIA")
@SequenceGenerator(name = "sqNoticia", sequenceName = "SQ_NOTICIA", allocationSize = 1)
public class Noticia implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String titulo;
	private String descricao;
	private Date data;
	private Boolean ativa;

	public Noticia() {
	}

	public Noticia(Long id) {
		this.id = id;
	}

	public Noticia(Long id, String titulo, String descricao, Date data, Boolean ativa) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.data = data;
		this.ativa = ativa;
	}

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sqNoticia")
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

	@Column(name = "DS_NOTICIA")
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	public Boolean getAtiva() {
		return ativa;
	}

	public void setAtiva(Boolean ativa) {
		this.ativa = ativa;
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
		} else if (!(obj instanceof Noticia)) {
			return false;
		}

		Noticia other = (Noticia) obj;
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
