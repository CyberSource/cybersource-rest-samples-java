package payments.authorizePayment.merchantInitiatedReversalandVoid;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.V2paymentsAggregatorInformation;
import Model.V2paymentsAggregatorInformationSubMerchant;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsOrderInformation;
import Model.V2paymentsOrderInformationAmountDetails;
import Model.V2paymentsOrderInformationBillTo;
import Model.V2paymentsPaymentInformation;
import Model.V2paymentsPaymentInformationCard;
import Model.V2paymentsPointOfSaleInformation;
import Model.V2paymentsPointOfSaleInformationEmv;

public class OriginaRequestAuth {
	private String responseCode=null;
	private String responseMsg=null;
	
	CreatePaymentRequest request;
	
   private CreatePaymentRequest getRequest(){
		 request=new CreatePaymentRequest();
		
		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("TC50171_1");
		request.clientReferenceInformation(client);
		
		V2paymentsPointOfSaleInformationEmv emv=new V2paymentsPointOfSaleInformationEmv();
		emv.cardSequenceNumber("c");
		emv.fallback(false);
		emv.tags("9F2608EF7753429A5D16B19F100706010A03A0000095058000040000");
		
		V2paymentsPointOfSaleInformation pointOfSaleInformation = new V2paymentsPointOfSaleInformation();
		pointOfSaleInformation.cardPresent(true);
		pointOfSaleInformation.emv(emv);
		pointOfSaleInformation.entryMode("contact");
		pointOfSaleInformation.terminalCapability(4);
		request.pointOfSaleInformation(pointOfSaleInformation);
		
		V2paymentsAggregatorInformationSubMerchant subMerchant = new V2paymentsAggregatorInformationSubMerchant();
		subMerchant.cardAcceptorId("1234567890");
		subMerchant.country("US");
		subMerchant.phoneNumber("650-432-0000");
		subMerchant.address1("900 Metro Center");
		subMerchant.postalCode("94404-2775");
		subMerchant.locality("Foster City");
		subMerchant.name("Visa Inc");
		subMerchant.administrativeArea("CA");
		subMerchant.region("PEN");
		subMerchant.email("test@cybs.com");
		
		V2paymentsAggregatorInformation aggregatorInformation = new V2paymentsAggregatorInformation();
		aggregatorInformation.subMerchant(subMerchant);
		aggregatorInformation.name("V-Internatio");
		aggregatorInformation.aggregatorId("123456789");
		request.setAggregatorInformation(aggregatorInformation);

		V2paymentsOrderInformationBillTo billTo = new V2paymentsOrderInformationBillTo();
		billTo.country("US");
		billTo.lastName("VDP");
		billTo.company("Visa");
		billTo.address1("201 S. Division St.");
		billTo.address2("Address 2");
		billTo.postalCode("48104-2201");
		billTo.locality("Ann Arbor");
		billTo.administrativeArea("MI");
		billTo.firstName("RTS");
		billTo.district("MI");
		billTo.buildingNumber("123");
		billTo.email("test@cybs.com");
		billTo.phoneNumber("999999999");

		V2paymentsOrderInformationAmountDetails amountDetails = new V2paymentsOrderInformationAmountDetails();
		amountDetails.totalAmount("3000.00");
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
		
		V2paymentsPaymentInformation paymentInformation=new V2paymentsPaymentInformation();
		paymentInformation.card(card);
		request.paymentInformation(paymentInformation);

		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new OriginaRequestAuth();
	}

	public OriginaRequestAuth() throws Exception {
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
