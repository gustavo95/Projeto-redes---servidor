package connection;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	public static void main(String argv[]) throws Exception {

		System.out.println("SERVIDOR INICIOU, ESPERANDO CONEXÃO NA PORTA 6789!");
		 ArrayList<RequisitionServer> list_of_requisitions  = new ArrayList<RequisitionServer>();
		ServerSocket welcomeSocket = new ServerSocket(6789);

		while (true) {
			
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Conexão estabelecida com o cliente");
			Thread t = new Thread(new ClientThread(connectionSocket, list_of_requisitions));
			t.start();
		}
	}

}
