package server;

import javax.swing.SwingUtilities;

public class Controller {
	private ServerGUI serverGUI;
	private NetworkConnection networkConnection;
	private Controller controller = this;
	
	public Controller(){
		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				serverGUI = new ServerGUI(controller);
			}
		});
		
		networkConnection = new NetworkConnection(this);

	}
	
	public void startServer(int port){
		if(networkConnection == null){
			networkConnection.start();
		}
	}
	
	public void stopServer(){
		networkConnection.stopServer();
	}
	
	public static void main(String[] args){
		Controller controller = new Controller();
	}
}
