package samples.reporting.coreServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportSubscriptionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.RequestBody;
import Model.RequestBody.ReportMimeTypeEnum;

public class CreateReportSubscriptionForReportNameByOrganization {
	private  RequestBody request;
	private  Properties merchantProp;
	private  String reportName = "Dexa";
	
	private  RequestBody getRequest() {
		request = new RequestBody();
		
		request.reportDefinitionName("TransactionRequestClass");
		
		List<String> reportFields=new ArrayList<String>();
		reportFields.add("Request.RequestID");
		reportFields.add("Request.TransactionDate");
		reportFields.add("Request.MerchantID");
		request.reportFields(reportFields);

		
		request.reportFrequency("MONTHLY");
		request.startDay(2);
		request.startTime("0950");
		
		request.reportMimeType(ReportMimeTypeEnum.TEXT_CSV);
		request.reportName(reportName);
		request.timezone("America/Chicago");
		return request;

	}

	public static void main(String args[]) throws Exception {
		CreateReportSubscriptionForReportNameByOrganization createReportSubscriptionForReportNameByOrganization = new CreateReportSubscriptionForReportNameByOrganization();
		createReportSubscriptionForReportNameByOrganization.process();
	}

	private  void process() throws Exception {
		String className=CreateReportSubscriptionForReportNameByOrganization.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className + "\n");
		ApiClient apiClient = null;
		try {
			request = getRequest();

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ReportSubscriptionsApi reportSubscriptionsApi = new ReportSubscriptionsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			reportSubscriptionsApi.createSubscription(request);
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
