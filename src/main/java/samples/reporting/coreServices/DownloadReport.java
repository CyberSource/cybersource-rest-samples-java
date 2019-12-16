package samples.reporting.coreServices;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.joda.time.LocalDate;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportDownloadsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Invokers.ApiResponse;

public class DownloadReport {

	private static String responseCode = null;
	private static String status = null;
	private static String reportName = "testrest_subcription_v2989";
	private static String organizationId = "testrest";
	private static Properties merchantProp;
	public static String resourceFile = "DownloadReport";
	private static final String FILE_PATH = "src/main/resources/";
	static LocalDate reportDate = new LocalDate("2019-09-05");

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient = new ApiClient();
			apiClient.merchantConfig = merchantConfig;	

			ReportDownloadsApi downloadsApi = new ReportDownloadsApi(apiClient);
			ApiResponse<InputStream> responseStream = downloadsApi.downloadReportWithHttpInfo(reportDate, reportName, organizationId, null);
			
			String contentType = responseStream.getHeaders().get("Content-Type").get(0);
			
			String fileExtension = "csv";
			
			if (contentType.contains("json")) {
				fileExtension = contentType.substring(contentType.length() - 4);
			} else {
				fileExtension = contentType.substring(contentType.length() - 3);
			}
			
			File targetFile = new File(FILE_PATH + resourceFile + "." + fileExtension);
			
			FileUtils.copyInputStreamToFile(responseStream.getData(), targetFile);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println("File downloaded at the below location :");
			System.out.println(new File(FILE_PATH + resourceFile + "." + fileExtension).getAbsolutePath());
		} catch (ApiException e) {
			e.printStackTrace();
		}
	}

}
