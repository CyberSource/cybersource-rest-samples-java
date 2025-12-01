package samples.RecurringBillingSubscriptions.Subscriptions;

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

public class CreateSubscription {
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

	public static CreateSubscriptionResponse run() {
	
		CreateSubscriptionRequest requestObj = new CreateSubscriptionRequest();

		Rbsv1subscriptionsClientReferenceInformation clientReferenceInformation = new Rbsv1subscriptionsClientReferenceInformation();
		clientReferenceInformation.code("TC501713");
		Rbsv1subscriptionsClientReferenceInformationPartner clientReferenceInformationPartner = new Rbsv1subscriptionsClientReferenceInformationPartner();
		clientReferenceInformationPartner.developerId("ABCD1234");
		clientReferenceInformationPartner.solutionId("GEF1234");
		clientReferenceInformation.partner(clientReferenceInformationPartner);

		clientReferenceInformation.applicationName("CYBS-SDK");
		clientReferenceInformation.applicationVersion("v1");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Rbsv1subscriptionsProcessingInformation processingInformation = new Rbsv1subscriptionsProcessingInformation();
		processingInformation.commerceIndicator("recurring");
		Rbsv1subscriptionsProcessingInformationAuthorizationOptions processingInformationAuthorizationOptions = new Rbsv1subscriptionsProcessingInformationAuthorizationOptions();
		Rbsv1subscriptionsProcessingInformationAuthorizationOptionsInitiator processingInformationAuthorizationOptionsInitiator = new Rbsv1subscriptionsProcessingInformationAuthorizationOptionsInitiator();
		processingInformationAuthorizationOptionsInitiator.type("merchant");
		processingInformationAuthorizationOptions.initiator(processingInformationAuthorizationOptionsInitiator);

		processingInformation.authorizationOptions(processingInformationAuthorizationOptions);

		requestObj.processingInformation(processingInformation);

		Rbsv1subscriptionsSubscriptionInformation subscriptionInformation = new Rbsv1subscriptionsSubscriptionInformation();
		subscriptionInformation.planId("6868912495476705603955");
		subscriptionInformation.name("Subscription with PlanId");
		subscriptionInformation.startDate("2024-06-11");
		requestObj.subscriptionInformation(subscriptionInformation);

		Rbsv1subscriptionsPaymentInformation paymentInformation = new Rbsv1subscriptionsPaymentInformation();
		Rbsv1subscriptionsPaymentInformationCustomer paymentInformationCustomer = new Rbsv1subscriptionsPaymentInformationCustomer();
		paymentInformationCustomer.id("C24F5921EB870D99E053AF598E0A4105");
		paymentInformation.customer(paymentInformationCustomer);

		requestObj.paymentInformation(paymentInformation);

		CreateSubscriptionResponse response = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			SubscriptionsApi apiInstance = new SubscriptionsApi(apiClient);
			response = apiInstance.createSubscription(requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			WriteLogAudit(Integer.parseInt(responseCode));
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}