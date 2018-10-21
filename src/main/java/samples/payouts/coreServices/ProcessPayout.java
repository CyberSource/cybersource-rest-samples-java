package samples.payouts.coreServices;

import Api.DefaultApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse201ClientReferenceInformation;
import Model.OctCreatePaymentRequest;
import Model.V2payoutsMerchantInformation;
import Model.V2payoutsMerchantInformationMerchantDescriptor;
import Model.V2payoutsOrderInformation;
import Model.V2payoutsOrderInformationAmountDetails;
import Model.V2payoutsPaymentInformation;
import Model.V2payoutsPaymentInformationCard;
import Model.V2payoutsProcessingInformation;
import Model.V2payoutsRecipientInformation;
import Model.V2payoutsSenderInformation;
import Model.V2payoutsSenderInformationAccount;

public class ProcessPayout {

	private static String responseCode = null;
	private static String status = null;

	static OctCreatePaymentRequest request;

	private static OctCreatePaymentRequest getRequest() {
		request = new OctCreatePaymentRequest();

		InlineResponse201ClientReferenceInformation client = new InlineResponse201ClientReferenceInformation();
		client.code("33557799");
		request.clientReferenceInformation(client);

		V2payoutsSenderInformationAccount account = new V2payoutsSenderInformationAccount();
		account.number("1234567890123456789012345678901234");
		account.fundsSource("01");

		V2payoutsSenderInformation senderInformation = new V2payoutsSenderInformation();
		senderInformation.referenceNumber("1234567890");
		senderInformation.address1("900 Metro Center Blvd.900");
		senderInformation.countryCode("US");
		senderInformation.locality("Foster City");
		senderInformation.name("Thomas Jefferson");
		senderInformation.administrativeArea("CA");
		request.senderInformation(senderInformation);

		V2payoutsProcessingInformation processingInformation = new V2payoutsProcessingInformation();
		processingInformation.commerceIndicator("internet");
		processingInformation.businessApplicationId("FD");
		processingInformation.networkRoutingOrder("ECG");
		request.processingInformation(processingInformation);

		V2payoutsOrderInformationAmountDetails amountDetails = new V2payoutsOrderInformationAmountDetails();
		amountDetails.totalAmount("100.00");
		amountDetails.currency("USD");

		V2payoutsOrderInformation orderInformation = new V2payoutsOrderInformation();
		orderInformation.amountDetails(amountDetails);
		request.orderInformation(orderInformation);

		V2payoutsMerchantInformationMerchantDescriptor merchantDescriptor = new V2payoutsMerchantInformationMerchantDescriptor();
		merchantDescriptor.country("US");
		merchantDescriptor.postalCode("94440");
		merchantDescriptor.locality("FC");
		merchantDescriptor.name("Thomas");
		merchantDescriptor.administrativeArea("CA");

		V2payoutsMerchantInformation merchantInformation = new V2payoutsMerchantInformation();
		merchantInformation.categoryCode(123);
		merchantInformation.merchantDescriptor(merchantDescriptor);
		request.merchantInformation(merchantInformation);

		V2payoutsPaymentInformationCard card = new V2payoutsPaymentInformationCard();
		card.expirationYear("2025");
		card.number("4111111111111111");
		card.expirationMonth("12");
		card.type("001");
		card.sourceAccountType("CH");

		V2payoutsPaymentInformation paymentInformation = new V2payoutsPaymentInformation();
		paymentInformation.card(card);
		request.paymentInformation(paymentInformation);

		V2payoutsRecipientInformation recipientInformation = new V2payoutsRecipientInformation();
		recipientInformation.firstName("John");
		recipientInformation.lastName("Deo");
		recipientInformation.address1("Paseo Padre Boulevard");
		recipientInformation.locality("Foster City");
		recipientInformation.administrativeArea("CA");
		recipientInformation.postalCode("94400");
		recipientInformation.phoneNumber("6504320556");
		recipientInformation.country("US");
		request.recipientInformation(recipientInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			request = getRequest();

			DefaultApi defaultApi = new DefaultApi();
			defaultApi.octCreatePayment(request);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
