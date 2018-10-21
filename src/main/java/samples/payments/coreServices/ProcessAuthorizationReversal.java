package samples.payments.coreServices;

import Api.ReversalApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.AuthReversalRequest;
import Model.InlineResponse201;
import Model.InlineResponse2011;
import Model.V2paymentsidreversalsClientReferenceInformation;
import Model.V2paymentsidreversalsReversalInformation;
import Model.V2paymentsidreversalsReversalInformationAmountDetails;

public class ProcessAuthorizationReversal {

	private static String responseCode = null;
	private static String status = null;
	static InlineResponse2011 response;
	public static InlineResponse201 paymentResponse;

	static AuthReversalRequest request;

	private static AuthReversalRequest getRequest() {
		request = new AuthReversalRequest();

		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("test_reversal");
		request.setClientReferenceInformation(client);

		V2paymentsidreversalsReversalInformationAmountDetails amountDetails = new V2paymentsidreversalsReversalInformationAmountDetails();
		amountDetails.totalAmount("102.21");

		V2paymentsidreversalsReversalInformation reversalInformation = new V2paymentsidreversalsReversalInformation();
		reversalInformation.reason("testing");
		reversalInformation.setAmountDetails(amountDetails);

		request.setReversalInformation(reversalInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	
	private static void process() throws Exception {

		try {
			request = getRequest();

			ReversalApi reversalApi = new ReversalApi();
			
			paymentResponse=ProcessPayment.process(false);
			
			response = reversalApi.authReversal(paymentResponse.getId(), request);

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
