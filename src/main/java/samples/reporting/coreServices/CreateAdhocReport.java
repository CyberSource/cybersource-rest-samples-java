package samples.reporting.coreServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreateAdhocReportRequest;
import Model.Reportingv3reportsReportPreferences;

public class CreateAdhocReport {
	private static String responseCode = null;
	private static String status = null;
	private static CreateAdhocReportRequest request;
	private static Properties merchantProp;

	private static CreateAdhocReportRequest getRequest() {
		request = new CreateAdhocReportRequest();
		
		request.reportDefinitionName("TransactionRequestClass");
		request.timezone("GMT");
		request.reportMimeType("application/xml");
		request.reportName("testrest April V70");

		String timeString = "2020-03-02T12:00:00+05:00";
		DateTime ddateTime = new DateTime(timeString);
		DateTime startTime = ddateTime.withZone(DateTimeZone.forID("Asia/Dushanbe"));
		request.reportStartTime(startTime);
		

		String timeString2 = "2020-03-03T12:00:00+05:00";
		DateTime ddateTime2 = new DateTime(timeString2);
		DateTime endTime = ddateTime2.withZone(DateTimeZone.forID("Asia/Dushanbe"));
		request.reportEndTime(endTime);
		
		Reportingv3reportsReportPreferences reportPreferences = new Reportingv3reportsReportPreferences();
		reportPreferences.signedAmounts(true);
		reportPreferences.fieldNameConvention("SOAPI");
		request.reportPreferences(reportPreferences);

		List<String> reportFields = new ArrayList<String>();
		reportFields.add("Request.RequestID");
		reportFields.add("Request.TransactionDate");
		reportFields.add("Request.MerchantID");

		request.reportFields(reportFields);

		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static void process() throws Exception {

		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;	

			ReportsApi ReportsApi = new ReportsApi(apiClient);
			ReportsApi.createReport(request,null);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
