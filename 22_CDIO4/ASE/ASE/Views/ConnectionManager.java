package ASE.Views;

import ASE.Views.ConnectionReader;
import ASE.Controllers.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.io.IOException;

public class ConnectionManager implements Runnable {

	ConnectionReader connectionReader = new ConnectionReader(null);
	ConnectionManager connectionManager = new ConnectionManager();
	private ArrayList<String> allConnectedIPAdresses = new ArrayList<String>();
	private ArrayList<Integer> allConnectedPortNumbers = new ArrayList<Integer>();
	WeightController[] weightController;
	MeasurementController measurementController;
	Socket weightSocket;

	/**
	 * Method which tries to establish a connection to all of the listed weights
	 * in the WeightTable.txt file, while adding each successfull IP/Port number
	 * to an array.
	 */
	@Override
	public void run() {
		try {
			for (int i = 0; i < connectionReader.getAllIPAdresses().size(); i++) {
				weightSocket = new Socket(connectionReader.getIPString(i), connectionReader.getPortInt(i));
				// Add the newly connected IP/Socket to a list of successfully
				// connected weights.
				allConnectedIPAdresses.add(connectionReader.getIPString(i));
				allConnectedPortNumbers.add(connectionReader.getPortInt(i));
				System.out.println("Connection established.");

			}
		} catch (UnknownHostException e) {
			System.out.println("Error occured: Host unknown " + e);
		} catch (IOException e) {
			System.out.println("Error occured: Port number unknown " + e);
		}
	}

	/**
	 * Starts each individual thread for each socket connection established.
	 */
	public void threadStarter() {
		for (int i = 0; i < connectionReader.getAllIPAdresses().size(); i++) {
			try {
				weightSocket = new Socket(connectionReader.getIPString(i), connectionReader.getPortInt(i));
			} catch (IOException e) {
				System.out.println("Connection failed: " + e);
			}

			try {
				weightController[i] = new WeightController(measurementController, weightSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			(new Thread(weightController[i])).start();
		}
	}

	/**
	 * Method to return all of the connected IP adresses in an Array.
	 * 
	 * @return Returns an array of all the connected IP adresses.
	 */
	public ArrayList<String> getAllConnectedIPAdresses() {
		return allConnectedIPAdresses;
	}

	/**
	 * Method to return all of the connected Port Numbers in an Array.
	 * 
	 * @return Returns an array of all the connected Port numbers.
	 */
	public ArrayList<Integer> getAllConnectedPortNumbers() {
		return allConnectedPortNumbers;
	}

	/**
	 * Method to quickly get the number of connected weights
	 * 
	 * @return Returns the number of connected weights (defined by the number of
	 *         connected IP's), as an Integer.
	 */
	public int getNumberOfConnectedIP() {
		return connectionManager.allConnectedIPAdresses.size();
	}
}
