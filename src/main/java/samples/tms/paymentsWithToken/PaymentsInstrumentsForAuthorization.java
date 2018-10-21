package samples.tms.paymentsWithToken;

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

public class PaymentsInstrumentsForAuthorization {
	private String responseCode = null;
	private String status=null;
	static InlineResponse2016 response;
	private String profileId="93B32398-AD51-4CC2-A682-EA3E93614EB1";

	Body2 body;

	private Body2 getRequest() {
		body = new Body2();
		
		PaymentinstrumentsCard card=new PaymentinstrumentsCard();
		card.expirationMonth("09");
		card.expirationYear("2022");
		card.type(TypeEnum.visa);
		body.card(card);
		
		PaymentinstrumentsBillTo billTo=new PaymentinstrumentsBillTo();
		billTo.firstName("john");
		billTo.lastName("Deo");
		billTo.company("Cybersource");
		billTo.address1("12 Main Street");
		billTo.address2("20 My Street");
		billTo.locality("Foster City");
		billTo.administrativeArea("CA");
		billTo.postalCode("90200");
		billTo.country("US");
		billTo.email("john.smith@example.com");
		billTo.phoneNumber("555123456");
		body.billTo(billTo);
		
		InstrumentidentifiersCard instrumentidentifiersCard=new InstrumentidentifiersCard();
		instrumentidentifiersCard.number("4111111111111111");
		
		PaymentinstrumentsInstrumentIdentifier instrumentIdentifier=new PaymentinstrumentsInstrumentIdentifier();
		instrumentIdentifier.card(instrumentidentifiersCard);
		body.instrumentIdentifier(instrumentIdentifier);
		
		return body;

	}

	public static void main(String args[]) throws Exception {
		new PaymentsInstrumentsForAuthorization();
	}

	public PaymentsInstrumentsForAuthorization() throws Exception {
		process();
	}

	private void process() throws Exception {

		try {
			body = getRequest();

			PaymentInstrumentApi paymentInstrumentApi = new PaymentInstrumentApi();
			response=paymentInstrumentApi.paymentinstrumentsPost(profileId, body);


			responseCode=ApiClient.responseCode;
			status=ApiClient.status;
			
			System.out.println("ResponseCode :" +responseCode);
			System.out.println("Status :" +status);
			System.out.println(response.getId());

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}


}
