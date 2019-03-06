package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.InstrumentIdentifiersApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.Body;
import Model.TmsV1InstrumentidentifiersPost200Response;
import Model.Tmsv1instrumentidentifiersCard;
import Model.Tmsv1instrumentidentifiersProcessingInformation;
import Model.Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptions;
import Model.Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptionsInitiator;
import Model.Tmsv1instrumentidentifiersProcessingInformationAuthorizationOptionsInitiatorMerchantInitiatedTransaction;

public class CreateInstrumentIdentifier {
	private static String responseCode = null;
	private static String status = null;
	static TmsV1InstrumentidentifiersPost200Response response;
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static Properties merchantProp;

	static Body body;

	private static Body getRequest() {
		body = new Body();

		Tmsv1instrumentidentifiersCard card = new Tmsv1instrumentidentifiersCard();
		card.number("1234567432587654");
		body.card(card);

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
		process();
	}

	public static TmsV1InstrumentidentifiersPost200Response process() throws Exception {

		try {
			body = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient.merchantConfig = merchantConfig;
		
			InstrumentIdentifiersApi instrumentIdentifierApi = new InstrumentIdentifiersApi();
			response = instrumentIdentifierApi.tmsV1InstrumentidentifiersPost(profileId, body);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response);

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
