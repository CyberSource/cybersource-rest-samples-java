package samples.authentication.harness;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MerchantProperties {
	private static String propertiesPath = "src/main/resources/cybs.properties";
	
	public static Properties getMerchantProperties() throws IOException {
		InputStream merchantInput = getPropertyFile(propertiesPath);
		Properties merchantProperties = new Properties();
		merchantProperties.load(merchantInput);
		return merchantProperties;
	}

	private static InputStream getPropertyFile(String fileName) throws FileNotFoundException {
		return new FileInputStream(fileName);
	}
}
