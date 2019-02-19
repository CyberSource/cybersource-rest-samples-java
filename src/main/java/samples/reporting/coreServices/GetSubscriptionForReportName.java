package samples.reporting.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportSubscriptionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetSubscriptionForReportName {
	
	
	private  String reportName = "Texture";
	private  Properties merchantProp;
	
	public static void main(String args[]) throws Exception {
		GetSubscriptionForReportName getSubscriptionForReportName = new GetSubscriptionForReportName();
		getSubscriptionForReportName.process();
	}
    
	private  void process() throws Exception {
		String className=GetSubscriptionForReportName.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ReportSubscriptionsApi reportSubscriptionsApi = new ReportSubscriptionsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			reportSubscriptionsApi.getSubscription(reportName);
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " + className + ": " + apiClient.getRespBody() + "\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getRespBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE:" + className + "\n");
		}
  }

}
