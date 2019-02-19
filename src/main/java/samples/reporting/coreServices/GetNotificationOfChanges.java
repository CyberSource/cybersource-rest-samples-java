package samples.reporting.coreServices;


import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.NotificationOfChangesApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetNotificationOfChanges {

	private  Properties merchantProp;
	
	private  String timeString = "2018-02-05T12:00:00-05:00";
	private  DateTime ddateTime = new DateTime(timeString);
	private  DateTime startTime = ddateTime.withZone(DateTimeZone.forID("America/Atikokan"));
	
	private  String timeString2 = "2018-02-06T12:00:00-05:00";
	private  DateTime ddateTime2 = new DateTime(timeString2);
	private  DateTime endTime = ddateTime2.withZone(DateTimeZone.forID("America/Atikokan"));

	public static void main(String args[]) throws Exception {
		GetNotificationOfChanges getNotificationOfChanges = new GetNotificationOfChanges();
		getNotificationOfChanges.process();
	}

	private  void process() throws Exception {
		String className=GetNotificationOfChanges.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			NotificationOfChangesApi notificationOfChangesApi = new NotificationOfChangesApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			notificationOfChangesApi.getNotificationOfChangeReport(startTime, endTime);
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
