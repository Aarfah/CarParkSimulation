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
public class Client extends Thread{
	/**Ip to connect to server.*/
	private String ip;

	/**Port to connect to server*/
	private int port;

	/**Socket from server from ip and port*/
	private Socket socket;

	/**
	 * Construct a new client on given ip and port.	\n
	 * If the client cant connect to a server, a IOException will be throwen.
	 *
	 * @param ip ip to connect on the server.
	 *param port port to connect in combination with the ip on a server.
	 */
	public Client(String ip, int port) throws IOException{
		this.ip = ip; // localhost
		this.port = port;
		this.socket = new Socket(ip, port);
		start();
	}
	
	public void run() {
		try {
			handle(this.ip);
		} catch(IOException e) {
			System.err.println(ip + " does not run.");
		}
	} 

	/**
	 * Tests to communicate with a socket to a server.
	 * 
	 * @throws IOException then somethind went wrong on the communication.
	 */
	public void handle(String sendingMessage) throws IOException {
		writeMessage(sendingMessage);
		System.out.println("sent msg to Server!");
		String gettedMsg = readMessage(socket); // Wait on yes
		writeMessage("I park now.");
		writeMessage("...and now iam leaving, bye bye");
		System.out.println("MSN From Server: " + gettedMsg);
		System.out.println("---------------------------------");
	}

	/**
	 * Writes a Message to the Server.
	 * 
	 * @param socket Socket from the Server.
	 * @param msg Message for the Server.
	 * @throws IOException then messaging to server failed.
	 */
	public void writeMessage(String msg) throws IOException {
		PrintWriter printWriter =
				new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		printWriter.println(msg);
		printWriter.flush();
	}

	/**
	 * Reads a Message from the server.
	 * 
	 * @param socket the server Socket.
	 * @return the message from the server.
	 * @throws IOException reading from server failed
	 */
	public String readMessage(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		String message = bufferedReader.readLine();
		return message;
	}
}

