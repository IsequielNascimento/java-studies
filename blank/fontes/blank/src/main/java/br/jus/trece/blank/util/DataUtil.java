package br.jus.trece.blank.util;

import java.util.Date;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

/**
 * classe utilitária para data.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Named
public class DataUtil {

	public String getAsString(String formato, Date data) {
		return br.jus.trece.lib.util.DataUtil.getAsString(formato, data);
	}

	public String getAsString(String formato, Date data, boolean ordinal) {
		return StringUtils.capitalize(br.jus.trece.lib.util.DataUtil.getAsString(formato, data, ordinal).toLowerCase());
	}

}
