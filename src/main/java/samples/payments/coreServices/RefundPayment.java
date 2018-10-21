package samples.payments.coreServices;

import Api.RefundApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse201;
import Model.InlineResponse2013;
import Model.RefundPaymentRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsidcapturesOrderInformationAmountDetails;
import Model.V2paymentsidrefundsOrderInformation;

public class RefundPayment {
	private static String responseCode=null;
	private static String status=null;
	public static InlineResponse2013 response;
	public static InlineResponse201 paymentResponse;
	
	static RefundPaymentRequest request;
	
   private static RefundPaymentRequest getRequest(){
		 request=new RefundPaymentRequest();
		
		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("test_refund_payment");
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
		process();
	}
  
	public static InlineResponse2013 process() throws Exception {
	
	try {
	    request=getRequest();
	    RefundApi refundApi=new RefundApi();
	    
	     paymentResponse=ProcessPayment.process(true);
	    
		response=refundApi.refundPayment(request, paymentResponse.getId());
	    
		
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
