package samples.Reporting;

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

public class GetRetrievalDetails {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static ReportingV3RetrievalDetailsGet200Response run() {
	
		// QUERY PARAMETERS
		String organizationId = "testrest";
		DateTime startTime = new DateTime("2021-08-01T00:00:00Z").withZone(DateTimeZone.forID("GMT"));
		DateTime endTime = new DateTime("2021-09-01T23:59:59Z").withZone(DateTimeZone.forID("GMT"));
		ReportingV3RetrievalDetailsGet200Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			RetrievalDetailsApi apiInstance = new RetrievalDetailsApi(apiClient);
			result = apiInstance.getRetrievalDetails(startTime, endTime, organizationId);

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