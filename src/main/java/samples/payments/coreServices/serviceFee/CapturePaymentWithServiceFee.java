package samples.payments.coreServices.serviceFee;

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
import Model.Ptsv2paymentsMerchantInformationServiceFeeDescriptor;
import Model.Ptsv2paymentsidcapturesMerchantInformation;
import Model.Ptsv2paymentsidcapturesOrderInformation;
import Model.Ptsv2paymentsidcapturesOrderInformationAmountDetails;
import samples.payments.coreServices.ProcessPayment;

public class CapturePaymentWithServiceFee {
	private static String responseCode = null;
	private static String status = null;
	public static PtsV2PaymentsPost201Response paymentResponse;
	public static PtsV2PaymentsCapturesPost201Response response;
	private static Properties merchantProp;

	static CapturePaymentRequest request;

	/***
	 * This is the method to Capture payment which has service fee included.
	 * 
	 * @return
	 */
	private static CapturePaymentRequest getRequest() {
		request = new CapturePaymentRequest();

		// This is a section to set client reference information
		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("test_capture");
		request.setClientReferenceInformation(client);

		// This is a section to set Amount Details which is needed to capture the payment. Please note that it includes Service Fee Attribute
		Ptsv2paymentsidcapturesOrderInformationAmountDetails amountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("100");
		amountDetails.currency("USD");
		amountDetails.serviceFeeAmount("30.00");

		// Setting amount details to order information
		Ptsv2paymentsidcapturesOrderInformation orderInformation = new Ptsv2paymentsidcapturesOrderInformation();
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		// This is a section to set the details about service fee descriptor (It includes the details about service descriptor's name contact and state and ties it to merchant information).
		Ptsv2paymentsMerchantInformationServiceFeeDescriptor merchantInformationServiceFeeDescriptor = new Ptsv2paymentsMerchantInformationServiceFeeDescriptor();
		merchantInformationServiceFeeDescriptor.name("CyberVacations Service Fee");
		merchantInformationServiceFeeDescriptor.contact("800-999-9999");
		merchantInformationServiceFeeDescriptor.state("CA");

		// This is a section to set service fee descriptor to merchant information
		Ptsv2paymentsidcapturesMerchantInformation merchantInformation = new Ptsv2paymentsidcapturesMerchantInformation();
		merchantInformation.setServiceFeeDescriptor(merchantInformationServiceFeeDescriptor);
		request.setMerchantInformation(merchantInformation);

		return request;
	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static PtsV2PaymentsCapturesPost201Response process() throws Exception {
		/**
		 * This is a section to create payment request and get the trans id and use the trans id to capture the transaction with service fee
		 */
		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;
			
			paymentResponse = ProcessPayment.process(false);
			CaptureApi captureApi = new CaptureApi(apiClient);
			
			response = captureApi.capturePayment(request, paymentResponse.getId());
			responseCode = apiClient.responseCode;
			status = apiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response);	
			
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return response;
	}
}
