package samples.Reporting.ReportSubscriptions;

import java.*;
import java.util.*;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class CreateReportSubscription {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static void run() {
	
		CreateReportSubscriptionRequest requestObj = new CreateReportSubscriptionRequest();

		requestObj.reportDefinitionName("TransactionRequestClass");

		List <String> reportFields = new ArrayList <String>();
		reportFields.add("Request.RequestID");
		reportFields.add("Request.TransactionDate");
		reportFields.add("Request.MerchantID");
		requestObj.reportFields(reportFields);

		requestObj.reportMimeType("application/xml");
		requestObj.reportFrequency("WEEKLY");
		requestObj.reportName("testrest_subcription_v1");
		requestObj.timezone("GMT");
		requestObj.startTime("0900");
		requestObj.startDay(1);
		String organizationId = null;

		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			ReportSubscriptionsApi apiInstance = new ReportSubscriptionsApi(apiClient);
			apiInstance.createSubscription(requestObj, organizationId);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(apiClient.responseBody.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
