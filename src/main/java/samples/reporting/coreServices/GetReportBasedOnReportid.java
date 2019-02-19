package samples.reporting.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetReportBasedOnReportid {

	private  String organizationId;
	private  String reportId = "79642c43-2368-0cd5-e053-a2588e0a7b3c";
	private  Properties merchantProp;


	public static void main(String args[]) throws Exception {
		GetReportBasedOnReportid getReportBasedOnReportid = new GetReportBasedOnReportid();
		getReportBasedOnReportid.process();
	}

	private  void process() throws Exception {
		String className=GetReportBasedOnReportid.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ReportsApi reportsApi = new ReportsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			reportsApi.getReportByReportId(reportId, organizationId);
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
