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

public class ReplaySpecificListOfTransactions {
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
	
//		ReplayWebhooksRequest requestObj = new ReplayWebhooksRequest();
//
//
//		List <String> byTransactionTraceIdentifiers = new ArrayList <String>();
//		byTransactionTraceIdentifiers.add("1f1d0bf4-9299-418d-99d8-faa3313829f1");
//		byTransactionTraceIdentifiers.add("d19fb205-20e5-43a2-867e-bd0f574b771e");
//		byTransactionTraceIdentifiers.add("2f2461a3-457c-40e9-867f-aced89662bbb");
//		byTransactionTraceIdentifiers.add("e23ddc19-93d5-4f1f-8482-d7cafbb3ed9b");
//		byTransactionTraceIdentifiers.add("eb9fc4a9-b31f-48d5-81a9-b1d773fd76d8");
//		requestObj.byTransactionTraceIdentifiers(byTransactionTraceIdentifiers);

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