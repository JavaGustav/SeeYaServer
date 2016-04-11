package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * SeeYa
 * @author Gustav Frigren
 *
 */
public class DatabaseManager {

	private final String GET_PASSWORD_QUERY = "SELECT PASSWORD FROM users WHERE" +
			"username = ";
	private final String CHECK_IF_USER_EXISTS_QUERY = "SELECT * FROM users WHERE"+
			" username = ";
	private final String GET_ACTIVITIES_QUERY = "SELECT * FROM activities";
	private final String GET_ACTIVITIES_HEADLINES_QUERY = "SELECT id, headLine FROM "
			+ "activities WHERE subCategory = ";
	private final String ADD_NEW_ACTIVITY_QUERY = "INSERT INTO activities"
			+ "(subCategory, maxnbrofparticipants, minnbrofparticipants, date, "
			+ "time, message, owner, headLine) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
	private final String SET_ACTIVITY_PUBLIC_QUERY = "UPDATE activities "
			+ "SET public = 1 WHERE id = ";
	private final String WRITE_LOG_QUERY = "INSERT INTO serverLog(logType, message)"
			+ "VALUES(?, ?)";
	private final String DRIVER = "com.mysql.jdbc.Driver";

	private static final String URL = "jdbc:mysql://195.178.232.7:4040/ad4063";

	private final String USERNAME = "AD4063";
	private final String PASSWORD = "sys100";

	Connection connection = null;

	public DatabaseManager() {
		initConnection();
	}

	private void initConnection() {
		try {
			Class.forName(DRIVER).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		openConnection();
	}

	@SuppressWarnings("unchecked")
	public String getActivities(String location, String category, int id) {
		JSONObject mainObject = startJson(Constants.ACTIVITIES);
		JSONArray jArray = new JSONArray();
		Statement select;
		try {
			select = connection.createStatement();
			ResultSet result = select.executeQuery(GET_ACTIVITIES_QUERY);
			while(result.next()) {
				JSONObject temp = new JSONObject();
				temp.put(Constants.USERNAME, result.getString(1));
				temp.put("category", result.getString(2));
				temp.put(Constants.SUBCATEGORY, result.getString(3));
				temp.put(Constants.MAX_NBROF_PARTICIPANTS, result.getString(4));
				temp.put(Constants.MIN_NBR_OF_PARTICIPANTS, result.getString(5));
				temp.put(Constants.DATE, result.getString(6));
				temp.put(Constants.TIME, result.getString(7));
				temp.put(Constants.MESSAGE, result.getString(8));
				temp.put(Constants.ACTIVITY_OWNER, result.getString(9));
				temp.put(Constants.HEADLINE, result.getString(10));
				jArray.add(temp);
			}
			mainObject.put("activities", jArray);
			System.out.println("RESULTS: \n" + mainObject.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mainObject.toString();
	}

	@SuppressWarnings("unchecked")
	public String getActivityHeadLines(int categoryId) {
		JSONObject mainObject = startJson(Constants.ACTIVITY_HEADLINES);
		JSONArray jArray = new JSONArray();
		Statement select;
		try {
			select = connection.createStatement();
			ResultSet result = select.executeQuery(GET_ACTIVITIES_HEADLINES_QUERY + categoryId);
			while(result.next()) {
				JSONObject temp = new JSONObject();
				temp.put(Constants.ACTIVITY_ID, result.getInt(1));
				temp.put(Constants.HEADLINE, result.getString(2));
				jArray.add(temp);
			}
			mainObject.put("headlines", jArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mainObject.toString();
	}
	//TODO
	private void buildArray() {
		
	}
	//TODO
	public String getActivity() {
		return null;
	}

	public String getPassWord(String userName) {
		Statement select;
		String passWord = null;
		try {
			select = connection.createStatement();
			ResultSet result = select.executeQuery(GET_PASSWORD_QUERY + "'"+userName+"'");
			passWord = result.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return passWord;
	}

	public boolean checkIfUserExists(String userName) throws SQLException {
		Statement select;
		ResultSet result = null;
		try {
			select = connection.createStatement();
			result = select.executeQuery(CHECK_IF_USER_EXISTS_QUERY 
					+ "'"+userName+"'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(result.next()) {
			return true;
		}
		return false;
	}

	public boolean registerNewUser(String userName, String passWord, String email) {
		System.out.println("ADDING USER........");
		if(connection == null) {   //TODO or connection.isClosed()
			openConnection();
		}
		try {
			System.out.println("ADDING USER........");
			PreparedStatement statement = connection.prepareStatement("INSERT INTO"+
					" users (username, password, email) VALUES (?, ?, ?)");
			statement.setString(1, userName);
			statement.setString(2, passWord);
			statement.setString(3, email);
			statement.executeUpdate();
			return true;
		} catch (MySQLIntegrityConstraintViolationException e2) {
			//TODO if the username already exists. Check here?
			e2.printStackTrace();
		} catch (SQLException e) {
		}
		return false;
	}

	public boolean addNewActivity(String owner, String location, int subCategory,
			int maxNbr, int minNbr, String date, 
			String time, String message, String headLine) {
		
		if(connection == null) {
			openConnection();
		}
		try {
			PreparedStatement statement = connection.prepareStatement(ADD_NEW_ACTIVITY_QUERY);
			statement.setInt(1, subCategory);
			statement.setInt(2, maxNbr);
			statement.setInt(3, minNbr);
			statement.setString(4, date);
			statement.setString(5, time);
			statement.setString(6, message);
			statement.setString(7, owner);
			statement.setString(8, headLine);
			statement.executeQuery();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public boolean publishActivity(int activityID) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(SET_ACTIVITY_PUBLIC_QUERY + activityID);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getUsers() {
		return null;
	}

	public boolean signUpForActivity(String userName, int activityID) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("INSERT INTO "+
					" signup VALUES (?, ?)");
			statement.setInt(1, activityID);
			statement.setString(2, userName);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getCategories() {
		return null;
	}
	
	public boolean writeLog(int logType, String message) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(WRITE_LOG_QUERY);
			statement.setInt(1,  logType);
			statement.setString(2, message);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean makeInsertion(String table, String columns, String values) {
		PreparedStatement statement;
		String insert = "INSERT INTO " + table + " (" + columns + " )" + "VALUES (" + values + ")";
		try {
			statement = connection.prepareStatement(insert);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void openConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public JSONObject startJson(String type) {
		JSONObject obj = new JSONObject();
		obj.put(Constants.TYPE, type);
		return obj;
	}

	public static void main(String args[]) {
		DatabaseManager db = new DatabaseManager();
		//db.getActivities("sdg", "kjsdf", 5);
		//db.registerNewUser("GF", "Hemligt", "email.com");
		//db.signUpForActivity("Liza", 3);
		db.writeLog(2, "TEST FROM SERVERAPPLICATION");
	}
}