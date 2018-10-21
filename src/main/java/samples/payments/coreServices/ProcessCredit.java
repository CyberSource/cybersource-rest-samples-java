package samples.payments.coreServices;

import Api.CreditApi;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreateCreditRequest;
import Model.InlineResponse2014;
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

public class ProcessCredit {

	private static String responseCode = null;
	private static String status = null;
	private static InlineResponse2014 response;

	private static CreateCreditRequest request;

	private static CreateCreditRequest getRequest() {
		request = new CreateCreditRequest();

		V2paymentsClientReferenceInformation client = new V2paymentsClientReferenceInformation();
		client.code("test_credits");
		request.setClientReferenceInformation(client);

		V2paymentsidcapturesBuyerInformation buyerInformation = new V2paymentsidcapturesBuyerInformation();
		buyerInformation.merchantCustomerId("123456abcd");
		request.buyerInformation(buyerInformation);

		V2paymentsidcapturesAggregatorInformationSubMerchant subMerchant = new V2paymentsidcapturesAggregatorInformationSubMerchant();

		subMerchant.country("US");
		subMerchant.phoneNumber("4158880000");
		subMerchant.address1("1 Market St");
		subMerchant.postalCode("941055");
		subMerchant.locality("san francisco");
		subMerchant.name("Visa Inc");
		subMerchant.administrativeArea("CA");
		subMerchant.email("test@cybs.com");

		V2paymentsidcapturesAggregatorInformation aggregatorInformation = new V2paymentsidcapturesAggregatorInformation();
		aggregatorInformation.subMerchant(subMerchant);
		aggregatorInformation.name("V-Internatio");
		aggregatorInformation.aggregatorId("123456789");
		request.setAggregatorInformation(aggregatorInformation);

		V2paymentsidcapturesOrderInformationShippingDetails shippingDetails = new V2paymentsidcapturesOrderInformationShippingDetails();
		shippingDetails.shipFromPostalCode("47404");

		V2paymentsidcapturesOrderInformationBillTo billTo = new V2paymentsidcapturesOrderInformationBillTo();
		billTo.country("US");
		billTo.firstName("John");
		billTo.lastName("Deo");
		billTo.phoneNumber("4158880000");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
		billTo.locality("san francisco");
		billTo.company("Visa");
		billTo.administrativeArea("MI");
		billTo.email("test@cybs.com");
		

		V2paymentsidcapturesOrderInformationInvoiceDetails invoiceDetails = new V2paymentsidcapturesOrderInformationInvoiceDetails();
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

		V2paymentsidrefundsMerchantInformation merchantInformation = new V2paymentsidrefundsMerchantInformation();
		merchantInformation.categoryCode(1234);
		request.merchantInformation(merchantInformation);

		V2paymentsidrefundsPaymentInformationCard card = new V2paymentsidrefundsPaymentInformationCard();
		card.expirationYear("2031");
		card.number("5555555555554444");
		card.expirationMonth("12");
		card.type("002");

		V2paymentsidrefundsPaymentInformation paymentInformation = new V2paymentsidrefundsPaymentInformation();
		paymentInformation.card(card);
		request.setPaymentInformation(paymentInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static  InlineResponse2014 process() throws Exception {

		try {
			request = getRequest();
			CreditApi creditApi = new CreditApi();

			response = creditApi.createCredit(request);

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
