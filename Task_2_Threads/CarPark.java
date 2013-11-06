
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A parking deck allows cars to park on parking. Cars wait in a wainting queue and
 * will be picked up by the parking deck. 
 * Write all registration on a logfile from 0am to 11pm
 *
 * @author Dennis Hägler - s0532338
 */
public class CarPark extends Thread{
	private List<Car> waitingQueue;
	private Car[] parking;
	private int openTime;
	private int closeTime;

	/**
	 * Construct a parking deck with an empty waiting queue.
	 * The parking will initialize by an array of the length of 4.
	 */
	public CarPark() {
		waitingQueue = new ArrayList<>();
		parking = new Car[4];
		openTime = 8000;
		closeTime = 20000;
	}

	
	/**
	 * Deliver a car from index of parking
	 * 
	 * @param index the index where the car parks.
	 * @return a car from index.
	 */
	public Car get(int index) {
		return parking[index];
	}

	public List<Car> getWaitingQueue() {
		return waitingQueue;
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
	public void addCarToParking(int index, Car car) {
		parking[index] = car;
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
	synchronized public void removeCarFromWaitingQueue(Car aCar) {
		waitingQueue.remove(aCar);
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
	 * Create cars from a CSV-File and put them in the waiting queue.
	 * The methode <code>getCarFromCSV()</code> deliver a list of cars whitch 
	 * were read out from a CSV-File.
	 * 
	 * @param filename the name of the CSV-File.
	 * @throws IOException 
	 */
	public void setWaitingQueue(List<Car> list) {
		this.waitingQueue = list;
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
	 * @param car searched car in the first position of waiting queue.
	 * @return 
	 */
	public boolean isCarFirstInQueue(Car car) {
		return getWaitingQueue().isEmpty() || getWaitingQueue().contains(car);
	}
	
	/**
	 * Returns true if the car is in die waiting of cars.
	 * 
	 * @param car searched car in the waiting queue.
	 * @return TRUE  - Car is in the waiting queue.<br/>
	 *		   FALSE - Car is not in the waiting queue.
	 */
	public boolean isCarInWaitingQueue(Car car) {
		return waitingQueue.contains(car);
	}
	
	/**
	 * Adds a car to waiting queue
	 * 
	 * @param car Car to add in the waiting queue.
	 */
	public void addCarToWaitingQueue(Car car) {
		waitingQueue.add(car);
	}

	/**
	 * Sort the cars of the waiting queue of the parking deck.
	 * <p>All cars will be sort by the arrivale time.</p>
	 */
	public void sortWaitingQueue() {
		Collections.sort(waitingQueue);
	}
	
	/**
	 * Sort the cars of the waiting queue of the parking deck.
	 * <p>All cars will be sort by the arrivale time.</p>
	 */
	public void sortWaitingQueue(ArrayList<Car> carList) {
		Collections.sort(carList);
	}

	/**
	 * Write all cars of the waiting queue on the terminal.
	 * Write the tag, arrivale time and the period on the terminal.
	 */
	public void toStringQueue() {
		write("\n\nWaitingQueue:\n");
		write("-------------\n");
		System.out.printf("%-4s %-9s %-8s %7s\n","", "ID", "arrived", "stays");
		for (int i = 0; i < waitingQueue.size(); i++) {
			Car car = waitingQueue.get(i);
			System.out.printf("Car: %-10s "
					+ " %02d:00 "
					+ " %6dh \n", car.getTag(), car.getArrivaleTime(), car.getPeriod());
		}
		write("\n\n");
	}
	
	/**
	 * Returns the index of the car in the waiting queue.
	 */
	public int getIndexFromWaitingQueue(Car aCar) {
		return waitingQueue.indexOf(aCar);
	}
	
	/**
	 * Starts the car threads witch are waiting in the waiting queue.
	 */
	public void startAllFromWaitingQueue() {
		for (int i = 0; i < waitingQueue.size(); i++) {
			waitingQueue.get(i).start();
		}
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
		
	/**
	 * Writes a string on the terminal.
	 * Write the given text on terminal. Cut down <code>System.out.print</code>
	 * to write on the terminal.
	 * 
	 * @param text a string whitch will be written on the terminal.
	 */
	private void write(String text) {
		System.out.print(text);
	}
}
