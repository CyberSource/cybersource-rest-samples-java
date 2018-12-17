package samples.payments.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.VoidApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.PtsV2CreditsPost201Response;
import Model.PtsV2PaymentsVoidsPost201Response;
import Model.Ptsv2paymentsidreversalsClientReferenceInformation;
import Model.VoidCreditRequest;

public class VoidCredit {

	private static String responseCode = null;
	private static String status = null;
	public static PtsV2PaymentsVoidsPost201Response response;
	public static PtsV2CreditsPost201Response creditResponse;
	private static Properties merchantProp;

	static VoidCreditRequest request;

	private static VoidCreditRequest getRequest() {
		request = new VoidCreditRequest();

		Ptsv2paymentsidreversalsClientReferenceInformation client = new Ptsv2paymentsidreversalsClientReferenceInformation();
		client.code("test_credit_void");
		request.setClientReferenceInformation(client);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			request = getRequest();

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient = new ApiClient(merchantConfig);
			
			creditResponse = ProcessCredit.process();

			VoidApi voidApi = new VoidApi();
			response = voidApi.voidCredit(request, creditResponse.getId());

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
