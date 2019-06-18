package samples.payerAuthentication.coreServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PayerAuthenticationApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CheckPayerAuthEnrollmentRequest;
import Model.RiskV1AuthenticationsPost201Response;
import Model.Riskv1authenticationsBuyerInformation;
import Model.Riskv1authenticationsClientReferenceInformation;
import Model.Riskv1authenticationsConsumerAuthenticationInformation;
import Model.Riskv1authenticationsOrderInformation;
import Model.Riskv1authenticationsOrderInformationBillTo;
import Model.Riskv1authenticationsPaymentInformation;
import Model.Riskv1authenticationsPaymentInformationCard;
import Model.Riskv1authenticationsTravelInformation;
import Model.Riskv1authenticationsTravelInformationLegs;
import Model.Riskv1authenticationsTravelInformationPassengers;
import Model.Riskv1decisionsOrderInformationAmountDetails;

public class EnrollWithTravelInformation {
	
	private static CheckPayerAuthEnrollmentRequest requestObj = new CheckPayerAuthEnrollmentRequest();
	private static Properties merchantProp;
	private static String responseCode = null;
	private static String status = null;
	private static RiskV1AuthenticationsPost201Response response;

	public static void main(String[] args) throws Exception {
		try {
			requestObj = getRequest(requestObj);
			// set Merchant Details
			merchantProp = Configuration.getMerchantDetails();

			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			
			ApiClient apiClient = new ApiClient();
			apiClient.merchantConfig = merchantConfig;
			
			PayerAuthenticationApi payerAuthApi = new PayerAuthenticationApi(apiClient);
			response = payerAuthApi.checkPayerAuthEnrollment(requestObj);
			
			responseCode = apiClient.responseCode;
			status = apiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response);
		}
		catch(ApiException e)
		{
			e.printStackTrace();
		}
	}

	public static CheckPayerAuthEnrollmentRequest getRequest(CheckPayerAuthEnrollmentRequest request) {
		Riskv1authenticationsClientReferenceInformation clientReferenceInformation = new Riskv1authenticationsClientReferenceInformation();

		clientReferenceInformation.code("cybs_test");
		request.clientReferenceInformation(clientReferenceInformation);

		Riskv1authenticationsOrderInformation orderInformation = new Riskv1authenticationsOrderInformation();

		Riskv1decisionsOrderInformationAmountDetails amountDetails = new Riskv1decisionsOrderInformationAmountDetails();

		amountDetails.currency("USD");
		amountDetails.totalAmount("10.99");
		orderInformation.amountDetails(amountDetails);

		Riskv1authenticationsOrderInformationBillTo billTo = new Riskv1authenticationsOrderInformationBillTo();

		billTo.address1("1 Market St");
		billTo.address2("Address 2");
		billTo.administrativeArea("CA");
		billTo.country("US");
		billTo.locality("san francisco");
		billTo.firstName("John");
		billTo.lastName("Doe");
		billTo.phoneNumber("4158880000");
		billTo.email("test@cybs.com");
		billTo.postalCode("94105");
		orderInformation.billTo(billTo);

		request.orderInformation(orderInformation);

		Riskv1authenticationsPaymentInformation paymentInformation = new Riskv1authenticationsPaymentInformation();

		Riskv1authenticationsPaymentInformationCard card = new Riskv1authenticationsPaymentInformationCard();

		card.type("002");
		card.expirationMonth("12");
		card.expirationYear("2025");
		card.number("5200340000000015");
		paymentInformation.card(card);

		request.paymentInformation(paymentInformation);

		Riskv1authenticationsBuyerInformation buyerInformation = new Riskv1authenticationsBuyerInformation();

		buyerInformation.mobilePhone(1245789632);
		request.buyerInformation(buyerInformation);

		Riskv1authenticationsConsumerAuthenticationInformation consumerAuthenticationInformation = new Riskv1authenticationsConsumerAuthenticationInformation();

		consumerAuthenticationInformation.transactionMode("MOTO");
		request.consumerAuthenticationInformation(consumerAuthenticationInformation);

		Riskv1authenticationsTravelInformation travelInformation = new Riskv1authenticationsTravelInformation();

		List<Riskv1authenticationsTravelInformationLegs> legs = new ArrayList<Riskv1authenticationsTravelInformationLegs>();

		Riskv1authenticationsTravelInformationLegs legs0 = new Riskv1authenticationsTravelInformationLegs();
		legs0.destination("DEFGH");
		legs0.carrierCode("UA");
		legs0.departureDate("2019-01-01");
		legs.add(legs0);

		Riskv1authenticationsTravelInformationLegs legs1 = new Riskv1authenticationsTravelInformationLegs();
		legs1.destination("RESD");
		legs1.carrierCode("AS");
		legs1.departureDate("2019-02-21");
		legs.add(legs1);

		travelInformation.legs(legs);
		travelInformation.numberOfPassengers(2);
		List<Riskv1authenticationsTravelInformationPassengers> passengers = new ArrayList<Riskv1authenticationsTravelInformationPassengers>();

		Riskv1authenticationsTravelInformationPassengers passengers0 = new Riskv1authenticationsTravelInformationPassengers();
		passengers0.firstName("Raj");
		passengers0.lastName("Charles");
		passengers.add(passengers0);

		Riskv1authenticationsTravelInformationPassengers passengers1 = new Riskv1authenticationsTravelInformationPassengers();
		passengers1.firstName("Potter");
		passengers1.lastName("Suhember");
		passengers.add(passengers1);

		request.travelInformation(travelInformation);
		
		return request;
	}

}
