package payments.voidTransactions;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.VoidPaymentRequest;
import Model.V2paymentsidreversalsClientReferenceInformation;

public class VoidPayment {

	private String getId = "5383792176196541703005";
	private String responseCode = null;
	private String responseMsg = null;

	VoidPaymentRequest request;

	private VoidPaymentRequest getRequest() {
		request = new VoidPaymentRequest();

		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("test_void");
		request.setClientReferenceInformation(client);

		return request;

	}

	public static void main(String args[]) throws Exception {
		new VoidPayment();
	}

	public VoidPayment() throws Exception {
		process();
	}

	private void process() throws Exception {

		try {
			request = getRequest();

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
