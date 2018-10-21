package samples.payments.coreServices;

import Api.RefundApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.InlineResponse2012;
import Model.InlineResponse2013;
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

public class RefundCapture {
	private static String responseCode = null;
	private static String status=null;
	public static InlineResponse2013 response;
	public static InlineResponse2012 captureResponse;
	
	static RefundCaptureRequest request;

	private static RefundCaptureRequest getRequest() {
		request = new RefundCaptureRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("test_refund_capture");
		request.setClientReferenceInformation(client);

		V2paymentsidcapturesBuyerInformation buyerInformation = new V2paymentsidcapturesBuyerInformation();
		buyerInformation.merchantCustomerId("123456abcd");
		request.buyerInformation(buyerInformation);

		V2paymentsidcapturesAggregatorInformationSubMerchant subMerchant = new V2paymentsidcapturesAggregatorInformationSubMerchant();

		subMerchant.country("US");
		subMerchant.phoneNumber("4158880000");
		subMerchant.address1("1 Market St");
		subMerchant.postalCode("94105");
		subMerchant.locality("san francisco");
		subMerchant.name("Visa Inc");
		subMerchant.administrativeArea("CA");
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
		billTo.lastName("Deo");
		billTo.phoneNumber("9999999");
		billTo.address2("Foster City");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
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
		process();
	}

	public static  void process() throws Exception {

		try {
			request = getRequest();
			RefundApi refundApi = new RefundApi();
			 
		    captureResponse=CapturePayment.process();
		    
			response=refundApi.refundCapture(request, captureResponse.getId());

			responseCode=ApiClient.responseCode;
			status=ApiClient.status;
			
			System.out.println("ResponseCode :" +responseCode);
			System.out.println("Status :" +status);
			System.out.println(response.getId());

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
