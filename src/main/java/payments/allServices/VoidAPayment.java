package payments.allServices;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.V2paymentsidreversalsClientReferenceInformation;
import Model.VoidPaymentRequest;

public class VoidAPayment {

	private static String getId = "5265502011846829204101";
	private static String responseCode = null;
	private static String responseMsg = null;

	static VoidPaymentRequest request;

	private static VoidPaymentRequest getRequest() {
		request = new VoidPaymentRequest();

		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("1234567890");
		request.setClientReferenceInformation(client);

		// fields are missing in model

		return request;

	}
	
	public static void main(String args[]) throws Exception {
		new VoidAPayment();
	}

	public VoidAPayment() throws Exception {
		process();
	} 

	public static void process() throws Exception {

		try {
			request = getRequest();
			System.out.println(request);

			VoidApi voidApi = new VoidApi();
			voidApi.voidPayment(request, getId);

			responseCode = ApiClient.resp;
			responseMsg = ApiClient.respmsg;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMsg);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
