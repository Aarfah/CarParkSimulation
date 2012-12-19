import java.io.IOException;

/**
 *
 * @author Dennis Hägler
 */
public class ClientMain {
	//TODO Zeit bis Clientstartet
	public static void main(String[] args) {
		String ip = "127.0.0.1";
		int port = 1337;
		String tag = getTag(args[0]);
		int arrivaleTime = getArrivaleTime(args[0]);
		int parkTime = getParkingTime(args[0]);
		System.out.println("args 0 " + args[0]);
		System.out.println("--------------------------------------------");
		System.out.println("\nMy Tag: " + tag 
				+ " my time: " + arrivaleTime 
				+ " my stay time:" + parkTime);
		System.out.println("\nStarted new client " 
				+ tag 
				+" and connecting to: "
				+ ip + ":" + port);
		try {
			Client client = new Client(ip, port, tag, arrivaleTime, parkTime);
			client.testRun();
			System.out.println(tag + " Connected to: " + ip + ":" + port);
		} catch (IOException ex) {
			System.err.println("NO SERVER: Connection to: " + ip + ":" + port + " failed.");
		}
	}
	
	private static String getTag(String args) {
		return getStringFromArgs(args, 0);
	}
	
	private static int getArrivaleTime(String args) {
		return getTime(getStringFromArgs(args, 1));
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
		int minute = 0;
		try {
			minute = Integer.parseInt(result[1]) * 60;
		} catch(ArrayIndexOutOfBoundsException oob) {
		}
		return hour + minute;
	}
}
