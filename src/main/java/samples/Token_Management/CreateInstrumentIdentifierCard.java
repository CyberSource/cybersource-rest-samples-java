// 51
// Code Generated: createInstrumentIdentifier[Create Instrument Identifier (Card)]

package samples.Token_Management;
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

public class CreateInstrumentIdentifierCard{
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

/*
	public static void main(String args[]) throws Exception 
	{
		// Accept required parameters from args[] and pass to run.
		run( profileid );
	}
*/
	public static TmsV1InstrumentIdentifiersPost200Response run( String profileid ){
	
		CreateInstrumentIdentifierRequest requestObj = new CreateInstrumentIdentifierRequest();

		Tmsv1instrumentidentifiersCard card = new Tmsv1instrumentidentifiersCard();
		card.number("411111111111112");
		requestObj.card(card);

		TmsV1InstrumentIdentifiersPost200Response result = null;
		try
		{
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			InstrumentIdentifierApi apiInstance = new InstrumentIdentifierApi(apiClient);
			result = apiInstance.createInstrumentIdentifier( profileid, requestObj );

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


