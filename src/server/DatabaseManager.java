package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

	private static final String URL = "jdbc:mysql://195.178.232.7:4040/ad4063";
	private final String DRIVER = "com.mysql.jdbc.Driver";
	private final String USERNAME = "AD4063";
	private final String PASSWORD = "seeyaserver";

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

	public String getActivities(String location, String category) {
		return null;
	}

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

	public boolean checkLogin(String userName, String password) {
		
		return false;
	}

	public boolean registerNewUser(String userName, String passWord, String email) {
		if(connection == null) {   //TODO or connection.isClosed()
			openConnection();
		}
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO "+
										" users VALUES (?, ?, ?");
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

	public boolean addNewActivity(String owner, String location, String category,
			int nbrOfParticipants, String dateTime, String visibility) {
		return false;
	}

	public String getUsers() {
		return null;
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

	private void buildSimpleJson() {
		JSONObject jsonObject = new JSONObject();
	}
}