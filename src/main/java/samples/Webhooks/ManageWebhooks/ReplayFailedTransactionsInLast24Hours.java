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

public class ReplayFailedTransactionsInLast24Hours {
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
//	
//		ReplayWebhooksRequest requestObj = new ReplayWebhooksRequest();
//
//		Nrtfv1webhookswebhookIdreplaysByDeliveryStatus byDeliveryStatus = new Nrtfv1webhookswebhookIdreplaysByDeliveryStatus();
//		byDeliveryStatus.status("FAILED");
//		byDeliveryStatus.hoursBack(24);
//		byDeliveryStatus.productId("tokenManagement");
//		byDeliveryStatus.eventType("tms.token.created");
//		requestObj.byDeliveryStatus(byDeliveryStatus);

		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

//			ReplayWebhooksApi apiInstance = new ReplayWebhooksApi(apiClient);
//			apiInstance.replayPreviousWebhooks(webhookId, requestObj);

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