import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dennis Haegler
 */
public class CarImp implements CarInterface {
	/**The number licence tag of a car.*/
	private String tag;
	
	/**Time than the car is arrived.*/
	private int arrivaleTime;
	
	/**The period, how long the car will park.*/
	private int period;
	
	/**The stub from the server.*/
	CarParkInterface serverStub;
	
	/**The Stub from the car.*/
	CarInterface carStub;

	/**
	 * A construtor to create a car with a tag, the arrivale time and the <br/>
	 * period, how long the car wants to park or stay on a place.
	 *
	 * @param tag registration number of the car.
	 * @param arrivaleTime time then car arrives on the parking deck.
	 * @param period time how long the car wants to park in the parking deck.
	 */
	public CarImp(String tag, int arrivaleTime, int period, CarParkInterface serverStub) throws RemoteException {
		this.tag = tag;
		this.arrivaleTime = arrivaleTime;
		this.period = period;
		this.serverStub = serverStub;
		this.carStub = (CarInterface) UnicastRemoteObject.exportObject(this, 0);
	}

	/**
	 * Parks the car for the parking period.
	 * The car will park for the period time and call the server
	 * after it to unpark it on the server.
	 */
	public void park(int parkingIndex) throws RemoteException {
		System.out.println(tag + "----Iam parking now.");
		try {
			Thread.sleep(period);
		} catch (InterruptedException ex) {
			Logger.getLogger(CarImp.class.getName()).log(Level.SEVERE, null, ex);
		}
		serverStub.unparkCar(tag, parkingIndex);
		unpark();
	}

	/**
	 * Unparks a car.
	 */
	public void unpark() throws RemoteException {
		System.out.println(tag + "----Iam leaving now.");
	}

	/**
	 * Returns the ID (number licence tag) from the car to the server over RMI.
	 *
	 * @return the ID from the car.
	 */
	public String returnId() throws RemoteException {
		return tag;
	}
	
	/**
	 * Returns the returned time from the server.
	 * 
	 * @return the returned time from the server.
	 * @throws RemoteException if the connection to server failed.
	 */
	public int getTimeFromServer() throws RemoteException {
		return serverStub.getTime();
	}
	
	/**
	 * Sends a park request to the server.
	 * 
	 * @throws RemoteException if the connection to server failed.
	 */
	public void sendParkRequestToServer() throws RemoteException {
		serverStub.receiveParkRequest(tag, carStub);
	}
	
	/**
	 * 
	 * @throws RemoteException 
	 */
	public void startCarSimulation() throws RemoteException {
		int timeToPark;
		timeToPark = arrivaleTime - getTimeFromServer();
		if (timeToPark > 0) {
			setWait(timeToPark);
		}
		sendParkRequestToServer();
	}
	
	/**
	 * Sets a class to sleep for a time.
	 * 
	 * @param time the time to sleep.
	 */
	private void setWait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			System.out.println(tag + " failed on wait");
		}
	}
}
