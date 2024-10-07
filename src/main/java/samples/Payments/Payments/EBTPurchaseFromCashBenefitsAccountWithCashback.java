package samples.Payments.Payments;

import java.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.math.BigDecimal;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.common.base.Strings;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Invokers.ApiResponse;
import Model.*;

public class EBTPurchaseFromCashBenefitsAccountWithCashback {
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

	public static PtsV2PaymentsPost201Response run() {
	
		CreatePaymentRequest requestObj = new CreatePaymentRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("EBT - Purchase from Cash Benefits Account with CB");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
		processingInformation.capture(false);
		processingInformation.commerceIndicator("retail");
		Ptsv2paymentsProcessingInformationPurchaseOptions processingInformationPurchaseOptions = new Ptsv2paymentsProcessingInformationPurchaseOptions();
		processingInformationPurchaseOptions.isElectronicBenefitsTransfer(true);
		processingInformation.purchaseOptions(processingInformationPurchaseOptions);

		Ptsv2paymentsProcessingInformationElectronicBenefitsTransfer processingInformationElectronicBenefitsTransfer = new Ptsv2paymentsProcessingInformationElectronicBenefitsTransfer();
		processingInformationElectronicBenefitsTransfer.category("CASH");
		processingInformation.electronicBenefitsTransfer(processingInformationElectronicBenefitsTransfer);

		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
		paymentInformationCard.type("001");
		paymentInformation.card(paymentInformationCard);

		Ptsv2paymentsPaymentInformationPaymentType paymentInformationPaymentType = new Ptsv2paymentsPaymentInformationPaymentType();
		paymentInformationPaymentType.name("CARD");
		paymentInformationPaymentType.subTypeName("DEBIT");
		paymentInformation.paymentType(paymentInformationPaymentType);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("702.00");
		orderInformationAmountDetails.currency("USD");
		orderInformationAmountDetails.cashbackAmount("45.00");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsPointOfSaleInformation pointOfSaleInformation = new Ptsv2paymentsPointOfSaleInformation();
		pointOfSaleInformation.entryMode("swiped");
		pointOfSaleInformation.terminalCapability(4);
		pointOfSaleInformation.trackData("%B4111111111111111^JONES/JONES ^3112101976110000868000000?;4111111111111111=16121019761186800000?");
		pointOfSaleInformation.pinBlockEncodingFormat(1);
		pointOfSaleInformation.encryptedPin("52F20658C04DB351");
		pointOfSaleInformation.encryptedKeySerialNumber("FFFF1B1D140000000005");
		requestObj.pointOfSaleInformation(pointOfSaleInformation);

		PtsV2PaymentsPost201Response result = null;
		try {
			merchantProp = Configuration.getAlternativeMerchantDetails();
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