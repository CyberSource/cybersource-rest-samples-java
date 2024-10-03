package samples.Payments.BillingAgreements;

import java.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.math.BigDecimal;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Invokers.ApiResponse;
import Model.*;

public class StandingInstructionCompletionAmount0 {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String[] args) {
		run();
	}

	public static PtsV2CreditsPost201Response1 run() {
		String id = "";
		IntimateBillingAgreement requestObj = new IntimateBillingAgreement();

		Ptsv2billingagreementsInstallmentInformation installmentInformation = new Ptsv2billingagreementsInstallmentInformation();
		installmentInformation.alertPreference("SMS");
		installmentInformation.firstInstallmentDate("2111");
		installmentInformation.identifier("1000000000");
		installmentInformation.lastInstallmentDate("3110");
		installmentInformation.maxAmount("1000");
		installmentInformation.minAmount("100");
		installmentInformation.paymentType("1");
		installmentInformation.preferredDay("1");
		installmentInformation.sequence(2);
		requestObj.installmentInformation(installmentInformation);

		Ptsv2billingagreementsMerchantInformation merchantInformation = new Ptsv2billingagreementsMerchantInformation();
		merchantInformation.transactionLocalDateTime("20211216124549");
		requestObj.merchantInformation(merchantInformation);

		Ptsv2billingagreementsOrderInformation orderInformation = new Ptsv2billingagreementsOrderInformation();
		Ptsv2paymentsidreversalsReversalInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsidreversalsReversalInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("00");
		orderInformationAmountDetails.currency("INR");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		Ptsv2billingagreementsPaymentInformation paymentInformation = new Ptsv2billingagreementsPaymentInformation();
		Ptsv2billingagreementsPaymentInformationCard paymentInformationCard = new Ptsv2billingagreementsPaymentInformationCard();
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2031");
		paymentInformationCard.number("5082302886091");
		paymentInformationCard.securityCode("123");
		paymentInformationCard.type("061");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		PtsV2CreditsPost201Response1 result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			BillingAgreementsApi apiInstance = new BillingAgreementsApi(apiClient);
			result = apiInstance.billingAgreementsIntimation(requestObj, id);

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