package samples.userManagement.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.UserManagementApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetUserInformation {

	private static String responseCode = null;
	private static String status = null;
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

			UserManagementApi userManagementApi = new UserManagementApi(apiClient);
			userManagementApi.getUsers("testrest", null, null, "admin");

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
