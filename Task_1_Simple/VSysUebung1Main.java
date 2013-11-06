
import java.io.IOException;


/**
 *
 * @author Dennis Hägler - s0532338
 */
public class VSysUebung1Main {
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		String filename = "resource/Auto.csv";
		ParkingDeck parkingDeck = new ParkingDeck(filename);
		parkingDeck.toStringQueue();
		System.out.print("\n\nSystem Control\n");
		System.out.print("--------------\n");
		for (int i = 0; i < 24; i++) {
			parkingDeck.runSystem(i);
		}
		System.out.print("\n-------------\n");
		System.out.print("End of System\n");
		System.out.print("-------------\n\n");
	}
}
