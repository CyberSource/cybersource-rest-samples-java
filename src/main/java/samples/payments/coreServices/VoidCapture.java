package samples.payments.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.VoidApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.PtsV2PaymentsCapturesPost201Response;
import Model.PtsV2PaymentsVoidsPost201Response;
import Model.Ptsv2paymentsidreversalsClientReferenceInformation;
import Model.VoidCaptureRequest;

public class VoidCapture {

	private static String responseCode = null;
	private static String status = null;
	public static PtsV2PaymentsVoidsPost201Response response;
	public static PtsV2PaymentsCapturesPost201Response captureResponse;
	private static Properties merchantProp;

	static VoidCaptureRequest request;

	private static VoidCaptureRequest getRequest() {
		request = new VoidCaptureRequest();

		Ptsv2paymentsidreversalsClientReferenceInformation client = new Ptsv2paymentsidreversalsClientReferenceInformation();
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

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient.merchantConfig = merchantConfig;	

			captureResponse = CapturePayment.process();

			VoidApi voidApi = new VoidApi();
			response = voidApi.voidCapture(request, captureResponse.getId());

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
