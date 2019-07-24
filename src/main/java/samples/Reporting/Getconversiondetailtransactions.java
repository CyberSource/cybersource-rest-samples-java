// 39
// Code Generated: getConversionDetail[Get conversion detail transactions]

package samples.Reporting;
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

public class Getconversiondetailtransactions{
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

/*
	public static void main(String args[]) throws Exception 
	{
		// Accept required parameters from args[] and pass to run.
		run( );
	}
*/
	public static ReportingV3ConversionDetailsGet200Response run( ){
	
		DateTime startTime = new DateTime("2019-03-21T00:00:00.0Z");
		DateTime endTime = new DateTime("2019-03-21T23:00:00.0Z");
		String organizationId = "testrest";

		ReportingV3ConversionDetailsGet200Response result = null;
		try
		{
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			ConversionDetailsApi apiInstance = new ConversionDetailsApi(apiClient);
			result = apiInstance.getConversionDetail( startTime, endTime, organizationId );

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


