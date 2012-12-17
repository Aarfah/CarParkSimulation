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
		String msg = getReadMessage(socket);
		System.out.print(remoteUrl + " wrote: " + msg + "\n");
		try {
			sleep(new Random().nextInt(5) * 1000);
		} catch (InterruptedException ex) {
			System.err.print("No sleep this time\n");
		}
		System.out.print(msg + " Sleeped a while.\n\n");
	}
	
	/**
	 * Returns the read message form the client.
	 * 
	 * @param socket the socket to the client.
	 * @return a string from the client.
	 */
	public String getReadMessage(Socket socket) {
		try {
			return readMessage(socket);
		} catch (IOException ex) {
			System.out.println("Cant read message");
		}
		return "";
	}
	
	/**
	 * Reads a message from the client.
	 * @param socket
	 * @return
	 * @throws IOException 
	 */
	public String readMessage(Socket socket) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		String nachricht = bufferedReader.readLine(); 
		return nachricht;
	}
	
	/**
	 * Wirtes a message to the client.
	 * @param socket
	 * @param nachricht
	 * @throws IOException 
	 */
	public void wirteMessage(Socket socket, String nachricht)
			throws IOException {
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
				socket.getOutputStream()));
		printWriter.print(nachricht);
		printWriter.flush();
	}
}
