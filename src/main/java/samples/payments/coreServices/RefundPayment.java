package samples.payments.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.RefundApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.PtsV2PaymentsPost201Response;
import Model.PtsV2PaymentsRefundPost201Response;
import Model.Ptsv2paymentsClientReferenceInformation;
import Model.Ptsv2paymentsidcapturesOrderInformationAmountDetails;
import Model.Ptsv2paymentsidrefundsOrderInformation;
import Model.RefundPaymentRequest;

public class RefundPayment {
	private  PtsV2PaymentsRefundPost201Response response;
	private  PtsV2PaymentsPost201Response paymentResponse;
	private Properties merchantProp;
	private RefundPaymentRequest request;

	private RefundPaymentRequest getRequest() {
		request = new RefundPaymentRequest();

		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("test_refund_payment");
		request.setClientReferenceInformation(client);

		Ptsv2paymentsidcapturesOrderInformationAmountDetails amountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("102.21");
		amountDetails.currency("USD");

		Ptsv2paymentsidrefundsOrderInformation orderInformation = new Ptsv2paymentsidrefundsOrderInformation();
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		RefundPayment refundPayment=new RefundPayment();
		refundPayment.process();
	}

	public  PtsV2PaymentsRefundPost201Response process() throws Exception {
		String className=RefundPayment.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = new ApiClient();
		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ProcessPayment processPayment = new ProcessPayment();
			paymentResponse = processPayment.process(Boolean.TRUE);
			if (paymentResponse != null) {
				RefundApi refundApi = new RefundApi(merchantConfig);
				apiClient=Invokers.Configuration.getDefaultApiClient();
				response = refundApi.refundPayment(request, paymentResponse.getId());
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
