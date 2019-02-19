package samples.tms.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentInstrumentsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.Body2;
import Model.TmsV1PaymentinstrumentsPost201Response;
import Model.Tmsv1instrumentidentifiersCard;
import Model.Tmsv1paymentinstrumentsBillTo;
import Model.Tmsv1paymentinstrumentsCard;
import Model.Tmsv1paymentinstrumentsCard.TypeEnum;
import Model.Tmsv1paymentinstrumentsInstrumentIdentifier;

public class CreatePaymentInstrument {
	private TmsV1PaymentinstrumentsPost201Response response;
	private String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private Properties merchantProp;
	private Body2 body;

	private Body2 getRequest() {
		body = new Body2();

		Tmsv1paymentinstrumentsCard card = new Tmsv1paymentinstrumentsCard();
		card.expirationMonth("09");
		card.expirationYear("2022");
		card.type(TypeEnum.VISA);
		body.card(card);

		Tmsv1paymentinstrumentsBillTo billTo = new Tmsv1paymentinstrumentsBillTo();
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

		Tmsv1instrumentidentifiersCard instrumentidentifiersCard = new Tmsv1instrumentidentifiersCard();
		instrumentidentifiersCard.number("4111111111111111");

		Tmsv1paymentinstrumentsInstrumentIdentifier instrumentIdentifier = new Tmsv1paymentinstrumentsInstrumentIdentifier();
		instrumentIdentifier.card(instrumentidentifiersCard);
		body.instrumentIdentifier(instrumentIdentifier);

		return body;

	}

	public static void main(String args[]) throws Exception {
		CreatePaymentInstrument createPaymentInstrument = new CreatePaymentInstrument();
		createPaymentInstrument.process();
	}

	public TmsV1PaymentinstrumentsPost201Response process() throws Exception {
		String className=CreatePaymentInstrument.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			body = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			PaymentInstrumentsApi paymentInstrumentApi = new PaymentInstrumentsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			response = paymentInstrumentApi.tmsV1PaymentinstrumentsPost(profileId, body);
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " +className+": "+apiClient.getRespBody()+"\n");
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
			System.out.println("[END] EXECUTION OF SAMPLE CODE: " + className + "\n");
		}
		return response;
	}

}
