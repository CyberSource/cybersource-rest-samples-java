// 22
// Code Generated: createCredit[Service Fees Credit]

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

public class ServiceFeesCredit{
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;


	public static void main(String args[]) throws Exception 
	{
		// Accept required parameters from args[] and pass to run.
		run( );
	}

	public static PtsV2CreditsPost201Response run( ){
	
		CreateCreditRequest requestObj = new CreateCreditRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("12345678");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsidrefundsPaymentInformation paymentInformation = new Ptsv2paymentsidrefundsPaymentInformation();
		Ptsv2paymentsidrefundsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsidrefundsPaymentInformationCard();
		paymentInformationCard.number("4111111111111111");
		paymentInformationCard.expirationMonth("03");
		paymentInformationCard.expirationYear("2031");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsidrefundsOrderInformation orderInformation = new Ptsv2paymentsidrefundsOrderInformation();
		Ptsv2paymentsidcapturesOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("2325.00");
		orderInformationAmountDetails.currency("usd");
		orderInformationAmountDetails.serviceFeeAmount("30.0");
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


