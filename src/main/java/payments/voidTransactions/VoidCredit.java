package payments.voidTransactions;

import Api.VoidApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.VoidCreditRequest;
import Model.V2paymentsidreversalsClientReferenceInformation;

public class VoidCredit {

	private String getId = "5383790894716971503002";
	private String responseCode = null;
	private String responseMsg = null;

	VoidCreditRequest request;

	private VoidCreditRequest getRequest() {
		request = new VoidCreditRequest();

		V2paymentsidreversalsClientReferenceInformation client = new V2paymentsidreversalsClientReferenceInformation();
		client.code("test_void");
		request.setClientReferenceInformation(client);

		return request;

	}

	public static void main(String args[]) throws Exception {
		new VoidCredit();
	}

	public VoidCredit() throws Exception {
		process();
	}

	private void process() throws Exception {

		try {
			request = getRequest();

			VoidApi voidApi = new VoidApi();
			voidApi.voidCredit(request, getId);

			responseCode = ApiClient.resp;
			responseMsg = ApiClient.respmsg;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMsg);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
