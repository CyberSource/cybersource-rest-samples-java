package samples.transactionBatches.coreServices;

import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.TransactionBatchesApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetListOfBatchFiles {

	private Properties merchantProp;
	
	private  String timeString = "2018-10-01T00:00:00.00Z";
	private  DateTime ddateTime = new DateTime(timeString);
	private  DateTime startTime = ddateTime.withZone(DateTimeZone.forID("GMT"));
	
	private  String timeString2 = "2018-10-31T23:59:59.59Z";
	private  DateTime ddateTime2 = new DateTime(timeString2);
	private  DateTime endTime = ddateTime2.withZone(DateTimeZone.forID("GMT"));

	public static void main(String args[]) throws Exception {
		GetListOfBatchFiles getListOfBatchFiles = new GetListOfBatchFiles();
		getListOfBatchFiles.process();
	}

	private  void process() throws Exception {
		String className=GetListOfBatchFiles.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			 /* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			TransactionBatchesApi transactionBatchApi = new TransactionBatchesApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			transactionBatchApi.ptsV1TransactionBatchesGet(startTime, endTime);
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
