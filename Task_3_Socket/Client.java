import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Client excample to communicate with a server.
 * 
 * @author Dennis Hägler
 */
public class Client extends Thread {
	/**A buffered reader to read strings from the Server*/
	private BufferedReader bufferedReader;
	
	/**A print writer to write strings to the server*/
	private PrintWriter printWriter;
	
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
		initBufferedReader();
		initPrintWriter();
		simulateParking();
	}
	
	/**
	 * Simulates a parking on a car park server.
	 */
	public void simulateParking() {
		sendMessage(tag);
		int conTime = readTimeFromServer();
		rest(conTime);
		sendMessage("Want to park");
		String permission = readMessage();
		park(parkingTime);
		sendMessage(tag + " wants to leave.");
		int discTime = readTimeFromServer();
		System.out.println(toString(conTime, discTime));
	}

	/**
	 * Writes a Message to the Server.
	 * 
	 * @param msg Message for the Server.
	 * @throws IOException then messaging to server failed.
	 */
	public void sendMessage(String msg) {
		this.printWriter.println(msg);
		printWriter.flush();
	}

	/**
	 * Reads a message from the server.
	 * Waits until a string is sent by the server ended by an newline.
	 * 
	 * @return the message from the server.
	 * @throws IOException if were is no connection to the server.
	 */
	public String readMessage() {
		String message = "";
		try {
			message += bufferedReader.readLine();
		} catch (IOException ex) {
			System.err.println("Cant read from buffered reader");
		}
		return message;
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
	 * Lets the client rest to wait.
	 */
	private void rest(int serverTime) {
		if (serverTime < arrivaleTime) {
			park(arrivaleTime - serverTime);
		}
	}
	
	/**
	 * Parks the car for a time.
	 * 
	 * @param time the time how long to park.
	 */
	private void park(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * Initialize the buffered reader.
	 */
	private void initBufferedReader() {
		try {
			this.bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		} catch(IOException e){
			System.out.println("Cant init buffered reader");
		}
	}
	
	/**
	 * Initialize the print writer.
	 */
	private void initPrintWriter() {
		try {
			this.printWriter =
					new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException ex) {
			System.out.println("Cant init print writer");
		}
	}
	
	/**
	 * Returns all important facts of the client printable for the terminal.
	 *
	 * @param connectTime the time were the client connected to the server.
	 * @param disconnectTime the time were the client disconnected from the 
	 *						 server.
	 * @return all important facts of the client for the terminal.
	 */
	public String toString(int connectTime, int disconnectTime) {
		String output = "";
		output += String.format("--------------------------------------------\n");
		output += String.format("\nMy Tag: " + tag + "\n"
				+ "My park start wish: " + arrivaleTime + "\n"
				+ "My stay time: " + parkingTime + "\n");
		output += String.format("Connected to: " + ip + ":" + port + "\n");
		output += String.format("Connected on: " + connectTime +"\n");
		output += String.format("Disconnected on: " + disconnectTime + "\n");
		output += String.format("--------------------------------------------\n");
		output += String.format("############################################\n");
		return output;
	}
	
	/**
	 * Returns all important facts of the client printable for the terminal.
	 * 
	 * @return all important facts of the client for the terminal.
	 */
	public String toString() {
		String output = "";
		output += String.format("--------------------------------------------\n");
		output += String.format("\nMy Tag: " + tag + "\n"
				+ "My time: " + arrivaleTime + "\n"
				+ "My stay time:" + parkingTime + "\n");
		output += String.format("Connected to: " + ip + ":" + port + "\n");
		output += String.format("--------------------------------------------\n");
		return output;
	}
}

