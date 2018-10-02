package payments.authorizePayment.merchantInitiatedReversalandVoid;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.VoidPaymentRequest;
import Model.V2paymentsidreversalsClientReferenceInformation;

public class Void {
	
	private String getId="5265502011846829204101";
	private String responseCode=null;
	private String responseMsg=null;
	
	VoidPaymentRequest request;
	
   private VoidPaymentRequest getRequest(){
		 request=new VoidPaymentRequest();
		
		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("TC50171_1");
		request.setClientReferenceInformation(client);

		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new Void();
	}

	public Void() throws Exception {
		process();
	}
    
	private void process() throws Exception {
	
	try {
	    request=getRequest();
	    
	    VoidApi voidApi=new VoidApi();
	    voidApi.voidPayment(request, getId);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }


}
