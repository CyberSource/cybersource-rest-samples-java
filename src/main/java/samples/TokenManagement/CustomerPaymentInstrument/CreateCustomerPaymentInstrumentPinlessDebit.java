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

public class CreateCustomerPaymentInstrumentPinlessDebit {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}
	public static Tmsv2customersEmbeddedDefaultPaymentInstrument run() {
		String customerTokenId = "AB695DA801DD1BB6E05341588E0A3BDC";
		
		PostCustomerPaymentInstrumentRequest requestObj = new PostCustomerPaymentInstrumentRequest();

		Tmsv2customersEmbeddedDefaultPaymentInstrumentCard card = new Tmsv2customersEmbeddedDefaultPaymentInstrumentCard();
		card.expirationMonth("12");
		card.expirationYear("2031");
		card.type("001");
		card.issueNumber("01");
		card.startMonth("01");
		card.startYear("2020");
		card.useAs("pinless debit");
		requestObj.card(card);

		Tmsv2customersEmbeddedDefaultPaymentInstrumentBillTo billTo = new Tmsv2customersEmbeddedDefaultPaymentInstrumentBillTo();
		billTo.firstName("John");
		billTo.lastName("Doe");
		billTo.company("CyberSource");
		billTo.address1("1 Market St");
		billTo.locality("San Francisco");
		billTo.administrativeArea("CA");
		billTo.postalCode("94105");
		billTo.country("US");
		billTo.email("test@cybs.com");
		billTo.phoneNumber("4158880000");
		requestObj.billTo(billTo);

		Tmsv2customersEmbeddedDefaultPaymentInstrumentInstrumentIdentifier instrumentIdentifier = new Tmsv2customersEmbeddedDefaultPaymentInstrumentInstrumentIdentifier();
		instrumentIdentifier.id("7010000000016241111");
		requestObj.instrumentIdentifier(instrumentIdentifier);

		Tmsv2customersEmbeddedDefaultPaymentInstrument result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CustomerPaymentInstrumentApi apiInstance = new CustomerPaymentInstrumentApi(apiClient);
			result = apiInstance.postCustomerPaymentInstrument(customerTokenId, requestObj, null);

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
