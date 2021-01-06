package samples.authentication.AuthSampleCode;

import Api.OAuthApi;
import Api.PaymentsApi;
import Data.Configuration;
import Invokers.ApiClient;
import Model.*;
import com.cybersource.authsdk.core.MerchantConfig;

import java.util.Properties;

public class StandaloneOAuth {

    private static String responseCode = null;
    private static String status = null;
    private static String code = "";
    private static String grantType = "";
    private static String refreshToken = "";
    private static String accessToken = "";
    private static Properties merchantProp;
    public static MerchantConfig merchantConfig;
    public static boolean createUsingAuthCode = true;

    public static void main(String args[]) throws Exception {
        AccessTokenResponse result;
        if(createUsingAuthCode)
        {
            // Create Access Token using Auth Code
            code = "";
            grantType = "authorization_code";
            result = postAccessTokenFromAuthCode();
        }
        else {
            // Create Access Token using Refresh Token
            grantType = "refresh_token";
            refreshToken = "";
            result = postAccessTokenFromRefreshToken();
        }

        if(result != null && responseCode.equals("200")) {
            refreshToken = result.getRefreshToken();
            accessToken = result.getAccessToken();

            // Save accessToken and refreshToken before making API calls
            merchantConfig.setAccessToken(accessToken);
            merchantConfig.setRefreshToken(refreshToken);

            // Set Authentication to OAuth
            merchantConfig.setAuthenticationType("OAuth");

            //Call Payments SampleCode using OAuth, Set Authentication to OAuth in Sample Code Configuration
            SimpleAuthorizationInternet();
        }

    }
    public static AccessTokenResponse postAccessTokenFromAuthCode() {
        CreateAccessTokenRequest requestObj = new CreateAccessTokenRequest();

        AccessTokenResponse result = null;
        try {
            merchantProp = Configuration.getMerchantDetails();

            // Set Authentication to MutualAuth to call OAuth API
            merchantProp.setProperty("authenticationType","MutualAuth");

            ApiClient apiClient = new ApiClient();
            merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            requestObj.code(code);
            requestObj.grantType(grantType);
            requestObj.clientSecret(merchantConfig.getClientSecret());
            requestObj.clientId(merchantConfig.getClientId());

            OAuthApi apiInstance = new OAuthApi(apiClient);
            result = apiInstance.postAccessTokenFromAuthCode(requestObj);

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

    public static AccessTokenResponse postAccessTokenFromRefreshToken() {
        CreateAccessTokenRequest requestObj = new CreateAccessTokenRequest();

        AccessTokenResponse result = null;
        try {
            merchantProp = Configuration.getMerchantDetails();

            // Set Authentication to MutualAuth to call OAuth API
            merchantProp.setProperty("authenticationType","MutualAuth");

            ApiClient apiClient = new ApiClient();
            merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            requestObj.refreshToken(refreshToken);
            requestObj.grantType(grantType);
            requestObj.clientSecret(merchantConfig.getClientSecret());
            requestObj.clientId(merchantConfig.getClientId());

            OAuthApi apiInstance = new OAuthApi(apiClient);
            result = apiInstance.postAccessTokenFromAuthCode(requestObj);

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

    public static PtsV2PaymentsPost201Response SimpleAuthorizationInternet() {

        CreatePaymentRequest requestObj = new CreatePaymentRequest();

        Ptsv2paymentsClientReferenceInformation clientReferenceInformation = new Ptsv2paymentsClientReferenceInformation();
        clientReferenceInformation.code("TC50171_3");
        requestObj.clientReferenceInformation(clientReferenceInformation);

        Ptsv2paymentsProcessingInformation processingInformation = new Ptsv2paymentsProcessingInformation();
        processingInformation.capture(false);


        requestObj.processingInformation(processingInformation);

        Ptsv2paymentsPaymentInformation paymentInformation = new Ptsv2paymentsPaymentInformation();
        Ptsv2paymentsPaymentInformationCard paymentInformationCard = new Ptsv2paymentsPaymentInformationCard();
        paymentInformationCard.number("4111111111111111");
        paymentInformationCard.expirationMonth("12");
        paymentInformationCard.expirationYear("2031");
        paymentInformation.card(paymentInformationCard);

        requestObj.paymentInformation(paymentInformation);

        Ptsv2paymentsOrderInformation orderInformation = new Ptsv2paymentsOrderInformation();
        Ptsv2paymentsOrderInformationAmountDetails orderInformationAmountDetails = new Ptsv2paymentsOrderInformationAmountDetails();
        orderInformationAmountDetails.totalAmount("102.21");
        orderInformationAmountDetails.currency("USD");
        orderInformation.amountDetails(orderInformationAmountDetails);

        Ptsv2paymentsOrderInformationBillTo orderInformationBillTo = new Ptsv2paymentsOrderInformationBillTo();
        orderInformationBillTo.firstName("John");
        orderInformationBillTo.lastName("Doe");
        orderInformationBillTo.address1("1 Market St");
        orderInformationBillTo.locality("san francisco");
        orderInformationBillTo.administrativeArea("CA");
        orderInformationBillTo.postalCode("94105");
        orderInformationBillTo.country("US");
        orderInformationBillTo.email("test@cybs.com");
        orderInformationBillTo.phoneNumber("4158880000");
        orderInformation.billTo(orderInformationBillTo);

        requestObj.orderInformation(orderInformation);

        PtsV2PaymentsPost201Response result = null;
        try {
            ApiClient apiClient = new ApiClient();
            apiClient.merchantConfig = merchantConfig;
            PaymentsApi apiInstance = new PaymentsApi(apiClient);
            result = apiInstance.createPayment(requestObj);

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
