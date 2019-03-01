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
	private static String id = "9db3c19b-2f88-4828-97b2-a6a739eacace";
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			 /* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient.merchantConfig = merchantConfig;	

			SearchTransactionsApi searchTransactionsApi = new SearchTransactionsApi();
			searchTransactionsApi.getSearch(id);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println("ResponseBody :"+ApiClient.respBody);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
