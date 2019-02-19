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
	private TssV2TransactionsPost201Response response;
	private TssV2TransactionsPostResponse request;
	private Properties merchantProp;

	private TssV2TransactionsPostResponse getRequest() {
		request = new TssV2TransactionsPostResponse();
		
		request.save(false);
		request.name("TSS search");
		request.timezone("America/Chicago");
		request.query("clientReferenceInformation.code:12345");
		request.offset(0);
		request.limit(100);
		request.sort("id:asc, submitTimeUtc:asc");
		return request;

	}

	public static void main(String args[]) throws Exception {
		CreateSearch createSearch = new CreateSearch();
		createSearch.process();
	}

	private TssV2TransactionsPost201Response process() throws Exception {
		String className=CreateSearch.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			request = getRequest();

			 /* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			SearchTransactionsApi searchTransactionsApi = new SearchTransactionsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			response = searchTransactionsApi.createSearch(request);
		} catch (ApiException e) {
			System.out.println("Exception on calling the Sample Code " +className+": "+apiClient.getRespBody()+"\n");
		} finally {
			System.out.println("API REQUEST HEADERS:");
			System.out.println(apiClient.getRequestHeader() + "\n");
			System.out.println("API REQUEST BODY:");
			System.out.println(apiClient.getRequestBody() + "\n");
			System.out.println("API RESPONSE CODE: " + apiClient.getResponseCode() + "\n");
			System.out.println("API RESPONSE HEADERS:");
			System.out.println(apiClient.getResponseHeader() + "\n");
			System.out.println("API RESPONSE BODY:");
			System.out.println(apiClient.getRespBody() + "\n");
			System.out.println("[END] EXECUTION OF SAMPLE CODE: " + className + "\n");
		}
		return response;
	}

}
