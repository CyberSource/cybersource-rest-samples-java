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

public class EnrollWithTransientToken {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static RiskV1AuthenticationsPost201Response run() {
	
		CheckPayerAuthEnrollmentRequest requestObj = new CheckPayerAuthEnrollmentRequest();

		Riskv1decisionsClientReferenceInformation clientReferenceInformation = new Riskv1decisionsClientReferenceInformation();
		clientReferenceInformation.code("UNKNOWN");
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

		Riskv1authenticationsetupsTokenInformation tokenInformation = new Riskv1authenticationsetupsTokenInformation();
		tokenInformation.transientToken("eyJraWQiOiIwOFRPcEh3YlFhQlpZRk9BY1dSZUpHb2ZNYUpvMk85USIsImFsZyI6IlJTMjU2In0.eyJkYXRhIjp7Im51bWJlciI6IjQxMTExMVhYWFhYWDExMTEiLCJ0eXBlIjoiMDAxIn0sImlzcyI6IkZsZXgvMDgiLCJleHAiOjE1OTU5MjA0NDYsInR5cGUiOiJtZi0wLjExLjAiLCJpYXQiOjE1OTU5MTk1NDYsImp0aSI6IjFFNTM3NlhUR0Y4MjgwN0xERFhHODdaNVhZQ1NKRkNUMlhDMDNVSzgxRzAzWEVGN0xRRzg1RjFGRDAzRUI4NTYifQ.rxJOaTwfQLvElP43nZ7h60dx4-vTo6az-ej7owO1W_kGLixn6PlH7QTnpMZxc9RdDVRLcYwMs4fEYF1-D5Ua7iDeqKoCyA3uBGJ_G_zb1HMQlSK3K_KyWRiwNUbwFItHKIUonesJPajuauH_6GKI072_SZtnfnnQkToMRZtt379w_hP0eERztH-JNAMmwOPSc7Tq99QkaKXokfHinLk53tvI_NONyXcLf4Bthtwf-Li7_HbVLe28n-1HNPAoJX5VHdLOG9PhmsxMLQiAUbV41I810OXTxvHM6_VNSPE2xnfJV_yqU_GoI2E_zyeQkXB2w53qYOFa8n4LDhSEts3F8g");
		requestObj.tokenInformation(tokenInformation);

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
