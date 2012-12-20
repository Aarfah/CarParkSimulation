import java.io.IOException;

/**
 * A simple client simulation for a car to handle cars as client to communicate
 * with a car park server.
 * The clients can connect and communicate to the socket of server.
 * 
 * @author Dennis Hägler
 */
public class ClientMain {
	public static void main(String[] args) {
		String ip = "127.0.0.1";
		int port = 1337;
		String tag = getTag(args[0]);
		int arrivaleTime = getArrivaleTime(args[0]);
		int parkTime = getParkingTime(args[0]);
		try {
			Client client = new Client(ip, port, tag, arrivaleTime, parkTime);
		} catch (IOException ex) {
			System.err.println("NO SERVER: Connection to: " + ip + ":" + port + " failed.");
		}
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
