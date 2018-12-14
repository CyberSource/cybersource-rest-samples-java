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
	private static String id="5422054956856755003002";
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		process();
	}

	private static void process() throws Exception {

		try {
			 /* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient apiClient=new ApiClient(merchantConfig);

			TransactionDetailsApi transactionDetailsApi = new TransactionDetailsApi();
			TssV2TransactionsGet200Response respose = transactionDetailsApi.getTransaction(id);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(respose);
			
		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
