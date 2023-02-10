package br.com.project.util.all;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class Mensagens extends FacesContext implements Serializable {
	private static final long serialVersionUID = 1L;

	public Mensagens() {
		// TODO Auto-generated constructor stub
	}

	public static void responseOperation(StatusPersistencia statusPersistencia) {
		if (statusPersistencia != null && statusPersistencia.equals(statusPersistencia.SUCESSO)) {
			sucesso();
		} else if (statusPersistencia != null && statusPersistencia.equals(statusPersistencia.OBJETO_REFERENCIADO)) {
			msgDeFatal(statusPersistencia.OBJETO_REFERENCIADO.toString());
		} else {
			erroNaOperacao();
		}
	}

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	private static boolean facesContextValido() {
		return getFacesContext() != null;
	}

	public static void msg(String msg) {
		if (facesContextValido()) {
			getFacesContext().addMessage("msg", new FacesMessage(msg));
		}
	}

	public static void sucesso() {
		msgDeInfo(Constante.OPERAÇÃO_REALIZADA_COM_SUCESSO);
	}

	public static void erroNaOperacao() {
		if (facesContextValido()) {
			msgDeFatal(Constante.ERRO_NA_OPERAÇÃO);
		}
	}

	public static void msgDeWarning(String msg) {
		if (facesContextValido()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg));
		}
	}

	public static void msgDeFatal(String msg) {
		if (facesContextValido()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, msg));
		}
	}

	public static void msgDeError(String msg) {
		if (facesContextValido()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
		}
	}

	public static void msgDeInfo(String msg) {
		if (facesContextValido()) {
			getFacesContext().addMessage("msg", new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
		}
	}

}
