package samples.decisionManager.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.DecisionManagerApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreateDecisionManagerCaseRequest;
import Model.RiskV1DecisionsPost201Response;
import Model.Riskv1decisionsClientReferenceInformation;
import Model.Riskv1decisionsOrderInformation;
import Model.Riskv1decisionsOrderInformationAmountDetails;
import Model.Riskv1decisionsOrderInformationBillTo;
import Model.Riskv1decisionsPaymentInformation;
import Model.Riskv1decisionsPaymentInformationCard;
import Model.Riskv1decisionsTravelInformation;
import Model.Riskv1decisionsTravelInformationLegs;

/**
 * 
 * This is the sample code for Decision Manager Request With Details of Travel
 * information
 *
 */
public class DMWithTravelInformation {
	private static Properties merchantProp;
	private static String responseCode = null;
	private static String status = null;
	private static RiskV1DecisionsPost201Response response;

	/**
	 * Function to create Decision Manager Case Request
	 * 
	 * @return
	 * @throws Exception
	 */
	public static CreateDecisionManagerCaseRequest getRequest(
			CreateDecisionManagerCaseRequest createDecisionManagerCaseRequest) throws Exception {
		createDecisionManagerCaseRequest = new CreateDecisionManagerCaseRequest();

		// set Client reference information
		Riskv1decisionsClientReferenceInformation riskv1decisionsClientReferenceInformation = new Riskv1decisionsClientReferenceInformation();
		riskv1decisionsClientReferenceInformation.code("54323007");
		createDecisionManagerCaseRequest.clientReferenceInformation(riskv1decisionsClientReferenceInformation);

		Riskv1decisionsPaymentInformation riskv1decisionsPaymentInformation = new Riskv1decisionsPaymentInformation();
		Riskv1decisionsPaymentInformationCard riskv1decisionsPaymentInformationCard = new Riskv1decisionsPaymentInformationCard();
		riskv1decisionsPaymentInformationCard.setNumber("4444444444444448");
		riskv1decisionsPaymentInformationCard.expirationMonth("12");
		riskv1decisionsPaymentInformationCard.setExpirationYear("2020");
		riskv1decisionsPaymentInformation.card(riskv1decisionsPaymentInformationCard);
		createDecisionManagerCaseRequest.paymentInformation(riskv1decisionsPaymentInformation);

		// set Order information
		Riskv1decisionsOrderInformation riskv1decisionsOrderInformation = new Riskv1decisionsOrderInformation();
		Riskv1decisionsOrderInformationAmountDetails riskv1decisionsOrderInformationAmountDetails = new Riskv1decisionsOrderInformationAmountDetails();
		riskv1decisionsOrderInformationAmountDetails.setTotalAmount("144.14");
		riskv1decisionsOrderInformationAmountDetails.setCurrency("USD");
		riskv1decisionsOrderInformation.amountDetails(riskv1decisionsOrderInformationAmountDetails);

		// set bill to information
		Riskv1decisionsOrderInformationBillTo riskv1decisionsOrderInformationBillTo = new Riskv1decisionsOrderInformationBillTo();
		riskv1decisionsOrderInformationBillTo.address1("96, powers street");
		riskv1decisionsOrderInformationBillTo.country("US");
		riskv1decisionsOrderInformationBillTo.locality("Clearwater milford");
		riskv1decisionsOrderInformationBillTo.firstName("James");
		riskv1decisionsOrderInformationBillTo.lastName("Smith");
		riskv1decisionsOrderInformationBillTo.phoneNumber("7606160717");
		riskv1decisionsOrderInformationBillTo.email("test@visa.com");
		riskv1decisionsOrderInformationBillTo.postalCode("03055");
		riskv1decisionsOrderInformationBillTo.administrativeArea("NH");
		riskv1decisionsOrderInformation.billTo(riskv1decisionsOrderInformationBillTo);
		createDecisionManagerCaseRequest.orderInformation(riskv1decisionsOrderInformation);

		// Set Travel Information Details
		Riskv1decisionsTravelInformation riskv1decisionsTravelInformation = new Riskv1decisionsTravelInformation();
		riskv1decisionsTravelInformation.completeRoute("SFO-JFK:JFK-BLR");
		riskv1decisionsTravelInformation.journeyType("One way");

		// Set Travel leg information

		// set Travel Leg1 details
		Riskv1decisionsTravelInformationLegs riskv1decisionsTravelInformationLegs0 = new Riskv1decisionsTravelInformationLegs();
		riskv1decisionsTravelInformationLegs0.origination("SFO");
		riskv1decisionsTravelInformationLegs0.destination("JFK");
		riskv1decisionsTravelInformation.addLegsItem(riskv1decisionsTravelInformationLegs0);

		// set Travel leg2 details
		Riskv1decisionsTravelInformationLegs riskv1decisionsTravelInformationLegs1 = new Riskv1decisionsTravelInformationLegs();
		riskv1decisionsTravelInformationLegs1.origination("JFK");
		riskv1decisionsTravelInformationLegs1.destination("BLR");
		riskv1decisionsTravelInformation.addLegsItem(riskv1decisionsTravelInformationLegs1);

		createDecisionManagerCaseRequest.setTravelInformation(riskv1decisionsTravelInformation);
		return createDecisionManagerCaseRequest;
	}

	public static void main(String args[]) throws Exception {
		try {
			CreateDecisionManagerCaseRequest createDecisionManagerCaseRequest = null;
			// Create the Create Decision Manager Request
			createDecisionManagerCaseRequest = getRequest(createDecisionManagerCaseRequest);

			// set Merchant Details
			merchantProp = Configuration.getMerchantDetails();

			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			ApiClient.merchantConfig = merchantConfig;
			DecisionManagerApi decisionManagerApi = new DecisionManagerApi();
			response = decisionManagerApi.createDecisionManagerCase(createDecisionManagerCaseRequest);
			responseCode = ApiClient.responseCode;
			status = ApiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response);

		} catch (ApiException e) {
			e.printStackTrace();
		}

	}
}
