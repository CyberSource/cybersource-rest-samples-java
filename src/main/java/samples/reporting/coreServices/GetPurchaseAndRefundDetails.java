package samples.reporting.coreServices;

import java.util.Properties;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PurchaseAndRefundDetailsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;

public class GetPurchaseAndRefundDetails {

	private static String responseCode = null;
	private static String status = null;

	private static String timeString = "2019-05-01T12:00:00+05:00";
	private static DateTime ddateTime = new DateTime(timeString);
	private static DateTime startTime = ddateTime.withZone(DateTimeZone.forID("Asia/Ashkhabad"));
	
	private static String timeString2 = "2019-05-30T12:00:00-05:00";
	private static DateTime ddateTime2 = new DateTime(timeString2);
	private static DateTime endTime = ddateTime2.withZone(DateTimeZone.forID("Asia/Ashkhabad"));
	
	private static String organizationId = "testrest";
	private static String groupName = "groupName";
	private static String paymentSubtype = "VI";
	private static String viewBy = "requestDate";
	private static int offSet = 20;
	private static int limit = 2000;
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
			
			PurchaseAndRefundDetailsApi purchaseAndRefundDetailsApi = new PurchaseAndRefundDetailsApi(apiClient);
			purchaseAndRefundDetailsApi.getPurchaseAndRefundDetails(startTime, endTime, organizationId, paymentSubtype,
					viewBy, groupName, offSet, limit);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);

		} catch (ApiException e) {

			e.printStackTrace();
		}
	}

}
