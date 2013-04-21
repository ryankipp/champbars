package com.example.champbars;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Class: ServerInterface - Provides static methods to abstract the server
 * calls. This makes it easy for calling classes to use these functions without
 * worrying about the details of the server communication.
 * 
 */
public class ServerInterface {

	// Declared Constants
	public static final String SERVER_URL = "http://web.engr.illinois.edu/~kipp1/";

	/**
	 * Gets the list of animals from the server.
	 * 
	 * @return A string containing a comma-delimited list of animals.
	 */
	public static String getAnimalList() {
		/*
		 * Let's construct the query string. It should be a key/value pair. In
		 * this case, we just need to specify the command, so no additional
		 * arguments are needed.
		 */
		String data = "command=" + URLEncoder.encode("getAnimalList");
		return executeHttpRequest(data);
	}

	public static String getBarsList() {
		String data = "command=" + URLEncoder.encode("getBars");
		return executeHttpRequest(data);
	}

	public static String getBarInfo(String bar) {
		/*
		 * Let's construct the query string. We need the command getAnimalSound.
		 * In addition, we need to set the animal value to specify which animal
		 * we're talking about.
		 */
		String data = "command=" + URLEncoder.encode("getBarInfo");
		data += "&bar=" + URLEncoder.encode(bar);
		return executeHttpRequest(data);
	}
	
	//Tells DB on server to increment the number of people at a bar
	public static String barCommit(String bar) {
		String data = "command=" + URLEncoder.encode("commitIncrement");
		data += "&bar=" + URLEncoder.encode(bar);
		return executeHttpRequest(data);
	}
	
	//Tells server to decrement the number of people at a bar
	public static String barUnCommit(String bar) {
		String data = "command=" + URLEncoder.encode("commitDecrement");
		data += "&bar=" + URLEncoder.encode(bar);
		return executeHttpRequest(data);
	}
	
	public static String barCheckIn(String bar) {
		String data = "command=" + URLEncoder.encode("checkInIncrement");
		data += "&bar=" + URLEncoder.encode(bar);
		return executeHttpRequest(data);
	}
	
	public static String barCheckOut(String bar) {
		String data = "command=" + URLEncoder.encode("checkInDecrement");
		data += "&bar=" + URLEncoder.encode(bar);
		return executeHttpRequest(data);
	}

	/**
	 * Gets the sound that the animal makes from the server.
	 * 
	 * @param animal
	 *            String specifying the animal.
	 * @return A string representing the sound an animal makes.
	 */
	public static String getAnimalSound(String animal) {
		/*
		 * Let's construct the query string. We need the command getAnimalSound.
		 * In addition, we need to set the animal value to specify which animal
		 * we're talking about.
		 */
		String data = "command=" + URLEncoder.encode("getAnimalSound");
		data += "&animal=" + URLEncoder.encode(animal);
		return executeHttpRequest(data);
	}

	/**
	 * Helper function used to communicate with the server by sending/receiving
	 * POST commands.
	 * 
	 * @param data
	 *            String representing the command and (possibly) arguments.
	 * @return String response from the server.
	 */
	private static String executeHttpRequest(String data) {
		String result = "";
		try {
			URL url = new URL(SERVER_URL);
			URLConnection connection = url.openConnection();

			/*
			 * We need to make sure we specify that we want to provide input and
			 * get output from this connection. We also want to disable caching,
			 * so that we get the most up-to-date result. And, we need to
			 * specify the correct content type for our data.
			 */
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			// Send the POST data
			DataOutputStream dataOut = new DataOutputStream(
					connection.getOutputStream());
			dataOut.writeBytes(data);
			dataOut.flush();
			dataOut.close();

			// get the response from the server and store it in result
			DataInputStream dataIn = new DataInputStream(
					connection.getInputStream());
			String inputLine;
			while ((inputLine = dataIn.readLine()) != null) {
				result += inputLine;
			}
			dataIn.close();
		} catch (IOException e) {
			/*
			 * In case of an error, we're going to return a null String. This
			 * can be changed to a specific error message format if the client
			 * wants to do some error handling. For our simple app, we're just
			 * going to use the null to communicate a general error in
			 * retrieving the data.
			 */
			e.printStackTrace();
			result = null;
		}

		return result;
	}
}