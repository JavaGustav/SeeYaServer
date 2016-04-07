package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Hanterar en anslten klient. Tar emot och skickar meddelanden.
 * @author KEJ
 *
 */
public class ClientHandler implements Runnable {
	private Controller controller;
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	/**
	 * Konstruktor. 
	 * @param controller
	 * @param socket
	 */
	public ClientHandler(Controller controller, Socket socket){
		this.controller = controller;
		this.socket = socket;
	}
	
	/**
	 * Tar emot meddelanden fr�n klienten och skickar detta vidare till Controllern.
	 */
	public void run() {
		String jsonString;
		try{
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			while(true){
				jsonString = dis.readUTF();
				controller.processCommand(jsonString);
			}
			
		} catch(IOException e){
			//Logga
		} finally {
			if(dis != null){
				try{
					dis.close();
				} catch (IOException e){
					//Logga
				}
			}
			if(dos != null){
				try{
					dos.close();
				} catch (IOException e){
					//Logga
				}
			}
		}
	}
	
	/**
	 * Skickar meddelande tillbaka till klienten.
	 */
	public void send(String jsonString){
		try {
			dos.writeUTF(jsonString);
		} catch (IOException e) {
			// Logga
		}
	}
}