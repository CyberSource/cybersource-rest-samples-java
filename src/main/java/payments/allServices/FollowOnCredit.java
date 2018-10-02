package payments.allServices;

import Api.RefundApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.RefundCaptureRequest;
import Model.V2paymentsClientReferenceInformation;
import Model.V2paymentsidcapturesAggregatorInformation;
import Model.V2paymentsidcapturesAggregatorInformationSubMerchant;
import Model.V2paymentsidcapturesBuyerInformation;
import Model.V2paymentsidcapturesOrderInformationAmountDetails;
import Model.V2paymentsidcapturesOrderInformationBillTo;
import Model.V2paymentsidcapturesOrderInformationInvoiceDetails;
import Model.V2paymentsidcapturesOrderInformationShippingDetails;
import Model.V2paymentsidrefundsMerchantInformation;
import Model.V2paymentsidrefundsOrderInformation;

public class FollowOnCredit {
	private static String getId = "5374223514976497403001";
	private static String responseCode = null;
	private static String responseMsg = null;

	static RefundCaptureRequest request;

	private static RefundCaptureRequest getRequest() {
		request = new RefundCaptureRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("Testing-VDP-Payments-Refund");
		request.setClientReferenceInformation(client);

		V2paymentsidcapturesBuyerInformation buyerInformation = new V2paymentsidcapturesBuyerInformation();
		buyerInformation.merchantCustomerId("123456abcd");
		request.buyerInformation(buyerInformation);

		V2paymentsidcapturesAggregatorInformationSubMerchant subMerchant = new V2paymentsidcapturesAggregatorInformationSubMerchant();

		// commented field mising from model
		// subMerchant.cardAcceptorId("1234567890");
		subMerchant.country("US");
		subMerchant.phoneNumber("650-432-0000");
		subMerchant.address1("900 Metro Center");
		subMerchant.postalCode("94404-2775");
		subMerchant.locality("Foster City");
		subMerchant.name("Visa Inc");
		subMerchant.administrativeArea("CA");
		// subMerchant.region("PEN");
		subMerchant.email("test@cybs.com");
		
		V2paymentsidcapturesAggregatorInformation aggregatorInformation = new V2paymentsidcapturesAggregatorInformation();
		aggregatorInformation.subMerchant(subMerchant);
		aggregatorInformation.name("V-Internatio");
		aggregatorInformation.aggregatorId("123456789");
		request.setAggregatorInformation(aggregatorInformation);
		
		V2paymentsidcapturesOrderInformationShippingDetails shippingDetails=new V2paymentsidcapturesOrderInformationShippingDetails();
		shippingDetails.shipFromPostalCode("47404");
		
		V2paymentsidcapturesOrderInformationBillTo billTo = new V2paymentsidcapturesOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("John");
		billTo.lastName("Test");
		billTo.phoneNumber("9999999");
		billTo.address2("test2credit");
		billTo.address1("testcredit");
		billTo.postalCode("48104-2201");
		billTo.locality("Ann Arbor");
		billTo.company("Visa");
		billTo.administrativeArea("MI");
		billTo.email("test@cybs.com");
		
		V2paymentsidcapturesOrderInformationInvoiceDetails invoiceDetails=new V2paymentsidcapturesOrderInformationInvoiceDetails();
		invoiceDetails.purchaseOrderDate("20111231");
		invoiceDetails.purchaseOrderNumber("CREDIT US");

		V2paymentsidcapturesOrderInformationAmountDetails amountDetails = new V2paymentsidcapturesOrderInformationAmountDetails();
		amountDetails.totalAmount("100");
		amountDetails.exchangeRate("0.5");
		amountDetails.exchangeRateTimeStamp("2.01304E+13");
		amountDetails.currency("usd");

		V2paymentsidrefundsOrderInformation orderInformation = new V2paymentsidrefundsOrderInformation();
		orderInformation.shippingDetails(shippingDetails);
		orderInformation.billTo(billTo);
		orderInformation.invoiceDetails(invoiceDetails);
		orderInformation.amountDetails(amountDetails);
		request.setOrderInformation(orderInformation);
		
		V2paymentsidrefundsMerchantInformation merchantInformation=new V2paymentsidrefundsMerchantInformation();
		merchantInformation.categoryCode(1234);
		request.merchantInformation(merchantInformation);

		return request;

	}
	
	public static void main(String args[]) throws Exception {
		new FollowOnCredit();
	}

	public FollowOnCredit() throws Exception {
		process();
	} 

	public static void process() throws Exception {

		try {
			request = getRequest();
			RefundApi refundApi = new RefundApi();
			refundApi.refundCapture(request, getId);

			responseCode = ApiClient.resp;
			responseMsg = ApiClient.respmsg;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMsg);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
