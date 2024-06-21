package br.jus.trece.blank.bean;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import br.jus.trece.blank.business.ParametroBusiness;
import br.jus.trece.blank.util.AmbienteUtil;
import br.jus.trece.blank.util.UnidadeUtil;
import br.jus.trece.lib.util.FacesUtil;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**
 * bean de relatório de ambiente.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
@Named
@ViewScoped
@SuppressWarnings("deprecation")
public class RelatorioParametroBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// filtro.
	private String relatorio;

	private List<SelectItem> relatorios = null;
	
	@EJB
	ParametroBusiness parametroBusiness;

	@PostConstruct
	protected void init() {
		// definição de relatório.
		this.relatorio = FacesUtil.getRequestParam("relatorio");

		SelectItem si = new SelectItem("parametro", "Parâmetro");

		this.relatorios = new ArrayList<SelectItem>();
		this.relatorios.add(si);
	}

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public List<SelectItem> getRelatorios() {
		return relatorios;
	}

	public void onAlterarRelatorio() {
	}

	/**
	 * emite parâmetro.
	 */
	@SuppressWarnings("rawtypes")
	public void emitir() {
		try {
			InputStream entrada = ((ServletContext) FacesUtil.getContext()).getResourceAsStream("/WEB-INF/classes/reports/" + this.relatorio + ".jasper");
			HttpServletResponse saida = FacesUtil.getHttpServletResponse();

			JRExporter jre = new JRPdfExporter();

			HashMap<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("TRE_APP_NAME", AmbienteUtil.getAppProperty("sistema.SIGLA"));

			parametros.put("TRE_DOC_TITLE", "Relatório de parâmetro");
			parametros.put("TRE_DOC_SUBTITLE", "");

			parametros.put("TRE_IMAGE_PATH", ((ServletContext) FacesUtil.getContext()).getRealPath("/resources/images") + "/");

			parametros.put("TRE_LOCAL_TRIBUNAL_ACRONYM", AmbienteUtil.getAppProperty("tre.SIGLA"));
			parametros.put("TRE_LOCAL_TRIBUNAL_NAME", AmbienteUtil.getAppProperty("tre.NOME"));
			parametros.put("TRE_LOCAL_SECRETARIA_NAME", UnidadeUtil.getSecretariaGestor() != null ? UnidadeUtil.getSecretariaGestor().getNome() : null);
			parametros.put("TRE_LOCAL_COORDENADORIA_NAME", UnidadeUtil.getCoordenadoriaGestor() != null ? UnidadeUtil.getCoordenadoriaGestor().getNome() : null);
			parametros.put("TRE_LOCAL_SECAO_NAME", UnidadeUtil.getGestor() != null ? UnidadeUtil.getGestor().getNome() : null);

			parametros.put("TRE_REPORT_EMPTY", AmbienteUtil.getMsgProperty("aviso.VAZIO"));

			parametros.put(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
			
			JRDataSource dados = new JRBeanCollectionDataSource(parametroBusiness.emitir());

			saida.setContentType("application/pdf");
			saida.setHeader("Content-Disposition", "attachment; filename=" + AmbienteUtil.getAppProperty("sistema.SIGLA") + "-" + this.relatorio + ".pdf");

			jre.setParameter(JRPdfExporterParameter.JASPER_PRINT,
					JasperFillManager.fillReport(entrada, parametros, dados));
			jre.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, saida.getOutputStream());

			jre.exportReport();

			FacesUtil.getFacesContext().responseComplete();
		} catch (Exception e) {
			e.printStackTrace();

			FacesUtil.getFacesContext().addMessage("msgAmbiente", new FacesMessage(FacesMessage.SEVERITY_ERROR, AmbienteUtil.getMsgProperty("erro.relatorio.EMITIR"), null));
		}
	}
	
}
