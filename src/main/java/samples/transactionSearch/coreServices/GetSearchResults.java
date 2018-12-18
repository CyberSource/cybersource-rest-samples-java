package samples.transactionSearch.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.SearchTransactionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.TssV2TransactionsPost201Response;

public class GetSearchResults {

	private static String responseCode = null;
	private static String status = null;
	private static String id = "95f6ab1c-d64d-4fdb-949d-cf174405c21f";
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			 /* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient = new ApiClient(merchantConfig);

			SearchTransactionsApi searchTransactionsApi = new SearchTransactionsApi();
			TssV2TransactionsPost201Response response = searchTransactionsApi.getSearch(id);

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
