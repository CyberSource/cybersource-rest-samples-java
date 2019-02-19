package samples.transactionDetails.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.TransactionDetailsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class RetrieveTransaction {

	private String id = "5422054956856755003002";
	private Properties merchantProp;

	public static void main(String args[]) throws Exception {
		RetrieveTransaction retrieveTransaction = new RetrieveTransaction();
		retrieveTransaction.process();
	}

	private void process() throws Exception {
		String className=RetrieveTransaction.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			 /* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			TransactionDetailsApi transactionDetailsApi = new TransactionDetailsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			transactionDetailsApi.getTransaction(id);
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
			System.out.println("[END] EXECUTION OF SAMPLE CODE:" + className);
		}
	}

}
