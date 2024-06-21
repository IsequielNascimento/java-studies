package br.jus.trece.blank.util;

import javax.faces.context.FacesContext;

import br.jus.trece.blank.bean.SessaoBean;

/**
 * classe utilitária para JSF.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public class FacesUtil {

	public static SessaoBean getSessaoBean() {
		final FacesContext context = br.jus.trece.lib.util.FacesUtil.getFacesContext();

		return (SessaoBean) context.getApplication().evaluateExpressionGet(context, "#{sessaoBean}", Object.class);
	}

}
