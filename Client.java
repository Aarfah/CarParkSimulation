import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A Client excample to communicate with a server.
 * 
 * @author Dennis Hägler
 */
public class Client extends Thread {
	/**Ip to connect to server.*/
	private String ip;

	/**Port to connect to server*/
	private int port;

	/**Socket from server from ip and port*/
	private Socket socket;
	
	/**Tag of the car*/
	private String tag;
	
	/**The time to arrive in the car park*/
	private int arrivaleTime;
	
	/**The time to park in the car park*/
	private int parkingTime;

	/**
	 * Construct a new client on given ip and port, and the information for the
	 * car, tag, arrivale time and parking time. \n
	 * If the client cant connect to a server, a IOException will be throwen.
	 *
	 * @param ip ip to connect on the server.
	 * @param port port to connect in combination with the ip on a server.
	 * @param tag a tag for the car.
	 * @param arrivaleTime arrivale time in the car park from the car.
	 * @param parkingTime parking time from the car.
	 */
	public Client(String ip, int port, String tag, int arrivaleTime, int parkingTime) throws IOException{
		this.ip = ip; // localhost
		this.port = port;
		this.socket = new Socket(ip, port);
		this.tag = tag;
		this.arrivaleTime = arrivaleTime;
		this.parkingTime = parkingTime;
		//testRun();
	}
	
	public void testRun() {
		sendIdToServer();
		sendHelloToServer();
		//int serverTime = readTimeFromServer();
		String serverTime = readMessage();
		System.out.println("Server Time is: " + serverTime);
		sendMessage("Thanks");
	} 
	
	public void sendHelloToServer() {
		sendMessage("Hi server, here is " 
				+ tag
				+ ". Want to park on"
				+ arrivaleTime
				+ " h for "
				+parkingTime +" minutes.\n");
	}
	
	/**
	 * Returns the time from the server.
	 * 
	 * @return the time form the server.
	 * @throws IOException 
	 */
	public int readTimeFromServer() {
		String time = readMessage();
		int result;
		try {
			result = Integer.parseInt(time);
		} catch (NumberFormatException num) {
			System.err.println("Time From Server Failed.");
			result = 0;
		}
		return result;
	}
	
	/**
	 * Sends the id of the car to the server.
	 */
	public void sendIdToServer() {
		sendMessage(this.tag);
	}
	

	/**
	 * Tests to communicate with a socket to a server.
	 * 
	 * @throws IOException then somethind went wrong on the communication.
	 */
	public void test(String sendingMessage) {
		sendMessage(sendingMessage);
		System.out.println("sent msg to Server!");
		//String gettedMsg = readMessage(socket); // Wait on yes
		sendMessage("I park now.");
		trySleep(port);
		sendMessage("...and now iam leaving, bye bye");
		//System.out.println("MSN From Server: " + gettedMsg);
		System.out.println("---------------------------------");
	}
	
	/**
	 * Tries to sleep this thread.
	 * Uses the methode sleep from Thread.java and catch the 
	 * exception. In case of a throwen exception, a warning will be printed on 
	 * the terminal.
	 * 
	 * @param n the time to sleep in milliseconds.
	 */
	public void trySleep(int n) {
		try {
			sleep(n);
		} catch (InterruptedException ex) {
			System.err.print("No sleep this time\n");
		}
	}

	/**
	 * Writes a Message to the Server.
	 * 
	 * @param msg Message for the Server.
	 * @throws IOException then messaging to server failed.
	 */
	private void sendMessage(String msg) {
		tryMessageToServer(msg);
	}

	/**
	 * Reads a message from the server.
	 * 
	 * @return the message from the server.
	 * @throws IOException if were is no connection to the server.
	 */
	public String readMessage() {
		return tryReadMessage();
	}
	
	/**
	 * Error handler for sending a message to the client.
	 */
	private String tryReadMessage() {
		String message = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
			message += bufferedReader.readLine();
		} catch(IOException e){
			System.out.println("Cant read message from server on ." + ip +":" + port);
		}
		return message;
	}
	
	/**
	 * Error handler for sending a message to the client.
	 * 
	 * @param msg the message for the client.
	 */
	private void tryMessageToServer(String msg) {
		try {
			PrintWriter printWriter =
				new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			printWriter.println(msg);
			printWriter.flush();
		} catch(IOException e){
			System.out.println("Cant write message to server on ." 
					+ ip +":"
					+ port);
		}
	}
}

