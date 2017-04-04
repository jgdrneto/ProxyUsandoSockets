package servidor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Servidor {
	
	public static int PORTA = 12345;
	public static String SERVIDOR = "localhost";
	public static Map<String,Integer> TABELA = new HashMap<String,Integer>();
	
	public static byte[] serialize(Object obj) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(obj);
	    return out.toByteArray();
	}
	
	public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
	    ByteArrayInputStream in = new ByteArrayInputStream(data);
	    ObjectInputStream is = new ObjectInputStream(in);
	    return is.readObject();
	}
		
	public static void main(String[] args) {
		
		//Mensagem m;
		
		try {
			ServerSocket servidor = new ServerSocket(12345);
			System.out.println("Servidor ouvindo a porta 12345");
			    
			while(true){
				Socket cliente = servidor.accept();
				
				new ThreadCliente(cliente,TABELA).start();
			   
			}					
			
			/*
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				m = (Mensagem)entrada.readObject();
				entrada.close();
				    
				cliente.close();
				    
				m.setMensagem(descritografar(m.getMensagem()));
					
				System.out.println("Enviar Mensagem de " + m.getMandatario() + " para " + m.getReceptor());
					
				enviarMensagem(m);
			*/
			
		} catch (IOException /* | ClassNotFoundException*/ e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}
}
