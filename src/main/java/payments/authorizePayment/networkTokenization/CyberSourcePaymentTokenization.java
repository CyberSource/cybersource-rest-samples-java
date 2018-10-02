package payments.authorizePayment.networkTokenization;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsOrderInformation;
import Model.V2paymentsOrderInformationAmountDetails;
import Model.V2paymentsOrderInformationBillTo;
import Model.V2paymentsPaymentInformation;
import Model.V2paymentsPaymentInformationFluidData;
import Model.V2paymentsPaymentInformationTokenizedCard;
import Model.V2paymentsProcessingInformation;

public class CyberSourcePaymentTokenization {
	private String responseCode=null;
	private String responseMsg=null;
	
	CreatePaymentRequest request;
	
   private CreatePaymentRequest getRequest(){
		 request=new CreatePaymentRequest();
		
		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("TC_MPOS_Paymentech_3");
		request.clientReferenceInformation(client);

		V2paymentsProcessingInformation processingInformation = new V2paymentsProcessingInformation();
		processingInformation.commerceIndicator("internet");
		request.setProcessingInformation(processingInformation);
		
		V2paymentsOrderInformationBillTo billTo = new V2paymentsOrderInformationBillTo();
		billTo.country("US");
		billTo.lastName("VDP");
		billTo.address2("Address 2");
		billTo.address1("201 S. Division St.");
		billTo.postalCode("48104-2201");
		billTo.locality("Ann Arbor");
		billTo.administrativeArea("MI");
		billTo.firstName("RTS");
		billTo.phoneNumber("999999999");
		billTo.district("MI");
		billTo.buildingNumber("123");
		billTo.company("Visa");
		billTo.email("test@cybs.com");

		V2paymentsOrderInformationAmountDetails amountDetails = new V2paymentsOrderInformationAmountDetails();
		amountDetails.totalAmount("2719");
		amountDetails.currency("USD");

		V2paymentsOrderInformation orderInformation = new V2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);
		
		V2paymentsPaymentInformationTokenizedCard tokenizedCard = new V2paymentsPaymentInformationTokenizedCard();
		tokenizedCard.expirationYear("2031");
		tokenizedCard.number("4111111111111111");
		tokenizedCard.expirationMonth("12");
		tokenizedCard.type("002");
		
		V2paymentsPaymentInformationFluidData fluidData = new V2paymentsPaymentInformationFluidData();
		fluidData.value("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");

		V2paymentsPaymentInformation paymentInformation = new V2paymentsPaymentInformation();
		paymentInformation.tokenizedCard(tokenizedCard);
		paymentInformation.fluidData(fluidData);
		request.setPaymentInformation(paymentInformation);
		
		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new CyberSourcePaymentTokenization();
	}

	public CyberSourcePaymentTokenization() throws Exception {
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
