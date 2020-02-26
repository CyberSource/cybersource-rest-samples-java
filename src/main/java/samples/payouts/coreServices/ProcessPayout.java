package samples.payouts.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PayoutsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.OctCreatePaymentRequest;
import Model.PtsV2IncrementalAuthorizationPatch201ResponseClientReferenceInformation;
import Model.Ptsv2payoutsMerchantInformation;
import Model.Ptsv2payoutsMerchantInformationMerchantDescriptor;
import Model.Ptsv2payoutsOrderInformation;
import Model.Ptsv2payoutsOrderInformationAmountDetails;
import Model.Ptsv2payoutsPaymentInformation;
import Model.Ptsv2payoutsPaymentInformationCard;
import Model.Ptsv2payoutsProcessingInformation;
import Model.Ptsv2payoutsRecipientInformation;
import Model.Ptsv2payoutsSenderInformation;
import Model.Ptsv2payoutsSenderInformationAccount;

public class ProcessPayout {

	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;
	
	static OctCreatePaymentRequest request;

	private static OctCreatePaymentRequest getRequest() {
		request = new OctCreatePaymentRequest();

		PtsV2IncrementalAuthorizationPatch201ResponseClientReferenceInformation client = new PtsV2IncrementalAuthorizationPatch201ResponseClientReferenceInformation();
		client.code("33557799");
		request.clientReferenceInformation(client);

		Ptsv2payoutsSenderInformationAccount account = new Ptsv2payoutsSenderInformationAccount();
		account.number("1234567890123456789012345678901234");
		account.fundsSource("01");

		Ptsv2payoutsSenderInformation senderInformation = new Ptsv2payoutsSenderInformation();
		senderInformation.referenceNumber("1234567890");
		senderInformation.address1("900 Metro Center Blvd.900");
		senderInformation.countryCode("US");
		senderInformation.locality("Foster City");
		senderInformation.name("Thomas Jefferson");
		senderInformation.administrativeArea("CA");
		request.senderInformation(senderInformation);

		Ptsv2payoutsProcessingInformation processingInformation = new Ptsv2payoutsProcessingInformation();
		processingInformation.commerceIndicator("internet");
		processingInformation.businessApplicationId("FD");
		processingInformation.networkRoutingOrder("ECG");
		request.processingInformation(processingInformation);

		Ptsv2payoutsOrderInformationAmountDetails amountDetails = new Ptsv2payoutsOrderInformationAmountDetails();
		amountDetails.totalAmount("100.00");
		amountDetails.currency("USD");

		Ptsv2payoutsOrderInformation orderInformation = new Ptsv2payoutsOrderInformation();
		orderInformation.amountDetails(amountDetails);
		request.orderInformation(orderInformation);

		Ptsv2payoutsMerchantInformationMerchantDescriptor merchantDescriptor = new Ptsv2payoutsMerchantInformationMerchantDescriptor();
		merchantDescriptor.country("US");
		merchantDescriptor.postalCode("94440");
		merchantDescriptor.locality("FC");
		merchantDescriptor.name("Thomas");
		merchantDescriptor.administrativeArea("CA");

		Ptsv2payoutsMerchantInformation merchantInformation = new Ptsv2payoutsMerchantInformation();
		merchantInformation.categoryCode(123);
		merchantInformation.merchantDescriptor(merchantDescriptor);
		request.merchantInformation(merchantInformation);

		Ptsv2payoutsPaymentInformationCard card = new Ptsv2payoutsPaymentInformationCard();
		card.expirationYear("2025");
		card.number("4111111111111111");
		card.expirationMonth("12");
		card.type("001");
		card.sourceAccountType("CH");

		Ptsv2payoutsPaymentInformation paymentInformation = new Ptsv2payoutsPaymentInformation();
		paymentInformation.card(card);
		request.paymentInformation(paymentInformation);

		Ptsv2payoutsRecipientInformation recipientInformation = new Ptsv2payoutsRecipientInformation();
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

	public static void process() throws Exception {

		try {
			request = getRequest();
			
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;
			
			PayoutsApi payoutApi = new PayoutsApi(apiClient);
			payoutApi.octCreatePayment(request);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println("ResponseBody :"+apiClient.respBody);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
