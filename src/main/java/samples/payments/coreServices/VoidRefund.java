package samples.payments.coreServices;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse2013;
import Model.InlineResponse2015;
import Model.V2paymentsidreversalsClientReferenceInformation;
import Model.VoidRefundRequest;

public class VoidRefund {

	private String responseCode = null;
	private String status=null;
	static InlineResponse2015 response;
	public static InlineResponse2013 refundResponse;

	VoidRefundRequest request;

	private VoidRefundRequest getRequest() {
		request = new VoidRefundRequest();

		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("test_refund_void");
		request.setClientReferenceInformation(client);

		return request;

	}

	public static void main(String args[]) throws Exception {
		new VoidRefund();
	}

	public VoidRefund() throws Exception {
		process();
	}

	private void process() throws Exception {

		try {
			request = getRequest();

			VoidApi voidApi = new VoidApi();
			
			refundResponse=RefundPayment.process();
			
			response=voidApi.voidRefund(request, refundResponse.getId());

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
