package br.jus.trece.blank.util.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.jus.trece.blank.util.AmbienteUtil;

/**
 * validador de inscrição.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@FacesValidator("inscricaoValidator")
@SuppressWarnings("rawtypes")
public class InscricaoValidator implements Validator {

	/**
	 * validador.
	 * 
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		Integer dig1, dig2, dig3, dig4, dig5, dig6, dig7, dig8, dig9, dig10, dig11, dig12;
		int dv1, dv2;
		int qDig;

		if (((String) value).length() < 12) {
			value = "000000000000" + value.toString();
			value = ((String) value).substring(((String) value).length() - 12);
		}

		qDig = ((String) value).length();

		dig1 = Integer.parseInt(((String) value).substring(qDig - 12, qDig - 11));
		dig2 = Integer.parseInt(((String) value).substring(qDig - 11, qDig - 10));
		dig3 = Integer.parseInt(((String) value).substring(qDig - 10, qDig - 9));
		dig4 = Integer.parseInt(((String) value).substring(qDig - 9, qDig - 8));
		dig5 = Integer.parseInt(((String) value).substring(qDig - 8, qDig - 7));
		dig6 = Integer.parseInt(((String) value).substring(qDig - 7, qDig - 6));
		dig7 = Integer.parseInt(((String) value).substring(qDig - 6, qDig - 5));
		dig8 = Integer.parseInt(((String) value).substring(qDig - 5, qDig - 4));
		dig9 = Integer.parseInt(((String) value).substring(qDig - 4, qDig - 3));
		dig10 = Integer.parseInt(((String) value).substring(qDig - 3, qDig - 2));
		dig11 = Integer.parseInt(((String) value).substring(qDig - 2, qDig - 1));
		dig12 = Integer.parseInt(((String) value).substring(qDig - 1, qDig));

		String uf = dig9.toString() + dig10.toString();
		Integer digUf = Integer.parseInt(uf);

		// dígitos de UF vão de 1 a 27 e 28 para exterior.
		if (digUf > 28) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, AmbienteUtil.getMsgProperty("validacao.INSCRICAO"), null));
		}

		dv1 = (dig1 * 2) + (dig2 * 3) + (dig3 * 4) + (dig4 * 5) + (dig5 * 6) + (dig6 * 7) + (dig7 * 8) + (dig8 * 9);
		dv1 = dv1 % 11;

		if (dv1 == 10) {
			dv1 = 0;
		}

		dv2 = (dig9 * 7) + (dig10 * 8) + (dv1 * 9);
		dv2 = dv2 % 11;

		if (dv2 == 10) {
			dv2 = 0;
		}

		if (dig11 == dv1 && dig12 == dv2) {
			return;
		} else {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, AmbienteUtil.getMsgProperty("validacao.INSCRICAO"), null));
		}
	}

}
