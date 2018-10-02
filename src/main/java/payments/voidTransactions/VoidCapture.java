package payments.voidTransactions;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.VoidCaptureRequest;
import Model.V2paymentsidreversalsClientReferenceInformation;

public class VoidCapture {
	
	private String getId="5383789932916597603006";
	private String responseCode=null;
	private String responseMsg=null;
	
	VoidCaptureRequest request;
	
   private VoidCaptureRequest getRequest(){
		 request=new VoidCaptureRequest();
		
		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("test_void");
		request.setClientReferenceInformation(client);

		return request;
		
	}
	
	public static void main(String args[]) throws Exception {
		new VoidCapture();
	}

	public VoidCapture() throws Exception {
		process();
	}
    
	private void process() throws Exception {
	
	try {
	    request=getRequest();
	    
	    VoidApi voidApi=new VoidApi();
	    voidApi.voidCapture(request, getId);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }


}
