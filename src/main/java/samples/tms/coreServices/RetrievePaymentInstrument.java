package samples.tms.coreServices;

import Api.PaymentInstrumentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse2016;

public class RetrievePaymentInstrument {
	private static String profileId="93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static String tokenId="786A6C043D7D9DBDE05340588D0A4847";
	private static  String responseCode=null;
	private static String status=null;
	static InlineResponse2016 response;
	
	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {
	
	try {
		
		PaymentInstrumentApi paymentInstrumentApi = new PaymentInstrumentApi();
		response=paymentInstrumentApi.paymentinstrumentsTokenIdGet(profileId, tokenId);
		
		responseCode=ApiClient.responseCode;
		status=ApiClient.status;
		
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("Status :" +status);
		System.out.println(response.getId());
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }

}
