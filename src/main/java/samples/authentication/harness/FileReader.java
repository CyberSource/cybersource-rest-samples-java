package samples.authentication.harness;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileReader {
	public static String readJsonFromFile(String fileName) throws IOException {
		if (fileName != null) {
			InputStream jsonIn = new FileInputStream(fileName);
			byte[] jsonBytes = new byte[jsonIn.available()];
			jsonIn.read(jsonBytes);
			jsonIn.close();
			return new String(jsonBytes);
		} else {
			return null;
		}
	}
}
