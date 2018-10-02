package tms;

import Api.InstrumentIdentifierApi;
import Invokers.ApiClient;
import Invokers.ApiException;

public class RetrieveAllPaymentsIdentifierForGiveInstrumentIdentifier {
	private String profileId="93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private String responseCode=null;
	private String responseMsg=null;
	private String tokenId="7020000000000137654";
	
	public static void main(String args[]) throws Exception {
		new RetrieveAllPaymentsIdentifierForGiveInstrumentIdentifier();
	}

	public RetrieveAllPaymentsIdentifierForGiveInstrumentIdentifier() throws Exception {
	
		process();
	}
    
	private void process() throws Exception {
	
	try {
		
		InstrumentIdentifierApi instrumentIdentifierApi = new InstrumentIdentifierApi();
		instrumentIdentifierApi.instrumentidentifiersTokenIdPaymentinstrumentsGet(profileId, tokenId, null,null);
		
		responseCode=ApiClient.resp;
		responseMsg=ApiClient.respmsg;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +responseMsg);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }

}
