package samples.reporting.coreServices;

import java.util.Properties;

import org.joda.time.LocalDate;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportDownloadsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class DownloadReport {

	private static String responseCode = null;
	private static String status = null;
	private static String reportName = "testrest_v2";
	private static String organizationId = "testrest";
	private static Properties merchantProp;

	static LocalDate reportDate = new LocalDate("2018-09-02");

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ReportDownloadsApi downloadsApi = new ReportDownloadsApi();
			downloadsApi.downloadReport(reportDate, reportName, organizationId,merchantConfig);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
