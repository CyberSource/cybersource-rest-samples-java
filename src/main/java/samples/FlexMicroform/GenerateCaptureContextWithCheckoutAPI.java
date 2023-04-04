package samples.FlexMicroform;

import java.*;
import java.util.*;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class GenerateCaptureContextWithCheckoutAPI {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;
	
	public static void main(String args[]) throws Exception {
		run();
	}

	public static void run() {
	
		GenerateCaptureContextRequest requestObj = new GenerateCaptureContextRequest();

		List <String> targetOrigins = new ArrayList <String>();
		targetOrigins.add("https://www.test.com");
		requestObj.targetOrigins(targetOrigins);

		requestObj.clientVersion("v2.0");

		List <String> allowedCardNetworks = new ArrayList <String>();
		allowedCardNetworks.add("VISA");
		allowedCardNetworks.add("MASTERCARD");
		allowedCardNetworks.add("AMEX");
		requestObj.allowedCardNetworks(allowedCardNetworks);
		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			MicroformIntegrationApi apiInstance = new MicroformIntegrationApi(apiClient);
			String response = apiInstance.generateCaptureContext(requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println("Response Body :" + response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}