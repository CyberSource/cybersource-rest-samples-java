package samples.Payments.Payments;

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

public class SICompletionAmount0 {
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

	public static PtsV2PaymentsPost201Response run() {
	
		CreatePaymentRequest requestObj = new CreatePaymentRequest();

		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();

		List <String> actionList = new ArrayList <String>();
		actionList.add("BILLING_AGREEMENT_CREATE");
		processingInformation.actionList(actionList);

		processingInformation.capture(false);
		processingInformation.commerceIndicator("rpy");
		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
		paymentInformationCard.number("5082794233463");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2031");
		paymentInformationCard.type("061");
		paymentInformationCard.securityCode("123");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("100.0");
		orderInformationAmountDetails.currency("INR");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
		orderInformationBillTo.firstName("RTS");
		orderInformationBillTo.lastName("VDP");
		Ptsv2paymentsOrderInformationBillToCompany orderInformationBillToCompany = new Ptsv2paymentsOrderInformationBillToCompany();
		orderInformationBillToCompany.name("Visa");
		orderInformationBillTo.company(orderInformationBillToCompany);

		orderInformationBillTo.address1("201 S. Division St.");
		orderInformationBillTo.address2("test");
		orderInformationBillTo.locality("Ann Arbor");
		orderInformationBillTo.administrativeArea("MI");
		orderInformationBillTo.postalCode("48104-2201");
		orderInformationBillTo.country("SG");
		orderInformationBillTo.district("MI");
		orderInformationBillTo.buildingNumber("123");
		orderInformationBillTo.email("test@cybs.com");
		orderInformationBillTo.phoneNumber("999999999");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsDeviceInformation deviceInformation = new Ptsv2paymentsDeviceInformation();
		deviceInformation.ipAddress("10.10.10.10");
		deviceInformation.httpAcceptBrowserValue("http");
		deviceInformation.userAgentBrowserValue("safari");
		requestObj.deviceInformation(deviceInformation);

		Ptsv2paymentsAggregatorInformation aggregatorInformation = new Ptsv2paymentsAggregatorInformation();
		aggregatorInformation.name("aggregatorname");
		Ptsv2paymentsAggregatorInformationSubMerchant aggregatorInformationSubMerchant = new Ptsv2paymentsAggregatorInformationSubMerchant();
		aggregatorInformationSubMerchant.name("rupay");
		aggregatorInformation.subMerchant(aggregatorInformationSubMerchant);

		requestObj.aggregatorInformation(aggregatorInformation);

		Ptsv2paymentsConsumerAuthenticationInformation consumerAuthenticationInformation = new Ptsv2paymentsConsumerAuthenticationInformation();
		requestObj.consumerAuthenticationInformation(consumerAuthenticationInformation);

		Ptsv2paymentsInstallmentInformation installmentInformation = new Ptsv2paymentsInstallmentInformation();
		installmentInformation.paymentType("1");
		requestObj.installmentInformation(installmentInformation);

		PtsV2PaymentsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentsApi apiInstance = new PaymentsApi(apiClient);
			result = apiInstance.createPayment(requestObj);

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