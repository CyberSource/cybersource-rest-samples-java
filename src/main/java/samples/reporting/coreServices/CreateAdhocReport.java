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
import Invokers.ApiResponse;
import Model.ReportingV3ReportSubscriptionsGet200ResponseReportPreferences;
import Model.ReportingV3ReportsIdGet200Response;
import Model.ReportingV3ReportSubscriptionsGet200ResponseReportPreferences.FieldNameConventionEnum;
import Model.RequestBody1;
import Model.RequestBody1.ReportMimeTypeEnum;

public class CreateAdhocReport {
	private static String responseCode = null;
	private static String status = null;
	private static RequestBody1 request;
	private static Properties merchantProp;

	private static RequestBody1 getRequest() {
		request = new RequestBody1();
		
		request.reportDefinitionName("TransactionRequestClass");
		request.timezone("GMT");
		request.reportMimeType(ReportMimeTypeEnum.APPLICATION_XML);
		request.reportName("testrest dec V70");

		String timeString = "2018-09-02T12:00:00+05:00";
		DateTime ddateTime = new DateTime(timeString);
		DateTime startTime = ddateTime.withZone(DateTimeZone.forID("Asia/Dushanbe"));
		request.reportStartTime(startTime);
		

		String timeString2 = "2018-09-03T12:00:00+05:00";
		DateTime ddateTime2 = new DateTime(timeString2);
		DateTime endTime = ddateTime2.withZone(DateTimeZone.forID("Asia/Dushanbe"));
		request.reportEndTime(endTime);
		
		ReportingV3ReportSubscriptionsGet200ResponseReportPreferences reportPreferences = new ReportingV3ReportSubscriptionsGet200ResponseReportPreferences();
		reportPreferences.signedAmounts(true);
		reportPreferences.fieldNameConvention(FieldNameConventionEnum.SOAPI);
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
			ApiClient apiClient = new ApiClient(merchantConfig);

			ReportsApi ReportsApi = new ReportsApi();
			ApiResponse<ReportingV3ReportsIdGet200Response> response = ReportsApi.createReport(request);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(ApiClient.responseBody.toString());
			

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
