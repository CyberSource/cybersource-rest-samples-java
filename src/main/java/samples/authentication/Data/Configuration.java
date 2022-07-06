package samples.authentication.Data;

import java.util.Properties;

public class Configuration {
	public static Properties getMerchantDetails() {
		Properties props = new Properties();

		// HTTP_Signature and JWT
		props.setProperty("authenticationType", "http_signature");
		props.setProperty("merchantID", "mirakl_slush0001");
		props.setProperty("runEnvironment", "apitest.cybersource.com");
		props.setProperty("requestJsonPath", "resources/request.json");

		// JWT Parameters
		props.setProperty("keyAlias", "testrest");
		props.setProperty("keyPass", "testrest");
		props.setProperty("keyFileName", "testrest");

		// P12 key path. Enter the folder path where the .p12 file is located.

		props.setProperty("keysDirectory", "resources");
		// HTTP Parameters
		props.setProperty("merchantKeyId", "fe0c55d7-2525-4809-93bd-9746d10d5923");
		props.setProperty("merchantsecretKey", "5JP/dtU7sJiZ9UrjQBvCXzOBIQwGlef19AVW90hp1Fs=");
		props.setProperty("useMetaKey", "false");
		props.setProperty("enableClientCert", "false");
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
