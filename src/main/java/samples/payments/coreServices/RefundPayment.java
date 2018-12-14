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
	private static String responseCode = null;
	private static String status = null;
	public static PtsV2PaymentsRefundPost201Response response;
	public static PtsV2PaymentsPost201Response paymentResponse;
	private static Properties merchantProp;

	static RefundPaymentRequest request;

	private static RefundPaymentRequest getRequest() {
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
		process();
	}

	public static PtsV2PaymentsRefundPost201Response process() throws Exception {

		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient=new ApiClient(merchantConfig);
			
			paymentResponse = ProcessPayment.process(true);
			RefundApi refundApi = new RefundApi();
			response = refundApi.refundPayment(request, paymentResponse.getId());

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response);

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return response;
	}

}
