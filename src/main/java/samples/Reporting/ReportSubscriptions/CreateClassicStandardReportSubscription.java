package samples.Reporting.ReportSubscriptions;

import java.*;
import java.io.InputStream;
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
import Invokers.ApiResponse;
import Model.*;

public class CreateClassicStandardReportSubscription {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static void run() {
	
		PredefinedSubscriptionRequestBean requestObj = new PredefinedSubscriptionRequestBean();

		requestObj.reportDefinitionName("TransactionRequestClass");
		requestObj.subscriptionType("CLASSIC");
		String organizationId = null;

		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			ReportSubscriptionsApi apiInstance = new ReportSubscriptionsApi(apiClient);
			apiInstance.createStandardOrClassicSubscription(requestObj, organizationId);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
