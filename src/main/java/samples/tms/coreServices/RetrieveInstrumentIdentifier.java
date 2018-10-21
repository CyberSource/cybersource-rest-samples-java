package samples.tms.coreServices;

import Api.InstrumentIdentifierApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse2008;

public class RetrieveInstrumentIdentifier {
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static String tokenId = "7010000000004697654";
	private static String responseCode = null;
	private static String status = null;
	public static InlineResponse2008 response;
	
	public static void main(String args[]) throws Exception {
		process();
	}


	private static void process() throws Exception {

		try {

			InstrumentIdentifierApi instrumentIdentifierApi = new InstrumentIdentifierApi();
			response = instrumentIdentifierApi.instrumentidentifiersTokenIdPaymentinstrumentsGet(profileId, tokenId,
					null, null);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response.getCount());
			

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
