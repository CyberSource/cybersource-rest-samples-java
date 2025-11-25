package samples.MLEFeature;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Api.EnrollmentApi;
import Data.ConfigurationWithMLE;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.AgenticCardEnrollmentRequest;
import Model.AgenticCardEnrollmentResponse200;

public class AcpApiExampleWithRequestAndResponseMLE {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;
	public static boolean userCapture = false;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String args[]) throws Exception {
		run();
	}

	public static AgenticCardEnrollmentResponse200  run() throws JsonMappingException, JsonProcessingException {
	
		String request = "{\"clientCorrelationId\":\"3e1b7943-6567-4965-a32b-5aa93d057d35\",\"deviceInformation\":{\"userAgent\":\"SampleUserAgent\",\"applicationName\":\"MyMagicApp\",\"fingerprintSessionId\":\"finSessionId\",\"country\":\"US\",\"deviceData\":{\"type\":\"Mobile\",\"manufacturer\":\"Apple\",\"brand\":\"Apple\",\"model\":\"iPhone16ProMax\"},\"ipAddress\":\"192.168.0.100\",\"clientDeviceId\":\"000b2767814e4416999f4ee2b099491d2087\"},\"buyerInformation\":{\"merchantCustomerId\":\"3e1b7943-6567-4965-a32b-5aa93d057d35\",\"personalIdentification\":[{\"type\":\"Theidentificationtype\",\"id\":\"1\",\"issuedBy\":\"Thegovernmentagencythatissuedthedriver'slicenseorpassport\"}],\"language\":\"en\"},\"billTo\":{\"firstName\":\"John\",\"lastName\":\"Doe\",\"fullName\":\"JohnMichaelDoe\",\"email\":\"john.doe@example.com\",\"countryCallingCode\":\"1\",\"phoneNumber\":\"5551234567\",\"numberIsVoiceOnly\":false,\"country\":\"US\"},\"consumerIdentity\":{\"identityType\":\"EMAIL_ADDRESS\",\"identityValue\":\"john.doe@example.com\",\"identityProvider\":\"PARTNER\",\"identityProviderUrl\":\"https://identity.partner.com\"},\"paymentInformation\":{\"customer\":{\"id\":\"\"},\"paymentInstrument\":{\"id\":\"\"},\"instrumentIdentifier\":{\"id\":\"4044EB915C613A82E063AF598E0AE6EF\"}},\"enrollmentReferenceData\":{\"enrollmentReferenceType\":\"TOKEN_REFERENCE_ID\",\"enrollmentReferenceProvider\":\"VTS\"},\"assuranceData\":[{\"verificationType\":\"DEVICE\",\"verificationEntity\":\"10\",\"verificationEvents\":[\"01\"],\"verificationMethod\":\"02\",\"verificationResults\":\"01\",\"verificationTimestamp\":\"1735690745\",\"authenticationContext\":{\"action\":\"AUTHENTICATE\"},\"authenticatedIdentities\":{\"data\":\"authenticatedData\",\"provider\":\"VISA_PAYMENT_PASSKEY\",\"id\":\"f48ac10b-58cc-4372-a567-0e02b2c3d489\"},\"additionalData\":\"\"}],\"consentData\":[{\"id\":\"550e8400-e29b-41d4-a716-446655440000\",\"type\":\"PERSONALIZATION\",\"source\":\"CLIENT\",\"acceptedTime\":\"1719169800\",\"effectiveUntil\":\"1750705800\"}]}";
		ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		AgenticCardEnrollmentRequest requestObj = objectMapper.readValue(request, AgenticCardEnrollmentRequest.class);
		
		AgenticCardEnrollmentResponse200  result = null;
		
		
		//Case1: Simple way to configure the request and response MLE in SDK.
		try {
			merchantProp = ConfigurationWithMLE.getMerchantDetailsWithRequestAndResponseMLE1();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			EnrollmentApi apiInstance = new EnrollmentApi(apiClient);
			result = apiInstance.enrollCard(requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			WriteLogAudit(Integer.parseInt(responseCode));
			
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		//Case2: Control Request and Response MLE through map on API level
		try {
			merchantProp = ConfigurationWithMLE.getMerchantDetailsWithRequestAndResponseMLE2();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			EnrollmentApi apiInstance = new EnrollmentApi(apiClient);
			result = apiInstance.enrollCard(requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			WriteLogAudit(Integer.parseInt(responseCode));
			
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	return result;
	}
}
