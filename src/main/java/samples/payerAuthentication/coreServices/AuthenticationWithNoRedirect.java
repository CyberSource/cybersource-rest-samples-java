package samples.payerAuthentication.coreServices;

import java.util.Properties;

import com.cybersource.authsdk.core.MerchantConfig;

import Api.PayerAuthenticationApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.CheckPayerAuthEnrollmentRequest;
import Model.RiskV1AuthenticationsPost201Response;
import Model.Riskv1authenticationsClientReferenceInformation;
import Model.Riskv1authenticationsOrderInformation;
import Model.Riskv1authenticationsOrderInformationBillTo;
import Model.Riskv1authenticationsPaymentInformation;
import Model.Riskv1authenticationsPaymentInformationCard;
import Model.Riskv1decisionsOrderInformationAmountDetails;

public class AuthenticationWithNoRedirect {
	
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

	public static CheckPayerAuthEnrollmentRequest getRequest(CheckPayerAuthEnrollmentRequest requestObj) {
		Riskv1authenticationsClientReferenceInformation clientReferenceInformation = new Riskv1authenticationsClientReferenceInformation();

		clientReferenceInformation.code("cybs_test");
		requestObj.clientReferenceInformation(clientReferenceInformation);

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

		requestObj.orderInformation(orderInformation);

		Riskv1authenticationsPaymentInformation paymentInformation = new Riskv1authenticationsPaymentInformation();

		Riskv1authenticationsPaymentInformationCard card = new Riskv1authenticationsPaymentInformationCard();

		card.type("001");
		card.expirationMonth("12");
		card.expirationYear("2025");
		card.number("4000990000000004");
		paymentInformation.card(card);

		requestObj.paymentInformation(paymentInformation);
		
		return requestObj;
	}

}
