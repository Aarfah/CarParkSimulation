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
		while (true) {
			try {
				startServer(serverSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
	 * Start the server by given Socket.
	 * 
	 * @param serverSocket the socket to communicate with the server.
	 * @throws IOException if connection from client failed by socket.
	 */
	private static void startServer(ServerSocket serverSocket) throws IOException {
		Socket client = waitOnLog(serverSocket);
		ServerTaskThread st = new ServerTaskThread(client);
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
