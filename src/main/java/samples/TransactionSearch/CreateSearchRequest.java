package samples.TransactionSearch;

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

public class CreateSearchRequest {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}

	public static TssV2TransactionsPost201Response run() {
	
		Model.CreateSearchRequest requestObj = new Model.CreateSearchRequest();

		requestObj.save(false);
		requestObj.name("MRN");
		requestObj.timezone("America/Chicago");
		requestObj.query("clientReferenceInformation.code:TC50171_3 AND submitTimeUtc:[NOW/DAY-7DAYS TO NOW/DAY+1DAY}");
		requestObj.offset(0);
		requestObj.limit(100);
		requestObj.sort("id:asc,submitTimeUtc:asc");
		TssV2TransactionsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			SearchTransactionsApi apiInstance = new SearchTransactionsApi(apiClient);
			result = apiInstance.createSearch(requestObj);

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
