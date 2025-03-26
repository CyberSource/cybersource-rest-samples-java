package samples.Webhooks.ManageWebhooks;

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

public class UpdateWebhook {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String args[]) throws Exception {
		run(null);
	}

	public static void run(String webhookId) {
	
//		UpdateWebhookRequest requestObj = new UpdateWebhookRequest();
//
//		requestObj.name("My Sample Webhook");
//		requestObj.description("Update to my sample webhook");
//		requestObj.organizationId("<INSERT ORGANIZATION ID HERE>");
//		requestObj.productId("terminalManagement");
//
//		List <String> eventTypes = new ArrayList <String>();
//		eventTypes.add("terminalManagement.assignment.update");
//		eventTypes.add("terminalManagement.status.update");
//		requestObj.eventTypes(eventTypes);
//
//		requestObj.webhookUrl("https://MyWebhookServer.com:8443:/simulateClient");
//		requestObj.healthCheckUrl("https://MyWebhookServer.com:8443:/simulateClientHealthCheck");
//		requestObj.status("INACTIVE");
//		Notificationsubscriptionsv1webhooksNotificationScope notificationScope = new Notificationsubscriptionsv1webhooksNotificationScope();
//		notificationScope.scope("SELF");
//		requestObj.notificationScope(notificationScope);

		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			ManageWebhooksApi apiInstance = new ManageWebhooksApi(apiClient);
//			apiInstance.updateWebhookSubscription(webhookId, requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			WriteLogAudit(Integer.parseInt(responseCode));
//		} catch (ApiException e) {
//			e.printStackTrace();
//			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}