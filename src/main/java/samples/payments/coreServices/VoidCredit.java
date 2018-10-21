package samples.payments.coreServices;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse2014;
import Model.InlineResponse2015;
import Model.V2paymentsidreversalsClientReferenceInformation;
import Model.VoidCreditRequest;

public class VoidCredit {

	private static String responseCode = null;
	private static String status = null;
	public static InlineResponse2015 response;
	public static InlineResponse2014 creditResponse;

	static VoidCreditRequest request;

	private static VoidCreditRequest getRequest() {
		request = new VoidCreditRequest();

		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("test_credit_void");
		request.setClientReferenceInformation(client);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			request = getRequest();

			VoidApi voidApi = new VoidApi();

			creditResponse=ProcessCredit.process();

			response = voidApi.voidCredit(request, creditResponse.getId());

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response.getId());

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
