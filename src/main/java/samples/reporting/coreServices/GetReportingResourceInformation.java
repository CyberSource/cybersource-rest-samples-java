package samples.reporting.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.ReportDefinitionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.ReportingV3ReportDefinitionsGet200Response;

public class GetReportingResourceInformation {

	private static String organisationId="testrest";
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

			ReportDefinitionsApi reportDefinitionsApi = new ReportDefinitionsApi();
			ReportingV3ReportDefinitionsGet200Response response = reportDefinitionsApi.getResourceV2Info(organisationId,merchantConfig);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(response);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
