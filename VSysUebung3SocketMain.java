import java.io.IOException;

/**
 *
 * @author Dennis Hägler
 */
public class VSysUebung3SocketMain {
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String ip = "127.0.0.1";
		int port = 1337;
		System.out.println("\nStarted new client " + args[0] +" and connecting to: "+
						ip +":" + port);
		try {
			Client client = new Client(ip, port);
			client.handle(args[0]+"\n");
			System.out.println("Conected to: " + ip + ":" + port);
		} catch (IOException ex) {
			System.err.println("Connection to: " + ip + ":" + port + " failed.");
		}
	}
}
