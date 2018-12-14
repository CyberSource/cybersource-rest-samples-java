package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentInstrumentsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.Body3;
import Model.TmsV1PaymentinstrumentsPost201Response;
import Model.Tmsv1instrumentidentifiersCard;
import Model.Tmsv1paymentinstrumentsBillTo;
import Model.Tmsv1paymentinstrumentsCard;
import Model.Tmsv1paymentinstrumentsCard.TypeEnum;
import Model.Tmsv1paymentinstrumentsInstrumentIdentifier;

public class UpdatePaymentInstrument {
	private static String responseCode = null;
	private static String status = null;
	static TmsV1PaymentinstrumentsPost201Response response;
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static String tokenId = "79AB33E6A3DE03B6E05340588D0A4B9A";

	private static Properties merchantProp;

	static Body3 body;

	private static Body3 getRequest() {
		body = new Body3();

		Tmsv1paymentinstrumentsCard card = new Tmsv1paymentinstrumentsCard();
		card.expirationMonth("09");
		card.expirationYear("2022");
		card.type(TypeEnum.VISA);
		body.card(card);

		Tmsv1paymentinstrumentsBillTo billTo = new Tmsv1paymentinstrumentsBillTo();
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
		body.billTo(billTo);

		Tmsv1instrumentidentifiersCard instrumentidentifiersCard = new Tmsv1instrumentidentifiersCard();
		instrumentidentifiersCard.number("4111111111111111");

		Tmsv1paymentinstrumentsInstrumentIdentifier instrumentIdentifier = new Tmsv1paymentinstrumentsInstrumentIdentifier();
		instrumentIdentifier.card(instrumentidentifiersCard);
		body.instrumentIdentifier(instrumentIdentifier);

		return body;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static TmsV1PaymentinstrumentsPost201Response process() throws Exception {

		try {
			body = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient=new ApiClient(merchantConfig);

			PaymentInstrumentsApi paymentInstrumentApi = new PaymentInstrumentsApi();
			response = paymentInstrumentApi.tmsV1PaymentinstrumentsTokenIdPatch(profileId, tokenId, body);

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
