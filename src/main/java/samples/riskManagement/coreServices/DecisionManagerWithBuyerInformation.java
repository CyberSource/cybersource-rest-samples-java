package samples.riskManagement.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.DecisionManagerApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreateDecisionManagerCaseRequest;
import Model.Ptsv2paymentsBuyerInformationPersonalIdentification;
import Model.RiskV1DecisionsPost201Response;
import Model.Riskv1decisionsBuyerInformation;
import Model.Riskv1decisionsClientReferenceInformation;
import Model.Riskv1decisionsOrderInformation;
import Model.Riskv1decisionsOrderInformationAmountDetails;
import Model.Riskv1decisionsOrderInformationBillTo;
import Model.Riskv1decisionsPaymentInformation;
import Model.Riskv1decisionsPaymentInformationCard;

/**
 * 
 * This is the sample code for Decision Manager Request With Details of Buyer
 * information
 *
 */
public class DecisionManagerWithBuyerInformation {
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

		// set Buyer information details
		Riskv1decisionsBuyerInformation riskv1decisionsBuyerInformation = new Riskv1decisionsBuyerInformation();
		riskv1decisionsBuyerInformation.hashedPassword("");
		riskv1decisionsBuyerInformation.dateOfBirth("1998-05-05");

		// set Personal Information of the buyer
		Ptsv2paymentsBuyerInformationPersonalIdentification ptsv2paymentsBuyerInformationPersonalIdentification0 = new Ptsv2paymentsBuyerInformationPersonalIdentification();
		ptsv2paymentsBuyerInformationPersonalIdentification0.type("CPF");
		ptsv2paymentsBuyerInformationPersonalIdentification0.id("1a23apwe98");
		riskv1decisionsBuyerInformation
				.addPersonalIdentificationItem(ptsv2paymentsBuyerInformationPersonalIdentification0);

		createDecisionManagerCaseRequest.buyerInformation(riskv1decisionsBuyerInformation);
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
			
			ApiClient apiClient = new ApiClient();
			
			apiClient.merchantConfig = merchantConfig;	
			DecisionManagerApi decisionManagerApi = new DecisionManagerApi(apiClient);
			response = decisionManagerApi.createDecisionManagerCase(createDecisionManagerCaseRequest);
			responseCode = apiClient.responseCode;
			status = apiClient.status;

			System.out.println("ResponseCode :" + responseCode);
			System.out.println("Status :" + status);
			System.out.println(response);

		} catch (ApiException e) {
			e.printStackTrace();
		}

	}
}
