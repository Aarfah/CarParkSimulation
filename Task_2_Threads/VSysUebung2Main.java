
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author s0532338
 */
public class VSysUebung2Main extends Thread{

	public static void main(String[] args) throws IOException {
		String filename = "resource/Auto.csv";
		CarPark carPark = new CarPark();
		List<Car> carList = getCarFromCSV(filename, carPark);
		startAllCarThreads(carList);
		mySleep(20000);
		writePark(filled("Empty", ".", 10), "" + carPark.isParkingEmpty());
		writePark(filled("FULL", ".", 10), "" + carPark.isParkingFull());
		writePark(filled("Park", ".", 10), filename);
		writeSlots(carPark);
		System.exit(0);
	}
	
	private static void mySleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException ex) {
			Logger.getLogger(VSysUebung2Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private static void startAllCarThreads(List<Car> list) {
		for (int i = 0; i < list.size(); i++) {
			list.get(i).start();
		}
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
	private static List<Car> getCarFromCSV(String filename, CarPark carPark) throws IOException {
		List<Car> autoliste = new ArrayList<>();
		String strLine;
		String delimiter1 = ";";
		String delimiter2 = ":";
		try {
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((strLine = bufferedReader.readLine()) != null) {
				if (strLine.length() > 0) {
					String[] result = strLine.split(delimiter1);
					int stringsInLine = result.length;
					for (int i = 0; i < stringsInLine; i = i + 3) {
						int tagIndex = i;
						int arrivaleIndex = i + 1;
						int periodIndex = i + 2;
						String tag = result[tagIndex];
						String[] arrTime = result[arrivaleIndex].split(delimiter2);
						int arrivaleTime = Integer.parseInt(arrTime[0] + arrTime[1]);
						int periodTime = Integer.parseInt(result[periodIndex]);
						Car car = new Car(tag, arrivaleTime, periodTime, carPark);
						autoliste.add(car);
					}
				}
			}
		} catch (FileNotFoundException fnf) {
			System.out.println(filename + " -FILE NOT FOUND");
		}
		return autoliste;
	}
	
	
	private static void writePark(String s1, String s2) {
		System.out.printf("%s%s\n", s1, s2);
	}
	
	private static void writeSlots(CarPark carPark) {
		int countOfFree = 0;
		for (int i = 0; i < 4; i++) {
			if (carPark.get(i) == null) {
				countOfFree++;
			}
		}
		System.out.printf("They are %d free slots in the parking\n", countOfFree);
		for (int i = 0; i < 4; i++) {
			if (carPark.get(i) != null) {
				writePark(filled("Park " + (i + 1), ".", 10), carPark.get(i).getTag());
			} else {
				writePark(filled("Park " + (i + 1), ".", 10), "FREE");
			}
		}
	}
	
	/**
	 * Fills on string to the lenght.
	 * 
	 * @param s the String to fill.
	 * @param length the length of the result string
	 * @return a String filled by dots.
	 */
	private static String filled(String s, String fill, int length) {
		String resutl = s;
		while(resutl.length() < length) {
			resutl += fill;
		}
		return resutl;
	}
}
