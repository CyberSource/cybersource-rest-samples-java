package payments.authorizePayment.digitalPayments.androidPay;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsOrderInformation;
import Model.V2paymentsOrderInformationAmountDetails;
import Model.V2paymentsOrderInformationBillTo;
import Model.V2paymentsPaymentInformation;
import Model.V2paymentsPaymentInformationCard;
import Model.V2paymentsPaymentInformationTokenizedCard;
import Model.V2paymentsPointOfSaleInformation;
import Model.V2paymentsPointOfSaleInformationEmv;
import Model.V2paymentsProcessingInformation;

public class AndroidPay {
	private String responseCode = null;
	private String responseMsg = null;

	CreatePaymentRequest request;

	private CreatePaymentRequest getRequest() {
		request = new CreatePaymentRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("33557799");
		request.clientReferenceInformation(client);
		
		V2paymentsPointOfSaleInformationEmv emv=new V2paymentsPointOfSaleInformationEmv();
		emv.cardSequenceNumber("123");
		emv.tags("9C01019A031207109F33036040209F1A0207849F370482766E409F3602001F82025C009F2608EF7753429A5D16B19F100706010A"
				+ "03A00000950580000400009F02060000000700009F6E0482766E409F5B04123456789F2701809F3403AB12349F0902AB129F410"
				+ "4AB1234AB9F0702AB129F0610123456789012345678901234567890AB9F030200005F2A0207849F7C031234569F350123");

		V2paymentsPointOfSaleInformation pointOfSaleInformation = new V2paymentsPointOfSaleInformation();
		pointOfSaleInformation.terminalId("terminal");
		pointOfSaleInformation.cardPresent(true);
		pointOfSaleInformation.emv(emv);
		pointOfSaleInformation.entryMode("QRCode");
		pointOfSaleInformation.terminalCapability(4);
		request.pointOfSaleInformation(pointOfSaleInformation);

		V2paymentsProcessingInformation processingInformation = new V2paymentsProcessingInformation();
		processingInformation.commerceIndicator("retail");
		processingInformation.paymentSolution("006");
		request.processingInformation(processingInformation);

		V2paymentsOrderInformationBillTo billTo = new V2paymentsOrderInformationBillTo();
		billTo.country("US");
		billTo.lastName("VDP");
		billTo.address2("test");
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
		amountDetails.totalAmount("102.21");
		amountDetails.currency("USD");

		V2paymentsOrderInformation orderInformation = new V2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		V2paymentsPaymentInformationTokenizedCard tokenizedCard=new V2paymentsPaymentInformationTokenizedCard();
		tokenizedCard.transactionType("1");
		tokenizedCard.requestorId("12345678901");
		
		V2paymentsPaymentInformationCard card=new V2paymentsPaymentInformationCard();
		card.type("001");
		//card.trackdata(";4111111111111111=21121019761186800000?");
		
		V2paymentsPaymentInformation paymentInformation = new V2paymentsPaymentInformation();
		paymentInformation.tokenizedCard(tokenizedCard);
		paymentInformation.card(card);
		request.setPaymentInformation(paymentInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		new AndroidPay();
	}

	public AndroidPay() throws Exception {
		process();
	}

	private void process() throws Exception {

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
