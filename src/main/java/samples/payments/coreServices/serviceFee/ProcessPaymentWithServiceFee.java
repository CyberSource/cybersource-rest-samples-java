package samples.payments.coreServices.serviceFee;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.PtsV2PaymentsPost201Response;
import Model.Ptsv2paymentsClientReferenceInformation;
import Model.Ptsv2paymentsMerchantInformation;
import Model.Ptsv2paymentsMerchantInformationServiceFeeDescriptor;
import Model.Ptsv2paymentsOrderInformation;
import Model.Ptsv2paymentsOrderInformationAmountDetails;
import Model.Ptsv2paymentsOrderInformationBillTo;
import Model.Ptsv2paymentsPaymentInformation;
import Model.Ptsv2paymentsPaymentInformationCard;
import Model.Ptsv2paymentsProcessingInformation;

/**
 * 
 * This is a sample code to process
 *
 */
public class ProcessPaymentWithServiceFee {

	private static String responseCode = null;
	private static String status = null;
	private static PtsV2PaymentsPost201Response response;
	private static boolean capture = false;
	private static Properties merchantProp;

	private static CreatePaymentRequest request;

	private static CreatePaymentRequest getRequest(boolean capture) {
		request = new CreatePaymentRequest();

		// This is a section to set client reference information
		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("test_payment");
		request.clientReferenceInformation(client);

		// This is a section to set ProcessingInformation
		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
		processingInformation.commerceIndicator("internet");
		if (capture == true) {
			processingInformation.capture(true);
		}
		request.setProcessingInformation(processingInformation);

		// This is a section to initialize Bill to Order information
		Ptsv2paymentsOrderInformationBillTo billTo = new Ptsv2paymentsOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
		billTo.locality("san francisco");
		billTo.administrativeArea("CA");
		billTo.email("test@cybs.com");

		// This is a section to set Amount Details which is needed to capture
		// the payment. Please note that it includes Service Fee Attribute
		Ptsv2paymentsOrderInformationAmountDetails amountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		amountDetails.totalAmount("2325.00");
		amountDetails.currency("USD");
		amountDetails.serviceFeeAmount("30.0");

		// setting amount details to order information
		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		// This is a section to set Card Information details
		Ptsv2paymentsPaymentInformationCard card = new Ptsv2paymentsPaymentInformationCard();
		card.expirationYear("2031");
		card.number("4111111111111111");
		card.securityCode("123");
		card.expirationMonth("12");

		// This is a section to initialize card information details to payment
		// information
		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		paymentInformation.card(card);
		request.setPaymentInformation(paymentInformation);

		// This is a section to set service fee descriptor details
		Ptsv2paymentsMerchantInformationServiceFeeDescriptor merchantInformationServiceFeeDescriptor = new Ptsv2paymentsMerchantInformationServiceFeeDescriptor();
		merchantInformationServiceFeeDescriptor.name("CyberSource Service Fee");
		merchantInformationServiceFeeDescriptor.contact("800-999-9999");
		merchantInformationServiceFeeDescriptor.state("CA");

		// This is a section to set service descriptor to merchant information
		Ptsv2paymentsMerchantInformation merchantInformation = new Ptsv2paymentsMerchantInformation();
		merchantInformation.setServiceFeeDescriptor(merchantInformationServiceFeeDescriptor);
		request.setMerchantInformation(merchantInformation);
		return request;

	}

	public static void main(String args[]) throws Exception {
		process(capture);
	}

	public static PtsV2PaymentsPost201Response process(boolean check) throws Exception {

		try {
			capture = check;
			request = getRequest(capture);
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient = new ApiClient(merchantConfig);

			PaymentsApi paymentApi = new PaymentsApi();
			response = paymentApi.createPayment(request);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response);

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
