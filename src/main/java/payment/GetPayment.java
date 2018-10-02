package payment;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetPayment {
	
	
	private String getId="5265502011846829204101";
	private String responseCode=null;
	private String responseMsg=null;
	
	public static void main(String args[]) throws Exception {
		new GetPayment();
	}

	public GetPayment() throws Exception {
	
		process();
	}
    
	private void process() throws Exception {
	
	try {
		
		PaymentApi paymentApi=new PaymentApi();
		paymentApi.getPayment(getId);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }

}
