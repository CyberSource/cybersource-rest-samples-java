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

public class SetupCompletionWithCardNumber {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}
	public static RiskV1AuthenticationSetupsPost201Response run() {
	
		PayerAuthSetupRequest requestObj = new PayerAuthSetupRequest();

		Riskv1decisionsClientReferenceInformation clientReferenceInformation = new Riskv1decisionsClientReferenceInformation();
		clientReferenceInformation.code("cybs_test");
		Riskv1decisionsClientReferenceInformationPartner clientReferenceInformationPartner = new Riskv1decisionsClientReferenceInformationPartner();
		clientReferenceInformationPartner.developerId("7891234");
		clientReferenceInformationPartner.solutionId("89012345");
		clientReferenceInformation.partner(clientReferenceInformationPartner);

		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1authenticationsetupsPaymentInformation paymentInformation = new Riskv1authenticationsetupsPaymentInformation();
		Riskv1authenticationsetupsPaymentInformationCard paymentInformationCard = new Riskv1authenticationsetupsPaymentInformationCard();
		paymentInformationCard.type("001");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2025");
		paymentInformationCard.number("4000000000000101");
		paymentInformation.card(paymentInformationCard);

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
