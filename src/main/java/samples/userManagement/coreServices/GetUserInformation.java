package samples.userManagement.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.UserManagementApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.UmsV1UsersGet200Response;

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

			UserManagementApi userManagementApi = new UserManagementApi();
			UmsV1UsersGet200Response response = userManagementApi.getUsers("testrest", null, null, "admin",merchantConfig);

			responseCode = ApiClient.responseCode;
			status = ApiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(response);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
