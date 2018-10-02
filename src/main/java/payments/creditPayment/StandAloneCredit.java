package payments.creditPayment;

import Api.CreditApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreateCreditRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsidcapturesOrderInformationAmountDetails;
import Model.V2paymentsidcapturesOrderInformationBillTo;
import Model.V2paymentsidrefundsOrderInformation;
import Model.V2paymentsidrefundsPaymentInformation;
import Model.V2paymentsidrefundsPaymentInformationCard;

public class StandAloneCredit {

	private String responseCode = null;
	private String responseMsg = null;

	CreateCreditRequest request;

	private CreateCreditRequest getRequest() {
		request = new CreateCreditRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("TC50171_3");
		request.setClientReferenceInformation(client);

		V2paymentsidcapturesOrderInformationBillTo billTo = new V2paymentsidcapturesOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("Test");
		billTo.lastName("test");
		billTo.phoneNumber("999999999");
		billTo.address1("201 S. Division St.");
		billTo.postalCode("48104-2201");
		billTo.locality("Ann Arbor");
		billTo.administrativeArea("MI");
		billTo.email("test@cybs.com");

		V2paymentsidcapturesOrderInformationAmountDetails amountDetails = new V2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("102.21");
		amountDetails.currency("USD");

		V2paymentsidrefundsOrderInformation orderInformation = new V2paymentsidrefundsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		V2paymentsidrefundsPaymentInformationCard card = new V2paymentsidrefundsPaymentInformationCard();
		card.expirationYear("2031");
		card.number("4111111111111111");
		card.expirationMonth("03");
		card.type("001");

		V2paymentsidrefundsPaymentInformation paymentInformation = new V2paymentsidrefundsPaymentInformation();
		paymentInformation.card(card);
		request.setPaymentInformation(paymentInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		new StandAloneCredit();
	}

	public StandAloneCredit() throws Exception {
		process();
	}

	private void process() throws Exception {

		try {
			request = getRequest();
			CreditApi creditApi = new CreditApi();
			creditApi.createCredit(request);

			responseCode = ApiClient.resp;
			responseMsg = ApiClient.respmsg;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMsg);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
