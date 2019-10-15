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

public class CreateInstrumentIdentifierCardEnrollForNetworkToken {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

/*
	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run(profileid);
	}
*/
	public static TmsV1InstrumentIdentifiersPost200Response run(String profileid) {
	
		CreateInstrumentIdentifierRequest requestObj = new CreateInstrumentIdentifierRequest();

		requestObj.type("enrollable card");
		Tmsv1instrumentidentifiersCard card = new Tmsv1instrumentidentifiersCard();
		card.number("4622943127013705");
		card.expirationMonth("12");
		card.expirationYear("2022");
		card.securityCode("838");
		requestObj.card(card);

		Tmsv1instrumentidentifiersBillTo billTo = new Tmsv1instrumentidentifiersBillTo();
		billTo.address1("8310 Capital of Texas Highway North");
		billTo.address2("Bluffstone Drive");
		billTo.locality("Austin");
		billTo.administrativeArea("TX");
		billTo.postalCode("78731");
		billTo.country("US");
		requestObj.billTo(billTo);

		TmsV1InstrumentIdentifiersPost200Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			InstrumentIdentifierApi apiInstance = new InstrumentIdentifierApi(apiClient);
			result = apiInstance.createInstrumentIdentifier(profileid, requestObj);

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
