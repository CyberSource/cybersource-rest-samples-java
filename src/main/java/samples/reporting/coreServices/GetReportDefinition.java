package samples.reporting.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportDefinitionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetReportDefinition {

	private String reportDefinitionName = "AcquirerExceptionDetailClass";
	private String organisationId;
	private Properties merchantProp;

	public static void main(String args[]) throws Exception {
		GetReportDefinition getReportDefinition = new GetReportDefinition();
		getReportDefinition.process();
	}

	private  void process() throws Exception {
		String className=GetReportDefinition.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ReportDefinitionsApi reportDefinitionsApi = new ReportDefinitionsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			reportDefinitionsApi.getResourceInfoByReportDefinition(reportDefinitionName, organisationId);
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
