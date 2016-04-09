package connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	public  ArrayList<RequisitionServer> l ;

	public ClientThread(Socket cs, ServerSocket sds, ArrayList<RequisitionServer> l) {
		this.connectionSocket = cs;
		this.serverDataSocket = sds;
		this.l = l;
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
							break;
						case "1 SendingRequisition":
							receiveRequisition();
							break;
						case "2 SendFile":
							receiveFile();
							break;
						case "3 ReceiveFile":
							sendFile();
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
	
	public void receiveRequisition() throws IOException{
		String clientSentence = inFromClient.readLine();
		System.out.println(clientSentence);
		
		RequisitionServer req = new RequisitionServer(l);
		req.createRequisition(clientSentence, connectionSocket);
		
		outToClient.writeBytes(req.getName_client() + " sent\n");
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