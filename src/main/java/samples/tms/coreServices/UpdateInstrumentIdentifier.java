package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.InstrumentIdentifierApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.Body1;
import Model.TmsV1InstrumentidentifiersPost200Response;
import Model.Tmsv1instrumentidentifiersProcessingInformation;
import Model.Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptions;
import Model.Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptionsInitiator;
import Model.Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction;

public class UpdateInstrumentIdentifier {
	private TmsV1InstrumentidentifiersPost200Response response;
	private String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private Properties merchantProp;
	private Body1 body;

	private  Body1 getRequest() {
		body = new Body1();

		Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction merchantInitiatedTransaction = new Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction();
		merchantInitiatedTransaction.previousTransactionId("123456789012345");

		Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptionsInitiator initiator = new Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptionsInitiator();
		initiator.merchantInitiatedTransaction(merchantInitiatedTransaction);

		Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptions authorizationOptions = new Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptions();
		authorizationOptions.initiator(initiator);

		Tmsv1instrumentidentifiersProcessingInformation processingInformation = new Tmsv1instrumentidentifiersProcessingInformation();
		processingInformation.authorizationOptions(authorizationOptions);
		body.processingInformation(processingInformation);

		return body;

	}

	public static void main(String args[]) throws Exception {
		UpdateInstrumentIdentifier updateInstrumentIdentifier = new UpdateInstrumentIdentifier();
		updateInstrumentIdentifier.process();
	}

	private TmsV1InstrumentidentifiersPost200Response process() throws Exception {
		String className=UpdateInstrumentIdentifier.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = new ApiClient();
		try {
			body = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			CreateInstrumentIdentifier createInstrumentIdentifier = new CreateInstrumentIdentifier();
			TmsV1InstrumentidentifiersPost200Response post200Response=createInstrumentIdentifier.process();
			if (post200Response != null) {
			InstrumentIdentifierApi instrumentIdentifierApi = new InstrumentIdentifierApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			response = instrumentIdentifierApi.tmsV1InstrumentidentifiersTokenIdPatch(profileId, post200Response.getId(), body);
			}
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " +className+": "+apiClient.getRespBody()+"\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API REQUEST BODY:");
			System.out.println(apiClient.getRequestBody()+ "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getRespBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE:" + className + "\n");
		}
		
		return response;
	}

}
