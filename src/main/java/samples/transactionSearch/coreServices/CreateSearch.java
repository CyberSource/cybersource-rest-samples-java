package samples.transactionSearch.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.SearchTransactionsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.TssV2TransactionsPostResponse;
import Model.TssV2TransactionsPost201Response;

public class CreateSearch {
	private static String responseCode = null;
	private static String status = null;
	private static TssV2TransactionsPost201Response reponse;
	private static TssV2TransactionsPostResponse request;
	private static Properties merchantProp;

	private static TssV2TransactionsPostResponse getRequest() {
		request = new TssV2TransactionsPostResponse();
		
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
			ApiClient apiClient = new ApiClient(merchantConfig);
			
			SearchTransactionsApi searchTransactionsApi = new SearchTransactionsApi();
			reponse = searchTransactionsApi.createSearch(request);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(reponse);

		} catch (ApiException e) {

			e.printStackTrace();
		}
		return reponse;
	}

}
