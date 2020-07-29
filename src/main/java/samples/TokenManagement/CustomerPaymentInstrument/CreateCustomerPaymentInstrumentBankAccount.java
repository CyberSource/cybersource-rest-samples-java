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

public class CreateCustomerPaymentInstrumentBankAccount {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}
	public static Tmsv2customersEmbeddedDefaultPaymentInstrument run() {
		String customerTokenId = "AB695DA801DD1BB6E05341588E0A3BDC";
		
		PostCustomerPaymentInstrumentRequest requestObj = new PostCustomerPaymentInstrumentRequest();

		Tmsv2customersEmbeddedDefaultPaymentInstrumentBankAccount bankAccount = new Tmsv2customersEmbeddedDefaultPaymentInstrumentBankAccount();
		bankAccount.type("savings");
		requestObj.bankAccount(bankAccount);

		Tmsv2customersEmbeddedDefaultPaymentInstrumentBuyerInformation buyerInformation = new Tmsv2customersEmbeddedDefaultPaymentInstrumentBuyerInformation();
		buyerInformation.companyTaxID("12345");
		buyerInformation.currency("USD");
		buyerInformation.dateOfBirth(new LocalDate("2000-12-13"));

		List <Tmsv2customersEmbeddedDefaultPaymentInstrumentBuyerInformationPersonalIdentification> personalIdentification =  new ArrayList <Tmsv2customersEmbeddedDefaultPaymentInstrumentBuyerInformationPersonalIdentification>();
		Tmsv2customersEmbeddedDefaultPaymentInstrumentBuyerInformationPersonalIdentification personalIdentification1 = new Tmsv2customersEmbeddedDefaultPaymentInstrumentBuyerInformationPersonalIdentification();
		personalIdentification1.id("57684432111321");
		personalIdentification1.type("driver license");
		Tmsv2customersEmbeddedDefaultPaymentInstrumentBuyerInformationIssuedBy issuedBy1 = new Tmsv2customersEmbeddedDefaultPaymentInstrumentBuyerInformationIssuedBy();
		issuedBy1.administrativeArea("CA");
		personalIdentification1.issuedBy(issuedBy1);

		personalIdentification.add(personalIdentification1);

		buyerInformation.personalIdentification(personalIdentification);

		requestObj.buyerInformation(buyerInformation);

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

		Tmsv2customersEmbeddedDefaultPaymentInstrumentProcessingInformation processingInformation = new Tmsv2customersEmbeddedDefaultPaymentInstrumentProcessingInformation();
		Tmsv2customersEmbeddedDefaultPaymentInstrumentProcessingInformationBankTransferOptions processingInformationBankTransferOptions = new Tmsv2customersEmbeddedDefaultPaymentInstrumentProcessingInformationBankTransferOptions();
		processingInformationBankTransferOptions.seCCode("WEB");
		processingInformation.bankTransferOptions(processingInformationBankTransferOptions);

		requestObj.processingInformation(processingInformation);

		Tmsv2customersEmbeddedDefaultPaymentInstrumentInstrumentIdentifier instrumentIdentifier = new Tmsv2customersEmbeddedDefaultPaymentInstrumentInstrumentIdentifier();
		instrumentIdentifier.id("A7A91A2CA872B272E05340588D0A0699");
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
