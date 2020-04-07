package samples.payments.coreServices.electronicCheck;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.RefundApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.PtsV2PaymentsPost201Response;
import Model.PtsV2PaymentsRefundPost201Response;
import Model.Ptsv2paymentsClientReferenceInformation;
import Model.Ptsv2paymentsPaymentInformationBank;
import Model.Ptsv2paymentsPaymentInformationBankAccount;
import Model.Ptsv2paymentsidcapturesOrderInformationAmountDetails;
import Model.Ptsv2paymentsidcapturesOrderInformationBillTo;
import Model.Ptsv2paymentsidrefundsOrderInformation;
import Model.Ptsv2paymentsidrefundsPaymentInformation;
import Model.Ptsv2paymentsidrefundsProcessingInformation;
import Model.RefundPaymentRequest;
import samples.payments.coreServices.ProcessPayment;

public class RefundEcheckPayment {
	private static String responseCode = null;
	private static String status = null;
	public static PtsV2PaymentsRefundPost201Response response;
	public static PtsV2PaymentsPost201Response paymentResponse;
	private static Properties merchantProp;

	static RefundPaymentRequest request;

	private static RefundPaymentRequest getRequest() {
		request = new RefundPaymentRequest();

		// This is a section to set client reference information
		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("test_refund_payment");
		request.setClientReferenceInformation(client);

		// This is a section to set Processing Information
		Ptsv2paymentsidrefundsProcessingInformation processingInformation = new Ptsv2paymentsidrefundsProcessingInformation();
		processingInformation.setPaymentSolution("Internet");
		request.setProcessingInformation(processingInformation);

		// This is a section to initialize Bill to Order information
		Ptsv2paymentsidcapturesOrderInformationBillTo billTo = new Ptsv2paymentsidcapturesOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.phoneNumber("4158880000");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
		billTo.locality("san francisco");
		billTo.administrativeArea("MI");
		billTo.email("test@cybs.com");

		// This is a section to set Amount Details which is needed to capture
		// the payment.
		Ptsv2paymentsidcapturesOrderInformationAmountDetails amountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("100");
		amountDetails.currency("usd");

		// setting amount details to order information
		Ptsv2paymentsidrefundsOrderInformation orderInformation = new Ptsv2paymentsidrefundsOrderInformation();
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

		// This is a section to set payment information details using the bank account
		// we initialized
		Ptsv2paymentsidrefundsPaymentInformation paymentInformation = new Ptsv2paymentsidrefundsPaymentInformation();
		paymentInformation.bank(bankInformation);
		request.setPaymentInformation(paymentInformation);

		return request;
	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static PtsV2PaymentsRefundPost201Response process() throws Exception {
		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;

			paymentResponse = ProcessPayment.process(true);
			RefundApi refundApi = new RefundApi(apiClient);
			response = refundApi.refundPayment(request, paymentResponse.getId());

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