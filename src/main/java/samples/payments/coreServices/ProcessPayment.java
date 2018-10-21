package samples.payments.coreServices;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.InlineResponse201;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsOrderInformation;
import Model.V2paymentsOrderInformationAmountDetails;
import Model.V2paymentsOrderInformationBillTo;
import Model.V2paymentsPaymentInformation;
import Model.V2paymentsPaymentInformationCard;
import Model.V2paymentsPointOfSaleInformation;
import Model.V2paymentsProcessingInformation;

public class ProcessPayment {
	private static String responseCode = null;
	private static String status=null;
	static InlineResponse201 response;
	static boolean capture=false;

	private static CreatePaymentRequest request;

	private static CreatePaymentRequest getRequest(boolean capture) {
		request = new CreatePaymentRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("test_payment");
		request.clientReferenceInformation(client);

		V2paymentsPointOfSaleInformation saleInformation = new V2paymentsPointOfSaleInformation();
		saleInformation.cardPresent(false);
		saleInformation.catLevel(6);
		saleInformation.terminalCapability(4);
		request.pointOfSaleInformation(saleInformation);

		V2paymentsOrderInformationBillTo billTo = new V2paymentsOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
		billTo.locality("san francisco");
		billTo.administrativeArea("CA");
		billTo.email("test@cybs.com");
		

		V2paymentsOrderInformationAmountDetails amountDetails = new V2paymentsOrderInformationAmountDetails();
		amountDetails.totalAmount("100.00");
		amountDetails.currency("USD");

		V2paymentsOrderInformation orderInformation = new V2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);
		
		V2paymentsProcessingInformation processingInformation=new V2paymentsProcessingInformation();
		if(capture==true){
		processingInformation.capture(true);
		}
		request.processingInformation(processingInformation);

		V2paymentsPaymentInformationCard card = new V2paymentsPaymentInformationCard();
		card.expirationYear("2031");
		card.number("4111111111111111");
		card.securityCode("123");
		card.expirationMonth("12");

		V2paymentsPaymentInformation paymentInformation = new V2paymentsPaymentInformation();
		paymentInformation.card(card);
		request.setPaymentInformation(paymentInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process(capture);
	}

	
	public static  InlineResponse201 process(boolean check) throws Exception {

		try {
			capture=check;
			request = getRequest(capture);

			PaymentApi paymentApi = new PaymentApi();
			response=paymentApi.createPayment(request);

			responseCode=ApiClient.responseCode;
			status=ApiClient.status;
			
			System.out.println("ResponseCode :" +responseCode);
			System.out.println("Status :" +status);
			System.out.println(response.getId());

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
