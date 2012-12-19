import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simple server simulation for a car park to handle requesting clients as 
 * cars to drive in the car park.
 * The clients can connect and communicate to the socket of server.
 * 
 * @author Dennis Hägler
 */
public class ServerMain {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		int port = 1337;
		serverSocket = getServerSocketBy(port);
		Timer systemTimer = new Timer();
		while (true) {
			try {
				startServerThread(serverSocket, systemTimer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Starts the server thread by given Socket and the system timer.
	 * 
	 * @param serverSocket the socket to communicate with the server.
	 * @throws IOException if connection from client failed by socket.
	 */
	private static void startServerThread(ServerSocket serverSocket, Timer sysTimer) throws IOException {
		Socket client = waitOnLog(serverSocket);
		ServerThread st = new ServerThread(client, sysTimer);
	}
	
	/**
	 * Returns the server socket by the given port.
	 * 
	 * @param port a port to comminicate with the server.
	 * @return a server socket.
	 */
	private static ServerSocket getServerSocketBy(int port) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException ex) {
			System.err.println("No Socket on Port: " + port);
			System.exit(1);
		}
		return serverSocket;
	}
	
	/**
	 * Returns the socket from the client.
	 * 
	 * @param serverSocket the socket to communicate with the server.
	 * @return the socket from the client.
	 * @throws IOException if the connection from client failed.
	 */
	private static Socket waitOnLog(ServerSocket serverSocket) throws IOException {
		Socket socket = serverSocket.accept(); // blockiert, bis sich ein Client angemeldet hat
		return socket;
	}
}
