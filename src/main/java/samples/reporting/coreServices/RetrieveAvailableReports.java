package samples.reporting.coreServices;

import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.ReportingV3ReportsGet200Response;

public class RetrieveAvailableReports {

	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	private static String timeString="2018-08-02T00:00:00.0Z";
	private static DateTime ddateTime = new DateTime(timeString);
	private static DateTime startTime = ddateTime.withZone(DateTimeZone.forID("GMT"));
	
	private static String timeString2 = "2018-08-10T23:59:59.0Z";
	private static DateTime ddateTime2 = new DateTime(timeString2);
	private static DateTime endTime = ddateTime2.withZone(DateTimeZone.forID("GMT"));
	
	private static String timeQueryType = "executedTime";
	private static String organizationId = "testrest";
	

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient = new ApiClient(merchantConfig);

			ReportsApi reportsApi = new ReportsApi();
			ReportingV3ReportsGet200Response response = reportsApi.searchReports(startTime, endTime, timeQueryType, organizationId, null, null,
					null, null, null);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println("ResponseBody :"+ApiClient.respBody);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
