package tms;

import Api.PaymentInstrumentApi;
import Invokers.ApiClient;
import Invokers.ApiException;

public class RetrievePaymentIdentifiers {
	private String profileId="93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private String tokenId="76C16E5FCB608FEAE05340588D0ADAB1";
	private String responseCode=null;
	private String responseMsg=null;
	
	public static void main(String args[]) throws Exception {
		new RetrievePaymentIdentifiers();
	}

	public RetrievePaymentIdentifiers() throws Exception {
	
		process();
	}
    
	private void process() throws Exception {
	
	try {
		
		PaymentInstrumentApi paymentInstrumentApi = new PaymentInstrumentApi();
		paymentInstrumentApi.paymentinstrumentsTokenIdGet(profileId, tokenId);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }

}
