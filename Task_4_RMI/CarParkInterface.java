import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Interface to communicate over RMI with a car (client).
 *
 * @author Dennis Haegler
 */
public interface CarParkInterface extends Remote {
	/**
	 * Receives information from a car, witch want to park in the car park. This
	 * is needed to remote the car from the car park and use the methodes from
	 * the car.
	 *
	 * @param id The id or tag of the car. witch wants to park.
	 * @param stub the stub from the car to call his mathodes.
	 *
	 * @throws RemoteException will be throwen if something failed on the 
	 * connection to the car (client)
	 */
	public int getTime() throws RemoteException;
	public void receiveParkRequest(String id, CarInterface stub) throws RemoteException;
	public void unparkCar(String id, int parkingIndex) throws RemoteException;
}
