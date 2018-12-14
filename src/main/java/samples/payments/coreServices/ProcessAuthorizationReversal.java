package samples.payments.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReversalApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.AuthReversalRequest;
import Model.PtsV2PaymentsPost201Response;
import Model.PtsV2PaymentsReversalsPost201Response;
import Model.Ptsv2paymentsidreversalsClientReferenceInformation;
import Model.Ptsv2paymentsidreversalsReversalInformation;
import Model.Ptsv2paymentsidreversalsReversalInformationAmountDetails;

public class ProcessAuthorizationReversal {

	private static String responseCode = null;
	private static String status = null;
	static PtsV2PaymentsReversalsPost201Response response;
	public static PtsV2PaymentsPost201Response paymentResponse;
	private static Properties merchantProp;

	static AuthReversalRequest request;

	private static AuthReversalRequest getRequest() {
		request = new AuthReversalRequest();

		Ptsv2paymentsidreversalsClientReferenceInformation client = new Ptsv2paymentsidreversalsClientReferenceInformation();
		client.code("test_reversal");
		request.setClientReferenceInformation(client);

		Ptsv2paymentsidreversalsReversalInformationAmountDetails amountDetails = new Ptsv2paymentsidreversalsReversalInformationAmountDetails();
		amountDetails.totalAmount("100.00");

		Ptsv2paymentsidreversalsReversalInformation reversalInformation = new Ptsv2paymentsidreversalsReversalInformation();
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

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient=new ApiClient(merchantConfig);
			
			paymentResponse = ProcessPayment.process(false);

			ReversalApi reversalApi = new ReversalApi();
			response = reversalApi.authReversal(paymentResponse.getId(), request);

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
