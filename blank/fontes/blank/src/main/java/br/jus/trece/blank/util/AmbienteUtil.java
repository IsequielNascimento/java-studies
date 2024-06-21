package br.jus.trece.blank.util;

import javax.inject.Named;

import br.jus.trece.blank.enumeration.AmbienteEnum;

/**
 * classe utilitária para ambiente.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Named
public class AmbienteUtil {

	public static String getAppProperty(String chave) {
		return br.jus.trece.lib.util.AmbienteUtil.getProperty("application", chave);
	}

	public static String getMsgProperty(String chave) {
		return br.jus.trece.lib.util.AmbienteUtil.getProperty("messages", chave);
	}

	public static String getMvnProperty(String chave) {
		return br.jus.trece.lib.util.AmbienteUtil.getProperty("maven", chave);
	}

	public static boolean isDesenvolvimento() {
		return getDescricao().equals(AmbienteEnum.DESENVOLVIMENTO.getId());
	}

	public static boolean isHomologacao() {
		return getDescricao().equals(AmbienteEnum.HOMOLOGACAO.getId());
	}

	public static boolean isProducao() {
		return getDescricao().equals(AmbienteEnum.PRODUCAO.getId());
	}
	
	public static String getDescricao() {
		return System.getenv(getAppProperty("tre.AMBIENTE"));
	}

}
