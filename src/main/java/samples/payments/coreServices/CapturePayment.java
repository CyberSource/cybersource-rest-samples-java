package samples.payments.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.CaptureApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CapturePaymentRequest;
import Model.PtsV2PaymentsCapturesPost201Response;
import Model.PtsV2PaymentsPost201Response;
import Model.Ptsv2paymentsClientReferenceInformation;
import Model.Ptsv2paymentsidcapturesOrderInformation;
import Model.Ptsv2paymentsidcapturesOrderInformationAmountDetails;
import Model.Ptsv2paymentsidcapturesOrderInformationBillTo;
import Model.Ptsv2paymentsidcapturesPointOfSaleInformation;

public class CapturePayment {

	private static String responseCode = null;
	private static String status = null;
	public static PtsV2PaymentsPost201Response paymentResponse;
	public static PtsV2PaymentsCapturesPost201Response response;
	private static Properties merchantProp;

	static CapturePaymentRequest request;

	private static CapturePaymentRequest getRequest() {
		request = new CapturePaymentRequest();

		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("test_capture");
		request.setClientReferenceInformation(client);

		Ptsv2paymentsidcapturesPointOfSaleInformation saleInformation = new Ptsv2paymentsidcapturesPointOfSaleInformation();
		request.pointOfSaleInformation(saleInformation);

		Ptsv2paymentsidcapturesOrderInformationBillTo billTo = new Ptsv2paymentsidcapturesOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
		billTo.locality("san francisco");
		billTo.administrativeArea("CA");
		billTo.email("test@cybs.com");

		Ptsv2paymentsidcapturesOrderInformationAmountDetails amountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("100.00");
		amountDetails.currency("USD");

		Ptsv2paymentsidcapturesOrderInformation orderInformation = new Ptsv2paymentsidcapturesOrderInformation();
		orderInformation.billTo(billTo);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static PtsV2PaymentsCapturesPost201Response process() throws Exception {

		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			paymentResponse = ProcessPayment.process(false);
			CaptureApi captureApi = new CaptureApi();
			response = captureApi.capturePayment(request,merchantConfig, paymentResponse.getId());

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
