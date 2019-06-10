package samples.payments.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.VoidApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.PtsV2PaymentsPost201Response;
import Model.PtsV2PaymentsVoidsPost201Response;
import Model.Ptsv2paymentsidreversalsClientReferenceInformation;
import Model.VoidPaymentRequest;

public class VoidPayment {

	private static String responseCode = null;
	private static String status = null;
	public static PtsV2PaymentsVoidsPost201Response response;
	public static PtsV2PaymentsPost201Response paymentResponse;
	private static Properties merchantProp;

	static VoidPaymentRequest request;

	private static VoidPaymentRequest getRequest() {
		request = new VoidPaymentRequest();

		Ptsv2paymentsidreversalsClientReferenceInformation client = new Ptsv2paymentsidreversalsClientReferenceInformation();
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

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;
			
			paymentResponse = ProcessPayment.process(true);

			VoidApi voidApi = new VoidApi(apiClient);
			response = voidApi.voidPayment(request, paymentResponse.getId());

			responseCode = apiClient.responseCode;
			status = apiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
