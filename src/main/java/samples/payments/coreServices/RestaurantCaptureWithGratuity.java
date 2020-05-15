package samples.payments.coreServices;

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

public class RestaurantCaptureWithGratuity {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static PtsV2PaymentsCapturesPost201Response run() {
	
		CapturePaymentRequest requestObj = new CapturePaymentRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("1234567890");
		Ptsv2paymentsClientReferenceInformationPartner clientReferenceInformationPartner = new Ptsv2paymentsClientReferenceInformationPartner();
		clientReferenceInformationPartner.thirdPartyCertificationNumber("123456789012");
		clientReferenceInformation.partner(clientReferenceInformationPartner);

		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsidcapturesProcessingInformation processingInformation = new Ptsv2paymentsidcapturesProcessingInformation();
		processingInformation.industryDataType("restaurant");
		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsidcapturesOrderInformation orderInformation = new Ptsv2paymentsidcapturesOrderInformation();
		Ptsv2paymentsidcapturesOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("100");
		orderInformationAmountDetails.currency("USD");
		orderInformationAmountDetails.gratuityAmount("11.50");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		PtsV2PaymentsCapturesPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PtsV2PaymentsPost201Response paymentResponse = RestaurantAuthorization.run();

			CaptureApi apiInstance = new CaptureApi(apiClient);
			result = apiInstance.capturePayment(requestObj, paymentResponse.getId());

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
