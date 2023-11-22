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

public class CreateInvoicingWebhook {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}
	
	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	
	public static InlineResponse2013 run() {
	
		CreateWebhook requestObj = new CreateWebhook();

		requestObj.name("My Custom Webhook");
		requestObj.description("Sample Webhook from Developer Center");
		requestObj.organizationId("organizationId");
		requestObj.productId("customerInvoicing");

		List <String> eventTypes = new ArrayList <String>();
		eventTypes.add("invoicing.customer.invoice.cancel");
		eventTypes.add("invoicing.customer.invoice.overdue-reminder");
		eventTypes.add("invoicing.customer.invoice.paid");
		eventTypes.add("invoicing.customer.invoice.partial-payment");
		eventTypes.add("invoicing.customer.invoice.partial-resend");
		eventTypes.add("invoicing.customer.invoice.reminder");
		eventTypes.add("invoicing.customer.invoice.send");
		requestObj.eventTypes(eventTypes);

		requestObj.webhookUrl("https://MyWebhookServer.com:8443/simulateClient");
		requestObj.healthCheckUrl("https://MyWebhookServer.com:8443/simulateClientHealthCheck");
		requestObj.notificationScope("SELF");
		Notificationsubscriptionsv1webhooksRetryPolicy retryPolicy = new Notificationsubscriptionsv1webhooksRetryPolicy();
		retryPolicy.algorithm("ARITHMETIC");
		retryPolicy.firstRetry(1);
		retryPolicy.interval(1);
		retryPolicy.numberOfRetries(3);
		retryPolicy.deactivateFlag("false");
		retryPolicy.repeatSequenceCount(0);
		retryPolicy.repeatSequenceWaitTime(0);
		requestObj.retryPolicy(retryPolicy);

		Notificationsubscriptionsv1webhooksSecurityPolicy1 securityPolicy = new Notificationsubscriptionsv1webhooksSecurityPolicy1();
		securityPolicy.securityType("KEY");
		securityPolicy.proxyType("external");
		requestObj.securityPolicy(securityPolicy);

		
		InlineResponse2013 result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CreateNewWebhooksApi apiInstance = new CreateNewWebhooksApi(apiClient);
			result=apiInstance.createWebhook(requestObj);

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