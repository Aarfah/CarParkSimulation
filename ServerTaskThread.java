import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

/**
 * Server task thread to handle more client request at ones.
 * 
 * @author Dennis Hägler
 */
public class ServerTaskThread extends Thread {
	private Socket socket;
	
	/**
	 * Constructor of the ServerTaskThread.
	 * 
	 * @param socket the socket from the client.
	 */
	public ServerTaskThread(Socket socket) {
		this.socket = socket;
		start();
	}
	
	/**
	 * Overrunning methode from Thread.java.
	 * Runs the server task thread.
	 */
	public void run() {
		String remoteUrl = socket.getInetAddress().getHostAddress();
		int remotePort = socket.getLocalPort();
		System.out.print(remoteUrl + " Connected to server on port: " 
				+ remotePort + "\n");
		String msg = tryReadMessage();
		System.out.print(remoteUrl + " wrote: " + msg + "\n");
		
		//TODO logig to say yes
		tryMessageToClient("here Server, your can park");
		
		//wait on parking
		msg = tryReadMessage();
		System.out.print(remoteUrl + " wrote: " + msg + "\n");
		
		//wait on leaving
		msg = tryReadMessage();
		System.out.print(remoteUrl + " wrote: " + msg + "\n");
		System.out.print("Communication over.\n");
	}
	
	/**
	 * Tries to sleep this thread.
	 * Uses the methode sleep from Thread.java and catch the 
	 * exception. In case of a throwen exception, a warning will be printed on 
	 * the terminal.
	 */
	public void trySleep() {
		trySleep(new Random().nextInt(5) * 1000);
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
	 * Returns the read message form the client.
	 * 
	 * @return a string from the client.
	 */
	public String tryReadMessage() {
		try {
			return readMessage();
		} catch (IOException ex) {
			System.out.println("Cant read message");
		}
		return "";//TODO better handling
	}
	
	/**
	 * Error handler for sending a message to the client.
	 * 
	 * @param msg the message for the client.
	 */
	public void tryMessageToClient(String msg) {
		try {
			writeMessage(msg);
		} catch(IOException e){
			System.out.println("Cant write message.");
		}
	}
	
	/**
	 * Reads and returns a message from the client. 
	 * The message from the client must end by a new line.
	 * 
	 * @return the message from a client.
	 * @throws IOException if inputStream cannot be readed.
	 */
	public String readMessage() throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		String nachricht = bufferedReader.readLine(); 
		return nachricht;
	}
	
	/**
	 * Wirtes a message to the client.
	 * 
	 * @param msg the message to send to the client.
	 * @throws IOException if outputStream cannot be written.
	 */
	public void writeMessage(String msg) throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		printWriter.println(msg);
		printWriter.flush();
	}
}
