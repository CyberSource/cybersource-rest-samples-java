package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentInstrumentApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentInstrumentRequest;
import Model.TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBillTo;
import Model.TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedCard;
import Model.TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedInstrumentIdentifier;
import Model.TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments;
import Model.TmsV1InstrumentIdentifiersPost200ResponseCard;

/**
 * 
 * This is the sample create Payment instrument identifier request
 *
 */
public class CreatePaymentInstrument {
	private static String responseCode = null;
	private static String status = null;
	static TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments response;
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static Properties merchantProp;

	/**
	 * 
	 * Add Card details and instrument identifier as part of the request
	 * 
	 * @param createPaymentInstrumentRequest
	 * @return
	 */
	private static CreatePaymentInstrumentRequest getRequest(
			CreatePaymentInstrumentRequest createPaymentInstrumentRequest) {

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedCard card = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedCard();
		card.expirationMonth("09");
		card.expirationYear("2022");
		card.type("visa");
		createPaymentInstrumentRequest.card(card);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBillTo billTo = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBillTo();
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.company("CyberSource");
		billTo.address1("12 Main Street");
		billTo.address2("20 My Street");
		billTo.locality("Foster City");
		billTo.administrativeArea("CA");
		billTo.postalCode("90200");
		billTo.country("US");
		billTo.email("test@cybs.com");
		billTo.phoneNumber("555123456");
		createPaymentInstrumentRequest.billTo(billTo);

		TmsV1InstrumentIdentifiersPost200ResponseCard instrumentidentifiersCard = new TmsV1InstrumentIdentifiersPost200ResponseCard();
		instrumentidentifiersCard.number("4111111111111111");

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedInstrumentIdentifier instrumentIdentifier = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedInstrumentIdentifier();
		instrumentIdentifier.card(instrumentidentifiersCard);

		createPaymentInstrumentRequest.instrumentIdentifier(instrumentIdentifier);

		return createPaymentInstrumentRequest;
	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments process()
			throws Exception {

		try {
			CreatePaymentInstrumentRequest createPaymentInstrumentRequest = new CreatePaymentInstrumentRequest();
			createPaymentInstrumentRequest = getRequest(createPaymentInstrumentRequest);
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;

			PaymentInstrumentApi paymentInstrumentApi = new PaymentInstrumentApi(apiClient);
			response = paymentInstrumentApi.createPaymentInstrument(profileId, createPaymentInstrumentRequest);

			responseCode = apiClient.responseCode;
			status = apiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println("ResponseBody :" + apiClient.respBody);

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
