package samples.reporting.coreServices;

import java.util.Arrays;
import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.PaymentBatchSummariesApi;
import Data.Configuration;
import Invokers.ApiClient;
import Model.ReportingV3PaymentBatchSummariesGet200Response;

public class GetPaymentBatchSummaryData{
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		process();
	}

	public static void process() throws Exception {
		DateTime startTime = new DateTime("2019-05-01T12:00:00Z").withZone(DateTimeZone.forID("Asia/Dushanbe"));
		DateTime endTime = new DateTime("2019-08-30T12:00:00Z").withZone(DateTimeZone.forID("Asia/Dushanbe"));
		String organizationId = "testrest";

		ReportingV3PaymentBatchSummariesGet200Response result = null;
		try
		{
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentBatchSummariesApi apiInstance = new PaymentBatchSummariesApi(apiClient);
			result = apiInstance.getPaymentBatchSummary(startTime, endTime, organizationId, null, null, null);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(Arrays.toString(result.getPaymentBatchSummaries().toArray()));			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}