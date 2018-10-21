package samples.payments.coreServices;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse2012;
import Model.InlineResponse2015;
import Model.V2paymentsidreversalsClientReferenceInformation;
import Model.VoidCaptureRequest;

public class VoidCapture {

	private static String responseCode = null;
	private static String status = null;
	public static InlineResponse2015 response;
	public static InlineResponse2012 captureResponse;

	static VoidCaptureRequest request;

	private static VoidCaptureRequest getRequest() {
		request = new VoidCaptureRequest();

		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("test_capture_void");
		request.setClientReferenceInformation(client);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static void process() throws Exception {

		try {
			request = getRequest();

			VoidApi voidApi = new VoidApi();

			captureResponse = CapturePayment.process();

			response = voidApi.voidCapture(request, captureResponse.getId());

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
