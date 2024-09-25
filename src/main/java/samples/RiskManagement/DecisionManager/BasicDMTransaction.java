package samples.RiskManagement.DecisionManager;

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

public class BasicDMTransaction {
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

	public static RiskV1DecisionsPost201Response run() {
	
		CreateBundledDecisionManagerCaseRequest requestObj = new CreateBundledDecisionManagerCaseRequest();

		Riskv1decisionsClientReferenceInformation clientReferenceInformation = new Riskv1decisionsClientReferenceInformation();
		clientReferenceInformation.code("54323007");
		clientReferenceInformation.comments("decision manager case");
		Riskv1decisionsClientReferenceInformationPartner clientReferenceInformationPartner = new Riskv1decisionsClientReferenceInformationPartner();
		clientReferenceInformationPartner.developerId("7891234");
		clientReferenceInformationPartner.solutionId("89012345");
		clientReferenceInformation.partner(clientReferenceInformationPartner);

		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1decisionsPaymentInformation paymentInformation = new Riskv1decisionsPaymentInformation();
		Riskv1decisionsPaymentInformationCard paymentInformationCard = new Riskv1decisionsPaymentInformationCard();
		paymentInformationCard.number("4111111111111111");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2030");

		requestObj.paymentInformation(paymentInformation);

		Riskv1decisionsOrderInformation orderInformation = new Riskv1decisionsOrderInformation();
		Riskv1decisionsOrderInformationAmountDetails orderInformationAmountDetails = new Riskv1decisionsOrderInformationAmountDetails();
		orderInformationAmountDetails.currency("USD");
		orderInformationAmountDetails.totalAmount("144.14");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Riskv1decisionsOrderInformationBillTo orderInformationBillTo = new Riskv1decisionsOrderInformationBillTo();
		orderInformationBillTo.address1("1-1-2 Oshiage");
		orderInformationBillTo.administrativeArea("Tokyo");
		orderInformationBillTo.country("JP");
		orderInformationBillTo.locality("Sumida-ku");
		orderInformationBillTo.firstName("Taro");
		orderInformationBillTo.lastName("Yamada");
		orderInformationBillTo.phoneNumber("0312345678");
		orderInformationBillTo.email("taro.yamada@example.jp");
		orderInformationBillTo.postalCode("131-0045");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		RiskV1DecisionsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			DecisionManagerApi apiInstance = new DecisionManagerApi(apiClient);
			result = apiInstance.createBundledDecisionManagerCase(requestObj);

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
