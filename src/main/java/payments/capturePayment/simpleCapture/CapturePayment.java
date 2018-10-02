package payments.capturePayment.simpleCapture;

import Api.CaptureApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CapturePaymentRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsidcapturesOrderInformation;
import Model.V2paymentsidcapturesOrderInformationAmountDetails;

public class CapturePayment {
	
	private String getId="5336362702096794603526";
	private String responseCode=null;
	private String responseMsg=null;
	
	CapturePaymentRequest request;
	
   private CapturePaymentRequest getRequest(){
		 request=new CapturePaymentRequest();
		
		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("TC50171_3");
		request.setClientReferenceInformation(client);

		V2paymentsidcapturesOrderInformationAmountDetails amountDetails = new V2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("102.21");
		amountDetails.currency("USD");
		
		V2paymentsidcapturesOrderInformation orderInformation=new V2paymentsidcapturesOrderInformation();
        orderInformation.setAmountDetails(amountDetails);
		
        request.setOrderInformation(orderInformation);
		
		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new CapturePayment();
	}

	public CapturePayment() throws Exception {
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
