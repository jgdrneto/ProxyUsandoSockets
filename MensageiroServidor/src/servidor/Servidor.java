package servidor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


import mensagem.Mensagem;

public class Servidor {
	
	public static int PORTA = 12345;
	public static String END_MULTICAST = "239.0.0.1";
	
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
	
	private static String descritografar(String s){
	
		String result="";
		
		for(char c : s.toCharArray()){
			result+=(char)(c-1);
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		boolean ObterMensagem = true;
		
		Mensagem m = new Mensagem("padrao", "padrao","padrao");
		
		
		try {
			ServerSocket servidor = new ServerSocket(12345);
			System.out.println("Servidor ouvindo a porta 12345");
			    
			while(ObterMensagem){
				Socket cliente = servidor.accept();
			    System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
				    
			    ObjectInputStream   entrada = new ObjectInputStream(cliente.getInputStream());
			    m = (Mensagem)entrada.readObject();
			    entrada.close();
				    
			    m.setMensagem(descritografar(m.getMensagem()));
					
				System.out.println("Enviar Mensagem de " + m.getMandatario() + " para " + m.getReceptor());
				    
				byte[] mS = serialize(m);
				InetAddress addr = InetAddress.getByName(END_MULTICAST);     
			    DatagramSocket ds = new DatagramSocket();
			    DatagramPacket pkg = new DatagramPacket(mS, mS.length, addr, PORTA);  
			    ds.send(pkg);
			    
			    ds.close();
			    
			}
				
			servidor.close();
				
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
	}	
}
