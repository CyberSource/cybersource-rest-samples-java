package Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigurationWithMLE {
	public static Properties getMerchantDetailsWithMLE1() {
			
			Properties props = new Properties();
	
			// MLE only support with JWT = jwt auth type only
			props.setProperty("authenticationType", "JWT");
			
			props.setProperty("merchantID", "testrest");
			props.setProperty("runEnvironment", "apitest.cybersource.com");
			props.setProperty("requestJsonPath", "src/main/resources/request.json");
			
			//Set MLE Settings in Merchant Configuration
			props.setProperty("useMLEGlobally", "true"); //globally MLE will be enabled for all MLE supported APIs
			props.setProperty("mleKeyAlias", "CyberSource_SJC_US"); //this is optional parameter, not required to set the parameter if custom value is not required for MLE key alias. Default value is "CyberSource_SJC_US".
			
			// MetaKey Parameters
			props.setProperty("portfolioID", "");
			props.setProperty("useMetaKey", "false");
	
			// JWT Parameters
			props.setProperty("keyAlias", "testrest");
			props.setProperty("keyPass", "testrest");
			props.setProperty("keyFileName", "testrest");
			// P12 key path. Enter the folder path where the .p12 file is located.
			props.setProperty("keysDirectory", "src/main/resources");
			
			// Logging to be enabled or not.
			props.setProperty("enableLog", "true");
			// Log directory Path
			props.setProperty("logDirectory", "log");
			props.setProperty("logFilename", "cybs");
	
			// Log file size in KB
			props.setProperty("logMaximumSize", "5M");
	
			// OAuth related properties.
			props.setProperty("enableClientCert", "false");
			props.setProperty("clientCertDirectory", "src/main/resources");
			props.setProperty("clientCertFile", "");
			props.setProperty("clientCertPassword", "");
			props.setProperty("clientId", "");
			props.setProperty("clientSecret", "");
			
//			// HTTP Parameters
//			props.setProperty("merchantKeyId", "08c94330-f618-42a3-b09d-e1e43be5efda");
//			props.setProperty("merchantsecretKey", "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=");
	
			/*
			PEM Key file path for decoding JWE Response Enter the folder path where the .pem file is located.
			It is optional property, require adding only during JWE decryption.
			*/
//			props.setProperty("jwePEMFileDirectory", "src/main/resources/NetworkTokenCert.pem");
	
			return props;
	
		}
	
	public static Properties getMerchantDetailsWithMLE2() {
		
		Properties props = new Properties();

		// MLE only support with JWT = jwt auth type only
		props.setProperty("authenticationType", "JWT");
		
		props.setProperty("merchantID", "testrest");
		props.setProperty("runEnvironment", "apitest.cybersource.com");
		props.setProperty("requestJsonPath", "src/main/resources/request.json");
		
		//Set MLE Settings in Merchant Configuration
		Map<String,Boolean> mleMap = new HashMap<>();
		mleMap.put("createPayment", false); //only createPayment function will have MLE=false i.e. (/pts/v2/payments POST API) out of all MLE supported APIs
		mleMap.put("capturePayment", true); //capturePayment function will have MLE=true i.e.  (/pts/v2/payments/{id}/captures POST API), if it not in list of MLE supportedAPIs else it will already have MLE=true by global MLE parameter.
		
		props.setProperty("useMLEGlobally", "true"); //globally MLE will be enabled for all MLE supported APIs
		props.put("mapToControlMLEonAPI", mleMap);   //disable or enable the MLE for APIs which is in the list of mleMAP 
		props.setProperty("mleKeyAlias", "CyberSource_SJC_US"); //this is optional parameter, not required to set the parameter if custom value is not required for MLE key alias. Default value is "CyberSource_SJC_US".
		
		// MetaKey Parameters
		props.setProperty("portfolioID", "");
		props.setProperty("useMetaKey", "false");

		// JWT Parameters
		props.setProperty("keyAlias", "testrest");
		props.setProperty("keyPass", "testrest");
		props.setProperty("keyFileName", "testrest");
		// P12 key path. Enter the folder path where the .p12 file is located.
		props.setProperty("keysDirectory", "src/main/resources");
		
		// Logging to be enabled or not.
		props.setProperty("enableLog", "true");
		// Log directory Path
		props.setProperty("logDirectory", "log");
		props.setProperty("logFilename", "cybs");

		// Log file size in KB
		props.setProperty("logMaximumSize", "5M");

		// OAuth related properties.
		props.setProperty("enableClientCert", "false");
		props.setProperty("clientCertDirectory", "src/main/resources");
		props.setProperty("clientCertFile", "");
		props.setProperty("clientCertPassword", "");
		props.setProperty("clientId", "");
		props.setProperty("clientSecret", "");
		
//		// HTTP Parameters
//		props.setProperty("merchantKeyId", "08c94330-f618-42a3-b09d-e1e43be5efda");
//		props.setProperty("merchantsecretKey", "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=");

		/*
		PEM Key file path for decoding JWE Response Enter the folder path where the .pem file is located.
		It is optional property, require adding only during JWE decryption.
		*/
//		props.setProperty("jwePEMFileDirectory", "src/main/resources/NetworkTokenCert.pem");

		return props;

	}
	
    public static Properties getMerchantDetailsWithMLE3() {
		
		Properties props = new Properties();

		// MLE only support with JWT = jwt auth type only
		props.setProperty("authenticationType", "JWT");
		
		props.setProperty("merchantID", "testrest");
		props.setProperty("runEnvironment", "apitest.cybersource.com");
		props.setProperty("requestJsonPath", "src/main/resources/request.json");
		
		//Set MLE Settings in Merchant Configuration
		Map<String,Boolean> mleMap = new HashMap<>();
		mleMap.put("createPayment", true); //only createPayment function will have MLE=true i.e. (/pts/v2/payments POST API)
		mleMap.put("capturePayment", true); //only capturePayment function will have MLE=true i.e. (/pts/v2/payments/{id}/captures POST API)
		
		props.setProperty("useMLEGlobally", "false"); //globally MLE will be disabled for all the APIs in SDK
		props.put("mapToControlMLEonAPI", mleMap);   //disable or enable the MLE for APIs which is in the list of mleMAP 
		props.setProperty("mleKeyAlias", "CyberSource_SJC_US"); //this is optional parameter, not required to set the parameter if custom value is not required for MLE key alias. Default value is "CyberSource_SJC_US".
		
		// MetaKey Parameters
		props.setProperty("portfolioID", "");
		props.setProperty("useMetaKey", "false");

		// JWT Parameters
		props.setProperty("keyAlias", "testrest");
		props.setProperty("keyPass", "testrest");
		props.setProperty("keyFileName", "testrest");
		// P12 key path. Enter the folder path where the .p12 file is located.
		props.setProperty("keysDirectory", "src/main/resources");
		
		// Logging to be enabled or not.
		props.setProperty("enableLog", "true");
		// Log directory Path
		props.setProperty("logDirectory", "log");
		props.setProperty("logFilename", "cybs");

		// Log file size in KB
		props.setProperty("logMaximumSize", "5M");

		// OAuth related properties.
		props.setProperty("enableClientCert", "false");
		props.setProperty("clientCertDirectory", "src/main/resources");
		props.setProperty("clientCertFile", "");
		props.setProperty("clientCertPassword", "");
		props.setProperty("clientId", "");
		props.setProperty("clientSecret", "");
		
//		// HTTP Parameters
//		props.setProperty("merchantKeyId", "08c94330-f618-42a3-b09d-e1e43be5efda");
//		props.setProperty("merchantsecretKey", "yBJxy6LjM2TmcPGu+GaJrHtkke25fPpUX+UY6/L/1tE=");

		/*
		PEM Key file path for decoding JWE Response Enter the folder path where the .pem file is located.
		It is optional property, require adding only during JWE decryption.
		*/
//		props.setProperty("jwePEMFileDirectory", "src/main/resources/NetworkTokenCert.pem");

		return props;

	}

}