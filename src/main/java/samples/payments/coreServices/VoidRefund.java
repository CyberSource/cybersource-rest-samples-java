package samples.payments.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.VoidApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.PtsV2PaymentsRefundPost201Response;
import Model.PtsV2PaymentsVoidsPost201Response;
import Model.Ptsv2paymentsidreversalsClientReferenceInformation;
import Model.VoidRefundRequest;

public class VoidRefund {

	private String responseCode = null;
	private String status = null;
	static PtsV2PaymentsVoidsPost201Response response;
	public static PtsV2PaymentsRefundPost201Response refundResponse;
	private static Properties merchantProp;

	VoidRefundRequest request;

	private VoidRefundRequest getRequest() {
		request = new VoidRefundRequest();

		Ptsv2paymentsidreversalsClientReferenceInformation client = new Ptsv2paymentsidreversalsClientReferenceInformation();
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

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient=new ApiClient(merchantConfig);

			refundResponse = RefundPayment.process();
			
			VoidApi voidApi = new VoidApi();
			response = voidApi.voidRefund(request, refundResponse.getId());

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
