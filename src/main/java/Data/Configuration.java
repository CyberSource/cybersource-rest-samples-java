package Data;

import java.util.Properties;

public class Configuration {
	public static Properties getMerchantDetails() {
		Properties props = new Properties();

		// HTTP_Signature and JWT
		props.setProperty("authenticationType", "http_signature");
		props.setProperty("merchantID", "testrest");
		props.setProperty("runEnvironment", "CyberSource.Environment.SANDBOX");
		props.setProperty("requestJsonPath", "src/main/resources/request.json");

		// JWT Parameters
		props.setProperty("keyAlias", "testrest");
		props.setProperty("keyPass", "testrest");
		props.setProperty("keyFileName", "testrest");

		// P12 key path. Enter the folder path where the .p12 file is located.

		props.setProperty("keysDirectory", "src/main/resources");
		// HTTP Parameters
		props.setProperty("merchantKeyId", "08c94330-f618-42a3-b09d-e1e43be5efda");
		props.setProperty("merchantsecretKey", "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=");
		// Logging to be enabled or not.
		props.setProperty("enableLog", "true");
		// Log directory Path
		props.setProperty("logDirectory", "log");
		props.setProperty("logFilename", "cybs");

		// Log file size in KB
		props.setProperty("logMaximumSize", "5M");

		return props;

	}

}
