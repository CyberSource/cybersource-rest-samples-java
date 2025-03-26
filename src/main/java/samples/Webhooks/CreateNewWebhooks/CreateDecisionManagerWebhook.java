package samples.Webhooks.CreateNewWebhooks;

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

public class CreateDecisionManagerWebhook {
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
	
//		CreateWebhookRequest requestObj = new CreateWebhookRequest();
//
//		requestObj.name("My Custom Webhook");
//		requestObj.description("Sample Webhook from Developer Center");
//		requestObj.organizationId("<INSERT ORGANIZATION ID HERE>");
//		requestObj.productId("decisionManager");
//
//		List <String> eventTypes = new ArrayList <String>();
//		eventTypes.add("risk.profile.decision.reject");
//		eventTypes.add("risk.casemanagement.addnote");
//		eventTypes.add("risk.casemanagement.decision.accept");
//		eventTypes.add("risk.casemanagement.decision.reject");
//		requestObj.eventTypes(eventTypes);
//
//		requestObj.webhookUrl("https://MyWebhookServer.com:8443/simulateClient");
//		requestObj.healthCheckUrl("https://MyWebhookServer.com:8443/simulateClientHealthCheck");
//		requestObj.notificationScope("SELF");
//		Notificationsubscriptionsv1webhooksRetryPolicy retryPolicy = new Notificationsubscriptionsv1webhooksRetryPolicy();
//		retryPolicy.algorithm("ARITHMETIC");
//		retryPolicy.firstRetry(1);
//		retryPolicy.interval(1);
//		retryPolicy.numberOfRetries(3);
//		retryPolicy.deactivateFlag("false");
//		retryPolicy.repeatSequenceCount(0);
//		retryPolicy.repeatSequenceWaitTime(0);
//		requestObj.retryPolicy(retryPolicy);
//
//		Notificationsubscriptionsv1webhooksSecurityPolicy1 securityPolicy = new Notificationsubscriptionsv1webhooksSecurityPolicy1();
//		securityPolicy.securityType("KEY");
//		securityPolicy.proxyType("external");
//		requestObj.securityPolicy(securityPolicy);

		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CreateNewWebhooksApi apiInstance = new CreateNewWebhooksApi(apiClient);
			//apiInstance.createWebhookSubscription(requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			WriteLogAudit(Integer.parseInt(responseCode));
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}