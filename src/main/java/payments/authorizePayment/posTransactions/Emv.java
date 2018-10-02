package payments.authorizePayment.posTransactions;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsConsumerAuthenticationInformation;
import Model.V2paymentsOrderInformation;
import Model.V2paymentsOrderInformationAmountDetails;
import Model.V2paymentsOrderInformationBillTo;
import Model.V2paymentsPaymentInformation;
import Model.V2paymentsPaymentInformationCard;
import Model.V2paymentsPaymentInformationFluidData;
import Model.V2paymentsPointOfSaleInformation;
import Model.V2paymentsPointOfSaleInformationEmv;

public class Emv {
	private String responseCode=null;
	private String responseMsg=null;
	
	CreatePaymentRequest request;
	
   private CreatePaymentRequest getRequest(){
		 request=new CreatePaymentRequest();
		
		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("123456");
		request.clientReferenceInformation(client);
		
		V2paymentsPointOfSaleInformationEmv emv=new V2paymentsPointOfSaleInformationEmv();
		//Model to be fixed
		//emv.fallbackCondition("swiped");
		emv.fallback(true);
		
		V2paymentsPointOfSaleInformation pointOfSaleInformation = new V2paymentsPointOfSaleInformation();
		pointOfSaleInformation.cardPresent(true);
		pointOfSaleInformation.catLevel(2);
		pointOfSaleInformation.emv(emv);
		pointOfSaleInformation.entryMode("contact");
		pointOfSaleInformation.terminalCapability(1);
		request.pointOfSaleInformation(pointOfSaleInformation);

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
		amountDetails.totalAmount("100.00");
		amountDetails.currency("usd");

		V2paymentsOrderInformation orderInformation = new V2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);
		
		V2paymentsPaymentInformationCard card = new V2paymentsPaymentInformationCard();
		card.expirationYear("2031");
		card.number("372425119311008");
		card.securityCode("123");
		card.expirationMonth("12");
		
		V2paymentsPaymentInformationFluidData fluidData = new V2paymentsPaymentInformationFluidData();
		fluidData.value("%B373235387881007^SMITH/JOHN         ^31121019761100      00868000000?;373235387881007=31121019761186800000?");
		
		V2paymentsPaymentInformation paymentInformation = new V2paymentsPaymentInformation();
		paymentInformation.card(card);
		paymentInformation.fluidData(fluidData);
		request.setPaymentInformation(paymentInformation);
		
		V2paymentsConsumerAuthenticationInformation consumerAuthenticationInformation=new V2paymentsConsumerAuthenticationInformation();
		consumerAuthenticationInformation.cavv("AAABCSIIAAAAAAACcwgAEMCoNh+=");
		consumerAuthenticationInformation.xid("T1Y0OVcxMVJJdkI0WFlBcXptUzE=");
		request.consumerAuthenticationInformation(consumerAuthenticationInformation);
		
		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new Emv();
	}

	public Emv() throws Exception {
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
