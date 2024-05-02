package samples.TokenManagement.PaymentInstrument;

import java.lang.invoke.MethodHandles;
import java.util.*;

import org.joda.time.LocalDate;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.*;

public class CreatePaymentInstrumentBankAccount {
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

	public static PostPaymentInstrumentRequest run() {
		String profileid = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
	
		PostPaymentInstrumentRequest requestObj = new PostPaymentInstrumentRequest();

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

		TmsPaymentInstrumentProcessingInfo processingInformation = new TmsPaymentInstrumentProcessingInfo();
		TmsPaymentInstrumentProcessingInfoBankTransferOptions processingInformationBankTransferOptions = new TmsPaymentInstrumentProcessingInfoBankTransferOptions();
		processingInformationBankTransferOptions.seCCode("WEB");
		processingInformation.bankTransferOptions(processingInformationBankTransferOptions);

		requestObj.processingInformation(processingInformation);

		Tmsv2customersEmbeddedDefaultPaymentInstrumentInstrumentIdentifier instrumentIdentifier = new Tmsv2customersEmbeddedDefaultPaymentInstrumentInstrumentIdentifier();
		instrumentIdentifier.id("A7A91A2CA872B272E05340588D0A0699");
		requestObj.instrumentIdentifier(instrumentIdentifier);

		PostPaymentInstrumentRequest result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentInstrumentApi apiInstance = new PaymentInstrumentApi(apiClient);
			result = apiInstance.postPaymentInstrument(requestObj, profileid);

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
