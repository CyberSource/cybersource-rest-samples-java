package payments.voidTransactions;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.VoidRefundRequest;
import Model.V2paymentsidreversalsClientReferenceInformation;

public class VoidRefund {

	private String getId = "5383793515666599103006";
	private String responseCode = null;
	private String responseMsg = null;

	VoidRefundRequest request;

	private VoidRefundRequest getRequest() {
		request = new VoidRefundRequest();

		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("test_void");
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
			voidApi.voidRefund(request, getId);

			responseCode = ApiClient.resp;
			responseMsg = ApiClient.respmsg;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMsg);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
