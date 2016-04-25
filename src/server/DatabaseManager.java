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

	private final String GET_PASSWORD_QUERY = "SELECT PASSWORD FROM users WHERE " +
			"username = ";
	private final String CHECK_IF_USER_EXISTS_QUERY = "SELECT * FROM users WHERE"+
			" username = ";
	private final String GET_ACTIVITIES_QUERY = "SELECT subCategory, "
			+ "maxnbrofparticipants, minnbrofparticipants, date, time, message, "
			+ "owner, headline, datePublished, location FROM activities WHERE id = ";
	private final String GET_ACTIVITIES_HEADLINES_QUERY = "SELECT id, headLine, date FROM "
			+ "activities WHERE subCategory = ";
	private final String GET_OWNED_ACTIVITIES_HEADLINES = "SELECT id, headLine "
			+ ", date FROM activities WHERE owner = ";
	private final String ADD_NEW_ACTIVITY_QUERY = "INSERT INTO activities"
			+ "(subCategory, maxnbrofparticipants, minnbrofparticipants, date, "
			+ "time, message, owner, headLine, location) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String WRITE_LOG_QUERY = "INSERT INTO serverLog(logType, message)"
			+ "VALUES(?, ?)";
	private final String GET_VERSION_QUERY = "SELECT version FROM versions"
			+ " WHERE title = ";
	
	private final String DRIVER = "com.mysql.jdbc.Driver";

	private static final String URL = "jdbc:mysql://195.178.232.7:4040/ad4063";

	private final String USERNAME = "AD4063";
	private final String PASSWORD = "sys100";
	
	private Controller controller;

	Connection connection = null;

	public DatabaseManager(Controller controller) {
		this.controller = controller;
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
	public String getActivitiy(long id) {
		JSONObject mainObject = startJson(Constants.ACTIVITIY);
		Statement select;
		try {
			select = connection.createStatement();
			ResultSet result = select.executeQuery(GET_ACTIVITIES_QUERY + id);
			result.first();
			mainObject.put(Constants.ID, id);
			mainObject.put(Constants.SUBCATEGORY, result.getString(1));
			mainObject.put(Constants.MAX_NBROF_PARTICIPANTS, result.getInt(2));
			mainObject.put(Constants.MIN_NBR_OF_PARTICIPANTS, result.getInt(3));
			mainObject.put(Constants.DATE, result.getString(4));
			mainObject.put(Constants.TIME, result.getString(5));
			mainObject.put(Constants.MESSAGE, result.getString(6));
			mainObject.put(Constants.ACTIVITY_OWNER, result.getString(7));
			mainObject.put(Constants.HEADLINE, result.getString(8));
			mainObject.put(Constants.NBR_OF_SIGNEDUP, getNumberOfSignedUp(id));
			mainObject.put(Constants.DATE_PUBLISHED, result.getString(9));
			long location = result.getInt(10);
			mainObject.put(Constants.PLACE, getLocationName(location));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(mainObject.toString());
		return mainObject.toString();
	}
	
	//TODO remove
	private String getLocationName(long id) {
		Statement select;
		String name = "";
		try {
			select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT title FROM cities WHERE"
					+ " id = " + id);
			result.first();
			name = result.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}

	private long getNumberOfSignedUp(long activityID) {
		long nbr = 0;
		Statement select;
		try {
			select = connection.createStatement();
			ResultSet result = select.executeQuery("SELECT COUNT(*) FROM signup"
					+ " WHERE activityID = " + activityID);
			result.first();
			nbr = result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbr;
	}
	
	private long getMaxNbrOfParticipants(long activityId) {
		long maxNbr = -1;
		Statement select;
		ResultSet result;
		try {
			select = connection.createStatement();
			result = select.executeQuery("SELECT maxnbrofparticipants FROM activities"
					+ " WHERE id = " + activityId);
			result.first();
			maxNbr = result.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return maxNbr;
	}

	@SuppressWarnings("unchecked")
	public String getActivityHeadLines(long categoryId) {
		JSONObject mainObject = startJson(Constants.ACTIVITY_HEADLINES);
		JSONArray jArray = new JSONArray();
		Statement select;
		try {
			select = connection.createStatement();
			ResultSet result = select.executeQuery(
					GET_ACTIVITIES_HEADLINES_QUERY + categoryId);
			while(result.next()) {
				JSONObject temp = new JSONObject();
				temp.put(Constants.ID, result.getInt(1));
				temp.put(Constants.HEADLINE, result.getString(2));
				temp.put(Constants.DATE, result.getString(3));
				jArray.add(temp);
			}
			mainObject.put(Constants.ARRAY_HEADLINE, jArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(mainObject.toString());
		return mainObject.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getOwnedActivitiesHeadlines(String userName) {
		JSONObject mainObject = startJson(Constants.ACTIVITY_HEADLINES);
		JSONArray jArray = new JSONArray();
		Statement select;
		try {
			select = connection.createStatement();
			ResultSet result = select.executeQuery(GET_OWNED_ACTIVITIES_HEADLINES + 
					"'"+userName+"'");
			while(result.next()) {
				JSONObject temp = new JSONObject();
				temp.put(Constants.ID, result.getInt(1));
				temp.put(Constants.HEADLINE, result.getString(2));
				temp.put(Constants.DATE, result.getString(3));
				jArray.add(temp);
			}
			mainObject.put(Constants.ARRAY_HEADLINE, jArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(mainObject.toString());
		return mainObject.toString();
	}

	//TODO
	private void buildArray() {

	}

	public String getPassWord(String userName) {
		Statement select;
		String passWord = null;
		try {
			select = connection.createStatement();
			ResultSet result = select.executeQuery(
					GET_PASSWORD_QUERY + "'"+userName+"'");
			result.first();
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
		if(connection == null) {   //TODO or connection.isClosed()
			openConnection();
		}
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO"+
					" users (username, password, email) VALUES (?, ?, ?)");
			statement.setString(1, userName);
			statement.setString(2, passWord);
			statement.setString(3, email);
			statement.executeUpdate();
			controller.log(Constants.LOG_INFO, "New user added: " + userName);
			return true;
		} catch (MySQLIntegrityConstraintViolationException e2) {
			//TODO if the username already exists. Check here?
			e2.printStackTrace();
		} catch (SQLException e) {
		}
		return false;
	}

	public boolean addNewActivity(String owner, long location, long subCategory,
			long maxNbr, long minNbr, String date,
			String time, String message, String headLine) {

		if(connection == null) {
			openConnection();
		}
		try {
			PreparedStatement statement = connection.prepareStatement(
					ADD_NEW_ACTIVITY_QUERY);;
			statement.setLong(1, subCategory);
			statement.setLong(2, maxNbr);
			statement.setLong(3, minNbr);
			statement.setString(4, date);
			statement.setString(5, time);
			statement.setString(6, message);
			statement.setString(7, owner);
			statement.setString(8, headLine);
			statement.setLong(9, location);
			statement.executeUpdate();
			controller.log(Constants.LOG_INFO, "New activity created");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean publishActivity(long activityID) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("UPDATE activities SET "
					+ "datePublished = NOW() WHERE id = " + activityID);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String getUsers() {
		return null;
	}

	public boolean checkIfActivityIsFull(long activityId) {
		long maxNbr = getMaxNbrOfParticipants(activityId);
		long signedUp = getNumberOfSignedUp(activityId);
		if(maxNbr == signedUp) {
			return true;
		}
		return false;
	}

	public boolean signUpForActivity(String userName, long activityID) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("INSERT INTO "+
					" signup VALUES (?, ?)");
			statement.setLong(1, activityID);
			statement.setString(2, userName);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public String getCategories() {
		JSONObject mainObj = startJson(Constants.ACTIVITY_CATEGORIES);
		mainObj.put(Constants.CATEGORIES_VERSION_NUMBER, 
				getVersion(Constants.ACTIVITY_CATEGORIES));
		Statement select;
		Statement selectInner;
		ResultSet resultOuter;
		ResultSet resultInner;
		JSONArray jArray = new JSONArray();
		JSONArray mainArray = new JSONArray();
		JSONObject temp = new JSONObject();
		int index = 0;
		try {
			select = connection.createStatement();
			resultOuter = select.executeQuery("SELECT id, title FROM mainCategories");
			while(resultOuter.next()) {
				index++;
				temp = new JSONObject();
				int mainId = resultOuter.getInt(1);
				String mainTitle = resultOuter.getString(2);
				temp.put(Constants.NAME, mainTitle);
				temp.put(Constants.ID, mainId);
				selectInner = connection.createStatement();
				resultInner = selectInner.executeQuery("SELECT id, title FROM "
						+ "subcategories WHERE parentId = " + index);
				jArray = new JSONArray();
				while(resultInner.next()) {
					JSONObject inner = new JSONObject();
					inner.put(Constants.ID, resultInner.getInt(1));
					inner.put(Constants.NAME, resultInner.getString(2));
					jArray.add(inner);
				}
				temp.put(Constants.ARRAY_SUBCATEGORY, jArray);
				mainArray.add(temp);
			}
			mainObj.put(Constants.ARRAY_MAINCATEGORY, mainArray);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return mainObj.toString();
	}

	@SuppressWarnings("unchecked")
	public String getLocations() {
		JSONObject mainObj = startJson(Constants.LOCATIONS);
		mainObj.put(Constants.LOCATIONS_VERSION_NBR, getVersion(Constants.LOCATIONS));
		Statement select;
		Statement selectInner;
		ResultSet resultOuter;
		ResultSet resultInner;
		JSONArray jArray = new JSONArray();
		JSONArray mainArray = new JSONArray();
		JSONObject temp = new JSONObject();
		int index = 0;
		try {
			select = connection.createStatement();
			resultOuter = select.executeQuery("SELECT id, title FROM landscapes");
			while(resultOuter.next()) {
				index++;
				temp = new JSONObject();
				int mainId = resultOuter.getInt(1);
				String mainTitle = resultOuter.getString(2);
				temp.put(Constants.NAME, mainTitle);
				temp.put(Constants.ID, mainId);
				selectInner = connection.createStatement();
				resultInner = selectInner.executeQuery("SELECT id, title FROM "
						+ "cities WHERE parentId = " + index);
				jArray = new JSONArray();
				while(resultInner.next()) {
					JSONObject inner = new JSONObject();
					inner.put(Constants.ID, resultInner.getInt(1));
					inner.put(Constants.NAME, resultInner.getString(2));
					jArray.add(inner);
				}
				temp.put(Constants.ARRAY_CITY, jArray);
				mainArray.add(temp);
			}
			mainObj.put(Constants.ARRAY_LANDSCAPE, mainArray);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return mainObj.toString();
	}

	public String getVersion(String version) {
		String line = null;
		if(version.equals(Constants.LOCATIONS)) {
			line = "locations";
		} else if(version.equals(Constants.ACTIVITY_CATEGORIES)) {
			line = "categories";
		}
		Statement select;
		ResultSet result;
		try {
			select = connection.createStatement();
			result = select.executeQuery(GET_VERSION_QUERY + "'"+line+"'");
			result.first();
			String currentVersion = result.getString(1);
			System.out.println(currentVersion);
			return currentVersion;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean writeLog(String logType, String message) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(WRITE_LOG_QUERY);
			statement.setString(1,  logType);
			statement.setString(2, message);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int returnActivityID() {
		int id = 0;
		return id;
	}

	//TODO ???
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
		DatabaseManager db = new DatabaseManager(null);
		//db.getActivities("sdg", "kjsdf", 5);
		//db.registerNewUser("GF", "Hemligt", "email.com");
		//db.signUpForActivity("Liza", 3);
		//db.writeLog(2, "TEST FROM SERVERAPPLICATION");
		//db.getCategories();
		//db.addNewActivity("GFGF", 500, 5, 3, 6, "2016-02-12", "10:00:00", "mjhb", "kjh");
		//db.getActivityHeadLines(1);
		//db.getActivitiy(19);
		//db.getOwnedActivitiesHeadlines("dfgh");
		db.getVersion(Constants.ACTIVITY_CATEGORIES);
	}
}