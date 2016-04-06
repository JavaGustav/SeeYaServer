package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * SeeYa
 * @author Gustav Frigren
 *
 */
public class DatabaseManager {
	
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
	
	public void getPassWord(String userName) {
		
	}

	public boolean checkLogin(String userName, String password) {
		return false;
	}

	public boolean registerNewUser(String userName, String password, String email) {
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
}