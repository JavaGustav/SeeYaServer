package TestClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import server.Constants;

public class TClient2 {
	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	// Data to be used during test
	private String[][] usersToCreateForS1 = {{"SubTest2_user1", "passwordUser1", "emailUser1@testmail.te"}, 
											{"SubTest2_user2", "passwordUser2", "emailUser2@testmail.te"}, 
											{"SubTest2_user3", "passwordUser3", "emailUser3@testmail.te"}, 
											{"SubTest2_user4", "passwordUser4", "emailUser4@testmail.te"},
											{"SubTest2_user5", "passwordUser5", "emailUser5@testmail.te"}, 
											{"SubTest2_user6", "passwordUser6", "emailUser6@testmail.te"}};
	
	private String[][] usersToLogInForS2 = {{"SubTest2_user1", "passwordUser1"}, 
											{"SubTest2_user2", "passwordUser2"}, 
											{"SubTest2_user3", "passwordUser3"}, 
											{"SubTest2_user4", "passwordUser4"}};
	
	private String[][] usersToCreateForS3 = {{"SubTest2_user1", "passwordUser1", "emailUser1@testmail.te"}, 
											{"SubTest2_user2", "passwordUser2", "emailUser2@testmail.te"}, 
											{"SubTest2_user3", "passwordUser3", "emailUser3@testmail.te"}, 
											{"SubTest2_user4", "passwordUser4", "emailUser4@testmail.te"},
											{"SubTest2_user5", "passwordUser5", "emailUser5@testmail.te"}, 
											{"SubTest2_user6", "passwordUser6", "emailUser6@testmail.te"}};
	private String[][] usersToLogInForS4 = {{"SubTest2_user7", "passwordUser7", "emailUser7@testmail.te"}, 
											{"SubTest2_user8", "passwordUser8", "emailUser8@testmail.te"}};
	
	private String[][] usersToLogInForS5 = {{"SubTest2_user7", "wrongPasswordUser7", "emailUser7@testmail.te"}, 
											{"SubTest2_user8", "wrongPasswordUser8", "emailUser8@testmail.te"}};
	
	private String[] usersToCheckForS6 = {"SubTest2_user1", "SubTest2_user2", "SubTest2_user3", "SubTest2_user4",
											"SubTest2_user5", "SubTest2_user6"};
	
	private String[] usersToCheckForS7 = {"SubTest2_user7", "SubTest2_user8"};
	private String categoriesVersionNbrUpToDateForS8 = "2016-04-25 10:27:34";
	private String categoriesVersionNbrNotUpToDateForS9 = "2016-04-20 10:27:34";
	private String locationsVersionNbrUpToDateForS10 = "2016-04-25 10:27:45";
	private String locationsVersionNbrNotUpToDateForS11 = "2016-04-24 10:27:45";
	private String[][] newActivitesForS12 = {{"SubTest2_user1", "505", "202", "4", "2", "2016-05-11", "10:00:00",
												"Welcome to play!", "Badminton at wednesday morning!"},
											{"SubTest2_user1", "505", "202", "4", "2", "2016-05-11", "18:00:00",
												"Welcome to play!", "Badminton at wednesday evening!"},
											{"SubTest2_user1", "505", "202", "4", "2", "2016-05-11", "20:00:00",
												"Welcome to play!", "Badminton at wednesday evening!"},
											{"SubTest2_user1", "504", "203", "100", "2", "2016-05-12", "19:00:00",
												"Let´s run!", "Running at thursday evening!"},
											{"SubTest2_user2", "505", "202", "4", "2", "2016-05-11", "18:00:00",
												"Welcome to play!", "Badminton at wednesday evening!"},
											{"SubTest2_user2", "502", "200", "15", "2", "2016-05-13", "08:30:00",
												"Welcome to read!", "Book club at friday!"},
											{"SubTest2_user3", "501", "202", "4", "2", "2016-05-11", "18:00:00",
												"Welcome to play!", "Badminton at wednesday evening!"},
											{"SubTest2_user4", "501", "202", "8", "4", "2016-06-01", "15:00:00",
												"Let's play!", "Badminton two teams"}
											};
	
