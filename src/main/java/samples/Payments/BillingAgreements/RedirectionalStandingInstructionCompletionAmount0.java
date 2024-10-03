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

public class RedirectionalStandingInstructionCompletionAmount0 {
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

	public static PtsV2CreateBillingAgreementPost201Response run() {
	
		CreateBillingAgreement requestObj = new CreateBillingAgreement();

		Ptsv2billingagreementsAggregatorInformation aggregatorInformation = new Ptsv2billingagreementsAggregatorInformation();
		aggregatorInformation.name("aggregatorname");
		Ptsv2paymentsAggregatorInformationSubMerchant aggregatorInformationSubMerchant = new Ptsv2paymentsAggregatorInformationSubMerchant();
		aggregatorInformationSubMerchant.name("rupay");
		aggregatorInformation.subMerchant(aggregatorInformationSubMerchant);

		requestObj.aggregatorInformation(aggregatorInformation);

		Ptsv2billingagreementsConsumerAuthenticationInformation consumerAuthenticationInformation = new Ptsv2billingagreementsConsumerAuthenticationInformation();
		consumerAuthenticationInformation.cavv("MTAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDI1MjM2");
		consumerAuthenticationInformation.xid("OTE0OTE2MzI5MzE1MDUyOTU4Mjc=");
		requestObj.consumerAuthenticationInformation(consumerAuthenticationInformation);

		Ptsv2billingagreementsDeviceInformation deviceInformation = new Ptsv2billingagreementsDeviceInformation();
		deviceInformation.httpAcceptBrowserValue("http");
		deviceInformation.ipAddress("10.10.10.10");
		deviceInformation.userAgentBrowserValue("safari");
		requestObj.deviceInformation(deviceInformation);

		Ptsv2billingagreementsInstallmentInformation installmentInformation = new Ptsv2billingagreementsInstallmentInformation();
		installmentInformation.paymentType("1");
		requestObj.installmentInformation(installmentInformation);

		Ptsv2billingagreementsOrderInformation orderInformation = new Ptsv2billingagreementsOrderInformation();
		Ptsv2paymentsidreversalsReversalInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsidreversalsReversalInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("00.00");
		orderInformationAmountDetails.currency("INR");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		Ptsv2billingagreementsPaymentInformation paymentInformation = new Ptsv2billingagreementsPaymentInformation();
		Ptsv2billingagreementsPaymentInformationCard paymentInformationCard = new Ptsv2billingagreementsPaymentInformationCard();
		paymentInformationCard.expirationMonth("08");
		paymentInformationCard.expirationYear("2026");
		paymentInformationCard.number("50823043205909");
		paymentInformationCard.securityCode("123");
		paymentInformationCard.type("061");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2billingagreementsProcessingInformation processingInformation = new Ptsv2billingagreementsProcessingInformation();
		processingInformation.commerceIndicator("rpy");
		requestObj.processingInformation(processingInformation);

		PtsV2CreateBillingAgreementPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			BillingAgreementsApi apiInstance = new BillingAgreementsApi(apiClient);
			result = apiInstance.billingAgreementsRegistration(requestObj);

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