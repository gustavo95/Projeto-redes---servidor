package connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientThread implements Runnable {

	private Socket connectionSocket;
	private ServerSocket serverDataSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private  ArrayList<Requisition> listRequisitions;
	private ArrayList<User> listUsers;
	private User user;

	public ClientThread(Socket cs, ServerSocket sds, ArrayList<Requisition> listRequisitions, ArrayList<User> listUsers) {
		this.connectionSocket = cs;
		this.serverDataSocket = sds;
		this.listRequisitions = listRequisitions;
		this.listUsers = listUsers;
	}

	public void run() {
		boolean isConnected = true;
		String clientSentence;

		try {
			inFromClient = new BufferedReader(new InputStreamReader(
					connectionSocket.getInputStream()));

			outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());
			int i = 1;
			while(isConnected){
				clientSentence = inFromClient.readLine();

				if(clientSentence != null){
					switch(clientSentence){
					case "0 Exit":
						isConnected = false;
						connectionSocket.close();
						break;
					case "1 SendingRequisition":
						receiveRequisition();
						break;
					case "2 AskList":
						sendList() ;
						break;
					case "3 SendFile":
						receiveFile();
						break;
					case "4 ReceiveFile":
						sendFile();
						break;
					case "5 CreateUser":
						createUser();						
						break;
					case "6 Login":
						login();
						break;
					default:
						System.out.println("outra coisa: " + clientSentence);
						break;
					}
				}else{
					System.out.println("IS NULL");
					//inFromClient.reset();
				}
				if(i == 10){
					isConnected = false;
				}
				i++;
				System.out.println(i);
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Realizar login de um usuario
	public void login() throws IOException, ClassNotFoundException{
		ObjectInputStream in =   new ObjectInputStream(connectionSocket.getInputStream());
		user = (User)in.readObject();
		if (user.checkLogin(listUsers, user)){

			outToClient.writeBytes("Ok\n");
		}
		else{
			outToClient.writeBytes("NotOk\n");
		}
	}

	//Criar novo usuario
	public void createUser() throws IOException, ClassNotFoundException{
		ObjectInputStream in =   new ObjectInputStream(connectionSocket.getInputStream());
		User user = (User)in.readObject();
		if (!user.checkUser(listUsers, user)){
			listUsers.add(user);
			outToClient.writeBytes("Ok\n");
		}
		else{
			outToClient.writeBytes("NotOk\n");
		}
	}

	//Receber Requisi��o do cliente
	public void receiveRequisition() throws IOException, ClassNotFoundException{
		
		ObjectInputStream in =  new ObjectInputStream(connectionSocket.getInputStream());
		Requisition req = (Requisition) in.readObject();
		listRequisitions.add(req);
		outToClient.writeBytes("Ok\n");
	}

	//Enviar lista de Requisi��es para o cliente
	public void sendList() throws IOException{
		ObjectOutputStream out = new ObjectOutputStream(connectionSocket.getOutputStream());
		out.writeObject(listRequisitions);
		out.flush();
	}

	//Receber arquivo enviado pelo cliente
	public void receiveFile() throws IOException{
		Socket dataSocket = null;
		FileOutputStream fos = null;
		InputStream is = null;
		String fileName = null;

		try{

			fileName = inFromClient.readLine();

			dataSocket = serverDataSocket.accept();
			System.out.println("conectado na porta 6790");

			is = dataSocket.getInputStream();

			// Cria arquivo local no servidor
			fos = new FileOutputStream(new File("C://Users//guga//Documents//Redes//" + fileName));

			// Prepara variaveis para transferencia
			byte[] cbuffer = new byte[1024];
			int bytesRead;

			// Copia conteudo do canal
			while ((bytesRead = is.read(cbuffer)) != -1) {
				fos.write(cbuffer, 0, bytesRead);
				fos.flush();
			}

		}catch (FileNotFoundException e){
			System.out.println("O arquivo especificado n�o foi encontrado");
		}catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (dataSocket != null) {
				try {
					dataSocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		outToClient.writeBytes(fileName + " sent\n");
	}

	//Enviar arquivo do servidor para o cliente
	public void sendFile() throws IOException{
		Socket dataSocket = null;
		FileInputStream fileIn = null;
		OutputStream os = null;
		File file = null;

		try{

			String fileLocation = inFromClient.readLine();

			dataSocket = serverDataSocket.accept();

			// Criando tamanho de leitura
			byte[] cbuffer = new byte[1024];
			int bytesRead;

			// Criando arquivo que sera transferido pelo servidor
			file = new File(fileLocation);
			fileIn = new FileInputStream(file);

			// Criando canal de transferencia
			os = dataSocket.getOutputStream();

			// Lendo arquivo criado e enviado para o canal de transferencia
			while ((bytesRead = fileIn.read(cbuffer)) != -1) {
				os.write(cbuffer, 0, bytesRead);
				os.flush();
			}

		}catch (FileNotFoundException e){
			System.out.println("O arquivo especificado n�o foi encontrado");
		}catch (Exception e) {
			e.printStackTrace();
		} finally {		
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (dataSocket != null) {
				try {
					dataSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fileIn != null) {
				try {
					fileIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		outToClient.writeBytes("file received\n");
		if(file != null){
			file.delete();
		}
	}

}