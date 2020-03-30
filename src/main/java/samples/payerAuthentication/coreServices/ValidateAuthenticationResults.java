//package samples.payerAuthentication.coreServices;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//import com.cybersource.authsdk.core.MerchantConfig;
//
//import Api.PayerAuthenticationApi;
//import Data.Configuration;
//import Invokers.ApiClient;
//import Invokers.ApiException;
//import Model.Request;
//import Model.RiskV1AuthenticationResultsPost201Response;
//import Model.Riskv1authenticationresultsConsumerAuthenticationInformation;
//import Model.Riskv1authenticationresultsOrderInformation;
//import Model.Riskv1authenticationresultsOrderInformationLineItems;
//import Model.Riskv1authenticationresultsPaymentInformation;
//import Model.Riskv1authenticationresultsPaymentInformationCard;
//import Model.Riskv1authenticationsClientReferenceInformation;
//import Model.Riskv1decisionsOrderInformationAmountDetails;
//
//public class ValidateAuthenticationResults {
//	
//	private static Request requestObj = new Request();
//	private static Properties merchantProp;
//	private static String responseCode = null;
//	private static String status = null;
//	private static RiskV1AuthenticationResultsPost201Response response;
//
//	public static void main(String[] args) throws Exception {
//		try {
//			requestObj = getRequest(requestObj);
//			// set Merchant Details
//			merchantProp = Configuration.getMerchantDetails();
//
//			MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
//			
//			ApiClient apiClient = new ApiClient();
//			apiClient.merchantConfig = merchantConfig;
//			
//			PayerAuthenticationApi payerAuthApi = new PayerAuthenticationApi(apiClient);
//			response = payerAuthApi.validateAuthenticationResults(requestObj);
//			
//			responseCode = apiClient.responseCode;
//			status = apiClient.status;
//
//			System.out.println("ResponseCode :" + responseCode);
//			System.out.println("Status :" + status);
//			System.out.println(response);
//		}
//		catch(ApiException e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	public static Request getRequest(Request request) {
//		Riskv1authenticationsClientReferenceInformation clientReferenceInformation = new Riskv1authenticationsClientReferenceInformation();
//
//		clientReferenceInformation.code("pavalidatecheck");
//		request.clientReferenceInformation(clientReferenceInformation);
//
//		Riskv1authenticationresultsOrderInformation orderInformation = new Riskv1authenticationresultsOrderInformation();
//
//		Riskv1decisionsOrderInformationAmountDetails amountDetails = new Riskv1decisionsOrderInformationAmountDetails();
//
//		amountDetails.currency("USD");
//		amountDetails.totalAmount("200.00");
//		orderInformation.amountDetails(amountDetails);
//
//		List<Riskv1authenticationresultsOrderInformationLineItems> lineItems = new ArrayList<Riskv1authenticationresultsOrderInformationLineItems>();
//
//		Riskv1authenticationresultsOrderInformationLineItems lineItems0 = new Riskv1authenticationresultsOrderInformationLineItems();
//		lineItems0.unitPrice("10");
//		lineItems0.quantity(BigDecimal.valueOf(2));
//		lineItems0.taxAmount("32.40");
//		lineItems.add(lineItems0);
//
//		request.orderInformation(orderInformation);
//
//		Riskv1authenticationresultsPaymentInformation paymentInformation = new Riskv1authenticationresultsPaymentInformation();
//
//		Riskv1authenticationresultsPaymentInformationCard card = new Riskv1authenticationresultsPaymentInformationCard();
//
//		card.type("002");
//		card.expirationMonth("12");
//		card.expirationYear("2025");
//		card.number("5200000000000007");
//		paymentInformation.card(card);
//
//		request.paymentInformation(paymentInformation);
//
//		Riskv1authenticationresultsConsumerAuthenticationInformation consumerAuthenticationInformation = new Riskv1authenticationresultsConsumerAuthenticationInformation();
//
//		consumerAuthenticationInformation.authenticationTransactionId("PYffv9G3sa1e0CQr5fV0");
//		consumerAuthenticationInformation.signedPares("eNqdmFmT4jgSgN+J4D90zD4yMz45PEFVhHzgA2zwjXnzhQ984Nvw61dAV1");
//		request.consumerAuthenticationInformation(consumerAuthenticationInformation);
//		
//		return request;
//	}
//
//}
