package br.jus.trece.blank.util;

import javax.inject.Named;

import br.jus.trece.api.domain.corporativo.Unidade;

/**
 * classe utilitária para unidade.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Named
public class UnidadeUtil {

	public static Unidade getGestor() {
		Unidade result = null;

		if (FacesUtil.getSessaoBean().getParametro() != null) {
			result = FacesUtil.getSessaoBean().getParametro().getSistema().getGestor();
		}

		return result;
	}

	public static Unidade getCoordenadoriaGestor() {
		Unidade result = null;

		if (getGestor() != null) {
			result = getGestor().getUnidadePai();
		}

		return result;
	}

	public static Unidade getSecretariaGestor() {
		Unidade result = null;

		if (getCoordenadoriaGestor() != null) {
			result = getCoordenadoriaGestor().getUnidadePai();
		}

		return result;
	}

	public static Unidade getGestorTecnico() {
		Unidade result = null;

		if (FacesUtil.getSessaoBean().getParametro() != null) {
			result = FacesUtil.getSessaoBean().getParametro().getSistema().getGestorTecnico();
		}

		return result;
	}

}
