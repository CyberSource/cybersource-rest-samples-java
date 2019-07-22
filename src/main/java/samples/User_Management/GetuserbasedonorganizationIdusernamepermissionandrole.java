// 64
// Code Generated: getUsers[Get user based on organization Id, username, permission and role]

package samples.User_Management;
import java.*;
import java.util.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class GetuserbasedonorganizationIdusernamepermissionandrole{
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception
	{
		// Accept required parameters from args[] and pass to run.
		run( );
	}
	public static UmsV1UsersGet200Response run( ){
	
		String organizationId = "testrest";
		String userName = null;
		String permissionId = "CustomerProfileViewPermission";
		String roleId = null;

		UmsV1UsersGet200Response result = null;
		try
		{
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			UserManagementApi apiInstance = new UserManagementApi(apiClient);
			result = apiInstance.getUsers( organizationId, userName, permissionId, roleId );

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	return result;
	}
}


//****************************************************************************************************


