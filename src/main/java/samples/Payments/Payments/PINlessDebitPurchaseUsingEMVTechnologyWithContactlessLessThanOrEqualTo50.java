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

public class PINlessDebitPurchaseUsingEMVTechnologyWithContactlessLessThanOrEqualTo50 {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void WriteLogAudit(int status) {
		String filename = MethodHandles.lookup().lookupClass().getSimpleName();
		System.out.println("[Sample Code Testing] [" + filename + "] " + status);
	}

	public static void main(String[] args) {
		run();
	}

	public static PtsV2PaymentsPost201Response run() {
	
		CreatePaymentRequest requestObj = new CreatePaymentRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("PIN-less Debit Purchase Using EMV Contactless");
		Ptsv2paymentsClientReferenceInformationPartner clientReferenceInformationPartner = new Ptsv2paymentsClientReferenceInformationPartner();
		clientReferenceInformationPartner.thirdPartyCertificationNumber("PTP1234");
		clientReferenceInformation.partner(clientReferenceInformationPartner);

		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
		processingInformation.capture(false);
		processingInformation.commerceIndicator("retail");
		processingInformation.networkRoutingOrder("VMHF");
		requestObj.processingInformation(processingInformation);

		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
		paymentInformationCard.useAs("");
		paymentInformationCard.sourceAccountType("UA");
		paymentInformation.card(paymentInformationCard);

		Ptsv2paymentsPaymentInformationPaymentType paymentInformationPaymentType = new Ptsv2paymentsPaymentInformationPaymentType();
		paymentInformationPaymentType.name("CARD");
		paymentInformationPaymentType.subTypeName("DEBIT");
		paymentInformation.paymentType(paymentInformationPaymentType);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("10.00");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsPointOfSaleInformation pointOfSaleInformation = new Ptsv2paymentsPointOfSaleInformation();
		pointOfSaleInformation.entryMode("contactless");
		pointOfSaleInformation.terminalCapability(4);
		Ptsv2paymentsPointOfSaleInformationEmv pointOfSaleInformationEmv = new Ptsv2paymentsPointOfSaleInformationEmv();
		pointOfSaleInformationEmv.tags("9F3303204000950500000000009F3704518823719F100706011103A000009F26081E1756ED0E2134E29F36020015820200009C01009F1A0208409A030006219F02060000000020005F2A0208409F0306000000000000");
		pointOfSaleInformationEmv.cardSequenceNumber("1");
		pointOfSaleInformation.emv(pointOfSaleInformationEmv);

		pointOfSaleInformation.trackData(";4111111111111111=33121019761186800000?");
		pointOfSaleInformation.terminalPinCapability(0);
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