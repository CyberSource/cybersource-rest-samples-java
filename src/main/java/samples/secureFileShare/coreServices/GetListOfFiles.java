 package samples.secureFileShare.coreServices;

import java.util.Properties;

import org.joda.time.LocalDate;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.SecureFileShareApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetListOfFiles {

	private String organizationId = "testrest";
	private Properties merchantProp;
	
	private LocalDate startDate = new LocalDate("2018-10-20");
	private LocalDate endDate = new LocalDate("2018-10-30");


	public static void main(String args[]) throws Exception {
		GetListOfFiles getListOfFiles = new GetListOfFiles();
		getListOfFiles.process();
	}

	private void process() throws Exception {
		String className=GetListOfFiles.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			SecureFileShareApi secureFileShareApi = new SecureFileShareApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			secureFileShareApi.getFileDetails(startDate, endDate, organizationId);
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
			System.out.println("[END] EXECUTION OF SAMPLE CODE: " + className + "\n");
		}
	}

}
