package connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientThread implements Runnable {

	private Socket connectionSocket;
	public  ArrayList<RequisitionServer> l ;

	public ClientThread(Socket s, ArrayList<RequisitionServer> l) {
		this.connectionSocket = s;
		this.l = l;
	}

	public void run() {
		String clientSentence;

		BufferedReader inFromClient;
		DataOutputStream outToClient;
		try {
			inFromClient = new BufferedReader(new InputStreamReader(
					connectionSocket.getInputStream()));

			outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());
			
			clientSentence = inFromClient.readLine();
			System.out.println(clientSentence);
			RequisitionServer req = new RequisitionServer(l);
			req.createRequisition(clientSentence, connectionSocket);
			outToClient.writeBytes(req.getName_client() + " recived\r\n");
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
