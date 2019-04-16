package samples.tms.paymentsWithToken;

import Api.PaymentInstrumentsApi;
import Api.PaymentsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;
import Model.Tmsv1paymentinstrumentsCard.TypeEnum;
import com.cybersource.authsdk.core.MerchantConfig;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class PaymentsInstrumentsForAuthorization {
	private static String responseCode = null;
	private static String status = null;
	static TmsV1PaymentinstrumentsPost201Response response;
	static PtsV2PaymentsPost201Response paymentResponse;
	private static String profileId = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	private static Properties merchantProp;

	static Body2 body;
	private static CreatePaymentRequest request;

	private static Body2 getPaymentInstrumentRequest() {
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

	private static CreatePaymentRequest getpaymentRequest(String token) {
		request = new CreatePaymentRequest();

		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("test_payment");
		request.clientReferenceInformation(client);

		Ptsv2paymentsOrderInformationAmountDetails amountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		amountDetails.totalAmount("22");
		amountDetails.currency("USD");

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		Ptsv2paymentsPaymentInformationCustomer customer = new Ptsv2paymentsPaymentInformationCustomer();
		customer.customerId(token);

		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		paymentInformation.customer(customer);

		request.setPaymentInformation(paymentInformation);

		return request;


	}
	private static void process() throws Exception {

		try {
			body = getPaymentInstrumentRequest();

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient.merchantConfig = merchantConfig;
			
			PaymentInstrumentsApi paymentInstrumentApi = new PaymentInstrumentsApi();
			response = paymentInstrumentApi.tmsV1PaymentinstrumentsPost(profileId, body);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("Create Payment Instrument");
			System.out.println("ResponseCode :" +responseCode);
			System.out.println("Status :" +status);
			System.out.println("Payment Instrument Token :" + response.getId());
			String token = response.getId();

			request = getpaymentRequest(token);
			TimeUnit.SECONDS.sleep(10);
			PaymentsApi paymentApi = new PaymentsApi();
			paymentResponse = paymentApi.createPayment(request);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("\nProcess a Payment");
			System.out.println("ResponseCode :" +responseCode);
			System.out.println("Status :" +status);
			System.out.println("Payment ID :" +paymentResponse.getId());

		} catch (ApiException e) {
			e.printStackTrace();
		}
	}


}
