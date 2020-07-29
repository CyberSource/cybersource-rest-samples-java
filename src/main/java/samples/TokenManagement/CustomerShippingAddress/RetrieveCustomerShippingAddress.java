package samples.TokenManagement.CustomerShippingAddress;

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

public class RetrieveCustomerShippingAddress {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}
	public static Tmsv2customersEmbeddedDefaultShippingAddress run() {
		String customerTokenId = "AB695DA801DD1BB6E05341588E0A3BDC";
		String shippingAddressTokenId = "AB6A54B97C00FCB6E05341588E0A3935";
		
		Tmsv2customersEmbeddedDefaultShippingAddress result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CustomerShippingAddressApi apiInstance = new CustomerShippingAddressApi(apiClient);
			result = apiInstance.getCustomerShippingAddress(customerTokenId, shippingAddressTokenId, null);

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
