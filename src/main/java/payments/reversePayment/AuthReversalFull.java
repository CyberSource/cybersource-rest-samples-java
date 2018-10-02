package payments.reversePayment;

import Api.ReversalApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.AuthReversalRequest;
import Model.V2paymentsidreversalsClientReferenceInformation;
import Model.V2paymentsidreversalsOrderInformation;
import Model.V2paymentsidreversalsReversalInformation;
import Model.V2paymentsidreversalsReversalInformationAmountDetails;

public class AuthReversalFull {
	
	private String getId="5265502011846829204101";
	private String responseCode=null;
	private String responseMsg=null;
	
	AuthReversalRequest request;
	
   private AuthReversalRequest getRequest(){
		 request=new AuthReversalRequest();
		
		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("TC50171_1");
		request.setClientReferenceInformation(client);
		
		V2paymentsidreversalsReversalInformationAmountDetails details=new V2paymentsidreversalsReversalInformationAmountDetails();
		details.currency("USD");
		
		//model to be fixed
		V2paymentsidreversalsOrderInformation orderInformation=new V2paymentsidreversalsOrderInformation();
		//orderInformation.
		request.setOrderInformation(orderInformation);

		V2paymentsidreversalsReversalInformationAmountDetails amountDetails = new V2paymentsidreversalsReversalInformationAmountDetails();
		amountDetails.totalAmount("100.00");
		
		V2paymentsidreversalsReversalInformation reversalInformation=new V2paymentsidreversalsReversalInformation();
		reversalInformation.setAmountDetails(amountDetails);
		
        request.setReversalInformation(reversalInformation);
		
		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new AuthReversalFull();
	}

	public AuthReversalFull() throws Exception {
		process();
	}
    
	private void process() throws Exception {
	
	try {
	    request=getRequest();
	     
	    ReversalApi reversalApi=new ReversalApi();
	    reversalApi.authReversal(getId, request);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }


}
