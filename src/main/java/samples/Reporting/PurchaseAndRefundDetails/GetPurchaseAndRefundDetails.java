package samples.Reporting.PurchaseAndRefundDetails;

import java.*;
import java.util.*;
import java.math.BigDecimal;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class GetPurchaseAndRefundDetails {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static ReportingV3PurchaseRefundDetailsGet200Response run() {
	
		DateTime startTime = new DateTime("2020-01-01T12:00:00Z").withZone(DateTimeZone.forID("GMT"));
		DateTime endTime = new DateTime("2020-01-30T12:00:00Z").withZone(DateTimeZone.forID("GMT"));
		String organizationId = "testrest";
		String paymentSubtype = "VI";
		String viewBy = "requestDate";
		String groupName = "groupName";
		int offset = 20;
		int limit = 2000;

		ReportingV3PurchaseRefundDetailsGet200Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PurchaseAndRefundDetailsApi apiInstance = new PurchaseAndRefundDetailsApi(apiClient);
			result = apiInstance.getPurchaseAndRefundDetails(startTime, endTime, organizationId, paymentSubtype, viewBy, groupName, offset, limit);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}
