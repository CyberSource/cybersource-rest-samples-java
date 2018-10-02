package payments.refundPayment;

import Api.RefundApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.RefundCaptureRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsidcapturesOrderInformationAmountDetails;
import Model.V2paymentsidrefundsOrderInformation;

public class RefundCapture {
	private String getId="5335508882306969603526";
	private String responseCode=null;
	private String responseMsg=null;
	
	RefundCaptureRequest request;
	
   private RefundCaptureRequest getRequest(){
		 request=new RefundCaptureRequest();
		
		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("Testing-VDP-Payments-Refund");
		request.setClientReferenceInformation(client);
		
		V2paymentsidcapturesOrderInformationAmountDetails amountDetails = new V2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("102.21");
		amountDetails.currency("USD");

		V2paymentsidrefundsOrderInformation orderInformation=new V2paymentsidrefundsOrderInformation();
         orderInformation.amountDetails(amountDetails);
        request.setOrderInformation(orderInformation);
		
		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new RefundCapture();
	}

	public RefundCapture() throws Exception {
		process();
	}
    
	private void process() throws Exception {
	
	try {
	    request=getRequest();
	    RefundApi refundApi=new RefundApi();
		refundApi.refundCapture(request, getId);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }

}
