package TestClient;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.json.simple.JSONObject;

import server.Constants;

public class TClient1 {

	private Socket socket;
	private DataInputStream dis;
	private DataOutputStream dos;
	
	public TClient1(){
		try{
			socket = new Socket("127.0.0.1",7500);
			dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));	
			dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		} catch (IOException e){
			System.out.println("Error initializing");
		}
	}
	
	public void performTest(){
		
		test1();
		test2();
		test3();
		test4();
		test5();
		test6();
		test7();
	}
	
	private void test1(){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.NEWUSER);
		jsonSendObject.put(Constants.USERNAME, "Karl-Ebbe");
		jsonSendObject.put(Constants.PASSWORD, "123");
		jsonSendObject.put(Constants.EMAIL, "karlebbej@gmail.com");

		System.out.println("TClient" + "\nTestID: S1 \n" + "Sent:" + jsonSendObject.toString() + "\n");

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
	
	private void test2(){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.LOGIN);
		jsonSendObject.put(Constants.USERNAME, "Karl-Ebbe");
		jsonSendObject.put(Constants.PASSWORD, "123");

		System.out.println("TClient" + "\nTestID: S2 \n" + "Sent:" + jsonSendObject.toString() + "\n");

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
	
	private void test3(){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.NEWACTIVITY);
		jsonSendObject.put(Constants.NAME, "Karl-Ebbe");
		jsonSendObject.put(Constants.PLACE, "Lund");
		jsonSendObject.put(Constants.SUBCATEGORY, 21);
		jsonSendObject.put(Constants.MAX_NBROF_PARTICIPANTS, 12);
		jsonSendObject.put(Constants.MIN_NBR_OF_PARTICIPANTS, 4);
		jsonSendObject.put(Constants.DATE, "2016-04-20");
		jsonSendObject.put(Constants.TIME, "10:00:00");
		jsonSendObject.put(Constants.MESSAGE, "Welcome and play!");
		jsonSendObject.put(Constants.HEADLINE, "Badminton!!!");
		

		System.out.println("TClient" + "\nTestID: S3 \n" + "Sent:" + jsonSendObject.toString() + "\n");

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

	private void test4(){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.PUBLISH_ACTIVITY);
		jsonSendObject.put(Constants.ID, 2);

		System.out.println("TClient" + "\nTestID: S4 \n" + "Sent:" + jsonSendObject.toString() + "\n");

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

	private void test5(){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.LOGIN);
		jsonSendObject.put(Constants.USERNAME, "Inge_Användare");
		jsonSendObject.put(Constants.PASSWORD, "123");

		System.out.println("TClient" + "\nTestID: S5 \n" + "Sent:" + jsonSendObject.toString() + "\n");

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
	
	private void test6(){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.LOGIN);
		jsonSendObject.put(Constants.USERNAME, "Karl-Ebbe");
		jsonSendObject.put(Constants.PASSWORD, "000");

		System.out.println("TClient" + "\nTestID: S6 \n" + "Sent:" + jsonSendObject.toString() + "\n");

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
	
	private void test7(){
		JSONObject jsonSendObject = new JSONObject();

		jsonSendObject.put(Constants.TYPE, Constants.NEWUSER);
		jsonSendObject.put(Constants.USERNAME, "Karl-Ebbe");
		jsonSendObject.put(Constants.PASSWORD, "123");
		jsonSendObject.put(Constants.EMAIL, "karlebbej@gmail.com");

		System.out.println("TClient" + "\nTestID: S7 \n" + "Sent:" + jsonSendObject.toString() + "\n");

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
	
	public static void main(String[] args){
		TClient1 tc1 = new TClient1();
		tc1.performTest();
	}
}
