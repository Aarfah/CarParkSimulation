import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A parking deck allows cars to park on parking. Cars wait in a wainting queue 
 * until they can park.
 *
 * @author Dennis Hägler - s0532338
 */
public class CarPark extends Thread{
	private List<String> waitingQueue;
	private String[] parking;

	/**
	 * Construct a parking deck with an empty waiting queue.
	 * The parking will initialize by an array of the length of 4.
	 */
	public CarPark() {
		waitingQueue = new ArrayList<>();
		parking = new String[4];
	}

	
	/**
	 * Deliver a car from index of parking
	 * 
	 * @param index the index where the car parks.
	 * @return a car from index.
	 */
	public String get(int index) {
		return parking[index];
	}

	/**
	 * Checks the parking on a free slot.
	 * 
	 * @return	true - one or more slots are free
	 *			false - no slot is empty
	 */
	public boolean isParkingSlotFree() {
		boolean isFree = false;
		int index = 0;
		while (!isFree && index < parking.length) {
			if (parking[index] == null) {
				isFree = true;
			}
			index++;
		}
		return isFree;
	}

	/**
	 * Returns an index of a free slot of parking.
	 * A free slot has a null objekt.
	 * 
	 * @return index as integer auf a free slot.
	 */
	public int getIndexOfFreeSlot() {
		int index = 0;
		boolean gotOne = false;
		while (!gotOne && index < parking.length) {
			if (parking[index] == null) {
				gotOne = true;
			} else {
				index++;
			}
		}
		int result = -1;
		if (index < parking.length) {
			result = index;
		}
		return result;
	}
	
	/**
	 * Returns an index of a full slot on the parking.
	 * @return 
	 */
	public int getIndexOfFullSlot() {
		int index = 0;
		boolean gotOne = false;
		while (gotOne && index < parking.length) {
			if (parking[index] == null) {
				gotOne = true;
			} else {
				index++;
			}
		}
		int result = -1;
		if (index < parking.length) {
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
	public void addCarIdToParking(int index, String carId) {
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
	 * Removes the Car from the waiting queue.
	 * @param aCar a Car witch will be removed from the queue.
	 */
	synchronized public void removeCarFromWaitingQueue(String carId) {
		waitingQueue.remove(carId);
	}
	
	/**
	 * Returns the status of the car park.
	 * 
	 * @return TRUE	- car park is open  \n
	 *		   FALSE - car park is close
	 */
	public boolean isOpen() {
		return true;
	}

	/**
	 * Returns <code>true</code> if the parking is complete empty and no car
	 * is parking.
	 * 
	 * @return	true - no car is parking <br/>
	 *			false - at least one car is parking
	 */
	public boolean isParkingEmpty() {
		boolean isEmpty = true;
		int i = 0;
		int placesOfParking = parking.length;
		while (isEmpty && i < placesOfParking) {
			isEmpty = (parking[i] == null);
			i++;
		}
		return isEmpty;
	}

	/**
	 * Returns a true then all parking slots are full.
	 * 
	 * @return	true - parking is complete full <br/>
	 *			false - at one parking is free
	 */
	public boolean isParkingFull() {
		boolean isFull = true;
		int i = 0;
		int placesOfParking = parking.length;
		while (isFull && i < placesOfParking) {
			isFull = !isSlotEmpty(i);
			i++;
		}
		return isFull;
	}
	
	/**
	 * Returns true if the car is in first on the waiting queue.
	 * 
	 * @param carId searched car in the first position of waiting queue.
	 * @return 
	 */
	public boolean isCarFirstInQueue(String carId) {
		return waitingQueue.isEmpty() || waitingQueue.contains(carId);
	}
	
	/**
	 * Returns true if the car is in die waiting of cars.
	 * 
	 * @param carId searched car in the waiting queue.
	 * @return TRUE  - Car is in the waiting queue.<br/>
	 *		   FALSE - Car is not in the waiting queue.
	 */
	public boolean isCarInWaitingQueue(String carId) {
		return waitingQueue.contains(carId);
	}
	
	/**
	 * Adds a car to waiting queue
	 * 
	 * @param carId Car to add in the waiting queue.
	 */
	public void addCarToWaitingQueue(String carId) {
		waitingQueue.add(carId);
	}
	
	/**
	 * Returns the index of the car in the waiting queue.
	 */
	public int getIndexFromWaitingQueue(String carId) {
		return waitingQueue.indexOf(carId);
	}
	
	/**
	 * Checks if the parking on index is empty. 
	 * 
	 * @param index the index of the parking.
	 * @return	true	- slot is empty
	 *			false	- slot is full
	 */
	private boolean isSlotEmpty(int index) {
		return (parking[index] == null);
	}
}
