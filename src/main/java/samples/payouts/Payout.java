package samples.payouts;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ProcessAPayoutApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.PtsV2PaymentsPost201ResponseClientReferenceInformation;
import Model.PtsV2PayoutsPostResponse;
import Model.Ptsv2payoutsMerchantInformation;
import Model.Ptsv2payoutsMerchantInformationMerchantDescriptor;
import Model.Ptsv2payoutsOrderInformation;
import Model.Ptsv2payoutsOrderInformationAmountDetails;
import Model.Ptsv2payoutsPaymentInformation;
import Model.Ptsv2payoutsPaymentInformationCard;
import Model.Ptsv2payoutsProcessingInformation;
import Model.Ptsv2payoutsRecipientInformation;
import Model.Ptsv2payoutsSenderInformation;
import Model.Ptsv2payoutsSenderInformationAccount;

public class Payout {
	
	private  Properties merchantProp;
	
	private PtsV2PayoutsPostResponse request;
	
    private  PtsV2PayoutsPostResponse getRequest(){
		 request=new PtsV2PayoutsPostResponse();
		
		 PtsV2PaymentsPost201ResponseClientReferenceInformation client = new PtsV2PaymentsPost201ResponseClientReferenceInformation();
		client.code("1234567890");
		request.clientReferenceInformation(client);
		
		Ptsv2payoutsSenderInformationAccount account=new Ptsv2payoutsSenderInformationAccount();
		account.fundsSource("05");
		
		Ptsv2payoutsSenderInformation senderInformation = new Ptsv2payoutsSenderInformation();
		senderInformation.referenceNumber("1234567890");
		senderInformation.address1("900 Metro Center Blvd.900");
		senderInformation.countryCode("US");
		senderInformation.locality("Foster City");
		senderInformation.name("Company Name");
		senderInformation.administrativeArea("CA");
		request.senderInformation(senderInformation);
		
		Ptsv2payoutsProcessingInformation processingInformation = new Ptsv2payoutsProcessingInformation();
		processingInformation.commerceIndicator("internet");
		processingInformation.businessApplicationId("FD");
		request.processingInformation(processingInformation);
		
		Ptsv2payoutsOrderInformationAmountDetails amountDetails = new Ptsv2payoutsOrderInformationAmountDetails();
		amountDetails.totalAmount("100.00");
		amountDetails.currency("USD");
		
		Ptsv2payoutsOrderInformation orderInformation = new Ptsv2payoutsOrderInformation();
		orderInformation.amountDetails(amountDetails);
		request.orderInformation(orderInformation);
		
		Ptsv2payoutsMerchantInformationMerchantDescriptor merchantDescriptor=new Ptsv2payoutsMerchantInformationMerchantDescriptor();
		merchantDescriptor.country("US");
		merchantDescriptor.postalCode("94440");
		merchantDescriptor.locality("FC");
		merchantDescriptor.name("Sending Company Name");
		merchantDescriptor.administrativeArea("CA");
		
		Ptsv2payoutsMerchantInformation merchantInformation = new Ptsv2payoutsMerchantInformation();
		merchantInformation.merchantDescriptor(merchantDescriptor);
		request.merchantInformation(merchantInformation);
		
		Ptsv2payoutsPaymentInformationCard card=new Ptsv2payoutsPaymentInformationCard();
		card.expirationYear("2025");
		card.number("4111111111111111");
		card.expirationMonth("12");
		card.type("001");
		
		Ptsv2payoutsPaymentInformation paymentInformation = new Ptsv2payoutsPaymentInformation();
		paymentInformation.card(card);
		request.paymentInformation(paymentInformation);
		
		Ptsv2payoutsRecipientInformation recipientInformation = new Ptsv2payoutsRecipientInformation();
		recipientInformation.firstName("John");
		recipientInformation.lastName("Deo");
		recipientInformation.address1("Paseo Padre Boulevard");
		recipientInformation.locality("Foster City");
		recipientInformation.administrativeArea("CA");
		recipientInformation.postalCode("94400");
		recipientInformation.phoneNumber("6504320556");
		recipientInformation.country("US");
		request.recipientInformation(recipientInformation);

		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		Payout payout = new Payout();
		payout.process();
	}
    
	private  void process() throws Exception {
		String className=Payout.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient=null;
		try {
			request = getRequest();

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ProcessAPayoutApi defaultApi = new ProcessAPayoutApi(merchantConfig);
			apiClient = Invokers.Configuration.getDefaultApiClient();
			defaultApi.octCreatePayment(request);
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " + className + ": " + apiClient.getRespBody() + "\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API REQUEST BODY:");
			System.out.println(apiClient.getRequestBody() + "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getRespBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE:" + className + "\n");
	}
  }


}
