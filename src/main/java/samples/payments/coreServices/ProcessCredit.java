package samples.payments.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.CreditApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreateCreditRequest;
import Model.PtsV2CreditsPost201Response;
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
import Model.Ptsv2paymentsidrefundsPaymentInformation;
import Model.Ptsv2paymentsidrefundsPaymentInformationCard;

public class ProcessCredit {

	private  PtsV2CreditsPost201Response response;
	private  Properties merchantProp;

	private  CreateCreditRequest request;

	private  CreateCreditRequest getRequest() {
		request = new CreateCreditRequest();

		Ptsv2paymentsClientReferenceInformation client = new Ptsv2paymentsClientReferenceInformation();
		client.code("test_credits");
		request.setClientReferenceInformation(client);

		Ptsv2paymentsidcapturesBuyerInformation buyerInformation = new Ptsv2paymentsidcapturesBuyerInformation();
		buyerInformation.merchantCustomerId("123456abcd");
		request.buyerInformation(buyerInformation);

		Ptsv2paymentsidcapturesAggregatorInformationSubMerchant subMerchant = new Ptsv2paymentsidcapturesAggregatorInformationSubMerchant();

		subMerchant.country("US");
		subMerchant.phoneNumber("4158880000");
		subMerchant.address1("1 Market St");
		subMerchant.postalCode("941055");
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
		billTo.phoneNumber("4158880000");
		billTo.address1("1 Market St");
		billTo.postalCode("94105");
		billTo.locality("san francisco");
		billTo.company("Visa");
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

		Ptsv2paymentsidrefundsPaymentInformationCard card = new Ptsv2paymentsidrefundsPaymentInformationCard();
		card.expirationYear("2031");
		card.number("5555555555554444");
		card.expirationMonth("12");
		card.type("002");

		Ptsv2paymentsidrefundsPaymentInformation paymentInformation = new Ptsv2paymentsidrefundsPaymentInformation();
		paymentInformation.card(card);
		request.setPaymentInformation(paymentInformation);

		return request;

	}

	public static void main(String args[]) throws Exception {
		ProcessCredit processCredit= new ProcessCredit();
		processCredit.process();
	}

	public PtsV2CreditsPost201Response process() throws Exception {
		String className=ProcessCredit.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			CreditApi creditApi = new CreditApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			response = creditApi.createCredit(request);
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
