package br.jus.trece.blank.enumeration;

/**
 * enumeração de ambiente.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public enum AmbienteEnum {

	PRODUCAO("PRODUÇÃO"),
	HOMOLOGACAO("HOMOLOGAÇÃO"),
	DESENVOLVIMENTO("DESENVOLVIMENTO");

	private String id;

	private AmbienteEnum(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
