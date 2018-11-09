package samples.payments.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.PtsV2PaymentsPost201Response;
import Model.Ptsv2paymentsClientReferenceInformation;
import Model.Ptsv2paymentsOrderInformation;
import Model.Ptsv2paymentsOrderInformationAmountDetails;
import Model.Ptsv2paymentsOrderInformationBillTo;
import Model.Ptsv2paymentsPaymentInformation;
import Model.Ptsv2paymentsPaymentInformationCard;
import Model.Ptsv2paymentsPointOfSaleInformation;
import Model.Ptsv2paymentsProcessingInformation;

public class ProcessPayment {
	private static String responseCode = null;
	private static String status = null;
	private static PtsV2PaymentsPost201Response response;
	private static boolean capture = false;
	private static Properties merchantProp;

	private static CreatePaymentRequest request;

	private static CreatePaymentRequest getRequest(boolean capture) {
		request = new CreatePaymentRequest();

		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("test_payment");
		request.clientReferenceInformation(client);

		Ptsv2paymentsPointOfSaleInformation saleInformation = new Ptsv2paymentsPointOfSaleInformation();
		saleInformation.cardPresent(false);
		saleInformation.catLevel(6);
		saleInformation.terminalCapability(4);
		request.pointOfSaleInformation(saleInformation);

		Ptsv2paymentsOrderInformationBillTo billTo = new Ptsv2paymentsOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
		billTo.locality("san francisco");
		billTo.administrativeArea("CA");
		billTo.email("test@cybs.com");

		Ptsv2paymentsOrderInformationAmountDetails amountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		amountDetails.totalAmount("100.00");
		amountDetails.currency("USD");

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
		if (capture == true) {
			processingInformation.capture(true);
		}
		request.processingInformation(processingInformation);

		Ptsv2paymentsPaymentInformationCard card = new Ptsv2paymentsPaymentInformationCard();
		card.expirationYear("2031");
		card.number("4111111111111111");
		card.securityCode("123");
		card.expirationMonth("12");

		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		paymentInformation.card(card);
		request.setPaymentInformation(paymentInformation);

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
			
			PaymentsApi paymentApi = new PaymentsApi();
			response = paymentApi.createPayment(request,merchantConfig);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response.getId());

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
