package samples.Payments.Void;

import java.*;
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
import samples.Payments.Payments.PinDebitPurchaseUsingSwipedTrackDataWithVisaPlatformConnect;

public class PinDebitPurchaseReversalVoid {
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
	public static PtsV2PaymentsVoidsPost201Response run() {
		
		PtsV2PaymentsPost201Response purchaseResponse = PinDebitPurchaseUsingSwipedTrackDataWithVisaPlatformConnect.run();
		String id = purchaseResponse.getId();
	
		VoidPaymentRequest requestObj = new VoidPaymentRequest();

		Ptsv2paymentsidreversalsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsidreversalsClientReferenceInformation();
		clientReferenceInformation.code("Pin Debit Purchase Reversal(Void)");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsidvoidsPaymentInformation paymentInformation = new Ptsv2paymentsidvoidsPaymentInformation();
		Ptsv2paymentsidrefundsPaymentInformationPaymentType paymentInformationPaymentType = new Ptsv2paymentsidrefundsPaymentInformationPaymentType();
		paymentInformationPaymentType.name("CARD");
		paymentInformationPaymentType.subTypeName("DEBIT");
		paymentInformation.paymentType(paymentInformationPaymentType);
		requestObj.paymentInformation(paymentInformation);
		
		Ptsv2paymentsidvoidsOrderInformation orderInformation = new Ptsv2paymentsidvoidsOrderInformation();
		Ptsv2paymentsidreversalsReversalInformationAmountDetails amountDetails = new Ptsv2paymentsidreversalsReversalInformationAmountDetails();
		amountDetails.currency("USD");
		amountDetails.totalAmount("202.00");
		orderInformation.amountDetails(amountDetails);
		requestObj.orderInformation(orderInformation);

		PtsV2PaymentsVoidsPost201Response result = null;
		try {
			merchantProp = Configuration.getAlternativeMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			VoidApi apiInstance = new VoidApi(apiClient);
			result = apiInstance.voidPayment(requestObj, id);

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
