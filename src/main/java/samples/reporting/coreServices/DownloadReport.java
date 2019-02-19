package samples.reporting.coreServices;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.joda.time.LocalDate;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportDownloadsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class DownloadReport {

	private  String reportName = "testrest v2 Feb06";
	private  String organizationId = "testrest";
	private  Properties merchantProp;
	private  String resourceFile = "DownloadReport";
	private  final String FILE_PATH = "src/test/resources/";
	private LocalDate reportDate = new LocalDate("2018-09-03");

	public static void main(String args[]) throws Exception {
		DownloadReport downloadReport = new DownloadReport();
		downloadReport.process();
	}

	private  void process() throws Exception {
		String className=DownloadReport.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		String reportType = null;
		try {

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);

			ReportDownloadsApi downloadsApi = new ReportDownloadsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			downloadsApi.downloadReportWithHttpInfo(reportDate, reportName, organizationId);
			InputStream stream = new ByteArrayInputStream(apiClient.getResponseBody().getBytes(StandardCharsets.UTF_8));

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			org.apache.commons.io.IOUtils.copy(stream, baos);
			byte[] bytes = baos.toByteArray();
			BufferedReader br = new BufferedReader(new InputStreamReader((new ByteArrayInputStream(bytes))));

			String output;
			reportType = "csv";
			while ((output = br.readLine()) != null) {
				if (output.contains("xml")) {
					reportType = "xml";
				}
			}
			BufferedReader br_write = new BufferedReader(new InputStreamReader((new ByteArrayInputStream(bytes))));
			BufferedWriter bw = new BufferedWriter(
					new FileWriter(new File(FILE_PATH + resourceFile + "." + reportType)));
			while ((output = br_write.readLine()) != null) {
				bw.write(output + "\n");
			}
			bw.close();
			System.out.println("File downloaded at the below location :");
			System.out.println(new File(FILE_PATH + resourceFile + "." + reportType).getAbsolutePath() + "\n");
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " +className+": "+apiClient.getRespBody()+"\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getResponseBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE: " + className + "\n");
		}
	}

}
