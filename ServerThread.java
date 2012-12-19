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
public class ServerThread extends Thread {
	private BufferedReader bufferedReader;
	private PrintWriter printWriter;
	private Socket socket;
	private Timer serverTimer;
	private String id;
	
	/**
	 * Constructor of the ServerThread.
	 * 
	 * @param socket the socket from the client.
	 */
	public ServerThread(Socket socket, Timer serverTimer) {
		this.socket = socket;
		this.serverTimer = serverTimer;
		initBufferedReader();
		initPrintWriter();
		start();
	}
	
	/**
	 * Overrunning methode from Thread.java.
	 * Runs the server task thread.
	 */
	public void run() {
		String id = readMessage();
		System.out.println("This is my id: " + id);
		String greeting = readMessage();
		System.out.println("This is a Greeting: " + greeting);
		sendTime();
		//sendMessage("Fick Dick");
	}
	
	/**
	 * Writes a message to the client.
	 * 
	 * @param msg the message to send to the client.
	 * @throws IOException if outputStream cannot be written.
	 */
	public void sendMessage(String msg) {
		printWriter.println(msg);
		printWriter.flush();
	}
	
	/**
	 * Returns the read message from the client. 
	 * The message from the client must end by a new line.
	 * 
	 * @return the message from a client.
	 * @throws IOException if inputStream cannot be readed.
	 */
	public String readMessage() {
		String msg = "";
		try {
			msg += bufferedReader.readLine();
		} catch (IOException ex) {
			System.out.println("Cant read message");
		}
		return msg;//TODO better handling
	}
	
	/**
	 * Sends the system time from the server to the client over the socket.
	 */
	public void sendTime() {
		String time = serverTimer.getTime() + "";
		sendMessage(time);
	}
	
	/**
	 * Let the thread sleep a time n.
	 * 
	 * @param n the time how long the thread has to sleep.
	 */
	public void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException ex) {
			System.err.println("Cant sleep this time.");
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
}
