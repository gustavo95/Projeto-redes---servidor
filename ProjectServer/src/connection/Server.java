package connection;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String argv[]) throws Exception {

		System.out.println("SERVIDOR INICIOU, ESPERANDO CONEXÃO NA PORTA 6789!");

		ServerSocket welcomeSocket = new ServerSocket(6789);

		while (true) {

			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Conexão estabelecida com o cliente");
			Thread t = new Thread(new ClientThread(connectionSocket));
			t.start();
		}
	}

}
