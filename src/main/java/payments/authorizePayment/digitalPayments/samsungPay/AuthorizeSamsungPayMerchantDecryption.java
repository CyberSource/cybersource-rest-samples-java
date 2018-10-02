package payments.authorizePayment.digitalPayments.samsungPay;

import Api.PaymentApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreatePaymentRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsOrderInformation;
import Model.V2paymentsOrderInformationAmountDetails;
import Model.V2paymentsOrderInformationBillTo;
import Model.V2paymentsPaymentInformation;
import Model.V2paymentsPaymentInformationTokenizedCard;
import Model.V2paymentsProcessingInformation;

public class AuthorizeSamsungPayMerchantDecryption {
	private String responseCode = null;
	private String responseMsg = null;

	CreatePaymentRequest request;

	private CreatePaymentRequest getRequest() {
		request = new CreatePaymentRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("TC_MPOS_Paymentech_3");
		request.clientReferenceInformation(client);
		
		V2paymentsProcessingInformation processingInformation = new V2paymentsProcessingInformation();
		processingInformation.commerceIndicator("vbv");
		processingInformation.paymentSolution("008");
		request.processingInformation(processingInformation);

		V2paymentsOrderInformationBillTo billTo = new V2paymentsOrderInformationBillTo();
		billTo.country("US");
		billTo.lastName("VDP");
		billTo.address2("address2");
		billTo.address1("201 S. Division St.");
		billTo.postalCode("48104-2201");
		billTo.locality("Ann Arbor");
		billTo.administrativeArea("MI");
		billTo.firstName("RTS");
		billTo.phoneNumber("999999999");
		billTo.district("MI");
		billTo.buildingNumber("123");
		billTo.company("visa");
		billTo.email("test@cybs.com");

		V2paymentsOrderInformationAmountDetails amountDetails = new V2paymentsOrderInformationAmountDetails();
		amountDetails.totalAmount("100.0");
		amountDetails.currency("USD");

		V2paymentsOrderInformation orderInformation = new V2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		
		V2paymentsPaymentInformationTokenizedCard tokenizedCard=new V2paymentsPaymentInformationTokenizedCard();
		tokenizedCard.expirationYear("2031");
		tokenizedCard.number("4111111111111111");
		tokenizedCard.expirationMonth("12");
		tokenizedCard.transactionType("1");
		tokenizedCard.cryptogram("020000000000064e03599e02dbcc944000000000");
		
		
		V2paymentsPaymentInformation paymentInformation = new V2paymentsPaymentInformation();
		paymentInformation.tokenizedCard(tokenizedCard);
		request.setPaymentInformation(paymentInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		new AuthorizeSamsungPayMerchantDecryption();
	}

	public AuthorizeSamsungPayMerchantDecryption() throws Exception {
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
