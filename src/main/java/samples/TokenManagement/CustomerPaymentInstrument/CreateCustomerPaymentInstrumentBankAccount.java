package samples.TokenManagement.CustomerPaymentInstrument;

import java.lang.invoke.MethodHandles;
import java.util.*;

import org.joda.time.LocalDate;

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

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String args[]) throws Exception {
		run();
	}
	public static PostCustomerPaymentInstrumentRequest run() {
		String customerTokenId = "AB695DA801DD1BB6E05341588E0A3BDC";
		
		PostCustomerPaymentInstrumentRequest requestObj = new PostCustomerPaymentInstrumentRequest();

		Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBankAccount bankAccount = new Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBankAccount();
		bankAccount.type("savings");
		requestObj.bankAccount(bankAccount);

		Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBuyerInformation buyerInformation = new Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBuyerInformation();
		buyerInformation.companyTaxID("12345");
		buyerInformation.currency("USD");
		buyerInformation.dateOfBirth(new LocalDate("2000-12-13"));

		List <Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBuyerInformationPersonalIdentification> personalIdentification =  new ArrayList <Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBuyerInformationPersonalIdentification>();
		Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBuyerInformationPersonalIdentification personalIdentification1 = new Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBuyerInformationPersonalIdentification();
		personalIdentification1.id("57684432111321");
		personalIdentification1.type("driver license");
		Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBuyerInformationIssuedBy issuedBy1 = new Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBuyerInformationIssuedBy();
		issuedBy1.administrativeArea("CA");
		personalIdentification1.issuedBy(issuedBy1);

		personalIdentification.add(personalIdentification1);

		buyerInformation.personalIdentification(personalIdentification);

		requestObj.buyerInformation(buyerInformation);

		Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBillTo billTo = new Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentBillTo();
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

		TmsPaymentInstrumentProcessingInfo processingInformation = new TmsPaymentInstrumentProcessingInfo();
		TmsPaymentInstrumentProcessingInfoBankTransferOptions processingInformationBankTransferOptions = new TmsPaymentInstrumentProcessingInfoBankTransferOptions();
		processingInformationBankTransferOptions.seCCode("WEB");
		processingInformation.bankTransferOptions(processingInformationBankTransferOptions);

		requestObj.processingInformation(processingInformation);

		Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentInstrumentIdentifier instrumentIdentifier = new Tmsv2tokenizeTokenInformationCustomerEmbeddedDefaultPaymentInstrumentInstrumentIdentifier();
		instrumentIdentifier.id("A7A91A2CA872B272E05340588D0A0699");
		requestObj.instrumentIdentifier(instrumentIdentifier);

		PostCustomerPaymentInstrumentRequest result = null;
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
			WriteLogAudit(Integer.parseInt(responseCode));
		} catch (ApiException e) {
			e.printStackTrace();
			WriteLogAudit(e.getCode());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
