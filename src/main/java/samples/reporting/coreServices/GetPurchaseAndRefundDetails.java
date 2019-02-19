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

	private  String timeString = "2018-05-01T12:00:00+05:00";
	private  DateTime ddateTime = new DateTime(timeString);
	private  DateTime startTime = ddateTime.withZone(DateTimeZone.forID("Asia/Ashkhabad"));
	
	private  String timeString2 = "2018-05-30T12:00:00-05:00";
	private  DateTime ddateTime2 = new DateTime(timeString2);
	private  DateTime endTime = ddateTime2.withZone(DateTimeZone.forID("Asia/Ashkhabad"));
	
	private  String organizationId = "testrest";
	private  String groupName = "groupName";
	private  String paymentSubtype = "VI";
	private  String viewBy = "requestDate";
	private  int offSet = 20;
	private  int limit = 2000;
	private  Properties merchantProp;

	public static void main(String args[]) throws Exception {
		GetPurchaseAndRefundDetails getPurchaseAndRefundDetails = new GetPurchaseAndRefundDetails();
		getPurchaseAndRefundDetails.process();
	}

	private  void process() throws Exception {
		String className=GetPurchaseAndRefundDetails.class.getSimpleName();
		System.out.println("[BEGIN] EXECUTION OF SAMPLE CODE: "+className+"\n");
		ApiClient apiClient = null;
		try {
			/* Read Merchant details. */
			merchantProp = Configuration.getMerchantDetails();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			PurchaseAndRefundDetailsApi purchaseAndRefundDetailsApi = new PurchaseAndRefundDetailsApi(merchantConfig);
			apiClient=Invokers.Configuration.getDefaultApiClient();
			purchaseAndRefundDetailsApi.getPurchaseAndRefundDetails(startTime, endTime, organizationId, paymentSubtype,
					viewBy, groupName, offSet, limit);
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
