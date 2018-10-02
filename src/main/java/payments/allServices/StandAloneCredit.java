package payments.allServices;

import Api.CreditApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreateCreditRequest;
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
import Model.V2paymentsidrefundsPaymentInformation;
import Model.V2paymentsidrefundsPaymentInformationCard;

public class StandAloneCredit {

	private static String responseCode = null;
	private static String responseMsg = null;

	static CreateCreditRequest request;

	private static CreateCreditRequest getRequest() {
		request = new CreateCreditRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("TC12345");
		request.setClientReferenceInformation(client);
		
		V2paymentsidcapturesBuyerInformation buyerInformation=new V2paymentsidcapturesBuyerInformation();
		buyerInformation.merchantCustomerId("123456abcd");
		request.buyerInformation(buyerInformation);
		
		V2paymentsidcapturesAggregatorInformationSubMerchant subMerchant = new V2paymentsidcapturesAggregatorInformationSubMerchant();
		
		//commented field mising from model
		//subMerchant.cardAcceptorId("1234567890");
		subMerchant.country("US");
		subMerchant.phoneNumber("650-432-0000");
		subMerchant.address1("900 Metro Center");
		subMerchant.postalCode("94404-2775");
		subMerchant.locality("Foster City");
		subMerchant.name("Visa Inc");
		subMerchant.administrativeArea("CA");
		//subMerchant.region("PEN");
		subMerchant.email("test@cybs.com");
		
		
		V2paymentsidcapturesAggregatorInformation aggregatorInformation=new V2paymentsidcapturesAggregatorInformation();
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
		billTo.address1("test2credit");
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
		

		V2paymentsidrefundsPaymentInformationCard card = new V2paymentsidrefundsPaymentInformationCard();
		card.expirationYear("2031");
		card.number("5555555555554444");
		
		//Missing field in model
		//card.securityCode("123");
		card.expirationMonth("12");
		card.type("002");

		V2paymentsidrefundsPaymentInformation paymentInformation = new V2paymentsidrefundsPaymentInformation();
		paymentInformation.card(card);
		request.setPaymentInformation(paymentInformation);

		return request;

	}
	
	public static void main(String args[]) throws Exception {
		new StandAloneCredit();
	}

	public StandAloneCredit() throws Exception {
		process();
	} 

	public static void process() throws Exception {

		try {
			request = getRequest();
			CreditApi creditApi = new CreditApi();
			creditApi.createCredit(request);

			responseCode = ApiClient.resp;
			responseMsg = ApiClient.respmsg;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + responseMsg);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
