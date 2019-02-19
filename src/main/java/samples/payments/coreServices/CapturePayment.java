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

	private  PtsV2PaymentsPost201Response paymentResponse;
	private  PtsV2PaymentsCapturesPost201Response response;
	private  Properties merchantProp;
	private  CapturePaymentRequest request;

	private  CapturePaymentRequest getRequest() {
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
		CapturePayment capturePayment=new CapturePayment();
		capturePayment.process();
	}

	public  PtsV2PaymentsCapturesPost201Response process() throws Exception {
		String className=CapturePayment.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient=new ApiClient();
		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ProcessPayment processPayment = new ProcessPayment();
			paymentResponse = processPayment.process(Boolean.FALSE);
			
			if (paymentResponse != null) {
				CaptureApi captureApi = new CaptureApi(merchantConfig);
				apiClient=Invokers.Configuration.getDefaultApiClient();
				response = captureApi.capturePayment(request, paymentResponse.getId());
			}
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " +className+": "+apiClient.getRespBody()+"\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API REQUEST BODY:");
			System.out.println(apiClient.getRequestBody() + "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getRespBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE:" + className + "\n");
		}
		return response;
	}

}
