package samples.payments.coreServices.electronicCheck;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.CreditApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreateCreditRequest;
import Model.PtsV2CreditsPost201Response;
import Model.Ptsv2creditsProcessingInformation;
import Model.Ptsv2paymentsClientReferenceInformation;
import Model.Ptsv2paymentsOrderInformationBillToCompany;
import Model.Ptsv2paymentsPaymentInformationBank;
import Model.Ptsv2paymentsPaymentInformationBankAccount;
import Model.Ptsv2paymentsidcapturesOrderInformationAmountDetails;
import Model.Ptsv2paymentsidcapturesOrderInformationBillTo;
import Model.Ptsv2paymentsidrefundsOrderInformation;
import Model.Ptsv2paymentsidrefundsPaymentInformation;

public class ProcessEcheckCredit {
	private static String responseCode = null;
	private static String status = null;
	private static PtsV2CreditsPost201Response response;
	private static Properties merchantProp;

	private static CreateCreditRequest request;

	private static CreateCreditRequest getRequest() {
		request = new CreateCreditRequest();

		// This is a section to set client reference information
		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("test_credits");
		request.setClientReferenceInformation(client);

		// This is a section to set ProcessingInformation
		Ptsv2creditsProcessingInformation processingInformation = new Ptsv2creditsProcessingInformation();
		processingInformation.commerceIndicator("internet");
		request.setProcessingInformation(processingInformation);

		// This is a section to set Amount Details which is needed to capture the
		// payment. Please note that it includes Service Fee Attribute
		Ptsv2paymentsOrderInformationBillToCompany billToCompany = new Ptsv2paymentsOrderInformationBillToCompany();
		billToCompany.name("ABC Company");

		// This is a section to initialize Bill to company information
		Ptsv2paymentsidcapturesOrderInformationBillTo billTo = new Ptsv2paymentsidcapturesOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.phoneNumber("4158880000");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
		billTo.locality("san francisco");
		billTo.company(billToCompany);
		billTo.administrativeArea("MI");
		billTo.email("test@cybs.com");

		// This is a section to initialize Order information
		Ptsv2paymentsidcapturesOrderInformationAmountDetails amountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("100");
		amountDetails.currency("usd");

		// setting amount details and bill to details to order information
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

		// This is a section to set Payment refunds information
		Ptsv2paymentsidrefundsPaymentInformation paymentInformation = new Ptsv2paymentsidrefundsPaymentInformation();
		paymentInformation.bank(bankInformation);
		request.setPaymentInformation(paymentInformation);
		
		return request;
	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static PtsV2CreditsPost201Response process() throws Exception {
		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;

			CreditApi creditApi = new CreditApi(apiClient);
			response = creditApi.createCredit(request);

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
