package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

import javafx.util.Pair;
import mensagem.Mensagem;
import mensagem.Pacote;

class ThreadCliente extends Thread {

	private Socket cliente;
	private Map<String, Integer> mapa;
	public static String SERVIDOR = "localhost";
	
	public ThreadCliente(Socket cliente, Map<String, Integer> tabela) {
		this.cliente = cliente;
		mapa = tabela;
	}
	
	private static String descritografar(String s){
		
		String result="";
		
		for(char c : s.toCharArray()){
			result+=(char)(c-1);
		}
		
		return result;
	}	
	
	public void run() {
		try {
			 
			System.out.println("Cliente conectado com porta local : " + cliente.getPort());
			    
			ObjectInputStream   entrada = new ObjectInputStream(cliente.getInputStream());
			Pacote pct = (Pacote)entrada.readObject();
			
			mapa.put(pct.getNome(), cliente.getPort());
						
			if(pct.getTipo().equals("RECEBER_MENSAGEM")){
				
			}else{
				if(pct.getTipo().equals("ESCREVER_MENSAGEM")){
					      
					pct.getMensagem().setMensagem(descritografar(pct.getMensagem().getMensagem()));
					
					Integer port = mapa.get(pct.getMensagem().getReceptor());
					
					if(port!=null){
						System.out.println("Enviar Mensagem de " + pct.getMensagem().getMandatario() + " para " + pct.getMensagem().getReceptor());
						
						enviarMensagem(pct.getMensagem(), port);
					}else{
						System.out.println("Error: Cliente destinatário não encontrado");
					}
				}
			}
			
			cliente.close();
			
		}catch(Exception e) {
	    	System.out.println("Excecao ocorrida na thread: " + e.getMessage());            
	    	
	    	try {
				cliente.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    	
	    }
	}
	
	public static void enviarMensagem(Mensagem m, int port){
		
		try {
			Socket s = new Socket(SERVIDOR, port);
				
			ObjectOutputStream saida = new ObjectOutputStream(s.getOutputStream());
		    saida.writeObject(m);
		    saida.close();

			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			System.out.println("ERRO EM ENVIAR PARA O DESTINATÁRIO");
			
			e.printStackTrace();
		}
			
	}
	
}
