package samples.Payments.Payments;

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

public class SaleUsingEMVTechnologyWithContactlessReadForCardPresentEnabledAcquirer {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static PtsV2PaymentsPost201Response run() {
	
		CreatePaymentRequest requestObj = new CreatePaymentRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("123456");
		Ptsv2paymentsClientReferenceInformationPartner clientReferenceInformationPartner = new Ptsv2paymentsClientReferenceInformationPartner();
		clientReferenceInformationPartner.originalTransactionId("510be4aef90711e6acbc7d88388d803d");
		clientReferenceInformation.partner(clientReferenceInformationPartner);

		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
		processingInformation.capture(true);
		processingInformation.commerceIndicator("retail");
		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("100.00");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsPointOfSaleInformation pointOfSaleInformation = new Ptsv2paymentsPointOfSaleInformation();
		pointOfSaleInformation.catLevel(1);
		pointOfSaleInformation.entryMode("contactless");
		pointOfSaleInformation.terminalCapability(5);
		Ptsv2paymentsPointOfSaleInformationEmv pointOfSaleInformationEmv = new Ptsv2paymentsPointOfSaleInformationEmv();
		pointOfSaleInformationEmv.tags("9F3303204000950500000000009F3704518823719F100706011103A000009F26081E1756ED0E2134E29F36020015820200009C01009F1A0208409A030006219F02060000000020005F2A0208409F0306000000000000");
		pointOfSaleInformationEmv.cardholderVerificationMethodUsed(2);
		pointOfSaleInformationEmv.cardSequenceNumber("1");
		pointOfSaleInformationEmv.fallback(false);
		pointOfSaleInformation.emv(pointOfSaleInformationEmv);

		pointOfSaleInformation.trackData("%B4111111111111111^TEST/CYBS         ^2012121019761100      00868000000?;");

		List <String> cardholderVerificationMethod = new ArrayList <String>();
		cardholderVerificationMethod.add("pin");
		cardholderVerificationMethod.add("signature");
		pointOfSaleInformation.cardholderVerificationMethod(cardholderVerificationMethod);


		List <String> terminalInputCapability = new ArrayList <String>();
		terminalInputCapability.add("contact");
		terminalInputCapability.add("contactless");
		terminalInputCapability.add("keyed");
		terminalInputCapability.add("swiped");
		pointOfSaleInformation.terminalInputCapability(terminalInputCapability);

		pointOfSaleInformation.terminalCardCaptureCapability("1");
		pointOfSaleInformation.deviceId("123lkjdIOBK34981slviLI39bj");
		pointOfSaleInformation.encryptedKeySerialNumber("01043191");
		requestObj.pointOfSaleInformation(pointOfSaleInformation);

		PtsV2PaymentsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PaymentsApi apiInstance = new PaymentsApi(apiClient);
			result = apiInstance.createPayment(requestObj);

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
