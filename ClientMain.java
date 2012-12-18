import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dennis Hägler
 */
public class ClientMain {
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String ip = "127.0.0.1";
		int port = 1337;
		String tag = getStringFromArgs(args[0], 0);
		String arrivaleTime = getStringFromArgs(args[0], 1);
		String getParkingTime = getStringFromArgs(args[0], 2);
		
		System.out.println("\nStarted new client " + tag +" and connecting to: "+
						ip +":" + port);
		try {
			Client client = new Client(ip, port);
			client.handle(tag + "\n");
			System.out.println("Conected to: " + ip + ":" + port);
		} catch (IOException ex) {
			System.err.println("Connection to: " + ip + ":" + port + " failed.");
		}
	}
	
	private static String getTag(String args) {
		return getStringFromArgs(args, 0);
	}
	
	
	private static int getArrivaleTime(String args) {
		return Integer.parseInt(getStringFromArgs(args, 1));
	}
	
	private static int getParkingTime(String args) {
		return getTime(getStringFromArgs(args, 2));
	}
	
	private static String getStringFromArgs(String s, int n) {
		String[] result = s.split(";");
		return result[n];
	}
	
	private static int getTime(String s) {
		String[] result = s.split(":");
		int hour = Integer.parseInt(result[0]) * 60;
		int minute = Integer.parseInt(result[1]) * 60;
		return hour + minute;
	}
}