	private String[] usersForS13 = {"SubTest2_user1", "SubTest2_user2", "SubTest2_user3", "SubTest2_user4"};
	private HashMap userAndActivities1 = new HashMap();
	private String[] usersForS14 = {"SubTest2_user1", "SubTest2_user2"};
	private String[] usersFromForS15 = {"SubTest2_user2", "SubTest2_user3"};
	private String[][] usersToForS15 = {{"SubTest2_user3"}, 
										{"SubTest2_user4", "SubTest2_user5", "SubTest2_user6"}};
	
	private String[] usersForS16 = {"SubTest2_user1", "SubTest2_user2", "SubTest2_user3", "SubTest2_user4", "SubTest2_user5", "SubTest2_user6"};

	private HashMap userAndActivities2 = new HashMap();
	
	// Contains users as key and a JSONArray with the user's HeadLines JSONObjects as value.
	// One entry for every user.
	private HashMap userAndActivities3 = new HashMap();
	
	private String[] usersForS19 = {"SubTest2_user1", "SubTest2_user2"};
	
	public TClient2(){
		try{
			socket = new Socket("127.0.0.1",7500);
			dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));	
			dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e){
			System.out.println("Error initializing");
		}
		
	}
	
	
	public void performTest(){
		
		testS1(usersToCreateForS1);
		testS2(usersToLogInForS2);
		testS3(usersToCreateForS3);
		testS4(usersToLogInForS4);
		testS5(usersToLogInForS5);
		testS6(usersToCheckForS6);
		testS7(usersToCheckForS7);
		testS8(categoriesVersionNbrUpToDateForS8);
		testS9(categoriesVersionNbrNotUpToDateForS9);
		testS10(locationsVersionNbrUpToDateForS10);
		testS11(locationsVersionNbrNotUpToDateForS11);
		testS12(newActivitesForS12);
		testS13(usersForS13, userAndActivities1);
		testS14(userAndActivities1, usersForS14);
		testS15(userAndActivities1, usersFromForS15, usersToForS15);
		testS16(usersForS16, userAndActivities2);
		testS17(usersForS16, userAndActivities1, userAndActivities3);
		testS18(usersForS19, userAndActivities3);
		
	}
	
	/**
	 * S1, Create new user, not existing users.
	 * @param users
	 */
	private void testS1(String[][] users){
		
		for(int i=0; i<users.length; i++){
			JSONObject jsonSendObject = new JSONObject();

			jsonSendObject.put(Constants.TYPE, Constants.NEWUSER);
			jsonSendObject.put(Constants.USERNAME, users[i][0]);
			jsonSendObject.put(Constants.PASSWORD, users[i][1]);
			jsonSendObject.put(Constants.EMAIL, users[i][2]);

			System.out.println("TClient2" + "\nTestID: S1 \n" + "Sent:" + jsonSendObject.toString() + "\n");

			try{
				dos.writeUTF(jsonSendObject.toString());
				dos.flush();
			} catch (IOException e){
				System.out.println("Could not write");
			}
			
			try{
				System.out.println("Received: \n" + dis.readUTF() + "\n");
			}catch (IOException e){
				System.out.println("Could not read");
			}
		}
	}
	
	/**
	 * S2, Log in, existing users.
	 * @param users
	 */
	private void testS2(String[][] users){
		for(int i=0; i<users.length; i++){
			JSONObject jsonSendObject = new JSONObject();

			jsonSendObject.put(Constants.TYPE, Constants.LOGIN);
			jsonSendObject.put(Constants.USERNAME, users[i][0]);
			jsonSendObject.put(Constants.PASSWORD, users[i][1]);

			System.out.println("TClient2" + "\nTestID: S2 \n" + "Sent:" + jsonSendObject.toString() + "\n");

			try{
				dos.writeUTF(jsonSendObject.toString());
				dos.flush();
			} catch (IOException e){
				System.out.println("Could not write");
			}
			
			try{
				System.out.println("Received: \n" + dis.readUTF() + "\n");
			}catch (IOException e){
				System.out.println("Could not read");
			}
		}
	}
	
	/**
	 * S3, Register new user with invalid username. Usernam allready exist in database.
	 * @param users
	 */
	private void testS3(String[][] users){
		for(int i=0; i<users.length; i++){
			JSONObject jsonSendObject = new JSONObject();

			jsonSendObject.put(Constants.TYPE, Constants.NEWUSER);
			jsonSendObject.put(Constants.USERNAME, users[i][0]);
			jsonSendObject.put(Constants.PASSWORD, users[i][1]);
			jsonSendObject.put(Constants.EMAIL, users[i][2]);

			System.out.println("TClient3" + "\nTestID: S3 \n" + "Sent:" + jsonSendObject.toString() + "\n");

			try{
				dos.writeUTF(jsonSendObject.toString());
				dos.flush();
			} catch (IOException e){
				System.out.println("Could not write");
			}
			
			try{
				System.out.println("Received: \n" + dis.readUTF() + "\n");
			}catch (IOException e){
				System.out.println("Could not read");
			}
		}
	}

	/**
	 * S4, Log in, invalid user name.
	 * @param users
	 */
	private void testS4(String[][] users){
		for(int i=0; i<users.length; i++){
			JSONObject jsonSendObject = new JSONObject();

			jsonSendObject.put(Constants.TYPE, Constants.LOGIN);
			jsonSendObject.put(Constants.USERNAME, users[i][0]);
			jsonSendObject.put(Constants.PASSWORD, users[i][1]);

			System.out.println("TClient2" + "\nTestID: S4 \n" + "Sent:" + jsonSendObject.toString() + "\n");

			try{
				dos.writeUTF(jsonSendObject.toString());
				dos.flush();
			} catch (IOException e){
				System.out.println("Could not write");
			}
			
			try{
				System.out.println("Received: \n" + dis.readUTF() + "\n");
			}catch (IOException e){
				System.out.println("Could not read");
			}
		}
	}

	/**
	 * S5, Log in, invalid password.
	 * @param users
	 */
	private void testS5(String[][] users){
		for(int i=0; i<users.length; i++){
			JSONObject jsonSendObject = new JSONObject();

			jsonSendObject.put(Constants.TYPE, Constants.LOGIN);
			jsonSendObject.put(Constants.USERNAME, users[i][0]);
			jsonSendObject.put(Constants.PASSWORD, users[i][1]);

			System.out.println("TClient2" + "\nTestID: S5 \n" + "Sent:" + jsonSendObject.toString() + "\n");

			try{
				dos.writeUTF(jsonSendObject.toString());
				dos.flush();
			} catch (IOException e){
				System.out.println("Could not write");
			}
			
			try{
				System.out.println("Received: \n" + dis.readUTF() + "\n");
			}catch (IOException e){
				System.out.println("Could not read");
			}
		}
	}

	/**
	 * S6, Check if user exists, existing users.
	 * @param users
	 */
	private void testS6(String[] users){
		for(int i=0; i<users.length; i++){
			JSONObject jsonSendObject = new JSONObject();

			jsonSendObject.put(Constants.TYPE, Constants.CHECK_IF_USER_EXISTS);
			jsonSendObject.put(Constants.USERNAME, users[i]);

			System.out.println("TClient2" + "\nTestID: S6 \n" + "Sent:" + jsonSendObject.toString() + "\n");

			try{
				dos.writeUTF(jsonSendObject.toString());
				dos.flush();
			} catch (IOException e){
				System.out.println("Could not write");
			}
			
			try{
				System.out.println("Received: \n" + dis.readUTF() + "\n");
			}catch (IOException e){
				System.out.println("Could not read");
			}
		}
	}


	/**
	 * S7, Check if user exists, non existing users.
	 * @param users
	 */
	private void testS7(String[] users){
		for(int i=0; i<users.length; i++){
			JSONObject jsonSendObject = new JSONObject();

			jsonSendObject.put(Constants.TYPE, Constants.CHECK_IF_USER_EXISTS);
			jsonSendObject.put(Constants.USERNAME, users[i]);

			System.out.println("TClient2" + "\nTestID: S7 \n" + "Sent:" + jsonSendObject.toString() + "\n");

			try{
				dos.writeUTF(jsonSendObject.toString());
				dos.flush();
			} catch (IOException e){
				System.out.println("Could not write");
			}
			
			try{
				System.out.println("Received: \n" + dis.readUTF() + "\n");
			}catch (IOException e){
				System.out.println("Could not read");
			}
		}
	}

	/**
	 * S8, Get main categories, record updated.
	 * @param users
	 */
	private void testS8(String categoriesVersionNbrUpToDate){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.CATEGORIES_VERSION_NUMBER);
		jsonSendObject.put(Constants.ID, categoriesVersionNbrUpToDate);

		System.out.println("TClient2" + "\nTestID: S8 \n" + "Sent:" + jsonSendObject.toString() + "\n");

		try{
			dos.writeUTF(jsonSendObject.toString());
			dos.flush();
		} catch (IOException e){
			System.out.println("Could not write");
		}
		
		try{
			System.out.println("Received: \n" + dis.readUTF() + "\n");
		}catch (IOException e){
			System.out.println("Could not read");
		}
		
	}
	
	/**
	 * S9, Get main categories, record not up to date.
	 * @param users
	 */
	private void testS9(String categoriesVersionNbrNotUpToDateForS9){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.CATEGORIES_VERSION_NUMBER);
		jsonSendObject.put(Constants.ID, categoriesVersionNbrNotUpToDateForS9);

		System.out.println("TClient2" + "\nTestID: S9 \n" + "Sent:" + jsonSendObject.toString() + "\n");

		try{
			dos.writeUTF(jsonSendObject.toString());
			dos.flush();
		} catch (IOException e){
			System.out.println("Could not write");
		}
		
		try{
			System.out.println("Received: \n" + dis.readUTF() + "\n");
		}catch (IOException e){
			System.out.println("Could not read");
		}
		
	}

	/**
	 * S10, Get locations, record up to date.
	 * @param users
	 */
	private void testS10(String locationsVersionNbrUpToDateForS10){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.LOCATIONS_VERSION_NBR);
		jsonSendObject.put(Constants.ID, locationsVersionNbrUpToDateForS10);

		System.out.println("TClient2" + "\nTestID: S10 \n" + "Sent:" + jsonSendObject.toString() + "\n");

		try{
			dos.writeUTF(jsonSendObject.toString());
			dos.flush();
		} catch (IOException e){
			System.out.println("Could not write");
		}
		
		try{
			System.out.println("Received: \n" + dis.readUTF() + "\n");
		}catch (IOException e){
			System.out.println("Could not read");
		}
		
	}

	/**
	 * S11, Get locations, record not up to date.
	 * @param users
	 */
	private void testS11(String locationsVersionNbrNotUpToDateForS11){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.LOCATIONS_VERSION_NBR);
		jsonSendObject.put(Constants.ID, locationsVersionNbrNotUpToDateForS11);

		System.out.println("TClient2" + "\nTestID: S11 \n" + "Sent:" + jsonSendObject.toString() + "\n");

		try{
			dos.writeUTF(jsonSendObject.toString());
			dos.flush();
		} catch (IOException e){
			System.out.println("Could not write");
		}
		
		try{
			System.out.println("Received: \n" + dis.readUTF() + "\n");
		}catch (IOException e){
			System.out.println("Could not read");
		}
		
	}

	/**
	 * S12, Add new activity.
	 * @param users
	 */
	private void testS12(String[][] newActivitiesForS12){
		JSONObject jsonSendObject = new JSONObject();
		for(int i=0; i<newActivitiesForS12.length; i++){
			jsonSendObject.put(Constants.TYPE, Constants.NEWACTIVITY);
			jsonSendObject.put(Constants.NAME, newActivitiesForS12[i][0]);
			jsonSendObject.put(Constants.SUBCATEGORY, newActivitiesForS12[i][1]);
			jsonSendObject.put(Constants.MAX_NBROF_PARTICIPANTS, newActivitiesForS12[i][2]);
			jsonSendObject.put(Constants.MIN_NBR_OF_PARTICIPANTS, newActivitiesForS12[i][3]);
			jsonSendObject.put(Constants.DATE, newActivitiesForS12[i][4]);
			jsonSendObject.put(Constants.TIME, newActivitiesForS12[i][5]);
			jsonSendObject.put(Constants.MESSAGE, newActivitiesForS12[i][6]);
			jsonSendObject.put(Constants.HEADLINE, newActivitiesForS12[i][7]);

			System.out.println("TClient2" + "\nTestID: S12 \n" + "Sent:" + jsonSendObject.toString() + "\n");

			try{
				dos.writeUTF(jsonSendObject.toString());
				dos.flush();
			} catch (IOException e){
				System.out.println("Could not write");
			}
			
			try{
				System.out.println("Received: \n" + dis.readUTF() + "\n");
			}catch (IOException e){
				System.out.println("Could not read");
			}
			
		}
		
	}

	/**
	 * S13, Get ownd activities.
	 * @param users
	 */
	private void testS13(String[] usersForS13, HashMap userAndActivities1){
		JSONObject jsonSendObject = new JSONObject();
		JSONObject jsonRecieved;
		JSONParser jsonParser = new JSONParser();
		String recieved = null;
		for(int i=0; i<usersForS13.length; i++){
			jsonSendObject.put(Constants.TYPE, Constants.GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER_OWND_ACTIVITIES);
			jsonSendObject.put(Constants.USERNAME, usersForS13[i]);

			System.out.println("TClient2" + "\nTestID: S13 \n" + "Sent:" + jsonSendObject.toString() + "\n");

			try{
				dos.writeUTF(jsonSendObject.toString());
				dos.flush();
			} catch (IOException e){
				System.out.println("Could not write");
			}
			
			try{
				recieved = dis.readUTF();
				jsonRecieved = (JSONObject) jsonParser.parse(recieved);
				
				//Lägger in username och JSONObject (innehållande hela strukturen) i hashmap:en userAndActivities.
				userAndActivities1.put(usersForS13[i], jsonRecieved);
				System.out.println("Received: \n" + recieved + "\n");
			}catch (IOException e){
				System.out.println("Could not read");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	

	/**
	 * S14, Publish activities to all users. 
	 * @param users
	 */
	private void testS14(HashMap userAndActivities, String[] users){
		JSONObject jsonSendObject = new JSONObject();
		JSONObject jsonObjectMain;
		
		JSONArray jsonArrayMainCategories;
		JSONObject jsonObjectMainCategory;
		
		JSONArray jsonArraySubCategories;
		JSONObject jsonObjectSubCategory;
		
		JSONArray jsonHeadlinesArray;
		JSONObject jsonObjectHeadline;
		
		// For each user in the user array, get the associated json-object.
		for(int i=0; i<users.length; i++){
			jsonObjectMain = (JSONObject) userAndActivities.get(users[i]);
			jsonArrayMainCategories = (JSONArray) jsonObjectMain.get(Constants.ARRAY_MAINCATEGORY);
			
			// For every main category json-object in the main categories array (for that user), get the sub categories array
			// and from that, get the sub category object.
			for(int j=0; j<jsonArrayMainCategories.size(); j++){
				jsonObjectMainCategory = (JSONObject) jsonArrayMainCategories.get(j);
				jsonArraySubCategories = (JSONArray)jsonObjectMainCategory.get(Constants.ARRAY_SUBCATEGORY);
				
				// For every sub category json-object in the sub categories array (for that user), get the headlines array
				// and from that, get the headline object.
				for(int k=0; k<jsonArraySubCategories.size(); k++){
					jsonObjectSubCategory = (JSONObject)jsonArrayMainCategories.get(k);
					jsonHeadlinesArray = (JSONArray) jsonObjectSubCategory.get(Constants.ARRAY_HEADLINE);
					
					// For every headline json object in the headlines array (for that user), get the headlines ID
					// and publish it.
					for(int l=0; l<jsonHeadlinesArray.size(); l++){
						jsonObjectHeadline = (JSONObject) jsonHeadlinesArray.get(l);
						
						jsonSendObject.put(Constants.TYPE, Constants.PUBLISH_ACTIVITY);
						jsonSendObject.put(Constants.ID, jsonObjectHeadline.get(Constants.ID));

						System.out.println("TClient2" + "\nTestID: S14 \n" + "Sent:" + jsonSendObject.toString() + "\n");

						try{
							dos.writeUTF(jsonSendObject.toString());
							dos.flush();
						} catch (IOException e){
							System.out.println("Could not write");
						}
						
						try{
							System.out.println("Received: \n" + dis.readUTF() + "\n");
						}catch (IOException e){
							System.out.println("Could not read");
						}
					}
				}
			}	
		}	
	}

	/**
	 * S15, Publish activities to individual users. 
	 * @param users
	 */
	private void testS15(HashMap userAndActivities, String[] usersFrom, String[][] usersTo){
		JSONObject jsonSendObject = new JSONObject();
		JSONObject jsonObjectMain;
		
		JSONArray jsonArrayMainCategories;
		JSONObject jsonObjectMainCategory;
		
		JSONArray jsonArraySubCategories;
		JSONObject jsonObjectSubCategory;
		
		JSONArray jsonHeadlinesArray;
		JSONObject jsonObjectHeadline;
		
		// For each user in the user array, get the associated json-object.
		for(int i=0; i<usersFrom.length; i++){
			jsonObjectMain = (JSONObject) userAndActivities.get(usersFrom[i]);
			jsonArrayMainCategories = (JSONArray) jsonObjectMain.get(Constants.ARRAY_MAINCATEGORY);
			
			// For every main category json-object in the main categories array (for that user), get the sub categories array
			// and from that, get the sub category object.
			for(int j=0; j<jsonArrayMainCategories.size(); j++){
				jsonObjectMainCategory = (JSONObject) jsonArrayMainCategories.get(j);
				jsonArraySubCategories = (JSONArray)jsonObjectMainCategory.get(Constants.ARRAY_SUBCATEGORY);
				
				// For every sub category json-object in the sub categories array (for that user), get the headlines array
				// and from that, get the headline object.
				for(int k=0; k<jsonArraySubCategories.size(); k++){
					jsonObjectSubCategory = (JSONObject)jsonArrayMainCategories.get(k);
					jsonHeadlinesArray = (JSONArray) jsonObjectSubCategory.get(Constants.ARRAY_HEADLINE);
					
					// For every headline json object in the headlines array (for that user), get the headlines ID
					// and publish it.
					for(int l=0; l<jsonHeadlinesArray.size(); l++){
						jsonObjectHeadline = (JSONObject) jsonHeadlinesArray.get(l);
						
						jsonSendObject.put(Constants.TYPE, Constants.PUBLISH_ACTIVITY_TO_SPECIFIC_USERS);
						jsonSendObject.put(Constants.ID, jsonObjectHeadline.get(Constants.ID));
						JSONArray jsonArrayPublishToUsers = new JSONArray();
						for(int m=0; m<usersTo[i].length; m++){
							JSONObject jsonObjectUserTo = new JSONObject();
							jsonObjectUserTo.put(Constants.USERNAME, usersTo[i][m]);
							jsonArrayPublishToUsers.add(jsonObjectUserTo);
						}
						jsonSendObject.put(Constants.ARRAY_USERNAME, jsonArrayPublishToUsers);

						System.out.println("TClient2" + "\nTestID: S14 \n" + "Sent:" + jsonSendObject.toString() + "\n");

						try{
							dos.writeUTF(jsonSendObject.toString());
							dos.flush();
						} catch (IOException e){
							System.out.println("Could not write");
						}
						
						try{
							System.out.println("Received: \n" + dis.readUTF() + "\n");
						}catch (IOException e){
							System.out.println("Could not read");
						}
					}
				}
			}	
		}	
	}

	/**
	 * S16, Get activity headlines for activities which user is invited to.
	 * @param users
	 */
	private void testS16(String[] users, HashMap userAndActivities2){
		JSONObject jsonSendObject = new JSONObject();
		JSONObject jsonRecieved;
		JSONParser jsonParser = new JSONParser();
		String recieved = null;
		for(int i=0; i<users.length; i++){
			jsonSendObject.put(Constants.TYPE, Constants.GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER);
			jsonSendObject.put(Constants.USERNAME, users[i]);

			System.out.println("TClient2" + "\nTestID: S16 \n" + "Sent:" + jsonSendObject.toString() + "\n");

			try{
				dos.writeUTF(jsonSendObject.toString());
				dos.flush();
			} catch (IOException e){
				System.out.println("Could not write");
			}
			
			try{
				recieved = dis.readUTF();
				jsonRecieved = (JSONObject) jsonParser.parse(recieved);
				
				//Lägger in username och JSONObject (innehållande hela strukturen) i hashmap:en userAndActivities2.
				userAndActivities2.put(users[i], jsonRecieved);
				System.out.println("Received: \n" + recieved + "\n");
			}catch (IOException e){
				System.out.println("Could not read");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}


	/**
	 * S17, Get activities. Get and prints the activities, for each user
	 * @param users
	 */
	private void testS17(String[] users, HashMap userAndActivities2, HashMap userAndActivities3){
		JSONObject jsonSendObject = new JSONObject();
		JSONObject jsonObjectMain;
		
		JSONArray jsonArrayMainCategories;
		JSONObject jsonObjectMainCategory;
		
		JSONArray jsonArraySubCategories;
		JSONObject jsonObjectSubCategory;
		
		JSONArray jsonHeadlinesArray;
		JSONObject jsonObjectHeadline;
		JSONArray jsonHeadlinesArrayForUser;
		
		// For each user in the user array, get the associated json-object.
		for(int i=0; i<users.length; i++){
			jsonObjectMain = (JSONObject) userAndActivities2.get(users[i]);
			jsonArrayMainCategories = (JSONArray) jsonObjectMain.get(Constants.ARRAY_MAINCATEGORY);
			jsonHeadlinesArrayForUser = new JSONArray();
			
			// For every main category json-object in the main categories array (for that user), get the sub categories array
			// and from that, get the sub category object.
			for(int j=0; j<jsonArrayMainCategories.size(); j++){
				jsonObjectMainCategory = (JSONObject) jsonArrayMainCategories.get(j);
				jsonArraySubCategories = (JSONArray)jsonObjectMainCategory.get(Constants.ARRAY_SUBCATEGORY);
				
				// For every sub category json-object in the sub categories array (for that user), get the headlines array
				// and from that, get the headline object.
				for(int k=0; k<jsonArraySubCategories.size(); k++){
					jsonObjectSubCategory = (JSONObject)jsonArrayMainCategories.get(k);
					jsonHeadlinesArray = (JSONArray) jsonObjectSubCategory.get(Constants.ARRAY_HEADLINE);
					
					// Sparar username som key och 
					userAndActivities3.put(users[i], jsonHeadlinesArray);

					// For every headline json object in the headlines array (for that user), get the headlines ID
					// and print it.
					for(int l=0; l<jsonHeadlinesArray.size(); l++){
						jsonObjectHeadline = (JSONObject) jsonHeadlinesArray.get(l);
						jsonHeadlinesArrayForUser.add(jsonObjectHeadline);
						
						jsonSendObject.put(Constants.TYPE, Constants.ACTIVITIY);
						jsonSendObject.put(Constants.ID, jsonObjectHeadline.get(Constants.ID));

						System.out.println("TClient2" + "\nTestID: S17 \n" + "Sent:" + jsonSendObject.toString() + "\n");

						try{
							dos.writeUTF(jsonSendObject.toString());
							dos.flush();
						} catch (IOException e){
							System.out.println("Could not write");
						}
						
						try{
							System.out.println("Received: \n" + dis.readUTF() + "\n");
						}catch (IOException e){
							System.out.println("Could not read");
						}
					}
				}
				// Lägger in aktuell user som key och en JSONArray innehållande JSONObject för samtliga aktiviteter
				// för den användaren, som value, i HashMap:en.
				userAndActivities3.put(users[i], jsonHeadlinesArrayForUser);
			}	
		}			
	}

	/**
	 * S18, Sign up for activities. Selected users sign up for all its activities.
	 * @param users
	 */
	private void testS18(String[] users, HashMap userAndActivities3){
		JSONArray activities;
		for(int i=0; i<users.length; i++){
			activities = (JSONArray) userAndActivities3.get(users[i]);
			for(int j=0; j<activities.size(); j++){
				JSONObject jsonSendObject = (JSONObject) activities.get(j);
				jsonSendObject.put(Constants.TYPE, Constants.SIGNUP);
				System.out.println("TClient2" + "\nTestID: S18 \n" + "Sent:" + jsonSendObject.toString() + "\n");

				try{
					dos.writeUTF(jsonSendObject.toString());
					dos.flush();
				} catch (IOException e){
					System.out.println("Could not write");
				}
				
				try{
					System.out.println("Received: \n" + dis.readUTF() + "\n");
				}catch (IOException e){
					System.out.println("Could not read");
				}
				
			}
		}
	}

	/**
	 * S19, Unsign from activities. Selected users unsign from all its activities.
	 * @param users
	 */
	private void testS19(String[] users, HashMap userAndActivities3){
		JSONArray activities;
		for(int i=0; i<users.length; i++){
			activities = (JSONArray) userAndActivities3.get(users[i]);
			for(int j=0; j<activities.size(); j++){
				JSONObject jsonSendObject = (JSONObject) activities.get(j);
				jsonSendObject.put(Constants.TYPE, Constants.SIGNUP);
				System.out.println("TClient2" + "\nTestID: S18 \n" + "Sent:" + jsonSendObject.toString() + "\n");

				try{
					dos.writeUTF(jsonSendObject.toString());
					dos.flush();
				} catch (IOException e){
					System.out.println("Could not write");
				}
				
				try{
					System.out.println("Received: \n" + dis.readUTF() + "\n");
				}catch (IOException e){
					System.out.println("Could not read");
				}
				
			}
		}
	}

	
	//	private void test1(){
//		JSONObject jsonSendObject = new JSONObject();
//
//		jsonSendObject.put(Constants.TYPE, Constants.GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER);
//		jsonSendObject.put(Constants.USERNAME, "Test");
//
//		System.out.println("TClient" + "\nTestID: S1 \n" + "Sent:" + jsonSendObject.toString() + "\n");
//
//		try{
//			dos.writeUTF(jsonSendObject.toString());
//			dos.flush();
//		} catch (IOException e){
//			System.out.println("Could not write");
//		}
//		
//		try{
//			System.out.println("Received: \n" + dis.readUTF() + "\n");
//		}catch (IOException e){
//			System.out.println("Could not read");
//		}
//	}
//
//	private void test2(){
//		JSONObject jsonSendObject = new JSONObject();
//
//		jsonSendObject.put(Constants.TYPE, Constants.GET_MAINCATEGORY_SUBCATEGORY_HEADLINES_FOR_USER_OWND_ACTIVITIES);
//		jsonSendObject.put(Constants.USERNAME, "Test");
//
//		System.out.println("TClient" + "\nTestID: S2 \n" + "Sent:" + jsonSendObject.toString() + "\n");
//
//		try{
//			dos.writeUTF(jsonSendObject.toString());
//			dos.flush();
//		} catch (IOException e){
//			System.out.println("Could not write");
//		}
//		
//		try{
//			System.out.println("Received: \n" + dis.readUTF() + "\n");
//		}catch (IOException e){
//			System.out.println("Could not read");
//		}
//	}
	
	public static void main(String[] args){
		TClient2 tc2 = new TClient2();
		tc2.performTest();
	}

}
	

	
	
