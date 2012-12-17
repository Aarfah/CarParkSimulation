
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dennis Hägler
 */
public class Server {
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		int port = 1337;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException ex) {
			Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		while (true) {
			try {
				test(serverSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void test(ServerSocket serverSocket) throws IOException {
		Socket client = waitOnLog(serverSocket);
		ServerTaskThread st = new ServerTaskThread(client);
	}
	
	
	private static Socket waitOnLog(ServerSocket serverSocket) throws IOException {
		Socket socket = serverSocket.accept(); // blockiert, bis sich ein Client angemeldet hat
		return socket;
	}
}
