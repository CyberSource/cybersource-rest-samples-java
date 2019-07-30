package samples.reporting.coreServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportSubscriptionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.RequestBody1;


public class CreateReportSubscriptionForReportNameByOrganization {
	private static String responseCode = null;
	private static String status = null;
	private static RequestBody1 request;
	private static Properties merchantProp;
	private static String report_name = "Dexa";
	private static RequestBody1 getRequest() {
		request = new RequestBody1();
		
		request.reportDefinitionName("TransactionRequestClass");
		
		List<String> reportFields=new ArrayList<String>();
		reportFields.add("Request.RequestID");
		reportFields.add("Request.TransactionDate");
		reportFields.add("Request.MerchantID");
		request.reportFields(reportFields);

		
		request.reportFrequency("MONTHLY");
		request.startDay(2);
		request.startTime("0950");
		
		request.reportMimeType("text/csv");
		request.reportName(report_name);
		request.timezone("America/Chicago");
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
			
			ReportSubscriptionsApi reportSubscriptionsApi = new ReportSubscriptionsApi(apiClient);
			reportSubscriptionsApi.createSubscription(request,"testrest");
			
			responseCode = apiClient.responseCode;
			status = apiClient.status;
		
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(apiClient.responseBody);
			
		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
