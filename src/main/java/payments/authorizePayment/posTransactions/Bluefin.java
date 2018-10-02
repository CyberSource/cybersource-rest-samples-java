package payments.authorizePayment.posTransactions;

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
import Model.V2paymentsPaymentInformationFluidData;
import Model.V2paymentsPointOfSaleInformation;
import Model.V2paymentsProcessingInformation;

public class Bluefin {
	private String responseCode = null;
	private String responseMsg = null;

	CreatePaymentRequest request;

	private CreatePaymentRequest getRequest() {
		request = new CreatePaymentRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("demomerchant");
		request.clientReferenceInformation(client);

		V2paymentsPointOfSaleInformation pointOfSaleInformation = new V2paymentsPointOfSaleInformation();
		pointOfSaleInformation.cardPresent(true);
		pointOfSaleInformation.catLevel(1);
		pointOfSaleInformation.entryMode("keyed");
		pointOfSaleInformation.terminalCapability(2);
		request.pointOfSaleInformation(pointOfSaleInformation);

		V2paymentsProcessingInformation processingInformation = new V2paymentsProcessingInformation();
		processingInformation.commerceIndicator("retail");
		request.processingInformation(processingInformation);

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
		amountDetails.totalAmount("100.00");
		amountDetails.currency("usd");

		V2paymentsOrderInformation orderInformation = new V2paymentsOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		V2paymentsPaymentInformationCard card = new V2paymentsPaymentInformationCard();
		card.expirationYear("2050");
		card.expirationMonth("12");

		V2paymentsPaymentInformationFluidData fluidData = new V2paymentsPaymentInformationFluidData();
		fluidData.descriptor("Ymx1ZWZpbg==");
		fluidData.value("02d700801f3c20008383252a363031312a2a2a2a2a2a2a2a303030395e46444d5320202020202020202020202020202020"
				+ "2020202020205e323231322a2a2a2a2a2a2a2a3f2a3b363031312a2a2a2a2a2a2a2a303030393d323231322a2a2a2a2a2a2a2a3f"
				+ "2a7a75ad15d25217290c54b3d9d1c3868602136c68d339d52d98423391f3e631511d548fff08b414feac9ff6c6dede8fb09bae87"
				+ "0e4e32f6f462d6a75fa0a178c3bd18d0d3ade21bc7a0ea687a2eef64551751e502d97cb98dc53ea55162cdfa3954313234393238"
				+ "30303762994901000001a000731a8003");
		V2paymentsPaymentInformation paymentInformation = new V2paymentsPaymentInformation();
		paymentInformation.card(card);
		paymentInformation.fluidData(fluidData);
		request.setPaymentInformation(paymentInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		new Bluefin();
	}

	public Bluefin() throws Exception {
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
