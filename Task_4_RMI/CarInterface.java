import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * A interface for cars to communicate over RMI.
 * 
 * @author Dennis Haegler
 */
public interface CarInterface extends Remote{
	public void park(int parkingIndex) throws RemoteException;
	public void unpark() throws RemoteException;
	public String returnId() throws RemoteException;
}
