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

public class AuthorizationWithDecisionManagerMerchantDefinedInformation {
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
		clientReferenceInformation.code("54323007");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
		paymentInformationCard.number("4444444444444448");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2030");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("144.14");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
		orderInformationBillTo.firstName("James");
		orderInformationBillTo.lastName("Smith");
		orderInformationBillTo.address1("96, powers street");
		orderInformationBillTo.locality("Clearwater milford");
		orderInformationBillTo.administrativeArea("NH");
		orderInformationBillTo.postalCode("03055");
		orderInformationBillTo.country("US");
		orderInformationBillTo.email("test@visa.com");
		orderInformationBillTo.phoneNumber("7606160717");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);


		List <Ptsv2paymentsMerchantDefinedInformation> merchantDefinedInformation = new ArrayList <Ptsv2paymentsMerchantDefinedInformation>();
		Ptsv2paymentsMerchantDefinedInformation merchantDefinedInformation1 = new Ptsv2paymentsMerchantDefinedInformation();
		merchantDefinedInformation1.key("1");
		merchantDefinedInformation1.value("Test");
		merchantDefinedInformation.add(merchantDefinedInformation1);

		Ptsv2paymentsMerchantDefinedInformation merchantDefinedInformation2 = new Ptsv2paymentsMerchantDefinedInformation();
		merchantDefinedInformation2.key("2");
		merchantDefinedInformation2.value("Test2");
		merchantDefinedInformation.add(merchantDefinedInformation2);

		requestObj.merchantDefinedInformation(merchantDefinedInformation);

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