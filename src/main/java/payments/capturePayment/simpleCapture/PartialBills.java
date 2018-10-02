package payments.capturePayment.simpleCapture;

import Api.CaptureApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CapturePaymentRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsidcapturesOrderInformation;
import Model.V2paymentsidcapturesOrderInformationAmountDetails;
import Model.V2paymentsidcapturesOrderInformationBillTo;
import Model.V2paymentsidcapturesPointOfSaleInformation;

public class PartialBills {
	
	private String getId="5336362702096794603526";
	private String responseCode=null;
	private String responseMsg=null;
	
	CapturePaymentRequest request;
	
   private CapturePaymentRequest getRequest(){
		 request=new CapturePaymentRequest();
		
		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("1234567890");
		request.setClientReferenceInformation(client);
		
		V2paymentsidcapturesPointOfSaleInformation saleInformation=new V2paymentsidcapturesPointOfSaleInformation();
		//model to be fixed
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
		amountDetails.totalAmount("10.00");
		amountDetails.currency("USD");

		V2paymentsidcapturesOrderInformation orderInformation = new V2paymentsidcapturesOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);
		
		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new PartialBills();
	}

	public PartialBills() throws Exception {
		process();
	}
    
	private void process() throws Exception {
	
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
