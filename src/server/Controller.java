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
	private TimeDate time;
	
	/**
	 * Konstruktor. Skapar GUI och NetworkConnection
	 */
	public Controller(){
		time = new TimeDate();
		SwingUtilities.invokeLater(new Runnable(){

			public void run() {
				serverGUI = new ServerGUI(controller);
			}
		});
		
		databaseManager = new DatabaseManager(this);

	}
	
	/**
	 * Startar NetworkConnection s� att klienter kan ansluta.
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
	 * Stoppar NetorkConnection s� att klienter inte l�ngre kan ansluta.
	 */
	public void stopServer(){
		networkConnection.stopServer();
	}
	
	/**
	 * Processar kommandostr�ngen fr�n ClientHandler och anropar r�tt metod i DatabaseManagern.
	 */
	public void processCommand(ClientHandler clientHandler, String jsonString){
		JSONObject jsonObject = null;
		JSONParser jsonParser = new JSONParser();
		try {
			jsonObject = (JSONObject) jsonParser.parse(jsonString);
			System.out.println(jsonObject.toString());
			
			//Checkar typ av meddelande.
			String type;
			System.out.println("JSON: " + jsonObject.toString());
			type = (String) jsonObject.get(Constants.TYPE);
			System.out.println("TYPE: " + type);
			if(type.equals(Constants.NEWUSER)){
				System.out.println("NEWUSER......................");
				createNewUser(clientHandler, jsonObject);
			} else if(type.equals(Constants.LOGIN)){
				logIn(clientHandler, jsonObject);
			} else if(type.equals(Constants.NEWACTIVITY)){
				newActivity(clientHandler, jsonObject);
			} else if(type.equals(Constants.PUBLISH_ACTIVITY)){
				publishActivity(clientHandler, jsonObject);
			} else if(type.equals(Constants.ACTIVITY_CATEGORIES)) {
				sendCategories(clientHandler);
			} else if(type.equals(Constants.MY_ACTIVITIES)) {
				sendOwnedActivities(clientHandler, jsonObject);
			} else if(type.equals(Constants.LOCATIONS)) {
				sendLocations(clientHandler);
			} else if(type.equals(Constants.ACTIVITIY)) {
				sendActivity(clientHandler, (long)jsonObject.get(Constants.ID));
			} else if(type.equals(Constants.LOCATIONS_VERSION_NBR)) {
				String version = (String)jsonObject.get(Constants.ID);
				checkLocationsVersion(clientHandler, version);
			} else if(type.equals(Constants.CATEGORIES_VERSION_NUMBER)) {
				String version = (String)jsonObject.get(Constants.ID);
				checkCategoriesVersion(clientHandler, version);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("No valid JSON-string: " + jsonString);
			e.printStackTrace();
		}

	}
	
	private void checkLocationsVersion(ClientHandler clientHandler, String userVersion) {
		String currentVersion = databaseManager.getVersion(Constants.LOCATIONS);
		if(currentVersion.equals(userVersion)) {
			JSONObject json = new JSONObject();
			json.put(Constants.TYPE, Constants.LOCATIONS_CONFIRMATION);
			clientHandler.send(json.toString());
		} else {
			sendCategories(clientHandler);
		}
	}
	
	private void checkCategoriesVersion(ClientHandler clientHandler, String userVersion) {
		String currentVersion = databaseManager.getVersion(Constants.ACTIVITY_CATEGORIES);
		if(currentVersion.equals(userVersion)) {
			JSONObject json = new JSONObject();
			json.put(Constants.TYPE, Constants.CATEGORIES_CONFIRMATION);
			clientHandler.send(json.toString());
		} else {
			sendLocations(clientHandler);
		}
	}
	
	private void sendActivity(ClientHandler clientHandler, long id) {
		String activity;
		activity = databaseManager.getActivitiy(id);
		clientHandler.send(activity);
	}
	
	private void sendOwnedActivities(ClientHandler clientHandler, JSONObject json) {
		String userName;
		userName = (String)json.get(Constants.USERNAME);
		String headLines = databaseManager.getOwnedActivitiesHeadlines(userName);
		clientHandler.send(headLines);
	}
	
	private void sendCategories(ClientHandler clientHandler) {
		String categories = databaseManager.getCategories();
		clientHandler.send(categories);
	}
	
	private void sendLocations(ClientHandler clientHandler) {
		String locations = databaseManager.getLocations();
		clientHandler.send(locations);
	}
	
	/**
	 * H�mtar v�rden ur new user-objektet och anropar DatabaseManager f�r att f�rs�ka l�gga in anv�ndaren.
	 * Vid lyckat resultat skickar metoden ett confirm-meddelandet till klienten via ClientHandler.
	 * Vid misslyckat resultat, anv�ndaren finns redan i db, skickar metoden ett error-meddelande till 
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
			
		} else { //D� har det inte g�tt att lagra anv�ndaren
			
			String message = "User: " + userName + " is NOT created";
			String error_type = "Rejected";
			
			errorMessage(clientHandler, message, error_type);
		}
	}
	
	/**
	 * H�mtar v�rden ur new logIn-objektet och anropar DatabaseManager f�r att f�rs�ka logga in anv�ndaren.
	 * Vid lyckat resultat skickar metoden ett confirm-meddelandet till klienten via ClientHandler.
	 * Vid misslyckat resultat, anv�ndaren finns inte i db, skickar metoden ett error-meddelande till 
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
					
					confirmMessage(clientHandler, message, Constants.LOGIN_OK);
					clientHandler.setUserName(userName);
				} else { //Fel passord.

					String message = "Wrong password";
					String error_type = "Rejected";
					
					errorMessage(clientHandler, message, Constants.LOGIN_FAIL);
				}
			} else { //Anv�ndaren finns inte i databasen
				
				String message = "User not in database";
				String error_type = "Rejected";
				
				errorMessage(clientHandler, message, Constants.LOGIN_FAIL);
			}
			
		}catch (SQLException e){
			//ToDo
		}	
	}
	
	/**
	 * Skapar ny aktivitet.
	 * @param clientHandler. Klienten som resultatet ska s�ndas till.
	 * @param jsonObject. Objektet med intata/kommando.
	 */
	private void newActivity(ClientHandler clientHandler, JSONObject jsonObject){
		String owner = (String) jsonObject.get(Constants.NAME);
		long location = (long) jsonObject.get(Constants.PLACE);
		long subcategory = (long) jsonObject.get(Constants.SUBCATEGORY); 
		long maxnbr = (long) jsonObject.get(Constants.MAX_NBROF_PARTICIPANTS);
		long minNbrOfParticipants = (long) jsonObject.get(Constants.MIN_NBR_OF_PARTICIPANTS);
		String date = (String) jsonObject.get(Constants.DATE); 
		String time = (String) jsonObject.get(Constants.TIME); 	
		String message = (String) jsonObject.get(Constants.MESSAGE);
		String headline = (String) jsonObject.get(Constants.HEADLINE); 
		
		if(databaseManager.addNewActivity(owner, location, subcategory, maxnbr, minNbrOfParticipants, date, time, message, headline)){
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
	 * H�mtar v�rden ur publish-objektet och anropar DatabaseManager f�r att f�rs�ka l�gga till publiceringen.
	 * Vid lyckat resultat skickar metoden ett confirm-meddelandet till klienten via ClientHandler.
	 * Vid misslyckat resultat, det gick inte att publicera aktiviteten, skickar metoden ett error-meddelande till 
	 * klienten via ClientHandler.
	 * @param clientHandler
	 * @param jsonObject
	 */
	public void publishActivity(ClientHandler clientHandler, JSONObject jsonObject ){
		System.out.println("publishActivity, 1");
		long activityID = (long) jsonObject.get(Constants.ID);
		System.out.println("publishActivity 1");
		System.out.println("activityID: " + activityID);
		
		if(databaseManager.publishActivity(activityID)){
			
			String message = "Activity Id: " + activityID + " published";
			String confirmation_type = "OK";
			
			confirmMessage(clientHandler, message, confirmation_type);
			
		} else { //D� har det inte g�tt att lagra anv�ndaren
			
			String message = "Activity Id: " + activityID + " is NOT published";
			String error_type = "Rejected";
			
			errorMessage(clientHandler, message, error_type);
		}	
	}

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

	public void log(String logType, String message) {
		databaseManager.writeLog(logType, message);
		String date = time.getDateTime();
		String txt = date + " " + logType + " " + message;
		serverGUI.writeLogToGUI(txt);
	}

	public static void main(String[] args){
		Controller controller = new Controller();
	}
}