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
			
			//Set Request MLE Settings in Merchant Configuration [Refer MLE.md on cybersource-rest-client-java github repo]
			props.setProperty("enableRequestMLEForOptionalApisGlobally", "true"); //Enables request MLE globally for all APIs that have optional MLE support //same as older deprecated variable "useMLEGlobally" //APIs that has MLE Request mandatory is default has MLE support in SDK without any configuration but support with JWT auth type.
			props.setProperty("requestMleKeyAlias", "CyberSource_SJC_US"); //this is optional parameter, not required to set the parameter if custom value is not required for MLE key alias. Default value is "CyberSource_SJC_US". //same as older deprecated variable "mleKeyAlias"
			
			//Set Response MLE Settings in Merchant Configuration [Refer MLE.md on cybersource-rest-client-java github repo]
			props.setProperty("enableResponseMleGlobally", "false"); //Enables/Disable response MLE globally for all APIs that support MLE responses
			props.setProperty("responseMlePrivateKeyFilePath", ""); //Path to the Response MLE private key file. Supported formats: .p12, .pfx, .pem, .key, .p8. Recommendation use encrypted private Key (password protection) for MLE response.
			props.setProperty("responseMlePrivateKeyFilePassword", ""); //Password for the private key file (required for .p12/.pfx files or encrypted private keys).
			props.setProperty("responseMleKID", ""); //This parameter is optional when responseMlePrivateKeyFilePath points to a CyberSource-generated P12 file. If not provided, the SDK will automatically fetch the Key ID from the P12 file. If provided, the SDK will use the user-provided value instead of the auto-fetched value.
			                                         //Required when using PEM format files (.pem, .key, .p8) or when providing responseMlePrivateKey object directly.
			
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
		
		//Set Request MLE Settings in Merchant Configuration [Refer MLE.md on cybersource-rest-client-java github repo]
		Map<String,String> mleMap = new HashMap<>();
		mleMap.put("createPayment", "false"); //only createPayment function will have MLE=false i.e. (/pts/v2/payments POST API) out of all MLE supported APIs
		mleMap.put("capturePayment", "true"); //capturePayment function will have MLE=true i.e.  (/pts/v2/payments/{id}/captures POST API), if it not in list of MLE supportedAPIs else it will already have MLE=true by global MLE parameter.
		
		props.setProperty("enableRequestMLEForOptionalApisGlobally", "true"); //Enables request MLE globally for all APIs that have optional MLE support //same as older deprecated variable "useMLEGlobally"
		props.put("mapToControlMLEonAPI", mleMap);   //disable or enable the MLE for APIs which is in the list of mleMAP 
		props.setProperty("requestMleKeyAlias", "CyberSource_SJC_US"); //this is optional parameter, not required to set the parameter if custom value is not required for MLE key alias. Default value is "CyberSource_SJC_US". //same as older deprecated variable "mleKeyAlias"		
		
		//Set Response MLE Settings in Merchant Configuration [Refer MLE.md on cybersource-rest-client-java github repo]
		props.setProperty("enableResponseMleGlobally", "false"); //Enables/Disable response MLE globally for all APIs that support MLE responses
		props.setProperty("responseMlePrivateKeyFilePath", ""); //Path to the Response MLE private key file. Supported formats: .p12, .pfx, .pem, .key, .p8. Recommendation use encrypted private Key (password protection) for MLE response.
		props.setProperty("responseMlePrivateKeyFilePassword", ""); //Password for the private key file (required for .p12/.pfx files or encrypted private keys).
		props.setProperty("responseMleKID", ""); //This parameter is optional when responseMlePrivateKeyFilePath points to a CyberSource-generated P12 file. If not provided, the SDK will automatically fetch the Key ID from the P12 file. If provided, the SDK will use the user-provided value instead of the auto-fetched value.
		                                         //Required when using PEM format files (.pem, .key, .p8) or when providing responseMlePrivateKey object directly.
		
		// JWT Parameters
		props.setProperty("keyAlias", "testrest");
		props.setProperty("keyPass", "testrest");
		props.setProperty("keyFileName", "testrest");
		// P12 key path. Enter the folder path where the .p12 file is located.
		props.setProperty("keysDirectory", "src/main/resources");

		return props;

	}
	
    public static Properties getMerchantDetailsWithMLE3() {
		
		Properties props = new Properties();

		// MLE only support with JWT = jwt auth type only
		props.setProperty("authenticationType", "JWT");
		
		props.setProperty("merchantID", "testrest");
		props.setProperty("runEnvironment", "apitest.cybersource.com");
		
		//Set Request MLE Settings in Merchant Configuration [Refer MLE.md on cybersource-rest-client-java github repo]
		Map<String,String> mleMap = new HashMap<>();
		mleMap.put("createPayment", "true"); //only createPayment function will have MLE=true i.e. (/pts/v2/payments POST API)
		mleMap.put("capturePayment", "true"); //only capturePayment function will have MLE=true i.e. (/pts/v2/payments/{id}/captures POST API)
		
		props.setProperty("enableRequestMLEForOptionalApisGlobally", "false"); //Disabled  request MLE globally for all APIs that have optional MLE support //same as older deprecated variable "useMLEGlobally"
		props.put("mapToControlMLEonAPI", mleMap);   //disable or enable the MLE for APIs which is in the list of mleMAP 
		props.setProperty("requestMleKeyAlias", "CyberSource_SJC_US"); //this is optional parameter, not required to set the parameter if custom value is not required for MLE key alias. Default value is "CyberSource_SJC_US". //same as older deprecated variable "mleKeyAlias"		
		
		//Set Response MLE Settings in Merchant Configuration [Refer MLE.md on cybersource-rest-client-java github repo]
		props.setProperty("enableResponseMleGlobally", "false"); //Enables/Disable response MLE globally for all APIs that support MLE responses
		props.setProperty("responseMlePrivateKeyFilePath", ""); //Path to the Response MLE private key file. Supported formats: .p12, .pfx, .pem, .key, .p8. Recommendation use encrypted private Key (password protection) for MLE response.
		props.setProperty("responseMlePrivateKeyFilePassword", ""); //Password for the private key file (required for .p12/.pfx files or encrypted private keys).
		props.setProperty("responseMleKID", ""); //This parameter is optional when responseMlePrivateKeyFilePath points to a CyberSource-generated P12 file. If not provided, the SDK will automatically fetch the Key ID from the P12 file. If provided, the SDK will use the user-provided value instead of the auto-fetched value.
		                                         //Required when using PEM format files (.pem, .key, .p8) or when providing responseMlePrivateKey object directly.
		

		// JWT Parameters
		props.setProperty("keyAlias", "testrest");
		props.setProperty("keyPass", "testrest");
		props.setProperty("keyFileName", "testrest");
		// P12 key path. Enter the folder path where the .p12 file is located.
		props.setProperty("keysDirectory", "src/main/resources");
		
		return props;

	}
    
    public static Properties getMerchantDetailsWithRequestAndResponseMLE1() {
		
		Properties props = new Properties();

		// MLE only support with JWT = jwt auth type only
		props.setProperty("authenticationType", "JWT");
		
		props.setProperty("merchantID", "agentic_mid_091225001");
		props.setProperty("runEnvironment", "apitest.cybersource.com");
		
		//Set Request MLE Settings in Merchant Configuration [Refer MLE.md on cybersource-rest-client-java github repo]
		props.setProperty("enableRequestMLEForOptionalApisGlobally", "true"); //Enables request MLE globally for all APIs that have optional MLE support //same as older deprecated variable "useMLEGlobally" //APIs that has MLE Request mandatory is default has MLE support in SDK without any configuration but support with JWT auth type.
		
		//Set Response MLE Settings in Merchant Configuration [Refer MLE.md on cybersource-rest-client-java github repo]
		props.setProperty("enableResponseMleGlobally", "true"); //Enables response MLE globally for all APIs that support MLE responses
		props.setProperty("responseMlePrivateKeyFilePath", "src/main/resources/agentic_mid_091225001_mle.p12"); //Path to the Response MLE private key file. Supported formats: .p12, .pfx, .pem, .key, .p8. Recommendation use encrypted private Key (password protection) for MLE response.
		props.setProperty("responseMlePrivateKeyFilePassword", "Changeit@123"); //Password for the private key file (required for .p12/.pfx files or encrypted private keys).
		props.setProperty("responseMleKID", "1757970970891045729358"); //Optional since p12 is Cybs Generated. 
		                                         //This parameter is optional when responseMlePrivateKeyFilePath points to a CyberSource-generated P12 file. If not provided, the SDK will automatically fetch the Key ID from the P12 file. If provided, the SDK will use the user-provided value instead of the auto-fetched value.
		                                         //Required when using PEM format files (.pem, .key, .p8) or when providing responseMlePrivateKey object directly.
		

		// JWT Parameters
		props.setProperty("keyAlias", "agentic_mid_091225001");
		props.setProperty("keyPass", "Changeit@123");
		props.setProperty("keyFileName", "agentic_mid_091225001");
		// P12 key path. Enter the folder path where the .p12 file is located.
		props.setProperty("keysDirectory", "src/main/resources");
		


		return props;

	}
    
