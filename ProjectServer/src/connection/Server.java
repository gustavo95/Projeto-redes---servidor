package connection;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import requisitionManagement.Requisition;

public class Server {

	public static void main(String argv[]) throws Exception {

		System.out.println("SERVIDOR INICIOU, ESPERANDO CONEXÃO NA PORTA 6789!");
		ArrayList<Requisition> listRequisitions  = new ArrayList<Requisition>();
		ArrayList<User> listUsers = new ArrayList<User>();
		@SuppressWarnings("resource")
		ServerSocket welcomeSocket = new ServerSocket(6789);
		ServerSocket dataSocket = new ServerSocket(6790);;

		while (true) {
			
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Conexão estabelecida com o cliente");
			Thread t = new Thread(new ClientThread(connectionSocket, dataSocket, listRequisitions, listUsers));
			t.start();
		}
	}

}