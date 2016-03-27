package connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable {

	private Socket connectionSocket;

	public ClientThread(Socket s) {
		this.connectionSocket = s;
	}

	public void run() {
		String clientSentence;
		String capitalizedSentence;

		BufferedReader inFromClient;
		DataOutputStream outToClient;
		try {
			inFromClient = new BufferedReader(new InputStreamReader(
					connectionSocket.getInputStream()));

			outToClient = new DataOutputStream(
					connectionSocket.getOutputStream());

			clientSentence = inFromClient.readLine();
			capitalizedSentence = clientSentence.toUpperCase() + '\n';
			System.out.println(clientSentence);
			System.out.println(capitalizedSentence);
			//outToClient.writeBytes(capitalizedSentence);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
