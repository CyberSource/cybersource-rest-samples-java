package samples.Payments.Payments;

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

public class AuthorizationWithDecisionManagerBuyerInformation {
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
	public static PtsV2PaymentsPost201Response run() {
	
		CreatePaymentRequest requestObj = new CreatePaymentRequest();

		Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
		clientReferenceInformation.code("54323007");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
		Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
		paymentInformationCard.number("4444444444444448");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2020");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
		Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
		orderInformationAmountDetails.totalAmount("144.14");
		orderInformationAmountDetails.currency("USD");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
		orderInformationBillTo.firstName("James");
		orderInformationBillTo.lastName("Smith");
		orderInformationBillTo.address1("96, powers street");
		orderInformationBillTo.locality("Clearwater milford");
		orderInformationBillTo.administrativeArea("NH");
		orderInformationBillTo.postalCode("03055");
		orderInformationBillTo.country("US");
		orderInformationBillTo.email("test@visa.com");
		orderInformationBillTo.phoneNumber("7606160717");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		Ptsv2paymentsBuyerInformation buyerInformation = new Ptsv2paymentsBuyerInformation();
		buyerInformation.dateOfBirth("19980505");

		List <Ptsv2paymentsBuyerInformationPersonalIdentification> personalIdentification =  new ArrayList <Ptsv2paymentsBuyerInformationPersonalIdentification>();
		Ptsv2paymentsBuyerInformationPersonalIdentification personalIdentification1 = new Ptsv2paymentsBuyerInformationPersonalIdentification();
		personalIdentification1.type("CPF");
		personalIdentification1.id("1a23apwe98");
		personalIdentification.add(personalIdentification1);

		buyerInformation.personalIdentification(personalIdentification);

		buyerInformation.hashedPassword("");
		requestObj.buyerInformation(buyerInformation);

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
