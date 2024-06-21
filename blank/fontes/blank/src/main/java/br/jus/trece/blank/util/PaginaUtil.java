package br.jus.trece.blank.util;

/**
 * classe utilitária para página.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public class PaginaUtil {

	public static boolean isAutorizada(String pagina) {
		return (pagina.equals("/pages/home.xhtml"))
			|| (pagina.equals("/pages/cadastro_link.xhtml") && FacesUtil.getSessaoBean().getFuncionalidades().contains("cadastrarLink"))
			|| (pagina.equals("/pages/cadastro_noticia.xhtml") && FacesUtil.getSessaoBean().getFuncionalidades().contains("cadastrarNoticia"))
			|| (pagina.equals("/pages/relatorio_parametro.xhtml") && FacesUtil.getSessaoBean().getFuncionalidades().contains("emitirParametro"));

		// TODO: adicionar páginas e permissões específicas.
	}

}
