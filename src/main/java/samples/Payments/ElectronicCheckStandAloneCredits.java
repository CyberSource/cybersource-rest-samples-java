// 23
// Code Generated: createCredit[Electronic Check Stand-Alone Credits]

package samples.Payments;
import java.*;
import java.util.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class ElectronicCheckStandAloneCredits{
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

/*
	public static void main(String args[]) throws Exception 
	{
		// Accept required parameters from args[] and pass to run.
		run( );
	}
*/
	public static PtsV2CreditsPost201Response run( ){
	
		CreateCreditRequest requestObj = new CreateCreditRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("TC46125-1");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2creditsProcessingInformation processingInformation = new Ptsv2creditsProcessingInformation();
		processingInformation.commerceIndicator("internet");
		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsidrefundsPaymentInformation paymentInformation = new Ptsv2paymentsidrefundsPaymentInformation();
		Ptsv2paymentsPaymentInformationBank paymentInformationBank = new Ptsv2paymentsPaymentInformationBank();
		Ptsv2paymentsPaymentInformationBankAccount paymentInformationBankAccount = new Ptsv2paymentsPaymentInformationBankAccount();
		paymentInformationBankAccount.type("C");
		paymentInformationBankAccount.number("4100");
		paymentInformationBankAccount.checkNumber("123456");
		paymentInformationBank.account(paymentInformationBankAccount);

		paymentInformationBank.routingNumber("071923284");
		paymentInformation.bank(paymentInformationBank);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsidrefundsOrderInformation orderInformation = new Ptsv2paymentsidrefundsOrderInformation();
		Ptsv2paymentsidcapturesOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("100");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Ptsv2paymentsidcapturesOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsidcapturesOrderInformationBillTo();
		orderInformationBillTo.firstName("John");
		orderInformationBillTo.lastName("Doe");
		orderInformationBillTo.address1("1 Market St");
		orderInformationBillTo.locality("san francisco");
		orderInformationBillTo.administrativeArea("CA");
		orderInformationBillTo.postalCode("94105");
		orderInformationBillTo.country("US");
		orderInformationBillTo.email("test@cybs.com");
		orderInformationBillTo.phoneNumber("4158880000");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		PtsV2CreditsPost201Response result = null;
		try
		{
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CreditApi apiInstance = new CreditApi(apiClient);
			result = apiInstance.createCredit( requestObj );

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	return result;
	}
}


//****************************************************************************************************


