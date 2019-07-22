// 58
// Code Generated: createPaymentInstrument[Create Payment Instrument (Bank Account)]

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

public class CreatePaymentInstrumentBankAccount{
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception
	{
		// Accept required parameters from args[] and pass to run.
		run( "93B32398-AD51-4CC2-A682-EA3E93614EB1" );
	}
	public static TmsV1PaymentInstrumentsPost201Response run( String profileid ){
	
		CreatePaymentInstrumentRequest requestObj = new CreatePaymentInstrumentRequest();

		Tmsv1paymentinstrumentsBankAccount bankAccount = new Tmsv1paymentinstrumentsBankAccount();
		bankAccount.type("savings");
		requestObj.bankAccount(bankAccount);

		Tmsv1paymentinstrumentsBuyerInformation buyerInformation = new Tmsv1paymentinstrumentsBuyerInformation();
		buyerInformation.companyTaxID("12345");
		buyerInformation.currency("USD");

		List <Tmsv1paymentinstrumentsBuyerInformationPersonalIdentification> personalIdentification =  new ArrayList <Tmsv1paymentinstrumentsBuyerInformationPersonalIdentification>();
		Tmsv1paymentinstrumentsBuyerInformationPersonalIdentification personalIdentification1 = new Tmsv1paymentinstrumentsBuyerInformationPersonalIdentification();
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

		Tmsv1paymentinstrumentsProcessingInformation processingInformation = new Tmsv1paymentinstrumentsProcessingInformation();
		processingInformation.billPaymentProgramEnabled(true);
		Tmsv1paymentinstrumentsProcessingInformationBankTransferOptions processingInformationBankTransferOptions = new Tmsv1paymentinstrumentsProcessingInformationBankTransferOptions();
		processingInformationBankTransferOptions.seCCode("WEB");
		processingInformation.bankTransferOptions(processingInformationBankTransferOptions);

		requestObj.processingInformation(processingInformation);

		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedMerchantInformation merchantInformation = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedMerchantInformation();
		TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedMerchantInformationMerchantDescriptor merchantInformationMerchantDescriptor = new TmsV1InstrumentIdentifiersPaymentInstrumentsGet200ResponseEmbeddedMerchantInformationMerchantDescriptor();
		merchantInformationMerchantDescriptor.alternateName("Branch Name");
		merchantInformation.merchantDescriptor(merchantInformationMerchantDescriptor);

		requestObj.merchantInformation(merchantInformation);

		Tmsv1paymentinstrumentsInstrumentIdentifier instrumentIdentifier = new Tmsv1paymentinstrumentsInstrumentIdentifier();
		TmsV1InstrumentIdentifiersPost200ResponseBankAccount instrumentIdentifierBankAccount = new TmsV1InstrumentIdentifiersPost200ResponseBankAccount();
		instrumentIdentifierBankAccount.number("4100");
		instrumentIdentifierBankAccount.routingNumber("071923284");
		instrumentIdentifier.bankAccount(instrumentIdentifierBankAccount);

		requestObj.instrumentIdentifier(instrumentIdentifier);

		TmsV1PaymentInstrumentsPost201Response result = null;
		try
		{
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentInstrumentApi apiInstance = new PaymentInstrumentApi(apiClient);
			result = apiInstance.createPaymentInstrument( profileid, requestObj );

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


