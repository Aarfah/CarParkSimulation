import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Server task thread to handle more client request at ones.
 * 
 * @author Dennis Hägler
 */
public class ServerTaskThread extends Thread {
	private Socket socket;
	private Timer serverTimer;
	private String id;
	
	/**
	 * Constructor of the ServerTaskThread.
	 * 
	 * @param socket the socket from the client.
	 */
	public ServerTaskThread(Socket socket, Timer serverTimer) {
		this.socket = socket;
		this.serverTimer = serverTimer;
		start();
	}
	
	/**
	 * Overrunning methode from Thread.java.
	 * Runs the server task thread.
	 */
	public void run() {
		String remoteUrl = socket.getInetAddress().getHostAddress();
		int remotePort = socket.getLocalPort();
		String idOfCar = readMessage();
		this.id = idOfCar;
		System.out.println("Car Id is: " + id);
		System.out.print("SERVER: " + idOfCar + " Connected to : "
				+ remoteUrl + ":"
				+ remotePort + "\n");
		String msg = readMessage();
		System.out.println(idOfCar + " wrote: " + msg); 
		//trySleep(1000);
		
		//sending Time
		sendTime();
		
		//TODO logig to say yes
		//tryMessageToClient("here Server, your can park");
		
		//wait on parking
		msg = readMessage();
		System.out.print(idOfCar + " wrote: " + msg + "\n");
		
		//wait on leaving
		msg = readMessage();
		System.out.print(idOfCar + " wrote: " + msg + "\n");
		System.out.print("Communication over.\n");
	}
	
	/**
	 * Writes a message to the client.
	 * 
	 * @param msg the message to send to the client.
	 * @throws IOException if outputStream cannot be written.
	 */
	public void sendMessage(String msg) {
		tryMessageToClient(msg);
	}
	
	/**
	 * Send the runned system time from the server to the client over socket.
	 */
	public void sendTime() {
		tryMessageToClient(serverTimer.getTime() + "");
	}
	
	/**
	 * Tries to sleep this thread.
	 * Uses the methode sleep from Thread.java and catch the 
	 * exception. In case of a throwen exception, a warning will be printed on 
	 * the terminal.
	 * 
	 * @param n the time how long the thread has to sleep.
	 */
	public void sleep(int n) {
		trySleep(n);
	}
	
	/**
	 * Reads and returns a message from the client. 
	 * The message from the client must end by a new line.
	 * 
	 * @return the message from a client.
	 * @throws IOException if inputStream cannot be readed.
	 */
	public String readMessage() {
		return tryReadMessage();
	}
	
	/**
	 * Error handler for sending a message to the client.
	 * 
	 * @param msg the message for the client.
	 */
	private void tryMessageToClient(String msg) {
		try {
			PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			printWriter.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Tries to sleep this thread.
	 * Uses the methode sleep from Thread.java and catch the 
	 * exception. In case of a throwen exception, a warning will be printed on 
	 * the terminal.
	 * 
	 * @param n the time to sleep in milliseconds.
	 */
	private void trySleep(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException ex) {
			System.err.println("Cant sleep this time.");
		}	
	}
	
	/**
	 * Returns the read message form the client.
	 * 
	 * @return a string from the client.
	 */
	private String tryReadMessage() {
		String msg = "";
		try {
			BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
			msg += bufferedReader.readLine(); 
		} catch (IOException ex) {
			System.out.println("Cant read message");
		}
		return msg;//TODO better handling
	}
	
}
