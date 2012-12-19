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
	private BufferedReader bufferedReader;
	
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
		testRun();
	}
	/**
	 * Testmethode to run a client on a server.
	 */
	public void testRun() {
		System.out.println("--------------------------------------------");
		System.out.println("\nMy Tag: " + tag 
				+ " my time: " + arrivaleTime 
				+ " my stay time:" + parkingTime);
		System.out.println("Connected to: "	+ ip + ":" + port);
		sendMessage(tag);
		sendMessage("Hi server, here is " + tag
				+ ". Want to park on " + arrivaleTime
				+ " h for "	+ parkingTime +" minutes.");
		String time = readMessage();
		System.out.println("The time from Server: " + time);
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
	 * 
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
}

