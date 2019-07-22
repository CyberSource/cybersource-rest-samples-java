// 48
// Code Generated: getFileDetail[Get list of files]

package samples.Secure_File_Share;
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

public class Getlistoffiles{
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
	public static V1FileDetailsGet200Response run( ){
	
		LocalDate startDate = new LocalDate("2018-10-20T00:00:00.000Z");
		LocalDate endDate = new LocalDate("2018-10-30T00:00:00.000Z");
		String organizationId = "testrest";

		V1FileDetailsGet200Response result = null;
		try
		{
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			SecureFileShareApi apiInstance = new SecureFileShareApi(apiClient);
			result = apiInstance.getFileDetail( startDate, endDate, organizationId );

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


