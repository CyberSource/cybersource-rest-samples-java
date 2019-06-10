package samples.transactionDetails.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.TransactionDetailsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.TssV2TransactionsGet200Response;

public class RetrieveTransaction {

	private static String responseCode = null;
	private static String status = null;
	private static String id = "5562245381336083504001";
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

			TransactionDetailsApi transactionDetailsApi = new TransactionDetailsApi(apiClient);
			TssV2TransactionsGet200Response respose = transactionDetailsApi.getTransaction(id);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(respose);
			
		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
