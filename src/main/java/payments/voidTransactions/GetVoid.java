package payments.voidTransactions;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetVoid {

	private String getId="5335528892726038303523";
	private String responseCode=null;
	private String responseMsg=null;
	
	public static void main(String args[]) throws Exception {
		new GetVoid();
	}

	public GetVoid() throws Exception {
	
		process();
	}
    
	private void process() throws Exception {
	
	try {
		
		VoidApi voidApi=new VoidApi();
		voidApi.getVoid(getId);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }
}
