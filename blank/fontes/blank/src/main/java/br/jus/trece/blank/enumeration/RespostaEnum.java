package br.jus.trece.blank.enumeration;

/**
 * enumeração de resposta a web service.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public enum RespostaEnum {

	OK(200, "Ok"),
	CRIADO(201, "Criado"),

	REQUISICAO_INVALIDA(400, "Requisição inválida"),
	NAO_AUTORIZADO(401, "Não autorizado"),
	RECURSO_PROIBIDO(403, "Recurso proibido"),
	NENHUM_DADO_ENCONTRADO(404, "Nenhum dado encontrado"),

	ERRO_INTERNO_SERVIDOR(500, "Erro interno do servidor"),
	SERVICO_INDISPONIVEL(503, "Serviço indisponível");

	private Integer id;
	private String descricao;

	private RespostaEnum(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
