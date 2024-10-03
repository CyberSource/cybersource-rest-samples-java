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

public class AccountFundingTransaction {
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

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("RTS-AFT3-8");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
		processingInformation.capture(true);
		processingInformation.businessApplicationId("AA");
		processingInformation.commerceIndicator("internet");
		Ptsv2paymentsProcessingInformationAuthorizationOptions processingInformationAuthorizationOptions = new Ptsv2paymentsProcessingInformationAuthorizationOptions();
		processingInformationAuthorizationOptions.ignoreAvsResult(true);
		processingInformationAuthorizationOptions.ignoreCvResult(false);
		processingInformationAuthorizationOptions.aftIndicator(true);
		processingInformation.authorizationOptions(processingInformationAuthorizationOptions);

		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
		paymentInformationCard.number("4539791001730106");
		paymentInformationCard.expirationMonth("03");
		paymentInformationCard.expirationYear("2025");
		paymentInformationCard.type("001");
		paymentInformationCard.securityCode("351");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("28.00");
		orderInformationAmountDetails.currency("GBP");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
		orderInformationBillTo.firstName("Joe");
		orderInformationBillTo.lastName("Root");
		orderInformationBillTo.middleName("S");
		orderInformationBillTo.address1("34 Orchard Grove");
		orderInformationBillTo.locality("Southampton");
		orderInformationBillTo.postalCode("DE6 1BE");
		orderInformationBillTo.country("GB");
		orderInformationBillTo.email("test@cybs.com");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsSenderInformation senderInformation = new Ptsv2paymentsSenderInformation();
		senderInformation.firstName("Joe");
		senderInformation.lastName("Root");
		senderInformation.middleName("S");
		senderInformation.address1("34 Orchard Grove");
		senderInformation.locality("Southampton");
		senderInformation.countryCode("GB");
		requestObj.senderInformation(senderInformation);

		Ptsv2paymentsRecipientInformation recipientInformation = new Ptsv2paymentsRecipientInformation();
		recipientInformation.accountId("4929421234600821");
		recipientInformation.firstName("Ben");
		recipientInformation.lastName("Stokes");
		recipientInformation.middleName("A");
		requestObj.recipientInformation(recipientInformation);

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