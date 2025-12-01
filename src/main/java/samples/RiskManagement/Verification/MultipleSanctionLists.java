package samples.RiskManagement.Verification;

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

public class MultipleSanctionLists {
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

	public static RiskV1ExportComplianceInquiriesPost201Response run() {
	
		ValidateExportComplianceRequest requestObj = new ValidateExportComplianceRequest();

		Riskv1liststypeentriesClientReferenceInformation clientReferenceInformation = new Riskv1liststypeentriesClientReferenceInformation();
		clientReferenceInformation.code("verification example");
		clientReferenceInformation.comments("All fields");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1exportcomplianceinquiriesOrderInformation orderInformation = new Riskv1exportcomplianceinquiriesOrderInformation();
		Riskv1exportcomplianceinquiriesOrderInformationBillTo orderInformationBillTo = new Riskv1exportcomplianceinquiriesOrderInformationBillTo();
		orderInformationBillTo.address1("901 Metro Centre Blvd");
		orderInformationBillTo.address2(" ");
		orderInformationBillTo.address3("");
		orderInformationBillTo.address4("Foster City");
		orderInformationBillTo.administrativeArea("NH");
		orderInformationBillTo.country("US");
		orderInformationBillTo.locality("CA");
		orderInformationBillTo.postalCode("03055");
		Riskv1exportcomplianceinquiriesOrderInformationBillToCompany orderInformationBillToCompany = new Riskv1exportcomplianceinquiriesOrderInformationBillToCompany();
		orderInformationBillToCompany.name("A & C International Trade, Inc.");
		orderInformationBillTo.company(orderInformationBillToCompany);

		orderInformationBillTo.firstName("Suman");
		orderInformationBillTo.lastName("Kumar");
		orderInformationBillTo.email("test@domain.com");
		orderInformation.billTo(orderInformationBillTo);

		Riskv1exportcomplianceinquiriesOrderInformationShipTo orderInformationShipTo = new Riskv1exportcomplianceinquiriesOrderInformationShipTo();
		orderInformationShipTo.country("IN");
		orderInformationShipTo.firstName("DumbelDore");
		orderInformationShipTo.lastName("Albus");
		orderInformation.shipTo(orderInformationShipTo);


		List <Riskv1exportcomplianceinquiriesOrderInformationLineItems> lineItems =  new ArrayList <Riskv1exportcomplianceinquiriesOrderInformationLineItems>();
		Riskv1exportcomplianceinquiriesOrderInformationLineItems lineItems1 = new Riskv1exportcomplianceinquiriesOrderInformationLineItems();
		lineItems1.unitPrice("120.50");
		lineItems1.quantity(3);
		lineItems1.productSKU("610009");
		lineItems1.productName("Xer");
		lineItems1.productCode("physical_software");
		lineItems.add(lineItems1);

		orderInformation.lineItems(lineItems);

		requestObj.orderInformation(orderInformation);

		Riskv1addressverificationsBuyerInformation buyerInformation = new Riskv1addressverificationsBuyerInformation();
		buyerInformation.merchantCustomerId("Export1");
		requestObj.buyerInformation(buyerInformation);

		Riskv1exportcomplianceinquiriesDeviceInformation deviceInformation = new Riskv1exportcomplianceinquiriesDeviceInformation();
		deviceInformation.ipAddress("127.0.0.1");
		deviceInformation.hostName("www.cybersource.ir");
		requestObj.deviceInformation(deviceInformation);

		Riskv1exportcomplianceinquiriesExportComplianceInformation exportComplianceInformation = new Riskv1exportcomplianceinquiriesExportComplianceInformation();
		exportComplianceInformation.addressOperator("and");
		Ptsv2paymentsWatchlistScreeningInformationWeights exportComplianceInformationWeights = new Ptsv2paymentsWatchlistScreeningInformationWeights();
		exportComplianceInformationWeights.address("low");
		exportComplianceInformationWeights.company("exact");
		exportComplianceInformationWeights.name("exact");
		exportComplianceInformation.weights(exportComplianceInformationWeights);


		List <String> sanctionLists = new ArrayList <String>();
		sanctionLists.add("Bureau Of Industry and Security");
		sanctionLists.add("DOS_DTC");
		sanctionLists.add("AUSTRALIA");
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
