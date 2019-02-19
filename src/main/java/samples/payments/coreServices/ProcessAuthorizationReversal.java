package samples.payments.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReversalApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.AuthReversalRequest;
import Model.PtsV2PaymentsPost201Response;
import Model.Ptsv2paymentsidreversalsClientReferenceInformation;
import Model.Ptsv2paymentsidreversalsReversalInformation;
import Model.Ptsv2paymentsidreversalsReversalInformationAmountDetails;

public class ProcessAuthorizationReversal {

	private  PtsV2PaymentsPost201Response paymentResponse;
	private  Properties merchantProp;
	private AuthReversalRequest request;

	private  AuthReversalRequest getRequest() {
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
		ProcessAuthorizationReversal processAuthorizationReversal=new ProcessAuthorizationReversal();
		processAuthorizationReversal.process();
	}

	public  void process() throws Exception {
		String className=ProcessAuthorizationReversal.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = new ApiClient();
		try {
			request = getRequest();

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ProcessPayment processPayment = new ProcessPayment();
			paymentResponse = processPayment.process(false);
			
			if(paymentResponse!=null){
			ReversalApi reversalApi = new ReversalApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			reversalApi.authReversal(paymentResponse.getId(), request);
			}
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code" +className+": "+apiClient.getRespBody()+"\n");
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
