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

public class CreatePaymentInstrumentBankAccount{
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

/*
	public static void main(String args[]) throws Exception 
	{
		// Accept required parameters from args[] and pass to run.
		run(profileid);
	}
*/
	public static TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments run(String profileid){
	
		CreatePaymentInstrumentRequest requestObj = new CreatePaymentInstrumentRequest();

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBankAccount bankAccount = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBankAccount();
		bankAccount.type("savings");
		requestObj.bankAccount(bankAccount);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBuyerInformation buyerInformation = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBuyerInformation();
		buyerInformation.companyTaxID("12345");
		buyerInformation.currency("USD");

		List <TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBuyerInformationPersonalIdentification> personalIdentification =  new ArrayList <TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBuyerInformationPersonalIdentification>();
		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBuyerInformationPersonalIdentification personalIdentification1 = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBuyerInformationPersonalIdentification();
		personalIdentification1.id("57684432111321");
		personalIdentification1.type("driver license");
		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBuyerInformationIssuedBy issuedBy1 = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBuyerInformationIssuedBy();
		issuedBy1.administrativeArea("CA");
		personalIdentification1.issuedBy(issuedBy1);

		personalIdentification.add(personalIdentification1);

		buyerInformation.personalIdentification(personalIdentification);

		requestObj.buyerInformation(buyerInformation);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBillTo billTo = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedBillTo();
		billTo.firstName("John");
		billTo.lastName("Smith");
		billTo.company("Cybersource");
		billTo.address1("8310 Capital of Texas Highwas North");
		billTo.address2("Bluffstone Drive");
		billTo.locality("Austin");
		billTo.administrativeArea("TX");
		billTo.postalCode("78731");
		billTo.country("US");
		billTo.email("john.smith@test.com");
		billTo.phoneNumber("+44 2890447951");
		requestObj.billTo(billTo);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedProcessingInformation processingInformation = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedProcessingInformation();
		processingInformation.billPaymentProgramEnabled(true);
		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedProcessingInformationBankTransferOptions processingInformationBankTransferOptions = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedProcessingInformationBankTransferOptions();
		processingInformationBankTransferOptions.seCCode("WEB");
		processingInformation.bankTransferOptions(processingInformationBankTransferOptions);

		requestObj.processingInformation(processingInformation);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedMerchantInformation merchantInformation = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedMerchantInformation();
		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedMerchantInformationMerchantDescriptor merchantInformationMerchantDescriptor = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedMerchantInformationMerchantDescriptor();
		merchantInformationMerchantDescriptor.alternateName("Branch Name");
		merchantInformation.merchantDescriptor(merchantInformationMerchantDescriptor);

		requestObj.merchantInformation(merchantInformation);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedInstrumentIdentifier instrumentIdentifier = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedInstrumentIdentifier();
		Tmsv1instrumentidentifiersBankAccount instrumentIdentifierBankAccount = new Tmsv1instrumentidentifiersBankAccount();
		instrumentIdentifierBankAccount.number("4100");
		instrumentIdentifierBankAccount.routingNumber("071923284");
		instrumentIdentifier.bankAccount(instrumentIdentifierBankAccount);

		requestObj.instrumentIdentifier(instrumentIdentifier);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedPaymentInstruments result = null;
		try
		{
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentInstrumentApi apiInstance = new PaymentInstrumentApi(apiClient);
			result = apiInstance.createPaymentInstrument(profileid, requestObj);

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
