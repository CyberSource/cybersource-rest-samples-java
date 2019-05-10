package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.InstrumentIdentifierApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.TmsV1InstrumentIdentifiersPost200Response;
import Model.TmsV1InstrumentIdentifiersPost200ResponseProcessingInformation;
import Model.TmsV1InstrumentIdentifiersPost200ResponseProcessingInformationAuthorizationOptions;
import Model.TmsV1InstrumentIdentifiersPost200ResponseProcessingInformationAuthorizationOptionsInitiator;
import Model.TmsV1InstrumentIdentifiersPost200ResponseProcessingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction;
import Model.UpdateInstrumentIdentifierRequest;

/**
 * 
 * This is the code for Update instrument identifier
 *
 */
public class UpdateInstrumentIdentifier {
	private static String responseCode = null;
	private static String status = null;
	static TmsV1InstrumentIdentifiersPost200Response response;
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static String tokenId = "7010000000004697654";
	private static Properties merchantProp;

	private static UpdateInstrumentIdentifierRequest getRequest(
			UpdateInstrumentIdentifierRequest updateInstrumentIdentifierRequest) {

		TmsV1InstrumentIdentifiersPost200ResponseProcessingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction merchantInitiatedTransaction = new TmsV1InstrumentIdentifiersPost200ResponseProcessingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction();
		merchantInitiatedTransaction.previousTransactionId("123456789012345");

		TmsV1InstrumentIdentifiersPost200ResponseProcessingInformationAuthorizationOptionsInitiator initiator = new TmsV1InstrumentIdentifiersPost200ResponseProcessingInformationAuthorizationOptionsInitiator();
		initiator.merchantInitiatedTransaction(merchantInitiatedTransaction);

		TmsV1InstrumentIdentifiersPost200ResponseProcessingInformationAuthorizationOptions authorizationOptions = new TmsV1InstrumentIdentifiersPost200ResponseProcessingInformationAuthorizationOptions();
		authorizationOptions.initiator(initiator);

		TmsV1InstrumentIdentifiersPost200ResponseProcessingInformation processingInformation = new TmsV1InstrumentIdentifiersPost200ResponseProcessingInformation();
		processingInformation.authorizationOptions(authorizationOptions);
		updateInstrumentIdentifierRequest.processingInformation(processingInformation);

		return updateInstrumentIdentifierRequest;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static TmsV1InstrumentIdentifiersPost200Response process() throws Exception {

		try {
			UpdateInstrumentIdentifierRequest updateInstrumentIdentifierRequest = new UpdateInstrumentIdentifierRequest();
			updateInstrumentIdentifierRequest = getRequest(updateInstrumentIdentifierRequest);
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient.merchantConfig = merchantConfig;

			InstrumentIdentifierApi instrumentIdentifierApi = new InstrumentIdentifierApi();
			response = instrumentIdentifierApi.updateInstrumentIdentifier(profileId, tokenId,
					updateInstrumentIdentifierRequest);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response.toString());

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
