package payments.capturePayment.simpleCapture;

import Api.CaptureApi;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetCapture {
	private String getId="5289697403596987704107";
	private String responseCode=null;
	private String responseMsg=null;
	
	public static void main(String args[]) throws Exception {
		new GetCapture();
	}

	public GetCapture() throws Exception {
	
		process();
	}
    
	private void process() throws Exception {
	
	try {
		
		CaptureApi captureApi=new CaptureApi();
		captureApi.getCapture(getId);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }

}
