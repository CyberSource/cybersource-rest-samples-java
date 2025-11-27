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

public class UpdateSubscription {
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

	public static void run() {
		
		String id = CreateSubscription.run().getId();
		
		Model.UpdateSubscription requestObj = new Model.UpdateSubscription();

        GetAllSubscriptionsResponseClientReferenceInformation clientReferenceInformation = new GetAllSubscriptionsResponseClientReferenceInformation();
		clientReferenceInformation.code("APGHU");
        GetAllSubscriptionsResponseClientReferenceInformation clientReferenceInformationPartner = new GetAllSubscriptionsResponseClientReferenceInformation();
//		clientReferenceInformationPartner.developerId("ABCD1234");
//		clientReferenceInformationPartner.solutionId("GEF1234");
//		clientReferenceInformation.partner(clientReferenceInformationPartner);

		requestObj.clientReferenceInformation(clientReferenceInformation);

		Rbsv1subscriptionsProcessingInformation processingInformation = new Rbsv1subscriptionsProcessingInformation();
		Rbsv1subscriptionsProcessingInformationAuthorizationOptions processingInformationAuthorizationOptions = new Rbsv1subscriptionsProcessingInformationAuthorizationOptions();
		Rbsv1subscriptionsProcessingInformationAuthorizationOptionsInitiator processingInformationAuthorizationOptionsInitiator = new Rbsv1subscriptionsProcessingInformationAuthorizationOptionsInitiator();
		processingInformationAuthorizationOptionsInitiator.type("merchant");
		processingInformationAuthorizationOptions.initiator(processingInformationAuthorizationOptionsInitiator);

		processingInformation.authorizationOptions(processingInformationAuthorizationOptions);

		requestObj.processingInformation(processingInformation);

		Rbsv1subscriptionsidSubscriptionInformation subscriptionInformation = new Rbsv1subscriptionsidSubscriptionInformation();
		subscriptionInformation.planId("6868912495476705603955");
		subscriptionInformation.name("Subscription with PlanId");
		subscriptionInformation.startDate("2024-06-11");
		requestObj.subscriptionInformation(subscriptionInformation);

		Rbsv1subscriptionsidOrderInformation orderInformation = new Rbsv1subscriptionsidOrderInformation();
		Rbsv1subscriptionsidOrderInformationAmountDetails orderInformationAmountDetails = new Rbsv1subscriptionsidOrderInformationAmountDetails();
		orderInformationAmountDetails.billingAmount("10");
		orderInformationAmountDetails.setupFee("5");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			SubscriptionsApi apiInstance = new SubscriptionsApi(apiClient);
			apiInstance.updateSubscription(id, requestObj);

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
	
	}
}