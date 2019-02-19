package samples.userManagement.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.UserManagementApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetUserInformation {

	private Properties merchantProp;

	public static void main(String args[]) throws Exception {
		GetUserInformation getUserInformation = new GetUserInformation();
		getUserInformation.process();
	}

	private  void process() throws Exception {
		String className=GetUserInformation.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			UserManagementApi userManagementApi = new UserManagementApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			userManagementApi.getUsers("testrest", null, null, "admin");
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
