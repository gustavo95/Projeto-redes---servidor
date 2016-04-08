package connection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import fileManagement.FileOperations;
import fileManagement.FileToTransfer;

public class ClientThread implements Runnable {

	private Socket connectionSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	public  ArrayList<RequisitionServer> l ;

	public ClientThread(Socket s, ArrayList<RequisitionServer> l) {
		this.connectionSocket = s;
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
						case "2 SendingFile":
							receiveFile();
							break;
						default:
							System.out.println("outra coisa: " + clientSentence);
							break;
					}
				}else{
					inFromClient.reset();
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
		outToClient.writeBytes(req.getName_client() + " recived\n");
	}
	
	public void receiveFile() throws IOException{
		byte[] objectAsByte = new byte[connectionSocket.getReceiveBufferSize()];
		BufferedInputStream bf = new BufferedInputStream( connectionSocket.getInputStream());
		bf.read(objectAsByte);
		FileOperations fo = new FileOperations();
		FileToTransfer ftt = fo.getFileFromByte(objectAsByte);
		bf.close();
		//fo.writeFile(ftt, "C:\\Users\\guga\\Documents\\Redes");
		outToClient.writeBytes("File recived\n");
	}

}
