package payments.allServices;

import Api.CaptureApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CapturePaymentRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsidcapturesOrderInformation;
import Model.V2paymentsidcapturesOrderInformationAmountDetails;
import Model.V2paymentsidcapturesOrderInformationBillTo;
import Model.V2paymentsidcapturesPointOfSaleInformation;

public class CapturePayment {
	
	private static String getId="5380315449156459803004";
	private static String responseCode=null;
	private static String responseMsg=null;
	
	static CapturePaymentRequest request;
	
   private static CapturePaymentRequest getRequest(){
		 request=new CapturePaymentRequest();
		
		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("TC50171_3");
		request.setClientReferenceInformation(client);
		

		V2paymentsidcapturesPointOfSaleInformation saleInformation=new V2paymentsidcapturesPointOfSaleInformation();
		//model to be fixed,fields are missing
		
		/*saleInformation.cardPresent(false);
		BigDecimal bd = new BigDecimal(6);
		saleInformation.catLevel(bd);
		BigDecimal tc = new BigDecimal(4);
		saleInformation.terminalCapability(tc);*/
		request.pointOfSaleInformation(saleInformation);
		
		V2paymentsidcapturesOrderInformationBillTo billTo = new V2paymentsidcapturesOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("RTS");
		billTo.lastName("VDP");
		billTo.address1("901 Metro Center Blvd");
		billTo.postalCode("40500");
		billTo.locality("Foster City");
		billTo.administrativeArea("CA");
		billTo.email("test@cybs.com");
		

		V2paymentsidcapturesOrderInformationAmountDetails amountDetails = new V2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("100.00");
		amountDetails.currency("USD");

		V2paymentsidcapturesOrderInformation orderInformation = new V2paymentsidcapturesOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);
	
		return request;
		
	}
   public static void main(String args[]) throws Exception {
		new CapturePayment();
	}

	public CapturePayment() throws Exception {
		process();
	} 
	
	public static void process() throws Exception {
	
	try {
	    request=getRequest();
	    CaptureApi captureApi=new CaptureApi();
		captureApi.capturePayment(request, getId);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }


}
