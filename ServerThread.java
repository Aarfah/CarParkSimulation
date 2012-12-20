import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Server task thread to handle more client request at ones.
 * 
 * @author Dennis Hägler
 */
public class ServerThread extends Thread {
	private BufferedReader bufferedReader;
	private PrintWriter printWriter;
	private String carId;
	private CarPark carPark;
	private Socket socket;
	private Timer serverTimer;
	
	/**
	 * Constructor of the ServerThread.
	 * 
	 * @param socket the socket from the client.
	 */
	public ServerThread(Socket socket, CarPark carPark, Timer serverTimer) {
		this.socket = socket;
		this.carPark = carPark;
		this.serverTimer = serverTimer;
		initBufferedReader();
		initPrintWriter();
		start();
	}
	
	/**
	 * Overrunning methode from Thread.java.
	 * Runs the task of a car park server thread.
	 */
	public void run() {
		this.carId = readMessage();
		int time = (int) serverTimer.getTime();
		System.out.println("System time:" + time + " : " + "Connected: " + carId);
		sendMessage(time + "");
		readMessage();
		while (!canPark()) {
			sleep(60);
		}
		int free = carPark.getIndexOfFreeSlot();
		carPark.addCarIdToParking(free, carId);
		System.out.println("System time:" + serverTimer.getTime() +" : " + "Parking: " + carId);
		System.out.println("Place: " + (free + 1));
		sendMessage("OK go");
		String leave = readMessage();
		time = (int) serverTimer.getTime();
		System.out.println("System time:" + time +" : "+ "Unpark: " + carId);
		System.out.println("Place: " + (free + 1));
		carPark.removeCarFromParking(free);
		sendMessage(time + "");
		closeSocket();
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
		return msg;
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
	
	private boolean canPark() {
		boolean parkOk = false;
		parkOk = carPark.isParkingSlotFree();
		return parkOk;
	}
	
	/**
	 * Closes the connection to the socket of the client.
	 */
	private void closeSocket() {
		try {
			socket.close();
		} catch (IOException ex) {
			System.err.println("Socket cant be closed.");
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
