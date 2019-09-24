package samples.Payouts;
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

public class ProcessPayoutToken{
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

/*
	public static void main(String args[]) throws Exception 
	{
		// Accept required parameters from args[] and pass to run.
		run();
	}
*/
	public static PtsV2PayoutsPost201Response run(){
	
		OctCreatePaymentRequest requestObj = new OctCreatePaymentRequest();

		Ptsv2payoutsClientReferenceInformation clientReferenceInformation = new Ptsv2payoutsClientReferenceInformation();
		clientReferenceInformation.code("111111113");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2payoutsOrderInformation orderInformation = new Ptsv2payoutsOrderInformation();
		Ptsv2payoutsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2payoutsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("111.00");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		Ptsv2payoutsMerchantInformation merchantInformation = new Ptsv2payoutsMerchantInformation();
		Ptsv2payoutsMerchantInformationMerchantDescriptor merchantInformationMerchantDescriptor = new Ptsv2payoutsMerchantInformationMerchantDescriptor();
		merchantInformationMerchantDescriptor.name("Sending Company Name");
		merchantInformationMerchantDescriptor.locality("FC");
		merchantInformationMerchantDescriptor.country("US");
		merchantInformationMerchantDescriptor.administrativeArea("CA");
		merchantInformationMerchantDescriptor.postalCode("94440");
		merchantInformation.merchantDescriptor(merchantInformationMerchantDescriptor);

		requestObj.merchantInformation(merchantInformation);

		Ptsv2payoutsRecipientInformation recipientInformation = new Ptsv2payoutsRecipientInformation();
		recipientInformation.firstName("John");
		recipientInformation.lastName("Doe");
		recipientInformation.address1("Paseo Padre Boulevard");
		recipientInformation.locality("Foster City");
		recipientInformation.administrativeArea("CA");
		recipientInformation.country("US");
		recipientInformation.postalCode("94400");
		recipientInformation.phoneNumber("6504320556");
		requestObj.recipientInformation(recipientInformation);

		Ptsv2payoutsSenderInformation senderInformation = new Ptsv2payoutsSenderInformation();
		senderInformation.referenceNumber("1234567890");
		Ptsv2payoutsSenderInformationAccount senderInformationAccount = new Ptsv2payoutsSenderInformationAccount();
		senderInformationAccount.fundsSource("05");
		senderInformationAccount.number("1234567890123456789012345678901234");
		senderInformation.account(senderInformationAccount);

		senderInformation.name("Company Name");
		senderInformation.address1("900 Metro Center Blvd.900");
		senderInformation.locality("Foster City");
		senderInformation.administrativeArea("CA");
		senderInformation.countryCode("US");
		requestObj.senderInformation(senderInformation);

		Ptsv2payoutsProcessingInformation processingInformation = new Ptsv2payoutsProcessingInformation();
		processingInformation.businessApplicationId("FD");
		processingInformation.networkRoutingOrder("V8");
		processingInformation.commerceIndicator("internet");
		requestObj.processingInformation(processingInformation);

		Ptsv2payoutsPaymentInformation paymentInformation = new Ptsv2payoutsPaymentInformation();
		Ptsv2paymentsPaymentInformationCustomer paymentInformationCustomer = new Ptsv2paymentsPaymentInformationCustomer();
		paymentInformationCustomer.customerId("7500BB199B4270EFE05340588D0AFCAD");
		paymentInformation.customer(paymentInformationCustomer);

		requestObj.paymentInformation(paymentInformation);

		PtsV2PayoutsPost201Response result = null;
		try
		{
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PayoutsApi apiInstance = new PayoutsApi(apiClient);
			result = apiInstance.octCreatePayment(requestObj);

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
