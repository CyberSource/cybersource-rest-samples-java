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
import Model.ReportingV3ReportSubscriptionsGet200ResponseReportPreferences;
import Model.ReportingV3ReportSubscriptionsGet200ResponseReportPreferences.FieldNameConventionEnum;
import Model.RequestBody1;
import Model.RequestBody1.ReportMimeTypeEnum;

public class CreateAdhocReport {
	private  RequestBody1 request;
	private  Properties merchantProp;

	private  RequestBody1 getRequest() {
		request = new RequestBody1();
		
		request.reportDefinitionName("TransactionRequestClass");
		request.timezone("GMT");
		request.reportMimeType(ReportMimeTypeEnum.APPLICATION_XML);
		request.reportName("testrest v2 Feb06");

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
		CreateAdhocReport createAdhocReport = new CreateAdhocReport();
		createAdhocReport.process();
	}

	private  void process() throws Exception {
		String className=CreateAdhocReport.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			request = getRequest();
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);

			ReportsApi ReportsApi = new ReportsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			ReportsApi.createReport(request);
			
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " +className+": "+apiClient.getRespBody()+"\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API REQUEST BODY:");
			System.out.println(apiClient.getRequestBody() + "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getRespBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE: " + className + "\n");
		}
	}

}
