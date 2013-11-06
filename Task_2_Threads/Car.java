
/**
 *
 * @author Dennis Haegler - s0532338
 */
public class Car extends Thread implements Comparable<Car> {
	
	/**The number licence tag of a car.*/
	private String tag;
	
	/**Time than the car is arrived.*/
	private int arrivaleTime;
	
	/**The period, how long the car will park.*/
	private int period;
	
	/**The car park where the car wants to park.*/
	private CarPark carPark;

	/**
	 * A construtor to create a car with a tag, the arrivale time and the <br/>
	 * period, how long the car wants to park or stay on a place.
	 * 
	 * @param tag registration number of the car.
	 * @param arrivaleTime time then car arrives on the parking deck.
	 * @param period time how long the car wants to park in the parking deck.
	 */
	public Car(String tag, int arrivaleTime, int period, CarPark carPark) {
		this.tag = tag;
		this.arrivaleTime = arrivaleTime;
		this.period = period;
		this.carPark = carPark;
	}

	/**
	 * Getter for the Tag of the car.
	 * 
	 * @return the tag of the car as <code>String</code>.
	 */
	public String getTag() {
		return this.tag;
	}

	/**
	 * Getter for the arrivale time of the car.
	 * 
	 * @return the arrivale time of car as <code>Integer</code>
	 */
	public int getArrivaleTime() {
		return this.arrivaleTime;
	}

	/**
	 * Getter for the period time.
	 * <p>Shows how long the car wishes to park</p>
	 * 
	 * @return the time how long the car wants to park as <code>Integer</code>
	 */
	public int getPeriod() {
		return this.period;
	}
	
	
	
	/**
	 * Sets an new time as arrivale time on the car.
	 * 
	 * @param time the new arrivale time.
	 */
	public void setArrivaleTime(int time) {
		this.arrivaleTime = time;
	}
	
	/**
	 * A method to compare two cars by the <code>arrivaleTime</code>.
	 * <p>Overrides the methode compareTo to compare two cars by the <br/>
	 * arrivale time, to sort them correctly in the waiting queue of cars</p>
	 * @param aCar
	 * @return 
	 */
	@Override
	public int compareTo(Car aCar) {
		int result = 0;
		if (this.arrivaleTime < aCar.arrivaleTime) {
			result = -1;
		} else {
			if (this.arrivaleTime > aCar.arrivaleTime) {
				result = 1;
			}
		}
		return result;
	}
	
	/**
	 * Runs the car thread.
	 */
	public void run() {
		sleepTime(arrivaleTime);
		System.out.printf("%-5sarrives: %d o'clock\n", filled(tag, 8), arrivaleTime);
		while (!isInterrupted()) {
			if (carPark.isOpen()) {
				comesOnOpen();
			} else {
				comesOnClose();
			}
		}
	}
	
	/**
	 * Handels a car on situation on open car park.
	 */
	private void comesOnOpen() {
		if (carPark.isParkingSlotFree() && (carPark.isCarFirstInQueue(this))) {
			park();
		} else {
			if (carPark.isCarInWaitingQueue(this)) {
				sleepTime(600);
			} else {
				carPark.addCarToWaitingQueue(this);
				sleepTime(600);
			}
		}
	}
	
	/**
	 * Handels a car on situation on close car park.
	 */
	public void comesOnClose() {
		carPark.addCarToWaitingQueue(this);
		while (!carPark.isOpen()) {	
			sleepTime(60);
		}
	}
	
	/**
	 * Handles the parking of the car in the CarPark.
	 * Parking includes the park on the parking and the leaving from the parking
	 * on a free slot. The free slot will be given by the car park.
	 */
	private void park() {
		int freeSlot = carPark.getIndexOfFreeSlot();
		parkIn(freeSlot);
		sleepTime(period * 1000);
		parkOut(freeSlot);
	}
	
	/**
	 * Parks in the car on the parking the CarPark.
	 * Car parks on the given free slot.
	 * 
	 * @param freeSlot A free slot in the car park.
	 */
	private void parkIn(int freeSlot) {
		//System.out.println("Parking free on:..." + freeSlot);
		carPark.removeCarFromWaitingQueue(this);//TODO sychronize
		System.out.printf("%-5smoves from waiting queue to Slot %s\n", filled(tag, 8), freeSlot + 1);
		carPark.addCarToParking(freeSlot, this);
		System.out.printf("%-5sparks on Slot %d\n", filled(tag, 8), freeSlot + 1);
		//TODO Terminal output
		
	}
	
	/**
	 * Parks out the car from the parking on the slot of the car park.
	 *
	 * @param slot The slot where the car is parked.
	 */
	private void parkOut(int slot) {
		carPark.removeCarFromParking(slot);
		//TODO Terminal out of all parkings
		this.interrupt();
		System.out.printf("%-5sleaves the car park.\n", filled(tag, 8));
		System.out.printf("FREE SLOT ON %d\n", slot + 1);
	}
	
	/**
	 * Sets the thread to sleep by the given time.
	 * 
	 * @param time The Time to sleep.
	 */
	private void sleepTime(int time) {
		try {
			sleep(time);
		} catch (InterruptedException ex) {
			Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * Fills a string to the lenght.
	 * 
	 * @param s the String to fill.
	 * @param length the length of the result string
	 * @return a String filled by dots.
	 */
	private String filled(String s, int length) {
		String resutl = s;
		while(resutl.length() < length) {
			resutl += ".";
		}
		return resutl;
	}
}
