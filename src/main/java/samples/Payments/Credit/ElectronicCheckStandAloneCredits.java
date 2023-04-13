package samples.Payments.Credit;

import java.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class ElectronicCheckStandAloneCredits {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String args[]) throws Exception {
		run();
	}

	public static PtsV2CreditsPost201Response run() {
	
		CreateCreditRequest requestObj = new CreateCreditRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("TC46125-1");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsidrefundsPaymentInformation paymentInformation = new Ptsv2paymentsidrefundsPaymentInformation();
		Ptsv2paymentsidrefundsPaymentInformationBank paymentInformationBank = new Ptsv2paymentsidrefundsPaymentInformationBank();
		Ptsv2paymentsPaymentInformationBankAccount paymentInformationBankAccount = new Ptsv2paymentsPaymentInformationBankAccount();
		paymentInformationBankAccount.type("C");
		paymentInformationBankAccount.number("4100");
		paymentInformationBankAccount.checkNumber("123456");
		paymentInformationBank.account(paymentInformationBankAccount);

		paymentInformationBank.routingNumber("071923284");
		paymentInformation.bank(paymentInformationBank);

		Ptsv2paymentsidrefundsPaymentInformationPaymentType paymentInformationPaymentType = new Ptsv2paymentsidrefundsPaymentInformationPaymentType();
		paymentInformationPaymentType.name("CHECK");
		paymentInformation.paymentType(paymentInformationPaymentType);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsidrefundsOrderInformation orderInformation = new Ptsv2paymentsidrefundsOrderInformation();
		Ptsv2paymentsidcapturesOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("100");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Ptsv2paymentsidcapturesOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsidcapturesOrderInformationBillTo();
		orderInformationBillTo.firstName("John");
		orderInformationBillTo.lastName("Doe");
		orderInformationBillTo.address1("1 Market St");
		orderInformationBillTo.locality("san francisco");
		orderInformationBillTo.administrativeArea("CA");
		orderInformationBillTo.postalCode("94105");
		orderInformationBillTo.country("US");
		orderInformationBillTo.email("test@cybs.com");
		orderInformationBillTo.phoneNumber("4158880000");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		PtsV2CreditsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CreditApi apiInstance = new CreditApi(apiClient);
			result = apiInstance.createCredit(requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			WriteLogAudit(Integer.parseInt(responseCode));
			
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}
