package payments.authorizePayment;

import java.util.ArrayList;
import java.util.List;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.V2paymentsBuyerInformation;
import Model.V2paymentsBuyerInformationPersonalIdentification;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsDeviceInformation;
import Model.V2paymentsOrderInformation;
import Model.V2paymentsOrderInformationAmountDetails;
import Model.V2paymentsOrderInformationBillTo;
import Model.V2paymentsPaymentInformation;
import Model.V2paymentsPaymentInformationCard;
import Model.V2paymentsProcessingInformation;
import Model.V2paymentsProcessingInformationAuthorizationOptions;

public class VoiceAuthReferral {
	private static String responseCode = null;
	private static String responseMsg = null;

	static CreatePaymentRequest request;

	private static CreatePaymentRequest getRequest() {
		request = new CreatePaymentRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("TC1102345");
		request.clientReferenceInformation(client);

		V2paymentsDeviceInformation deviceInformation = new V2paymentsDeviceInformation();
		deviceInformation.hostName("cybersource.com");
		deviceInformation.ipAddress("66.185.179.2");
		request.deviceInformation(deviceInformation);

		V2paymentsProcessingInformationAuthorizationOptions authorizationOptions = new V2paymentsProcessingInformationAuthorizationOptions();
		authorizationOptions.ignoreAvsResult(true);
		authorizationOptions.ignoreCvResult(false);

		V2paymentsProcessingInformation processingInformation = new V2paymentsProcessingInformation();
		processingInformation.authorizationOptions(authorizationOptions);
		request.processingInformation(processingInformation);

		List<V2paymentsBuyerInformationPersonalIdentification> personalIdentification = new ArrayList<V2paymentsBuyerInformationPersonalIdentification>();
		// personalIdentification.add(0, "123* 4s∆");

		V2paymentsBuyerInformation buyerInformation = new V2paymentsBuyerInformation();
		buyerInformation.personalIdentification(personalIdentification);
		request.buyerInformation(buyerInformation);
		V2paymentsOrderInformationBillTo billTo = new V2paymentsOrderInformationBillTo();
		billTo.country("US");
		billTo.lastName("VDP");
		billTo.address2("Address 2");
		billTo.address1("201 S. Division St.");
		billTo.postalCode("48104-2201");
		billTo.locality("Ann Arbor");
		billTo.administrativeArea("MI");
		billTo.firstName("RTS");
		billTo.phoneNumber("999999999");
		billTo.district("MI");
		billTo.buildingNumber("123");
		billTo.company("Visa");
		billTo.email("test@cybs.com");

		V2paymentsOrderInformationAmountDetails amountDetails = new V2paymentsOrderInformationAmountDetails();
		amountDetails.totalAmount("2401");
		amountDetails.currency("usd");

		V2paymentsOrderInformation orderInformation = new V2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		V2paymentsPaymentInformationCard card = new V2paymentsPaymentInformationCard();
		card.expirationYear("2031");
		card.number("372425119311008");
		card.securityCode("1111");
		card.expirationMonth("12");
		card.type("003");
		card.securityCodeIndicator("1");

		V2paymentsPaymentInformation paymentInformation = new V2paymentsPaymentInformation();
		paymentInformation.card(card);
		request.setPaymentInformation(paymentInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		new VoiceAuthReferral();
	}

	public VoiceAuthReferral() throws Exception {
		process();
	}

	public static void process() throws Exception {

		try {
			request = getRequest();

			PaymentApi paymentApi = new PaymentApi();
			paymentApi.createPayment(request);

			responseCode = ApiClient.resp;
			responseMsg = ApiClient.respmsg;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMsg);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
