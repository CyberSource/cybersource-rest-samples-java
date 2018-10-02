package payouts;

import Api.DefaultApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse201ClientReferenceInformation;
import Model.OctCreatePaymentRequest;
import Model.V2payoutsMerchantInformation;
import Model.V2payoutsMerchantInformationMerchantDescriptor;
import Model.V2payoutsOrderInformation;
import Model.V2payoutsOrderInformationAmountDetails;
import Model.V2payoutsPaymentInformation;
import Model.V2payoutsPaymentInformationCard;
import Model.V2payoutsProcessingInformation;
import Model.V2payoutsRecipientInformation;
import Model.V2payoutsSenderInformation;
import Model.V2payoutsSenderInformationAccount;

public class Payout {
	
	private String responseCode=null;
	private String responseMsg=null;
	
	OctCreatePaymentRequest request;
	
   private OctCreatePaymentRequest getRequest(){
		 request=new OctCreatePaymentRequest();
		
		 InlineResponse201ClientReferenceInformation client = new InlineResponse201ClientReferenceInformation();
		client.code("1234567890");
		request.clientReferenceInformation(client);
		
		V2payoutsSenderInformationAccount account=new V2payoutsSenderInformationAccount();
		account.fundsSource("05");
		
		V2payoutsSenderInformation senderInformation = new V2payoutsSenderInformation();
		senderInformation.referenceNumber("1234567890");
		senderInformation.address1("900 Metro Center Blvd.900");
		senderInformation.countryCode("US");
		senderInformation.locality("Foster City");
		senderInformation.name("Company Name");
		senderInformation.administrativeArea("CA");
		request.senderInformation(senderInformation);
		
		V2payoutsProcessingInformation processingInformation = new V2payoutsProcessingInformation();
		processingInformation.commerceIndicator("internet");
		processingInformation.businessApplicationId("FD");
		request.processingInformation(processingInformation);
		
		V2payoutsOrderInformationAmountDetails amountDetails = new V2payoutsOrderInformationAmountDetails();
		amountDetails.totalAmount("100.00");
		amountDetails.currency("USD");
		
		V2payoutsOrderInformation orderInformation = new V2payoutsOrderInformation();
		orderInformation.amountDetails(amountDetails);
		request.orderInformation(orderInformation);
		
		V2payoutsMerchantInformationMerchantDescriptor merchantDescriptor=new V2payoutsMerchantInformationMerchantDescriptor();
		merchantDescriptor.country("US");
		merchantDescriptor.postalCode("94440");
		merchantDescriptor.locality("FC");
		merchantDescriptor.name("Sending Company Name");
		merchantDescriptor.administrativeArea("CA");
		
		V2payoutsMerchantInformation merchantInformation = new V2payoutsMerchantInformation();
		merchantInformation.merchantDescriptor(merchantDescriptor);
		request.merchantInformation(merchantInformation);
		
		V2payoutsPaymentInformationCard card=new V2payoutsPaymentInformationCard();
		card.expirationYear("2025");
		card.number("4111111111111111");
		card.expirationMonth("12");
		card.type("001");
		
		V2payoutsPaymentInformation paymentInformation = new V2payoutsPaymentInformation();
		paymentInformation.card(card);
		request.paymentInformation(paymentInformation);
		
		V2payoutsRecipientInformation recipientInformation = new V2payoutsRecipientInformation();
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
		new Payout();
	}

	public Payout() throws Exception {
		process();
	}
    
	private void process() throws Exception {
	
	try {
	    request=getRequest();
	   
	    DefaultApi defaultApi=new DefaultApi();
	    defaultApi.octCreatePayment(request);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }


}
