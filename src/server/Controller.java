package server;

import javax.swing.SwingUtilities;

/**
 * Hanterar kommunikation mellan GUI och ClientHandler och mellan ClientHandler och DatabaseManagern
 * @author KEJ
 *
 */
public class Controller {
	private ServerGUI serverGUI;
	private NetworkConnection networkConnection;
	private Controller controller = this;
	
	/**
	 * Konstruktor. Skapar GUI och NetworkConnection
	 */
	public Controller(){
		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				serverGUI = new ServerGUI(controller);
			}
		});

	}
	
	/**
	 * Startar NetworkConnection så att klienter kan ansluta.
	 * @param port
	 */
	public void startServer(int port){
		System.out.println("In Controller, startServer");
		if(networkConnection == null){
			networkConnection = new NetworkConnection(this, port);
			networkConnection.start();
		}
	}
	
	/**
	 * Stoppar NetorkConnection så att klienter inte längre kan ansluta.
	 */
	public void stopServer(){
		networkConnection.stopServer();
	}
	
	/**
	 * Processar kommandosträngen från ClientHandler och anropar rätt metod i DatabaseManagern.
	 */
	public void processCommand(String jsonString){
		
	}
	
	public static void main(String[] args){
		Controller controller = new Controller();
	}
}