public static Properties getMerchantDetailsWithRequestAndResponseMLE2() {
		
		Properties props = new Properties();

		// MLE only support with JWT = jwt auth type only
		props.setProperty("authenticationType", "JWT");
		
		props.setProperty("merchantID", "agentic_mid_091225001");
		props.setProperty("runEnvironment", "apitest.cybersource.com");
		
		//Set Request MLE Settings in Merchant Configuration [Refer MLE.md on cybersource-rest-client-java github repo]
		props.setProperty("enableRequestMLEForOptionalApisGlobally", "false"); //Disable request MLE globally for all APIs that have optional MLE support //same as older deprecated variable "useMLEGlobally" //APIs that has MLE Request mandatory is default has MLE support in SDK without any configuration but support with JWT auth type.
		
		//Set Response MLE Settings in Merchant Configuration [Refer MLE.md on cybersource-rest-client-java github repo]
		props.setProperty("enableResponseMleGlobally", "false"); //Disable response MLE globally for all APIs that support MLE responses
		
		//Set Request & Response MLE Settings in Merchant Configuration through MAP for API control level [Refer MLE.md on cybersource-rest-client-java github repo]
		Map<String,String> mleMap = new HashMap<>();
		mleMap.put("createPayment", "true::false"); //only createPayment function will have Request MLE=true and Response MLE = false i.e. (/pts/v2/payments POST API)
		mleMap.put("enrollCard", "true::true"); //only enrollCard function will have Request MLE=true & Response MLE =true i.e. (/acp/v1/tokens POST API)
		
		props.put("mapToControlMLEonAPI", mleMap);   //disable or enable the MLE for APIs which is in the list of mleMAP 
		
		//since one of the API has Response MLE true, so below fields are required for Response MLE
		props.setProperty("responseMlePrivateKeyFilePath", "src/main/resources/agentic_mid_091225001_mle.p12"); //Path to the Response MLE private key file. Supported formats: .p12, .pfx, .pem, .key, .p8. Recommendation use encrypted private Key (password protection) for MLE response.
		props.setProperty("responseMlePrivateKeyFilePassword", "Changeit@123"); //Password for the private key file (required for .p12/.pfx files or encrypted private keys).
		props.setProperty("responseMleKID", "1757970970891045729358"); //Optional since p12 is Cybs Generated. 
		                                         //This parameter is optional when responseMlePrivateKeyFilePath points to a CyberSource-generated P12 file. If not provided, the SDK will automatically fetch the Key ID from the P12 file. If provided, the SDK will use the user-provided value instead of the auto-fetched value.
		                                         //Required when using PEM format files (.pem, .key, .p8) or when providing responseMlePrivateKey object directly.
		

		// JWT Parameters
		props.setProperty("keyAlias", "agentic_mid_091225001");
		props.setProperty("keyPass", "Changeit@123");
		props.setProperty("keyFileName", "agentic_mid_091225001");
		// P12 key path. Enter the folder path where the .p12 file is located.
		props.setProperty("keysDirectory", "src/main/resources");
		


		return props;

	}


}