// 31
// Code Generated: searchReports[Retrieve available reports]

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

public class Retrieveavailablereports{
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
	public static ReportingV3ReportsGet200Response run( ){
	
		String organizationId = null;
		DateTime startTime = new DateTime("2018-10-01T00:00:00.0Z");
		DateTime endTime = new DateTime("2018-10-30T23:59:59.0Z");
		String timeQueryType = "executedTime";
		String reportMimeType = "application/xml";
		String reportFrequency = null;
		String reportName = null;
		Integer reportDefinitionId = null;
		String reportStatus = null;

		ReportingV3ReportsGet200Response result = null;
		try
		{
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			ReportsApi apiInstance = new ReportsApi(apiClient);
			result = apiInstance.searchReports( startTime, endTime, timeQueryType, organizationId, reportMimeType, reportFrequency, reportName, reportDefinitionId, reportStatus );

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


