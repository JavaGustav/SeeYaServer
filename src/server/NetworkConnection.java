package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.simple.JSONObject;
/**
 * SeeYa
 * @author Gustav Frigren
 *
 */
public class NetworkConnection extends Thread {
	
	private boolean running = false;
	
	//private final int PORT = 7500;
	private int port;
	private ServerSocket serverSocket;
	private Controller controller;
	private Socket socket;
	
	public NetworkConnection(Controller controller, int port) {
		this.controller = controller;
		this.port = port;
		System.out.println("NetworkConnection");
	}

	public void run() {
		System.out.println("In NetworkConnection, run");
		try {
			running = true;
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			running = false;
			e.printStackTrace();
		}
		while(serverSocket != null && running) {
			System.out.println("In NetworkConnection, run, while");
			try {
				socket = serverSocket.accept();
				System.out.println("NetworkConnection, run, new client connected");
				new Thread(new ClientHandler(controller, socket)).start();
			} catch (IOException e) {
				//e.printStackTrace();
				//Logga
				System.out.println("ServerSocket closed");
			}
		}
	}

	public void stopServer() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("stopServer in IOException");
			//e.printStackTrace();
		}
		running = false;
	}
}