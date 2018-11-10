package samples.reporting.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportSubscriptionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.ReportingV3ReportSubscriptionsGet200Response;

public class GetAllSubscriptions {

	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {

			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ReportSubscriptionsApi reportSubscriptionsApi = new ReportSubscriptionsApi();
			ReportingV3ReportSubscriptionsGet200Response response = reportSubscriptionsApi.getAllSubscriptions(merchantConfig);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(response);
			System.out.println(ApiClient.responseBody);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
