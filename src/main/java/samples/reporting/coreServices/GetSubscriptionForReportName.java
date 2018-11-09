package samples.reporting.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportSubscriptionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetSubscriptionForReportName {
	
	
	private static String reportName="Texture";
	private static String responseCode=null;
	private static String status=null;
	private static Properties merchantProp;
	
	public static void main(String args[]) throws Exception {
		process();
	}
    
	private static void process() throws Exception {
	
	try {
		/* Read Merchant details. */
		merchantProp = Configuration.getMerchantDetails();
		MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
		
		ReportSubscriptionsApi reportSubscriptionsApi=new ReportSubscriptionsApi();
		reportSubscriptionsApi.getSubscription(reportName,merchantConfig);
		
		responseCode=ApiClient.responseCode;
		status=ApiClient.status;
		System.out.println("ResponseCode :" +responseCode);
		System.out.println("ResponseMessage :" +status);
		
	
	} catch (ApiException e) {
		
		e.printStackTrace();
	}
  }

}
