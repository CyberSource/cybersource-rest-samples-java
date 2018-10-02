package payments.voidTransactions.VoidAPayment;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.VoidPaymentRequest;
import Model.V2paymentsidreversalsClientReferenceInformation;

public class VoidAPayment {
	
	private String getId="5265502011846829204101";
	private String responseCode=null;
	private String responseMsg=null;
	
	VoidPaymentRequest request;
	
   private VoidPaymentRequest getRequest(){
		 request=new VoidPaymentRequest();
		
		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("1234567890");
		request.setClientReferenceInformation(client);
		
		//model need to be fixed to set other fields,missing

		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new VoidAPayment();
	}

	public VoidAPayment() throws Exception {
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
