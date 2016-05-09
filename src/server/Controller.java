package server;

import java.sql.SQLException;

import javax.swing.SwingUtilities;

import org.json.simple.JSONArray;
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
	 * Startar NetworkConnection sï¿½ att klienter kan ansluta.
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
	 * Stoppar NetorkConnection sï¿½ att klienter inte lï¿½ngre kan ansluta.
	 */
	public void stopServer(){
		networkConnection.stopServer();
	}
	
	/**
	 * Processar kommandostrï¿½ngen frï¿½n ClientHandler och anropar rï¿½tt metod i DatabaseManagern.
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
//				sendOwnedActivities(clientHandler, jsonObject);
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
			} else if(type.equals(Constants.SIGNUP)) {
				signUpForActivity(clientHandler, jsonObject);
			} else if(type.equals(Constants.UNREGISTER_FROM_ACTIVITY)) {
				unregisterFromActivity(clientHandler, jsonObject);
			} else if(type.equals(Constants.CHECK_IF_USER_EXISTS)){
				checkIfUserExists(clientHandler, jsonObject);
			} else if(type.equals(Constants.GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER)){
				getMaincategorySubcategoryHeadlinesForUser(clientHandler, jsonObject);
			} else if(type.equals(Constants.GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER_OWND_ACTIVITIES)){
				getMaincategorySubcategoryHeadlinesForUserOwndActivities(clientHandler,jsonObject);
			} else if(type.equals(Constants.PUBLISH_ACTIVITY_TO_SPECIFIC_USERS)){
				publishActivityToSpecificUsers(clientHandler, jsonObject);
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
			String message = "Version up to date";
			confirmMessage(clientHandler, Constants.LOCATIONS_CONFIRMATION, message);
		} else {
			sendLocations(clientHandler);
		}
	}
	
	private void checkCategoriesVersion(ClientHandler clientHandler, String userVersion) {
		String currentVersion = databaseManager.getVersion(Constants.ACTIVITY_CATEGORIES);
		if(currentVersion.equals(userVersion)) {
			String message = "Version up to date";
			confirmMessage(clientHandler, Constants.CATEGORIES_CONFIRMATION, message);
			
		} else {
			sendCategories(clientHandler);
		}
	}
	
	private void sendActivity(ClientHandler clientHandler, long id) {
		String activity;
		activity = databaseManager.getActivitiy(id);
		clientHandler.send(activity);
	}
	
//	private void sendOwnedActivities(ClientHandler clientHandler, JSONObject json) {
//		String userName;
//		userName = (String)json.get(Constants.USERNAME);
//		String headLines = databaseManager.getOwnedActivitiesHeadlines(userName);
//		clientHandler.send(headLines);
//	}
	
	private void sendCategories(ClientHandler clientHandler) {
		String categories = databaseManager.getCategories();
		clientHandler.send(categories);
	}
	
	private void sendLocations(ClientHandler clientHandler) {
		String locations = databaseManager.getLocations();
		clientHandler.send(locations);
	}
	
	/**
	 * Hï¿½mtar vï¿½rden ur new user-objektet och anropar DatabaseManager fï¿½r att fï¿½rsï¿½ka lï¿½gga in anvï¿½ndaren.
	 * Vid lyckat resultat skickar metoden ett confirm-meddelandet till klienten via ClientHandler.
	 * Vid misslyckat resultat, anvï¿½ndaren finns redan i db, skickar metoden ett error-meddelande till 
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
			
			confirmMessage(clientHandler, Constants.NEW_USER_CONFIRMATION, message);
			
		} else { //Dï¿½ har det inte gï¿½tt att lagra anvï¿½ndaren
			
			String message = "User: " + userName + " is NOT created";
			
			errorMessage(clientHandler, Constants.NEW_USER_ERROR, message);
		}
	}
	
	/**
	 * Hï¿½mtar vï¿½rden ur new logIn-objektet och anropar DatabaseManager fï¿½r att fï¿½rsï¿½ka logga in anvï¿½ndaren.
	 * Vid lyckat resultat skickar metoden ett confirm-meddelandet till klienten via ClientHandler.
	 * Vid misslyckat resultat, anvï¿½ndaren finns inte i db, skickar metoden ett error-meddelande till 
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
					
					confirmMessage(clientHandler, Constants.LOGIN_OK, message);
					clientHandler.setUserName(userName);
				} else { //Fel passord.

					String message = "Wrong password";
					
					errorMessage(clientHandler, Constants.LOGIN_FAIL, message);
				}
			} else { //Anvï¿½ndaren finns inte i databasen
				
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
	 * @param clientHandler. Klienten som resultatet ska sï¿½ndas till.
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
			
			confirmMessage(clientHandler, Constants.NEW_ACTIVTIY_CONFIRMATION ,message);
			
		} else { //Det gick inte att skapa den nya aktiviteten
			String sendMessage = "Activity not created in database";
			String error_type = "Rejected";
			
			errorMessage(clientHandler, Constants.NEW_ACTIVITY_ERROR, message);
		}
	}
	
	/**
	 * Hï¿½mtar vï¿½rden ur publish-objektet och anropar DatabaseManager fï¿½r att fï¿½rsï¿½ka lï¿½gga till publiceringen.
	 * Vid lyckat resultat skickar metoden ett confirm-meddelandet till klienten via ClientHandler.
	 * Vid misslyckat resultat, det gick inte att publicera aktiviteten, skickar metoden ett error-meddelande till 
	 * klienten via ClientHandler.
	 * @param clientHandler
	 * @param jsonObject
	 */
	private void publishActivity(ClientHandler clientHandler, JSONObject jsonObject ){
		System.out.println("publishActivity, 1");
		long activityID = (long) jsonObject.get(Constants.ID);
		System.out.println("publishActivity 1");
		System.out.println("activityID: " + activityID);
		
		if(databaseManager.publishActivity(activityID)){
			
			String message = "Activity Id: " + activityID + " published";
			
			confirmMessage(clientHandler, Constants.PUBLISH_ACTIVITY_CONFIRMATION, message);
			
		} else { //Dï¿½ har det inte gï¿½tt att lagra anvï¿½ndaren
			
			String errorType = Constants.PUBLISH_ACTIVITY_ERROR;
			String message = "Activity Id: " + activityID + " is NOT published";
			
			errorMessage(clientHandler,errorType, message);
		}	
	}
	
	/**
	 * Låter användare anmäla sig till en aktivitet förutsatt att max antal deltagare inte överskrids.
	 * @param clientHandler. Klienthanteraren varifrån anropet kom och dit svaret ska skickas.
	 * @param jsonObject. JSON-objektet med begäran om att anmäla till aktivitet.
	 */
	private void signUpForActivity(ClientHandler clientHandler, JSONObject jsonObject){
			long maxNbr = databaseManager.getMaxNbrOfParticipants((long) jsonObject.get(Constants.ID));
			long signedUp = databaseManager.getNumberOfSignedUp((long) jsonObject.get(Constants.ID));
			if(maxNbr == signedUp) { // Aktiviteten är fulltecknad.
				String message = "Activity is full";
				errorMessage(clientHandler, Constants.SIGNUP_ERROR, message);
			} else if(databaseManager.signUpForActivity(Constants.USERNAME, Long.parseLong(Constants.ID))){
				String message = "User: " + jsonObject.get(Constants.USERNAME) + " is signed up";
				confirmMessage(clientHandler, Constants.SIGNUP_CONFIRMATION, message);
			} else {
				String message = "Activity is not full. Another error occured";
				errorMessage(clientHandler, Constants.SIGNUP_ERROR, message);
			}
			
			
	}
	
	/**
	 * If a user has registered for an activity, this method do the reverse i.e do an unregister from the
	 * choosen activity.
	 * @param clientHandler. The client handler taking care of the client.
	 * @param jsonObject. The JSON object containing the command to unregister from activity.
	 */
	private void unregisterFromActivity(ClientHandler clientHandler, JSONObject jsonObject){
		if(databaseManager.unregisterFromActivity((long)jsonObject.get(Constants.ID), (String)jsonObject.get(Constants.USERNAME))){
			String message = "User: " + Constants.USERNAME + " successfully unregistered from " + Constants.ID;
			confirmMessage(clientHandler, Constants.UNREGISTER_FROM_ACTIVITY_CONFIRMATION, message);
		} else {
			String message = "User: " + Constants.USERNAME + " could not be unregistered from " + Constants.ID;
			errorMessage(clientHandler, Constants.UNREGISTER_FROM_ACTIVITY_ERROR, message);
		}
	}
	
	private void checkIfUserExists(ClientHandler clientHandler, JSONObject jsonObject){
		String userName = (String)jsonObject.get(Constants.USERNAME);
		try {
			if(databaseManager.checkIfUserExists(userName)){
				String message = userName;
				confirmMessage(clientHandler, Constants.USER_EXISTS, message);
			} else {
				String message = userName;
				errorMessage(clientHandler, Constants.INVALID_USERNAME, message);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * This method sends a structured JSON-string with the main category, subcategory and headlines for 
	 * a specific user. I sends only this for the categories where there are activities in.
	 * @param clientHandler
	 * @param jsonObject
	 */
	private void getMaincategorySubcategoryHeadlinesForUser(ClientHandler clientHandler, JSONObject jsonObject){
		String userName = (String)jsonObject.get(Constants.USERNAME);
		
		JSONObject mainObject = new JSONObject();
		JSONArray mainCategoriesArray; 
		JSONObject mainCategoryObject;
		long mainCategoryID;
		JSONArray subCategoriesArray;
		JSONObject subCategoryObject;
		long subCategoryID;
		JSONArray activityHeadlinesArray;
		JSONObject activityHeadlineObject;
		
		mainObject.put(Constants.TYPE, Constants.MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER);
		mainObject.put(Constants.USERNAME, userName);
		// Hämtar en JSONArray med huvudkategorierna i
		mainCategoriesArray = databaseManager.getMainCategoriesWithActivities(userName);
		if(mainCategoriesArray != null){
			mainObject.put(Constants.ARRAY_MAINCATEGORY, mainCategoriesArray);
			// Loopar igenom samtliga huvudkategorier och hämtar dess ID
			for(int i=0; i<mainCategoriesArray.size(); i++){
				mainCategoryObject = (JSONObject) mainCategoriesArray.get(i);
				mainCategoryID = (long) mainCategoryObject.get(Constants.ID);

				subCategoriesArray = databaseManager.getSubCategoriesWithActivities(userName, mainCategoryID);
				if(subCategoriesArray != null){
					System.out.println("getMaincategorySubcategoryHeadlinesForUser" + subCategoriesArray.toString());
					// Loopar igenom samtliga subkategorier tillhörande aktuell huvudkategori.
					for(int j=0; j<subCategoriesArray.size(); j++){
						subCategoryObject = (JSONObject) subCategoriesArray.get(j);
						subCategoryID = (long) subCategoryObject.get(Constants.ID);
						mainCategoryObject.put(Constants.ARRAY_SUBCATEGORY, subCategoriesArray);
						
						activityHeadlinesArray = databaseManager.getActivityHeadLines(userName, subCategoryID);
						subCategoryObject.put(Constants.ARRAY_HEADLINE, activityHeadlinesArray);
					}	
				}
			}
			dataMessage(clientHandler, mainObject);
		}
	}

	/**
	 * This method sends a structured JSON-string with the main category, subcategory and headlines for 
	 * a specific user. I sends only this for the categories where there are activities in.
	 * @param clientHandler
	 * @param jsonObject
	 */
	private void getMaincategorySubcategoryHeadlinesForUserOwndActivities(ClientHandler clientHandler, JSONObject jsonObject){
		String userName = (String)jsonObject.get(Constants.USERNAME);
		
		JSONObject mainObject = new JSONObject();
		JSONArray mainCategoriesArray; 
		JSONObject mainCategoryObject;
		long mainCategoryID;
		JSONArray subCategoriesArray;
		JSONObject subCategoryObject;
		long subCategoryID;
		JSONArray activityHeadlinesArray;
		JSONObject activityHeadlineObject;
		
		mainObject.put(Constants.TYPE, Constants.MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER_OWND_ACTIVITIES);
		mainObject.put(Constants.USERNAME, userName);
		// Hämtar en JSONArray med huvudkategorierna i
		mainCategoriesArray = databaseManager.getMainCategoriesWithOwnActivities(userName);
		if(mainCategoriesArray != null){
			mainObject.put(Constants.ARRAY_MAINCATEGORY, mainCategoriesArray);
			// Loopar igenom samtliga huvudkategorier och hämtar dess ID
			for(int i=0; i<mainCategoriesArray.size(); i++){
				mainCategoryObject = (JSONObject) mainCategoriesArray.get(i);
				mainCategoryID = (long) mainCategoryObject.get(Constants.ID);

				subCategoriesArray = databaseManager.getSubCategoriesWithOwnActivities(userName, mainCategoryID);
				if(subCategoriesArray != null){
					System.out.println("getMaincategorySubcategoryHeadlinesForUser" + subCategoriesArray.toString());
					// Loopar igenom samtliga subkategorier tillhörande aktuell huvudkategori.
					for(int j=0; j<subCategoriesArray.size(); j++){
						subCategoryObject = (JSONObject) subCategoriesArray.get(j);
						subCategoryID = (long) subCategoryObject.get(Constants.ID);
						mainCategoryObject.put(Constants.ARRAY_SUBCATEGORY, subCategoriesArray);
						
						activityHeadlinesArray = databaseManager.getOwnActivityHeadlines(userName, subCategoryID);
						subCategoryObject.put(Constants.ARRAY_HEADLINE, activityHeadlinesArray);
					}	
				}
			}
			dataMessage(clientHandler, mainObject);
		}
	}
	
	/**
	 * Publish an activity to the specified users. Notifies app with a PUBLISH_ACTIVITY_CONFIRMATION message
	 * together with an array with the users if the activity was published to all specified users. 
	 * Otherwise an PUBLISH_ACTIVITY_ERROR message is sent back to app with an array of the users for who 
	 * the publish did not succeed.
	 * @param clientHandler
	 * @param jsonObject
	 */
	private void publishActivityToSpecificUsers(ClientHandler clientHandler, JSONObject jsonObject){
		JSONArray users = (JSONArray) jsonObject.get(Constants.ARRAY_USERNAME);
		long activityID = (long) jsonObject.get(Constants.ID);
		JSONArray published = new JSONArray();
		JSONArray notPublished = new JSONArray();
		for(int i=0; i<users.size(); i++){
			JSONObject userObject = (JSONObject) users.get(i);
			String userName = (String) userObject.get(Constants.USERNAME);
			
			// If the publish succeed, add to published array.
			if(databaseManager.publishActivityToIndividualUser(activityID, userName)){
				published.add(userObject);
			} else {
				notPublished.add(userObject);
			}
		}
		
		if(users.size() == published.size()){
			confirmMessage(clientHandler, Constants.PUBLISH_ACTIVITY_CONFIRMATION, published.toString());
		} else {
			errorMessage(clientHandler, Constants.PUBLISH_ACTIVITY_ERROR, notPublished.toString());
		}
	}
	
	/**
	 * Send a confirmation message back to client.
	 * @param clientHandler. The client handler servicing the requesting client.
	 * @param confirmType. The type of confirm message.
	 * @param message. A complementary message.
	 */
	private void confirmMessage(ClientHandler clientHandler, String confirmType, String message){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, confirmType);
		jsonSendObject.put(Constants.MESSAGE, message);

		clientHandler.send(jsonSendObject.toString());
	}

	/**
	 * Send an error message back to client.
	 * @param clientHandler. The client handler servicing the requesting client.
	 * @param errorType. The type of error.
	 * @param message. A complementary message.
	 */
	private void errorMessage(ClientHandler clientHandler, String errorType, String message){
		JSONObject jsonSendObject = new JSONObject();
		
		jsonSendObject.put(Constants.TYPE, errorType);
		jsonSendObject.put(Constants.MESSAGE, message);

		clientHandler.send(jsonSendObject.toString());
	}
	
	private void dataMessage(ClientHandler clientHandler, JSONObject data){
		clientHandler.send(data.toString());
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