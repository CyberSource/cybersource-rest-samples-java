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

public class AuthorizationWithPAEnrollAuthenticationNeeded {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static PtsV2PaymentsPost201Response run() {
	
		CreatePaymentRequest requestObj = new CreatePaymentRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("TC50171_3");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();

		List <String> actionList = new ArrayList <String>();
		actionList.add("CONSUMER_AUTHENTICATION");
		processingInformation.actionList(actionList);

		processingInformation.capture(false);
		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
		paymentInformationCard.number("4000000000001091");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2023");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("100.00");
		orderInformationAmountDetails.currency("usd");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
		orderInformationBillTo.firstName("John");
		orderInformationBillTo.lastName("Smith");
		orderInformationBillTo.address1("201 S. Division St._1");
		orderInformationBillTo.address2("Suite 500");
		orderInformationBillTo.locality("Foster City");
		orderInformationBillTo.administrativeArea("CA");
		orderInformationBillTo.postalCode("94404");
		orderInformationBillTo.country("US");
		orderInformationBillTo.email("accept@cybersource.com");
		orderInformationBillTo.phoneNumber("6504327113");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsConsumerAuthenticationInformation consumerAuthenticationInformation = new Ptsv2paymentsConsumerAuthenticationInformation();
		consumerAuthenticationInformation.requestorId("123123197675");
		consumerAuthenticationInformation.referenceId("CybsCruiseTester-8ac0b02f");
		requestObj.consumerAuthenticationInformation(consumerAuthenticationInformation);

		PtsV2PaymentsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentsApi apiInstance = new PaymentsApi(apiClient);
			result = apiInstance.createPayment(requestObj);

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
