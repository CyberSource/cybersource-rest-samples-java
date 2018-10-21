package samples.payments.coreServices;

import Api.CaptureApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CapturePaymentRequest;
import Model.InlineResponse201;
import Model.InlineResponse2012;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsidcapturesOrderInformation;
import Model.V2paymentsidcapturesOrderInformationAmountDetails;
import Model.V2paymentsidcapturesOrderInformationBillTo;
import Model.V2paymentsidcapturesPointOfSaleInformation;

public class CapturePayment {

	private static String responseCode = null;
	private static String status = null;
	public static InlineResponse201 paymentResponse;
	public static InlineResponse2012 response;

	static CapturePaymentRequest request;

	private static CapturePaymentRequest getRequest() {
		request = new CapturePaymentRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("test_capture");
		request.setClientReferenceInformation(client);

		V2paymentsidcapturesPointOfSaleInformation saleInformation = new V2paymentsidcapturesPointOfSaleInformation();
		request.pointOfSaleInformation(saleInformation);

		V2paymentsidcapturesOrderInformationBillTo billTo = new V2paymentsidcapturesOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
		billTo.locality("san francisco");
		billTo.administrativeArea("CA");
		billTo.email("test@cybs.com");

		V2paymentsidcapturesOrderInformationAmountDetails amountDetails = new V2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("100.00");
		amountDetails.currency("USD");

		V2paymentsidcapturesOrderInformation orderInformation = new V2paymentsidcapturesOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static InlineResponse2012 process() throws Exception {

		try {
			request = getRequest();
			CaptureApi captureApi = new CaptureApi();

			paymentResponse = ProcessPayment.process(false);

			response = captureApi.capturePayment(request, paymentResponse.getId());

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response.getId());

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
