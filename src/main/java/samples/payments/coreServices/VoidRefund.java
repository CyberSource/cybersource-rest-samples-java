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

	private PtsV2PaymentsVoidsPost201Response response;
	private PtsV2PaymentsRefundPost201Response refundResponse;
	private Properties merchantProp;

	private VoidRefundRequest request;

	private VoidRefundRequest getRequest() {
		request = new VoidRefundRequest();

		Ptsv2paymentsidreversalsClientReferenceInformation client = new Ptsv2paymentsidreversalsClientReferenceInformation();
		client.code("test_refund_void");
		request.setClientReferenceInformation(client);

		return request;

	}

	public static void main(String args[]) throws Exception {
		VoidRefund voidRefund =new VoidRefund();
		voidRefund.process();
	}

	

	public void process() throws Exception {
		String className=VoidRefund.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = new ApiClient();
		try {
			request = getRequest();

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			RefundPayment refundPayment = new RefundPayment();
			refundResponse = refundPayment.process();
			
			if (refundResponse != null) {
				VoidApi voidApi = new VoidApi(merchantConfig);
				apiClient=Invokers.Configuration.getDefaultApiClient();
				response = voidApi.voidRefund(request, refundResponse.getId());
			}
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " +className+": "+apiClient.getRespBody()+"\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API REQUEST BODY:");
			System.out.println(apiClient.getRequestBody() + "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getRespBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE:" + className + "\n");
		}
	}

}
