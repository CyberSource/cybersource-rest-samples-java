package samples.payments.coreServices;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse201;
import Model.InlineResponse2015;
import Model.V2paymentsidreversalsClientReferenceInformation;
import Model.VoidPaymentRequest;

public class VoidPayment {

	private static  String responseCode = null;
	private static String status = null;
	public static InlineResponse2015 response;
	public static InlineResponse201 paymentResponse;

	static VoidPaymentRequest request;

	private static VoidPaymentRequest getRequest() {
		request = new VoidPaymentRequest();

		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("test_payment_void");
		request.setClientReferenceInformation(client);

		return request;

	}

	public static void main(String args[]) throws Exception {
		new VoidPayment();
	}

	public VoidPayment() throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			request = getRequest();

			VoidApi voidApi = new VoidApi();

			paymentResponse = ProcessPayment.process(true);
			response = voidApi.voidPayment(request, paymentResponse.getId());

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
