package samples.reporting.coreServices;

import java.util.Properties;

import org.joda.time.DateTime;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.NetFundingsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
/**
 * This is the method to get net funding information for merchant information
 * User should pass starttime, end tim.
 *
 */
public class GetNetfundingInformationForAccountOrMerchant {
	private static String responseCode = null;
	private static String status = null;
	private static String organizationId="testrest";
	private static DateTime startTime=new DateTime ("2020-03-01T00:00:00.0Z");
	private static DateTime endTime=new DateTime ("2020-03-30T23:59:59.0Z");
	private static String groupName="groupName"; 
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
			
			NetFundingsApi netFundingsApi=new NetFundingsApi(apiClient); 
			netFundingsApi.getNetFundingDetails(startTime, endTime, organizationId, groupName);
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
