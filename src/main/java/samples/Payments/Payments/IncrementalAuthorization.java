package samples.Payments.Payments;

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

public class IncrementalAuthorization {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static PtsV2IncrementalAuthorizationPatch201Response run() {
		String id = AuthorizationForIncrementalAuthorizationFlow.run().getId();
		IncrementAuthRequest requestObj = new IncrementAuthRequest();

		Ptsv2paymentsidClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsidClientReferenceInformation();
		clientReferenceInformation.code("TC50171_3");

		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsidProcessingInformation processingInformation = new Ptsv2paymentsidProcessingInformation();
		Ptsv2paymentsidProcessingInformationAuthorizationOptions processingInformationAuthorizationOptions = new Ptsv2paymentsidProcessingInformationAuthorizationOptions();
		Ptsv2paymentsidProcessingInformationAuthorizationOptionsInitiator processingInformationAuthorizationOptionsInitiator = new Ptsv2paymentsidProcessingInformationAuthorizationOptionsInitiator();
		processingInformationAuthorizationOptionsInitiator.storedCredentialUsed("true");
		processingInformationAuthorizationOptions.initiator(processingInformationAuthorizationOptionsInitiator);

		processingInformation.authorizationOptions(processingInformationAuthorizationOptions);

		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsidOrderInformation orderInformation = new Ptsv2paymentsidOrderInformation();
		Ptsv2paymentsidOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsidOrderInformationAmountDetails();
		orderInformationAmountDetails.additionalAmount("22.49");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsidMerchantInformation merchantInformation = new Ptsv2paymentsidMerchantInformation();
		merchantInformation.transactionLocalDateTime("20191002080000");
		requestObj.merchantInformation(merchantInformation);

		Ptsv2paymentsidTravelInformation travelInformation = new Ptsv2paymentsidTravelInformation();
		travelInformation.duration("4");
		requestObj.travelInformation(travelInformation);

		PtsV2IncrementalAuthorizationPatch201Response result = null;
		try {
			merchantProp = Configuration.getAlternativeMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentsApi apiInstance = new PaymentsApi(apiClient);
			result = apiInstance.incrementAuth(id, requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}
