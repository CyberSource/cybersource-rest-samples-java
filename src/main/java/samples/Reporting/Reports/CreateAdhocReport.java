package samples.Reporting.Reports;

import java.*;
import java.lang.invoke.MethodHandles;
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

public class CreateAdhocReport {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String args[]) throws Exception {
		run();
	}

	public static void run() {
	
		CreateAdhocReportRequest requestObj = new CreateAdhocReportRequest();

		requestObj.reportDefinitionName("TransactionRequestClass");

		List <String> reportFields = new ArrayList <String>();
		reportFields.add("Request.RequestID");
		reportFields.add("Request.TransactionDate");
		reportFields.add("Request.MerchantID");
		requestObj.reportFields(reportFields);

		requestObj.reportMimeType("application/xml");
		requestObj.reportName("testrest_v2");
		requestObj.timezone("GMT");
		requestObj.reportStartTime(new DateTime("2020-03-01T17:30:00.000+05:30"));
		requestObj.reportEndTime(new DateTime("2020-03-02T17:30:00.000+05:30"));
		Reportingv3reportsReportPreferences reportPreferences = new Reportingv3reportsReportPreferences();
		reportPreferences.signedAmounts(true);
		reportPreferences.fieldNameConvention("SOAPI");
		requestObj.reportPreferences(reportPreferences);

		String organizationId = "testrest";

		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			ReportsApi apiInstance = new ReportsApi(apiClient);
			apiInstance.createReport(requestObj, organizationId);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			WriteLogAudit(Integer.parseInt(responseCode));
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
