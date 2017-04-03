package mensagem;

import java.io.Serializable;

public class Mensagem implements Serializable{
	
	private static final long serialVersionUID = -4462816670036150852L;
	
	private String mensagem;
	private String mandatario;
	private String receptor;
	
	public Mensagem(String mensagem, String mandatario, String receptor) {
		this.mensagem = mensagem;
		this.mandatario = mandatario;
		this.receptor = receptor;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	public String getMandatario() {
		return mandatario;
	}
	public void setMandatario(String mandatario) {
		this.mandatario = mandatario;
	}
	public String getReceptor() {
		return receptor;
	}
	public void setReceptor(String receptor) {
		this.receptor = receptor;
	}
}
