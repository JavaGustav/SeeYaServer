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
	
	private boolean running = false;
	
	private final int PORT = 7500;
	
	private ServerSocket serverSocket;
	private Controller controller;
	private Socket socket;
	
	public NetworkConnection(Controller controller) {
		this.controller = controller;
	}

	public void run() {
		try {
			running = true;
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			running = false;
			e.printStackTrace();
		}
		while(serverSocket != null && running) {
			try {
				socket = serverSocket.accept();
				new Thread(new ClientHandler()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopServer() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		running = false;
	}
}