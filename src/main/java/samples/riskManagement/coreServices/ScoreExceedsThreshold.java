package samples.riskManagement.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.DecisionManagerApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CreateBundledDecisionManagerCaseRequest;
import Model.Ptsv2paymentsRiskInformationProfile;
import Model.RiskV1DecisionsPost201Response;
import Model.Riskv1decisionsClientReferenceInformation;
import Model.Riskv1decisionsOrderInformation;
import Model.Riskv1decisionsOrderInformationAmountDetails;
import Model.Riskv1decisionsOrderInformationBillTo;
import Model.Riskv1decisionsOrderInformationShipTo;
import Model.Riskv1decisionsPaymentInformation;
import Model.Riskv1decisionsPaymentInformationCard;
import Model.Riskv1decisionsRiskInformation;
/**
 * 
 * This is the sample code for score Exceeds Threshold
 *
 */
public class ScoreExceedsThreshold {
	private static Properties merchantProp;
	private static String responseCode = null;
	private static String status = null;
	private static RiskV1DecisionsPost201Response response;
	
	
	/**
	 * sample request to simulate score exceeds threshold
	 * @return
	 * @throws Exception
	 */
	public static CreateBundledDecisionManagerCaseRequest getRequest(CreateBundledDecisionManagerCaseRequest CreateBundledDecisionManagerCaseRequest) throws Exception
	{
		CreateBundledDecisionManagerCaseRequest= new CreateBundledDecisionManagerCaseRequest();
		
		
		// set Client reference information 
		Riskv1decisionsClientReferenceInformation riskv1decisionsClientReferenceInformation= new Riskv1decisionsClientReferenceInformation();
		riskv1decisionsClientReferenceInformation.code("54323007");
		CreateBundledDecisionManagerCaseRequest.clientReferenceInformation(riskv1decisionsClientReferenceInformation);
		
		Riskv1decisionsPaymentInformation riskv1decisionsPaymentInformation= new Riskv1decisionsPaymentInformation();
		Riskv1decisionsPaymentInformationCard riskv1decisionsPaymentInformationCard= new Riskv1decisionsPaymentInformationCard();
		riskv1decisionsPaymentInformationCard.setNumber("4444444444444448");
		riskv1decisionsPaymentInformationCard.expirationMonth("12");
		riskv1decisionsPaymentInformationCard.setExpirationYear("2020");
		riskv1decisionsPaymentInformation.card(riskv1decisionsPaymentInformationCard);
		CreateBundledDecisionManagerCaseRequest.paymentInformation(riskv1decisionsPaymentInformation);
		
		// set Order information
		Riskv1decisionsOrderInformation riskv1decisionsOrderInformation= new Riskv1decisionsOrderInformation();
		Riskv1decisionsOrderInformationAmountDetails riskv1decisionsOrderInformationAmountDetails= new Riskv1decisionsOrderInformationAmountDetails();
		riskv1decisionsOrderInformationAmountDetails.setTotalAmount("144.14");
		riskv1decisionsOrderInformationAmountDetails.setCurrency("USD");
		riskv1decisionsOrderInformation.amountDetails(riskv1decisionsOrderInformationAmountDetails);
		
		// Set Ship to information
		Riskv1decisionsOrderInformationShipTo riskv1decisionsOrderInformationShipTo= new Riskv1decisionsOrderInformationShipTo();
		riskv1decisionsOrderInformationShipTo.address1("96, powers street");
		riskv1decisionsOrderInformationShipTo.address2("");
		riskv1decisionsOrderInformationShipTo.administrativeArea("KA");
		riskv1decisionsOrderInformationShipTo.country("INDIA");
		riskv1decisionsOrderInformationShipTo.locality("Clearwater milford");
		riskv1decisionsOrderInformationShipTo.firstName("James");
		riskv1decisionsOrderInformationShipTo.lastName("Smith");
		riskv1decisionsOrderInformationShipTo.setPhoneNumber("7606160717");
		riskv1decisionsOrderInformationShipTo.setPostalCode("560056");
		riskv1decisionsOrderInformation.shipTo(riskv1decisionsOrderInformationShipTo);
		
		// set bill to information
		Riskv1decisionsOrderInformationBillTo riskv1decisionsOrderInformationBillTo= new Riskv1decisionsOrderInformationBillTo();
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
		CreateBundledDecisionManagerCaseRequest.orderInformation(riskv1decisionsOrderInformation);
		
		
		// set risk information details with the profile info
		Riskv1decisionsRiskInformation riskv1decisionsRiskInformation= new Riskv1decisionsRiskInformation();
		
		// set the profile info which needs to be rejected
		Ptsv2paymentsRiskInformationProfile riskv1decisionsRiskInformationProfile= new Ptsv2paymentsRiskInformationProfile();
		riskv1decisionsRiskInformationProfile.name("profile2");
		riskv1decisionsRiskInformation.setProfile(riskv1decisionsRiskInformationProfile);
		CreateBundledDecisionManagerCaseRequest.riskInformation(riskv1decisionsRiskInformation);
		
		return CreateBundledDecisionManagerCaseRequest;
	}
	public static void main(String args[]) throws Exception{
		try
		{
			CreateBundledDecisionManagerCaseRequest CreateBundledDecisionManagerCaseRequest=null;
		// Create the Create Decision Manager Request 
					CreateBundledDecisionManagerCaseRequest=getRequest(CreateBundledDecisionManagerCaseRequest);
		
		// set Merchant Details
					merchantProp = Configuration.getMerchantDetails();
		
					MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
					
					ApiClient apiClient = new ApiClient();
					
					apiClient.merchantConfig = merchantConfig;	
					DecisionManagerApi decisionManagerApi= new DecisionManagerApi(apiClient);	
					response=decisionManagerApi.createBundledDecisionManagerCase(CreateBundledDecisionManagerCaseRequest);
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
}
