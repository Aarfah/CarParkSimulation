import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMain {
	public static void main(String args[]) {

		try {
			LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
			CarParkImp carPark = new CarParkImp();
			CarParkInterface stub = (CarParkInterface) UnicastRemoteObject.exportObject(carPark, 0);
			//RemoteServer.setLog(System.out);
			System.out.println("Setted Stub");
			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("CarPark", stub);
			System.out.println("Binded the stub");
			System.out.println("Server ready");
		} catch (RemoteException | AlreadyBoundException e) {
			System.err.println("Server exception: " + e.toString());
		}
	}
}