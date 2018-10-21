package samples.tms.coreServices;

import Api.PaymentInstrumentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.Body2;
import Model.InlineResponse2016;
import Model.InstrumentidentifiersCard;
import Model.PaymentinstrumentsBillTo;
import Model.PaymentinstrumentsCard;
import Model.PaymentinstrumentsCard.TypeEnum;
import Model.PaymentinstrumentsInstrumentIdentifier;

public class CreatePaymentInstrument {
	private static String responseCode = null;
	private static String status = null;
	static InlineResponse2016 response;
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";

	static Body2 body;

	private static Body2 getRequest() {
		body = new Body2();

		PaymentinstrumentsCard card = new PaymentinstrumentsCard();
		card.expirationMonth("09");
		card.expirationYear("2022");
		card.type(TypeEnum.visa);
		body.card(card);

		PaymentinstrumentsBillTo billTo = new PaymentinstrumentsBillTo();
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
		body.billTo(billTo);

		InstrumentidentifiersCard instrumentidentifiersCard = new InstrumentidentifiersCard();
		instrumentidentifiersCard.number("4111111111111111");

		PaymentinstrumentsInstrumentIdentifier instrumentIdentifier = new PaymentinstrumentsInstrumentIdentifier();
		instrumentIdentifier.card(instrumentidentifiersCard);
		body.instrumentIdentifier(instrumentIdentifier);

		return body;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static InlineResponse2016 process() throws Exception {

		try {
			body = getRequest();

			PaymentInstrumentApi paymentInstrumentApi = new PaymentInstrumentApi();
			response = paymentInstrumentApi.paymentinstrumentsPost(profileId, body);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response.getId());

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
