package samples.reporting.coreServices;


import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.NotificationOfChangesApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.ReportingV3NotificationofChangesGet200Response;

public class GetNotificationOfChanges {

	private static String responseCode = null;
	private static String status = null;
	
	private static Properties merchantProp;
	
	private static String timeString = "2018-09-01T12:00:00-05:00";
	private static DateTime ddateTime = new DateTime(timeString);
	private static DateTime startTime = ddateTime.withZone(DateTimeZone.forID("America/Atikokan"));
	
	private static String timeString2 = "2018-05-30T12:00:00-05:00";
	private static DateTime ddateTime2=new DateTime(timeString2);
	private static DateTime endTime = ddateTime2.withZone(DateTimeZone.forID("America/Atikokan"));

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient=new ApiClient(merchantConfig);
			
			NotificationOfChangesApi notificationOfChangesApi = new NotificationOfChangesApi();
			System.out.println("startTime :" +startTime + "endTime : "+endTime);
			ReportingV3NotificationofChangesGet200Response response = notificationOfChangesApi.getNotificationOfChangeReport(startTime, endTime);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(response);
		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
