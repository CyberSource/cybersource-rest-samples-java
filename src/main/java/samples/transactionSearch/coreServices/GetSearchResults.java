package samples.transactionSearch.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.SearchTransactionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetSearchResults {

	private  String id = "50b4d6fa-3f7c-46c1-986f-4a746b12661d";
	private  Properties merchantProp;

	public static void main(String args[]) throws Exception {
		GetSearchResults getSearchResults = new GetSearchResults();
		getSearchResults.process();
	}

	private  void process() throws Exception {
		String className=GetSearchResults.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			 /* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			SearchTransactionsApi searchTransactionsApi = new SearchTransactionsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			searchTransactionsApi.getSearch(id);
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
			System.out.println("[END] EXECUTION OF SAMPLE CODE:" + className + "\n");
		}
	}

}
