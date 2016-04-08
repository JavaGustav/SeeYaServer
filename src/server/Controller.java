package server;

import java.sql.SQLException;

import javax.swing.SwingUtilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Hanterar kommunikation mellan GUI och ClientHandler och mellan ClientHandler och DatabaseManagern
 * @author KEJ
 *
 */
public class Controller {
	private ServerGUI serverGUI;
	private NetworkConnection networkConnection;
	private Controller controller = this;
	private DatabaseManager databaseManager;
	
	/**
	 * Konstruktor. Skapar GUI och NetworkConnection
	 */
	public Controller(){
		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				serverGUI = new ServerGUI(controller);
			}
		});
		
		databaseManager = new DatabaseManager();

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
	public void processCommand(ClientHandler clientHandler, String jsonString){
		JSONObject jsonObject = null;
		JSONParser jsonParser = new JSONParser();
		try {
			jsonObject = (JSONObject) jsonParser.parse(jsonString);
			System.out.println(jsonObject.toString());
			
			//Checkar typ av meddelande.
			int type;
			type = (int) jsonObject.get(Constants.TYPE);
			if(type == Constants.NEWUSER){
				createNewUser(clientHandler, jsonObject);
			} else if(type == Constants.LOGIN){
				logIn(clientHandler, jsonObject);
			} else if(type == Constants.NEWACTIVITY){
				newActivity(clientHandler, jsonObject);
			} else if(type == Constants.PUBLISH_ACTIVITY){
//				publishActivity(clientHandler, jsonObject);
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("No valid JSON-string: " + jsonString);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Hämtar värden ur new user-objektet och anropar DatabaseManager för att försöka lägga in användaren.
	 * Vid lyckat resultat skickar metoden ett confirm-meddelandet till klienten via ClientHandler.
	 * Vid misslyckat resultat, användaren finns redan i db, skickar metoden ett error-meddelande till 
	 * klienten via ClientHandler.
	 * @param jsonObject
	 */
	private void createNewUser(ClientHandler clientHandler, JSONObject jsonObject){
		String userName, passWord, email;
		userName = (String) jsonObject.get(Constants.USERNAME);
		passWord = (String) jsonObject.get(Constants.PASSWORD);
		email = (String) jsonObject.get(Constants.EMAIL);
		
		if(databaseManager.registerNewUser(userName, passWord,email)){
			
			String message = "User: " + userName + " created";
			String confirmation_type = "OK";
			
			confirmMessage(clientHandler, message, confirmation_type);
			
		} else { //Då har det inte gått att lagra användaren
			
			String message = "User: " + userName + " is NOT created";
			String error_type = "Rejected";
			
			errorMessage(clientHandler, message, error_type);
		}
	}
	
	/**
	 * Hämtar värden ur new logIn-objektet och anropar DatabaseManager för att försöka logga in användaren.
	 * Vid lyckat resultat skickar metoden ett confirm-meddelandet till klienten via ClientHandler.
	 * Vid misslyckat resultat, användaren finns inte i db, skickar metoden ett error-meddelande till 
	 * klienten via ClientHandler.
	 * @param clientHandler
	 * @param jsonObject
	 */
	private void logIn(ClientHandler clientHandler, JSONObject jsonObject){
		String userName, passWord;
		userName = (String) jsonObject.get(Constants.USERNAME);
		passWord = (String) jsonObject.get(Constants.PASSWORD);
		
		try{
			if(databaseManager.checkIfUserExists(userName)){
				if(passWord.equals(databaseManager.getPassWord(userName))){ //Korrekt passord.
					
					String message = "User: " + "logged in succesfully";
					String confirmation_type = "OK";
					
					confirmMessage(clientHandler, message, confirmation_type);
					
				} else { //Fel passord.

					String message = "Wrong password";
					String error_type = "Rejected";
					
					errorMessage(clientHandler, message, error_type);
				}
			} else { //Användaren finns inte i databasen
				
				String message = "User not in database";
				String error_type = "Rejected";
				
				errorMessage(clientHandler, message, error_type);
			}
			
		}catch (SQLException e){
			//ToDo
		}	
	}
	
	/**
	 * Skapar ny aktivitet.
	 * @param clientHandler. Klienten som resultatet ska sändas till.
	 * @param jsonObject. Objektet med intata/kommando.
	 */
	private void newActivity(ClientHandler clientHandler, JSONObject jsonObject){
		String owner = (String) jsonObject.get(Constants.NAME);
		String location = (String) jsonObject.get(Constants.PLACE);
		int subcategory = (int) jsonObject.get(Constants.SUBCATEGORY); 
		int maxnbr = (int) jsonObject.get(Constants.MAX_NBROF_PARTICIPANTS);
		int nbrOfParticipants = (int) jsonObject.get(Constants.NBR_OF_PARTICIPANTS);
		int minnbr = (int) jsonObject.get(Constants.TIME);
		String date = (String) jsonObject.get(Constants.DATE); 
		String time = (String) jsonObject.get(Constants.TIME); 	
		String message = (String) jsonObject.get(Constants.MESSAGE);
		String headline = (String) jsonObject.get(Constants.HEADLINE); 
		
		if(databaseManager.addNewActivity(owner, location, subcategory, maxnbr, minnbr, date, time, message, headline)){
			//Det gick att skapa den nya aktiviteten
			String sendMessage = "Activity: " + "created succesfully";
			String confirmation_type = "OK";
			
			confirmMessage(clientHandler, message, confirmation_type);
			
		} else { //Det gick inte att skapa den nya aktiviteten
			String sendMessage = "Activity not created in database";
			String error_type = "Rejected";
			
			errorMessage(clientHandler, message, error_type);
		}
	}
	
	/**
	 * Hämtar värden ur publish-objektet och anropar DatabaseManager för att försöka lägga till publiceringen.
	 * Vid lyckat resultat skickar metoden ett confirm-meddelandet till klienten via ClientHandler.
	 * Vid misslyckat resultat, det gick inte att publicera aktiviteten, skickar metoden ett error-meddelande till 
	 * klienten via ClientHandler.
	 * @param clientHandler
	 * @param jsonObject
	 */
//	public void publishActivity(ClientHandler clientHandler, JSONObject jsonObject ){
//		int activityID = (int) jsonObject.get(Constants.ACTIVITY_ID);
//		
//		if(databaseManager.publishActivity(int activityID)){
//			
//			String message = "Activity Id: " + activityID + " published";
//			String confirmation_type = "OK";
//			
//			confirmMessage(clientHandler, message, confirmation_type);
//			
//		} else { //Då har det inte gått att lagra användaren
//			
//			String message = "Activity Id: " + activityID + " is NOT published";
//			String error_type = "Rejected";
//			
//			errorMessage(clientHandler, message, error_type);
//		}	
//	}
	
	public void confirmMessage(ClientHandler clientHandler, String message, String confirmation_type){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.CONFIRMATION);
		jsonSendObject.put(Constants.MESSAGE, message);
		jsonSendObject.put(Constants.CONFIRMATION_TYPE, confirmation_type);

		clientHandler.send(jsonSendObject.toString());
	}

	public void errorMessage(ClientHandler clientHandler, String message, String error_type){
		JSONObject jsonSendObject = new JSONObject();
		
		jsonSendObject.put(Constants.TYPE, Constants.ERROR);
		jsonSendObject.put(Constants.MESSAGE, message);
		jsonSendObject.put(Constants.ERROR_TYPE, error_type);

		clientHandler.send(jsonSendObject.toString());
	}
	
	public static void main(String[] args){
		Controller controller = new Controller();
	}
}
