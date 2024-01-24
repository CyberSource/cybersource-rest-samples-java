package samples.networkTokenization;

import Api.TokenApi;
import Data.Configuration;
import Invokers.ApiClient;
import Invokers.ApiException;
import Model.PostPaymentCredentialsRequest;
import com.cybersource.authsdk.core.MerchantConfig;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

public class PaymentCredentialsFromNetworkToken {
    private static String responseCode = null;
    private static String status = null;
    private static Properties merchantProp;

    public static void WriteLogAudit(int status) {
        String filename = MethodHandles.lookup().lookupClass().getSimpleName();
        System.out.println("[Sample Code Testing] [" + filename + "] " + status);
    }

    public static void main(String[] args) throws Exception {
        if (args != null && args.length > 0) {
            String tokenID = args[0];
            run(tokenID);
        } else {
            run(null);
        }
    }

    public static String run(String tokenID) {
        String result = null;
        String profileid = "93B32398-AD51-4CC2-A682-EA3E93614EB1";
        if (tokenID == null) {
            tokenID = CreateInstrumentIdentifierCardEnrollForNetworkToken.run().getId();
        }
        try {
            merchantProp = Configuration.getMerchantDetails();
            ApiClient apiClient = new ApiClient();
            MerchantConfig merchantConfig = new MerchantConfig(merchantProp);
            apiClient.merchantConfig = merchantConfig;

            TokenApi apiInstance = new TokenApi(apiClient);
            PostPaymentCredentialsRequest postPaymentCredentialsRequest = new PostPaymentCredentialsRequest();
            postPaymentCredentialsRequest.paymentCredentialType("NETWORK_TOKEN");
            result = apiInstance.postTokenPaymentCredentials(tokenID, postPaymentCredentialsRequest, profileid);
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
