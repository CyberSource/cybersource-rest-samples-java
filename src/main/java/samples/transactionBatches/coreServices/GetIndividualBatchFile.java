package samples.transactionBatches.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.TransactionBatchApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetIndividualBatchFile {

	private String id = "Owcyk6pl";
	private Properties merchantProp;

	public static void main(String args[]) throws Exception {
		GetIndividualBatchFile getIndividualBatchFile = new GetIndividualBatchFile();
		getIndividualBatchFile.process();
	}

	private void process() throws Exception {
		String className=GetIndividualBatchFile.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			 /* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			TransactionBatchApi transactionBatchApi = new TransactionBatchApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			transactionBatchApi.ptsV1TransactionBatchesIdGet(id);
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
