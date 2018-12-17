package samples.tms.paymentsWithToken;

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

public class PaymentsInstrumentsForAuthorization {
	private static String responseCode = null;
	private static String status = null;
	static TmsV1PaymentinstrumentsPost201Response response;
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static Properties merchantProp;

	static Body2 body;

	private static Body2 getRequest() {
		body = new Body2();
		
		Tmsv1paymentinstrumentsCard card=new Tmsv1paymentinstrumentsCard();
		card.expirationMonth("09");
		card.expirationYear("2022");
		card.type(TypeEnum.VISA);
		body.card(card);
		
		Tmsv1paymentinstrumentsBillTo billTo=new Tmsv1paymentinstrumentsBillTo();
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
		
		Tmsv1instrumentidentifiersCard instrumentidentifiersCard=new Tmsv1instrumentidentifiersCard();
		instrumentidentifiersCard.number("4111111111111111");
		
		Tmsv1paymentinstrumentsInstrumentIdentifier instrumentIdentifier=new Tmsv1paymentinstrumentsInstrumentIdentifier();
		instrumentIdentifier.card(instrumentidentifiersCard);
		body.instrumentIdentifier(instrumentIdentifier);
		
		return body;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			body = getRequest();

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient = new ApiClient(merchantConfig);
			
			PaymentInstrumentsApi paymentInstrumentApi = new PaymentInstrumentsApi();
			response = paymentInstrumentApi.tmsV1PaymentinstrumentsPost(profileId, body);


			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			
			System.out.println("ResponseCode :" +responseCode);
			System.out.println("Status :" +status);
			System.out.println(response.getId());

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}


}
