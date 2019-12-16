package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentInstrumentApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBillTo;
import Model.TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedCard;
import Model.Tmsv1paymentinstrumentsInstrumentIdentifier;
import Model.TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments;
import Model.TmsV1InstrumentIdentifiersPost200ResponseCard;
import Model.UpdatePaymentInstrumentRequest;

/**
 * 
 * Update payment instrument
 *
 */
public class UpdatePaymentInstrument {
	private static String responseCode = null;
	private static String status = null;
	static TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments response;
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static String tokenId = "82EFD42A2806C20CE05340588D0A2D59";

	private static Properties merchantProp;

	private static UpdatePaymentInstrumentRequest getRequest(UpdatePaymentInstrumentRequest updatePaymentInstrumentRequest) {
		
		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedCard card = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedCard();
		card.expirationMonth("09");
		card.expirationYear("2022");
		card.type("visa");
		updatePaymentInstrumentRequest.card(card);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBillTo billTo = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBillTo();
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.company("CyberSource");
		billTo.address1("1 Market St");
		billTo.address2("20 Main Street");
		billTo.locality("san francisco");
		billTo.administrativeArea("CA");
		billTo.postalCode("94105");
		billTo.country("US");
		billTo.email("test@cybs.com");
		billTo.phoneNumber("555123456");
		updatePaymentInstrumentRequest.billTo(billTo);

		TmsV1InstrumentIdentifiersPost200ResponseCard instrumentidentifiersCard = new TmsV1InstrumentIdentifiersPost200ResponseCard();
		instrumentidentifiersCard.number("4111111111111111");

		Tmsv1paymentinstrumentsInstrumentIdentifier instrumentIdentifier = new Tmsv1paymentinstrumentsInstrumentIdentifier();
		instrumentIdentifier.card(instrumentidentifiersCard);
		updatePaymentInstrumentRequest.instrumentIdentifier(instrumentIdentifier);

		return updatePaymentInstrumentRequest;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments process() throws Exception {

		try {
			UpdatePaymentInstrumentRequest updatePaymentInstrumentRequest= new UpdatePaymentInstrumentRequest();
			updatePaymentInstrumentRequest = getRequest(updatePaymentInstrumentRequest);
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;

			PaymentInstrumentApi paymentInstrumentApi = new PaymentInstrumentApi(apiClient);
			response = paymentInstrumentApi.updatePaymentInstrument(profileId, tokenId, updatePaymentInstrumentRequest);

			responseCode = apiClient.responseCode;
			status = apiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println("ResponseBody :"+apiClient.respBody);

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
