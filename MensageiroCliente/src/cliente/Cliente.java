package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javafx.util.Pair;
import mensagem.Mensagem;
import mensagem.Pacote;

public class Cliente {
	
	public static int PORTA_LOCAL;
	public static int PORTA = 12345;
	public static String SERVIDOR = "localhost";
		
	private static String critografar(String s){
		
		String result="";
		
		for(char c : s.toCharArray()){
			result+=(char)(c+1);
		}
		
		return result;
	}
	
	public static void receberMensagem(String nome){
		
		try {
				
			ServerSocket receptor = new ServerSocket(PORTA_LOCAL);
				
			Socket cliente = receptor.accept();
				
			ObjectInputStream   entrada = new ObjectInputStream(cliente.getInputStream());
			Mensagem m = (Mensagem)entrada.readObject();
			entrada.close();
				
			System.out.println("Recebida mensagem de " + m.getMandatario());
			System.out.println("Mensagem: " + m.getMensagem());
				
			receptor.close();
				
		}catch(Exception e) {
			System.out.println("Erro: " + e.getMessage()); 
		} 
	}
	
	public static void main(String[] args) {
		
		if(args.length==2){
		
			//Definidos parâmetros para comunicação
			PORTA_LOCAL = Integer.parseInt(args[1]);
			String nome  = args[0];			
			
			try {
				
				Socket rec = new Socket  (SERVIDOR,PORTA,null,PORTA_LOCAL);
				ObjectOutputStream saida = new ObjectOutputStream(rec.getOutputStream());
				saida.flush();
				
			    Scanner scanner = new Scanner(System.in); 
				
				boolean continua=true;
				Pacote p;
				
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
							
						    p = new Pacote(nome,"ESCREVER_MENSAGEM",escreverMensagem(nome));
							saida.writeObject(p);
						    saida.close();
						    
						break;
						case 2:
							
							p = new Pacote(nome,"RECEBER_MENSAGEM");
							
							saida.writeObject(p);
							saida.close();
							
							System.out.println("Esperando a mensagem...");
							
							receberMensagem(nome);
						break;
						default:
							continua=false;
						break;	
					}
					
					System.out.println("\n");
					
				}
				
				rec.close();
				scanner.close();
			    
			    
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}else{
			System.out.println("Informe nos parâmetros:");
			System.out.println("1º parâmetro: Nome do cliente");
			System.out.println("2º parâmetro: Número da porta");
		}
	}

	private static Mensagem escreverMensagem(String mandatario) {  
		
		Scanner scanner = new Scanner(System.in);  
		
		System.out.print("Digite o nome do destinatário da mensagem: ");
		String destinatario = scanner.nextLine();
		System.out.print("Digite o conteúdo da menssagem: ");
		String mensagem = scanner.nextLine();
		
		return  new Mensagem(critografar(mensagem), mandatario, destinatario);
			
	}
}
