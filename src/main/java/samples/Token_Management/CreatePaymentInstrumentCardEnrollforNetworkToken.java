// 54
// Code Generated: createPaymentInstrument[Create Payment Instrument (Card & Enroll for Network Token)]

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

public class CreatePaymentInstrumentCardEnrollforNetworkToken{
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
	public static TmsV1PaymentInstrumentsPost201Response run( String profileid ){
	
		CreatePaymentInstrumentRequest requestObj = new CreatePaymentInstrumentRequest();

		Tmsv1paymentinstrumentsCard card = new Tmsv1paymentinstrumentsCard();
		card.expirationMonth("09");
		card.expirationYear("2017");
		card.type("visa");
		card.issueNumber("01");
		card.startMonth("01");
		card.startYear("2016");
		requestObj.card(card);

		Tmsv1paymentinstrumentsBuyerInformation buyerInformation = new Tmsv1paymentinstrumentsBuyerInformation();
		buyerInformation.companyTaxID("12345");
		buyerInformation.currency("USD");
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
		billTo.country("United States");
		billTo.email("john.smith@test.com");
		billTo.phoneNumber("+44 2890447951");
		requestObj.billTo(billTo);

		Tmsv1paymentinstrumentsProcessingInformation processingInformation = new Tmsv1paymentinstrumentsProcessingInformation();
		processingInformation.billPaymentProgramEnabled(true);
		requestObj.processingInformation(processingInformation);

		Tmsv1paymentinstrumentsInstrumentIdentifier instrumentIdentifier = new Tmsv1paymentinstrumentsInstrumentIdentifier();
		TmsV1InstrumentIdentifiersPost200ResponseCard instrumentIdentifierCard = new TmsV1InstrumentIdentifiersPost200ResponseCard();
		instrumentIdentifierCard.number("4622943127013705");
		instrumentIdentifier.card(instrumentIdentifierCard);

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


