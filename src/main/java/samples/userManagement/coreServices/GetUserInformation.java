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
			ApiClient.merchantConfig = merchantConfig;	

			UserManagementApi userManagementApi = new UserManagementApi();
			userManagementApi.getUsers("testrest", null, null, "admin");

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
