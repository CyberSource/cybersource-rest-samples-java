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
    private static String clientId = "Byox4jxUi6";
    private static String grantType = "";
    private static String clientSecret = "5ce33002-1653-45db-821b-d726a2c41c8f";
    private static String refreshToken = "";
    private static String accessToken = "";
    private static Properties merchantProp;
    public static boolean userCapture = false;
    public static MerchantConfig merchantConfig;
    public static boolean createUsingAuthCode = false;

    public static void main(String args[]) throws Exception {
        // Accept required parameters from args[] and pass to run.
        AccessTokenResponse result;
        if(createUsingAuthCode)
        {
            // Create Access Token using Auth Code
            code = "fcugsa";
            grantType = "authorization_code";
            result = postAccessTokenFromAuthCode();
        }
        else {
            // Create Access Token using Refresh Token
            grantType = "refresh_token";
            refreshToken = "eyJraWQiOiIxMGM2MTYxNzg2MzE2ZWMzMGJjZmI5ZDcyZGU4MzFjOSIsImFsZyI6IlJTMjU2In0.eyJqdGkiOiJiNDVlYTU2OS1mMjYzLTRiOGQtYTNlNy1kOTlmYzJiNjY1M2EiLCJzY29wZXMiOlsicGF5bWVudHNfd2l0aF9zdGFuZGFsb25lX2NyZWRpdCIsInBheW1lbnRzX3dpdGhvdXRfc3RhbmRhbG9uZV9jcmVkaXQiLCJ0cmFuc2FjdGlvbnMiXSwiaWF0IjoxNjA5ODI2NjgyMjQzLCJhc3NvY2lhdGVkX2lkIjoiZWJjMl9jYXNfb2F1dGh0cDIiLCJjbGllbnRfaWQiOiJCeW94NGp4VWk2IiwibWVyY2hhbnRfaWQiOiJjZ2syX3B1c2hfdGVzdHMiLCJleHBpcmVzX2luIjoxNjQxMzYyNjgyMjQzLCJ0b2tlbl90eXBlIjoicmVmcmVzaF90b2tlbiIsImdyYW50X3R5cGUiOiJhdXRob3JpemF0aW9uX2NvZGUiLCJncmFudF90aW1lIjoiMjAyMTAxMDQyMjAzIn0.dGm4jshBU2JCfI6o3h5qCcpe0_LlJ-HhmSo8BdDe91lYYtjaKJuBOer_dlHZCjMphL7IjVgTjNrCOHTiGv6JIUJQ6vK_log_FBIOtLylnKn6MCSzfwKpxSz7trz_eINmGGICz_6UhIN9w_f5qu3aWuO40Gv1yrIzlUOTajpgZ7CeDJpm9crGUmu3lNgjq1RPGuWaXmqFfBvnpmo-VVsdaR5tK2c1Iw1UTJJYvcjCv4Tw_gHxPcy3cMagoDQeUPqUV2YI7maddWuLnzeFO-01xfbNXMPMzfw8P09cznbWKSHZZlSEyXFaOMCz3zRoy-WJwHiNNasqZmvMI5P-ZrftdA";
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
        requestObj.code(code);
        requestObj.clientId(clientId);
        requestObj.grantType(grantType);
        requestObj.clientSecret(clientSecret);

        AccessTokenResponse result = null;
        try {
            merchantProp = Configuration.getMerchantDetails();

            // Set Authentication to NoAuth to call OAuth API
            merchantProp.setProperty("authenticationType","NoAuth");

            ApiClient apiClient = new ApiClient();
            merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

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
        requestObj.refreshToken(refreshToken);
        requestObj.clientId(clientId);
        requestObj.grantType(grantType);
        requestObj.clientSecret(clientSecret);

        AccessTokenResponse result = null;
        try {
            merchantProp = Configuration.getMerchantDetails();

            // Set Authentication to NoAuth to call OAuth API
            merchantProp.setProperty("authenticationType","NoAuth");

            ApiClient apiClient = new ApiClient();
            merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

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
        if (userCapture) {
            processingInformation.capture(true);
        }

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
