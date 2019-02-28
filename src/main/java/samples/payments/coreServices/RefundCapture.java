package samples.payments.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.RefundApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.PtsV2PaymentsCapturesPost201Response;
import Model.PtsV2PaymentsRefundPost201Response;
import Model.Ptsv2paymentsClientReferenceInformation;
import Model.Ptsv2paymentsidcapturesAggregatorInformation;
import Model.Ptsv2paymentsidcapturesAggregatorInformationSubMerchant;
import Model.Ptsv2paymentsidcapturesBuyerInformation;
import Model.Ptsv2paymentsidcapturesOrderInformationAmountDetails;
import Model.Ptsv2paymentsidcapturesOrderInformationBillTo;
import Model.Ptsv2paymentsidcapturesOrderInformationInvoiceDetails;
import Model.Ptsv2paymentsidcapturesOrderInformationShippingDetails;
import Model.Ptsv2paymentsidrefundsMerchantInformation;
import Model.Ptsv2paymentsidrefundsOrderInformation;
import Model.RefundCaptureRequest;

public class RefundCapture {
	private static String responseCode = null;
	private static String status = null;
	public static PtsV2PaymentsRefundPost201Response response;
	public static PtsV2PaymentsCapturesPost201Response captureResponse;
	private static Properties merchantProp;

	static RefundCaptureRequest request;

	private static RefundCaptureRequest getRequest() {
		request = new RefundCaptureRequest();

		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("test_refund_capture");
		request.setClientReferenceInformation(client);

		Ptsv2paymentsidcapturesBuyerInformation buyerInformation = new Ptsv2paymentsidcapturesBuyerInformation();
		buyerInformation.merchantCustomerId("123456abcd");
		request.buyerInformation(buyerInformation);

		Ptsv2paymentsidcapturesAggregatorInformationSubMerchant subMerchant = new Ptsv2paymentsidcapturesAggregatorInformationSubMerchant();

		subMerchant.country("US");
		subMerchant.phoneNumber("4158880000");
		subMerchant.address1("1 Market St");
		subMerchant.postalCode("94105");
		subMerchant.locality("san francisco");
		subMerchant.name("Visa Inc");
		subMerchant.administrativeArea("CA");
		subMerchant.email("test@cybs.com");

		Ptsv2paymentsidcapturesAggregatorInformation aggregatorInformation = new Ptsv2paymentsidcapturesAggregatorInformation();
		aggregatorInformation.subMerchant(subMerchant);
		aggregatorInformation.name("V-Internatio");
		aggregatorInformation.aggregatorId("123456789");
		request.setAggregatorInformation(aggregatorInformation);

		Ptsv2paymentsidcapturesOrderInformationShippingDetails shippingDetails = new Ptsv2paymentsidcapturesOrderInformationShippingDetails();
		shippingDetails.shipFromPostalCode("47404");

		Ptsv2paymentsidcapturesOrderInformationBillTo billTo = new Ptsv2paymentsidcapturesOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.phoneNumber("9999999");
		billTo.address2("Foster City");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
		billTo.locality("Ann Arbor");
		billTo.administrativeArea("MI");
		billTo.email("test@cybs.com");

		Ptsv2paymentsidcapturesOrderInformationInvoiceDetails invoiceDetails = new Ptsv2paymentsidcapturesOrderInformationInvoiceDetails();
		invoiceDetails.purchaseOrderDate("20111231");
		invoiceDetails.purchaseOrderNumber("CREDIT US");

		Ptsv2paymentsidcapturesOrderInformationAmountDetails amountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("100");
		amountDetails.exchangeRate("0.5");
		amountDetails.exchangeRateTimeStamp("2.01304E+13");
		amountDetails.currency("usd");

		Ptsv2paymentsidrefundsOrderInformation orderInformation = new Ptsv2paymentsidrefundsOrderInformation();
		orderInformation.shippingDetails(shippingDetails);
		orderInformation.billTo(billTo);
		orderInformation.invoiceDetails(invoiceDetails);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);

		Ptsv2paymentsidrefundsMerchantInformation merchantInformation = new Ptsv2paymentsidrefundsMerchantInformation();
		merchantInformation.categoryCode(1234);
		request.merchantInformation(merchantInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static void process() throws Exception {

		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient.merchantConfig = merchantConfig;	
			
			captureResponse = CapturePayment.process();
			RefundApi refundApi = new RefundApi();
			response = refundApi.refundCapture(request, captureResponse.getId());

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
