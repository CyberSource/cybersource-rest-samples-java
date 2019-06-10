package samples.payments.coreServices.electronicCheck;

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
import Model.Ptsv2paymentsPaymentInformationBank;
import Model.Ptsv2paymentsPaymentInformationBankAccount;
import Model.Ptsv2paymentsProcessingInformation;

public class ProcessEcheckPayment {
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

		// This is a section to set Bank Information details
		Ptsv2paymentsPaymentInformationBank bankInformation = new Ptsv2paymentsPaymentInformationBank();
		Ptsv2paymentsPaymentInformationBankAccount bankAccount = new Ptsv2paymentsPaymentInformationBankAccount();
		bankAccount.number("4100");
		bankAccount.type("C");
		bankInformation.account(bankAccount);
		bankInformation.routingNumber("071923284");

		// This is a section to initiali ze bank information details to payment
		// information
		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		paymentInformation.bank(bankInformation);
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
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;

			PaymentsApi paymentApi = new PaymentsApi(apiClient);
			response = paymentApi.createPayment(request);

			responseCode = apiClient.responseCode;
			status = apiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response);

		} catch (ApiException e) {
			e.printStackTrace();
		}
		return response;
	}
}
