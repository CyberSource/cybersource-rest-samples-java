package payments.allServices;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetAPayment {
	
	
	private static String getId="5265502011846829204101";
	private static String responseCode=null;
	private static String responseMsg=null;
	
	public static void main(String args[]) throws Exception {
		new GetAPayment();
	}

	public GetAPayment() throws Exception {
		process();
	} 
	
	public static void process() throws Exception {
	
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
