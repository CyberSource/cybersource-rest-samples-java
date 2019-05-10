package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.InstrumentIdentifierApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreateInstrumentIdentifierRequest;
import Model.TmsV1InstrumentIdentifiersPost200Response;
import Model.Tmsv1instrumentidentifiersCard;

/**
 * 
 * This is the sample code to create instrument identifier
 *
 */
public class CreateInstrumentIdentifier {
	private static String responseCode = null;
	private static String status = null;
	static TmsV1InstrumentIdentifiersPost200Response response;
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static Properties merchantProp;

	
	/**
	 * Create a instrument identifier request with only card details
	 * @param createInstrumentIdentifierRequest
	 * @return
	 */
	private static CreateInstrumentIdentifierRequest getRequest(CreateInstrumentIdentifierRequest createInstrumentIdentifierRequest) {
		Tmsv1instrumentidentifiersCard card = new Tmsv1instrumentidentifiersCard();
		card.number("1234567432587654");
		createInstrumentIdentifierRequest.card(card);
		return createInstrumentIdentifierRequest;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static TmsV1InstrumentIdentifiersPost200Response process() throws Exception {

		try {
			CreateInstrumentIdentifierRequest createInstrumentIdentifierRequest= new CreateInstrumentIdentifierRequest();
			createInstrumentIdentifierRequest = getRequest(createInstrumentIdentifierRequest);
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient.merchantConfig = merchantConfig;
		
			InstrumentIdentifierApi instrumentIdentifierApi = new InstrumentIdentifierApi();
			response = instrumentIdentifierApi.createInstrumentIdentifier(profileId, createInstrumentIdentifierRequest);

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
