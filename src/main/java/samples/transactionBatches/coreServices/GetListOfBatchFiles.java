package samples.transactionBatches.coreServices;

import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.TransactionBatchesApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.PtsV1TransactionBatchesGet200Response;

public class GetListOfBatchFiles {

	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;
	
	private static String timeString = "2018-10-01T00:00:00.00Z";
	private static DateTime ddateTime = new DateTime(timeString);
	private static DateTime startTime = ddateTime.withZone(DateTimeZone.forID("GMT"));
	
	private static String timeString2 = "2018-10-31T23:59:59.59Z";
	private static DateTime ddateTime2 = new DateTime(timeString2);
	private static DateTime endTime = ddateTime2.withZone(DateTimeZone.forID("GMT"));

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			 /* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient = new ApiClient(merchantConfig);
            
			TransactionBatchesApi transactionBatchApi = new TransactionBatchesApi();
			PtsV1TransactionBatchesGet200Response response = transactionBatchApi.ptsV1TransactionBatchesGet(startTime, endTime);
			
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
