package samples.PayerAuthentication;

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

public class SetupCompletionWithTokenizedCard {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static RiskV1AuthenticationSetupsPost201Response run() {
	
		PayerAuthSetupRequest requestObj = new PayerAuthSetupRequest();

		Riskv1decisionsClientReferenceInformation clientReferenceInformation = new Riskv1decisionsClientReferenceInformation();
		clientReferenceInformation.code("cybs_test");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1authenticationsetupsPaymentInformation paymentInformation = new Riskv1authenticationsetupsPaymentInformation();
		Riskv1authenticationsetupsPaymentInformationTokenizedCard paymentInformationTokenizedCard = new Riskv1authenticationsetupsPaymentInformationTokenizedCard();
		paymentInformationTokenizedCard.transactionType("1");
		paymentInformationTokenizedCard.type("001");
		paymentInformationTokenizedCard.expirationMonth("11");
		paymentInformationTokenizedCard.expirationYear("2025");
		paymentInformationTokenizedCard.number("4111111111111111");
		paymentInformation.tokenizedCard(paymentInformationTokenizedCard);

		requestObj.paymentInformation(paymentInformation);

		RiskV1AuthenticationSetupsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PayerAuthenticationApi apiInstance = new PayerAuthenticationApi(apiClient);
			result = apiInstance.payerAuthSetup(requestObj);

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
