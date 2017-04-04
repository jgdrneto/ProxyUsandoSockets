package mensagem;

import java.io.Serializable;

public class Pacote implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -570431841458414512L;
	String nome;
	String tipo;
	Mensagem Mensagem;
	
	public Pacote(String nome, String tipo, mensagem.Mensagem mensagem) {
		this.nome = nome;
		this.tipo = tipo;
		Mensagem = mensagem;
	}

	public Pacote(String nome, String tipo) {
		this.nome = nome;
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Mensagem getMensagem() {
		return Mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		Mensagem = mensagem;
	}
	
	
	
}
