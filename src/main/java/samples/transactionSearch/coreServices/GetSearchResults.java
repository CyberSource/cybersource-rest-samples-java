package samples.transactionSearch.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.SearchTransactionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetSearchResults {

	private static String responseCode = null;
	private static String status = null;
	private static String id = "09c93a61-2517-431a-b5d5-a516550f6d98";
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

			SearchTransactionsApi searchTransactionsApi = new SearchTransactionsApi(apiClient);
			searchTransactionsApi.getSearch(id);

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
