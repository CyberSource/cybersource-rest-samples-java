package samples.UnifiedCheckout;

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

public class GenerateUnifiedCheckoutCaptureContext {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;
	
	public static void main(String args[]) throws Exception {
		run();
	}

	public static void run() {
	
		GenerateUnifiedCheckoutCaptureContextRequest requestObj = new GenerateUnifiedCheckoutCaptureContextRequest();

		List <String> targetOrigins = new ArrayList <String>();
		targetOrigins.add("https://the-up-demo.appspot.com");
		requestObj.targetOrigins(targetOrigins);

		requestObj.clientVersion("0.11");

		List <String> allowedCardNetworks = new ArrayList <String>();
		allowedCardNetworks.add("VISA");
		allowedCardNetworks.add("MASTERCARD");
		allowedCardNetworks.add("AMEX");
		requestObj.allowedCardNetworks(allowedCardNetworks);

		List <String> allowedPaymentTypes = new ArrayList <String>();
		allowedPaymentTypes.add("PANENTRY");
		allowedPaymentTypes.add("SRC");
		requestObj.allowedPaymentTypes(allowedPaymentTypes);

		requestObj.country("US");
		requestObj.locale("en_US");
		Upv1capturecontextsCaptureMandate upv1capturecontextsCaptureMandate = new Upv1capturecontextsCaptureMandate();
		upv1capturecontextsCaptureMandate.setBillingType("FULL");
		upv1capturecontextsCaptureMandate.setRequestEmail(true);
		upv1capturecontextsCaptureMandate.setRequestPhone(true);
		upv1capturecontextsCaptureMandate.setRequestShipping(true);
		upv1capturecontextsCaptureMandate.setShipToCountries(Arrays.asList("US", "GB"));
		upv1capturecontextsCaptureMandate.setShowAcceptedNetworkIcons(true);
		requestObj.captureMandate(upv1capturecontextsCaptureMandate);
		Upv1capturecontextsOrderInformation orderInformation = new Upv1capturecontextsOrderInformation();
		Upv1capturecontextsOrderInformationAmountDetails orderInformationAmountDetails = new Upv1capturecontextsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("21.00");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			UnifiedCheckoutCaptureContextApi apiInstance = new UnifiedCheckoutCaptureContextApi(apiClient);
			String response = apiInstance.generateUnifiedCheckoutCaptureContext(requestObj);

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