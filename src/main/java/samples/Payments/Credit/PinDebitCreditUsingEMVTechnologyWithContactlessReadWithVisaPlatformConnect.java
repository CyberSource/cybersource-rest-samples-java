package samples.Payments.Credit;

import java.*;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles;
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

public class PinDebitCreditUsingEMVTechnologyWithContactlessReadWithVisaPlatformConnect {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;
	
	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static PtsV2CreditsPost201Response run() {
	
		CreateCreditRequest requestObj = new CreateCreditRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("2.2 Credit");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2creditsProcessingInformation processingInformation = new Ptsv2creditsProcessingInformation();
		processingInformation.commerceIndicator("retail");
		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsidrefundsPaymentInformation paymentInformation = new Ptsv2paymentsidrefundsPaymentInformation();
		Ptsv2paymentsidrefundsPaymentInformationPaymentType paymentInformationPaymentType = new Ptsv2paymentsidrefundsPaymentInformationPaymentType();
		paymentInformationPaymentType.name("CARD");
		paymentInformationPaymentType.subTypeName("DEBIT");
		paymentInformation.paymentType(paymentInformationPaymentType);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsidrefundsOrderInformation orderInformation = new Ptsv2paymentsidrefundsOrderInformation();
		Ptsv2paymentsidcapturesOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsidcapturesOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("202.00");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsidrefundsMerchantInformation merchantInformation = new Ptsv2paymentsidrefundsMerchantInformation();
		requestObj.merchantInformation(merchantInformation);

		Ptsv2paymentsPointOfSaleInformation pointOfSaleInformation = new Ptsv2paymentsPointOfSaleInformation();
		pointOfSaleInformation.entryMode("contactless");
		pointOfSaleInformation.terminalCapability(4);
		Ptsv2paymentsPointOfSaleInformationEmv pointOfSaleInformationEmv = new Ptsv2paymentsPointOfSaleInformationEmv();
		pointOfSaleInformationEmv.tags("9F3303204000950500000000009F3704518823719F100706011103A000009F26081E1756ED0E2134E29F36020015820200009C01009F1A0208409A030006219F02060000000020005F2A0208409F0306000000000000");
		pointOfSaleInformationEmv.cardSequenceNumber("1");
		pointOfSaleInformationEmv.fallback(false);
		pointOfSaleInformation.emv(pointOfSaleInformationEmv);

		pointOfSaleInformation.trackData("%B4111111111111111^JONES/JONES ^3112101976110000868000000?;4111111111111111=16121019761186800000?");
		requestObj.pointOfSaleInformation(pointOfSaleInformation);

		PtsV2CreditsPost201Response result = null;
		try {
			merchantProp = Configuration.getAlternativeMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			CreditApi apiInstance = new CreditApi(apiClient);
			result = apiInstance.createCredit(requestObj);

			responseCode = apiClient.responseCode;
			status = apiClient.status;
			System.out.println("ResponseCode :" + responseCode);
			System.out.println("ResponseMessage :" + status);
			System.out.println(result);
			WriteLogAudit(Integer.parseInt(responseCode));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	return result;
	}
}
