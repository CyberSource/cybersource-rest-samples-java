package samples.PayerAuthentication;

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

public class EnrollWithTravelInformation {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		run();
	}
	public static RiskV1AuthenticationsPost201Response run() {
	
		CheckPayerAuthEnrollmentRequest requestObj = new CheckPayerAuthEnrollmentRequest();

		Riskv1decisionsClientReferenceInformation clientReferenceInformation = new Riskv1decisionsClientReferenceInformation();
		clientReferenceInformation.code("cybs_test");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1authenticationsOrderInformation orderInformation = new Riskv1authenticationsOrderInformation();
		Riskv1authenticationsOrderInformationAmountDetails orderInformationAmountDetails = new Riskv1authenticationsOrderInformationAmountDetails();
		orderInformationAmountDetails.currency("USD");
		orderInformationAmountDetails.totalAmount("10.99");
		orderInformation.amountDetails(orderInformationAmountDetails);

		Riskv1authenticationsOrderInformationBillTo orderInformationBillTo = new Riskv1authenticationsOrderInformationBillTo();
		orderInformationBillTo.address1("1 Market St");
		orderInformationBillTo.address2("Address 2");
		orderInformationBillTo.administrativeArea("CA");
		orderInformationBillTo.country("US");
		orderInformationBillTo.locality("san francisco");
		orderInformationBillTo.firstName("John");
		orderInformationBillTo.lastName("Doe");
		orderInformationBillTo.phoneNumber("4158880000");
		orderInformationBillTo.email("test@cybs.com");
		orderInformationBillTo.postalCode("94105");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		Riskv1authenticationsPaymentInformation paymentInformation = new Riskv1authenticationsPaymentInformation();
		Riskv1authenticationsPaymentInformationCard paymentInformationCard = new Riskv1authenticationsPaymentInformationCard();
		paymentInformationCard.type("002");
		paymentInformationCard.expirationMonth("12");
		paymentInformationCard.expirationYear("2025");
		paymentInformationCard.number("5200340000000015");
		paymentInformation.card(paymentInformationCard);

		requestObj.paymentInformation(paymentInformation);

		Riskv1authenticationsBuyerInformation buyerInformation = new Riskv1authenticationsBuyerInformation();
		buyerInformation.mobilePhone(1245789632);
		requestObj.buyerInformation(buyerInformation);

		Riskv1decisionsConsumerAuthenticationInformation consumerAuthenticationInformation = new Riskv1decisionsConsumerAuthenticationInformation();
		consumerAuthenticationInformation.transactionMode("MOTO");
		requestObj.consumerAuthenticationInformation(consumerAuthenticationInformation);

		Riskv1authenticationsTravelInformation travelInformation = new Riskv1authenticationsTravelInformation();

		List <Riskv1decisionsTravelInformationLegs> legs =  new ArrayList <Riskv1decisionsTravelInformationLegs>();
		Riskv1decisionsTravelInformationLegs legs1 = new Riskv1decisionsTravelInformationLegs();
		legs1.destination("DEF");
		legs1.carrierCode("UA");
		legs1.departureDate("2019-01-01");
		legs.add(legs1);

		Riskv1decisionsTravelInformationLegs legs2 = new Riskv1decisionsTravelInformationLegs();
		legs2.destination("RES");
		legs2.carrierCode("AS");
		legs2.departureDate("2019-02-21");
		legs.add(legs2);

		travelInformation.legs(legs);

		travelInformation.numberOfPassengers(2);

		List <Riskv1decisionsTravelInformationPassengers> passengers =  new ArrayList <Riskv1decisionsTravelInformationPassengers>();
		Riskv1decisionsTravelInformationPassengers passengers1 = new Riskv1decisionsTravelInformationPassengers();
		passengers1.firstName("Raj");
		passengers1.lastName("Charles");
		passengers.add(passengers1);

		Riskv1decisionsTravelInformationPassengers passengers2 = new Riskv1decisionsTravelInformationPassengers();
		passengers2.firstName("Potter");
		passengers2.lastName("Suhember");
		passengers.add(passengers2);

		travelInformation.passengers(passengers);

		requestObj.travelInformation(travelInformation);

		RiskV1AuthenticationsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PayerAuthenticationApi apiInstance = new PayerAuthenticationApi(apiClient);
			result = apiInstance.checkPayerAuthEnrollment(requestObj);

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
