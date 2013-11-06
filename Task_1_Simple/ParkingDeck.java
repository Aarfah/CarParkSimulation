
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
public class ParkingDeck {
	List<Car> waitingQueue;
	Car[] parking;

	/**
	 * Construct a parking deck with an empty waiting queue.
	 * The parking will initialize by an array of the length of 4.
	 */
	public ParkingDeck() {
		waitingQueue = new ArrayList<>();
		parking = new Car[4];
	}

	/**
	 * Construct a parking deck with a waiting queue of cars out of a
	 * CSV-file.
	 * Constructor use the methode <code>setWaitingQueueFromCSV()</code> to
	 * read indormations for the car out whtich will be set in the wainting
	 * queue. 
	 * The parking will be initialize by an array of the length of 4.
	 * 
	 * @param filename a name of a CSV-File where information will be get.
	 * @throws IOException when file will be not found or the information in th
	 *					   file is in wrong order.
	 */
	public ParkingDeck(String filename) throws IOException {
		waitingQueue = new ArrayList<>();
		parking = new Car[4];
		setWaitingQueueFromCSV(filename);
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

	/**
	 * Checks the parking on a free slot.
	 * 
	 * @return	true - one or more slots are free
	 *			false - no slot is empty
	 */
	public boolean isFreeSlot() {
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
	 * Create cars from a CSV-File and put them in the waiting queue.
	 * The methode <code>getCarFromCSV()</code> deliver a list of cars whitch 
	 * were read out from a CSV-File.
	 * 
	 * @param filename the name of the CSV-File.
	 * @throws IOException 
	 */
	public void setWaitingQueueFromCSV(String filename) throws IOException {
		this.waitingQueue = getCarFromCSV(filename);
		sortWaitingQueue();
	}

	/**
	 * Creates a list of cars out of a CSV-file.
	 * <p>The CSV-file must have the right order of informations 
	 * to create a car from it.</p>
	 * <p>The information have to be like<br/>
	 * - a string as tag, to identify the car<br/>
	 * - the arrivale time in the parking deck, as integer value<br/>
	 * - the period, how long the car wants to park<br/>
	 * All informtaion have to be cutted by a semicolon</p>
	 * <p>Is the file name wrong or the syntax of the file is incorrect
	 * it might be come to an Exception.</p>
	 *
	 * @param filename the name of the file, where the cars a saved.
	 * @return a list of cars whitch were read out of the CSV-file.
	 * @throws IOException will be throwen then anything was wrong by reading
	 * the file.
	 */
	public List<Car> getCarFromCSV(String filename) throws IOException {
		List<Car> autoliste = new ArrayList<>();
		String strLine;
		String delimiter = ";";
		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((strLine = bufferedReader.readLine()) != null) {
				if (strLine.length() > 0) {
					String[] result = strLine.split(delimiter);
					int stringsInLine = result.length;
					for (int i = 0; i < stringsInLine; i = i + 3) {
						int tagIndex = i;
						int arrivaleIndex = i + 1;
						int periodIndex = i + 2;
						String tag = result[tagIndex];
						int arrivaleTime = Integer.parseInt(result[arrivaleIndex]);
						int periodTime = Integer.parseInt(result[periodIndex]);
						Car car = new Car(tag, arrivaleTime, periodTime);
						autoliste.add(car);
					}
				}
			}
		} catch (FileNotFoundException fnf) {
			System.out.println(filename + " -FILE NOT FOUND");
		}
		return autoliste;
	}

	/**
	 * Runs the managing of an parking deck. 
	 * Parking Deck will check following points 
	 * <p>- is a car on a parking -
	 * </p>
	 *
	 * @param time Sytem time.
	 */
	public void runSystem(int time) {
		if (isParkingEmpty()) {
			checkIn(time);
		} else {
			checkOut(time);
			checkIn(time);
		}
	}

	/**
	 * Checks out a car from the parking.
	 * Car will leave the parking deck, after stay as long as the period.
	 * To clean up a parking, it will be setted be a <code>null</code>. 
	 * <code>null</code> stays for empty in the parking.
	 * @param time 
	 */
	public void checkOut(int time) {
		//TODO check out parking by clean up and Write in 
		for (int i = 0; i < parking.length; i++) {
			if (!isSlotEmpty(i)) {
				Car carOnParking = parking[i];
				int startOfParking = carOnParking.getArrivaleTime();
				int endOfParking = startOfParking + carOnParking.getPeriod();
				if (endOfParking <= time) {
					writeProtokoll(carOnParking);
					removeCarFromParking(i);
				}
			}
		}
	}

	/**
	 * Checks in a new car from waiting queue into the parking if it is possible.
	 * <p>Checks the <code>arrivaleTime</code> from car with the time from the 
	 * system. Is the <code>arrivaletTime</code> smaller than the time from the
	 * system, and there is a free slot on the parking, the car will be send to 
	 * the free slot an leaves the <code>waitingQueue</code>.</p>
	 * 
	 * @param time the time from the system.
	 */
	public void checkIn(int time) throws ArrayIndexOutOfBoundsException{
		Car carFromFirst = waitingQueue.get(0);
		if (carFromFirst.getArrivaleTime() <= time && isFreeSlot()) {
			int freeSlot = getIndexOfFreeSlot();
			parking[freeSlot] = carFromFirst;
			carFromFirst.setArrivaleTime(time);
			waitingQueue.remove(0);
		}
		//TODO Protokoll
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
			isFull = isSlotEmpty(i);
			i++;
		}
		return isFull;
	}

	/**
	 * Sort the cars of the waiting queue of the parking deck.
	 * <p>All cars will be sort by the arrivale time.</p>
	 */
	public void sortWaitingQueue() {
		Collections.sort(waitingQueue);
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
	 * Writes all stats of the parking protokoll on the terminal.
	 * 
	 * @param car 
	 */
	private void writeProtokoll(Car car) {
		System.out.printf("\n%s........... %s\n", "Car-ID:", car.getTag());	
		System.out.printf("%-18s %02d:00\n","Parking Starts:", car.getArrivaleTime());
		int endOfParking = car.getArrivaleTime() + car.getPeriod();	
		System.out.printf("%-18s %02d:00\n", "Parking Ends:", endOfParking);
		System.out.printf("Period of parking: %sh\n", car.getPeriod());
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
