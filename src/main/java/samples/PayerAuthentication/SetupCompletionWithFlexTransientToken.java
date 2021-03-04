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

public class SetupCompletionWithFlexTransientToken {
	private static String responseCode = null;
	private static String status = null;
	private static Properties merchantProp;

	public static void main(String args[]) throws Exception {
		// Accept required parameters from args[] and pass to run.
		run();
	}
	public static RiskV1AuthenticationSetupsPost201Response run() {
	
		PayerAuthSetupRequest requestObj = new PayerAuthSetupRequest();

		Riskv1decisionsClientReferenceInformation clientReferenceInformation = new Riskv1decisionsClientReferenceInformation();
		clientReferenceInformation.code("cybs_test");
		requestObj.clientReferenceInformation(clientReferenceInformation);

		Riskv1authenticationsetupsTokenInformation tokenInformation = new Riskv1authenticationsetupsTokenInformation();
		tokenInformation.transientToken("eyJraWQiOiIwOFRPcEh3YlFhQlpZRk9BY1dSZUpHb2ZNYUpvMk85USIsImFsZyI6IlJTMjU2In0.eyJkYXRhIjp7Im51bWJlciI6IjQxMTExMVhYWFhYWDExMTEiLCJ0eXBlIjoiMDAxIn0sImlzcyI6IkZsZXgvMDgiLCJleHAiOjE1OTU5MjA0NDYsInR5cGUiOiJtZi0wLjExLjAiLCJpYXQiOjE1OTU5MTk1NDYsImp0aSI6IjFFNTM3NlhUR0Y4MjgwN0xERFhHODdaNVhZQ1NKRkNUMlhDMDNVSzgxRzAzWEVGN0xRRzg1RjFGRDAzRUI4NTYifQ.rxJOaTwfQLvElP43nZ7h60dx4-vTo6az-ej7owO1W_kGLixn6PlH7QTnpMZxc9RdDVRLcYwMs4fEYF1-D5Ua7iDeqKoCyA3uBGJ_G_zb1HMQlSK3K_KyWRiwNUbwFItHKIUonesJPajuauH_6GKI072_SZtnfnnQkToMRZtt379w_hP0eERztH-JNAMmwOPSc7Tq99QkaKXokfHinLk53tvI_NONyXcLf4Bthtwf-Li7_HbVLe28n-1HNPAoJX5VHdLOG9PhmsxMLQiAUbV41I810OXTxvHM6_VNSPE2xnfJV_yqU_GoI2E_zyeQkXB2w53qYOFa8n4LDhSEts3F8g");
		requestObj.tokenInformation(tokenInformation);

		RiskV1AuthenticationSetupsPost201Response result = null;
		try {
			merchantProp = Configuration.getMerchantDetails();
			ApiClient apiClient = new ApiClient();
			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
			apiClient.merchantConfig = merchantConfig;

			PayerAuthenticationApi apiInstance = new PayerAuthenticationApi(apiClient);
			result = apiInstance.payerAuthSetup(requestObj);

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
