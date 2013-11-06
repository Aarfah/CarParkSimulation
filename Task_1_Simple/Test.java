
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Test {
	public static void main(String[] arc) throws InterruptedException, IOException {
		String[] result;
		String strLine;
		String delimiter = ";";
		String filename = "auto.csv";
		try {
			FileReader fileReader = new FileReader(new File(filename));
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			//TODO bufferedReader.readLine();
			while ((strLine = bufferedReader.readLine()) != null) {
				if (strLine.length() > 0) {
					result = strLine.split(delimiter);
					System.out.println(strLine);
					int g = result.length;
					for (int i = 0; i < g; i = i + 3) {
						int stringIndex = i;
						int ankuftsIndex = i + 1;
						int dauerIndex = i + 2;
						String a = result[stringIndex];
						String b = result[ankuftsIndex];
						String c = result[dauerIndex];
						System.out.println("length..." + a+" " + b +" " + c);
					}
					System.out.println("length..." + g);
				}
			}
		} catch (FileNotFoundException fnf) {
		}
	}
}
