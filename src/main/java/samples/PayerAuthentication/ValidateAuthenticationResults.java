package samples.PayerAuthentication;

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

public class ValidateAuthenticationResults {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static RiskV1AuthenticationResultsPost201Response run() {
	
		ValidateRequest requestObj = new ValidateRequest();

		Riskv1decisionsClientReferenceInformation clientReferenceInformation = new Riskv1decisionsClientReferenceInformation();
		clientReferenceInformation.code("pavalidatecheck");
		Riskv1decisionsClientReferenceInformationPartner clientReferenceInformationPartner = new Riskv1decisionsClientReferenceInformationPartner();
		clientReferenceInformationPartner.developerId("7891234");
		clientReferenceInformationPartner.solutionId("89012345");
		clientReferenceInformation.partner(clientReferenceInformationPartner);

		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1authenticationresultsOrderInformation orderInformation = new Riskv1authenticationresultsOrderInformation();
		Riskv1authenticationresultsOrderInformationAmountDetails orderInformationAmountDetails = new Riskv1authenticationresultsOrderInformationAmountDetails();
		orderInformationAmountDetails.currency("USD");
		orderInformationAmountDetails.totalAmount("200.00");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		Riskv1authenticationresultsPaymentInformation paymentInformation = new Riskv1authenticationresultsPaymentInformation();
		Riskv1authenticationresultsPaymentInformationCard paymentInformationCard = new Riskv1authenticationresultsPaymentInformationCard();
		paymentInformationCard.type("002");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2025");
		paymentInformationCard.number("5200000000000007");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		Riskv1authenticationresultsConsumerAuthenticationInformation consumerAuthenticationInformation = new Riskv1authenticationresultsConsumerAuthenticationInformation();
		consumerAuthenticationInformation.authenticationTransactionId("PYffv9G3sa1e0CQr5fV0");
		requestObj.consumerAuthenticationInformation(consumerAuthenticationInformation);

		RiskV1AuthenticationResultsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PayerAuthenticationApi apiInstance = new PayerAuthenticationApi(apiClient);
			result = apiInstance.validateAuthenticationResults(requestObj);

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
