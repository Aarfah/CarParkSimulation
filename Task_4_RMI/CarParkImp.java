import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * A parking deck allows cars to park on parking. Cars wait in a wainting queue
 * until they can park.
 *
 * @author Dennis Hägler - s0532338
 */
public class CarParkImp implements CarParkInterface {
	/**Timer or clock of the car park*/
	Timer timer;
	
	/**The parkings of the car park*/
	String[] parking = new String[4];
	
	/**The waiting queue of the car park*/
	List<String> waitingQueue;
			
	/**
	 * Constructs a car park with the implement of the interface CarParkInterface.
	 */
	public CarParkImp() {
		waitingQueue = new ArrayList<>();
		timer = new Timer();
	}
	
	/**
	 * Checks the parking on a free slot.
	 * 
	 * @return	true - one or more slots are free
	 *			false - no slot is empty
	 */
	public boolean isParkingSlotFree() {
		boolean isFree = false;
		int parkingLenght = parking.length;
		int index = 0;
		while (!isFree && index < parkingLenght) {
			if (parking[index] == null) {
				isFree = true;
			}
			index++;
		}
		return isFree;
	}
	
	/**
	 * Returns an index of a free slot of parking.
	 * A free slot has a null objekt. A -1 will be returned if no free slot 
	 * was found.
	 * 
	 * @return index as integer auf a free slot.
	 */
	public int getIndexOfFreeSlot() {
		int index = 0;
		int parkingLength = parking.length;
		boolean gotOne = false;
		while (!gotOne && index < parkingLength) {
			if (parking[index] == null) {
				gotOne = true;
			} else {
				index++;
			}
		}
		int result = -1;
		if (index < parkingLength) {
			result = index;
		}
		return result;
	}
	
	/**
	 * Adds a car into the parking.
	 * Methode needs an index, to know where to put the car. And a car to put it
	 * on the slot of the given index.
	 * 
	 * @param index the parking slot index.
	 * @param car a car which get added.
	 */
	public void setCarIdOnIndexOfParking(String carId, int index) {
		parking[index] = carId;
	}
	
	/**
	 * Removes a car from parking of the parking deck.
	 * To remove a car it will be overwritten by a null.
	 * 
	 * @param index the index of the parking slot whitch
	 */
	public void removeCarFromParking(int index) {
		parking[index] = null;
	}
	
	/**
	 * Adds a car to waiting queue
	 * 
	 * @param carId Car to add in the waiting queue.
	 */
	synchronized public void addCarToWaitingQueue(String carId) {
		waitingQueue.add(carId);
	}
	
	/**
	 * Removes the Car from the waiting queue.
	 * @param aCar a Car witch will be removed from the queue.
	 */
	synchronized public void removeCarFromWaitingQueue(String carId) {
		waitingQueue.remove(carId);
	}
	
	/**
	 * Returns a true if at least one car is in the waiting queue.
	 * 
	 * @return	TRUE - at least one car is in the waiting queue. \n
	 *			FALSE - no car is in the waiting queue.
	 */
	public boolean isWaitingQueueEmpty() {
		return (waitingQueue.size() > 0);
	}
	
	/**
	 * Returns the time from the start of the car park to now.
	 * The Time is from the timer of the car park.
	 * 
	 * @return the time from the server.
	 */
	public int getTime() {
		return (int) timer.getTime() / 100;
	}
	
	/**
	 * Unparks a car from the parking of the car park.
	 * 
	 * @param id the ID of the car.
	 * @param parkingIndex the index of the parking to remove the car.
	 * 
	 * @throws RemoteException if connection over RMI failed
	 */
	public void unparkCar(String id, int parkingIndex) throws RemoteException {
		//logic of car park to unpark the car from the car park
		removeCarFromParking(parkingIndex);
	}
	
	/**
	 * Handls a request from a car with the wish to park in the car park.
	 * Send car to park if the waiting queue is empty and a parking slot is
	 * free else it will send the car to list of waiting cars.
	 * 
	 * @param id the id of the car.
	 * @param stub the remote stub from the car.
	 */
	public void receiveParkRequest(String id, CarInterface stub) throws RemoteException {
		System.out.println(id + " is requesting to park.");
		if (isWaitingQueueEmpty() && isParkingSlotFree()) {
			parkCar(id, stub);
		} else {
			addCarToWaitingQueue(id);
			handleWaiter(id, stub);
		}
		System.out.println(id + " parked.");
	}
	
	/**
	 * Handles a waiting car on the queue of waiters.
	 * 
	 * @param id the id of the car.
	 * @param stub the remote stub of the car.
	 */
	public void handleWaiter(String id, CarInterface stub) throws RemoteException {
		while (waitingQueue.indexOf(id) > 0) {
			setSleep(60);
		}
		while (!isParkingSlotFree()) {
			setSleep(60);
		}
		parkCar(id, stub);
	}
	
	/**
	 * Parks a car on a free slot.
	 * 
	 * @param id the ID of the car to park.
	 * @param stub the remote stub of the car.
	 * 
	 * @throws RemoteException if connection to car failed.
	 */
	private void parkCar(String id, CarInterface stub) throws RemoteException {
		int parkingIndex = getIndexOfFreeSlot();
		stub.park(parkingIndex);
		setCarIdOnIndexOfParking(id, parkingIndex);
		removeCarFromWaitingQueue(id);
	}
	
	/**
	 * Sets a sleep time.
	 * 
	 * @param time the time to sleep.
	 */
	private void setSleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			System.err.println("Sleep failed");
		}
	}
}