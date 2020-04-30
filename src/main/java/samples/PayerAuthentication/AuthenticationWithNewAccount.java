package samples.PayerAuthentication.PayerAuthentication;

import java.*;
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

public class AuthenticationWithNewAccount {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static RiskV1AuthenticationsPost201Response run() {
	
		CheckPayerAuthEnrollmentRequest requestObj = new CheckPayerAuthEnrollmentRequest();

		Riskv1authenticationsClientReferenceInformation clientReferenceInformation = new Riskv1authenticationsClientReferenceInformation();
		clientReferenceInformation.code("New Account");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1authenticationsOrderInformation orderInformation = new Riskv1authenticationsOrderInformation();
		Riskv1authenticationsOrderInformationAmountDetails orderInformationAmountDetails = new Riskv1authenticationsOrderInformationAmountDetails();
		orderInformationAmountDetails.currency("USD");
		orderInformationAmountDetails.totalAmount("10.99");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Riskv1authenticationsOrderInformationBillTo orderInformationBillTo = new Riskv1authenticationsOrderInformationBillTo();
		orderInformationBillTo.address1("1 Market St");
		orderInformationBillTo.address2("Address 2");
		orderInformationBillTo.administrativeArea("CA");
		orderInformationBillTo.country("US");
		orderInformationBillTo.locality("san francisco");
		orderInformationBillTo.firstName("John");
		orderInformationBillTo.lastName("Doe");
		orderInformationBillTo.phoneNumber("4158880000");
		orderInformationBillTo.email("test@cybs.com");
		orderInformationBillTo.postalCode("94105");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		Riskv1authenticationsPaymentInformation paymentInformation = new Riskv1authenticationsPaymentInformation();
		Riskv1authenticationsPaymentInformationCard paymentInformationCard = new Riskv1authenticationsPaymentInformationCard();
		paymentInformationCard.type("001");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2025");
		paymentInformationCard.number("4000990000000004");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		Riskv1authenticationsConsumerAuthenticationInformation consumerAuthenticationInformation = new Riskv1authenticationsConsumerAuthenticationInformation();
		consumerAuthenticationInformation.transactionMode("MOTO");
		requestObj.consumerAuthenticationInformation(consumerAuthenticationInformation);

		Riskv1authenticationsRiskInformation riskInformation = new Riskv1authenticationsRiskInformation();
		Ptsv2paymentsRiskInformationBuyerHistory riskInformationBuyerHistory = new Ptsv2paymentsRiskInformationBuyerHistory();
		Ptsv2paymentsRiskInformationBuyerHistoryCustomerAccount riskInformationBuyerHistoryCustomerAccount = new Ptsv2paymentsRiskInformationBuyerHistoryCustomerAccount();
		riskInformationBuyerHistoryCustomerAccount.creationHistory("NEW_ACCOUNT");
		riskInformationBuyerHistory.customerAccount(riskInformationBuyerHistoryCustomerAccount);

		Ptsv2paymentsRiskInformationBuyerHistoryAccountHistory riskInformationBuyerHistoryAccountHistory = new Ptsv2paymentsRiskInformationBuyerHistoryAccountHistory();
		riskInformationBuyerHistoryAccountHistory.firstUseOfShippingAddress(false);
		riskInformationBuyerHistory.accountHistory(riskInformationBuyerHistoryAccountHistory);

		riskInformation.buyerHistory(riskInformationBuyerHistory);

		requestObj.riskInformation(riskInformation);

		RiskV1AuthenticationsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PayerAuthenticationApi apiInstance = new PayerAuthenticationApi(apiClient);
			result = apiInstance.checkPayerAuthEnrollment(requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}
