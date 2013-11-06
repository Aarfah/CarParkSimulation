
import java.awt.Point;


/**
 *
 * @author Dennis Haegler - s0532338
 */
public class Car implements Comparable<Car> {
	
	/**The number licence tag of a car*/
	private String tag;
	
	/**Time than the car is arrived*/
	private int arrivaleTime;
	
	/**The period, how long the car will park*/
	private int period;

	/**
	 * A construtor to create a car with a tag, the arrivale time and the <br/>
	 * period, how long the car wants to park or stay on a place.
	 * 
	 * @param tag registration number of the car.
	 * @param arrivaleTime time then car arrives on the parking deck.
	 * @param period time how long the car wants to park in the parking deck.
	 */
	public Car(String tag, int arrivaleTime, int period) {
		this.tag = tag;
		this.arrivaleTime = arrivaleTime;
		this.period = period;
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
	 * A methode to compare two cars by the <code>arrivaleTime</code>.
	 * <p>Overrides the methode compareTo to compare two cars by the <br/>
	 * arrivale time, to sort them correctly in the wainting queue of cars</p>
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
}
