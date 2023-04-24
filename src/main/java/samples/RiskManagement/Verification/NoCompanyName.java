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

public class NoCompanyName {
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
