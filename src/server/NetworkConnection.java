package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * SeeYa
 * @author Gustav Frigren
 *
 */
public class NetworkConnection extends Thread {
	
	private ServerSocket serverSocket;
	private Controller controller;
	private Socket socket;
	
	public NetworkConnection(Controller controller) {
		this.controller = controller;
	}
	
	public void run() {
		try {
			serverSocket = new ServerSocket(7500);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(serverSocket != null) {
			try {
				socket = serverSocket.accept();
				new Thread(new ClientHandler()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
