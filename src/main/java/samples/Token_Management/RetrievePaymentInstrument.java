package samples.Token_Management;
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

public class RetrievePaymentInstrument{
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

/*
	public static void main(String args[]) throws Exception 
	{
		// Accept required parameters from args[] and pass to run.
		run(profileid, tokenId);
	}
*/
	public static TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments run(String profileid, String tokenId){
	
		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments result = null;
		try
		{
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentInstrumentApi apiInstance = new PaymentInstrumentApi(apiClient);
			result = apiInstance.getPaymentInstrument(profileid, tokenId);

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
