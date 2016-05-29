package admin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

/**
 * SeeYa admin
 * @author Gustav Frigren
 *
 */
public class NetworkConnection extends Thread {
	
	//private String ip = "213.65.110.13";
	private String ip = "localhost";
	private int port = 7500;
	
	private Controller controller;
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private String result;
	private boolean running = false;
	
	public NetworkConnection(Controller controller) {
		System.out.println("ADMIN CONSTR NETWORKCONNECTION");
		this.controller = controller;
		this.start();
	}

	public void run() {
		try {
			socket = new Socket(ip, port);
			output = new DataOutputStream(socket.getOutputStream());
			input = new DataInputStream(socket.getInputStream());
			running = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(socket != null && running) {
			try {
				result = input.readUTF();
				controller.checkMessage(result);
				System.out.println(result);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void send(JSONObject obj) {
		try {
			output.writeUTF(obj.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopConnection() {
		running = false;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}