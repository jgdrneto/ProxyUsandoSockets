package cliente;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Scanner;

import mensagem.Mensagem;

public class Cliente {
	
	public static int PORTA = 12345;
	public static String END_MULTICAST = "239.0.0.1";
	public static String SERVIDOR = "localhost";
	
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
	
	private static String critografar(String s){
		
		String result="";
		
		for(char c : s.toCharArray()){
			result+=(char)(c+1);
		}
		
		return result;
	}
	
	public static void receberMensagem(String nome){
		
		boolean receberMensagem=true;
		
		while(receberMensagem) {
			try {       
				MulticastSocket mcs = new MulticastSocket(PORTA);
				InetAddress grp = InetAddress.getByName(END_MULTICAST);
				mcs.joinGroup(grp);
				byte rec[] = new byte[4096];
				DatagramPacket pkg = new DatagramPacket(rec, rec.length);
				mcs.receive(pkg);
				Mensagem m = (Mensagem) deserialize(rec);
				
				if(m.getReceptor().equals(nome)){
					System.out.println("Recebido uma mensagem de " + m.getMandatario() + " : "  + m.getMensagem());
				}
				mcs.close();
				receberMensagem=false;
			}catch(Exception e) {
				System.out.println("Erro: " + e.getMessage()); 
			} 
		}
	}
	
	public static void main(String[] args) {
		
		System.out.print("Digite o nome do seu usuário: ");
		Scanner scanner = new Scanner(System.in);  
		String nome = scanner.nextLine();
		
		System.out.println("\n");
		
		boolean continua=true;
		
		while(continua){
		
			System.out.println("O que deseja fazer?");
			System.out.println("1 - Escrever mensagem");
			System.out.println("2 - Receber mensagem");
			System.out.println("3 - Sair do programa");
			System.out.print(">>> ");
			int op = scanner.nextInt();
			
			System.out.println("\n");
			
			switch(op){
				case 1:
					escreverMensagem(nome);
				break;
				case 2:
					
					System.out.println("Esperando a mensagem...");
					
					receberMensagem(nome);
				break;
				default:
					continua=false;
				break;	
			}
			
			System.out.println("\n");
			
		}
		
		scanner.close();
	}

	private static void escreverMensagem(String mandatario) {  
		
		Scanner scanner = new Scanner(System.in);  
		
		System.out.print("Digite o nome do destinatário da mensagem: ");
		String destinatario = scanner.nextLine();
		System.out.print("Digite o conteúdo da menssagem: ");
		String mensagem = scanner.nextLine();
		
		Mensagem m = new Mensagem(critografar(mensagem), mandatario, destinatario);
		
		try {
			
			Socket rec = new Socket(SERVIDOR,PORTA);
			ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
		    saida.writeObject(m);
		    saida.close();
		    rec.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
