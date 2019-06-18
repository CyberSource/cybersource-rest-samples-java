package samples.payments.coreServices.serviceFee;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.CreditApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreateCreditRequest;
import Model.PtsV2CreditsPost201Response;
import Model.Ptsv2paymentsClientReferenceInformation;
import Model.Ptsv2paymentsidcapturesOrderInformationAmountDetails;
import Model.Ptsv2paymentsOrderInformationBillToCompany;
import Model.Ptsv2paymentsidcapturesOrderInformationBillTo;
import Model.Ptsv2paymentsidrefundsOrderInformation;
import Model.Ptsv2paymentsidrefundsPaymentInformation;
import Model.Ptsv2paymentsPaymentInformationBank;
import Model.Ptsv2paymentsPaymentInformationBankAccount;
import Model.Ptsv2creditsProcessingInformation;

public class ECheckStandaloneCreditWithServiceFee {

	private static String responseCode = null;
	private static String status = null;
	private static PtsV2CreditsPost201Response response;
	private static Properties merchantProp;

	private static CreateCreditRequest request;

	private static CreateCreditRequest getRequest() {
		request = new CreateCreditRequest();

		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("TC46125-1");
		request.setClientReferenceInformation(client);
		Ptsv2creditsProcessingInformation processingInformation= new Ptsv2creditsProcessingInformation();
		processingInformation.commerceIndicator("internet");
		request.setProcessingInformation(processingInformation);
		Ptsv2paymentsOrderInformationBillToCompany company=new Ptsv2paymentsOrderInformationBillToCompany();
		company.name("Visa");
		
		Ptsv2paymentsidcapturesOrderInformationBillTo billTo = new Ptsv2paymentsidcapturesOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.phoneNumber("4158880000");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
		billTo.company(company);
		billTo.locality("san francisco");
		billTo.administrativeArea("CA");
		billTo.email("test@cybs.com");
		
		Ptsv2paymentsidcapturesOrderInformationAmountDetails amountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("2325.00");
		amountDetails.serviceFeeAmount ("30.00");
		amountDetails.currency("usd");

		Ptsv2paymentsidrefundsOrderInformation orderInformation = new Ptsv2paymentsidrefundsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		// This is a section to set Bank Information details 
				Ptsv2paymentsPaymentInformationBank bankInformation= new Ptsv2paymentsPaymentInformationBank();
				Ptsv2paymentsPaymentInformationBankAccount bankAccount=new Ptsv2paymentsPaymentInformationBankAccount();
				bankAccount.number("4100");
				bankAccount.type("C");
				bankAccount.checkNumber("123456");
				
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

	public static  PtsV2CreditsPost201Response process() throws Exception {

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
