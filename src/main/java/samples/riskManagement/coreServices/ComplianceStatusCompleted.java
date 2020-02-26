package samples.riskManagement.coreServices;

import java.util.*;
import com.cybersource.authsdk.core.MerchantConfig;

import Api.*;
import Data.Configuration;
import Invokers.ApiClient;
import Model.*;

public class ComplianceStatusCompleted {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static RiskV1ExportComplianceInquiriesPost201Response run() {
	
		ValidateExportComplianceRequest requestObj = new ValidateExportComplianceRequest();

		Riskv1addressverificationsClientReferenceInformation clientReferenceInformation = new Riskv1addressverificationsClientReferenceInformation();
		clientReferenceInformation.code("verification example");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1exportcomplianceinquiriesOrderInformation orderInformation = new Riskv1exportcomplianceinquiriesOrderInformation();
		Riskv1exportcomplianceinquiriesOrderInformationBillTo orderInformationBillTo = new Riskv1exportcomplianceinquiriesOrderInformationBillTo();
		orderInformationBillTo.address1("901 Metro Centre Blvd");
		orderInformationBillTo.address2("2");
		orderInformationBillTo.administrativeArea("CA");
		orderInformationBillTo.country("US");
		orderInformationBillTo.locality("Foster City");
		orderInformationBillTo.postalCode("94404");
		orderInformationBillTo.firstName("Suman");
		orderInformationBillTo.lastName("Kumar");
		orderInformationBillTo.email("donewithhorizon@test.com");
		orderInformation.billTo(orderInformationBillTo);

		Riskv1exportcomplianceinquiriesOrderInformationShipTo orderInformationShipTo = new Riskv1exportcomplianceinquiriesOrderInformationShipTo();
		orderInformationShipTo.country("be");
		orderInformationShipTo.firstName("DumbelDore");
		orderInformationShipTo.lastName("Albus");
		orderInformation.shipTo(orderInformationShipTo);


		List <Riskv1exportcomplianceinquiriesOrderInformationLineItems> lineItems =  new ArrayList <Riskv1exportcomplianceinquiriesOrderInformationLineItems>();
		Riskv1exportcomplianceinquiriesOrderInformationLineItems lineItems1 = new Riskv1exportcomplianceinquiriesOrderInformationLineItems();
		lineItems1.unitPrice("19.00");
		lineItems.add(lineItems1);

		orderInformation.lineItems(lineItems);

		requestObj.orderInformation(orderInformation);

		Riskv1addressverificationsBuyerInformation buyerInformation = new Riskv1addressverificationsBuyerInformation();
		buyerInformation.merchantCustomerId("87789");
		requestObj.buyerInformation(buyerInformation);

		Riskv1exportcomplianceinquiriesExportComplianceInformation exportComplianceInformation = new Riskv1exportcomplianceinquiriesExportComplianceInformation();
		exportComplianceInformation.addressOperator("and");
		Riskv1exportcomplianceinquiriesExportComplianceInformationWeights exportComplianceInformationWeights = new Riskv1exportcomplianceinquiriesExportComplianceInformationWeights();
		exportComplianceInformationWeights.address("abc");
		exportComplianceInformationWeights.company("def");
		exportComplianceInformationWeights.name("adb");
		exportComplianceInformation.weights(exportComplianceInformationWeights);


		List <String> sanctionLists = new ArrayList <String>();
		sanctionLists.add("abc");
		sanctionLists.add("acc");
		sanctionLists.add("bac");
		exportComplianceInformation.sanctionLists(sanctionLists);

		requestObj.exportComplianceInformation(exportComplianceInformation);

		RiskV1ExportComplianceInquiriesPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			VerificationApi apiInstance = new VerificationApi(apiClient);
			result = apiInstance.validateExportCompliance(requestObj);

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
	
	public static void main(String args[]) throws Exception{
		
			run();
	}
}
