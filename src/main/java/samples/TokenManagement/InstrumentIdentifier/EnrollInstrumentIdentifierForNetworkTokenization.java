package samples.TokenManagement.InstrumentIdentifier;

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

public class EnrollInstrumentIdentifierForNetworkTokenization {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static void run() {
		String profileid = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
		String instrumentIdentifierTokenId = "7010000000016241111";
	
		PostInstrumentIdentifierEnrollmentRequest requestObj = new PostInstrumentIdentifierEnrollmentRequest();

		requestObj.type("enrollable card");
		Tmsv2customersEmbeddedDefaultPaymentInstrumentEmbeddedInstrumentIdentifierCard card = new Tmsv2customersEmbeddedDefaultPaymentInstrumentEmbeddedInstrumentIdentifierCard();
		card.expirationMonth("12");
		card.expirationYear("2031");
		card.securityCode("123");
		requestObj.card(card);

		Tmsv2customersEmbeddedDefaultPaymentInstrumentEmbeddedInstrumentIdentifierBillTo billTo = new Tmsv2customersEmbeddedDefaultPaymentInstrumentEmbeddedInstrumentIdentifierBillTo();
		billTo.address1("1 Market St");
		billTo.locality("San Francisco");
		billTo.administrativeArea("CA");
		billTo.postalCode("94105");
		billTo.country("US");
		requestObj.billTo(billTo);

		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			InstrumentIdentifierApi apiInstance = new InstrumentIdentifierApi(apiClient);
			apiInstance.postInstrumentIdentifierEnrollment(instrumentIdentifierTokenId, requestObj, profileid);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
