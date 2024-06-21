package br.jus.trece.blank.enumeration;

/**
 * enumeração de log.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public enum LogEnum {

	INCLUIR_LINK("INCLUIR_LINK"),
	ALTERAR_LINK("ALTERAR_LINK"),
	EXCLUIR_LINK("EXCLUIR_LINK"),
	INCLUIR_NOTICIA("INCLUIR_NOTICIA"),
	ALTERAR_NOTICIA("ALTERAR_NOTICIA"),
	EXCLUIR_NOTICIA("EXCLUIR_NOTICIA");

	// TODO: adicionar logs específicos.

	private String id;

	private LogEnum(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
