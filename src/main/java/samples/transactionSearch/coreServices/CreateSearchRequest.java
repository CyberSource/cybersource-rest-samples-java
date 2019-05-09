package samples.transactionSearch.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.SearchTransactionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.TssV2TransactionsPost201Response;

public class CreateSearchRequest {
	private static String responseCode = null;
	private static String status = null;
	private static TssV2TransactionsPost201Response reponse;
	private static Model.CreateSearchRequest request;
	private static Properties merchantProp;

	private static Model.CreateSearchRequest getRequest() {
		request = new Model.CreateSearchRequest();
		
		request.save(false);
		request.name("MRN");
		request.timezone("America/Chicago");
		request.query("clientReferenceInformation.code:TC50171_3");
		request.offset(0);
		request.limit(80);
		request.sort("id:asc, submitTimeUtc:asc");
		return request;

	}

	public static void main(String args[]) throws Exception {
		process();
	}

	public static TssV2TransactionsPost201Response process() throws Exception {

		try {
			request = getRequest();

			 /* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient.merchantConfig = merchantConfig;	
			
			SearchTransactionsApi searchTransactionsApi = new SearchTransactionsApi();
			reponse = searchTransactionsApi.createSearch(request);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println("ResponseBody :"+ApiClient.respBody);

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return reponse;
	}

}
