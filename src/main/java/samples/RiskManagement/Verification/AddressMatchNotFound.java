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

public class AddressMatchNotFound {
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

	public static RiskV1AddressVerificationsPost201Response run() {
	
		VerifyCustomerAddressRequest requestObj = new VerifyCustomerAddressRequest();

		Riskv1liststypeentriesClientReferenceInformation clientReferenceInformation = new Riskv1liststypeentriesClientReferenceInformation();
		clientReferenceInformation.code("addressEg");
		clientReferenceInformation.comments("dav-error response check");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1addressverificationsOrderInformation orderInformation = new Riskv1addressverificationsOrderInformation();
		Riskv1addressverificationsOrderInformationBillTo orderInformationBillTo = new Riskv1addressverificationsOrderInformationBillTo();
		orderInformationBillTo.address1("Apt C ");
		orderInformationBillTo.address2("");
		orderInformationBillTo.administrativeArea("CA");
		orderInformationBillTo.country("US");
		orderInformationBillTo.locality("Glendale");
		orderInformationBillTo.postalCode("91204");
		orderInformation.billTo(orderInformationBillTo);

		requestObj.orderInformation(orderInformation);

		RiskV1AddressVerificationsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			VerificationApi apiInstance = new VerificationApi(apiClient);
			result = apiInstance.verifyCustomerAddress(requestObj);

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
