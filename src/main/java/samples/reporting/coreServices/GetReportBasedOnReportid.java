package samples.reporting.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetReportBasedOnReportid {

	private static String responseCode = null;
	private static String status = null;
	private static String organizationId;
	private static String reportId = "79642c43-2368-0cd5-e053-a2588e0a7b3c";
	private static Properties merchantProp;


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
			
			ReportsApi reportsApi = new ReportsApi(apiClient);
			reportsApi.getReportByReportId(reportId, organizationId);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println("ResponseBody :"+apiClient.respBody);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
