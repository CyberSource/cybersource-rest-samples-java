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
import Model.Ptsv2paymentsOrderInformation;
import Model.Ptsv2paymentsOrderInformationAmountDetails;
import Model.Ptsv2paymentsOrderInformationBillTo;
import Model.Ptsv2paymentsPaymentInformation;
import Model.Ptsv2paymentsPaymentInformationBank;
import Model.Ptsv2paymentsPaymentInformationBankAccount;
import Model.Ptsv2paymentsProcessingInformation;

public class ProcessEcheckPaymentWithServiceFee {

	private static String responseCode = null;
	private static String status = null;
	private static PtsV2PaymentsPost201Response response;
	private static Properties merchantProp;
	private static CreatePaymentRequest request;

	private static CreatePaymentRequest getRequest() {
		request = new CreatePaymentRequest();

		// This is a section to set client reference information
		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("test_payment");
		request.clientReferenceInformation(client);

		// This is a section to set Processing Information
		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
		processingInformation.commerceIndicator("internet");
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
		// the payment.
		Ptsv2paymentsOrderInformationAmountDetails amountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		amountDetails.totalAmount("2325.00");
		amountDetails.currency("USD");
		amountDetails.serviceFeeAmount("30.0");

		// setting amount details to order information
		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);
		
		// This is a section to set Bank Information details
		Ptsv2paymentsPaymentInformationBank bankInformation = new Ptsv2paymentsPaymentInformationBank();
		Ptsv2paymentsPaymentInformationBankAccount bankAccount = new Ptsv2paymentsPaymentInformationBankAccount();
		bankAccount.number("4100");
		bankAccount.type("C");
		bankInformation.account(bankAccount);
		bankInformation.routingNumber("071923284");
		
		// This is a section to set payment information details using the bank account we initialized
		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		paymentInformation.bank(bankInformation);
		request.setPaymentInformation(paymentInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static PtsV2PaymentsPost201Response process() throws Exception {

		try {
			request = getRequest();
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
