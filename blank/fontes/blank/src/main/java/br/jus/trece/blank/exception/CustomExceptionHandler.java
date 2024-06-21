package br.jus.trece.blank.exception;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import br.jus.trece.api.domain.Email;
import br.jus.trece.api.domain.pandora.Erro;
import br.jus.trece.api.enumeration.FormatoEmailEnum;
import br.jus.trece.blank.client.EmailClient;
import br.jus.trece.blank.client.ErroClient;
import br.jus.trece.blank.util.AmbienteUtil;
import br.jus.trece.blank.util.FacesUtil;
import br.jus.trece.lib.util.DataUtil;
import br.jus.trece.lib.util.StreamUtil;

/**
 * manipulador de exceção.
 * 
 * @author SEDSC/COSIS/STI - TRE/CE
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	@SuppressWarnings("deprecation")
	CustomExceptionHandler(ExceptionHandler exception) {
		this.wrapped = exception;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	/**
	 * manipula exceção.
	 * 
	 * @throws FacesException
	 */
	@Override
	public void handle() throws FacesException {
		final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();

		while (i.hasNext()) {
			ExceptionQueuedEvent event = i.next();

			Throwable t = ((ExceptionQueuedEventContext) event.getSource()).getException();

			final FacesContext context = br.jus.trece.lib.util.FacesUtil.getFacesContext();
			HttpServletRequest request = br.jus.trece.lib.util.FacesUtil.getHttpServletRequest();

			Connection connection = null;

			String url = "";

			try {
				if (isSessaoExpirada(t)) {
					HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
					if (session != null) {
						session.invalidate();
					}

					// redireciona para a página de timeout.
					context.getExternalContext().redirect(request.getContextPath() + "/pages/timeout.xhtml");
				} else {
					String host = InetAddress.getLocalHost().getHostName();

					connection = ((DataSource) new InitialContext().lookup("java:/blankDatasource")).getConnection();
					if (connection != null) {
						url = connection.getMetaData().getURL();
					}

					String pagina = context.getViewRoot() != null ? context.getViewRoot().getViewId() : "Não disponível";
					String excecao = t.getClass().getName();
					String descricao = getStackTrace(t);

					ErroClient erroClient = new ErroClient();

					Erro erro = new Erro();
					erro.setSistema(FacesUtil.getSessaoBean().getParametro().getSistema());
					erro.setLogin(FacesUtil.getSessaoBean().getUsuario().getLogin());
					erro.setMatricula(FacesUtil.getSessaoBean().getUsuario().getMatricula());
					erro.setUnidade(FacesUtil.getSessaoBean().getUsuario().getUnidade());
					erro.setHost(host);
					erro.setConexao(url);
					erro.setPagina(pagina);
					erro.setData(new Date());
					erro.setExcecao(excecao);
					erro.setDescricao(descricao);

					// loga erro.
					erroClient.logar(erro);

					EmailClient emailClient = new EmailClient();

					Email email = new Email();
					email.setRemetente("nao-responda" + "@" + AmbienteUtil.getAppProperty("tre.DOMINIO"));
					email.setNome(AmbienteUtil.getAppProperty("sistema.SIGLA"));
					email.setAssunto("[" + AmbienteUtil.getAppProperty("sistema.SIGLA") + "] Erro");

					String conteudo = StreamUtil.getConteudo("/pages/email_erro.xhtml");
					conteudo = conteudo.replace("#login#", FacesUtil.getSessaoBean().getUsuario().getLogin())
									   .replace("#matricula#", FacesUtil.getSessaoBean().getUsuario().getMatricula())
									   .replace("#lotacao#", FacesUtil.getSessaoBean().getUsuario().getUnidade().getSigla())
									   .replace("#host#", host)
									   .replace("#conexao#", !url.equals("") ? url.substring(url.indexOf("@") + 1) : "")
									   .replace("#ambiente#", AmbienteUtil.getDescricao())
									   .replace("#pagina#", pagina)
									   .replace("#data#", DataUtil.getAsString("dd/MM/yyyy HH:mm:ss", new Date()))
									   .replace("#excecao#", excecao)
									   .replace("#descricao#", descricao)
									   .replace("#sigla_sistema#", AmbienteUtil.getAppProperty("sistema.SIGLA"))
									   .replace("#sigla_tre#", AmbienteUtil.getAppProperty("tre.SIGLA"))
									   .replace("#nome_tre#", AmbienteUtil.getAppProperty("tre.NOME"));
					email.setConteudo(conteudo);

					// envia e-mail.
					List<String> destinatarios = Arrays.asList(AmbienteUtil.getAppProperty("login.ERRO").split("[\\s,]+"));
					for (String destinatario : destinatarios) {
						email.setDestinatario(destinatario.toLowerCase() + "@" + AmbienteUtil.getAppProperty("tre.DOMINIO"));

						emailClient.enviar(email, FormatoEmailEnum.HTML);
					}

					// redireciona para a página de erro.
					context.getExternalContext().redirect(request.getContextPath() + "/pages/erro.xhtml");
				}

				context.renderResponse();
			} catch (Exception e) {
				try {
					// redireciona para a página de erro.
					context.getExternalContext().redirect(request.getContextPath() + "/pages/erro.xhtml");
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			} finally {
				i.remove();

				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		getWrapped().handle();
	}
	
	private String getStackTrace(Throwable t) {
		if (t != null) {
			StackTraceElement[] elements = t.getStackTrace();
			StringBuffer sb = null;

			if (t.getMessage() != null) {
				sb = new StringBuffer(t.getMessage());
			} else {
				sb = new StringBuffer("");
			}
			for (int i = 0; i < elements.length; i++) {
				sb.append(elements[i].toString());
				sb.append("<br/>\n");
			}

			return sb.toString() + getStackTrace(t.getCause());
		} else
			return "";
	}

	private boolean isSessaoExpirada(Throwable t) {
		if (t instanceof ViewExpiredException) {
			return true;
		} else {
			Throwable e = t.getCause();

			while (e != null) {
				if (e instanceof ViewExpiredException) {
					return true;
				}

				e = e.getCause();
			}
		}

		return false;
	}

}
