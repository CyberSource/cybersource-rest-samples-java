package samples.reporting.coreServices;

import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class RetrieveAvailableReports {

	private  Properties merchantProp;

	private  String timeString="2018-08-02T00:00:00.0Z";
	private  DateTime ddateTime = new DateTime(timeString);
	private  DateTime startTime = ddateTime.withZone(DateTimeZone.forID("GMT"));
	
	private  String timeString2 = "2018-08-10T23:59:59.0Z";
	private  DateTime ddateTime2 = new DateTime(timeString2);
	private DateTime endTime = ddateTime2.withZone(DateTimeZone.forID("GMT"));
	
	private  String timeQueryType = "executedTime";
	private  String organizationId = "testrest";
	

	public static void main(String args[]) throws Exception {
		RetrieveAvailableReports retrieveAvailableReports = new RetrieveAvailableReports();
		retrieveAvailableReports.process();
	}

	private  void process() throws Exception {
		String className=RetrieveAvailableReports.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ReportsApi reportsApi = new ReportsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			reportsApi.searchReports(startTime, endTime, timeQueryType, organizationId, null, null,null, null, null);
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " +className+": "+apiClient.getRespBody()+"\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getRespBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE: " + className + "\n");
		}
	}

}
