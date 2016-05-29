package admin;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * SeeYa admin
 * @author Gustav Frigren
 *
 */
public class Controller {

	private MainWindow mainWindow;
	private NetworkConnection connection;
	private JSONObject tempJson;
	JSONParser parser = new JSONParser();

	public Controller(MainWindow m) {
		System.out.println("ADMIN CONSTR CONTROLLER");
		mainWindow = m;
		connection = new NetworkConnection(this);
	}

	private void init() {
		
	}

	@SuppressWarnings("unchecked")
	public boolean addUser(String userName, String password, String email) {
		tempJson = startJson(Constants.NEWUSER);
		tempJson.put(Constants.USERNAME, userName);
		tempJson.put(Constants.PASSWORD, password);
		tempJson.put(Constants.EMAIL, email);
		connection.send(tempJson);
		tempJson = null;
		return false;
	}

	@SuppressWarnings("unchecked")
	private JSONObject startJson(String type) {
		JSONObject obj = new JSONObject();
		obj.put(Constants.TYPE, type);
		return obj;
	}

	public void requestUserList() {
		JSONObject obj = startJson(Constants.GET_ALL_USERS);
		connection.send(obj);
	}
	
	public void requestUserEmail(String userName) {
		JSONObject obj = startJson(Constants.GET_USER_EMAIL);
		obj.put(Constants.USERNAME, userName);
		connection.send(obj);
	}

	public void stopConnection() {
		connection.stopConnection();
		connection = null;
	}

	public void checkMessage(String message) {
		JSONObject obj = toJson(message);
		String type = (String) obj.get(Constants.TYPE);
		if(type.equals(Constants.GET_ALL_USERS)) {
			JSONArray array = (JSONArray) obj.get(Constants.ARRAY_USERNAME);
			String[] test = extractUserListContent(array);
			mainWindow.setUserListData(test);
		} else if(type.equals(Constants.GET_USER_EMAIL)) {
			String userName = (String) obj.get(Constants.USERNAME);
			String email = (String) obj.get(Constants.EMAIL);
			mainWindow.showInfoDialog("USER INFO", 
					"Username: " + userName, "Email: " + email);
		}
	}

	public JSONObject toJson(String source) {
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public String[] extractUserListContent(JSONArray array) {
		String[] userList = new String[array.size()];
		for(int i = 0; i < array.size(); i++) {
			JSONObject temp = (JSONObject) array.get(i);
			userList[i] = (String) temp.get(Constants.USERNAME);
		}
		System.out.println("ADMINCONTROLLER " + userList.toString());
		return userList;
	}

	public void sendNewSubCat(long mainCat, String subCat) {
		
	}

	public void sendNewMainCat(String mainCat) {
		
	}
	
	public void sendNewCity(long landscape, String city) {
		
	}
}