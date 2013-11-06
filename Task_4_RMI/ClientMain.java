import java.net.MalformedURLException;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Main client class with the runnable main methode.
 * @author Dennis Haegler - s0532338
 */
public class ClientMain {
	private ClientMain() {
	}

	/**
	 * Runs the client and communicate over RMI with the server on a localhost
	 * 
	 * @param args parameter given on starting the main.
	 * 
	 * @throws RemoteException if communication to server failed.
	 * @throws NotBoundException if the registry will be not a found.
	 */
	public static void main(String[] args) throws RemoteException, NotBoundException {
		String host = "127.0.0.1";
		String carData = args[0];
		Registry registry = LocateRegistry.getRegistry(host);
		System.out.println("Located the registry from server.");
		CarParkInterface serverStub = (CarParkInterface) registry.lookup("CarPark");
		System.out.println("Located the Stub from server.");
		CarImp car = new CarImp(getTag(carData), getArrivaleTime(carData), getParkingTime(carData), serverStub);
		System.out.println("Created a car.");
		car.startCarSimulation();
	}
	
	/**
	 * Returns the tag of a client car.
	 * The given string is a string from the argument from the main and 
	 * contains all informations seperated by a semicolon.
	 * 
	 * @param args the argument from the main.
	 * @return the tag for the car client.
	 */
	private static String getTag(String args) {
		return getStringFromArgs(args, 0);
	}
	
	/**
	 * Returns the arrivale time for the car client.
	 * The given string is a string from the argument from the main and 
	 * contains all informations seperated by a semicolon.
	 * 
	 * @param args argument from the main.
	 * @return the arrival for the car client.
	 */
	private static int getArrivaleTime(String args) {
		return getTime(getStringFromArgs(args, 1));
	}
	
	/**
	 * Returns the parking time for the car client.
	 * The given string is a string from the argument from the main and 
	 * contains all informations seperated by a semicolon.
	 * 
	 * @param args argument from the main.
	 * @return the parking time for the car client.
	 */
	private static int getParkingTime(String args) {
		return getTime(getStringFromArgs(args, 2));
	}
	
	/**
	 * Returns a string out of a argument of the main.
	 * The given string is a string from the argument from the main and 
	 * contains all informations seperated by a semicolon. 
	 * 
	 * @param s the string from the main.
	 * @param n the position in between the semicolons.
	 * @return a string seperated by simicolon.
	 */
	private static String getStringFromArgs(String s, int n) {
		String[] result = s.split(";");
		return result[n];
	}
	
	/**
	 * Returns a time as int from a string.
	 * The string can contain colon and integers. 
	 * 
	 * @param s string to convert to a int.
	 * @return integer from a string.
	 */
	private static int getTime(String s) {
		String[] result = s.split(":");
		int hour = Integer.parseInt(result[0]) * 600; // 1 h = 600ms
		int minute = 0;
		try {
			minute = Integer.parseInt(result[1]) * 10; // 1 min = 10ms
		} catch(ArrayIndexOutOfBoundsException oob) {
		}
		return hour + minute;
	}
}
