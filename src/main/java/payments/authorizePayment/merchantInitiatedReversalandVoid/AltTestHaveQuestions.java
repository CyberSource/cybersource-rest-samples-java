package payments.authorizePayment.merchantInitiatedReversalandVoid;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsOrderInformation;
import Model.V2paymentsOrderInformationAmountDetails;
import Model.V2paymentsOrderInformationBillTo;
import Model.V2paymentsPaymentInformationCard;
import Model.V2paymentsRecipientInformation;

public class AltTestHaveQuestions {
	private String responseCode=null;
	private String responseMsg=null;
	
	CreatePaymentRequest request;
	
   private CreatePaymentRequest getRequest(){
		 request=new CreatePaymentRequest();
		
		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("TC50171_1");
		request.clientReferenceInformation(client);

		V2paymentsOrderInformationBillTo billTo = new V2paymentsOrderInformationBillTo();
		billTo.country("US");
		billTo.lastName("VDP");
		billTo.address1("201 S. Division St.");
		billTo.postalCode("48104-2201");
		billTo.locality("Ann Arbor");
		billTo.administrativeArea("MI");
		billTo.firstName("RTS");
		billTo.district("MI");
		billTo.buildingNumber("123");
		billTo.email("test@cybs.com");

		V2paymentsOrderInformationAmountDetails amountDetails = new V2paymentsOrderInformationAmountDetails();
		amountDetails.currency("USD");

		V2paymentsOrderInformation orderInformation = new V2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.orderInformation(orderInformation);
		
		V2paymentsPaymentInformationCard card = new V2paymentsPaymentInformationCard();
		card.expirationYear("2031");
		card.number("5555555555554444");
		card.securityCode("123");
		card.expirationMonth("12");
		card.type("002");

		V2paymentsRecipientInformation recipientInformation = new V2paymentsRecipientInformation();
		//misiing field in model
		//recipientInformation.card(card);
		request.recipientInformation(recipientInformation);
		
		//Reversal information field missing for the request model
		
		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new AltTestHaveQuestions();
	}

	public AltTestHaveQuestions() throws Exception {
		process();
	}
    
	private void process() throws Exception {
	
	try {
	    request=getRequest();
	    
		PaymentApi paymentApi=new PaymentApi();
		paymentApi.createPayment(request);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }

}
