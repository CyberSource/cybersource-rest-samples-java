package samples.TokenManagement.CustomerPaymentInstrument;

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

public class DeleteCustomerPaymentInstrument {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static void run() {
		String customerTokenId = "AB695DA801DD1BB6E05341588E0A3BDC";
		String paymentInstrumentTokenId = CreateCustomerPaymentInstrumentCard.run().getId();
		
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CustomerPaymentInstrumentApi apiInstance = new CustomerPaymentInstrumentApi(apiClient);
			apiInstance.deleteCustomerPaymentInstrument(customerTokenId, paymentInstrumentTokenId, null);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
